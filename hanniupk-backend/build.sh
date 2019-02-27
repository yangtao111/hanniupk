#!/bin/bash

mvn clean package -Dmaven.test.skip=true -Ptest -U

docker build -t hanniupk-backend-app:latest .

docker tag hanniupk-backend-app hub.c.163.com/yt0001/hanniupk-backend-app

docker push hub.c.163.com/yt0001/hanniupk-backend-app