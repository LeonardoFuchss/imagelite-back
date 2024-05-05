
# Definições do contâiner, buildando a imagem
FROM maven:3.9.6-amazoncorretto-21 as builder
WORKDIR /app
# Copiando tudo que tiver junto do arquivo do Dockerfile (Minha aplicação) e passando pra pasta app criada
COPY . .
# Baixando as dependencias maven dentro do container
RUN mvn dependency:resolve
# Empacotando minha aplicação
RUN mvn clean package -DskipTests

# Camada de inicialização (RUN) da aplicação
FROM maven:3.9.6-amazoncorretto-21
WORKDIR /app2
# Copiando a aplicação empacotada do builder criada para -> application.jar
COPY --from=builder ./app/target/*.jar ./application.jar
EXPOSE 8080

ENV POSTGRES_HOST=localhost
# Configurando para a aplicação rodar em ambiente de produção e inicializando a aplicação empacotada
ENTRYPOINT ["java", "-jar", "-Dspring.profiles.active=production", "application.jar"]