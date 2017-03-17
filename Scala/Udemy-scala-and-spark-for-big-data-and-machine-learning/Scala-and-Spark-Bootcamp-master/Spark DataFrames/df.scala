import org.apache.spark.sql.SparkSession

val spark = SparkSession.builder().getOrCreate

val df = spark.read.option("header", true).
  option("inferSchema", true).
  csv("CitiGroup2006_2008")

df.head(5).foreach(println)

df.describe().show()