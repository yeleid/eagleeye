#!/usr/bin/env bash

sudo -u hive hadoop jar /opt/cloudera/parcels/CDH/jars/hadoop-examples.jar pi 500 500 &
sudo -u hive hadoop jar /opt/cloudera/parcels/CDH/jars/hadoop-examples.jar pi 500 500 &
sudo -u hive hadoop jar /opt/cloudera/parcels/CDH/jars/hadoop-examples.jar pi 1000 1000 &
sleep 10; sudo -u impala hadoop jar /opt/cloudera/parcels/CDH/jars/hadoop-examples.jar pi 500 500 &
sleep 15; sudo -u hue hadoop jar /opt/cloudera/parcels/CDH/jars/hadoop-examples.jar pi 10 10 &
sleep 15; sudo -u hue hadoop jar /opt/cloudera/parcels/CDH/jars/hadoop-examples.jar pi 100 100 &
sleep 20; sudo -u hue hadoop jar /opt/cloudera/parcels/CDH/jars/hadoop-examples.jar pi 100 100 &
sleep 25; sudo -u hue hadoop jar /opt/cloudera/parcels/CDH/jars/hadoop-examples.jar pi 100 100 &
sleep 35; sudo -u impala hadoop jar /opt/cloudera/parcels/CDH/jars/hadoop-examples.jar pi 500 500 &