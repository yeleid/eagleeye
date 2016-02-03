# Eagle Eye
An app built on CDH for tracking jobs that run in YARN framework with different priority. Those runtime "logs" are critical, in general they can be used for:

* real-time monitor, check whether the most critical jobs have gained enough resources;
* resource usage summary reporting at job/queue granularity;
* resource allocation snapshot to facilitate (cluster) historical status cross-check.

#### 1. Background

#### 2. Prerequisite

#### 3. Eagle Eye Setup & Demonstration

In CDH, Hue now can provides a very easy way to build custom dashboards and visualizations, like below:

![hue](docs/img/hue.png)

Since the data are all in CDH, with standard/open protocol, you can easily retrieve the partition and build a more dedicated dashboards, e.g. using [d3](http://d3js.org/) library.

#### 4. References

*Flume solrsink: https://flume.apache.org/FlumeUserGuide.html*

*Cloudera distribution of Apache Kafka: http://www.cloudera.com/documentation/kafka/latest/topics/kafka_packaging.html*

*Yarn RM REST api: https://hadoop.apache.org/docs/current/hadoop-yarn/hadoop-yarn-site/ResourceManagerRest.html*



    


    