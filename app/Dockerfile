FROM eclipse-temurin:17
VOLUME /tmp
COPY build/libs/*.jar app.jar
ENTRYPOINT ["sh", "-c", "java ${JAVA_OPTS} -jar /app.jar"]
