#!/bin/bash

mkdir .tmp
cd app
zip -r -X ../.tmp/app.zip *
cd ..
nw .tmp/app.zip
echo "Launched EDGE PMT"