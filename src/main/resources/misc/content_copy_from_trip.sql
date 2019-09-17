/**
* 直接从旅游项目的数据库中导出的
* 旅游项目的文章表结构和基础项目的好像不太一样
*/

DROP TABLE IF EXISTS `t_content_type`;
CREATE TABLE `t_content_type`  (
  `id` bigint(20) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '表主键',
  `create_time` bigint(20) NULL DEFAULT NULL COMMENT '添加时间',
  `update_time` bigint(20) NULL DEFAULT NULL COMMENT '更新时间',
  `enabled` int(1) NULL DEFAULT 1 COMMENT '是否可见，0为不可见，1为可见',
  `expand` tinyint(1) NULL DEFAULT 1 COMMENT '是否可扩展',
  `title` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '内容类型名称',
  `level` int(11) NOT NULL COMMENT '层级',
  `parent_id` bigint(20) NULL DEFAULT NULL COMMENT '父id',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `title`(`title`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 9 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '内容类型表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of t_content_type
-- ----------------------------
INSERT INTO `t_content_type` VALUES (1, 1495016519592, 1495016519592, 1, 1, '计算机语言', 1, NULL);
INSERT INTO `t_content_type` VALUES (2, 1495016519592, 1495016519592, 1, 1, '编程语言', 2, 1);
INSERT INTO `t_content_type` VALUES (3, 1495016519592, 1495016519592, 1, 1, 'JAVA', 3, 2);
INSERT INTO `t_content_type` VALUES (4, 1495016519592, 1495016519592, 1, 1, 'Android', 3, 2);
INSERT INTO `t_content_type` VALUES (5, 1495016519592, 1495016519592, 1, 1, '口语语言', 1, NULL);
INSERT INTO `t_content_type` VALUES (6, 1495016519592, 1495016519592, 1, 1, '中文', 2, 5);
INSERT INTO `t_content_type` VALUES (7, 1495016519592, 1495016519592, 1, 1, '普通话', 3, 6);
INSERT INTO `t_content_type` VALUES (8, 1495016519592, 1495016519592, 1, 1, '粤语', 3, 6);

SET FOREIGN_KEY_CHECKS = 1;

DROP TABLE IF EXISTS `t_content`;
CREATE TABLE `t_content`  (
  `id` bigint(20) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '表主键',
  `create_time` bigint(20) NULL DEFAULT NULL COMMENT '添加时间',
  `update_time` bigint(20) NULL DEFAULT NULL COMMENT '更新时间',
  `enabled` int(1) NULL DEFAULT 0 COMMENT '状态，0为禁用，1为草稿，2为发布',
  `title` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '内容标题',
  `sub_title` varchar(512) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '内容副标题',
  `author` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '作者',
  `content` longtext CHARACTER SET utf8 COLLATE utf8_general_ci NULL COMMENT '内容',
  `release_time` bigint(20) NULL DEFAULT NULL COMMENT '发布时间',
  `weight` int(11) NULL DEFAULT 0 COMMENT '文章顺序',
  `share_title` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '分享标题',
  `share_desc` varchar(512) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '分享描述',
  `share_img` varchar(512) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '分享图片',
  `create_user` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '' COMMENT '用户名，创建用户',
  `modify_user` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '' COMMENT '用户名，最后修改用户',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `title`(`title`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 2 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '内容表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of t_content
-- ----------------------------
INSERT INTO `t_content` VALUES (1, 1495016519592, 1543404379451, 1, '测试标题', NULL, '俊杰', '<p><img src=\"https://ss2.baidu.com/6ONYsjip0QIZ8tyhnq/it/u=3638599133,2127934094&fm=58\"/></p><p>888</p>', 0, 0, '', '', 'http://admin-resource.torinosrc.com/images/content/20180720142122_1532067682713', 'lvxin', 'lvxin');

SET FOREIGN_KEY_CHECKS = 1;

DROP TABLE IF EXISTS `t_content_type_content`;
CREATE TABLE `t_content_type_content`  (
  `id` bigint(20) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '表主键',
  `content_type_id` bigint(20) UNSIGNED NOT NULL COMMENT '内容类别id',
  `content_id` bigint(20) UNSIGNED NOT NULL COMMENT '内容id',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `content_type_id`(`content_type_id`, `content_id`) USING BTREE,
  INDEX `content_type_id_2`(`content_type_id`, `content_id`) USING BTREE,
  INDEX `content_id`(`content_id`) USING BTREE,
  CONSTRAINT `t_content_type_content_ibfk_1` FOREIGN KEY (`content_type_id`) REFERENCES `t_content_type` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `t_content_type_content_ibfk_2` FOREIGN KEY (`content_id`) REFERENCES `t_content` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 17 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '内容管理表' ROW_FORMAT = Dynamic;
