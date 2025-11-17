FROM eclipse-temurin:17-jdk AS builder

WORKDIR /app
COPY . .

RUN chmod +x ./gradlew && ./gradlew clean build -x test

FROM eclipse-temurin:17-jdk
WORKDIR /app
COPY --from=builder /app/build/libs/*.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java","-Duser.timezone=Asia/Seoul","-jar", "app.jar"]
