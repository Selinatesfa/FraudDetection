#!/bin/bash

setup() {
    echo "*****************************************************************"
    echo "********************** Spark Streaming *********************"
    echo "*****************************************************************"
}

runSimulator() {
    mvn exec:java -Dexec.mainClass="sparkStreming.FraudProcessor"
}

tearDown()
{
  echo "Shuting SparkStreaming"
}

setup;
runSimulator;
tearDown;