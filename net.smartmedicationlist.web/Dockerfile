FROM maven:3.5-jdk-8-alpine
RUN mkdir -p /usr/src/app
WORKDIR /usr/src/app
ADD . /usr/src/app
RUN mvn -U clean install

FROM tomcat:9.0.12-jre11
EXPOSE 8080
RUN rm -rf /usr/local/tomcat/webapps/*
COPY --from=0 /usr/src/app/target/smartmedicationlist*.war /usr/local/tomcat/webapps/ROOT.war
CMD ["catalina.sh","run"]
