package sparkStreming;


import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

import org.apache.calcite.linq4j.function.Function1;
import org.apache.log4j.Logger;
import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.function.Function3;
import org.apache.spark.broadcast.Broadcast;
import org.apache.spark.rdd.RDD;
import org.apache.spark.streaming.Duration;
import org.apache.spark.streaming.Durations;
import org.apache.spark.streaming.State;
import org.apache.spark.streaming.StateSpec;
import org.apache.spark.streaming.Time;
import org.apache.spark.streaming.api.java.JavaDStream;
import org.apache.spark.streaming.api.java.JavaMapWithStateDStream;
import org.apache.spark.streaming.api.java.JavaPairDStream;
import org.apache.spark.streaming.api.java.JavaPairInputDStream;
import org.apache.spark.streaming.api.java.JavaStreamingContext;
import org.apache.spark.streaming.kafka.KafkaUtils;
import org.joda.time.LocalDate;

import com.google.common.base.Optional;

import finalProject.fraudDetaction.FraudDetationInfo;
import finalProject.fraudDetaction.SparkWIthHIve;
import kafka.serializer.StringDecoder;
import scala.Tuple2;
import scala.Tuple3;
import util.Util;


public class FraudProcessor {

	;
	private static Logger logger = Logger.getLogger(FraudProcessor.class);
	
	public static void main (String [] args)throws Exception {
		final List<FraudDetationInfo>  frauds = SparkWIthHIve.fraudTxn();
		SparkConf conf= new SparkConf()
						.setAppName("SparkStreming")
						.setMaster("local[4]");
		//batch interval of 5 seconds for incoming stream
		JavaStreamingContext jssc = new JavaStreamingContext(conf,Durations.seconds(5));
		
		
		//read and set Kafka properties
		 Map<String, String> kafkaParams = new HashMap<String, String>();
		 kafkaParams.put("zookeeper.connect", Util.zookeeper);
		 kafkaParams.put("metadata.broker.list", Util.brokerList);
		 String topic = Util.topic;
		 Set<String> topicsSet = new HashSet<String>();
		 topicsSet.add(topic);
		 //create direct kafka stream
		 JavaPairInputDStream<String, TransactionInfo> directKafkaStream = KafkaUtils.createDirectStream(
			        jssc,
			        String.class,
			        TransactionInfo.class,
			        StringDecoder.class,
			        TxnInfoDecoder.class,
			        kafkaParams,
			        topicsSet
			    );
		 logger.info("Starting Stream Processing");
		 
		 
		
		 JavaDStream<TransactionInfo> nonFilteredIotDataStream = directKafkaStream.map(tuple -> tuple._2());
		 
	
		JavaDStream<FraudDetationInfo> tnxDataPairStream = nonFilteredIotDataStream.map(tnx -> new FraudDetationInfo(2/*(new Date()).getHours()*/, 
					tnx.getType(),
					tnx.getAmount(),
					tnx.getNameOrig(),0.0 ,0.0,
					tnx.getNameDest(), 0.0,0.0,
					frauds.parallelStream().anyMatch(fraud -> ( 
									(tnx.getType().equals("CASH_OUT") || tnx.getType().equals("TRANSFER")) && 
									(fraud.getNumeOrig().equals(tnx.getNameOrig()) ||
									fraud.getNameDest().equals(tnx.getNameDest()) || 
									tnx.getAmount() > 1400000)
								)
							) ? 1 : 0
					,tnx.getAmount() > 1200000 ?  1 : 0));
		
		 
		
		tnxDataPairStream.foreachRDD(rdd -> rdd.collect().forEach(rec -> System.out.println(rec)));
		 
		tnxDataPairStream.cache();
		
		JavaDStream<FraudDetationInfo> windowData = tnxDataPairStream.window(Durations.minutes(5));	
		 windowData.foreachRDD(val -> val.coalesce(1).saveAsTextFile("/home/cloudera/Desktop/fraudData/outputData"));
		// SparkWIthHIve.save();
		 jssc.start(); 
		 jssc.awaitTermination();  
		 
		
	}
	
	

}
