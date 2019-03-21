#!/usr/bin/env bash

# 本机
docker run --name -d mysql -v /Users/zhangliuning/Volumes/mysql/data:/var/lib/mysql -p 3306:3306 -e lower-case-table-names=1 -e MYSQL_ROOT_PASSWORD=123456 -e default-time-zone=+8:00 -e ollation-server=utf8_general_ci -e character-set-server=utf8 -e innodb-buffer-pool-size=20M registry.cn-hangzhou.aliyuncs.com/sherry/mysql:5.7
docker run --name -d redis -p 6379:6379 registry.cn-hangzhou.aliyuncs.com/sherry/redis:3.2.9
docker run --name -d rabbitmq -p 15672:15672 -p 5672:5672 -e RABBITMQ_DEFAULT_USER=pig -e RABBITMQ_DEFAULT_PASS=pig -v /Users/zhangliuning/Volumes/rabbitmq:/var/rabbitmq/lib registry.cn-hangzhou.aliyuncs.com/sherry/rabbitmq:3.7-management



# 测试
docker run --name mysql -d -p 3306:3306 -v /home/yiding/volumns/mysql:/var/lib/mysql -e lower-case-table-names=1 -e MYSQL_ROOT_PASSWORD=123456 -e default-time-zone=+8:00 -e ollation-server=utf8_general_ci -e character-set-server=utf8 -e innodb-buffer-pool-size=20M registry-internal.cn-hangzhou.aliyuncs.com/zhidianfan/mysql:5.7
docker run --name redis -d -p 6379:6379 registry-internal.cn-hangzhou.aliyuncs.com/zhidianfan/redis:3.2.9
docker run --name -d rabbitmq -p 15672:15672 -p 5672:5672 -e RABBITMQ_DEFAULT_USER=pig -e RABBITMQ_DEFAULT_PASS=pig -v /home/yiding/volumns/rabbitmq:/var/rabbitmq/lib registry-internal.cn-hangzhou.aliyuncs.com/zhidianfan/rabbitmq:3.7-management

docker run --name pig-eureka -d -p 1025:1025 registry-internal.cn-hangzhou.aliyuncs.com/zhidianfan/test-pig-eureka:v0.0.1

docker run --name pig-auth -d -p 3000:3000 --link pig-eureka:eureka --link redis:redishost --link rabbitmq:mqhost --link mysql:mysqlhost registry-internal.cn-hangzhou.aliyuncs.com/zhidianfan/test-pig-auth:v0.0.1
docker run --name pig-upms-service -d --link pig-eureka:eureka --link redis:redishost --link rabbitmq:mqhost --link mysql:mysqlhost registry-internal.cn-hangzhou.aliyuncs.com/zhidianfan/test-pig-upms-service:v0.0.1
docker run --name pig-gateway -d -p 9999:9999 --link pig-auth:authhost --link pig-eureka:eureka --link redis:redishost --link rabbitmq:mqhost --link mysql:mysqlhost registry-internal.cn-hangzhou.aliyuncs.com/zhidianfan/test-pig-gateway:v0.0.1
docker run --name pig-yd-log -d --link pig-eureka:eureka --link redis:redishost --link rabbitmq:mqhost --link mysql:mysqlhost registry-internal.cn-hangzhou.aliyuncs.com/zhidianfan/test-pig-yd-log:v0.0.1
docker run --name pig-yd-base -d --link pig-eureka:eureka --link redis:redishost --link rabbitmq:mqhost --link mysql:mysqlhost registry-internal.cn-hangzhou.aliyuncs.com/zhidianfan/test-pig-yd-base:v0.0.1
docker run --name pig-yd-sms -d --link pig-eureka:eureka --link redis:redishost --link rabbitmq:mqhost --link mysql:mysqlhost registry-internal.cn-hangzhou.aliyuncs.com/zhidianfan/test-pig-yd-sms:v0.0.1
docker run --name pig-yd-service -d --link pig-eureka:eureka --link redis:redishost --link rabbitmq:mqhost --link mysql:mysqlhost registry-internal.cn-hangzhou.aliyuncs.com/zhidianfan/test-pig-yd-service:v0.0.1


# 迁移
docker run --name mysql -d -p 3306:3306 -v /home/yiding/volumns/mysql/data:/var/lib/mysql -e lower-case-table-names=1 -e MYSQL_ROOT_PASSWORD=123456 -e default-time-zone=+8:00 -e ollation-server=utf8_general_ci -e character-set-server=utf8 -e innodb-buffer-pool-size=20M registry.cn-hangzhou.aliyuncs.com/sherry/mysql:5.7
docker run --name redis -d -p 6379:6379 registry-internal.cn-hangzhou.aliyuncs.com/zhidianfan/redis:3.2.9
docker run --name rabbitmq -d -p 15672:15672 -p 5672:5672 -e RABBITMQ_DEFAULT_USER=pig -e RABBITMQ_DEFAULT_PASS=pig -v /home/yiding/volumns/rabbitmq/data:/var/rabbitmq/lib registry-internal.cn-hangzhou.aliyuncs.com/zhidianfan/rabbitmq:3.7-management

docker run --name pig-eureka -d -p 1025:1025 registry-internal.cn-hangzhou.aliyuncs.com/zhidianfan/qianyi-pig-eureka:v0.0.1

docker run --name pig-auth -d -p 3000:3000 --link pig-eureka:eureka --link redis:redishost --link rabbitmq:mqhost --link mysql:mysqlhost registry-internal.cn-hangzhou.aliyuncs.com/zhidianfan/qianyi-pig-auth:v0.0.1
docker run --name pig-upms-service -d --link pig-eureka:eureka --link redis:redishost --link rabbitmq:mqhost --link mysql:mysqlhost registry-internal.cn-hangzhou.aliyuncs.com/zhidianfan/qianyi-pig-upms-service:v0.0.1
docker run --name pig-gateway -d -p 9999:9999 --link pig-auth:authhost --link pig-eureka:eureka --link redis:redishost --link rabbitmq:mqhost --link mysql:mysqlhost registry-internal.cn-hangzhou.aliyuncs.com/zhidianfan/qianyi-pig-gateway:v0.0.1
docker run --name pig-yd-log -d --link pig-eureka:eureka --link redis:redishost --link rabbitmq:mqhost --link mysql:mysqlhost registry-internal.cn-hangzhou.aliyuncs.com/zhidianfan/qianyi-pig-yd-log:v0.0.1
docker run --name pig-yd-base -d --link pig-eureka:eureka --link redis:redishost --link rabbitmq:mqhost --link mysql:mysqlhost registry-internal.cn-hangzhou.aliyuncs.com/zhidianfan/qianyi-pig-yd-base:v0.0.1
docker run --name pig-yd-sms -d --link pig-eureka:eureka --link redis:redishost --link rabbitmq:mqhost --link mysql:mysqlhost registry-internal.cn-hangzhou.aliyuncs.com/zhidianfan/qianyi-pig-yd-sms:v0.0.1
docker run --name pig-yd-service -d --link pig-eureka:eureka --link redis:redishost --link rabbitmq:mqhost --link mysql:mysqlhost registry-internal.cn-hangzhou.aliyuncs.com/zhidianfan/qianyi-pig-yd-service:v0.0.1




# 生产
docker run --name pig-eureka -d -p 1025:1025 registry-internal.cn-hangzhou.aliyuncs.com/zhidianfan/prod-pig-eureka:v0.0.1
docker run --name pig-auth -d -p 3000:3000 registry-internal.cn-hangzhou.aliyuncs.com/zhidianfan/prod-pig-auth:v0.0.1
docker run --name pig-upms-service -d registry-internal.cn-hangzhou.aliyuncs.com/zhidianfan/prod-pig-upms-service:v0.0.1
docker run --name pig-gateway -d -p 9999:9999 registry-internal.cn-hangzhou.aliyuncs.com/zhidianfan/prod-pig-gateway:v0.0.1