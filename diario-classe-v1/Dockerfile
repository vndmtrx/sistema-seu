######################### Estágio 1 #########################
# Use uma imagem base do JDK 22 para construir a aplicação
FROM eclipse-temurin:22-jdk AS build

# Defina o diretório de trabalho dentro do contêiner
WORKDIR /app

# Copie o arquivo pom.xml e outros arquivos de configuração necessários
COPY pom.xml .
COPY mvnw .
COPY .mvn .mvn

# Copie o código fonte da aplicação
COPY src ./src

# Execute o Maven para empacotar a aplicação
RUN ./mvnw clean package -DskipTests

######################### Estágio 2 #########################
# Use uma imagem base mais leve para o runtime
FROM eclipse-temurin:22-jre

# Defina o diretório de trabalho dentro do contêiner
WORKDIR /app

# Crie o diretório para o banco de dados
RUN mkdir -p /app/data

# Copie o JAR da aplicação do estágio de construção
COPY --from=build /app/target/app-deployment.jar /app/app.jar

# Exponha a porta que a aplicação vai rodar
EXPOSE 9001

# Defina o perfil ativo como produção
ENV SPRING_PROFILES_ACTIVE=prod

# Defina o comando padrão para executar a aplicação
ENTRYPOINT ["java", "-jar", "/app/app.jar"]
