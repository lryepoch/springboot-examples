/*
Navicat MySQL Data Transfer

Source Server         : localhost
Source Server Version : 50716
Source Host           : localhost:3306
Source Database       : mall-sc

Target Server Type    : MYSQL
Target Server Version : 50716
File Encoding         : 65001

Date: 2021-04-12 16:55:36
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for mall_order
-- ----------------------------
DROP TABLE IF EXISTS `mall_order`;
CREATE TABLE `mall_order` (
  `oid` int(50) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `uid` int(20) NOT NULL COMMENT '用户id',
  `username` varchar(10) CHARACTER SET utf8mb4 NOT NULL COMMENT '用户名称',
  `pid` int(20) NOT NULL COMMENT '商品id',
  `pprice` decimal(18,4) NOT NULL COMMENT '商品单价',
  `number` int(3) DEFAULT NULL COMMENT '购买数量',
  `pname` varchar(20) CHARACTER SET utf8mb4 DEFAULT NULL COMMENT '商品名称',
  PRIMARY KEY (`oid`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of mall_order
-- ----------------------------
INSERT INTO `mall_order` VALUES ('1', '1', '测试用户', '1', '1000.0000', '1', '小米');
INSERT INTO `mall_order` VALUES ('2', '1', '测试用户', '1', '1000.0000', '1', '小米');
INSERT INTO `mall_order` VALUES ('3', '1', '测试用户', '1', '1000.0000', '1', '小米');
INSERT INTO `mall_order` VALUES ('4', '1', '测试用户', '1', '1000.0000', '1', '小米');
INSERT INTO `mall_order` VALUES ('5', '1', '测试用户', '1', '1000.0000', '1', '小米');
INSERT INTO `mall_order` VALUES ('6', '1', '测试用户', '1', '1000.0000', '1', '小米');
INSERT INTO `mall_order` VALUES ('7', '1', '测试用户', '1', '1000.0000', '1', '小米');

-- ----------------------------
-- Table structure for mall_product
-- ----------------------------
DROP TABLE IF EXISTS `mall_product`;
CREATE TABLE `mall_product` (
  `pid` int(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `pname` varchar(20) CHARACTER SET utf8mb4 NOT NULL DEFAULT '' COMMENT '商品名称',
  `pprice` decimal(18,4) DEFAULT NULL COMMENT '商品价格',
  `stock` int(20) DEFAULT NULL COMMENT '库存',
  PRIMARY KEY (`pid`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of mall_product
-- ----------------------------
INSERT INTO `mall_product` VALUES ('1', '小米', '1000.0000', '5000');
INSERT INTO `mall_product` VALUES ('2', '华为', '2000.0000', '5000');
INSERT INTO `mall_product` VALUES ('3', '苹果', '3000.0000', '5000');
INSERT INTO `mall_product` VALUES ('4', 'OPPO', '4000.0000', '5000');

-- ----------------------------
-- Table structure for mall_user
-- ----------------------------
DROP TABLE IF EXISTS `mall_user`;
CREATE TABLE `mall_user` (
  `uid` int(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `username` varchar(10) CHARACTER SET utf8mb4 NOT NULL COMMENT '用户名',
  `password` varchar(10) NOT NULL COMMENT '密码',
  `telephone` varchar(11) CHARACTER SET utf8mb4 NOT NULL COMMENT '手机号',
  PRIMARY KEY (`uid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of mall_user
-- ----------------------------
