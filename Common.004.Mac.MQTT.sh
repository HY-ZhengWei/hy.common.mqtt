#!/bin/sh

cd ./bin


rm -R ./org/hy/common/mqtt/junit


jar cvfm hy.common.mqtt.jar MANIFEST.MF META-INF org

cp hy.common.mqtt.jar ..
rm hy.common.mqtt.jar
cd ..





cd ./src
jar cvfm hy.common.mqtt-sources.jar MANIFEST.MF META-INF org 
cp hy.common.mqtt-sources.jar ..
rm hy.common.mqtt-sources.jar
cd ..
