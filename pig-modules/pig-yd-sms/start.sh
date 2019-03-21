#! /usr/bin/env bash

docker run -d --name pig-yd-sms --link pig-eureka:eureka --link redis:redishost --link rabbitmq:mqhost --link mysql:mysqlhost sherry/pig-yd-sms --mysql.smsname=root --mysql.password=123456