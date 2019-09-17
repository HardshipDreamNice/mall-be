alter table t_product add `type` INT DEFAULT '0' COMMENT '0为普通商品，1为拼团，2为兼有';
alter table t_product add `expired_time` VARCHAR(20) COMMENT '过期时间';
alter table t_product add `count` INT DEFAULT '0' COMMENT '已团数';
alter table t_product add `member_count` INT COMMENT '成团数';
alter table t_product add `weight` INTEGER default 0 COMMENT '权重';
alter table t_product add `team_image_url` longtext COMMENT '拼团图片路径';
# alter table t_product add `expired_time_long` BIGINT COMMENT '过期时间(毫秒)';
# alter table t_product add `team_time` BIGINT COMMENT '开团有效时间';

alter table t_category add `weight` INTEGER default 0 COMMENT '权重';


drop table if exists t_product_detail_price;

CREATE TABLE IF NOT EXISTS `t_product_detail_price` (
  `id` BIGINT UNSIGNED AUTO_INCREMENT COMMENT '表主键',
  `create_time` BIGINT COMMENT '添加时间',
  `update_time` BIGINT COMMENT '更新时间',
  `enabled` INT(1) DEFAULT '1' COMMENT '是否可见，0为不可见，1为可见',
  `price` BIGINT COMMENT '原价格',
  `team_price` BIGINT COMMENT '拼团价格',
  `type` INT DEFAULT '0' COMMENT '会员等级',
  `product_detail_id` BIGINT UNSIGNED NOT NULL COMMENT '商品详情id',
  PRIMARY KEY (`id`)
) ENGINE=INNODB DEFAULT CHARSET=utf8 COMMENT='商品详情价格表' AUTO_INCREMENT=1;


alter table t_product_snapshot add `type` INT DEFAULT '0' COMMENT '0为普通商品，1为拼团，2为兼有';
alter table t_product_snapshot add `expired_time` VARCHAR(20) COMMENT '过期时间';
alter table t_product_snapshot add `count` INT DEFAULT '0' COMMENT '已团数';
alter table t_product_snapshot add `member_count` INT COMMENT '成团数';


drop table if exists t_team;

CREATE TABLE IF NOT EXISTS `t_team` (
  `id` BIGINT UNSIGNED AUTO_INCREMENT COMMENT '表主键',
  `create_time` BIGINT COMMENT '添加时间',
  `update_time` BIGINT COMMENT '更新时间',
  `enabled` INT(1) DEFAULT '1' COMMENT '是否可见，0为不可见，1为可见',
  `product_snapshot_id` BIGINT UNSIGNED COMMENT '商品快照id',
  `product_id` BIGINT UNSIGNED COMMENT '商品id',
  `expired_time` VARCHAR(20) COMMENT '过期时间，过期取消拼团退款',
  `product_name` VARCHAR(512)  COMMENT '商品名字',
  `count` INT COMMENT '拼团人数',
  PRIMARY KEY (`id`)
) ENGINE=INNODB DEFAULT CHARSET=utf8 COMMENT='拼团表' AUTO_INCREMENT=1;


drop table if exists t_team_user;

CREATE TABLE IF NOT EXISTS `t_team_user` (
  `id` BIGINT UNSIGNED AUTO_INCREMENT COMMENT '表主键',
  `create_time` BIGINT COMMENT '添加时间',
  `update_time` BIGINT COMMENT '更新时间',
  `enabled` INT(1) DEFAULT '1' COMMENT '是否可见，0为不可见，1为可见',
  `user_id` BIGINT UNSIGNED NOT NULL COMMENT '用户id',
  `team_id` BIGINT UNSIGNED NOT NULL COMMENT 'team id',
  `type` INT(1) DEFAULT '0' COMMENT '是否团长，0为不是，1为是',
  PRIMARY KEY (`id`)
) ENGINE=INNODB DEFAULT CHARSET=utf8 COMMENT='拼团成员表' AUTO_INCREMENT=1;

alter table t_order add `team_id` BIGINT default '0' COMMENT '拼团id';
alter table t_order add `order_type` INTEGER  COMMENT '订单类型';

#新增接口权限
INSERT INTO `t_sys_interface_permission` (`id`, `name`, `permission`, `create_time`, `update_time`, `enabled`) VALUES
  (91, '拼团表新增', 'sys:team:add', 1495016519592, 1495016519592, 1),
  (92, '拼团表删除', 'sys:team:delete', 1495016519592, 1495016519592, 1),
  (93, '拼团表修改', 'sys:team:update', 1495016519592, 1495016519592, 1),
  (94, '拼团表查询', 'sys:team:query', 1495016519592, 1495016519592, 1),
  (95, '拼团成员表新增', 'sys:teamuser:add', 1495016519592, 1495016519592, 1),
  (96, '拼团成员表删除', 'sys:teamuser:delete', 1495016519592, 1495016519592, 1),
  (97, '拼团成员表修改', 'sys:teamuser:update', 1495016519592, 1495016519592, 1),
  (98, '拼团成员表查询', 'sys:teamuser:query', 1495016519592, 1495016519592, 1);


#新增管理员权限关联接口权限
INSERT INTO `t_sys_authority_interface_permission` (`sys_authority_id`, `sys_interface_permission_id`) VALUES
  (3, 91),
  (3, 92),
  (3, 93),
  (3, 94),
  (3, 95),
  (3, 96),
  (3, 97),
  (3, 98);

#新增接口权限
INSERT INTO `t_sys_interface_permission` (`id`, `name`, `permission`, `create_time`, `update_time`, `enabled`) VALUES
  (99, '商品快照新增', 'sys:productsnapshot:add', 1495016519592, 1495016519592, 1),
  (100, '商品快照删除', 'sys:productsnapshot:delete', 1495016519592, 1495016519592, 1),
  (101, '商品快照修改', 'sys:productsnapshot:update', 1495016519592, 1495016519592, 1),
  (102, '商品快照查询', 'sys:productsnapshot:query', 1495016519592, 1495016519592, 1);


#新增管理员权限关联接口权限
INSERT INTO `t_sys_authority_interface_permission` (`sys_authority_id`, `sys_interface_permission_id`) VALUES
  (3, 99),
  (3, 100),
  (3, 101),
  (3, 102);

# alter table t_product_detail_snapshot  add `weight` INTEGER default 0 COMMENT '权重';


drop table if exists t_global_config;

CREATE TABLE `t_global_config` (
  `id` BIGINT UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '表主键',
  `create_time` BIGINT DEFAULT 0 COMMENT '添加时间',
  `update_time` BIGINT DEFAULT 0 COMMENT '更新时间',
  `enabled` INTEGER COMMENT '是否启用0：不启用，1：启用',
  `key` VARCHAR(256) COMMENT 'key',
  `value` VARCHAR(256) COMMENT 'value',
  `description` VARCHAR(256) COMMENT '描述',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='信息配置表' AUTO_INCREMENT=1;


INSERT INTO `t_global_config` (`id`, `key`,`value`, `description`, `create_time`, `update_time`, `enabled`) VALUES
  (1, 'teamTime', '86400000', '成团时间(毫秒)', 1495016519592, 1495016519592, 1);