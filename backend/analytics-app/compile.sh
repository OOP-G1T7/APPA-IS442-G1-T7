#!/bin/bash

# Define the Maven version to use
MAVEN_VERSION="3.9.5"

# Check if Maven is installed
if ! command -v mvn &> /dev/null
then
    # If Maven is not installed, use Homebrew to install it
    echo "Maven is not installed. Installing Maven with Homebrew..."
    brew install maven
fi

# Maven commands
mvn clean compile
