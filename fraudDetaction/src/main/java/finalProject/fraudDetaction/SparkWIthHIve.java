package finalProject.fraudDetaction;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.function.MapFunction;
import org.apache.spark.api.java.function.VoidFunction;
import org.apache.spark.network.protocol.Encoders;
import org.apache.spark.rdd.RDD;
import org.apache.spark.sql.DataFrameWriter;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SparkSession;
import org.apache.spark.streaming.Time;
import org.apache.spark.streaming.api.java.JavaDStream;

import scala.collection.mutable.HashMap;

public class SparkWIthHIve {

	
	public static void main (String [] args){
	
		SparkSession spark = getSession();
		CreateAndLoadTable.crateTable(spark);
		CreateAndLoadTable.loadData(spark);
		countFraud();
		showFraudData();
		countFlagedFraud();
		showFlagedFraud();
		//save();
		
		spark.stop();
	}
	
	public static void initSparkSql(){
		SparkSession spark = getSession();
		Dataset<Row> init = spark.sql("SELECT * FROM fraudinfo limit 1");
		if(init.count() < 1){
		CreateAndLoadTable.crateTable(spark);
		CreateAndLoadTable.loadData(spark);
		}
		spark.stop();
	}
	
	
	public static SparkSession getSession(){
		return Configration.getConfig();
	}
	
	public static List<FraudDetationInfo> fraudTxn(){
		SparkSession spark = getSession();
		Dataset<Row> fraudData= spark.sql("SELECT * FROM fraudinfo WHERE isFraud = 1");
		List<Row> rows =fraudData.collectAsList();
		List<FraudDetationInfo> fraudInfo =  new ArrayList<FraudDetationInfo>();
		for(Row row: rows){
			fraudInfo.add(new FraudDetationInfo(row.getInt(0),row.getString(1),row.getDouble(2)
				,row.getString(3),row.getDouble(4),row.getDouble(5),row.getString(6),row.getDouble(7),row.getDouble(8),row.getInt(9),row.getInt(10)));
		}
		spark.stop();
		return fraudInfo;
		
	}
	
public static void  save() {
	SparkSession spark = getSession();
	spark.sql("LOAD DATA LOCAL INPATH '/home/cloudera/Desktop/fraudData/outputData/part-00000' INTO TABLE fraudinfo");
	spark.stop();
//	Files.deleteIfExists(Paths.get("/home/cloudera/Desktop/fraudData/outputData/part-0000"));
		
	}
	
	
	public static void countFraud(){
		SparkSession spark = getSession();
		spark.sql("SELECT COUNT(*) FROM fraudinfo where isFraud = 1 ").show(); 
		spark.stop();
	}
	

	
	public static Dataset<Row> showFraudData(){
		SparkSession spark= getSession();
		Dataset<Row> data= spark.sql("SELECT * FROM fraudinfo where isFraud = 1 limit 5");
		data.show();
		spark.stop();
		return data;
	}
	
	public static void countFlagedFraud(){
		SparkSession spark= getSession();
		spark.sql("SELECT COUNT(*) FROM fraudinfo where isFlaggedFraud = 1").show();
		spark.stop();
	}
	
	public static void showFlagedFraud(){
		SparkSession spark= getSession();
		spark.sql("SELECT * FROM fraudinfo where isFlaggedFraud = 1 limit 5").show();
		spark.stop();
	}
	
}
