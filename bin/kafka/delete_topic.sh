#!/usr/bin/env bash

ZOOKEEPER="172.31.13.75:2181"
TOPIC="eagleeye"

kafka-topics --delete --zookeeper $ZOOKEEPER --topic $TOPIC