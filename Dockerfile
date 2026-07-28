FROM eclipse-temurin:21-jdk-alpine AS build
WORKDIR /app
COPY zero/ .
RUN ./mvnw clean package -DskipTests

FROM eclipse-temurin:21-jdk-alpine
WORKDIR /app
COPY --from=build /app/target/*.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "/app/app.jar"]