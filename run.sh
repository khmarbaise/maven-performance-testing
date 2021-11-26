#!/bin/bash

mkdir -p src/site/markdown
mkdir -p /var/www/soebes.team/site/

sdk install java 8.0.302-open
./testing.sh JDK8

sdk install java 11.0.12-open
./testing.sh JDK11

sdk install java 17.0.1-open
./testing.sh JDK17

./apache-maven-3.8.3/bin/mvn clean site
./apache-maven-3.8.3/bin/mvn site:stage -DstagingDirectory=/var/www/soebes.team/site/