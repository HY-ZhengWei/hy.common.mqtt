

del /Q hy.common.mqtt.jar
del /Q hy.common.mqtt-sources.jar


call mvn clean package
cd .\target\classes

rd /s/q .\org\hy\common\mqtt\junit


jar cvfm hy.common.mqtt.jar META-INF/MANIFEST.MF META-INF org

copy hy.common.mqtt.jar ..\..
del /q hy.common.mqtt.jar
cd ..\..





cd .\src\main\java
xcopy /S ..\resources\* .
jar cvfm hy.common.mqtt-sources.jar META-INF\MANIFEST.MF META-INF org 
copy hy.common.mqtt-sources.jar ..\..\..
del /Q hy.common.mqtt-sources.jar
rd /s/q META-INF
cd ..\..\..

pause