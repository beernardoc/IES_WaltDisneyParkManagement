#!/bin/bash

# Parar e remover containers do Docker Compose
docker-compose down -v #

# Remover as imagens
docker rmi ies_waltdisneyparkmanagement_management
docker rmi ies_waltdisneyparkmanagement_data

# Limpar o projeto Maven e construir o pacote (skipTests para pular os testes)
mvn clean
mvn package -DskipTests

# Subir o Docker Compose
docker-compose up --build -d # Use a opção -d para executar em segundo plano, se preferir
