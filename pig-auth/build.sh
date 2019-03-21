#!/usr/bin/env bash

#启动
docker run --nam=pig-auth -e SPRING_PROFILES_ACTIVE=docker-prod -d registry.cn-hangzhou.aliyuncs.com/zhidianfan/pig-auth:0.0.1-Alpha
