#!/bin/bash

declare -a StringArray=("confluentinc/cp-zookeeper:5.2.1" "confluentinc/cp-enterprise-kafka:5.2.1"
  "confluentinc/cp-schema-registry:5.2.1" "confluentinc/kafka-connect-datagen:latest"
  "confluentinc/cp-enterprise-control-center:5.2.1" "confluentinc/cp-ksql-server:5.2.1" "confluentinc/cp-ksql-cli:5.2.1"
  "confluentinc/ksql-examples:5.1.2" "confluentinc/cp-kafka-rest:5.2.1")

for val in "${StringArray[@]}"; do
  docker pull $val
done
