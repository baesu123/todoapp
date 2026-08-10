# 1. OpenJDK 21 베이스 이미지 사용
FROM eclipse-temurin:21-jdk

# 2. 작업 디렉토리 설정
WORKDIR /app

# 3. Gradle 또는 Maven 빌드 결과물(JAR) 복사
COPY build/libs/todoapp-0.0.1-SNAPSHOT.jar app.jar

# 4. 애플리케이션 실행 명령어
ENTRYPOINT ["java", "-jar", "app.jar"]