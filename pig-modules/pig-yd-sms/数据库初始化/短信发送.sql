/*
 Navicat Premium Data Transfer

 Source Server         : localhost_3306
 Source Server Type    : MySQL
 Source Server Version : 50723
 Source Host           : localhost:3306
 Source Schema         : yiding_sms

 Target Server Type    : MySQL
 Target Server Version : 50723
 File Encoding         : 65001

 Date: 06/11/2018 16:04:56
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for base_sms_log
-- ----------------------------
DROP TABLE IF EXISTS `base_sms_log`;
CREATE TABLE `base_sms_log`  (
  `id` bigint(20) NOT NULL,
  `phone` text CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL COMMENT '可以是批量短信，多个手机号使用英文分隔符分割',
  `req_time` datetime(0) NULL DEFAULT NULL,
  `res_time` datetime(0) NULL DEFAULT NULL,
  `sms_content` varchar(512) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '短信内容',
  `send_res` varchar(512) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '短信发送，第三方接口返回信息',
  `operator` int(1) NULL DEFAULT 1 COMMENT '1 创蓝 2乐信',
  `status` int(11) NULL DEFAULT NULL COMMENT '短信发送状态\n	0-准备发送\n	1-已发送，未确认\n	2-发送后被确认成功\n	3-发送后被确认失败\n	4-发送失败',
  `note` varchar(256) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '备注',
  `msgid` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '短信id',
  `sucnum` int(11) NULL DEFAULT 0 COMMENT '此次成功数量',
  `failnum` int(11) NULL DEFAULT 0 COMMENT '此次失败数量',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_bin COMMENT = '短信发送记录（与业务无关）' ROW_FORMAT = Dynamic;


-- ----------------------------
-- Table structure for config_sms
-- ----------------------------
DROP TABLE IF EXISTS `config_sms`;
CREATE TABLE `config_sms`  (
  `id` bigint(11) NOT NULL AUTO_INCREMENT,
  `operator_name` varchar(60) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL COMMENT '运营商名称',
  `bean_name` varchar(60) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '对应通道接口的实现类在Spring中的注册名',
  `default_operator` int(1) NULL DEFAULT NULL COMMENT '是否为默认通道 0-否,1-是',
  `threshold` varchar(3) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '阈值 百分制 当前通道处于正常状态时，其短信到达率的最低值',
  `status` int(1) NULL DEFAULT 1 COMMENT '人工设置通道是否可用 0-不可用 1 - 可用',
  `start_time` datetime(0) NULL DEFAULT NULL COMMENT '人工干预开始时间',
  `end_time` datetime(0) NULL DEFAULT NULL COMMENT '人工干预结束时间',
  `cal_period` int(3) NULL DEFAULT 1 COMMENT '计算周期,按天技术,N天内发送情况',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `bean_name`(`bean_name`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 3 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_bin ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of config_sms
-- ----------------------------
INSERT INTO `config_sms` VALUES (1, '创蓝', 'chuangLSmsServiceImpl', 1, '90', 0, '2018-11-05 14:00:00', '2018-11-06 18:00:00', 1);
INSERT INTO `config_sms` VALUES (2, '乐信通', 'leXtSmsServiceImpl', 0, '90', 1, '2018-11-05 14:00:00', '2018-11-06 18:00:00', 1);



-- ----------------------------
-- Table structure for sms_reply
-- ----------------------------
DROP TABLE IF EXISTS `sms_reply`;
CREATE TABLE `sms_reply`  (
  `id` bigint(11) NOT NULL AUTO_INCREMENT,
  `business_id` int(11) NULL DEFAULT NULL,
  `phone` varchar(11) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL,
  `msgid` varchar(8) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL,
  `reply_content` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL,
  `created_at` datetime(0) NULL DEFAULT NULL,
  `updated_at` datetime(0) NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_bin ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for task_sms_result
-- ----------------------------
DROP TABLE IF EXISTS `task_sms_result`;
CREATE TABLE `task_sms_result`  (
  `id` bigint(11) NOT NULL AUTO_INCREMENT,
  `operator_name` varchar(60) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL COMMENT '运营商名称',
  `bean_name` varchar(60) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '对应通道接口的实现类在Spring中的注册名',
  `operator_status` int(1) NULL DEFAULT 0 COMMENT '当前是否使用此通道 0-不启用 1-启用',
  `update_time` datetime(0) NULL DEFAULT NULL COMMENT '最近更新时间',
  `remark` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '变更理由',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `bean_name`(`bean_name`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 3 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_bin ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of task_sms_result
-- ----------------------------
INSERT INTO `task_sms_result` VALUES (1, '创蓝', 'chuangLSmsServiceImpl', 0, '2018-11-06 15:14:15', '计算选择最优通道');
INSERT INTO `task_sms_result` VALUES (2, '乐信通', 'leXtSmsServiceImpl', 1, '2018-11-06 15:58:31', '计算选择最优通道');

SET FOREIGN_KEY_CHECKS = 1;
