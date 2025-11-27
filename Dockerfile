# ---- Etapa 1: Build ----
# Imagem oficial do Maven com JDK 21
FROM maven:3.9.6-eclipse-temurin-21 AS builder

WORKDIR /app

# Copia o pom.xml primeiro para baixar dependências usando cache
COPY pom.xml .
RUN mvn dependency:go-offline -B

# Copia o restante do projeto
COPY . .

# Build gerando o .jar
RUN mvn clean package -DskipTests

# ---- Etapa 2: Runtime ----
# Imagem limpa apenas com JDK 21
FROM eclipse-temurin:21-jdk

WORKDIR /app

# Copia o .jar gerado
COPY --from=builder /app/target/*.jar app.jar

# Porta padrão do Spring Boot
EXPOSE 8080

# Comando para rodar
ENTRYPOINT ["java", "-jar", "app.jar"]