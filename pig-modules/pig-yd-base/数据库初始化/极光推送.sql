/*
 Navicat Premium Data Transfer

 Source Server         : 本地数据库-root
 Source Server Type    : MySQL
 Source Server Version : 50719
 Source Host           : localhost:3306
 Source Schema         : resv_sys

 Target Server Type    : MySQL
 Target Server Version : 50719
 File Encoding         : 65001

 Date: 22/08/2018 15:01:39
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for base_push_log
-- ----------------------------
DROP TABLE IF EXISTS `base_push_log`;
CREATE TABLE `base_push_log` (
  `id` bigint(20) NOT NULL,
  `app_key` varchar(64) COLLATE utf8mb4_bin DEFAULT NULL,
  `master_secret` varchar(64) COLLATE utf8mb4_bin DEFAULT NULL,
  `reg_id` varchar(32) COLLATE utf8mb4_bin DEFAULT NULL,
  `type` varchar(16) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '推送设备\nWX-微信\nAPP-手机\nPAD-pad预订台',
  `username` varchar(16) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '用户名（手机号）',
  `push_msg` text COLLATE utf8mb4_bin COMMENT '消息内容',
  `push_time` datetime DEFAULT NULL COMMENT '推送时间',
  `push_status` tinyint(1) DEFAULT NULL COMMENT '推送状态\n0-未推送\n1-推送成功\n2-推送失败\n3-新数据覆盖，无需推送',
  `note` text COLLATE utf8mb4_bin COMMENT '备注，一般在推送失败后说明',
  `insert_time` datetime DEFAULT NULL COMMENT '数据入库时间',
  `msg_seq` varchar(32) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '消息顺序',
  `business_id` varchar(32) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '酒店id',
  `pushed_count` int(11) DEFAULT NULL COMMENT '已被推送次数',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin COMMENT='极光推送历史表';

-- ----------------------------
-- Table structure for base_push_log_his
-- ----------------------------
DROP TABLE IF EXISTS `base_push_log_his`;
CREATE TABLE `base_push_log_his` (
  `id` bigint(20) NOT NULL,
  `app_key` varchar(64) COLLATE utf8mb4_bin DEFAULT NULL,
  `master_secret` varchar(64) COLLATE utf8mb4_bin DEFAULT NULL,
  `reg_id` varchar(32) COLLATE utf8mb4_bin DEFAULT NULL,
  `type` varchar(16) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '推送设备\n	WX-微信\n	APP-手机\n	PAD-pad预订台',
  `username` varchar(16) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '用户名（手机号）',
  `push_msg` text COLLATE utf8mb4_bin COMMENT '消息内容',
  `push_time` datetime DEFAULT NULL COMMENT '推送时间',
  `push_status` tinyint(1) DEFAULT NULL COMMENT '推送状态\n	0-未推送\n	1-推送成功\n	2-推送失败\n	3-新数据覆盖，无需推送',
  `note` text COLLATE utf8mb4_bin COMMENT '备注，一般在推送失败后说明',
  `insert_time` datetime DEFAULT NULL COMMENT '数据入库时间',
  `msg_seq` varchar(32) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '消息顺序',
  `business_id` varchar(32) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '酒店id',
  `pushed_count` int(11) DEFAULT NULL COMMENT '已被推送次数',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin COMMENT='极光推送历史表';

-- ----------------------------
-- Table structure for base_push_regid
-- ----------------------------
DROP TABLE IF EXISTS `base_push_regid`;
CREATE TABLE `base_push_regid` (
  `id` bigint(20) NOT NULL COMMENT '使用雪花算法生成主键',
  `type` varchar(8) COLLATE utf8mb4_bin NOT NULL COMMENT '客户端类型：\nWX-微信\nAPP-手机\nPAD-pad预订台',
  `username` varchar(16) COLLATE utf8mb4_bin NOT NULL COMMENT '用户名，一般指手机号',
  `registration_id` varchar(64) COLLATE utf8mb4_bin NOT NULL COMMENT '注册id与设备绑定',
  `reg_time` datetime NOT NULL COMMENT '设备注册时间',
  `version` int(11) NOT NULL DEFAULT '1' COMMENT '乐观锁',
  `business_id` varchar(16) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '酒店id',
  `on_off` int(11) DEFAULT '1' COMMENT '推送开关\n1-打开\n0-关闭',
  PRIMARY KEY (`id`),
  UNIQUE KEY `base_push_regid_username_registration_id_uindex` (`username`,`registration_id`),
  KEY `base_push_regid_username_index` (`username`),
  KEY `base_push_regid_registration_id_index` (`registration_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin COMMENT='极光推送客户的regid';

SET FOREIGN_KEY_CHECKS = 1;
