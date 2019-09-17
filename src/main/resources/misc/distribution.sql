--用户开店
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


--商店层级中间表
CREATE TABLE `t_shop_tree` (
  `id` BIGINT UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '表主键',
  `shop_id` BIGINT UNSIGNED NOT NULL COMMENT '商店id',
  `parent_shop_id` BIGINT UNSIGNED NOT NULL COMMENT '父级商店标题id',
  `enabled` INTEGER COMMENT '是否启用0：不启用，1：启用',
  `create_time` BIGINT COMMENT '添加时间',
  `update_time` BIGINT COMMENT '更新时间',
PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='商店层级中间表' AUTO_INCREMENT=1;


--记录每间商店拥有的商品

CREATE TABLE `t_shop_product` (
  `id` BIGINT UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '表主键',
  `create_time` BIGINT COMMENT '添加时间',
  `update_time` BIGINT COMMENT '更新时间',
  `enabled` INTEGER COMMENT '是否启用0：不启用，1：启用',
  `product_id` BIGINT UNSIGNED NOT NULL COMMENT '商品id',
  `on_sale` integer COMMENT '是否上架中 0：否，1：是',
  `shop_id` BIGINT UNSIGNED NOT NULL COMMENT '店铺id',
PRIMARY KEY (`id`),
FOREIGN KEY (product_id) REFERENCES t_product(id),
FOREIGN KEY (shop_id) REFERENCES t_shop(id)
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


CREATE TABLE `t_shop_account` (
  `id` BIGINT UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '表主键',
  `create_time` BIGINT COMMENT '添加时间',
  `update_time` BIGINT COMMENT '更新时间',
  `enabled` INTEGER COMMENT '是否启用0：不启用，1：启用',
  `total_amount` BIGINT COMMENT '总收入',
  `shop_id` BIGINT UNSIGNED NOT NULL COMMENT '拥有这个账户商店id',
PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='销售账户' AUTO_INCREMENT=1;


CREATE TABLE `t_shop_account_detail` (
  `id` BIGINT UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '表主键',
  `create_time` BIGINT COMMENT '添加时间',
  `update_time` BIGINT COMMENT '更新时间',
  `enabled` INTEGER COMMENT '是否启用0：不启用，1：启用',
  `product_name` VARCHAR(10) COMMENT '商品名称',
  `income_source` INTEGER COMMENT '收入渠道：0：分销，1.自营',
  `income_amount` BIGINT COMMENT '当次收入金额',
  `shop_id` BIGINT COMMENT '下级销售的shop_id',
  `sale_count` INTEGER COMMENT '销售件数',
  `shop_account_id` BIGINT UNSIGNED NOT NULL COMMENT '商店account',
  `shop_product_snapshot_id` BIGINT UNSIGNED NOT NULL COMMENT '商店商品快照id',
  `shop_product_detail_snapshot_id` BIGINT UNSIGNED NOT NULL COMMENT '商店商品详情快照Id',
  `order_detail_id` BIGINT UNSIGNED NOT NULL COMMENT '订单详情Id',
  `order_id` BIGINT UNSIGNED NOT NULL COMMENT '订单Id',
PRIMARY KEY (`id`),
FOREIGN KEY (shop_account_id) REFERENCES t_shop_account(id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='销售账户明细' AUTO_INCREMENT=1;

CREATE TABLE `t_invitation` (
  `id` BIGINT UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '表主键',
  `create_time` BIGINT COMMENT '添加时间',
  `update_time` BIGINT COMMENT '更新时间',
  `enabled` INTEGER COMMENT '是否启用0：不启用，1：启用',
  `shop_id` BIGINT UNSIGNED NOT NULL COMMENT '商店id',
  `user_id` BIGINT UNSIGNED NOT NULL COMMENT '当前扫码的user_id',
PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='邀请表' AUTO_INCREMENT=1;



CREATE TABLE `t_shop_product_snapshot` (
  `id` BIGINT UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '表主键',
  `create_time` BIGINT COMMENT '添加时间',
  `update_time` BIGINT COMMENT '更新时间',
  `enabled` INTEGER COMMENT '是否启用0：不启用，1：启用',
  `product_snapshot_id` BIGINT UNSIGNED NOT NULL COMMENT '商品快照id',
  `on_sale` integer COMMENT '是否上架中 0：否，1：是',
  `shop_id` BIGINT UNSIGNED NOT NULL COMMENT '店铺id',
PRIMARY KEY (`id`),
FOREIGN KEY (product_snapshot_id) REFERENCES t_product_snapshot(id),
FOREIGN KEY (shop_id) REFERENCES t_shop(id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='用户商店中的商品快照表' AUTO_INCREMENT=1;

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
PRIMARY KEY (`id`),
FOREIGN KEY (shop_product_snapshot_id) REFERENCES t_shop_product_snapshot(id),
FOREIGN KEY (product_detail_snapshot_id) REFERENCES t_product_detail_snapshot(id)

) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='用户商店中的商品详情快照表' AUTO_INCREMENT=1;




