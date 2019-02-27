#!/bin/bash

mvn clean package -Dmaven.test.skip=true -Ptest -U

docker build -t hannkupk-cloud-register:latest .

docker tag hannkupk-cloud-register hub.c.163.com/yt0001/hannkupk-cloud-register

docker push hub.c.163.com/yt0001/hannkupk-cloud-register