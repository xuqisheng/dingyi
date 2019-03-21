#! /usr/bin/env bash

docker run -p 4000:4000 -d --name pig-upms-service --link pig-eureka:eureka --link redis:redishost --link rabbitmq:mqhost --link mysql:mysqlhost sherry/pig-upms-service --mysql.username=root --mysql.password=123456