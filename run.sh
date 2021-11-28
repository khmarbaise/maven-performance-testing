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
sdk use java 17.0.1-open
# Download all Apache Versions into appropriate directory.
./apache-maven-${APACHE_MAVEN_VERSION}/bin/mvn --no-transfer-progress -B -Pperformance initialize
# build performance tool.
./apache-maven-${APACHE_MAVEN_VERSION}/bin/mvn --no-transfer-progress -B clean package -DskipTests

# Generate different scenarios
java -jar target/performance-1.0-SNAPSHOT.jar sc --nof 10,20,30
#
# Run real test.
java -jar target/performance-1.0-SNAPSHOT.jar exec --nom 10 --jdk JDK8
java -jar target/performance-1.0-SNAPSHOT.jar exec --nom 20 --jdk JDK8
java -jar target/performance-1.0-SNAPSHOT.jar exec --nom 30 --jdk JDK8

java -jar target/performance-1.0-SNAPSHOT.jar exec --nom 10 --jdk JDK11
java -jar target/performance-1.0-SNAPSHOT.jar exec --nom 20 --jdk JDK11
java -jar target/performance-1.0-SNAPSHOT.jar exec --nom 30 --jdk JDK11

java -jar target/performance-1.0-SNAPSHOT.jar exec --nom 10 --jdk JDK17
java -jar target/performance-1.0-SNAPSHOT.jar exec --nom 20 --jdk JDK17
java -jar target/performance-1.0-SNAPSHOT.jar exec --nom 30 --jdk JDK17

./apache-maven-${APACHE_MAVEN_VERSION}/bin/mvn  --no-transfer-progress -B site site:stage -DstagingDirectory=/home/tmpt/public_html/