#!/bin/bash

mvn clean package -Dmaven.test.skip=true -Ptest -U

docker build -t hannkupk-system-app:latest .

docker tag hannkupk-system-app hub.c.163.com/yt0001/hannkupk-system-app

docker push hub.c.163.com/yt0001/hannkupk-system-app