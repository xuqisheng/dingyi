-- ----------------------------
DROP TABLE IF EXISTS `xms_business`;
CREATE TABLE `xms_business`  (
  `id` int(11) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '自动编号',
  `business_id` int(11) NOT NULL COMMENT '酒店id',
  `brand_hotel_id` varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL COMMENT '集团酒店id',
  `business_hotel_id` varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '业务酒店id',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 2 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_bin ROW_FORMAT = Compact;

-- ----------------------------
-- Records of xms_business
-- ----------------------------
INSERT INTO `xms_business` VALUES (1, 727, 'G000001', 'H000013');