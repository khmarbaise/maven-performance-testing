#!/bin/bash
# Boot Strapping Apache Maven for later compilation of the performance tool.
URL="https://repo1.maven.org/maven2/org/apache/maven/apache-maven"
APACHE_MAVEN_VERSION="3.8.4"
APACHE_MAVEN="apache-maven-${APACHE_MAVEN_VERSION}-bin.tar.gz"

if [ -f "${APACHE_MAVEN}" ]; then
  echo "File ${APACHE_MAVEN} already downloaded."
else
  wget $URL/${APACHE_MAVEN_VERSION}/${APACHE_MAVEN}
fi
# Unpack
if [ -d "apache-maven-${APACHE_MAVEN_VERSION}" ]; then
  echo "Directory apache-maven-${APACHE_MAVEN_VERSION} exists."
else
  tar -zxf $APACHE_MAVEN
fi

source ~/.sdkman/bin/sdkman-init.sh

# TODO: Maybe we should precompile the tool via GraalVM?
sdk use java 17.0.3-tem

cd /home/tmpt/maven-performance-testing
#
# Download all Apache Versions into appropriate directory.
./apache-maven-${APACHE_MAVEN_VERSION}/bin/mvn --no-transfer-progress -B -Pperformance initialize
# build performance tool.
./apache-maven-${APACHE_MAVEN_VERSION}/bin/mvn --no-transfer-progress -B clean package -DskipTests

# Generate different scenarios
# TODO: Find a better way to keep in sync
java -jar target/performance-1.0-SNAPSHOT.jar sc --nof 10,20,30,40,50,60,70,80,90,100,110,120,130,140,150,160,170,180,190,200,300,400,500,600,700,800,900,1000
#
# Run real test.
# TODO: Find a better to keep in sync
NUMBER="10 20 30 40 50 60 70 80 90 100 110 120 130 140 150 160 170 180 190 200 300 400 500 600 700 800 900 1000"
#
for num in $NUMBER; do
  java -jar target/performance-1.0-SNAPSHOT.jar exec --nom ${num}
done

./apache-maven-${APACHE_MAVEN_VERSION}/bin/mvn  --no-transfer-progress -B site site:stage -DstagingDirectory=/home/tmpt/public_html/