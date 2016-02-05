#!/usr/bin/env bash

CONFIG="bin/flume/properties/flume-kafka.properties"

flume-ng agent -n a1 -c bin/flume/conf -f $CONFIG