#!/bin/bash -e
JDK=$1
URL="https://repo1.maven.org/maven2/org/apache/maven/apache-maven"
VERSIONS=('3.0.5' '3.1.1' '3.2.5' '3.3.9' '3.5.4' '3.6.3' '3.8.1' '3.8.2' '3.8.3' '3.8.4')
#VERSIONS=('3.8.1' '3.8.2' '3.8.3' '3.8.4' '4.0.0-alpha-1-SNAPSHOT')

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

#COMMAND=()
#for i in "${VERSIONS[@]}"; do
#	COMMAND+=( "../apache-maven-$i/bin/mvn clean" )
#done
#
cd reactor

###hyperfine --warmup 5 --export-markdown ../results.md --parameter-list version "${versions[*]}" '../apache-maven-{version}/bin/mvn clean'
  ## ( IFS=, ; hyperfine -w 5 --export-markdown ../results-$jdk.md --parameter-list version "${VERSIONS[*]}" -n "$jdk-{version}" '../apache-maven-{version}/bin/mvn clean' )
#hyperfine --warmup 1 --export-markdown ../results.md "${COMMAND[@]}"

( IFS=, ; hyperfine -w 5 --export-markdown ../src/site/markdown/results-$jdk.md -L VERSION "${VERSIONS[*]}" -n 'apache-maven-{VERSION}' '../apache-maven-{VERSION}/bin/mvn clean' )
cd ..
