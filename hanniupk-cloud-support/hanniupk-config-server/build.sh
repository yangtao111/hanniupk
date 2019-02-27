#!/bin/bash

mvn clean package -Dmaven.test.skip=true -Ptest -U

docker build -t hannkupk-config-server:latest .

docker tag hannkupk-config-server hub.c.163.com/yt0001/hannkupk-config-server

docker push hub.c.163.com/yt0001/hannkupk-config-server