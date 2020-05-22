/*
Navicat MySQL Data Transfer

Source Server         : localhost
Source Server Version : 50716
Source Host           : localhost:3306
Source Database       : how2java

Target Server Type    : MYSQL
Target Server Version : 50716
File Encoding         : 65001

Date: 2020-05-22 16:28:37
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for category
-- ----------------------------
DROP TABLE IF EXISTS `category`;
CREATE TABLE `category` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(200) DEFAULT NULL,
  `description` varchar(200) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=23 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of category
-- ----------------------------
INSERT INTO `category` VALUES ('1', 'demoData', 'demoData');
INSERT INTO `category` VALUES ('2', '元杰', 'wrrr');
INSERT INTO `category` VALUES ('3', '百年', '');
INSERT INTO `category` VALUES ('4', '一归来', '');
INSERT INTO `category` VALUES ('6', '平', '');
INSERT INTO `category` VALUES ('7', '一归', '');
INSERT INTO `category` VALUES ('12', '21524', '有任何');
INSERT INTO `category` VALUES ('13', '21524', '人');
INSERT INTO `category` VALUES ('14', '453', '方式');
INSERT INTO `category` VALUES ('15', '123', null);
INSERT INTO `category` VALUES ('16', '4245', '1');
INSERT INTO `category` VALUES ('17', '222222', null);
INSERT INTO `category` VALUES ('18', '222222', null);
INSERT INTO `category` VALUES ('19', 'asa', '哈哈哈');
INSERT INTO `category` VALUES ('22', 'erter', '啊哈哈哈');
