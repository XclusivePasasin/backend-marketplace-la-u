# Etapa 1: Construir el .jar usando Maven y Java 25
FROM maven:3.9-eclipse-temurin-25 AS build
WORKDIR /app
COPY pom.xml .
COPY src ./src
# Compilamos saltando las pruebas para que sea más rápido
RUN mvn clean package -DskipTests

# Etapa 2: Ejecutar el .jar con Java 25
FROM eclipse-temurin:25-jdk
WORKDIR /app
# Copiamos el .jar generado en la etapa 1
COPY --from=build /app/target/*.jar app.jar
# Exponemos el puerto
EXPOSE 8080
# Comando para arrancar Spring Boot
ENTRYPOINT ["java", "-jar", "app.jar"]