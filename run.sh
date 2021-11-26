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

#mkdir -p src/site/markdown
#mkdir -p /var/www/soebes.team/site/

sdk use java 8.0.302-open
./testing.sh JDK8

sdk use java 11.0.12-open
./testing.sh JDK11

sdk use java 17.0.1-open
./testing.sh JDK17

./apache-maven-${APACHE_MAVEN_VERSION}/bin/mvn clean site
./apache-maven-${APACHE_MAVEN_VERSION}/bin/mvn site:stage -DstagingDirectory=/var/www/soebes.team/site/