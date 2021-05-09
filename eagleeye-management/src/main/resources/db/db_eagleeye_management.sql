/*
 Navicat Premium Data Transfer

 Source Server         : 47.101.42.169
 Source Server Type    : MySQL
 Source Server Version : 50731
 Source Host           : 47.101.42.169:3306
 Source Schema         : db_eagleeye_management

 Target Server Type    : MySQL
 Target Server Version : 50731
 File Encoding         : 65001

 Date: 09/05/2021 12:21:20
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for app_config_info
-- ----------------------------
DROP TABLE IF EXISTS `app_config_info`;
CREATE TABLE `app_config_info`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '表主键ID',
  `app_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT 'APP名称',
  `app_id` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT 'APP ID',
  `callback_url` varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '回调通知地址',
  `delete_tag` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT 'N' COMMENT '删除标记：\'Y\' / \'N\'',
  `create_time` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '创建时间',
  `update_time` datetime(0) NULL DEFAULT CURRENT_TIMESTAMP(0) ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 2011231 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of app_config_info
-- ----------------------------
INSERT INTO `app_config_info` VALUES (2011229, 'halo', 'something', 'anything', 'N', '2021-03-12 14:37:08', '2021-03-12 14:37:08');
INSERT INTO `app_config_info` VALUES (2011230, 'webv', '1202', 'hewvbj', 'Y', '2021-03-16 10:07:57', '2021-03-16 10:07:57');

-- ----------------------------
-- Table structure for atm_info
-- ----------------------------
DROP TABLE IF EXISTS `atm_info`;
CREATE TABLE `atm_info`  (
  `id` bigint(20) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `atm_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT 'ATM名称',
  `atm_code` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT 'ATM编号',
  `atm_desc` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT 'ATM描述',
  `ip` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT 'IP地址',
  `status` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT 'ATM状态',
  `delete_tag` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT 'N' COMMENT '删除标记：\'Y\' / \'N\'',
  `create_time` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '创建时间',
  `update_time` datetime(0) NULL DEFAULT CURRENT_TIMESTAMP(0) ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 20200316 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_bin ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of atm_info
-- ----------------------------
INSERT INTO `atm_info` VALUES (20200312, NULL, NULL, NULL, NULL, NULL, 'Y', '2021-03-16 10:08:23', '2021-03-16 10:08:23');
INSERT INTO `atm_info` VALUES (20200313, 'webv', '1202', 'hewvbj', '10.25.3.125', 'ok', 'N', '2021-03-16 10:11:33', '2021-03-16 10:11:33');
INSERT INTO `atm_info` VALUES (20200314, NULL, '1202', 'hewvbj', '10.25.3.125', 'ok', 'Y', '2021-03-16 10:14:16', '2021-03-16 10:14:16');
INSERT INTO `atm_info` VALUES (20200315, 'wev', '1202', 'hewvbj', '10.25.3.125', 'ok', 'Y', '2021-03-16 10:14:59', '2021-03-16 10:14:59');

SET FOREIGN_KEY_CHECKS = 1;
