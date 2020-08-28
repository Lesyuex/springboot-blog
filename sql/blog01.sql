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
-- Table structure for table `t_blog_categories`
--

DROP TABLE IF EXISTS `t_blog_categories`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `t_blog_categories` (
  `blog_id` bigint(20) NOT NULL COMMENT '博客Id',
  `cate_id` int(11) NOT NULL COMMENT '分类Id',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  PRIMARY KEY (`blog_id`,`cate_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='博客分类表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `t_blog_categories`
--

LOCK TABLES `t_blog_categories` WRITE;
/*!40000 ALTER TABLE `t_blog_categories` DISABLE KEYS */;
/*!40000 ALTER TABLE `t_blog_categories` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `t_blog_comments`
--

DROP TABLE IF EXISTS `t_blog_comments`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `t_blog_comments` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `blog_id` bigint(20) NOT NULL COMMENT '博客id',
  `user_id` bigint(20) NOT NULL COMMENT '评论人id',
  `content` varchar(255) NOT NULL COMMENT '内容',
  `parent_id` varchar(255) NOT NULL COMMENT '父评论id',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `status` tinyint(2) NOT NULL COMMENT '状态:0启用 1禁用',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='博客评论表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `t_blog_comments`
--

LOCK TABLES `t_blog_comments` WRITE;
/*!40000 ALTER TABLE `t_blog_comments` DISABLE KEYS */;
/*!40000 ALTER TABLE `t_blog_comments` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `t_blog_metas`
--

DROP TABLE IF EXISTS `t_blog_metas`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `t_blog_metas` (
  `blog_id` bigint(20) NOT NULL COMMENT '博客Id',
  `meta_id` int(11) NOT NULL COMMENT '标签Id',
  PRIMARY KEY (`blog_id`,`meta_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='博客标签表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `t_blog_metas`
--

LOCK TABLES `t_blog_metas` WRITE;
/*!40000 ALTER TABLE `t_blog_metas` DISABLE KEYS */;
/*!40000 ALTER TABLE `t_blog_metas` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `t_category`
--

DROP TABLE IF EXISTS `t_category`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `t_category` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(60) NOT NULL COMMENT '名称',
  `parent_id` int(11) DEFAULT NULL COMMENT '父分类',
  `sort` int(11) DEFAULT NULL COMMENT '序号',
  `remark` varchar(60) DEFAULT NULL COMMENT '备注',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `status` tinyint(2) NOT NULL COMMENT '状态:0启用 1禁用',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='分类表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `t_category`
--

LOCK TABLES `t_category` WRITE;
/*!40000 ALTER TABLE `t_category` DISABLE KEYS */;
/*!40000 ALTER TABLE `t_category` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `t_menu_permission`
--

DROP TABLE IF EXISTS `t_menu_permission`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `t_menu_permission` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '权限id',
  `parent_id` tinyint(3) DEFAULT '0' COMMENT '父级id',
  `name` varchar(50) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '权限名',
  `title` varchar(50) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '备注',
  `type` varchar(50) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT 'unknown' COMMENT '资源类型',
  `path` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '资源路径',
  `redirect` varchar(200) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '路由重定向',
  `perm` varchar(50) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '权限标识',
  `component` varchar(50) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '组件资源(用于匹配component组件)',
  `icon` varchar(50) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `sort_id` int(11) NOT NULL,
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `status` tinyint(2) DEFAULT '0' COMMENT '状态',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1001 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `t_menu_permission`
--

LOCK TABLES `t_menu_permission` WRITE;
/*!40000 ALTER TABLE `t_menu_permission` DISABLE KEYS */;
INSERT INTO `t_menu_permission` VALUES (1,0,'SystemSet','系统管理','1','/system',NULL,NULL,'Layout','el-icon-setting',0,'2019-10-11 12:05:44','2020-08-26 17:16:48',1),(2,1,'UserManage','用户管理','1','/system/user/list',NULL,NULL,'system/user/UserList','el-icon-user',2,'2019-10-11 12:06:25','2020-08-26 17:16:48',1),(5,1,'RoleListManage','角色管理','1','/system/role/list',NULL,NULL,'system/role/RoleList','el-icon-s-check',3,'2019-10-11 12:07:25','2020-08-28 15:23:54',1),(6,1,'PermissionManage','权限管理','1','/system/permission/list',NULL,NULL,'system/permission/List','el-icon-collection-tag',5,'2019-10-11 12:07:25','2020-08-26 17:17:25',1),(7,1,'MenuManage','菜单管理','1','/system/menu/list',NULL,NULL,'system/menu/MenuList','el-icon-menu',4,'2020-08-28 15:21:58','2020-08-28 15:23:29',1),(999,0,'SystemAll','系统所有权限','0','/**/*',NULL,'sys:all','',NULL,9,'2020-08-10 10:15:37','2020-08-24 09:58:16',1);
/*!40000 ALTER TABLE `t_menu_permission` ENABLE KEYS */;
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
  `enabled` tinyint(1) DEFAULT '1' COMMENT '状态',
  `status` tinyint(1) DEFAULT '1',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='角色表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `t_role`
--

LOCK TABLES `t_role` WRITE;
/*!40000 ALTER TABLE `t_role` DISABLE KEYS */;
INSERT INTO `t_role` VALUES (1,'超级管理员','超级管理员','2019-10-11 11:05:43','2020-08-28 11:15:34',1,1),(2,'vip','vip','2019-11-04 17:19:49','2020-08-28 11:15:34',1,1),(3,'普通用户','普通用户','2019-11-04 17:24:26','2020-08-28 11:15:33',1,1);
/*!40000 ALTER TABLE `t_role` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `t_roles_menus_permissions`
--

DROP TABLE IF EXISTS `t_roles_menus_permissions`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `t_roles_menus_permissions` (
  `role_id` tinyint(3) NOT NULL COMMENT '角色id',
  `perm_id` int(11) NOT NULL COMMENT '权限id',
  PRIMARY KEY (`role_id`,`perm_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='角色权限表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `t_roles_menus_permissions`
--

LOCK TABLES `t_roles_menus_permissions` WRITE;
/*!40000 ALTER TABLE `t_roles_menus_permissions` DISABLE KEYS */;
INSERT INTO `t_roles_menus_permissions` VALUES (1,1),(1,2),(1,3),(1,4),(1,5),(1,6),(1,7),(1,9),(1,10),(1,999);
/*!40000 ALTER TABLE `t_roles_menus_permissions` ENABLE KEYS */;
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
  `email` varchar(100) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `enabled` tinyint(1) DEFAULT '1' COMMENT '状态',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `status` tinyint(2) DEFAULT '1',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='用户表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `t_user`
--

LOCK TABLES `t_user` WRITE;
/*!40000 ALTER TABLE `t_user` DISABLE KEYS */;
INSERT INTO `t_user` VALUES (1,'admin','123456','13076652356@163.com',1,'2019-10-16 15:24:17','2020-08-28 13:16:09',1),(2,'Test','123456','13076652356@163.com',0,'2019-10-16 15:24:17','2020-08-28 15:05:05',1);
/*!40000 ALTER TABLE `t_user` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `t_user_collect_blog`
--

DROP TABLE IF EXISTS `t_user_collect_blog`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `t_user_collect_blog` (
  `user_id` bigint(20) NOT NULL COMMENT '用户id',
  `blog_id` bigint(20) NOT NULL COMMENT '博客id',
  PRIMARY KEY (`user_id`,`blog_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='用户收藏的博客';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `t_user_collect_blog`
--

LOCK TABLES `t_user_collect_blog` WRITE;
/*!40000 ALTER TABLE `t_user_collect_blog` DISABLE KEYS */;
/*!40000 ALTER TABLE `t_user_collect_blog` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `t_user_collect_categories`
--

DROP TABLE IF EXISTS `t_user_collect_categories`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `t_user_collect_categories` (
  `user_id` bigint(20) NOT NULL COMMENT '用户id',
  `cate_id` int(11) NOT NULL COMMENT '分类id',
  PRIMARY KEY (`user_id`,`cate_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='用户收藏的分类';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `t_user_collect_categories`
--

LOCK TABLES `t_user_collect_categories` WRITE;
/*!40000 ALTER TABLE `t_user_collect_categories` DISABLE KEYS */;
/*!40000 ALTER TABLE `t_user_collect_categories` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `t_user_collect_metas`
--

DROP TABLE IF EXISTS `t_user_collect_metas`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `t_user_collect_metas` (
  `user_id` bigint(20) NOT NULL COMMENT '用户id',
  `meta_id` int(11) NOT NULL COMMENT '标签id',
  PRIMARY KEY (`user_id`,`meta_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='用户收藏的分类';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `t_user_collect_metas`
--

LOCK TABLES `t_user_collect_metas` WRITE;
/*!40000 ALTER TABLE `t_user_collect_metas` DISABLE KEYS */;
/*!40000 ALTER TABLE `t_user_collect_metas` ENABLE KEYS */;
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
INSERT INTO `t_users_roles` VALUES (1,1);
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

-- Dump completed on 2020-08-28 17:51:22
