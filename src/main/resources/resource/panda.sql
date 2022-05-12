-- MySQL dump 10.13  Distrib 5.7.19, for Win64 (x86_64)
--
-- Host: localhost    Database: panda
-- ------------------------------------------------------
-- Server version	5.7.19

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Current Database: `panda`
--

CREATE DATABASE /*!32312 IF NOT EXISTS*/ `panda` /*!40100 DEFAULT CHARACTER SET utf8mb4 */;

USE `panda`;

--
-- Table structure for table `log_login`
--

DROP TABLE IF EXISTS `log_login`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `log_login` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '登录日志 id',
  `user_id` int(11) NOT NULL COMMENT '用户 id',
  `username` varchar(32) NOT NULL COMMENT '用户名',
  `ip` char(15) NOT NULL COMMENT '登录 ip',
  `location` varchar(512) NOT NULL COMMENT '登录位置',
  `time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '登录时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `log_login`
--

LOCK TABLES `log_login` WRITE;
/*!40000 ALTER TABLE `log_login` DISABLE KEYS */;
/*!40000 ALTER TABLE `log_login` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `t_admin`
--

DROP TABLE IF EXISTS `t_admin`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `t_admin` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '管理员 id',
  `username` varchar(255) NOT NULL COMMENT '管理员用户名',
  `password` varchar(255) NOT NULL COMMENT '管理员密码',
  `status` int(11) NOT NULL COMMENT '管理员状态（正常、异常）',
  `register_date` date NOT NULL COMMENT '注册日期',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `t_admin`
--

LOCK TABLES `t_admin` WRITE;
/*!40000 ALTER TABLE `t_admin` DISABLE KEYS */;
INSERT INTO `t_admin` VALUES (1,'admin','admin',0,'2022-04-21');
/*!40000 ALTER TABLE `t_admin` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `t_background`
--

DROP TABLE IF EXISTS `t_background`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `t_background` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '背景 id',
  `user_id` int(11) NOT NULL COMMENT '用户 id（外键约束于 t_user）',
  `upload_id` int(11) NOT NULL COMMENT '上传记录 id（外键约束于 t_upload）',
  `status` int(11) NOT NULL COMMENT '记录状态（正常、异常）',
  `upload_time` date NOT NULL COMMENT '添加时间',
  `url` varchar(512) NOT NULL COMMENT '访问地址 url',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `t_background`
--

LOCK TABLES `t_background` WRITE;
/*!40000 ALTER TABLE `t_background` DISABLE KEYS */;
/*!40000 ALTER TABLE `t_background` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `t_book`
--

DROP TABLE IF EXISTS `t_book`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `t_book` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '图书 id',
  `title` varchar(255) NOT NULL COMMENT '书名',
  `author` varchar(255) NOT NULL COMMENT '作者',
  `translator` varchar(255) DEFAULT NULL COMMENT '译者',
  `downloads` int(11) NOT NULL COMMENT '下载量',
  `book_state` int(11) NOT NULL COMMENT '图书状态（上架、下架、待处理）',
  `book_upload_id` int(11) DEFAULT NULL COMMENT '图书上传记录 id',
  `cover_upload_id` int(11) NOT NULL COMMENT '封面上传记录 id',
  `upload_user_id` int(11) NOT NULL COMMENT '上传用户 id',
  `upload_username` varchar(32) NOT NULL COMMENT '上传用户名',
  `upload_time` date NOT NULL COMMENT '上传时间',
  `book_path` varchar(255) DEFAULT NULL COMMENT '图书保存路径',
  `cover_path` varchar(255) NOT NULL COMMENT '封面保存路径',
  `comments` varchar(255) NOT NULL COMMENT '图书评价',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `t_book`
--

LOCK TABLES `t_book` WRITE;
/*!40000 ALTER TABLE `t_book` DISABLE KEYS */;
/*!40000 ALTER TABLE `t_book` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `t_pixabay`
--

DROP TABLE IF EXISTS `t_pixabay`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `t_pixabay` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '图片 id',
  `condition` varchar(64) DEFAULT NULL COMMENT '检索条件',
  `tags` varchar(64) DEFAULT NULL COMMENT '标签',
  `views` int(11) DEFAULT NULL COMMENT '浏览量',
  `downloads` int(11) DEFAULT NULL COMMENT '下载量',
  `collections` int(11) DEFAULT NULL COMMENT '收藏量',
  `likes` int(11) DEFAULT NULL COMMENT '点赞量',
  `comments` int(11) DEFAULT NULL COMMENT '评论量',
  `add_time` date DEFAULT NULL COMMENT '添加时间',
  `url` varchar(512) DEFAULT NULL COMMENT '图片访问地址',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `t_pixabay`
--

LOCK TABLES `t_pixabay` WRITE;
/*!40000 ALTER TABLE `t_pixabay` DISABLE KEYS */;
/*!40000 ALTER TABLE `t_pixabay` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `t_record`
--

DROP TABLE IF EXISTS `t_record`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `t_record` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '记录 id',
  `user_id` int(11) NOT NULL COMMENT '用户 id',
  `book_id` int(11) NOT NULL COMMENT '图书 id',
  `type` int(11) NOT NULL COMMENT '记录类型（上传图书、下载图书）',
  `status` int(11) NOT NULL COMMENT '记录状态',
  `time` date NOT NULL COMMENT '操作时间',
  `title` varchar(255) NOT NULL COMMENT '书名',
  `author` varchar(255) NOT NULL COMMENT '作者',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `t_record`
--

LOCK TABLES `t_record` WRITE;
/*!40000 ALTER TABLE `t_record` DISABLE KEYS */;
/*!40000 ALTER TABLE `t_record` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `t_upload`
--

DROP TABLE IF EXISTS `t_upload`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `t_upload` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '上传记录 id',
  `user_id` int(11) NOT NULL COMMENT '用户 id',
  `user_type` int(11) NOT NULL COMMENT '用户类型（普通用户、管理员）',
  `username` varchar(255) NOT NULL COMMENT '用户名',
  `type` int(11) NOT NULL COMMENT '上传记录类型（图书、封面、头像、背景）',
  `status` int(11) NOT NULL COMMENT '上传记录状态（已处理、未处理）',
  `upload_time` date NOT NULL COMMENT '上传时间',
  `bucket` varchar(255) NOT NULL COMMENT '资源所在空间名',
  `domain` varchar(255) NOT NULL COMMENT '资源访问域名',
  `key` varchar(255) NOT NULL COMMENT '资源保存路径',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `t_upload`
--

LOCK TABLES `t_upload` WRITE;
/*!40000 ALTER TABLE `t_upload` DISABLE KEYS */;
/*!40000 ALTER TABLE `t_upload` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `t_user`
--

DROP TABLE IF EXISTS `t_user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `t_user` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '用户 id',
  `username` varchar(32) NOT NULL COMMENT '用户名',
  `password` varchar(32) NOT NULL COMMENT '密码',
  `email` varchar(255) NOT NULL COMMENT '邮箱地址',
  `type` int(11) NOT NULL COMMENT '用户类型（普通用户、管理员）',
  `status` int(11) NOT NULL COMMENT '用户状态（正常、异常）',
  `register_date` date NOT NULL COMMENT '注册时间',
  `portrait_path` varchar(255) NOT NULL COMMENT '头像保存路径',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE KEY `uniq_user_email` (`email`) USING BTREE,
  UNIQUE KEY `uniq_user_username` (`username`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 ROW_FORMAT=DYNAMIC;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `t_user`
--

LOCK TABLES `t_user` WRITE;
/*!40000 ALTER TABLE `t_user` DISABLE KEYS */;
/*!40000 ALTER TABLE `t_user` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2022-05-11  8:24:42
