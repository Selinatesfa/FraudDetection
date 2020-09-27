package finalProject.fraudDetaction;
import java.io.File;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.apache.spark.api.java.function.MapFunction;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Encoders;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SparkSession;

public class CreateAndLoadTable {
	
	
	public static void crateTable(SparkSession spark){
		spark.sql("CREATE TABLE IF NOT EXISTS fraudinfo( "+
				"step INT, "+
				"type STRING, "+
				"amount DOUBLE, "+
				"nameOrig STRING , "+ 
				"oldbalanceOrg DOUBLE, "+
				"newbalanceOrig DOUBLE, "+
				"nameDest STRING, "+
				"oldbalanceDest DOUBLE, "+
				"newbalanceDest DOUBLE, "+
				"isFraud INT, "+
				"isFlaggedFraud INT ) "+
				"ROW FORMAT DELIMITED "+
				"FIELDS TERMINATED BY ',' "+
				"LINES TERMINATED BY '\n' "+
				"tblproperties('skip.header.line.count'='1') ");
	}
	
	public static  void loadData(SparkSession spark){
		
		Dataset<Row> df = spark.sql("LOAD DATA LOCAL INPATH '/home/cloudera/workspace/fraudDetaction/input' INTO TABLE fraudinfo");
		/*Dataset<Row> df = spark.sql("SELECT * FROM fraudinfo");
		df.coalesce(1).write().csv("/home/cloudera/Desktop/fraudData/fraudData.csv");*/
	}
	
}
