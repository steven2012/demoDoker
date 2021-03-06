# Alpine for core-service-1.0-local-SNAPSHOT
# ./gradlew clean build
# Build image with:  docker build -t geta-manager-government-list .

# Use a parent image
FROM openjdk:8-jdk-slim
#FROM 10.25.10.193:8090/centos8_jdk8:v1
# Set the working directory
WORKDIR /opt/getta/games/services/manager-government-list

ADD build/libs /opt/getta/games/services/manager-government-list/
ADD src/main/resources  /opt/getta/games/services/manager-government-list/resources/
#ADD config /opt/getta/games/services/manager-government-list/config/
#ADD external-libs /opt/getta/games/services/manager-government-list/external-libs/

### Set Environment
#ENV SERVER_HOME /opt/getta/games/services/manager-government-list

# Make internal port available to the world outside this container
EXPOSE 3000

#permisos de carpeta
RUN chmod -R 777 /opt/getta

ENV TZ=America/Bogota
RUN ln -snf /usr/share/zoneinfo/$TZ /etc/localtime && echo $TZ > /etc/timezone

ENV JAVA_OPTS="-Xmx2g"

### Start instance
ENTRYPOINT ["java","-jar", "-Dspring.profiles.active=prod", "-Dfile.encoding=ISO8859_1", "/opt/getta/games/services/manager-government-list/geta-manager-goverment-list-1.0-SNAPSHOT.jar"]

# Run app when the container launches
#CMD ["java", "-jar", "geta-manager-goverment-list-1.0-SNAPSHOT.jar"]