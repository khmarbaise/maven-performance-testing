#!/bin/bash
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

sdk use java 8.0.302-open
mvn package -DskipTests
# Generate different scenarios
java -jar target/performance-1.0-SNAPSHOT.jar sc --nof 10,20,30
# Run real test.
java -jar target/performance-1.0-SNAPSHOT.jar exec --nom 10 --jdk JDK8
java -jar target/performance-1.0-SNAPSHOT.jar exec --nom 20 --jdk JDK8
java -jar target/performance-1.0-SNAPSHOT.jar exec --nom 30 --jdk JDK8

sdk use java 11.0.12-open
java -jar target/performance-1.0-SNAPSHOT.jar exec --nom 10 --jdk JDK11
java -jar target/performance-1.0-SNAPSHOT.jar exec --nom 20 --jdk JDK11
java -jar target/performance-1.0-SNAPSHOT.jar exec --nom 30 --jdk JDK11

sdk use java 17.0.1-open
java -jar target/performance-1.0-SNAPSHOT.jar exec --nom 10 --jdk JDK17
java -jar target/performance-1.0-SNAPSHOT.jar exec --nom 20 --jdk JDK17
java -jar target/performance-1.0-SNAPSHOT.jar exec --nom 30 --jdk JDK17

./apache-maven-${APACHE_MAVEN_VERSION}/bin/mvn clean site site:stage -DstagingDirectory=/home/tmpt/public_html/