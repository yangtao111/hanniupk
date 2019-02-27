FROM hub.c.163.com/library/java:8-alpine

MAINTAINER yangtao 6248594125@qq.com

ADD target/*.jar app.jar

EXPOSE 8000

ENTRYPOINT ["java","-jar","/app.jar"]