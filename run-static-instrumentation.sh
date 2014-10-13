#!/bin/sh
./build.sh
./instrument.sh

# run:
export CLASSPATH=classes
for file in `ls lib` ; do export  CLASSPATH=$CLASSPATH:lib/$file; done
for file in `ls src/edge/models` ; do export  CLASSPATH=$CLASSPATH:edge/models/$file; done
java -cp "$CLASSPATH" -DoutputDirectory=$OUTDIR edge.logic.PMT

