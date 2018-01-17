/*
SQLyog Ultimate v9.20 
MySQL - 5.5.32 : Database - mycom_db
*********************************************************************
*/


/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;
/*Table structure for table `base_mgr_core_login_log` */

DROP TABLE IF EXISTS `base_mgr_core_login_log`;

CREATE TABLE `base_mgr_core_login_log` (
  `LOGINID` int(11) NOT NULL AUTO_INCREMENT COMMENT '自增ID',
  `LOGINUSERID` int(11) DEFAULT NULL COMMENT '登录用户ID',
  `LOGINNAME` varchar(32) DEFAULT NULL COMMENT '登录用户',
  `IP` varchar(200) NOT NULL COMMENT '登录ip',
  `USERMOBILE` varchar(11) DEFAULT NULL COMMENT '号码',
  `ALIASNUM` varchar(100) DEFAULT NULL COMMENT '别名',
  `PROVCODE` int(11) NOT NULL DEFAULT '0' COMMENT '省编号',
  `AREACODE` int(11) NOT NULL DEFAULT '0' COMMENT '地区编号',
  `CARDTYPE` varchar(100) DEFAULT NULL COMMENT '卡类型',
  `LOGINTYPE` tinyint(4) DEFAULT NULL COMMENT '登录类型：0wap，1web',
  `USERTYPE` tinyint(4) DEFAULT NULL COMMENT '用户类型：0普通用户，1管理员用户',
  `LOGINTIME` datetime NOT NULL COMMENT '登录时间',
  PRIMARY KEY (`LOGINID`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8 COMMENT='登录日志表';

/*Data for the table `base_mgr_core_login_log` */

insert  into `base_mgr_core_login_log`(`LOGINID`,`LOGINUSERID`,`LOGINNAME`,`IP`,`USERMOBILE`,`ALIASNUM`,`PROVCODE`,`AREACODE`,`CARDTYPE`,`LOGINTYPE`,`USERTYPE`,`LOGINTIME`) values (1,1,'mycomadmin','127.0.0.1','13800000000',NULL,0,0,NULL,1,1,'2018-01-11 16:05:43');
insert  into `base_mgr_core_login_log`(`LOGINID`,`LOGINUSERID`,`LOGINNAME`,`IP`,`USERMOBILE`,`ALIASNUM`,`PROVCODE`,`AREACODE`,`CARDTYPE`,`LOGINTYPE`,`USERTYPE`,`LOGINTIME`) values (2,1,'mycomadmin','127.0.0.1','13800000000',NULL,0,0,NULL,1,1,'2018-01-11 17:07:12');
insert  into `base_mgr_core_login_log`(`LOGINID`,`LOGINUSERID`,`LOGINNAME`,`IP`,`USERMOBILE`,`ALIASNUM`,`PROVCODE`,`AREACODE`,`CARDTYPE`,`LOGINTYPE`,`USERTYPE`,`LOGINTIME`) values (3,3,'admin','127.0.0.1','',NULL,0,0,NULL,1,1,'2018-01-11 17:07:39');
insert  into `base_mgr_core_login_log`(`LOGINID`,`LOGINUSERID`,`LOGINNAME`,`IP`,`USERMOBILE`,`ALIASNUM`,`PROVCODE`,`AREACODE`,`CARDTYPE`,`LOGINTYPE`,`USERTYPE`,`LOGINTIME`) values (4,3,'admin','localhost','',NULL,0,0,NULL,1,1,'2018-01-11 17:18:47');
insert  into `base_mgr_core_login_log`(`LOGINID`,`LOGINUSERID`,`LOGINNAME`,`IP`,`USERMOBILE`,`ALIASNUM`,`PROVCODE`,`AREACODE`,`CARDTYPE`,`LOGINTYPE`,`USERTYPE`,`LOGINTIME`) values (5,3,'admin','localhost','',NULL,0,0,NULL,1,1,'2018-01-11 17:52:46');
insert  into `base_mgr_core_login_log`(`LOGINID`,`LOGINUSERID`,`LOGINNAME`,`IP`,`USERMOBILE`,`ALIASNUM`,`PROVCODE`,`AREACODE`,`CARDTYPE`,`LOGINTYPE`,`USERTYPE`,`LOGINTIME`) values (6,3,'admin','localhost','',NULL,0,0,NULL,1,1,'2018-01-17 09:28:06');
insert  into `base_mgr_core_login_log`(`LOGINID`,`LOGINUSERID`,`LOGINNAME`,`IP`,`USERMOBILE`,`ALIASNUM`,`PROVCODE`,`AREACODE`,`CARDTYPE`,`LOGINTYPE`,`USERTYPE`,`LOGINTIME`) values (7,3,'admin','localhost','',NULL,0,0,NULL,1,1,'2018-01-17 09:34:51');
insert  into `base_mgr_core_login_log`(`LOGINID`,`LOGINUSERID`,`LOGINNAME`,`IP`,`USERMOBILE`,`ALIASNUM`,`PROVCODE`,`AREACODE`,`CARDTYPE`,`LOGINTYPE`,`USERTYPE`,`LOGINTIME`) values (8,3,'admin','localhost','',NULL,0,0,NULL,1,1,'2018-01-17 09:36:25');
insert  into `base_mgr_core_login_log`(`LOGINID`,`LOGINUSERID`,`LOGINNAME`,`IP`,`USERMOBILE`,`ALIASNUM`,`PROVCODE`,`AREACODE`,`CARDTYPE`,`LOGINTYPE`,`USERTYPE`,`LOGINTIME`) values (9,3,'admin','localhost','',NULL,0,0,NULL,1,1,'2018-01-17 10:30:05');

/*Table structure for table `base_mgr_core_menu_permission` */

DROP TABLE IF EXISTS `base_mgr_core_menu_permission`;

CREATE TABLE `base_mgr_core_menu_permission` (
  `mpId` int(11) NOT NULL AUTO_INCREMENT COMMENT '自增ID',
  `mpPId` int(11) NOT NULL DEFAULT '0' COMMENT '父级菜单-权限id，默认0为顶级菜单（父级只能为菜单）',
  `mpName` varchar(16) NOT NULL COMMENT '菜单-权限名称',
  `mpType` varchar(16) NOT NULL DEFAULT 'Menu' COMMENT '菜单-权限类型(菜单:Menu;权限Permission)',
  `mpIcon` varchar(512) DEFAULT NULL COMMENT '菜单-权限图标',
  `mpCode` varchar(64) NOT NULL,
  `mpUrl` varchar(512) DEFAULT NULL COMMENT '菜单-权限链接，相对路径',
  `isHasPermission` char(1) NOT NULL DEFAULT 'Y' COMMENT '菜单-权限是否需要权限（需要Y;不需要N）默认需要',
  `mpStatus` char(1) NOT NULL DEFAULT 'Y' COMMENT '菜单-权限状态（启用Y;禁用N）默认启用',
  `mpSort` int(11) DEFAULT '0' COMMENT '排序，最大的排最前面',
  `updateTime` datetime DEFAULT NULL COMMENT '修改时间',
  `addTime` datetime NOT NULL COMMENT '添加时间',
  PRIMARY KEY (`mpId`)
) ENGINE=InnoDB AUTO_INCREMENT=31 DEFAULT CHARSET=utf8 COMMENT='菜单-权限模块表';

/*Data for the table `base_mgr_core_menu_permission` */

insert  into `base_mgr_core_menu_permission`(`mpId`,`mpPId`,`mpName`,`mpType`,`mpIcon`,`mpCode`,`mpUrl`,`isHasPermission`,`mpStatus`,`mpSort`,`updateTime`,`addTime`) values (1,0,'系统管理','Menu','&#xe723;','System','#','Y','Y',100,'2018-01-01 00:00:00','2018-01-01 00:00:00');
insert  into `base_mgr_core_menu_permission`(`mpId`,`mpPId`,`mpName`,`mpType`,`mpIcon`,`mpCode`,`mpUrl`,`isHasPermission`,`mpStatus`,`mpSort`,`updateTime`,`addTime`) values (2,1,'属性配置管理','Menu','&#xe723;','System:properties:list','/sysadminMain/properties/index.do','Y','Y',4,'2018-01-01 00:00:00','2018-01-01 00:00:00');
insert  into `base_mgr_core_menu_permission`(`mpId`,`mpPId`,`mpName`,`mpType`,`mpIcon`,`mpCode`,`mpUrl`,`isHasPermission`,`mpStatus`,`mpSort`,`updateTime`,`addTime`) values (3,2,'查看','Permission',NULL,'System:properties:show','#','Y','Y',4,'2018-01-01 00:00:00','2018-01-01 00:00:00');
insert  into `base_mgr_core_menu_permission`(`mpId`,`mpPId`,`mpName`,`mpType`,`mpIcon`,`mpCode`,`mpUrl`,`isHasPermission`,`mpStatus`,`mpSort`,`updateTime`,`addTime`) values (4,2,'修改','Permission',NULL,'System:properties:add','#','Y','Y',3,'2018-01-01 00:00:00','2018-01-01 00:00:00');
insert  into `base_mgr_core_menu_permission`(`mpId`,`mpPId`,`mpName`,`mpType`,`mpIcon`,`mpCode`,`mpUrl`,`isHasPermission`,`mpStatus`,`mpSort`,`updateTime`,`addTime`) values (5,2,'修改','Permission',NULL,'System:properties:update','#','Y','Y',2,'2018-01-01 00:00:00','2018-01-01 00:00:00');
insert  into `base_mgr_core_menu_permission`(`mpId`,`mpPId`,`mpName`,`mpType`,`mpIcon`,`mpCode`,`mpUrl`,`isHasPermission`,`mpStatus`,`mpSort`,`updateTime`,`addTime`) values (6,2,'删除','Permission',NULL,'System:properties:delete','#','Y','Y',1,'2018-01-01 00:00:00','2018-01-01 00:00:00');
insert  into `base_mgr_core_menu_permission`(`mpId`,`mpPId`,`mpName`,`mpType`,`mpIcon`,`mpCode`,`mpUrl`,`isHasPermission`,`mpStatus`,`mpSort`,`updateTime`,`addTime`) values (7,1,'菜单管理','Menu','&#xe705;','System:menu:list','/sysadminMain/menuPermission/index.do','Y','Y',3,'2018-01-01 00:00:00','2018-01-01 00:00:00');
insert  into `base_mgr_core_menu_permission`(`mpId`,`mpPId`,`mpName`,`mpType`,`mpIcon`,`mpCode`,`mpUrl`,`isHasPermission`,`mpStatus`,`mpSort`,`updateTime`,`addTime`) values (8,7,'查看','Permission','','System:menu:show','#','Y','Y',4,'2018-01-01 00:00:00','2018-01-01 00:00:00');
insert  into `base_mgr_core_menu_permission`(`mpId`,`mpPId`,`mpName`,`mpType`,`mpIcon`,`mpCode`,`mpUrl`,`isHasPermission`,`mpStatus`,`mpSort`,`updateTime`,`addTime`) values (9,7,'创建','Permission','','System:menu:add','#','Y','Y',3,'2018-01-01 00:00:00','2018-01-01 00:00:00');
insert  into `base_mgr_core_menu_permission`(`mpId`,`mpPId`,`mpName`,`mpType`,`mpIcon`,`mpCode`,`mpUrl`,`isHasPermission`,`mpStatus`,`mpSort`,`updateTime`,`addTime`) values (10,7,'修改','Permission','','System:menu:update','#','Y','Y',2,'2018-01-01 00:00:00','2018-01-01 00:00:00');
insert  into `base_mgr_core_menu_permission`(`mpId`,`mpPId`,`mpName`,`mpType`,`mpIcon`,`mpCode`,`mpUrl`,`isHasPermission`,`mpStatus`,`mpSort`,`updateTime`,`addTime`) values (11,7,'删除','Permission','','System:menu:delete','#','Y','Y',1,'2018-01-01 00:00:00','2018-01-01 00:00:00');
insert  into `base_mgr_core_menu_permission`(`mpId`,`mpPId`,`mpName`,`mpType`,`mpIcon`,`mpCode`,`mpUrl`,`isHasPermission`,`mpStatus`,`mpSort`,`updateTime`,`addTime`) values (12,1,'角色管理','Menu','&#xe726;','System:role:list','/sysadminMain/mgrRole/index.do','Y','Y',2,'2018-01-01 00:00:00','2018-01-01 00:00:00');
insert  into `base_mgr_core_menu_permission`(`mpId`,`mpPId`,`mpName`,`mpType`,`mpIcon`,`mpCode`,`mpUrl`,`isHasPermission`,`mpStatus`,`mpSort`,`updateTime`,`addTime`) values (13,12,'查看','Permission','','System:role:show','#','Y','Y',4,'2018-01-01 00:00:00','2018-01-01 00:00:00');
insert  into `base_mgr_core_menu_permission`(`mpId`,`mpPId`,`mpName`,`mpType`,`mpIcon`,`mpCode`,`mpUrl`,`isHasPermission`,`mpStatus`,`mpSort`,`updateTime`,`addTime`) values (14,12,'创建','Permission','','System:role:add','#','Y','Y',3,'2018-01-01 00:00:00','2018-01-01 00:00:00');
insert  into `base_mgr_core_menu_permission`(`mpId`,`mpPId`,`mpName`,`mpType`,`mpIcon`,`mpCode`,`mpUrl`,`isHasPermission`,`mpStatus`,`mpSort`,`updateTime`,`addTime`) values (15,12,'修改','Permission','','System:role:update','#','Y','Y',2,'2018-01-01 00:00:00','2018-01-01 00:00:00');
insert  into `base_mgr_core_menu_permission`(`mpId`,`mpPId`,`mpName`,`mpType`,`mpIcon`,`mpCode`,`mpUrl`,`isHasPermission`,`mpStatus`,`mpSort`,`updateTime`,`addTime`) values (16,12,'删除','Permission','','System:role:delete','#','Y','Y',1,'2018-01-01 00:00:00','2018-01-01 00:00:00');
insert  into `base_mgr_core_menu_permission`(`mpId`,`mpPId`,`mpName`,`mpType`,`mpIcon`,`mpCode`,`mpUrl`,`isHasPermission`,`mpStatus`,`mpSort`,`updateTime`,`addTime`) values (17,1,'管理员管理','Menu','&#xe6b8;','System:administrator:list','/sysadminMain/serviceMgr/index.do','Y','Y',1,'2018-01-01 00:00:00','2018-01-01 00:00:00');
insert  into `base_mgr_core_menu_permission`(`mpId`,`mpPId`,`mpName`,`mpType`,`mpIcon`,`mpCode`,`mpUrl`,`isHasPermission`,`mpStatus`,`mpSort`,`updateTime`,`addTime`) values (18,17,'查看','Permission','','System:administrator:show','#','Y','Y',4,'2018-01-01 00:00:00','2018-01-01 00:00:00');
insert  into `base_mgr_core_menu_permission`(`mpId`,`mpPId`,`mpName`,`mpType`,`mpIcon`,`mpCode`,`mpUrl`,`isHasPermission`,`mpStatus`,`mpSort`,`updateTime`,`addTime`) values (19,17,'创建','Permission','','System:administrator:add','#','Y','Y',3,'2018-01-01 00:00:00','2018-01-01 00:00:00');
insert  into `base_mgr_core_menu_permission`(`mpId`,`mpPId`,`mpName`,`mpType`,`mpIcon`,`mpCode`,`mpUrl`,`isHasPermission`,`mpStatus`,`mpSort`,`updateTime`,`addTime`) values (20,17,'修改','Permission','','System:administrator:update','#','Y','Y',2,'2018-01-01 00:00:00','2018-01-01 00:00:00');
insert  into `base_mgr_core_menu_permission`(`mpId`,`mpPId`,`mpName`,`mpType`,`mpIcon`,`mpCode`,`mpUrl`,`isHasPermission`,`mpStatus`,`mpSort`,`updateTime`,`addTime`) values (21,17,'删除','Permission','','System:administrator:delete','#','Y','Y',1,'2018-01-01 00:00:00','2018-01-01 00:00:00');
insert  into `base_mgr_core_menu_permission`(`mpId`,`mpPId`,`mpName`,`mpType`,`mpIcon`,`mpCode`,`mpUrl`,`isHasPermission`,`mpStatus`,`mpSort`,`updateTime`,`addTime`) values (22,0,'日志管理','Menu','&#xe6ce;','Log','#','Y','Y',99,'2018-01-01 00:00:00','2018-01-01 00:00:00');
insert  into `base_mgr_core_menu_permission`(`mpId`,`mpPId`,`mpName`,`mpType`,`mpIcon`,`mpCode`,`mpUrl`,`isHasPermission`,`mpStatus`,`mpSort`,`updateTime`,`addTime`) values (23,22,'操作日志列表','Menu','','Log:operator','/sysadminMain/log/operator.do','Y','Y',1,'2018-01-01 00:00:00','2018-01-01 00:00:00');
insert  into `base_mgr_core_menu_permission`(`mpId`,`mpPId`,`mpName`,`mpType`,`mpIcon`,`mpCode`,`mpUrl`,`isHasPermission`,`mpStatus`,`mpSort`,`updateTime`,`addTime`) values (24,22,'登录日志列表','Menu','','Log:login','/sysadminMain/log/login.do','Y','Y',1,'2018-01-01 00:00:00','2018-01-01 00:00:00');
insert  into `base_mgr_core_menu_permission`(`mpId`,`mpPId`,`mpName`,`mpType`,`mpIcon`,`mpCode`,`mpUrl`,`isHasPermission`,`mpStatus`,`mpSort`,`updateTime`,`addTime`) values (25,0,'Tests','Menu','&#xe6ce;','Tests','#','Y','Y',98,'2018-01-01 00:00:00','2018-01-01 00:00:00');
insert  into `base_mgr_core_menu_permission`(`mpId`,`mpPId`,`mpName`,`mpType`,`mpIcon`,`mpCode`,`mpUrl`,`isHasPermission`,`mpStatus`,`mpSort`,`updateTime`,`addTime`) values (26,25,'Test列表','Menu','','Tests:list','/sysadminMain/tests/index.do','Y','Y',1,'2018-01-01 00:00:00','2018-01-01 00:00:00');
insert  into `base_mgr_core_menu_permission`(`mpId`,`mpPId`,`mpName`,`mpType`,`mpIcon`,`mpCode`,`mpUrl`,`isHasPermission`,`mpStatus`,`mpSort`,`updateTime`,`addTime`) values (27,0,'查看','Permission',NULL,'Tests:show',NULL,'Y','Y',4,'2018-01-01 00:00:00','2018-01-01 00:00:00');
insert  into `base_mgr_core_menu_permission`(`mpId`,`mpPId`,`mpName`,`mpType`,`mpIcon`,`mpCode`,`mpUrl`,`isHasPermission`,`mpStatus`,`mpSort`,`updateTime`,`addTime`) values (28,0,'创建','Permission',NULL,'Tests:add',NULL,'Y','Y',3,'2018-01-01 00:00:00','2018-01-01 00:00:00');
insert  into `base_mgr_core_menu_permission`(`mpId`,`mpPId`,`mpName`,`mpType`,`mpIcon`,`mpCode`,`mpUrl`,`isHasPermission`,`mpStatus`,`mpSort`,`updateTime`,`addTime`) values (29,0,'修改','Permission',NULL,'Tests:update',NULL,'Y','Y',2,'2018-01-01 00:00:00','2018-01-01 00:00:00');
insert  into `base_mgr_core_menu_permission`(`mpId`,`mpPId`,`mpName`,`mpType`,`mpIcon`,`mpCode`,`mpUrl`,`isHasPermission`,`mpStatus`,`mpSort`,`updateTime`,`addTime`) values (30,0,'删除','Permission',NULL,'Tests:delete',NULL,'Y','Y',1,'2018-01-01 00:00:00','2018-01-01 00:00:00');

/*Table structure for table `base_mgr_core_mgr_role` */

DROP TABLE IF EXISTS `base_mgr_core_mgr_role`;

CREATE TABLE `base_mgr_core_mgr_role` (
  `roleId` int(11) NOT NULL AUTO_INCREMENT COMMENT '自增ID',
  `roleName` varchar(128) NOT NULL COMMENT '角色名称（程序校验唯一键）',
  `roleDesc` varchar(512) DEFAULT NULL COMMENT '角色描述',
  `roleStatus` char(1) NOT NULL DEFAULT 'Y' COMMENT '角色状态（启用Y;禁用N）',
  `roleSort` int(11) DEFAULT '0' COMMENT '排序，最大的排最前面',
  `updateTime` datetime DEFAULT NULL COMMENT '修改时间',
  `addTime` datetime NOT NULL COMMENT '添加时间',
  PRIMARY KEY (`roleId`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8 COMMENT='管理员角色表';

/*Data for the table `base_mgr_core_mgr_role` */

insert  into `base_mgr_core_mgr_role`(`roleId`,`roleName`,`roleDesc`,`roleStatus`,`roleSort`,`updateTime`,`addTime`) values (1,'超级系统管理员','超级系统管理员','Y',100,'2018-01-01 00:00:00','2018-01-01 00:00:00');
insert  into `base_mgr_core_mgr_role`(`roleId`,`roleName`,`roleDesc`,`roleStatus`,`roleSort`,`updateTime`,`addTime`) values (2,'普通管理员','普通管理员','Y',99,'2018-01-01 00:00:00','2018-01-01 00:00:00');

/*Table structure for table `base_mgr_core_mgrrole_menupermission` */

DROP TABLE IF EXISTS `base_mgr_core_mgrrole_menupermission`;

CREATE TABLE `base_mgr_core_mgrrole_menupermission` (
  `roleId` int(11) NOT NULL COMMENT '管理员角色id',
  `mpId` int(11) NOT NULL COMMENT '菜单-权限id',
  `addTime` datetime NOT NULL COMMENT '添加时间'
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='管理员角色-菜单权限关联表';

/*Data for the table `base_mgr_core_mgrrole_menupermission` */

insert  into `base_mgr_core_mgrrole_menupermission`(`roleId`,`mpId`,`addTime`) values (1,1,'2018-01-17 00:00:00');
insert  into `base_mgr_core_mgrrole_menupermission`(`roleId`,`mpId`,`addTime`) values (1,2,'2018-01-17 00:00:00');
insert  into `base_mgr_core_mgrrole_menupermission`(`roleId`,`mpId`,`addTime`) values (1,3,'2018-01-17 00:00:00');
insert  into `base_mgr_core_mgrrole_menupermission`(`roleId`,`mpId`,`addTime`) values (1,4,'2018-01-17 00:00:00');
insert  into `base_mgr_core_mgrrole_menupermission`(`roleId`,`mpId`,`addTime`) values (1,5,'2018-01-17 00:00:00');
insert  into `base_mgr_core_mgrrole_menupermission`(`roleId`,`mpId`,`addTime`) values (1,6,'2018-01-17 00:00:00');
insert  into `base_mgr_core_mgrrole_menupermission`(`roleId`,`mpId`,`addTime`) values (1,7,'2018-01-17 00:00:00');
insert  into `base_mgr_core_mgrrole_menupermission`(`roleId`,`mpId`,`addTime`) values (1,8,'2018-01-17 00:00:00');
insert  into `base_mgr_core_mgrrole_menupermission`(`roleId`,`mpId`,`addTime`) values (1,9,'2018-01-17 00:00:00');
insert  into `base_mgr_core_mgrrole_menupermission`(`roleId`,`mpId`,`addTime`) values (1,10,'2018-01-17 00:00:00');
insert  into `base_mgr_core_mgrrole_menupermission`(`roleId`,`mpId`,`addTime`) values (1,11,'2018-01-17 00:00:00');
insert  into `base_mgr_core_mgrrole_menupermission`(`roleId`,`mpId`,`addTime`) values (1,12,'2018-01-17 00:00:00');
insert  into `base_mgr_core_mgrrole_menupermission`(`roleId`,`mpId`,`addTime`) values (1,13,'2018-01-17 00:00:00');
insert  into `base_mgr_core_mgrrole_menupermission`(`roleId`,`mpId`,`addTime`) values (1,14,'2018-01-17 00:00:00');
insert  into `base_mgr_core_mgrrole_menupermission`(`roleId`,`mpId`,`addTime`) values (1,15,'2018-01-17 00:00:00');
insert  into `base_mgr_core_mgrrole_menupermission`(`roleId`,`mpId`,`addTime`) values (1,16,'2018-01-17 00:00:00');
insert  into `base_mgr_core_mgrrole_menupermission`(`roleId`,`mpId`,`addTime`) values (1,17,'2018-01-17 00:00:00');
insert  into `base_mgr_core_mgrrole_menupermission`(`roleId`,`mpId`,`addTime`) values (1,18,'2018-01-17 00:00:00');
insert  into `base_mgr_core_mgrrole_menupermission`(`roleId`,`mpId`,`addTime`) values (1,19,'2018-01-17 00:00:00');
insert  into `base_mgr_core_mgrrole_menupermission`(`roleId`,`mpId`,`addTime`) values (1,20,'2018-01-17 00:00:00');
insert  into `base_mgr_core_mgrrole_menupermission`(`roleId`,`mpId`,`addTime`) values (1,21,'2018-01-17 00:00:00');
insert  into `base_mgr_core_mgrrole_menupermission`(`roleId`,`mpId`,`addTime`) values (1,22,'2018-01-17 00:00:00');
insert  into `base_mgr_core_mgrrole_menupermission`(`roleId`,`mpId`,`addTime`) values (1,23,'2018-01-17 00:00:00');
insert  into `base_mgr_core_mgrrole_menupermission`(`roleId`,`mpId`,`addTime`) values (1,24,'2018-01-17 00:00:00');
insert  into `base_mgr_core_mgrrole_menupermission`(`roleId`,`mpId`,`addTime`) values (1,25,'2018-01-17 00:00:00');
insert  into `base_mgr_core_mgrrole_menupermission`(`roleId`,`mpId`,`addTime`) values (1,26,'2018-01-17 00:00:00');
insert  into `base_mgr_core_mgrrole_menupermission`(`roleId`,`mpId`,`addTime`) values (1,27,'2018-01-17 00:00:00');
insert  into `base_mgr_core_mgrrole_menupermission`(`roleId`,`mpId`,`addTime`) values (1,28,'2018-01-17 00:00:00');
insert  into `base_mgr_core_mgrrole_menupermission`(`roleId`,`mpId`,`addTime`) values (1,29,'2018-01-17 00:00:00');
insert  into `base_mgr_core_mgrrole_menupermission`(`roleId`,`mpId`,`addTime`) values (1,30,'2018-01-17 00:00:00');

/*Table structure for table `base_mgr_core_operator_log` */

DROP TABLE IF EXISTS `base_mgr_core_operator_log`;

CREATE TABLE `base_mgr_core_operator_log` (
  `opLogId` int(11) NOT NULL AUTO_INCREMENT COMMENT '自增ID',
  `opAuName` varchar(16) DEFAULT NULL COMMENT '操作用户名称',
  `opAuId` varchar(16) DEFAULT NULL COMMENT '操作用户id',
  `opBusinessName` varchar(16) DEFAULT NULL COMMENT '操作业务名称',
  `opBusinessDesc` varchar(16) DEFAULT NULL COMMENT '操作业务描述',
  `opMethodType` varchar(16) DEFAULT NULL COMMENT '操作方法类型（ADD新增，UPDATE修改，DELETE删除,BATCH_DELETE批量删除）',
  `opContent` text COMMENT '记录信息',
  `opJsonContent` longtext COMMENT '记录信息(json格式)',
  `opIp` varchar(256) DEFAULT NULL COMMENT '操作用户ip',
  `addTime` datetime NOT NULL COMMENT '添加时间',
  PRIMARY KEY (`opLogId`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8 COMMENT='操作记录表';

/*Data for the table `base_mgr_core_operator_log` */

insert  into `base_mgr_core_operator_log`(`opLogId`,`opAuName`,`opAuId`,`opBusinessName`,`opBusinessDesc`,`opMethodType`,`opContent`,`opJsonContent`,`opIp`,`addTime`) values (1,'admin','null','管理员帐号管理','新增管理员帐号','ADD','<b>新增数据</b><br/>id=null,loginName=admin,loginPassword=d033e22ae348aeb5660fc2140aec35850c4da997,isAllowLogin=Y,addTime=Thu Jan 11 17:07:25 CST 2018,updateTime=null,mobile=,userMail=,roleId=1','json数据：{\"addTime\":1515661645273,\"roleId\":\"1\",\"mobile\":\"\",\"isAllowLogin\":\"Y\",\"loginName\":\"admin\",\"loginPassword\":\"d033e22ae348aeb5660fc2140aec35850c4da997\",\"userMail\":\"\"}',NULL,'2018-01-11 17:07:25');

/*Table structure for table `base_mgr_core_operator_log_config` */

DROP TABLE IF EXISTS `base_mgr_core_operator_log_config`;

CREATE TABLE `base_mgr_core_operator_log_config` (
  `oplConfigId` int(11) NOT NULL AUTO_INCREMENT COMMENT '自增ID',
  `oplBusinessName` varchar(16) DEFAULT NULL COMMENT '操作业务名称',
  `oplBusinessDesc` varchar(32) DEFAULT NULL COMMENT '操作业务描述',
  `oplMethodType` varchar(16) DEFAULT NULL COMMENT '操作方法类型（ADD新增，UPDATE修改，DELETE删除,BATCH_DELETE批量删除）',
  `oplInterceptMethod` varchar(128) DEFAULT NULL COMMENT '拦截方法全路径com.xxx.xx.getxxx()[拦截在dao层],必须提供实体作为参数（程序校验唯一键）',
  `oplDomainName` varchar(128) DEFAULT NULL COMMENT '实体全路径com.xxx.xx.getxxx()',
  `oplPKIds` varchar(128) DEFAULT NULL COMMENT '主键列表（多个逗号分隔）',
  `oplFindDomainMethod` varchar(128) DEFAULT NULL COMMENT '查找实体方法全路径com.xxx.xx.getxxx(),必须提供实体作为参数[配置在dao层]',
  `oplRecordFields` varchar(512) DEFAULT NULL COMMENT '记录字段，默认空为全部字段（一般为varchar类型）',
  `oplStatus` char(1) NOT NULL DEFAULT 'Y' COMMENT '启用状态（启用Y;禁用N）',
  `updateTime` datetime NOT NULL COMMENT '修改时间',
  `addTime` datetime NOT NULL COMMENT '添加时间',
  PRIMARY KEY (`oplConfigId`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8 COMMENT='操作记录配置表';

/*Data for the table `base_mgr_core_operator_log_config` */

insert  into `base_mgr_core_operator_log_config`(`oplConfigId`,`oplBusinessName`,`oplBusinessDesc`,`oplMethodType`,`oplInterceptMethod`,`oplDomainName`,`oplPKIds`,`oplFindDomainMethod`,`oplRecordFields`,`oplStatus`,`updateTime`,`addTime`) values (1,'角色管理','新增角色','ADD','cn.mycom.sysadmin.mgrrole.mapper.SysAdminMgrRoleDao.addMgrRole','cn.mycom.sysadmin.mgrrole.domain.MgrRole','roleId','cn.mycom.sysadmin.mgrrole.mapper.SysAdminMgrRoleDao.findMgrRoleById','roleId,roleName,roleDesc,roleStatus,roleSort,menuPermissionIdsString,updateTime,addTime','Y','2018-01-01 00:00:00','2018-01-01 00:00:00');
insert  into `base_mgr_core_operator_log_config`(`oplConfigId`,`oplBusinessName`,`oplBusinessDesc`,`oplMethodType`,`oplInterceptMethod`,`oplDomainName`,`oplPKIds`,`oplFindDomainMethod`,`oplRecordFields`,`oplStatus`,`updateTime`,`addTime`) values (2,'角色管理','更新角色','UPDATE','cn.mycom.sysadmin.mgrrole.mapper.SysAdminMgrRoleDao.updateMgrRoleById','cn.mycom.sysadmin.mgrrole.domain.MgrRole','roleId','cn.mycom.sysadmin.mgrrole.mapper.SysAdminMgrRoleDao.findMgrRoleById','roleId,roleName,roleDesc,roleStatus,roleSort,menuPermissionIdsString,updateTime,addTime','Y','2018-01-01 00:00:00','2018-01-01 00:00:00');
insert  into `base_mgr_core_operator_log_config`(`oplConfigId`,`oplBusinessName`,`oplBusinessDesc`,`oplMethodType`,`oplInterceptMethod`,`oplDomainName`,`oplPKIds`,`oplFindDomainMethod`,`oplRecordFields`,`oplStatus`,`updateTime`,`addTime`) values (3,'角色管理','删除角色','DELETE','cn.mycom.sysadmin.mgrrole.mapper.SysAdminMgrRoleDao.deleteMgrRoleById','cn.mycom.sysadmin.mgrrole.domain.MgrRole','roleId','cn.mycom.sysadmin.mgrrole.mapper.SysAdminMgrRoleDao.findMgrRoleById','roleId,roleName,roleDesc,roleStatus,roleSort,menuPermissionIdsString,updateTime,addTime','Y','2018-01-01 00:00:00','2018-01-01 00:00:00');
insert  into `base_mgr_core_operator_log_config`(`oplConfigId`,`oplBusinessName`,`oplBusinessDesc`,`oplMethodType`,`oplInterceptMethod`,`oplDomainName`,`oplPKIds`,`oplFindDomainMethod`,`oplRecordFields`,`oplStatus`,`updateTime`,`addTime`) values (4,'管理员帐号管理','新增管理员帐号','ADD','cn.mycom.sysadmin.servicemgr.mapper.SysAdminServiceMgrDao.addServiceMgr','cn.mycom.sysadmin.servicemgr.domain.ServiceMgr','id','cn.mycom.sysadmin.servicemgr.mapper.SysAdminServiceMgrDao.findServiceMgrById','id,loginName,loginPassword,isAllowLogin,addTime,updateTime,mobile,userMail,roleId','Y','2018-01-01 00:00:00','2018-01-01 00:00:00');
insert  into `base_mgr_core_operator_log_config`(`oplConfigId`,`oplBusinessName`,`oplBusinessDesc`,`oplMethodType`,`oplInterceptMethod`,`oplDomainName`,`oplPKIds`,`oplFindDomainMethod`,`oplRecordFields`,`oplStatus`,`updateTime`,`addTime`) values (5,'管理员帐号管理','更新管理员帐号','UPDATE','cn.mycom.sysadmin.servicemgr.mapper.SysAdminServiceMgrDao.updateServiceMgrById','cn.mycom.sysadmin.servicemgr.domain.ServiceMgr','id','cn.mycom.sysadmin.servicemgr.mapper.SysAdminServiceMgrDao.findServiceMgrById','id,loginName,loginPassword,isAllowLogin,addTime,updateTime,mobile,userMail,roleId','Y','2018-01-01 00:00:00','2018-01-01 00:00:00');
insert  into `base_mgr_core_operator_log_config`(`oplConfigId`,`oplBusinessName`,`oplBusinessDesc`,`oplMethodType`,`oplInterceptMethod`,`oplDomainName`,`oplPKIds`,`oplFindDomainMethod`,`oplRecordFields`,`oplStatus`,`updateTime`,`addTime`) values (6,'管理员帐号管理','删除管理员帐号','DELETE','cn.mycom.sysadmin.servicemgr.mapper.SysAdminServiceMgrDao.deleteServiceMgrById','cn.mycom.sysadmin.servicemgr.domain.ServiceMgr','id','cn.mycom.sysadmin.servicemgr.mapper.SysAdminServiceMgrDao.findServiceMgrById','id,loginName,loginPassword,isAllowLogin,addTime,updateTime,mobile,userMail,roleId','Y','2018-01-01 00:00:00','2018-01-01 00:00:00');
insert  into `base_mgr_core_operator_log_config`(`oplConfigId`,`oplBusinessName`,`oplBusinessDesc`,`oplMethodType`,`oplInterceptMethod`,`oplDomainName`,`oplPKIds`,`oplFindDomainMethod`,`oplRecordFields`,`oplStatus`,`updateTime`,`addTime`) values (7,'tests管理','新增tests','ADD','cn.mycom.sysadmin.test.mapper.SysAdminTestsDao.addTests','cn.mycom.sysadmin.test.domain.Tests','testId','cn.mycom.sysadmin.test.mapper.SysAdminTestsDao.findTestsById','testId,name,updateTime,addTime','Y','2018-01-01 00:00:00','2018-01-01 00:00:00');
insert  into `base_mgr_core_operator_log_config`(`oplConfigId`,`oplBusinessName`,`oplBusinessDesc`,`oplMethodType`,`oplInterceptMethod`,`oplDomainName`,`oplPKIds`,`oplFindDomainMethod`,`oplRecordFields`,`oplStatus`,`updateTime`,`addTime`) values (8,'tests管理','更新tests','UPDATE','cn.mycom.sysadmin.test.mapper.SysAdminTestsDao.updateTests','cn.mycom.sysadmin.test.domain.Tests','testId','cn.mycom.sysadmin.test.mapper.SysAdminTestsDao.findTestsById','testId,name,updateTime,addTime','Y','2018-01-01 00:00:00','2018-01-01 00:00:00');
insert  into `base_mgr_core_operator_log_config`(`oplConfigId`,`oplBusinessName`,`oplBusinessDesc`,`oplMethodType`,`oplInterceptMethod`,`oplDomainName`,`oplPKIds`,`oplFindDomainMethod`,`oplRecordFields`,`oplStatus`,`updateTime`,`addTime`) values (9,'tests管理','删除tests','DELETE','cn.mycom.sysadmin.test.mapper.SysAdminTestsDao.deleteTests','cn.mycom.sysadmin.test.domain.Tests','testId','cn.mycom.sysadmin.test.mapper.SysAdminTestsDao.findTestsById','testId,name,updateTime,addTime','Y','2018-01-01 00:00:00','2018-01-01 00:00:00');

/*Table structure for table `base_mgr_core_properties` */

DROP TABLE IF EXISTS `base_mgr_core_properties`;

CREATE TABLE `base_mgr_core_properties` (
  `propKey` varchar(64) NOT NULL,
  `propValue` longtext NOT NULL COMMENT '参数长值',
  `remark` varchar(512) DEFAULT NULL,
  `updateTime` datetime DEFAULT NULL,
  `addTime` datetime NOT NULL,
  PRIMARY KEY (`propKey`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='用来保存属性配置';

/*Data for the table `base_mgr_core_properties` */

insert  into `base_mgr_core_properties`(`propKey`,`propValue`,`remark`,`updateTime`,`addTime`) values ('website.defaultPaw','defaultadmin888','管理员初始密码','2018-01-01 00:00:00','2018-01-01 00:00:00');

/*Table structure for table `base_mgr_core_serializable_number` */

DROP TABLE IF EXISTS `base_mgr_core_serializable_number`;

CREATE TABLE `base_mgr_core_serializable_number` (
  `serialKey` varchar(32) NOT NULL COMMENT '序列键值',
  `currentNumber` int(11) NOT NULL COMMENT '当前编号值',
  `remark` varchar(512) NOT NULL COMMENT '备注',
  `updateTime` datetime DEFAULT NULL COMMENT '更新时间',
  `addTime` datetime DEFAULT NULL COMMENT '添加时间',
  PRIMARY KEY (`serialKey`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='系统序列表';

/*Data for the table `base_mgr_core_serializable_number` */

/*Table structure for table `base_mgr_core_service_mgr` */

DROP TABLE IF EXISTS `base_mgr_core_service_mgr`;

CREATE TABLE `base_mgr_core_service_mgr` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '自增ID',
  `loginName` varchar(32) NOT NULL COMMENT '管理员登录名称',
  `loginPassword` varchar(128) NOT NULL,
  `isAllowLogin` char(1) NOT NULL COMMENT '用户是否可以登录(Y：允许；N：不允许；)',
  `addTime` datetime DEFAULT NULL,
  `mobile` varchar(16) DEFAULT NULL COMMENT '服务号管理员手机号',
  `userMail` varchar(128) DEFAULT NULL COMMENT '邮箱地址',
  `roleId` int(11) DEFAULT NULL COMMENT '角色id（一个用户一个角色）',
  `updateTime` datetime DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8 COMMENT='管理员表';

/*Data for the table `base_mgr_core_service_mgr` */

insert  into `base_mgr_core_service_mgr`(`id`,`loginName`,`loginPassword`,`isAllowLogin`,`addTime`,`mobile`,`userMail`,`roleId`,`updateTime`) values (1,'mycomadmin','16f9a5f82ffdef142180edc12e8309af08e744e3','Y','2018-01-01 00:00:00','13800000000','mycomadmin@mycom.cn',1,'2018-01-01 00:00:00');
insert  into `base_mgr_core_service_mgr`(`id`,`loginName`,`loginPassword`,`isAllowLogin`,`addTime`,`mobile`,`userMail`,`roleId`,`updateTime`) values (2,'genadmin','c4ee70e9f6aeb5ccd001232f97dabc495d122c4c','Y','2018-01-01 00:00:00','13800000000','genadmin@mycom.cn',2,'2018-01-01 00:00:00');
insert  into `base_mgr_core_service_mgr`(`id`,`loginName`,`loginPassword`,`isAllowLogin`,`addTime`,`mobile`,`userMail`,`roleId`,`updateTime`) values (3,'admin','d033e22ae348aeb5660fc2140aec35850c4da997','Y','2018-01-11 17:07:25','','',1,'2018-01-11 17:07:25');

/*Table structure for table `tests` */

DROP TABLE IF EXISTS `tests`;

CREATE TABLE `tests` (
  `testId` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `name` varchar(64) NOT NULL,
  `addTime` datetime NOT NULL,
  `updateTime` datetime DEFAULT NULL,
  PRIMARY KEY (`testId`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8;

/*Data for the table `tests` */

insert  into `tests`(`testId`,`name`,`addTime`,`updateTime`) values (1,'zhangsan','2018-01-01 00:00:00','2018-01-01 00:00:00');
insert  into `tests`(`testId`,`name`,`addTime`,`updateTime`) values (2,'lisi','2018-01-01 00:00:00','2018-01-01 00:00:00');
insert  into `tests`(`testId`,`name`,`addTime`,`updateTime`) values (3,'wangwu','2018-01-01 00:00:00','2018-01-01 00:00:00');
insert  into `tests`(`testId`,`name`,`addTime`,`updateTime`) values (4,'wang111','2018-01-01 00:00:00','2018-01-01 00:00:00');
insert  into `tests`(`testId`,`name`,`addTime`,`updateTime`) values (6,'11','2017-11-29 12:31:31','2017-11-29 12:31:31');
insert  into `tests`(`testId`,`name`,`addTime`,`updateTime`) values (7,'1122222222222','2017-11-29 12:33:41','2017-11-29 13:14:28');

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
