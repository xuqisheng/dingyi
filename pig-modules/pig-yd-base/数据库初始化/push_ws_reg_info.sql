
-- ws 相关表结构

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for push_ws_reg_info
-- ----------------------------
DROP TABLE IF EXISTS `push_ws_reg_info`;
CREATE TABLE `push_ws_reg_info` (
                                  `id` bigint(20) NOT NULL COMMENT '主键',
                                  `hotel_id` int(16) NOT NULL COMMENT '酒店id',
                                  `device_type` varchar(32) COLLATE utf8mb4_bin NOT NULL COMMENT '设备类型',
                                  `session_id` varchar(255) COLLATE utf8mb4_bin NOT NULL COMMENT '当前会话id',
                                  `reg_state` int(2) NOT NULL COMMENT '注册状态 0-断开 1-活动',
                                  `create_date` datetime NOT NULL COMMENT '创建时间',
                                  `update_date` datetime NOT NULL COMMENT '更新时间',
                                  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin COMMENT='Websockt 推送注册信息表';

SET FOREIGN_KEY_CHECKS = 1;