/*
SQLyog Ultimate v12.08 (64 bit)
MySQL - 5.7.25 : Database - k_test
*********************************************************************
*/

/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;
CREATE DATABASE /*!32312 IF NOT EXISTS*/`k_test` /*!40100 DEFAULT CHARACTER SET utf8 */;

USE `k_test`;

/*Table structure for table `role` */

DROP TABLE IF EXISTS `role`;

CREATE TABLE `role` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '自增主键id',
  `name` varchar(16) COLLATE utf8_bin DEFAULT NULL COMMENT '角色名',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

/*Data for the table `role` */

insert  into `role`(`id`,`name`) values (1,'超级管理员'),(2,'管理员'),(3,'用户');

/*Table structure for table `user` */

DROP TABLE IF EXISTS `user`;

CREATE TABLE `user` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '自增主键id',
  `login_name` varchar(16) COLLATE utf8_bin NOT NULL COMMENT '登录名',
  `nickname` varchar(16) COLLATE utf8_bin NOT NULL COMMENT '昵称',
  `password` varchar(60) COLLATE utf8_bin NOT NULL COMMENT '密码',
  `gender` tinyint(1) DEFAULT NULL COMMENT '性别',
  `age` int(11) DEFAULT NULL COMMENT '年龄',
  `email` varchar(32) COLLATE utf8_bin NOT NULL COMMENT '电子邮箱',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

/*Data for the table `user` */

insert  into `user`(`id`,`login_name`,`nickname`,`password`,`gender`,`age`,`email`) values (1,'yueyaye','月澤淵','$2a$10$FvEUuGD2RCLQ5OfF7usFWuM7smD./aUQYaYny21ePyosni3CzWKtC',1,18,'yueyaye@163.com'),(2,'admin1','hasaki','$2a$10$FvEUuGD2RCLQ5OfF7usFWuM7smD./aUQYaYny21ePyosni3CzWKtC',0,19,'yueyaye@163.com');

/*Table structure for table `user_role` */

DROP TABLE IF EXISTS `user_role`;

CREATE TABLE `user_role` (
  `user_id` int(11) DEFAULT NULL COMMENT '用户id',
  `role_id` int(11) DEFAULT NULL COMMENT '角色id'
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

/*Data for the table `user_role` */

insert  into `user_role`(`user_id`,`role_id`) values (1,1),(2,2);

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
