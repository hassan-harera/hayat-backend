#!/bin/bash
#Docker down
docker-compose -f docker-compose.yml down

#Maven build
mvn clean package -Dmaven.test.skip

#Docker up
docker-compose -f ./docker-compose.yml --env-file ./.env up --build -d
