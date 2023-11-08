#!/bin/bash

# Define the Maven version to use
MAVEN_VERSION="4.0.0"

# Check if Maven is installed
if ! command -v mvn &> /dev/null
then
    # If Maven is not installed, download and install it
    echo "Maven is not installed. Installing..."
    wget "https://archive.apache.org/dist/maven/maven-3/$MAVEN_VERSION/binaries/apache-maven-$MAVEN_VERSION-bin.zip" -O "/tmp/apache-maven-$MAVEN_VERSION-bin.zip"
    unzip "/tmp/apache-maven-$MAVEN_VERSION-bin.zip" -d "/tmp"
    export MAVEN_HOME="/tmp/apache-maven-$MAVEN_VERSION"
    export PATH="$MAVEN_HOME/bin:$PATH"
fi

# Maven commands
mvn clean compile
