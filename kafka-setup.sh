#!/usr/bin/env bash

echo '(-) Starting configuration kafka environment'

docker exec -it easycrypto_zookeeper_1  kafka-topics --create --zookeeper localhost:32181 --replication-factor 1 --partitions 1 --topic input-topic

echo '(✔) Input topic created'

docker exec -it easycrypto_zookeeper_1  kafka-topics --create --zookeeper localhost:32181 --replication-factor 1 --partitions 1 --topic output-topic

echo '(✔) Output topic created'

docker exec -it easycrypto_zookeeper_1  kafka-topics --create --zookeeper localhost:32181 --replication-factor 1 --partitions 1 --topic valid-messages

echo '(✔) Valid messages topic created'

docker exec -it easycrypto_zookeeper_1  kafka-topics --create --zookeeper localhost:32181 --replication-factor 1 --partitions 1 --topic invalid-messages

echo '(✔) Invalid messages topic created'
