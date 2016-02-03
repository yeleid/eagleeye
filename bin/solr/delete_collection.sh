#!/usr/bin/env bash

# delete solr collection
echo "delete solr collection..."
SOLR_COLLECTION="eagleeye"
SOLR="http://172.31.13.76:8983/solr"
ZK="172.31.13.75:2181/solr"
solrctl --solr $SOLR --zk $ZK collection --delete $SOLR_COLLECTION
solrctl --solr $SOLR --zk $ZK instancedir --delete $SOLR_COLLECTION