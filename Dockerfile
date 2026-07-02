# Stage 1: Build the application
FROM eclipse-temurin:21-jdk AS builder

# Establecer el directorio de trabajo
WORKDIR /app

# Copiar los archivos necesarios para construir (pom.xml y archivos de código)
COPY . .

# Construir la aplicación
RUN ./mvnw clean package -DskipTests

# Stage 2: Run the application
FROM eclipse-temurin:21-jre

# Establecer el directorio de trabajo
WORKDIR /app

# Copiar el archivo JAR generado desde la etapa de construcción
# Esto busca cualquier archivo .jar en la carpeta target
COPY --from=builder /app/target/*.jar app.jar

# Exponer el puerto
EXPOSE 8080

# Comando para ejecutar la app
ENTRYPOINT ["java", "-jar", "app.jar"]