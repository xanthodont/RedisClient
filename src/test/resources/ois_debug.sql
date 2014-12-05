/*
Navicat MySQL Data Transfer

Source Server         : 172.21.12.109
Source Server Version : 50530
Source Host           : 172.21.12.109:3306
Source Database       : ois_debug

Target Server Type    : MYSQL
Target Server Version : 50530
File Encoding         : 65001

Date: 2014-12-05 19:07:38
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for ois_account
-- ----------------------------
DROP TABLE IF EXISTS `ois_account`;
CREATE TABLE `ois_account` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `account` bigint(8) NOT NULL,
  `sn` varchar(255) DEFAULT NULL,
  `phoneType` varchar(255) DEFAULT NULL,
  `mobileVersion` varchar(255) DEFAULT NULL,
  `internalVersion` varchar(255) DEFAULT NULL,
  `longitude` double DEFAULT NULL,
  `latitude` double DEFAULT NULL,
  `createTime` bigint(8) DEFAULT NULL,
  `updateTime` bigint(8) DEFAULT NULL,
  `address` varchar(255) DEFAULT NULL,
  `country` varchar(255) DEFAULT '',
  `geoStatus` int(1) DEFAULT '0',
  `field1` varchar(255) DEFAULT NULL,
  `field2` varchar(255) DEFAULT NULL,
  `field3` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `account` (`account`)
) ENGINE=MyISAM AUTO_INCREMENT=11633021 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for ois_app
-- ----------------------------
DROP TABLE IF EXISTS `ois_app`;
CREATE TABLE `ois_app` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `systemVersion` varchar(100) DEFAULT NULL,
  `appVersion` varchar(100) NOT NULL,
  `appName` varchar(100) NOT NULL,
  `packageName` varchar(100) NOT NULL,
  `appSize` bigint(11) NOT NULL,
  `description` varchar(500) DEFAULT NULL,
  `md5` varchar(100) DEFAULT NULL,
  `updateTime` bigint(20) DEFAULT NULL,
  `downloadUri` varchar(500) DEFAULT NULL,
  `logoUri` varchar(500) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM AUTO_INCREMENT=73 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for ois_apps_info
-- ----------------------------
DROP TABLE IF EXISTS `ois_apps_info`;
CREATE TABLE `ois_apps_info` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `uploadTime` bigint(20) DEFAULT NULL,
  `appName` varchar(100) DEFAULT NULL,
  `packageName` varchar(250) DEFAULT NULL,
  `times` int(11) DEFAULT '0',
  `install` int(11) DEFAULT '0',
  `uninstall` int(11) DEFAULT '0',
  `phoneType` char(100) DEFAULT NULL,
  `country` char(100) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4556 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for ois_memory
-- ----------------------------
DROP TABLE IF EXISTS `ois_memory`;
CREATE TABLE `ois_memory` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `imei` bigint(8) NOT NULL,
  `type` char(10) NOT NULL,
  `size` double NOT NULL,
  `time` bigint(8) NOT NULL,
  `count` int(11) NOT NULL,
  `min` double(8,0) NOT NULL,
  `max` double(8,0) NOT NULL,
  `phoneType` varchar(64) NOT NULL,
  `country` varchar(64) DEFAULT NULL,
  `field1` varchar(128) DEFAULT NULL,
  `field2` varchar(128) DEFAULT NULL,
  `field3` varchar(128) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM AUTO_INCREMENT=224 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for ois_report
-- ----------------------------
DROP TABLE IF EXISTS `ois_report`;
CREATE TABLE `ois_report` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `reportName` varchar(100) NOT NULL,
  `titleText` varchar(100) NOT NULL,
  `subTitle` varchar(100) NOT NULL,
  `yaxisTitle` varchar(100) DEFAULT NULL,
  `sqlText` varchar(500) NOT NULL,
  `dataType` varchar(50) NOT NULL,
  `reportType` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM AUTO_INCREMENT=31 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for ois_shutdown
-- ----------------------------
DROP TABLE IF EXISTS `ois_shutdown`;
CREATE TABLE `ois_shutdown` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `phoneType` varchar(64) NOT NULL,
  `mobileVersion` varchar(255) NOT NULL,
  `country` varchar(255) DEFAULT NULL,
  `time` bigint(20) NOT NULL,
  `createTime` bigint(20) NOT NULL,
  `field1` varchar(255) DEFAULT NULL,
  `field2` varchar(255) DEFAULT NULL,
  `field3` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM AUTO_INCREMENT=11319 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for ois_switch
-- ----------------------------
DROP TABLE IF EXISTS `ois_switch`;
CREATE TABLE `ois_switch` (
  `id` bigint(11) NOT NULL AUTO_INCREMENT,
  `phoneType` varchar(64) NOT NULL,
  `country` varchar(64) DEFAULT '',
  `type` varchar(64) NOT NULL DEFAULT '',
  `value` int(11) NOT NULL,
  `field1` varchar(256) DEFAULT NULL,
  `field2` varchar(256) DEFAULT NULL,
  `field3` varchar(256) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM AUTO_INCREMENT=461 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for ois_system_update
-- ----------------------------
DROP TABLE IF EXISTS `ois_system_update`;
CREATE TABLE `ois_system_update` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `account` bigint(20) DEFAULT NULL,
  `currentVersion` varchar(255) DEFAULT NULL,
  `innerVersion` varchar(255) DEFAULT NULL,
  `phoneType` varchar(255) DEFAULT NULL,
  `country` varchar(255) DEFAULT NULL,
  `updateTime` bigint(20) DEFAULT NULL,
  `field1` varchar(255) DEFAULT NULL,
  `field2` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM AUTO_INCREMENT=7096 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Procedure structure for pro_insert_account
-- ----------------------------
DROP PROCEDURE IF EXISTS `pro_insert_account`;
DELIMITER ;;
CREATE DEFINER=`root`@`%` PROCEDURE `pro_insert_account`(IN imei BIGINT, IN sn VARCHAR(255),IN pType VARCHAR(255), IN mbVersion VARCHAR(255), IN inVersion VARCHAR(255), IN crTime BIGINT, IN upTime BIGINT, IN lng DOUBLE, IN lat DOUBLE)
BEGIN
	DECLARE cnt INT;
	SELECT COUNT(id) INTO cnt FROM `ois_account` oai WHERE oai.account = imei;
	SELECT cnt;
	IF cnt > 0
			THEN UPDATE `ois_account` oai SET oai.sn = sn, oai.phoneType = pType, oai.mobileVersion=mbVersion, oai.internalVersion=inVersion, oai.updateTime=upTime, oai.longitude=lng, oai.latitude=lat WHERE oai.account = imei;
	ELSE
			INSERT INTO `ois_account` (`account`, `sn`, `phoneType`, `mobileVersion`, `internalVersion`, `createTime`, `updateTime`, `longitude`, `latitude` ) VALUES (imei, sn, pType, mbVersion, inVersion, crTime, upTime, lng, lat); 
	END IF;
END
;;
DELIMITER ;

-- ----------------------------
-- Procedure structure for pro_insert_apps
-- ----------------------------
DROP PROCEDURE IF EXISTS `pro_insert_apps`;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `pro_insert_apps`(IN imei BIGINT, IN uploadTime BIGINT,IN packageName VARCHAR(250), IN appName VARCHAR(100), IN times INT)
BEGIN
	DECLARE pt VARCHAR(100);
	DECLARE ct VARCHAR(250);
	DECLARE cnt INT;
	SELECT phoneType , country INTO pt, ct FROM ois_account WHERE account = imei LIMIT 0, 1;
	SELECT pt, ct;
	IF pt is NOT NULL THEN
			SELECT COUNT(id) INTO cnt FROM ois_apps_info oai WHERE oai.`phoneType` = pt AND oai.`country` = ct AND oai.`packageName` = packageName;
			SELECT cnt;
			IF cnt > 0
				THEN UPDATE `ois_apps_info` oai SET oai.times = oai.times + times WHERE phoneType = pt AND country = ct AND oai.`packageName` = packageName;
			ELSE
				INSERT INTO `ois_apps_info` (`uploadTime`,`packageName`, `appName`, `times`, `phoneType`, `country`) VALUES(uploadTime, packageName, appName, times, pt, ct); 
			END IF;
	END IF;
END
;;
DELIMITER ;

-- ----------------------------
-- Procedure structure for pro_insert_memory
-- ----------------------------
DROP PROCEDURE IF EXISTS `pro_insert_memory`;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `pro_insert_memory`(IN imei BIGINT, IN type CHAR(10),IN size DOUBLE, IN time LONG, IN count INT, IN min DOUBLE, IN max DOUBLE)
BEGIN
	DECLARE pt VARCHAR(100);
	DECLARE ct VARCHAR(250);
	SELECT phoneType , country INTO pt, ct FROM ois_account WHERE account = imei LIMIT 0, 1;
	SELECT pt, ct;
	IF pt is NOT NULL THEN
			INSERT INTO `ois_memory` (`imei`, `type`, `size`, `time`, `count`, `min`, `max`, `phoneType`, `country`) VALUES (imei, type, size, time, count, min, max, pt, ct);
	END IF;
END
;;
DELIMITER ;

-- ----------------------------
-- Procedure structure for pro_insert_shutdown
-- ----------------------------
DROP PROCEDURE IF EXISTS `pro_insert_shutdown`;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `pro_insert_shutdown`(IN imei BIGINT, IN sdTime BIGINT, IN crTime BIGINT)
BEGIN
	DECLARE pt VARCHAR(100);
	DECLARE ct VARCHAR(250);
	DECLARE mv VARCHAR(255);
	SELECT phoneType , country, mobileVersion INTO pt, ct, mv FROM ois_account WHERE account = imei LIMIT 0, 1;
	SELECT pt, ct;
	IF pt is NOT NULL THEN
			INSERT INTO `ois_shutdown` (`phoneType`, `mobileVersion`, `country`, `time`, `createTime`) VALUES (pt, mv, ct, sdTime, crTime);
	END IF;
END
;;
DELIMITER ;

-- ----------------------------
-- Procedure structure for pro_insert_update
-- ----------------------------
DROP PROCEDURE IF EXISTS `pro_insert_update`;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `pro_insert_update`(IN imei BIGINT, IN cv VARCHAR(255), IN iv VARCHAR(255), IN time BIGINT)
BEGIN
	DECLARE pt VARCHAR(100);
	DECLARE ct VARCHAR(250);
	SELECT phoneType , country INTO pt, ct FROM ois_account WHERE account = imei LIMIT 0, 1;
	SELECT pt, ct;
	IF pt is NOT NULL THEN
			INSERT INTO `ois_system_update` (`account`, `currentVersion`, `innerVersion`, `phoneType`, `country`, `updateTime`) VALUES (imei, cv, iv, pt, ct, time);
	END IF;
END
;;
DELIMITER ;

-- ----------------------------
-- Procedure structure for pro_set_switch
-- ----------------------------
DROP PROCEDURE IF EXISTS `pro_set_switch`;
DELIMITER ;;
CREATE DEFINER=`root`@`%` PROCEDURE `pro_set_switch`(IN pt VARCHAR(100), IN vl INT)
BEGIN
	DECLARE cnt INT;
	IF pt is NOT NULL THEN
			SELECT COUNT(id) INTO cnt FROM ois_switch WHERE `phoneType` = pt;
			SELECT cnt;
			IF cnt > 0
				THEN UPDATE `ois_switch` os SET os.`value`  = vl WHERE phoneType = pt;
			ELSE
				INSERT INTO `ois_switch` (`phoneType`, `value`) VALUES(pt, vl); 
			END IF;
	END IF;
END
;;
DELIMITER ;

-- ----------------------------
-- Procedure structure for pro_update_location
-- ----------------------------
DROP PROCEDURE IF EXISTS `pro_update_location`;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `pro_update_location`(IN imei BIGINT, IN lng DOUBLE, IN lat DOUBLE, IN adr VARCHAR(255), IN cty VARCHAR(255), IN upt BIGINT)
BEGIN
	IF cty IS NOT NULL AND cty != '' THEN
		UPDATE `ois_account` SET `longitude`=lng, `latitude`=lat, `updateTime`=upt, `address`=adr, `country`=cty, `geoStatus`=1 WHERE `account`=imei;
	ELSE
		UPDATE `ois_account` SET `longitude`=lng, `latitude`=lat, `updateTime`=upt, `geoStatus`=0 WHERE `account`=imei;
	END IF;
END
;;
DELIMITER ;
