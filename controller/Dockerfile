FROM adoptopenjdk:11-jre-hotspot
EXPOSE 8080
LABEL version="1.0"

ENV APP_DIR=/app
ENV APP_NAME=controller.jar

RUN mkdir $APP_DIR
COPY /controller/target/$APP_NAME $APP_DIR

WORKDIR $APP_DIR

CMD java -jar $APP_NAME