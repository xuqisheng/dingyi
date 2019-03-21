#! /usr/bin/env bash

docker run -d --name pig-yd-user --link pig-eureka:eureka --link redis:redishost --link rabbitmq:mqhost --link mysql:mysqlhost sherry/pig-yd-user --mysql.username=root --mysql.password=123456