# ---- Etapa 1: build da aplicacao com Maven ----
FROM maven:3.9.8-eclipse-temurin-17-alpine AS build
WORKDIR /opt/app
COPY . .
RUN mvn clean package -DskipTests

# ---- Etapa 2: imagem final, apenas com o JRE e o artefato gerado ----
FROM eclipse-temurin:17-alpine-3.23
WORKDIR /opt/app
COPY --from=build /opt/app/target/app.jar /opt/app/app.jar

EXPOSE 8080

# Profile padrao caso nenhum seja informado na execucao do container.
ENV SPRING_PROFILES_ACTIVE=default

CMD ["sh", "-c", "java -Dspring.profiles.active=${SPRING_PROFILES_ACTIVE} -jar app.jar"]
