#! /usr/bin/env bash

docker run -d --name pig-monitor --link pig-eureka:eureka --link redis:redishost --link rabbitmq:mqhost sherry/pig-monitor