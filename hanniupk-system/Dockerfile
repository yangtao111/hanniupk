FROM hub.c.163.com/library/java:8-alpine

MAINTAINER yangtao 6248594125@qq.com

ADD hanniupk-system-app/target/*.jar app.jar

EXPOSE 8080

ENTRYPOINT ["java","-jar","/app.jar"]