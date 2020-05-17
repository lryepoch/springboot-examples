/*
Navicat MySQL Data Transfer

Source Server         : db
Source Server Version : 50562
Source Host           : localhost:3306
Source Database       : how2java

Target Server Type    : MYSQL
Target Server Version : 50562
File Encoding         : 65001

Date: 2019-05-18 12:03:40
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for category_
-- ----------------------------
DROP TABLE IF EXISTS `category_`;
CREATE TABLE `category_` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(30) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=19 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of category_
-- ----------------------------
INSERT INTO `category_` VALUES ('1', 'category 1');
INSERT INTO `category_` VALUES ('2', 'category 2');
INSERT INTO `category_` VALUES ('3', 'category3');
INSERT INTO `category_` VALUES ('4', 'category4');
INSERT INTO `category_` VALUES ('9', '55');
INSERT INTO `category_` VALUES ('10', '100');
INSERT INTO `category_` VALUES ('11', '2334');
INSERT INTO `category_` VALUES ('12', '234');
INSERT INTO `category_` VALUES ('13', '78');
INSERT INTO `category_` VALUES ('14', '89');
INSERT INTO `category_` VALUES ('15', '12345');
INSERT INTO `category_` VALUES ('17', '2');
INSERT INTO `category_` VALUES ('18', '100');
