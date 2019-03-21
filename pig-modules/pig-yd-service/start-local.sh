#! /usr/bin/env bash

docker run -d --name pig-yd-service --link pig-eureka:eureka --link redis:redishost --link rabbitmq:mqhost --link mysql:mysqlhost sherry/pig-yd-service --mysql.username=root --mysql.password=123456