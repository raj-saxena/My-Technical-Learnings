# Kafka

[Apache Kafka](https://kafka.apache.org/) is a distributed commit log that guarantees ordering and at-least-once delivery. It is a stream that can have variable producers and consumers publishing messages for a particular topic.

A **topic** is the core abstraction that is used for a set of records. Kafka maintains partition for each topic and each record contains a sequential id called **offset**.

**Producers** publish data to the topics of their choice. 

**Consumers** label themselves with a consumer group name, and each record published to a topic is delivered to one consumer instance within each subscribing consumer group. Consumer instances can be in separate processes or on separate machines. 

The consumer group concept in Kafka generalizes these two concepts. 
* *As with a queue* the consumer group allows you to divide up processing over a collection of processes (the members of the consumer group). 
* *As with publish-subscribe*, Kafka allows you to broadcast messages to multiple consumer groups. 


 It's strength lies in being able to support a high volume  of messages for use-cases like user-activity, metrics, logs, streams of data for processing pipelines.


Note: Apache Pulsar is probably better than Kafka coz it gives everything Kafka gives and more - https://streaml.io/blog/pulsar-streaming-queuing
