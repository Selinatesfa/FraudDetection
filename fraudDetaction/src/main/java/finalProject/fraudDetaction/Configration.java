package finalProject.fraudDetaction;

import java.io.File;

import org.apache.spark.sql.SparkSession;

public class Configration {
	
	public static SparkSession getConfig(){
		String warehouseLocation = new File("spark-warehouse").getAbsolutePath();
	    SparkSession spark = SparkSession
	      .builder()
	      .master("local")
	      .appName("FraudDetationHive")
	      .config("spark.sql.warehouse.dir", "/user/hive/warehouse")
	      .config("hive.metastore.uris","thrift://localhost:9083")
	      .enableHiveSupport()
	      .getOrCreate();
	    
	    return spark;
	}
	
}
