# 1단계: 빌드 (Build Stage)
FROM eclipse-temurin:21-jdk-jammy AS build
WORKDIR /app
COPY . .

# [수정됨] Maven 대신 Gradle 래퍼 실행 권한 부여 및 빌드 (테스트 제외)
RUN chmod +x ./gradlew
RUN ./gradlew clean build -x test

# 2단계: 실행 (Run Stage)
FROM eclipse-temurin:21-jre-jammy
WORKDIR /app

# [수정됨] Gradle은 빌드 결과물이 'target'이 아닌 'build/libs'에 생성됨
COPY --from=build /app/build/libs/*.jar /app/app.jar

EXPOSE 8080
ENTRYPOINT ["java", "-jar", "/app/app.jar"]