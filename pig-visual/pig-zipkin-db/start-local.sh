#! /usr/bin/env bash

docker run -d --name pig-zipkin-db --link pig-eureka:eureka --link redis:redishost --link rabbitmq:mqhost --link mysql:mysqlhost sherry/pig-zipkin-db --mysql.username=root --mysql.password=123456