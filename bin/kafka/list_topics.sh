#!/usr/bin/env bash

ZOOKEEPER="172.31.13.75:2181"

kafka-topics --list --zookeeper $ZOOKEEPER