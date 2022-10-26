FROM eclipse-temurin:17-jdk-jammy

WORKDIR /app

COPY absolwentbackend/.mvn .mvn
COPY absolwentbackend/mvnw absolwentbackend/pom.xml ./
RUN apt-get update && apt-get install -y dos2unix 
RUN dos2unix mvnw
RUN chmod +x mvnw
RUN ./mvnw dependency:resolve

COPY absolwentbackend/src ./src
EXPOSE 80
CMD ["./mvnw", "spring-boot:run"]
