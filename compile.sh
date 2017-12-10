#!/bin/bash
PROJECT_PATH=/java/java-msc-demo
JAR_PATH=$PROJECT_PATH/lib
BIN_PATH=$PROJECT_PATH/bin
SRC_PATH=$PROJECT_PATH/src
SOURCE_PATH=$PROJECT_PATH/source


if [ -e $SRC_PATH/source.list ]; then
	rm $SRC_PATH/source.list
fi
#exit 0 
find $SRC_PATH/com -name *.java > $SRC_PATH/source.list
rm -rf $BIN_PATH/$ONSSERVER
mkdir $BIN_PATH/$ONSSERVER

javac -d $BIN_PATH/$ONSSERVER -classpath $JAR_PATH/gson-2.7.jar:$JAR_PATH/lfasr-2.0.0.1005.jar:$JAR_PATH/mysql-connector-java-5.1.45-bin.jar @$SRC_PATH/source.list

#复制配置文件
cp $SOURCE_PATH/*.properties $BIN_PATH

