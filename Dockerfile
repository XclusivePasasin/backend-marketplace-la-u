# Etapa 1: Construir el proyecto usando Maven y Java 25
FROM maven:3.9-eclipse-temurin-25 AS build
WORKDIR /app
COPY pom.xml .
COPY src ./src
# Compilamos saltando las pruebas para que sea más rápido
RUN mvn clean package -DskipTests

# Etapa 2: Ejecutar la aplicación con Java 25
FROM eclipse-temurin:25-jdk
WORKDIR /app
# ¡AQUÍ ESTÁ EL CAMBIO! Ahora buscamos el .war en lugar del .jar
COPY --from=build /app/target/*.war app.war
# Exponemos el puerto
EXPOSE 8080
# Comando para arrancar Spring Boot
# Comando para arrancar Spring Boot limitando la RAM a 256MB
ENTRYPOINT ["java", "-Xms256m", "-Xmx256m", "-Dspring.datasource.url=jdbc:mysql://api.lauapp.es:3306/db_laumarket", "-Dspring.datasource.username=dev_laumarket", "-Dspring.datasource.password=laumarket123", "-jar", "app.war"]