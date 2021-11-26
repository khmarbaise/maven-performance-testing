#!/bin/bash

mkdir -p src/site/markdown

sdk install java 8.0.302-open
./testing.sh JDK8

sdk install java 11.0.12-open
./testing.sh JDK11

sdk install java 17.0.1-open
./testing.sh JDK17
