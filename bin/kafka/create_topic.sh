#!/usr/bin/env bash

ZOOKEEPER="172.31.13.75:2181"
TOPIC="eagleeye"

kafka-topics --create --zookeeper $ZOOKEEPER --topic $TOPIC --partitions 1 --replication-factor 1