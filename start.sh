#!/bin/bash
#Docker down
docker-compose -f docker-compose.yml down

#Maven build
mvn clean package -Dmaven.test.skip

#Docker build
docker build ./landscape/discovery-service -t hayat_discovery_service:latest
docker build ./landscape/config-service -t hayat_config_service:latest
docker build ./landscape/gateway -t hayat_gateway_service:latest

#Docker up
docker-compose -f docker-compose.yml up -d
