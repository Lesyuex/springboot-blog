-- MySQL dump 10.13  Distrib 5.7.27, for Win64 (x86_64)
--
-- Host: 127.0.0.1    Database: blog01
-- ------------------------------------------------------
-- Server version	5.7.27-log

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `t_article`
--

DROP TABLE IF EXISTS `t_article`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `t_article` (
  `id` int(10) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `title` varchar(200) DEFAULT NULL COMMENT ' 文章标题',
  `slug` varchar(200) DEFAULT NULL COMMENT ' url地址',
  `content` text COMMENT '文章内容',
  `author_id` int(10) DEFAULT NULL COMMENT '作者ID',
  `type` varchar(16) DEFAULT NULL COMMENT '文章类型',
  `categories` varchar(200) DEFAULT NULL COMMENT '分类',
  `thumbImg` varchar(512) DEFAULT NULL COMMENT '缩略图地址',
  `hits` int(10) DEFAULT NULL COMMENT '文章点击量',
  `comments_num` int(10) DEFAULT NULL COMMENT '评论数量',
  `allow_comment` int(1) DEFAULT NULL COMMENT '允许评论',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00' COMMENT '修改时间',
  `status` int(1) DEFAULT NULL COMMENT '文章状态',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='文章表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `t_article`
--

LOCK TABLES `t_article` WRITE;
/*!40000 ALTER TABLE `t_article` DISABLE KEYS */;
/*!40000 ALTER TABLE `t_article` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `t_article_comment`
--

DROP TABLE IF EXISTS `t_article_comment`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `t_article_comment` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `parent_id` bigint(20) DEFAULT NULL COMMENT '父评论',
  `article_id` int(10) DEFAULT NULL COMMENT '文章id',
  `content` varchar(500) DEFAULT NULL COMMENT '评论内容',
  `comment_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '评论时间',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `status` tinyint(2) DEFAULT '0' COMMENT '状态',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='文章评论表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `t_article_comment`
--

LOCK TABLES `t_article_comment` WRITE;
/*!40000 ALTER TABLE `t_article_comment` DISABLE KEYS */;
/*!40000 ALTER TABLE `t_article_comment` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `t_articles_metas`
--

DROP TABLE IF EXISTS `t_articles_metas`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `t_articles_metas` (
  `a_id` int(10) NOT NULL COMMENT '文章id',
  `m_id` int(10) NOT NULL COMMENT '标签id',
  PRIMARY KEY (`a_id`,`m_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='文章标签关联表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `t_articles_metas`
--

LOCK TABLES `t_articles_metas` WRITE;
/*!40000 ALTER TABLE `t_articles_metas` DISABLE KEYS */;
/*!40000 ALTER TABLE `t_articles_metas` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `t_blog`
--

DROP TABLE IF EXISTS `t_blog`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `t_blog` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `user_id` int(11) DEFAULT NULL COMMENT '用户id',
  `title` varchar(255) DEFAULT NULL COMMENT '标题',
  `description` varchar(255) DEFAULT NULL COMMENT '描述',
  `content` text COMMENT '内容',
  `allow_comment` tinyint(2) DEFAULT '0' COMMENT '允许评论 0是 1否',
  `comment_sum` int(11) DEFAULT '0' COMMENT '评论数量',
  `star_sum` int(11) DEFAULT '0' COMMENT ' 点赞数量',
  `collect_sum` int(11) DEFAULT '0' COMMENT '收藏数量',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `status` tinyint(2) DEFAULT '0' COMMENT '状态',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COMMENT='博客表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `t_blog`
--

LOCK TABLES `t_blog` WRITE;
/*!40000 ALTER TABLE `t_blog` DISABLE KEYS */;
INSERT INTO `t_blog` VALUES (1,1,'Test','ddd','dd',0,0,0,0,'2020-08-10 02:58:20',0,'2020-08-10 03:00:39');
/*!40000 ALTER TABLE `t_blog` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `t_meta`
--

DROP TABLE IF EXISTS `t_meta`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `t_meta` (
  `id` int(10) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `parent_id` int(10) DEFAULT NULL COMMENT '父标签',
  `name` varchar(200) DEFAULT NULL COMMENT '名称',
  `remark` varchar(200) DEFAULT NULL COMMENT '说明',
  `type` varchar(200) DEFAULT NULL COMMENT '类型',
  `description` varchar(200) DEFAULT NULL COMMENT '描述',
  `sort` int(10) DEFAULT NULL COMMENT '排序',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='标签表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `t_meta`
--

LOCK TABLES `t_meta` WRITE;
/*!40000 ALTER TABLE `t_meta` DISABLE KEYS */;
/*!40000 ALTER TABLE `t_meta` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `t_permission`
--

DROP TABLE IF EXISTS `t_permission`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `t_permission` (
  `id` tinyint(3) NOT NULL AUTO_INCREMENT COMMENT '权限id',
  `parent_id` tinyint(3) DEFAULT '0' COMMENT '父级id',
  `type` varchar(50) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT 'unknown' COMMENT '资源类型',
  `name` varchar(50) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '权限名',
  `url` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '资源路径',
  `permission` varchar(50) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '权限标识',
  `component` varchar(50) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '组件资源(用于匹配component组件)',
  `icon` varchar(50) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `remark` varchar(50) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '备注',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `status` tinyint(2) DEFAULT '0' COMMENT '状态',
  `sort_id` int(11) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `t_permission`
--

LOCK TABLES `t_permission` WRITE;
/*!40000 ALTER TABLE `t_permission` DISABLE KEYS */;
INSERT INTO `t_permission` VALUES (1,0,'1','用户管理','/userAdd',NULL,'views/user/UserAdd','el-icon-user-solid','用户管理','2019-10-11 12:05:44','2020-08-10 10:22:04',0,0),(2,1,'1','用户查询','/updateUser',NULL,'components/Main','el-icon-search','修改','2019-10-11 12:06:57','2020-08-10 10:21:41',0,1),(3,1,'1','用户添加','/addUser',NULL,'components/Main','el-icon-circle-plus-outline','用户添加','2019-10-11 12:06:25','2020-08-10 10:22:04',0,2),(4,0,'1','角色管理','/role',NULL,'components/Main','el-icon-collection-tag','角色管理','2019-10-11 12:07:25','2020-08-10 10:22:04',0,3),(5,4,'1','角色查询','/listRole',NULL,'views/user/UserList','el-icon-search','修改','2019-10-11 12:08:10','2020-08-10 10:22:04',0,4),(6,4,'1','vip特权','/vip',NULL,'components/Main','el-icon-search','修改','2019-10-11 12:08:10','2020-08-10 10:22:04',0,5),(8,3,'1','添加1','/userAdd1',NULL,'views/user/UserAdd','el-icon-circle-plus-outline','用户添加','2019-10-11 12:06:25','2020-08-10 10:22:04',0,6),(9,3,'1','添加2','/userAdd2',NULL,'views/user/UserAdd','el-icon-circle-plus-outline','用户添加','2019-10-11 12:06:25','2020-08-10 10:22:04',0,7),(10,2,'1','查询1','/userTest',NULL,'views/user/UserList','el-icon-search','修改','2019-10-11 12:06:57','2020-08-10 10:22:04',0,8),(11,0,'0','系统所有权限','/**/*','sys:all','',NULL,'','2020-08-10 10:15:37','2020-08-10 10:22:04',0,9);
/*!40000 ALTER TABLE `t_permission` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `t_role`
--

DROP TABLE IF EXISTS `t_role`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `t_role` (
  `id` tinyint(3) NOT NULL AUTO_INCREMENT COMMENT '角色id',
  `name` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '角色名',
  `remark` varchar(50) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '备注',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `enabled` tinyint(2) DEFAULT '1' COMMENT '状态',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='角色表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `t_role`
--

LOCK TABLES `t_role` WRITE;
/*!40000 ALTER TABLE `t_role` DISABLE KEYS */;
INSERT INTO `t_role` VALUES (1,'ROLE_ADMIN','超级管理员','2019-10-11 11:05:43','2020-04-22 16:31:12',1),(2,'ROLE_VIP','vip','2019-11-04 17:19:49','2020-04-22 16:31:12',1),(3,'ROLE_USER','普通用户','2019-11-04 17:24:26','2020-04-22 16:31:12',1);
/*!40000 ALTER TABLE `t_role` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `t_roles_permissions`
--

DROP TABLE IF EXISTS `t_roles_permissions`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `t_roles_permissions` (
  `role_id` tinyint(3) NOT NULL COMMENT '角色id',
  `perm_id` tinyint(3) NOT NULL COMMENT '权限id',
  PRIMARY KEY (`role_id`,`perm_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='角色权限表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `t_roles_permissions`
--

LOCK TABLES `t_roles_permissions` WRITE;
/*!40000 ALTER TABLE `t_roles_permissions` DISABLE KEYS */;
INSERT INTO `t_roles_permissions` VALUES (1,1),(1,2),(1,3),(1,4),(1,5),(1,7),(1,8),(1,9),(1,10),(1,11);
/*!40000 ALTER TABLE `t_roles_permissions` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `t_user`
--

DROP TABLE IF EXISTS `t_user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `t_user` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '用户id',
  `username` varchar(50) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL,
  `password` varchar(250) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL,
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `status` tinyint(2) DEFAULT '1' COMMENT '状态',
  `email` varchar(100) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='用户表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `t_user`
--

LOCK TABLES `t_user` WRITE;
/*!40000 ALTER TABLE `t_user` DISABLE KEYS */;
INSERT INTO `t_user` VALUES (1,'admin','123456','2019-10-11 11:05:03','2020-08-10 10:28:01',1,NULL),(2,'JyrpoKoo','JyrpoKoo','2019-10-16 15:23:59','2019-11-13 13:18:35',1,NULL),(3,'Lenmo','Lenmo','2019-10-16 15:24:17','2019-11-13 13:18:35',1,NULL);
/*!40000 ALTER TABLE `t_user` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `t_users_roles`
--

DROP TABLE IF EXISTS `t_users_roles`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `t_users_roles` (
  `user_id` int(11) NOT NULL DEFAULT '0' COMMENT '用户id',
  `role_id` tinyint(3) NOT NULL DEFAULT '0' COMMENT '角色id',
  PRIMARY KEY (`user_id`,`role_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='用户角色关联表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `t_users_roles`
--

LOCK TABLES `t_users_roles` WRITE;
/*!40000 ALTER TABLE `t_users_roles` DISABLE KEYS */;
INSERT INTO `t_users_roles` VALUES (1,1),(2,3),(3,2);
/*!40000 ALTER TABLE `t_users_roles` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2020-08-14 15:53:58
