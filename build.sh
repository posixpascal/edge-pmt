#!/bin/sh

# This script is an example of running instrumentation without Maven or Ant, just a simple command line.

# clean
rm -rf classes/*

# build classpath
for file in `ls lib` ; do export  CLASSPATH=$CLASSPATH:lib/$file; done
export  CLASSPATH=$CLASSPATH:src/

# compile
 

javac -cp $CLASSPATH -d classes src/edge/logic/*.java  

