#! /usr/bin/env bash

docker run -d --name pig-yd-monitor --link pig-eureka:eureka --link redis:redishost --link rabbitmq:mqhost --link mysql:mysqlhost sherry/pig-yd-monitor --mysql.username=root --mysql.password=123456