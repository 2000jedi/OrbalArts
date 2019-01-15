@echo off
SET "JAVA_HOME=E:\Development\jdk8_121\"
SET JAVA_EXE=%JAVA_HOME%\bin\java.exe
SET GRADLE_USER_HOME=E:\Development\.gradle\
SET PATH=%JAVA_HOME%\\bin\\;%PATH%
SET "JAVA_OPTS=-Xmx2048m"
rem gradlew -Dhttp.proxyHost=127.0.0.1 -Dhttp.proxyPort=1080 -Dhttps.proxyHost=127.0.0.1 -Dhttps.proxyPort=1080 setupDecompWorkspace  
rem gradlew -Dhttp.proxyHost=127.0.0.1 -Dhttp.proxyPort=1080 -Dhttps.proxyHost=127.0.0.1 -Dhttps.proxyPort=1080 idea
rem gradlew -Dhttp.proxyHost=127.0.0.1 -Dhttp.proxyPort=1080 -Dhttps.proxyHost=127.0.0.1 -Dhttps.proxyPort=1080 eclipse
gradlew -Dhttp.proxyHost=127.0.0.1 -Dhttp.proxyPort=1080 -Dhttps.proxyHost=127.0.0.1 -Dhttps.proxyPort=1080 %*