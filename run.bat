@echo off
rem gradlew setupDecompWorkspace  
rem gradlew idea
rem gradlew eclipse
gradlew -Dhttp.proxyHost=127.0.0.1 -Dhttp.proxyPort=1080 -Dhttps.proxyHost=127.0.0.1 -Dhttps.proxyPort=1080 %*