DROP TABLE IF EXISTS t_sys_user_role;
DROP TABLE IF EXISTS t_sys_role_authority;
DROP TABLE IF EXISTS t_sys_authority_menu_permission;
DROP TABLE IF EXISTS t_sys_authority_interface_permission;
DROP TABLE IF EXISTS t_sys_user;
DROP TABLE IF EXISTS t_sys_role;
DROP TABLE IF EXISTS t_sys_authority;
DROP TABLE IF EXISTS t_sys_menu_permission;
DROP TABLE IF EXISTS t_sys_interface_permission;
DROP TABLE IF EXISTS `t_captcha`;
DROP TABLE IF EXISTS `t_shopping_cart_detail`;
DROP TABLE IF EXISTS `t_shopping_cart`;
DROP TABLE IF EXISTS `t_order_detail`;
DROP TABLE IF EXISTS `t_customer_statistics`;
DROP TABLE IF EXISTS `t_order`;
DROP TABLE IF EXISTS `t_customer_consignee`;
DROP TABLE IF EXISTS `t_wx_refund`;
DROP TABLE IF EXISTS `t_merchant_consignee`;
DROP TABLE IF EXISTS `t_shop_product_detail`;
DROP TABLE IF EXISTS `t_shop_product`;
DROP TABLE IF EXISTS `t_log`;
DROP TABLE IF EXISTS `t_distribution_price_config`;
DROP TABLE IF EXISTS `t_shop_tree`;
DROP TABLE IF EXISTS `t_shop_account_detail`;
DROP TABLE IF EXISTS `t_shop_account`;
DROP TABLE IF EXISTS `t_invitation`;
DROP TABLE IF EXISTS `t_shop_product_detail_snapshot`;
DROP TABLE IF EXISTS `t_shop_product_snapshot`;
DROP TABLE IF EXISTS `t_product_detail_snapshot`;
DROP TABLE IF EXISTS `t_product_snapshot`;
DROP TABLE IF EXISTS `t_product_detail`;
DROP TABLE IF EXISTS `t_product`;
DROP TABLE IF EXISTS `t_shop`;
DROP TABLE IF EXISTS `t_user`;
DROP TABLE IF EXISTS `t_category`;



CREATE TABLE IF NOT EXISTS `t_product_snapshot` (
  `id` BIGINT UNSIGNED AUTO_INCREMENT COMMENT '表主键',
  `create_time` BIGINT COMMENT '添加时间',
  `update_time` BIGINT COMMENT '更新时间',
  `enabled` INT(1) DEFAULT '1' COMMENT '是否可见，0为不可见，1为可见',
  `name` VARCHAR(512) COMMENT '商品名称',
  `title` VARCHAR(512) COMMENT '商品简述',
  `image_url` LONGTEXT COMMENT '商品图片url',
  `small_image_url` LONGTEXT COMMENT '商品小图url',
  `description` LONGTEXT COMMENT '商品描述',
  `size_desc` LONGTEXT COMMENT '尺码表',
  `product_id` BIGINT UNSIGNED NOT NULL COMMENT '商品id',
PRIMARY KEY (`id`)
) ENGINE=INNODB DEFAULT CHARSET=utf8 COMMENT='商品快照表' AUTO_INCREMENT=1;

CREATE TABLE IF NOT EXISTS `t_sys_user` (
  `id` BIGINT UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '表主键',
  `username` VARCHAR(64) DEFAULT "" COMMENT '用户名',
  `password` VARCHAR(512) DEFAULT "" COMMENT '密码',
  `name` VARCHAR(128) DEFAULT "" COMMENT '姓名',
  `email` VARCHAR(512) DEFAULT "" COMMENT '邮箱',
  `mobile` VARCHAR(24) DEFAULT "" COMMENT '手机',
  `create_time` BIGINT NOT NULL DEFAULT '0' COMMENT '添加时间',
  `update_time` BIGINT NOT NULL DEFAULT '0' COMMENT '更新时间',
  `enabled` int(1) DEFAULT '1' COMMENT '是否可见，0为不可见，1为可见',
  PRIMARY KEY (`id`),
  INDEX (`username`),
  UNIQUE (`username`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='用户表' AUTO_INCREMENT=1;

INSERT INTO `t_sys_user` (`id`, `username`, `password`,`name`, `email`, `mobile`, `create_time`, `update_time`, `enabled`) VALUES
  (1, 'lvxin', '881e404b88965981e05e911832c14c96','吕鑫','lvxin@torinosrc.com','18027188617', 1495016519592, 1495016519592, 1),
  (2, 'admin', 'df655ad8d3229f3269fad2a8bab59b6c','管理员','lvxin@torinosrc.com','18027188617', 1495202295539, 1495202295539, 1),
  (3, 'guest', '565dd969076eef0ac3f9d49aa61e9489','游客','lvxin@torinosrc.com','18027188617', 1495202367492, 1495202367492, 1);

CREATE TABLE IF NOT EXISTS `t_sys_role` (
  `id` BIGINT UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '表主键',
  `english_name` VARCHAR(64) DEFAULT NULL COMMENT '角色英文名称',
  `chinese_name` VARCHAR(128) DEFAULT NULL COMMENT '角色中文名称',
  `description` VARCHAR(1024) DEFAULT NULL COMMENT '角色描述',
  `create_time` BIGINT NOT NULL DEFAULT '0' COMMENT '添加时间',
  `update_time` BIGINT NOT NULL DEFAULT '0' COMMENT '更新时间',
  `enabled` int(1) DEFAULT '1' COMMENT '是否可见，0为不可见，1为可见',
  PRIMARY KEY (`id`),
  UNIQUE(`chinese_name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='角色表' AUTO_INCREMENT=1;

INSERT INTO `t_sys_role` (`id`, `english_name`, `chinese_name`, `description`, `create_time`, `update_time`, `enabled`) VALUES
  (1, 'ADMIN', '管理员', '管理员', 1495016519592, 1495016519592, 1),
  (2, 'OPERATION', '运维人员', '运维人员', 1495016519592, 1495016519592, 1),
  (3, 'USER', '用户', '用户', 1495016519592, 1495016519592, 1);

CREATE TABLE IF NOT EXISTS `t_sys_authority` (
  `id` BIGINT UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '表主键',
  `name` VARCHAR(64) DEFAULT NULL COMMENT '权限名称',
  `description` VARCHAR(1024) DEFAULT NULL COMMENT '权限描述',
  `create_time` BIGINT NOT NULL DEFAULT '0' COMMENT '添加时间',
  `update_time` BIGINT NOT NULL DEFAULT '0' COMMENT '更新时间',
  `enabled` int(1) DEFAULT '1' COMMENT '是否可见，0为不可见，1为可见',
  PRIMARY KEY (`id`),
  INDEX (`name`),
  UNIQUE (`name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='权限表' AUTO_INCREMENT=1;

INSERT INTO `t_sys_authority` (`id`, `name`, `description`, `create_time`, `update_time`, `enabled`) VALUES
  (1, '后台菜单', '后台菜单', 1495016519592, 1495016519592, 1),
  (2, '前端用户接口', '前端用户接口', 1495016519592, 1495016519592, 1);

CREATE TABLE IF NOT EXISTS `t_sys_menu_permission` (
  `id` BIGINT UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '表主键',
  `parent_id` BIGINT UNSIGNED NOT NULL COMMENT '外键',
  `name` VARCHAR(64) DEFAULT NULL COMMENT '菜单名称',
  `description` VARCHAR(1024) DEFAULT NULL COMMENT '菜单描述',
  `create_time` BIGINT NOT NULL DEFAULT '0' COMMENT '添加时间',
  `update_time` BIGINT NOT NULL DEFAULT '0' COMMENT '更新时间',
  `enabled` int(1) DEFAULT '1' COMMENT '是否可见，0为不可见，1为可见',
  PRIMARY KEY (`id`),
  FOREIGN KEY (parent_id)
    REFERENCES t_sys_menu_permission(id)
    ON UPDATE RESTRICT ON DELETE RESTRICT
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='菜单权限表' AUTO_INCREMENT=1;

INSERT INTO `t_sys_menu_permission` (`id`, `parent_id`, `name`, `description`, `create_time`, `update_time`, `enabled`) VALUES
  (1, 1, '账号管理', '账号管理', 1495016519592, 1495016519592, 1),
  (2, 1, '账号设置', '账号设置', 1495016519592, 1495016519592, 1),
  (3, 1, '角色设置', '角色设置', 1495016519592, 1495016519592, 1),
  (4, 1, '权限设置', '权限设置', 1495016519592, 1495016519592, 1);

CREATE TABLE IF NOT EXISTS `t_sys_interface_permission` (
  `id` BIGINT UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '表主键',
  `name` VARCHAR(64) DEFAULT NULL COMMENT '接口权限名称',
  `permission` VARCHAR(1024) DEFAULT NULL COMMENT '接口权限',
  `create_time` BIGINT NOT NULL DEFAULT '0' COMMENT '添加时间',
  `update_time` BIGINT NOT NULL DEFAULT '0' COMMENT '更新时间',
  `enabled` int(1) DEFAULT '1' COMMENT '是否可见，0为不可见，1为可见',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='接口权限表' AUTO_INCREMENT=1;

INSERT INTO `t_sys_interface_permission` (`id`, `name`, `permission`, `create_time`, `update_time`, `enabled`) VALUES
  (1, '账号新增', 'sys:sysuser:add', 1495016519592, 1495016519592, 1),
  (2, '账号删除', 'sys:sysuser:delete', 1495016519592, 1495016519592, 1),
  (3, '账号修改', 'sys:sysuser:update', 1495016519592, 1495016519592, 1),
  (4, '账号查询', 'sys:sysuser:query', 1495016519592, 1495016519592, 1);

CREATE TABLE IF NOT EXISTS `t_sys_user_role` (
  `sys_user_id` BIGINT UNSIGNED NOT NULL COMMENT '用户Id',
  `sys_role_id` BIGINT UNSIGNED NOT NULL COMMENT '角色Id',
  PRIMARY KEY (`sys_user_id`, `sys_role_id`),
  INDEX (sys_user_id,sys_role_id),
  FOREIGN KEY (sys_user_id)
    REFERENCES t_sys_user(id),
  FOREIGN KEY (sys_role_id)
    REFERENCES t_sys_role(id)
    ON UPDATE RESTRICT ON DELETE RESTRICT
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='用户角色表';

INSERT INTO `t_sys_user_role` (`sys_user_id`, `sys_role_id`) VALUES
  (1, 1),
  (2, 2),
  (3, 3);


CREATE TABLE IF NOT EXISTS `t_sys_role_authority` (
  `sys_role_id` BIGINT UNSIGNED NOT NULL COMMENT '角色Id',
  `sys_authority_id` BIGINT UNSIGNED NOT NULL COMMENT '权限Id',
  PRIMARY KEY (`sys_role_id`,`sys_authority_id`),
  FOREIGN KEY (sys_authority_id)
  REFERENCES t_sys_authority(id),
  FOREIGN KEY (sys_role_id)
  REFERENCES t_sys_role(id)
    ON UPDATE RESTRICT ON DELETE RESTRICT
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='角色权限表';

INSERT INTO `t_sys_role_authority` (`sys_role_id`, `sys_authority_id`) VALUES
  (1, 1),
  (1, 2);


CREATE TABLE IF NOT EXISTS `t_sys_authority_menu_permission` (
  `sys_authority_id` BIGINT UNSIGNED NOT NULL COMMENT '权限Id',
  `sys_menu_permission_id` BIGINT UNSIGNED NOT NULL COMMENT '菜单Id',
  PRIMARY KEY (`sys_menu_permission_id`,`sys_authority_id`),
  FOREIGN KEY (sys_authority_id)
  REFERENCES t_sys_authority(id),
  FOREIGN KEY (sys_menu_permission_id)
  REFERENCES t_sys_menu_permission(id)
    ON UPDATE RESTRICT ON DELETE RESTRICT
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='权限菜单表';

INSERT INTO `t_sys_authority_menu_permission` (`sys_authority_id`, `sys_menu_permission_id`) VALUES
  (1, 1),
  (1, 2),
  (1, 3),
  (1, 4);

CREATE TABLE IF NOT EXISTS `t_sys_authority_interface_permission` (
  `sys_authority_id` BIGINT UNSIGNED NOT NULL COMMENT '权限Id',
  `sys_interface_permission_id` BIGINT UNSIGNED NOT NULL COMMENT '接口Id',
  PRIMARY KEY (`sys_interface_permission_id`,`sys_authority_id`),
  FOREIGN KEY (sys_authority_id)
  REFERENCES t_sys_authority(id),
  FOREIGN KEY (sys_interface_permission_id)
  REFERENCES t_sys_interface_permission(id)
    ON UPDATE RESTRICT ON DELETE RESTRICT
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='权限接口表';

INSERT INTO `t_sys_authority_interface_permission` (`sys_authority_id`, `sys_interface_permission_id`) VALUES
  (1, 1),
  (1, 2),
  (1, 3),
  (1, 4);

CREATE TABLE IF NOT EXISTS `t_captcha` (
  `id` BIGINT UNSIGNED AUTO_INCREMENT COMMENT '表主键',
  `create_time` BIGINT COMMENT '添加时间',
  `update_time` BIGINT COMMENT '更新时间',
  `enabled` INT(1) DEFAULT '1' COMMENT '是否可见，0为不可见，1为可见',
  `username` VARCHAR(255) NOT NULL UNIQUE COMMENT '用户名',
  `validate_code` VARCHAR(100) NOT NULL COMMENT '验证码',
  `type` INT(1) DEFAULT '2' NOT NULL COMMENT'验证码类型',
  PRIMARY KEY (`id`)
) ENGINE=INNODB DEFAULT CHARSET=utf8 COMMENT='验证码表' AUTO_INCREMENT=1;

DROP TABLE IF EXISTS `t_content`;
DROP TABLE IF EXISTS `t_content_type`;
CREATE TABLE IF NOT EXISTS `t_content_type` (
  `id` BIGINT UNSIGNED AUTO_INCREMENT COMMENT '表主键',
  `create_time` BIGINT COMMENT '添加时间',
  `update_time` BIGINT COMMENT '更新时间',
  `enabled` INT(1) DEFAULT '1' COMMENT '是否可见，0为不可见，1为可见',
  `name` VARCHAR(255) NOT NULL UNIQUE COMMENT '内容类型名称',
  `level` INT NOT NULL COMMENT '层级',
  `parent_id` BIGINT COMMENT '父id',
  PRIMARY KEY (`id`)
) ENGINE=INNODB DEFAULT CHARSET=utf8 COMMENT='内容类型表' AUTO_INCREMENT=1;

CREATE TABLE IF NOT EXISTS `t_content` (
  `id` BIGINT UNSIGNED AUTO_INCREMENT COMMENT '表主键',
  `create_time` BIGINT COMMENT '添加时间',
  `update_time` BIGINT COMMENT '更新时间',
  `enabled` INT(1) DEFAULT '0' COMMENT '状态，0为禁用，1为草稿，2为发布',
  `title` VARCHAR(255) NOT NULL UNIQUE COMMENT '内容标题',
  `sub_title` VARCHAR(512) COMMENT '内容副标题',
  `author` VARCHAR(255) COMMENT '作者',
  `content` LONGTEXT COMMENT '内容',
  `content_type_id` BIGINT UNSIGNED NOT NULL COMMENT '内容类型id',
  `release_time` BIGINT COMMENT '发布时间',
  `weight` int DEFAULT '0' COMMENT '文章顺序',
  `share_title` VARCHAR(255) COMMENT '分享标题',
  `share_desc` VARCHAR(512) COMMENT '分享描述',
  `share_img` VARCHAR(512) COMMENT '分享图片',
  `create_user` VARCHAR(64) DEFAULT "" COMMENT '用户名，创建用户',
  `modify_user` VARCHAR(64) DEFAULT "" COMMENT '用户名，最后修改用户',
  PRIMARY KEY (`id`),
  FOREIGN KEY (content_type_id)
    REFERENCES t_content_type(id)
    ON UPDATE RESTRICT ON DELETE RESTRICT
) ENGINE=INNODB DEFAULT CHARSET=utf8 COMMENT='内容表' AUTO_INCREMENT=1;

DROP TABLE IF EXISTS `t_log`;
CREATE TABLE IF NOT EXISTS `t_log` (
  `id` BIGINT UNSIGNED AUTO_INCREMENT COMMENT '表主键',
  `create_time` BIGINT COMMENT '添加时间',
  `update_time` BIGINT COMMENT '更新时间',
  `enabled` INT(1) DEFAULT '1' COMMENT '是否可见，0为不可见，1为可见',
  `username` VARCHAR(255)  COMMENT '用户名',
  `ip` VARCHAR(255) COMMENT '用户ip',
  `request_url` LONGTEXT COMMENT '用户请求url',
  `request_type` VARCHAR(255) COMMENT '用户请求类型',
  `request_begin_time` BIGINT COMMENT '用户请求开始时间',
  `request_end_time` BIGINT COMMENT '用户请求结束时间',
  `response_time` BIGINT COMMENT '接口响应时长，单位ms',
  `function` VARCHAR(255) COMMENT '用户操作类型，比如新增用户',
  `response_code` INT COMMENT '请求响应码',
  PRIMARY KEY (`id`)
) ENGINE=INNODB DEFAULT CHARSET=utf8 COMMENT='日志表' AUTO_INCREMENT=1;


#新增管理员权限
INSERT INTO `t_sys_authority` (`id`, `name`, `description`, `create_time`, `update_time`, `enabled`) VALUES
  (3, '管理员权限', '管理员权限', 1495016519592, 1495016519592, 1);
#新增管理员角色关联管理员权限
INSERT INTO `t_sys_role_authority` (`sys_role_id`, `sys_authority_id`) VALUES
  (1, 3);
#新增接口权限
INSERT INTO `t_sys_interface_permission` (`id`, `name`, `permission`, `create_time`, `update_time`, `enabled`) VALUES
  (5, '角色新增', 'sys:sysrole:add', 1495016519592, 1495016519592, 1),
  (6, '角色删除', 'sys:sysrole:delete', 1495016519592, 1495016519592, 1),
  (7, '角色修改', 'sys:sysrole:update', 1495016519592, 1495016519592, 1),
  (8, '角色查询', 'sys:sysrole:query', 1495016519592, 1495016519592, 1),
  (9, '权限新增', 'sys:sysauthority:add', 1495016519592, 1495016519592, 1),
  (10, '权限删除', 'sys:sysauthority:delete', 1495016519592, 1495016519592, 1),
  (11, '权限修改', 'sys:sysauthority:update', 1495016519592, 1495016519592, 1),
  (12, '权限查询', 'sys:sysauthority:query', 1495016519592, 1495016519592, 1),
  (13, '购物车新增', 'sys:shopcart:add', 1495016519592, 1495016519592, 1),
  (14, '购物车删除', 'sys:shopcart:delete', 1495016519592, 1495016519592, 1),
  (15, '购物车修改', 'sys:shopcart:update', 1495016519592, 1495016519592, 1),
  (16, '购物车查询', 'sys:shopcart:query', 1495016519592, 1495016519592, 1),
  (17, '购物车详情新增', 'sys:shopcartdetail:add', 1495016519592, 1495016519592, 1),
  (18, '购物车详情删除', 'sys:shopcartdetail:delete', 1495016519592, 1495016519592, 1),
  (19, '购物车详情修改', 'sys:shopcartdetail:update', 1495016519592, 1495016519592, 1),
  (20, '购物车详情查询', 'sys:shopcartdetail:query', 1495016519592, 1495016519592, 1),
  (21, '订单新增', 'sys:order:add', 1495016519592, 1495016519592, 1),
  (22, '订单删除', 'sys:order:delete', 1495016519592, 1495016519592, 1),
  (23, '订单修改', 'sys:order:update', 1495016519592, 1495016519592, 1),
  (24, '订单查询', 'sys:order:query', 1495016519592, 1495016519592, 1),
  (25, '订单详情新增', 'sys:orderdetail:add', 1495016519592, 1495016519592, 1),
  (26, '订单详情删除', 'sys:orderdetail:delete', 1495016519592, 1495016519592, 1),
  (27, '订单详情修改', 'sys:orderdetail:update', 1495016519592, 1495016519592, 1),
  (28, '订单详情查询', 'sys:orderdetail:query', 1495016519592, 1495016519592, 1),
  (29, '客户收货地址新增', 'sys:customerconsignee:add', 1495016519592, 1495016519592, 1),
  (30, '客户收货地址删除', 'sys:customerconsignee:delete', 1495016519592, 1495016519592, 1),
  (31, '客户收货地址修改', 'sys:customerconsignee:update', 1495016519592, 1495016519592, 1),
  (32, '商家收货地址新增', 'sys:merchantconsignee:add', 1495016519592, 1495016519592, 1),
  (33, '商家收货地址删除', 'sys:merchantconsignee:delete', 1495016519592, 1495016519592, 1),
  (34, '商家收货地址修改', 'sys:merchantconsignee:update', 1495016519592, 1495016519592, 1),
  (35, '微信下单', 'sys:wechat:add', 1495016519592, 1495016519592, 1),
  (36, '微信退款', 'sys:wechat:query', 1495016519592, 1495016519592, 1);

# 新增菜单管理
INSERT INTO `t_sys_menu_permission` (`id`, `parent_id`, `name`, `description`, `create_time`, `update_time`, `enabled`) VALUES
  (5, 5, '商品管理', '商品管理', 1495016519592, 1495016519592, 1),
  (6, 6, '订单管理', '订单管理', 1495016519592, 1495016519592, 1);

#新增管理员权限关联菜单权限
INSERT INTO `t_sys_authority_menu_permission` (`sys_authority_id`, `sys_menu_permission_id`) VALUES
  (3, 1),
  (3, 2),
  (3, 3),
  (3, 4),
  (3, 5),
  (3, 6);

#新增管理员权限关联接口权限
INSERT INTO `t_sys_authority_interface_permission` (`sys_authority_id`, `sys_interface_permission_id`) VALUES
  (3, 1),
  (3, 2),
  (3, 3),
  (3, 4),
  (3, 5),
  (3, 6),
  (3, 7),
  (3, 8),
  (3, 9),
  (3, 10),
  (3, 11),
  (3, 12),
  (3, 13),
  (3, 14),
  (3, 15),
  (3, 16),
  (3, 17),
  (3, 18),
  (3, 19),
  (3, 20),
  (3, 21),
  (3, 22),
  (3, 23),
  (3, 24),
  (3, 25),
  (3, 26),
  (3, 27),
  (3, 28),
  (3, 29),
  (3, 30),
  (3, 31),
  (3, 32),
  (3, 33),
  (3, 34),
  (3, 35),
  (3, 36);


CREATE TABLE `t_category` (
  `id` BIGINT UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '表主键',
  `create_time` BIGINT COMMENT '添加时间',
  `update_time` BIGINT COMMENT '更新时间',
  `enabled` INTEGER COMMENT '是否启用0：不启用，1：启用',
  `image_url` LONGTEXT COMMENT '分类图片url',
  `name` VARCHAR(20) NOT NULL COMMENT '类别名称',
  `description` VARCHAR(255) COMMENT '类别描述',
PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='商品分类表' AUTO_INCREMENT=1;


CREATE TABLE IF NOT EXISTS `t_product` (
  `id` BIGINT UNSIGNED AUTO_INCREMENT COMMENT '表主键',
  `create_time` BIGINT COMMENT '添加时间',
  `update_time` BIGINT COMMENT '更新时间',
  `enabled` INT(1) DEFAULT '1' COMMENT '是否可见，0为不可见，1为可见',
  `name` VARCHAR(512) COMMENT '商品名称',
  `title` VARCHAR(512) COMMENT '商品简述',
  `image_url` LONGTEXT COMMENT '商品图片url',
  `image_url1` LONGTEXT COMMENT '商品图片url',
  `image_url2` LONGTEXT COMMENT '商品图片url',
  `small_image_url` LONGTEXT COMMENT '商品小图url',
  `description` LONGTEXT COMMENT '商品描述',
  `size_desc` LONGTEXT COMMENT '尺码表',
PRIMARY KEY (`id`)
) ENGINE=INNODB DEFAULT CHARSET=utf8 COMMENT='商品表' AUTO_INCREMENT=1;
ALTER TABLE `t_product` ADD COLUMN `category_id` BIGINT UNSIGNED NOT NULL COMMENT '商品分类ID';
ALTER TABLE `t_product` ADD CONSTRAINT `fk_product_category` FOREIGN KEY (`category_id`) REFERENCES `t_category`(`id`);

CREATE TABLE `t_shop` (
  `id` BIGINT UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '表主键',
  `create_time` BIGINT COMMENT '添加时间',
  `update_time` BIGINT COMMENT '更新时间',
  `title` VARCHAR(512) COMMENT '商店名称',
  `sub_title` VARCHAR(512) COMMENT '二级标题',
  `description` LONGTEXT COMMENT '商店简介',
  `background_url` text COMMENT '背景链接',
  `phone` VARCHAR(16) COMMENT '电话号码',
  `qcode` text COMMENT '店铺二维码',
  `percent_1` INTEGER COMMENT '一级分配',
  `percent_2` INTEGER COMMENT '二级分配',
  `percent_3` INTEGER COMMENT '三级分配',
  `user_id` BIGINT UNSIGNED NOT NULL COMMENT '用户id',
  `enabled` INTEGER COMMENT '是否启用0：不启用，1：启用',
PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='商店表' AUTO_INCREMENT=1;

CREATE TABLE `t_shop_product_snapshot` (
  `id` BIGINT UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '表主键',
  `create_time` BIGINT COMMENT '添加时间',
  `update_time` BIGINT COMMENT '更新时间',
  `enabled` INTEGER COMMENT '是否启用0：不启用，1：启用',
  `product_snapshot_id` BIGINT UNSIGNED NOT NULL COMMENT '商品id',
  `on_sale` integer COMMENT '是否上架中 0：否，1：是',
  `shop_id` BIGINT UNSIGNED NOT NULL COMMENT '店铺id',
  `shop_product_id` BIGINT UNSIGNED NOT NULL COMMENT '商店商品id',
PRIMARY KEY (`id`),
FOREIGN KEY (product_snapshot_id) REFERENCES t_product_snapshot(id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='用户商店中的商品快照表' AUTO_INCREMENT=1;


CREATE TABLE IF NOT EXISTS `t_product_detail` (
  `id` BIGINT UNSIGNED AUTO_INCREMENT COMMENT '表主键',
  `create_time` BIGINT COMMENT '添加时间',
  `update_time` BIGINT COMMENT '更新时间',
  `enabled` INT(1) DEFAULT '1' COMMENT '是否可见，0为不可见，1为可见',
  `size` VARCHAR(255) COMMENT '尺码',
  `price` BIGINT COMMENT '建议价格',
  `uppest_price` BIGINT COMMENT '市场价格',
  `color` VARCHAR(255) COMMENT '颜色',
  `inventory` BIGINT COMMENT '库存',
  `commission` BIGINT COMMENT '佣金',
  `product_id` BIGINT UNSIGNED NOT NULL COMMENT '商品id',
  PRIMARY KEY (`id`),
  FOREIGN KEY (product_id)
  REFERENCES t_product(id)
    ON UPDATE RESTRICT ON DELETE RESTRICT
) ENGINE=INNODB DEFAULT CHARSET=utf8 COMMENT='商品详情表' AUTO_INCREMENT=1;




CREATE TABLE IF NOT EXISTS `t_user` (
  `id` BIGINT UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '表主键',
  `username` VARCHAR(64) DEFAULT "" COMMENT '用户名',
  `password` VARCHAR(512) DEFAULT "" COMMENT '密码',
  `nick_name` VARCHAR(64) DEFAULT '' COMMENT '用户昵称',
  `avatar` VARCHAR(255) DEFAULT '' COMMENT '头像',
  `open_id` VARCHAR(255) NOT NULL COMMENT '微信的openId',
  `create_time` BIGINT NOT NULL DEFAULT '0' COMMENT '添加时间',
  `update_time` BIGINT NOT NULL DEFAULT '0' COMMENT '更新时间',
  `name` VARCHAR(18) DEFAULT '' COMMENT '姓名',
  `phone` VARCHAR(18) DEFAULT '' COMMENT '电话号码',
  `id_card` VARCHAR(18) DEFAULT '' COMMENT '身份证号码',
  `audit` INTEGER(1) DEFAULT '0' COMMENT '0：默认 1:审核通过 2:审核不通过',
  `id_card_front_url` VARCHAR(255) DEFAULT '' COMMENT '身份证号码正面',
  `id_card_back_url` VARCHAR(255) DEFAULT '' COMMENT '身份证号码背面',
  `enabled` int(1) DEFAULT '1' COMMENT '是否可见，0为不可见，1为可见',
PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='用户表' AUTO_INCREMENT=1;



CREATE TABLE IF NOT EXISTS `t_customer_consignee` (
  `id` BIGINT UNSIGNED AUTO_INCREMENT COMMENT '表主键',
  `create_time` BIGINT COMMENT '添加时间',
  `update_time` BIGINT COMMENT '更新时间',
  `enabled` INT(1) DEFAULT '1' COMMENT '是否可见，0为不可见，1为可见',
  `province` VARCHAR(255) COMMENT '省',
  `city` VARCHAR(255) COMMENT '市',
  `county` VARCHAR(255) COMMENT '区',
  `address` LONGTEXT COMMENT '收货地址',
  `address_detail` LONGTEXT COMMENT '收货详细地址',
  `contact` VARCHAR(512) COMMENT '联系人',
  `phone` VARCHAR(512) COMMENT '联系电话',
  `auto_addr` INT(1) DEFAULT '1' COMMENT '是否默认地址，0为否，1为是',
  `user_id` BIGINT UNSIGNED NOT NULL COMMENT '用户id',
  PRIMARY KEY (`id`),
  FOREIGN KEY (user_id)
  REFERENCES t_user(id)
    ON UPDATE RESTRICT ON DELETE RESTRICT
) ENGINE=INNODB DEFAULT CHARSET=utf8 COMMENT='客户收货信息表' AUTO_INCREMENT=1;

CREATE TABLE IF NOT EXISTS `t_merchant_consignee` (
  `id` BIGINT UNSIGNED AUTO_INCREMENT COMMENT '表主键',
  `create_time` BIGINT COMMENT '添加时间',
  `update_time` BIGINT COMMENT '更新时间',
  `enabled` INT(1) DEFAULT '1' COMMENT '是否可见，0为不可见，1为可见',
  `address` LONGTEXT COMMENT '收货地址',
  `contact` VARCHAR(512) COMMENT '联系人',
  `phone` VARCHAR(512) COMMENT '联系电话',
  `auto_addr` INT(1) DEFAULT '1' COMMENT '是否默认地址，0为否，1为是',
  PRIMARY KEY (`id`)
) ENGINE=INNODB DEFAULT CHARSET=utf8 COMMENT='商家收货信息表' AUTO_INCREMENT=1;



CREATE TABLE IF NOT EXISTS `t_order` (
  `id` BIGINT UNSIGNED AUTO_INCREMENT COMMENT '表主键',
  `order_no` VARCHAR(32) NOT NULL UNIQUE COMMENT '订单号,如20150806125346100001',
  `prepay_id` VARCHAR(255) COMMENT '微信预支付交易会话标识',
  `create_time` BIGINT COMMENT '添加时间',
  `update_time` BIGINT COMMENT '更新时间',
  `enabled` INT(1) DEFAULT '1' COMMENT '是否可见，0为不可见，1为可见',
  `total_fee` INT COMMENT '总价，分为单位',
  `logistics_num` VARCHAR(512) COMMENT '物流号',
  `logistics_type` VARCHAR(512) COMMENT '物流类型',
  `customer_consignee_string` LONGTEXT NOT NULL COMMENT '用户收货信息',
  `user_id` BIGINT UNSIGNED NOT NULL COMMENT '用户id',
  `status` INT DEFAULT '0' COMMENT '状态，0为待支付，1为待发货，2为待收货，3为已收货，4为退款退货中，5为交易成功，6为交易关闭,7为退款成功,8为退款关闭',
  `pay_status` INT DEFAULT '0' COMMENT '微信支付状态，1为已申请支付，2为用户取消支付，3为微信确认支付成功，4为微信确认支付失败',
  `pay_time_end` VARCHAR(512) COMMENT '微信支付完成时间',
  `wx_result` LONGTEXT COMMENT '微信回调结果',
  `transaction_id` VARCHAR(512) COMMENT '微信回调transaction_id',
  `update_status` INT DEFAULT '0' COMMENT '是否更新商户金额状态，0为没更新，1已更新',
  `postage` int DEFAULT '0' COMMENT '邮费,分为单位',
  `refund` INT DEFAULT '0' comment '是否为允许退款,0为不允许,1允许',
  `shop_id` BIGINT UNSIGNED NOT NULL COMMENT '店铺Id',
--  退货信息
  `return_address` VARCHAR(512) comment '收货地址',
  `return_name` VARCHAR(512) COMMENT '收货人',
  `return_phone` VARCHAR(20) COMMENT '收货号码',
--   申请客服
  `service_status`  INT(1) DEFAULT '0' COMMENT '是否申请客服服务，0为不申请，1为未处理,2已处理',
  `contact_phone` VARCHAR(20) COMMENT '需要与客服联系的号码',
PRIMARY KEY (`id`),
FOREIGN KEY (user_id)
REFERENCES t_user(id)
ON UPDATE RESTRICT ON DELETE RESTRICT
) ENGINE=INNODB DEFAULT CHARSET=utf8 COMMENT='订单表' AUTO_INCREMENT=1;




CREATE TABLE IF NOT EXISTS `t_customer_statistics` (
  `id` BIGINT UNSIGNED AUTO_INCREMENT COMMENT '表主键',
  `create_time` BIGINT COMMENT '添加时间',
  `update_time` BIGINT COMMENT '更新时间',
  `enabled` INT(1) DEFAULT '1' COMMENT '是否可见，0为不可见，1为可见',
  `open_id` VARCHAR(512) COMMENT '微信的openId',
  PRIMARY KEY (`id`)
) ENGINE=INNODB DEFAULT CHARSET=utf8 COMMENT='客户统计表' AUTO_INCREMENT=1;



INSERT INTO `t_category` (create_time, update_time, enabled, name, description,image_url) VALUES (1495016519592, 1495016519592, 1, '服饰鞋包', '卖好看服饰鞋包','/product/20180621/c1.png');
INSERT INTO `t_category` (create_time, update_time, enabled, name, description,image_url) VALUES (1495016519592, 1495016519592, 1, '美味零食', '卖好吃美味零食','/item/lingshi.jpg');
INSERT INTO `t_category` (create_time, update_time, enabled, name, description,image_url) VALUES (1495016519592, 1495016519592, 1, '防晒护肤', '卖好用防晒护肤','/item/fangshai.jpg');

INSERT INTO `t_product` (`id`, `create_time`, `update_time`,`name`, `image_url`, `small_image_url`,`description`,`category_id`,`title`,`image_url1`,`image_url2`) VALUES
(1,1495016519592,1495016519592,'2018亲子装夏款 韩版黄色7字棒球肥仔宽松哺乳纯棉 中袖T恤','/product/20180621/c1.png','/product/20180621/c1.png','衣服1',1,'这是一件好衣服','/product/20180621/c2.png','/product/20180621/c3.png'),
(2,1495016519592,1495016519592,'2018亲子装夏款 一家三口亲子短袖T恤儿童小猪佩琪上衣','/product/20180621/c2.png','/product/20180621/c2.png','衣服2',1,'这是一件好衣服','/product/20180621/c1.png','/product/20180621/c3.png');

INSERT INTO `t_product_detail` (`id`, `create_time`, `update_time`,`size`, `price`, `color`, `inventory`,`product_id`,`commission`,`uppest_price`) VALUES
(1,1495016519592,1495016519591,'30',1,'红色',100,1,100,10000),
(2,1495016519592,1495016519592,'31',1,'蓝色',100,1,100,10000),
(3,1495016519592,1495016519593,'30',1,'红色',100,2,100,10000),
(4,1495016519592,1495016519594,'31',1,'蓝色',100,2,100,10000);


INSERT INTO `t_user` (`id`, `create_time`, `update_time`,`open_id`) VALUES
(1,1495016519592,1495016519592,'Jieer');



DROP TABLE IF EXISTS `t_wx_payment_config`;
CREATE TABLE `t_wx_payment_config` (
  `id` BIGINT UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '表主键',
  `title` VARCHAR(255) DEFAULT NULL COMMENT '微信支付赛事标题',
  `app_id` VARCHAR(255) DEFAULT NULL COMMENT 'AppID',
  `app_secret` VARCHAR(50) DEFAULT NULL COMMENT 'AppSecret',
  `mch_id` VARCHAR(255) DEFAULT NULL COMMENT 'MCHID',
  `wechat_key` VARCHAR(255) DEFAULT NULL COMMENT 'wechat_key',
  `sslcert_path` VARCHAR(255) DEFAULT NULL COMMENT 'sslcert_path',
  `sslkey_path` VARCHAR(255) DEFAULT NULL COMMENT 'sslkey_path',
  `p12_path` VARCHAR(255) DEFAULT NULL COMMENT 'p12_path',
  `create_time` BIGINT NOT NULL DEFAULT '0' COMMENT '添加时间',
  `update_time` BIGINT NOT NULL DEFAULT '0' COMMENT '更新时间',
  `enabled` INT(1) DEFAULT '1' COMMENT '是否可用，0为不可用，1为可用',
  `audit_content` VARCHAR(255) DEFAULT NULL,
  `audit_status` INT(11) DEFAULT NULL,
  `audit_time` BIGINT(20) DEFAULT NULL,
  `auditors` VARCHAR(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='微信支付设置表' AUTO_INCREMENT=1;


DROP TABLE IF EXISTS `t_wx_refund`;
CREATE TABLE `t_wx_refund` (
  `id` BIGINT UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '表主键',
  `open_id` varchar(255) NOT NULL COMMENT '微信openid',
  `status` INT(1) DEFAULT '1' NOT NULL COMMENT '退款状态，0为申请退款失败，1为申请退款成功，2为微信确认退款成功，3为微信确认退款异常，4为微信确认退款关闭',
  `out_refund_no` VARCHAR(255) NOT NULL COMMENT '商户退款单号',
  `transaction_id` VARCHAR(255) NOT NULL UNIQUE COMMENT '支付单号',
  `create_time` BIGINT NOT NULL DEFAULT '0' COMMENT '添加时间',
  `update_time` BIGINT NOT NULL DEFAULT '0' COMMENT '更新时间',
  `enabled` INT(1) DEFAULT '1' COMMENT '是否可用，0为不可用，1为可用',
  `refund_fee` INT COMMENT '退款金额，以分为单位',
  `wx_refund_fee` INT COMMENT '微信实际退款金额，以分为单位',
  `wx_refund_id` varchar(255) COMMENT '微信退款单号',
  `wx_success_time` varchar(255) COMMENT '微信退款成功时间',
  `wx_result` LONGTEXT COMMENT '微信退款回调结果',
  `wx_result_detail` LONGTEXT COMMENT '微信退款回调结果详情',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='退款信息表' AUTO_INCREMENT=1;


INSERT INTO `t_sys_user` (`id`, `username`, `password`,`name`, `email`, `mobile`, `create_time`, `update_time`, `enabled`) VALUES
  (4, 'user', 'bc67cc00dae6622b22797ba8f7cd76bc','前端用户','lvxin@torinosrc.com','18027188617', 1495016519592, 1495016519592, 1);
INSERT INTO `t_sys_user_role` (`sys_user_id`, `sys_role_id`) VALUES
(4, 1);

#新增接口权限
INSERT INTO `t_sys_interface_permission` (`id`, `name`, `permission`, `create_time`, `update_time`, `enabled`) VALUES
(37, '开店邀请新增', 'sys:invitation:add', 1495016519592, 1495016519592, 1),
(38, '开店邀请删除', 'sys:invitation:delete', 1495016519592, 1495016519592, 1),
(39, '开店邀请修改', 'sys:invitation:update', 1495016519592, 1495016519592, 1),
(40, '开店邀请查询', 'sys:invitation:query', 1495016519592, 1495016519592, 1);

#新增管理员权限关联接口权限
INSERT INTO `t_sys_authority_interface_permission` (`sys_authority_id`, `sys_interface_permission_id`) VALUES
(3, 37),
(3, 38),
(3, 39),
(3, 40);


#新增接口权限
INSERT INTO `t_sys_interface_permission` (`id`, `name`, `permission`, `create_time`, `update_time`, `enabled`) VALUES
(41, '分成默认配置新增', 'sys:distributionpriceconfig:add', 1495016519592, 1495016519592, 1),
(42, '分成默认配置删除', 'sys:distributionpriceconfig:delete', 1495016519592, 1495016519592, 1),
(43, '分成默认配置修改', 'sys:distributionpriceconfig:update', 1495016519592, 1495016519592, 1),
(44, '分成默认配置查询', 'sys:distributionpriceconfig:query', 1495016519592, 1495016519592, 1);



#新增管理员权限关联接口权限
INSERT INTO `t_sys_authority_interface_permission` (`sys_authority_id`, `sys_interface_permission_id`) VALUES
(3, 41),
(3, 42),
(3, 43),
(3, 44);


#新增接口权限
INSERT INTO `t_sys_interface_permission` (`id`, `name`, `permission`, `create_time`, `update_time`, `enabled`) VALUES
(45, '店铺新增', 'sys:shop:add', 1495016519592, 1495016519592, 1),
(46, '店铺删除', 'sys:shop:delete', 1495016519592, 1495016519592, 1),
(47, '店铺修改', 'sys:shop:update', 1495016519592, 1495016519592, 1),
(48, '店铺查询', 'sys:shop:query', 1495016519592, 1495016519592, 1);



#新增管理员权限关联接口权限
INSERT INTO `t_sys_authority_interface_permission` (`sys_authority_id`, `sys_interface_permission_id`) VALUES
(3, 45),
(3, 46),
(3, 47),
(3, 48);

#新增接口权限
INSERT INTO `t_sys_interface_permission` (`id`, `name`, `permission`, `create_time`, `update_time`, `enabled`) VALUES
(49, '店铺商品新增', 'sys:shopproduct:add', 1495016519592, 1495016519592, 1),
(50, '店铺商品删除', 'sys:shopproduct:delete', 1495016519592, 1495016519592, 1),
(51, '店铺商品修改', 'sys:shopproduct:update', 1495016519592, 1495016519592, 1),
(52, '店铺商品查询', 'sys:shopproduct:query', 1495016519592, 1495016519592, 1),
(53, '收货地址查询', 'sys:customerconsignee:query', 1495016519592, 1495016519592, 1);

#新增管理员权限关联接口权限
INSERT INTO `t_sys_authority_interface_permission` (`sys_authority_id`, `sys_interface_permission_id`) VALUES
(3, 49),
(3, 50),
(3, 51),
(3, 52),
(3, 53);


#新增接口权限
INSERT INTO `t_sys_interface_permission` (`id`, `name`, `permission`, `create_time`, `update_time`, `enabled`) VALUES
(54, '店铺商品详情新增', 'sys:shopproductdetail:add', 1495016519592, 1495016519592, 1),
(55, '店铺商品详情删除', 'sys:shopproductdetail:delete', 1495016519592, 1495016519592, 1),
(56, '店铺商品详情修改', 'sys:shopproductdetail:update', 1495016519592, 1495016519592, 1),
(57, '店铺商品详情查询', 'sys:shopproductdetail:query', 1495016519592, 1495016519592, 1);

#新增管理员权限关联接口权限
INSERT INTO `t_sys_authority_interface_permission` (`sys_authority_id`, `sys_interface_permission_id`) VALUES
(3, 54),
(3, 55),
(3, 56),
(3, 57);


#新增接口权限
INSERT INTO `t_sys_interface_permission` (`id`, `name`, `permission`, `create_time`, `update_time`, `enabled`) VALUES
(58, '商品详情新增', 'sys:productdetail:add', 1495016519592, 1495016519592, 1),
(59, '商品详情删除', 'sys:productdetail:delete', 1495016519592, 1495016519592, 1),
(60, '商品详情修改', 'sys:productdetail:update', 1495016519592, 1495016519592, 1),
(61, '商品详情查询', 'sys:productdetail:query', 1495016519592, 1495016519592, 1);

#新增商品详情权限关联接口权限
INSERT INTO `t_sys_authority_interface_permission` (`sys_authority_id`, `sys_interface_permission_id`) VALUES
(3, 58),
(3, 59),
(3, 60),
(3, 61);

#新增商品分类接口权限
INSERT INTO `t_sys_interface_permission` (`id`, `name`, `permission`, `create_time`, `update_time`, `enabled`) VALUES
(62, '商品分类新增', 'sys:category:add', 1495016519592, 1495016519592, 1),
(63, '商品分类删除', 'sys:category:delete', 1495016519592, 1495016519592, 1),
(64, '商品分类修改', 'sys:category:update', 1495016519592, 1495016519592, 1),
(65, '商品分类查询', 'sys:category:query', 1495016519592, 1495016519592, 1);

#新增商品分类权限关联接口权限
INSERT INTO `t_sys_authority_interface_permission` (`sys_authority_id`, `sys_interface_permission_id`) VALUES
(3, 62),
(3, 63),
(3, 64),
(3, 65);







CREATE TABLE IF NOT EXISTS `t_product_detail_snapshot` (
  `id` BIGINT UNSIGNED AUTO_INCREMENT COMMENT '表主键',
  `create_time` BIGINT COMMENT '添加时间',
  `update_time` BIGINT COMMENT '更新时间',
  `enabled` INT(1) DEFAULT '1' COMMENT '是否可见，0为不可见，1为可见',
  `size` VARCHAR(255) COMMENT '尺码',
  `price` BIGINT COMMENT '价格',
  `uppest_price` BIGINT COMMENT '价格',
  `color` VARCHAR(255) COMMENT '颜色',
  `inventory` BIGINT COMMENT '库存',
  `commission` BIGINT COMMENT '佣金',
  `product_snapshot_id` BIGINT UNSIGNED NOT NULL COMMENT '商品快照id',
  `product_detail_id` BIGINT UNSIGNED NOT NULL COMMENT '商品详情id',
PRIMARY KEY (`id`),
FOREIGN KEY (product_snapshot_id)
REFERENCES t_product_snapshot(id)
ON UPDATE RESTRICT ON DELETE RESTRICT
) ENGINE=INNODB DEFAULT CHARSET=utf8 COMMENT='商品详情快照表' AUTO_INCREMENT=1;

ALTER table t_product_detail_snapshot add column team_price BIGINT COMMENT '拼团价格';

CREATE TABLE `t_shop_product_detail_snapshot` (
  `id` BIGINT UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '表主键',
  `create_time` BIGINT COMMENT '添加时间',
  `update_time` BIGINT COMMENT '更新时间',
  `enabled` INTEGER COMMENT '是否启用0：不启用，1：启用',
  `shop_product_snapshot_id` BIGINT UNSIGNED NOT NULL COMMENT '商店商品快照id',
  `product_detail_snapshot_id` BIGINT UNSIGNED NOT NULL COMMENT '商品详情快照id',
  `uppest_price` INTEGER COMMENT '市场价：最高价',
  `advise_price` INTEGER COMMENT '建议零售价：最低价格',
  `product_price` INTEGER COMMENT '用户自定义商品价格，不自定就是建议零售价',
  `shop_product_detail_id` BIGINT UNSIGNED NOT NULL COMMENT '商店商品详情id',
PRIMARY KEY (`id`),
FOREIGN KEY (shop_product_snapshot_id) REFERENCES t_shop_product_snapshot(id),
FOREIGN KEY (product_detail_snapshot_id) REFERENCES t_product_detail_snapshot(id)

) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='用户商店中的商品详情快照表' AUTO_INCREMENT=1;

CREATE TABLE IF NOT EXISTS `t_order_detail` (
  `id` BIGINT UNSIGNED AUTO_INCREMENT COMMENT '表主键',
  `create_time` BIGINT COMMENT '添加时间',
  `update_time` BIGINT COMMENT '更新时间',
  `enabled` INT(1) DEFAULT '1' COMMENT '是否可见，0为不可见，1为可见',
  `count` INT COMMENT '数量',
  `price` BIGINT COMMENT '总价',
  `order_id` BIGINT UNSIGNED COMMENT '订单id',
  `shop_product_detail_snapshot_id` BIGINT UNSIGNED  COMMENT '商店的商品详情快照Id',
  `product_detail_snapshot_id` BIGINT UNSIGNED  COMMENT '商品详情快照Id',
  `product_detail_id` BIGINT UNSIGNED NOT NULL COMMENT '商品详情id',
PRIMARY KEY (`id`),
FOREIGN KEY (order_id)
REFERENCES t_order(id)
ON UPDATE RESTRICT ON DELETE RESTRICT
) ENGINE=INNODB DEFAULT CHARSET=utf8 COMMENT='订单详情表' AUTO_INCREMENT=1;


CREATE TABLE `t_distribution_price_config` (
  `id` BIGINT UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '表主键',
  `percent_config` VARCHAR(255) unique COMMENT '分成常量： percentConfig',
  `percent_1` INTEGER COMMENT '一级分配',
  `percent_2` INTEGER COMMENT '二级分配',
  `percent_3` INTEGER COMMENT '三级分配',
  `create_time` BIGINT COMMENT '添加时间',
  `update_time` BIGINT COMMENT '更新时间',
  `enabled` INTEGER COMMENT '是否启用0：不启用，1：启用',
PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='分成默认配置表' AUTO_INCREMENT=1;


-- 商店层级中间表
CREATE TABLE `t_shop_tree` (
  `id` BIGINT UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '表主键',
  `shop_id` BIGINT UNSIGNED NOT NULL COMMENT '商店id',
  `parent_shop_id` BIGINT UNSIGNED NOT NULL COMMENT '父级商店标题id',
  `enabled` INTEGER COMMENT '是否启用0：不启用，1：启用',
  `create_time` BIGINT COMMENT '添加时间',
  `update_time` BIGINT COMMENT '更新时间',
PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='商店层级中间表' AUTO_INCREMENT=1;


-- 记录每间商店拥有的商品

CREATE TABLE `t_shop_product` (
  `id` BIGINT UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '表主键',
  `create_time` BIGINT COMMENT '添加时间',
  `update_time` BIGINT COMMENT '更新时间',
  `enabled` INTEGER COMMENT '是否启用0：不启用，1：启用',
  `product_id` BIGINT UNSIGNED NOT NULL COMMENT '商品id',
  `on_sale` integer COMMENT '是否上架中 0：否，1：是',
  `shop_id` BIGINT UNSIGNED NOT NULL COMMENT '店铺id',
  `recommend_reason` VARCHAR(255) COMMENT '推荐语，推荐原因',
PRIMARY KEY (`id`),
FOREIGN KEY (product_id) REFERENCES t_product(id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='用户商店中的商品' AUTO_INCREMENT=1;

CREATE TABLE `t_shop_product_detail` (
  `id` BIGINT UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '表主键',
  `create_time` BIGINT COMMENT '添加时间',
  `update_time` BIGINT COMMENT '更新时间',
  `enabled` INTEGER COMMENT '是否启用0：不启用，1：启用',
  `shop_product_id` BIGINT UNSIGNED NOT NULL COMMENT '商店商品id',
  `product_detail_id` BIGINT UNSIGNED NOT NULL COMMENT '商品详情id',
  `uppest_price` INTEGER COMMENT '市场价：最高价',
  `advise_price` INTEGER COMMENT '建议零售价：最低价格',
  `product_price` INTEGER COMMENT '用户自定义商品价格，不自定就是建议零售价',
PRIMARY KEY (`id`),
FOREIGN KEY (shop_product_id) REFERENCES t_shop_product(id),
FOREIGN KEY (product_detail_id) REFERENCES t_product_detail(id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='用户商店中的商品详情' AUTO_INCREMENT=1;

CREATE TABLE IF NOT EXISTS `t_shopping_cart` (
  `id` BIGINT UNSIGNED AUTO_INCREMENT COMMENT '表主键',
  `create_time` BIGINT COMMENT '添加时间',
  `update_time` BIGINT COMMENT '更新时间',
  `enabled` INT(1) DEFAULT '1' COMMENT '是否可见，0为不可见，1为可见',
  `user_id` BIGINT UNSIGNED UNIQUE NOT NULL COMMENT '用户id',
PRIMARY KEY (`id`),
FOREIGN KEY (user_id)
REFERENCES t_user(id)
ON UPDATE RESTRICT ON DELETE RESTRICT
) ENGINE=INNODB DEFAULT CHARSET=utf8 COMMENT='购物车表' AUTO_INCREMENT=1;

CREATE TABLE IF NOT EXISTS `t_shopping_cart_detail` (
  `id` BIGINT UNSIGNED AUTO_INCREMENT COMMENT '表主键',
  `create_time` BIGINT COMMENT '添加时间',
  `update_time` BIGINT COMMENT '更新时间',
  `enabled` INT(1) DEFAULT '1' COMMENT '是否可见，0为不可见，1为可见',
  `count` INT COMMENT '数量',
  `shopping_cart_id` BIGINT UNSIGNED COMMENT '购物车id',
  `shop_product_detail_id` BIGINT UNSIGNED COMMENT '商店商品详情Id',
  `product_detail_id` BIGINT UNSIGNED NOT NULL COMMENT '商品详情Id',
  `shop_id` BIGINT UNSIGNED NOT NULL COMMENT '商店id',
PRIMARY KEY (`id`),
FOREIGN KEY (shopping_cart_id)
REFERENCES t_shopping_cart(id)
ON UPDATE RESTRICT ON DELETE RESTRICT
) ENGINE=INNODB DEFAULT CHARSET=utf8 COMMENT='购物车详情表' AUTO_INCREMENT=1;

CREATE TABLE `t_shop_account` (
  `id` BIGINT UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '表主键',
  `create_time` BIGINT COMMENT '添加时间',
  `update_time` BIGINT COMMENT '更新时间',
  `enabled` INTEGER COMMENT '是否启用0：不启用，1：启用',
  `total_amount` BIGINT DEFAULT 0 COMMENT '总收入',
  `money` BIGINT DEFAULT 0 COMMENT '账户现有金额',
  `shop_id` BIGINT UNSIGNED NOT NULL COMMENT '拥有这个账户商店id',
PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='销售账户' AUTO_INCREMENT=1;


CREATE TABLE `t_shop_account_detail` (
  `id` BIGINT UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '表主键',
  `create_time` BIGINT COMMENT '添加时间',
  `update_time` BIGINT COMMENT '更新时间',
  `enabled` INTEGER COMMENT '是否启用0：不启用，1：启用',
  `product_name` VARCHAR(35) COMMENT '商品名称',
  `income_source` INTEGER COMMENT '收入渠道：0：分销，1.自营',
  `income_amount` BIGINT COMMENT '当次收入金额',
  `shop_id` BIGINT COMMENT '下级销售的shop_id',
  `sale_count` INTEGER COMMENT '销售件数',
  `shop_account_id` BIGINT UNSIGNED NOT NULL COMMENT '商店account',
  `shop_product_snapshot_id` BIGINT COMMENT '商店商品快照id',
  `shop_product_detail_snapshot_id` BIGINT COMMENT '商店商品详情快照Id',
  `product_detail_snapshot_id` BIGINT COMMENT '商品详情快照Id',
  `order_detail_id` BIGINT UNSIGNED NOT NULL COMMENT '订单详情Id',
  `order_id` BIGINT UNSIGNED NOT NULL COMMENT '订单Id',
PRIMARY KEY (`id`),
FOREIGN KEY (shop_account_id) REFERENCES t_shop_account(id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='销售账户明细' AUTO_INCREMENT=1;

alter table t_shop_account_detail add `record_status` INTEGER COMMENT '0:销售收入 1:退款减账';

CREATE TABLE `t_invitation` (
  `id` BIGINT UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '表主键',
  `create_time` BIGINT COMMENT '添加时间',
  `update_time` BIGINT COMMENT '更新时间',
  `enabled` INTEGER COMMENT '是否启用0：不启用，1：启用',
  `shop_id` BIGINT UNSIGNED NOT NULL COMMENT '商店id',
  `user_id` BIGINT UNSIGNED NOT NULL COMMENT '当前扫码的user_id',
PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='邀请表' AUTO_INCREMENT=1;




-- INSERT INTO `t_order` (`id`,`order_no`, `prepay_id`,`create_time`, `update_time`,`logistics_num`, `status`, `pay_status`,`customer_consignee_string`,`user_id`) VALUES
-- (1,'20150806125346100001','11',1495016519592,1495016519592,'to123',0,1,'收货信息',1);
--
-- INSERT INTO `t_order_detail` (`id`, `create_time`, `update_time`,`count`, `price`,`order_id`,`product_detail_id`) VALUES
-- (1,1495016519592,1495016519592,10,350,1,1),
-- (2,1495016519592,1495016519592,10,350,1,2);



--   INSERT INTO `t_shop_product` (`id`, `create_time`, `update_time`,`product_id`,`enabled`,`shop_id`) VALUES
--  (1,1495016519592,1495016519592,1,1,1);
--
--   INSERT INTO `t_shop_product_detail` (`id`, `create_time`, `update_time`,`shop_product_id`,`enabled`,`product_detail_id`,`advise_price`,`uppest_price`,`product_price`) VALUES
--  (1,1495016519592,1495016519592,1,1,1,1,10000,1);

--  INSERT INTO `t_shopping_cart` (`id`, `create_time`, `update_time`,`user_id`) VALUES
--  (1,1495016519592,1495016519592,1);
--
--  INSERT INTO `t_shopping_cart_detail` (`id`, `create_time`, `update_time`,`count`,`shopping_cart_id`,`shop_product_detail_id`,`product_detail_id`,`shop_id`) VALUES
--  (1,1495016519592,1495016519592,5,1,1,1,1);


insert into t_distribution_price_config (percent_config, percent_1, percent_2, percent_3, create_time, update_time, enabled) VALUES
  ('default',30,30,40,0,0,1);

INSERT INTO `t_shop_account`(create_time, update_time, enabled, total_amount, shop_id) VALUES ('1495016519592', '1495016519592', '1', '0', '1');

INSERT INTO `t_shop_account`(create_time, update_time, enabled, total_amount, shop_id) VALUES ('1495016519592', '1495016519592', '1', '0', '2');

INSERT INTO `t_shop_account`(create_time, update_time, enabled, total_amount, shop_id) VALUES ('1495016519592', '1495016519592', '1', '0', '3');

INSERT INTO `t_shop_tree` (shop_id, parent_shop_id, enabled, create_time, update_time) VALUES (1, 0, 1, '1495016519592', '1495016519592');

insert into `t_shop_tree` (shop_id, parent_shop_id, enabled, create_time, update_time) values (2,1,1,0,0);

INSERT INTO `t_shop_tree` (shop_id, parent_shop_id, enabled, create_time, update_time) VALUES (3, 2, 1, '1495016519592', '1495016519592');



INSERT INTO `t_shop` (`id`, `create_time`, `update_time`,`user_id`,`enabled`,`percent_1`,`percent_2`,`percent_3`,`title`,`sub_title`,`description`) VALUES
(1,1495016519592,1495016519592,1,1,20,30,50,'我的小店铺','我的小店铺的二级标题','快快购物啦');

INSERT INTO `t_shop` (`id`, `create_time`, `update_time`,`user_id`,`enabled`,`percent_1`,`percent_2`,`percent_3`,`title`,`sub_title`,`description`) VALUES
(2,1495016519592,1495016519592,1,1,20,30,50,'我的小店铺（二级）','我的小店铺的二级标题（二级）','快快购物啦');

INSERT INTO `t_shop` (`id`, `create_time`, `update_time`,`user_id`,`enabled`,`percent_1`,`percent_2`,`percent_3`,`title`,`sub_title`,`description`) VALUES
(3,1495016519592,1495016519592,1,1,20,30,50,'我的小店铺（三级）','我的小店铺的三级级标题','快快购物啦');

DROP TABLE IF EXISTS `t_withdraw_money`;

CREATE TABLE `t_withdraw_money` (
  `id` BIGINT UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '表主键',
  `create_time` BIGINT COMMENT '添加时间',
  `update_time` BIGINT COMMENT '更新时间',
  `item` BIGINT COMMENT '0.提现',
  `amount` INTEGER COMMENT '提现金额',
  `shop_id` BIGINT UNSIGNED NOT NULL COMMENT '商店id',
  `user_id` BIGINT UNSIGNED NOT NULL COMMENT '用户id',
  `status` INTEGER COMMENT '0：处理中 1:提现成功 2:提现失败',
  `enabled` INTEGER COMMENT '是否启用0：不启用，1：启用',
PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='提现流水' AUTO_INCREMENT=1;

#新增流水账接口权限
INSERT INTO `t_sys_interface_permission` (`id`, `name`, `permission`, `create_time`, `update_time`, `enabled`) VALUES
(66, '提现流水新增', 'sys:withdrawmoney:add', 1495016519592, 1495016519592, 1),
(67, '提现流水删除', 'sys:withdrawmoney:delete', 1495016519592, 1495016519592, 1),
(68, '提现流水修改', 'sys:withdrawmoney:update', 1495016519592, 1495016519592, 1),
(69, '提现流水查询', 'sys:withdrawmoney:query', 1495016519592, 1495016519592, 1);

#新增流水账权限关联接口权限
INSERT INTO `t_sys_authority_interface_permission` (`sys_authority_id`, `sys_interface_permission_id`) VALUES
(3, 66),
(3, 67),
(3, 68),
(3, 69);

#新增商店账户接口权限
INSERT INTO `t_sys_interface_permission` (`id`, `name`, `permission`, `create_time`, `update_time`, `enabled`) VALUES
(70, '商店账户新增', 'sys:shopaccount:add', 1495016519592, 1495016519592, 1),
(71, '商店账户删除', 'sys:shopaccount:delete', 1495016519592, 1495016519592, 1),
(72, '商店账户修改', 'sys:shopaccount:update', 1495016519592, 1495016519592, 1),
(73, '商店账户查询', 'sys:shopaccount:query', 1495016519592, 1495016519592, 1),
(74, '用户图片上传', 'sys:users:upload', 1495016519592, 1495016519592, 1);

#新增商店账户权限关联接口权限
INSERT INTO `t_sys_authority_interface_permission` (`sys_authority_id`, `sys_interface_permission_id`) VALUES
(3, 70),
(3, 71),
(3, 72),
(3, 73),
(3, 74);


DROP TABLE IF EXISTS t_access_token;
CREATE TABLE `t_access_token` (
  `id` BIGINT UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '表主键',
  `create_time` BIGINT COMMENT '添加时间',
  `update_time` BIGINT COMMENT '更新时间',
  `enabled` INTEGER COMMENT '是否启用0：不启用，1：启用',
  `name` VARCHAR(20) COMMENT '名字',
  `value` VARCHAR(1024) COMMENT 'token值',
  `status` INTEGER DEFAULT 0 COMMENT  '是否通过定时器获取，0不是，1是',
PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='access_token表' AUTO_INCREMENT=1;

INSERT INTO `t_access_token` (`id`, `create_time`,`update_time`,`enabled`,`name`,`value`) VALUES
(1,1495016519592, 1495016519592,1,'ACCESS_TOKEN','ACCESS_TOKEN');

DROP TABLE IF EXISTS t_feedback;
CREATE TABLE `t_feedback`(
  `id` BIGINT UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '表主键',
  `create_time` BIGINT COMMENT '添加时间',
  `update_time` BIGINT COMMENT '更新时间',
  `enabled` INTEGER COMMENT '是否启用0：不启用，1：启用',
  `user_id` BIGINT UNSIGNED NOT NULL COMMENT '用户user_id',
  `content` LONGTEXT COMMENT '反馈的内容',
  `email` VARCHAR(20) COMMENT '反馈用的email',
  `image_url` LONGTEXT COMMENT '图片',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='用户反馈表' AUTO_INCREMENT=1;

# 更改content字段能存表情
ALTER TABLE t_feedback MODIFY COLUMN content LONGTEXT CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;

#新增反馈接口权限
INSERT INTO `t_sys_interface_permission` (`id`, `name`, `permission`, `create_time`, `update_time`, `enabled`) VALUES
  (75, '反馈新增', 'sys:feedback:add', 1495016519592, 1495016519592, 1),
  (76, '反馈删除', 'sys:feedback:delete', 1495016519592, 1495016519592, 1),
  (77, '反馈修改', 'sys:feedback:update', 1495016519592, 1495016519592, 1),
  (78, '反馈查询', 'sys:feedback:query', 1495016519592, 1495016519592, 1),
  (79, '反馈图片上传', 'sys:feedback:upload', 1495016519592, 1495016519592, 1);

INSERT INTO `t_sys_authority_interface_permission` (`sys_authority_id`, `sys_interface_permission_id`) VALUES
  (3, 75),
  (3, 76),
  (3, 77),
  (3, 78),
  (3, 79);

# CREATE OR REPLACE VIEW v_product_category_table AS
#   select tp.id,
#     MAX(IFNULL(s.product_price,p.price)) as max_price,
#     MIN(IFNULL(s.product_price,p.price)) as min_price,
#     max(p.commission) as max_commission,
#     min(p.commission) as min_commission,
#     tp.update_time,
#     tp.create_time,
#     tp.category_id,tp.name,tp.enabled
#   from t_product tp
#     left join t_product_detail p  on tp.id =p.product_id left join
#     t_shop_product_detail s on p.id= s.product_detail_id group by tp.id;

CREATE OR REPLACE VIEW v_product_category_table AS
  select tp.id,
    CONCAT(GROUP_CONCAT(distinct iptp.index_product_type_id),",") as index_product_type_id,
    MAX(p.price) as max_price,
    MIN(p.price) as min_price,
    max(p.commission) as max_commission,
    min(p.commission) as min_commission,
    tp.update_time,
    tp.create_time,
    tp.type,
    tp.category_id,tp.name,tp.enabled
  from t_product tp
    left join t_product_detail p  on tp.id =p.product_id
    left join t_index_product_type_product iptp on iptp.product_id=tp.id
  group by tp.id;


INSERT INTO `t_sys_interface_permission` (`id`, `name`, `permission`, `create_time`, `update_time`, `enabled`) VALUES
  (80, '客户收货地址查询', 'sys:customerconsignee:query', 1495016519592, 1495016519592, 1),
  (81, '商家收货地址查询', 'sys:merchantconsignee:query', 1495016519592, 1495016519592, 1),
  (82, '商品新增', 'sys:product:add', 1495016519592, 1495016519592, 1),
  (83, '商品删除', 'sys:product:delete', 1495016519592, 1495016519592, 1),
  (84, '商品修改', 'sys:product:update', 1495016519592, 1495016519592, 1),
  (85, '商品图片上传', 'sys:product:upload', 1495016519592, 1495016519592, 1),
#   默认后台所有角色拥有的权限
  (86, '商品详情新增', 'sys:productdetail:add', 1495016519592, 1495016519592, 1),
  (87, '商品详情删除', 'sys:productdetail:delete', 1495016519592, 1495016519592, 1),
  (88, '商品详情修改', 'sys:productdetail:update', 1495016519592, 1495016519592, 1),
  (89, '商品详情查询', 'sys:productdetail:query', 1495016519592, 1495016519592, 1);

 INSERT INTO `t_sys_authority_interface_permission` (`sys_authority_id`, `sys_interface_permission_id`) VALUES
  (3, 80),
  (3, 81),
  (3, 82),
  (3, 83),
  (3, 84),
  (3, 85),
  (3, 86),
  (3, 87),
  (3, 88),
  (3, 89);

 INSERT INTO `t_wx_payment_config` (`id`, `app_id`,`app_secret`,`wechat_key`, `mch_id`,`sslcert_path`, `create_time`,`update_time`,`enabled`) VALUES
  (1,'wxf3fd67da75d45f08','658ffa10a095ea244ba25d5902a0bc8f','develop8888888888888888888888888','1482420382','', 1495016519592, 1495016519592,1);

 INSERT INTO `t_merchant_consignee` (`id`,`create_time`,`update_time`,`enabled`,`address`,`contact`,`phone`) VALUES(1,1495016519592,1495016519592,1,'广州','','');

# liori 20181120
DROP TABLE IF EXISTS t_shop_visit_record;
CREATE TABLE `t_shop_visit_record`(
  `id` BIGINT UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '表主键',
  `create_time` BIGINT COMMENT '添加时间',
  `update_time` BIGINT COMMENT '更新时间',
  `enabled` INT COMMENT '是否启用0：不启用，1：启用',
  `user_id` BIGINT UNSIGNED NOT NULL COMMENT '用户user_id',
  `shop_id` BIGINT UNSIGNED NOT NULL COMMENT '访问的店铺Id',

  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='店铺访问记录表' AUTO_INCREMENT=1;

DROP TABLE IF EXISTS `t_banner`;
CREATE TABLE IF NOT EXISTS `t_banner` (
  `id` BIGINT UNSIGNED AUTO_INCREMENT COMMENT '表主键',
  `create_time` BIGINT DEFAULT 0 COMMENT '添加时间',
  `update_time` BIGINT DEFAULT 0 COMMENT '更新时间',
  `enabled` INT(1) DEFAULT '1' COMMENT '是否可见，0为不可见，1为可见',
  `title` VARCHAR(255)  COMMENT '主题',
  `sub_title` VARCHAR(512)  COMMENT '副标题',
  `content` LONGTEXT COMMENT '内容',
  `image` VARCHAR(512) COMMENT '图片',
  `url` LONGTEXT COMMENT '链接',
  `weight` INT COMMENT '排序，从小到大',
  PRIMARY KEY (`id`)
) ENGINE=INNODB DEFAULT CHARSET=utf8 COMMENT='轮播图' AUTO_INCREMENT=1;

# 新增菜单管理权限 店铺、用户审核
INSERT INTO `t_sys_menu_permission` (`id`, `parent_id`, `name`, `description`, `create_time`, `update_time`, `enabled`) VALUES
  (7, 7, '店铺管理', '店铺管理', 1495016519592, 1495016519592, 1),
  (8, 8, '认证审核', '认证审核', 1495016519592, 1495016519592, 1),
  (9, 9, '提现审核', '提现审核', 1495016519592, 1495016519592, 1);

#新增管理员权限关联菜单权限
INSERT INTO `t_sys_authority_menu_permission` (`sys_authority_id`, `sys_menu_permission_id`) VALUES
  (3, 7),
  (3, 8),
  (3, 9);

# 疑似重复的 interface_permission
# INSERT INTO `t_sys_interface_permission` (`id`, `name`, `permission`, `create_time`, `update_time`, `enabled`) VALUES
#   (101, '店铺访问记录新增', 'sys:shopvisitrecord:add', 1495016519592, 1495016519592, 1),
#   (102, '店铺访问记录删除', 'sys:shopvisitrecord:delete', 1495016519592, 1495016519592, 1),
#   (103, '店铺访问记录修改', 'sys:shopvisitrecord:update', 1495016519592, 1495016519592, 1),
#   (104, '店铺访问记录查询', 'sys:shopvisitrecord:query', 1495016519592, 1495016519592, 1);
#
# INSERT INTO `t_sys_authority_interface_permission` (`sys_authority_id`, `sys_interface_permission_id`) VALUES
#   (3, 91),
#   (3, 92),
#   (3, 93),
#   (3, 94);

INSERT INTO `t_sys_interface_permission` (`id`, `name`, `permission`, `create_time`, `update_time`, `enabled`) VALUES
  (103, '店铺访问记录新增', 'sys:shopvisitrecord:add', 1495016519592, 1495016519592, 1),
  (104, '店铺访问记录删除', 'sys:shopvisitrecord:delete', 1495016519592, 1495016519592, 1),
  (105, '店铺访问记录修改', 'sys:shopvisitrecord:update', 1495016519592, 1495016519592, 1),
  (106, '店铺访问记录查询', 'sys:shopvisitrecord:query', 1495016519592, 1495016519592, 1);

INSERT INTO `t_sys_authority_interface_permission` (`sys_authority_id`, `sys_interface_permission_id`) VALUES
  (3, 103),
  (3, 104),
  (3, 105),
  (3, 106);

INSERT INTO `t_sys_interface_permission` (`id`, `name`, `permission`, `create_time`, `update_time`, `enabled`) VALUES
  (107, '轮播图新增', 'sys:banner:add', 1495016519592, 1495016519592, 1),
  (108, '轮播图删除', 'sys:banner:delete', 1495016519592, 1495016519592, 1),
  (109, '轮播图修改', 'sys:banner:update', 1495016519592, 1495016519592, 1),
  (110, '轮播图查询', 'sys:banner:query', 1495016519592, 1495016519592, 1);

INSERT INTO `t_sys_authority_interface_permission` (`sys_authority_id`, `sys_interface_permission_id`) VALUES
  (3, 107),
  (3, 108),
  (3, 109),
  (3, 110);



# 新增菜单管理权限 商家店铺管理、会员管理
INSERT INTO `t_sys_menu_permission` (`id`, `parent_id`, `name`, `description`, `create_time`, `update_time`, `enabled`) VALUES
  (12, 12, '商家店铺管理', '商家店铺管理', 1495016519592, 1495016519592, 1),
  (13, 19, '会员设置', '会员设置', 1495016519592, 1495016519592, 1),
  (11, 5, '分类管理', '分类管理', 1495016519592, 1495016519592, 1),
  (15, 6, '退款维权', '退款维权', 1495016519592, 1495016519592, 1);

# 新增管理员权限关联菜单权限 商家店铺管理、会员管理
INSERT INTO `t_sys_authority_menu_permission` (`sys_authority_id`, `sys_menu_permission_id`) VALUES
  (3, 12),
  (3, 13),
  (3, 11),
  (3, 15);



