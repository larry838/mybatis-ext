/*
 Navicat Premium Data Transfer

 Source Server         : mysql-admin
 Source Server Type    : MySQL
 Source Server Version : 50621
 Source Host           : localhost
 Source Database       : mybatis-extends

 Target Server Type    : MySQL
 Target Server Version : 50621
 File Encoding         : utf-8

 Date: 09/09/2016 16:38:46 PM
*/

SET NAMES utf8;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
--  Table structure for `role`
-- ----------------------------
DROP TABLE IF EXISTS `role`;
CREATE TABLE `role` (
  `id` bigint(20) NOT NULL,
  `name` varchar(64) DEFAULT NULL,
  `sort` int(11) DEFAULT NULL,
  `description` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
--  Table structure for `user`
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user` (
  `test_id` bigint(20) NOT NULL COMMENT '主键ID',
  `name` varchar(30) COLLATE utf8_bin DEFAULT NULL COMMENT '名称',
  `age` int(11) DEFAULT NULL COMMENT '年龄',
  `test_type` int(11) DEFAULT '0' COMMENT '测试下划线字段命名类型',
  `role_id` bigint(20) DEFAULT NULL COMMENT '角色',
  `phone` varchar(64) COLLATE utf8_bin DEFAULT NULL COMMENT '电话号码',
  `desc` varchar(255) COLLATE utf8_bin DEFAULT NULL COMMENT '描述',
  `birthday` date DEFAULT NULL COMMENT '出生日期',
  PRIMARY KEY (`test_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='用户表';

insert  into `user`(`test_id`,`name`,`age`,`test_type`,`role_id`,`phone`,`desc`,`birthday`) values (1,'xiejian',32,0,1,'13735561307',' ','2016-10-31'),(2,'larry',32,1,2,'13777858464',' ','2016-1-30');

-- ----------------------------
-- Table structure for test
-- ----------------------------
DROP TABLE IF EXISTS `test`;
CREATE TABLE `test` (
  `id` bigint(20) NOT NULL,
  `type` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Records of test
-- ----------------------------
INSERT INTO `test` VALUES ('1', '11');
INSERT INTO `test` VALUES ('2', '22');

SET FOREIGN_KEY_CHECKS = 1;