#!/bin/bash -e
URL="https://repo1.maven.org/maven2/org/apache/maven/apache-maven"
# Define the versions we would like to test.
#VERSIONS=('3.0.5' '3.1.1' '3.2.5' '3.3.9' '3.5.4' '3.6.3' '3.8.1' '3.8.2' '3.8.3' '3.8.4')
VERSIONS=('3.0.5' '3.8.4')
JDK=$1

for i in "${VERSIONS[@]}"; do
	if [ -f "apache-maven-$i-bin.tar.gz" ]; then
		echo "File apache-maven-$i-bin.tar.gz already downloaded."
	else
		wget $URL/$i/apache-maven-$i-bin.tar.gz
	fi
done
# Unpack
for i in "${VERSIONS[@]}"; do
	if [ -d "apache-maven-$i" ]; then
		echo "Directory apache-maven-$i exists."
	else
		tar -zxf apache-maven-$i-bin.tar.gz
	fi
done


cd reactor
( IFS=, ; hyperfine -w 5 --export-markdown ../src/site/markdown/results-JDK.md -L VERSION "${VERSIONS[*]}" -n '{VERSION}' '../apache-maven-{VERSION}/bin/mvn clean' )
cd ..
