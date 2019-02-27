#!/bin/bash

mvn clean package -Dmaven.test.skip=true -Ptest -U

docker build -t hannkupk-gateway:latest .

docker tag hannkupk-gateway hub.c.163.com/yt0001/hannkupk-gateway

docker push hub.c.163.com/yt0001/hannkupk-gateway