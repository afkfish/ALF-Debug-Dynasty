#!/bin/bash

# Run tests
./gradlew test

# Check if tests were successful
if [ $? -eq 0 ]
then
  echo "JunitTests passed, starting application..."
  ./gradlew bootRun
else
  echo "Tests failed, not starting application."
fi