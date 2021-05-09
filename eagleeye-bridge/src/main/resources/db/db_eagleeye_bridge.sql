/*
 Navicat Premium Data Transfer

 Source Server         : 47.101.42.169
 Source Server Type    : MySQL
 Source Server Version : 50731
 Source Host           : 47.101.42.169:3306
 Source Schema         : db_eagleeye_bridge

 Target Server Type    : MySQL
 Target Server Version : 50731
 File Encoding         : 65001

 Date: 09/05/2021 12:25:35
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for record_detail
-- ----------------------------
DROP TABLE IF EXISTS `record_detail`;
CREATE TABLE `record_detail`  (
  `id` bigint(20) NOT NULL COMMENT '主键ID',
  `process_status` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT 'ACCEPTED' COMMENT '记录处理进度状态',
  `request_record_id` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '请求记录ID',
  `request_version` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '请求版本',
  `sid` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '事项编码',
  `calc_mode` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '调用模式',
  `project_id` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '项目ID',
  `routing_key` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '消息路由',
  `notice_times` int(11) NOT NULL DEFAULT 0 COMMENT '回调通知次数',
  `notice_status` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT 'INIT' COMMENT '回调通知状态（INIT/SUCCESS/PROCESSING/FAIL）',
  `create_time` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '创建时间',
  `update_time` datetime(0) NULL DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `record_detail_id_uindex`(`id`) USING BTREE,
  UNIQUE INDEX `record_detail_request_record_id_uindex`(`request_record_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '处理记录' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of record_detail
-- ----------------------------
INSERT INTO `record_detail` VALUES (1358718894977634305, 'fail', '01', 'string', 'string', 'string', 'string', 'superman', 0, 'INIT', '2021-02-08 18:06:43', '2021-02-08 18:06:44');
INSERT INTO `record_detail` VALUES (1358718979253784577, 'finished', '02', 'string', 'string', 'string', 'string', 'superman', 0, 'INIT', '2021-02-08 18:07:03', '2021-02-08 18:07:04');
INSERT INTO `record_detail` VALUES (1358720335494545410, 'fail', '03', 'string', 'string', 'string', 'string', 'superman', 0, 'INIT', '2021-02-08 18:12:26', '2021-02-08 18:12:27');

SET FOREIGN_KEY_CHECKS = 1;
