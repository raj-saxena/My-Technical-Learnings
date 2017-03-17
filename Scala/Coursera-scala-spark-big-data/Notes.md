* HDFS has default blocks of 128 MB each, replicated across 3 nodes. Used to distribute data.
* MapReduce has JobManager that distributes tasks to TaskManagers across nodes. Used to distribute computation.

### Spark
	* A flexible alternative to MapReduce.
	* Can consume data from Cassandra, S3, HDFS
	* Can perform 100x faster than MapReduce. (By keeping in memory, instead of writing after every mapping and reducing).
	* Works on Resilient Distributed Datasets.

___
### RDD
	* Immutable, lazy evaluated and cacheable.
	* Go thru transformation and action.
		- Transformations are steps to follow.
		- Actions perform task and return values
	* `DataFrames` are cleaner syntax for managing RDDs.

#### Transformations and Actions
	* `Transformations` return new RDDs. Eg: map, filter, flatmap, groupBy. These are __lazy__.
	* `Actions` return single value as results. Eg: reduce, fold, aggregate. These are __eager__.
	* `Laziness/Eagerness` is how Spark limits network activity
___
### DataFrame
	* Spark and scala are moving away from RDD and towards DataFrames as they are simpler to understand.
	* Creating a dataFrame
		```
		import org.apache.spark.sql.SparkSession

		val spark = SparkSession.builder().getOrCreate

		val df = spark.read.option("header", true).option("inferSchema", true).csv("CitiGroup2006_2008")

		```
	* `df.columns` - get columns
	* `df("column")` - get particular column (not the data)
	* `df.select($"column1", $"columnN")` => show first 20 rows
	* `df.withColumn("columnName", <formulae>)` => create df with new column as specified by formulae.
