#! /usr/bin/env bash

docker run -d --name pig-yd-log --link pig-eureka:eureka --link redis:redishost --link rabbitmq:mqhost --link mysql:mysqlhost sherry/pig-yd-log --mysql.username=root --mysql.password=123456