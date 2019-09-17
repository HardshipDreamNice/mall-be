
INSERT INTO `t_sys_interface_permission` (`id`, `name`, `permission`, `create_time`, `update_time`, `enabled`) VALUES
(183, '拼团配置新增', 'sys:globalconfig:add', 1495016519592, 1495016519592, 1),
(184, '拼团配置删除', 'sys:globalconfig:delete', 1495016519592, 1495016519592, 1),
(185, '拼团配置修改', 'sys:globalconfig:update', 1495016519592, 1495016519592, 1),
(186, '拼团配置查询', 'sys:globalconfig:query', 1495016519592, 1495016519592, 1);
# 新增其它设置详情权限关联接口权限
INSERT INTO `t_sys_authority_interface_permission` (`sys_authority_id`, `sys_interface_permission_id`) VALUES
(3,183),
(3,184),
(3,185),
(3,196);

# 新增分成配置、其它设置
INSERT INTO `t_sys_menu_permission` (`id`, `parent_id`, `name`, `description`, `create_time`, `update_time`, `enabled`) VALUES
(19, 19, '分成配置', '分成配置', 1495016519592, 1495016519592, 1),
(20, 19, '其它配置', '其它配置', 1495016519592, 1495016519592, 1);

# 新增分成配置、其它设置
INSERT INTO `t_sys_authority_menu_permission` (`sys_authority_id`, `sys_menu_permission_id`) VALUES
(3, 19),
(3, 20);


INSERT INTO `t_sys_interface_permission` (`id`, `name`, `permission`, `create_time`, `update_time`, `enabled`) VALUES
(187, '退款申请新增', 'sys:wxrefund:add', 1495016519592, 1495016519592, 1),
(188, '退款申请删除', 'sys:wxrefund:delete', 1495016519592, 1495016519592, 1),
(189, '退款申请修改', 'sys:wxrefund:update', 1495016519592, 1495016519592, 1),
(190, '退款申请查询', 'sys:wxrefund:query', 1495016519592, 1495016519592, 1);

# 新增商品详情权限关联接口权限
INSERT INTO `t_sys_authority_interface_permission` (`sys_authority_id`, `sys_interface_permission_id`) VALUES
(3,187),
(3,188),
(3,189),
(3,190);


# 新增分成配置、其它设置
INSERT INTO `t_sys_menu_permission` (`id`, `parent_id`, `name`, `description`, `create_time`, `update_time`, `enabled`) VALUES
(25, 19, '内容设置', '内容设置', 1495016519592, 1495016519592, 1),
(26, 19, '轮播设置', '轮播设置', 1495016519592, 1495016519592, 1);

# 新增分成配置、其它设置
INSERT INTO `t_sys_authority_menu_permission` (`sys_authority_id`, `sys_menu_permission_id`) VALUES
(3, 25),
(3, 26);

INSERT INTO `t_sys_menu_permission` (`id`, `parent_id`, `name`, `description`, `create_time`, `update_time`, `enabled`) VALUES
  (23, 5, '助力购管理', '助力购管理', 1495016519592, 1495016519592, 1),
  (24, 5, '免费领管理', '免费领管理', 1495016519592, 1495016519592, 1);

INSERT INTO `t_sys_authority_menu_permission` (`sys_authority_id`, `sys_menu_permission_id`) VALUES
  (3, 23),
  (3, 24);

