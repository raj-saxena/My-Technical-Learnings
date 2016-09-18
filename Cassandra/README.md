## Hadoop 
	* Combination of => Hadoop distributed file system (HDFS) which constitutes Hadoop's storage layer and a distributed computation framework(MapReduce) which constitutes the processing layer.
	* Not suitable for Real-time, but for batch processing of huge data.
	* Data access is sequential.

## HBase
	* Modelled after Google's BigTable, it stores data as key/value pairs.
	* Better suitable for real-time.
	* HBase, Cassandra, couchDB, Dynamo, and MongoDB are some of the databases that store huge amounts of data and access the data in a random manner.

## Hive 
	* It sits on top of your Hadoop cluster and provides you an SQL like interface to the data stored.
	* Use when mapReduce isn't needed.


More info [here](http://cloudfront.blogspot.in/2013/04/hadoop-herd-when-to-use-what.html#.UXwRFjWH6IQ)

## Cassandra vs HBase
	* http://www.infoworld.com/article/2610656/database/big-data-showdown--cassandra-vs--hbase.html 
	* https://www.linkedin.com/pulse/real-comparison-nosql-databases-hbase-cassandra-mongodb-sahu (look at the conclusion).

_____________________________

### Keyspace 
	* Like a db schema in MySQL
	* Has column families.

### Column
	* A column is the basic data structure of Cassandra with three values, namely key or column name, value, and a time stamp. 

The syntax of creating a Keyspace is as follows −

CREATE KEYSPACE Keyspace name
WITH replication = {'class': 'SimpleStrategy', 'replication_factor' : 3};

_____________________________
## Cassandra installation
	pip install cql & \ 
	curl -OL http://downloads.datastax.com/community/dsc.tar.gz & \
	tar -xzf dsc.tar.gz & \
	mv dsc-cassandra-3.0.8 /opt & \
	cd /opt/dsc-cassandra-3.0.8/bin & \
	sudo ./cassandra


## CQL 
	Cassandra Query Language - similar to SQL. Eg:
	* Create keyspace
		> CREATE KEYSPACE Keyspace name WITH replication = {'class': 'SimpleStrategy', 'replication_factor' : 3};
	* Create table 
		> CREATE TABLE emp(emp_id int PRIMARY KEY, emp_name text, emp_city text, emp_sal varint, emp_phone varint );
	* Insert in table 
		> INSERT INTO emp (emp_id, emp_name, emp_city, emp_phone, emp_sal) VALUES(1,'raj', 'Hyderabad', 2313131231, 50000);
	* Query 
		> SELECT * FROM emp;
	* Create index
		> CREATE INDEX name ON emp1 (emp_name);
	* Update 
		> UPDATE emp SET emp_city='Delhi',emp_sal=50000 WHERE emp_id=2;

