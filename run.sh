#!/bin/bash
source ~/.sdkman/bin/sdkman-init.sh

# Install Maven version 3.8.8
sdk install maven 3.8.8

# TODO: Maybe we should precompile the tool via GraalVM?
sdk use java 17.0.7-tem

cd /home/tmpt/maven-performance-testing
#
# Download all Apache Versions into appropriate directory.
mvn --no-transfer-progress -B -Pperformance initialize -ntp
# build performance tool.
mvn --no-transfer-progress -B clean package -DskipTests -ntp

# Generate different scenarios
# TODO: Find a better way to keep in sync
java -jar target/performance-1.0-SNAPSHOT.jar sc --nof 10,20,30,40,50,60,70,80,90,100,110,120,130,140,150,160,170,180,190,200,300,400,500,600,700,800,900,1000,2000,3000,4000,5000,6000,7000,8000,9000,10000
#
# Run real test.
# TODO: Find a better to keep in sync
NUMBER="10 20 30 40 50 60 70 80 90 100 110 120 130 140 150 160 170 180 190 200 300 400 500 600 700 800 900 1000 2000 3000 4000 5000 6000 7000 8000 9000 10000"
#
for num in $NUMBER; do
  java -jar target/performance-1.0-SNAPSHOT.jar exec --nom ${num}
done

mvn  --no-transfer-progress -B site site:stage -DstagingDirectory=/var/www/maven.soebes.team/