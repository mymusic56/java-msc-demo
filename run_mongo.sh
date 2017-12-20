#!/bin/bash

PROJECT_PATH=$(pwd)
JAR_PATH=$PROJECT_PATH/lib
BIN_PATH=$PROJECT_PATH/bin
SRC_PATH=$PROJECT_PATH/src

#java -classpath $BIN_PATH:$JAR_PATH/gson-2.7.jar com.iflytek.msp.lfasr.Test2
#nohup java -classpath $BIN_PATH:$JAR_PATH/gson-2.7.jar:$JAR_PATH/lfasr-2.0.0.1005.jar:$JAR_PATH/mysql-connector-java-5.1.45-bin.jar  com.iflytek.msp.lfasr.IndexMongo &
nohup java -classpath $BIN_PATH:$JAR_PATH/bson-3.6.0.jar:$JAR_PATH/gson-2.7.jar:$JAR_PATH/lfasr-2.0.0.1005.jar:$JAR_PATH/mysql-connector-java-5.1.45-bin.jar:$JAR_PATH/mongodb-driver-3.6.0.jar:$JAR_PATH/mongodb-driver-core-3.6.0.jar  com.iflytek.msp.lfasr.IndexMongo > /dev/null & 
