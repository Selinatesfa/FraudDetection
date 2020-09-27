#!/bin/bash

setup() {
    echo "*****************************************************************"
    echo "********************** SparkSQL with hive *********************"
    echo "*****************************************************************"
}

runSimulator() {
    mvn exec:java -Dexec.mainClass="finalProject.fraudDetaction.SparkWIthHIve"
}

tearDown()
{
  echo "Shuting Spark SQl"
}

setup;
runSimulator;
tearDown;
