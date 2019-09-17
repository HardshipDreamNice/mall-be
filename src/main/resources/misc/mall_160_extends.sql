# 菜单权限 首页商品类型新增、首页商品类型和商品关联新增
INSERT INTO `t_sys_interface_permission` (`id`, `name`, `permission`, `create_time`, `update_time`, `enabled`) VALUES
  (111, '首页商品类型新增', 'sys:indexproducttype:add', 1495016519592, 1495016519592, 1),
  (112, '首页商品类型删除', 'sys:indexproducttype:delete', 1495016519592, 1495016519592, 1),
  (113, '首页商品类型修改', 'sys:indexproducttype:update', 1495016519592, 1495016519592, 1),
  (114, '首页商品类型查询', 'sys:indexproducttype:query', 1495016519592, 1495016519592, 1),
  (115, '首页商品类型和商品关联新增', 'sys:indexproducttypeproduct:add', 1495016519592, 1495016519592, 1),
  (116, '首页商品类型和商品关联删除', 'sys:indexproducttypeproduct:delete', 1495016519592, 1495016519592, 1),
  (117, '首页商品类型和商品关联修改', 'sys:indexproducttypeproduct:update', 1495016519592, 1495016519592, 1),
  (118, '首页商品类型和商品关联查询', 'sys:indexproducttypeproduct:query', 1495016519592, 1495016519592, 1);

INSERT INTO `t_sys_authority_interface_permission` (`sys_authority_id`, `sys_interface_permission_id`) VALUES
  (3, 111),
  (3, 112),
  (3, 113),
  (3, 114),
  (3, 115),
  (3, 116),
  (3, 117),
  (3, 118);

# 菜单权限 首页商品类型新增、首页商品类型和商品关联新增
INSERT INTO `t_sys_interface_permission` (`id`, `name`, `permission`, `create_time`, `update_time`, `enabled`) VALUES
  (119, '内容新增', 'sys:content:add', 1543464635000, 1543464635000, 1),
  (120, '内容删除', 'sys:content:delete', 1543464635000, 1543464635000, 1),
  (121, '内容修改', 'sys:content:update', 1543464635000, 1543464635000, 1),
  (122, '内容查询', 'sys:content:query', 1543464635000, 1543464635000, 1),
  (123, '内容类别新增', 'sys:contenttype:add', 1543464635000, 1543464635000, 1),
  (124, '内容类别删除', 'sys:contenttype:delete', 1543464635000, 1543464635000, 1),
  (125, '内容类别修改', 'sys:contenttype:update', 1543464635000, 1543464635000, 1),
  (126, '内容类别查询', 'sys:contenttype:query', 1543464635000, 1543464635000, 1);

INSERT INTO `t_sys_authority_interface_permission` (`sys_authority_id`, `sys_interface_permission_id`) VALUES
  (3, 119),
  (3, 120),
  (3, 121),
  (3, 122),
  (3, 123),
  (3, 124),
  (3, 125),
  (3, 126);


# 菜单权限 首页商品类型新增、首页商品类型和商品关联新增
INSERT INTO `t_sys_interface_permission` (`id`, `name`, `permission`, `create_time`, `update_time`, `enabled`) VALUES
  (127, '免费领商品新增', 'sys:productfreereceive:add', 1495016519592, 1495016519592, 1),
  (128, '免费领商品删除', 'sys:productfreereceive:delete', 1495016519592, 1495016519592, 1),
  (129, '免费领商品修改', 'sys:productfreereceive:update', 1495016519592, 1495016519592, 1),
  (130, '免费领商品查询', 'sys:productfreereceive:query', 1495016519592, 1495016519592, 1),

  (131, '免费领商品详情新增', 'sys:productfreereceivedetail:add', 1495016519592, 1495016519592, 1),
  (132, '免费领商品详情删除', 'sys:productfreereceivedetail:delete', 1495016519592, 1495016519592, 1),
  (133, '免费领商品详情修改', 'sys:productfreereceivedetail:update', 1495016519592, 1495016519592, 1),
  (134, '免费领商品详情查询', 'sys:productfreereceivedetail:query', 1495016519592, 1495016519592, 1),

  (135, '免费领用户与免费领的商品中间表新增', 'sys:userproductfreereceive:add', 1495016519592, 1495016519592, 1),
  (136, '免费领用户与免费领的商品中间表删除', 'sys:userproductfreereceive:delete', 1495016519592, 1495016519592, 1),
  (137, '免费领用户与免费领的商品中间表修改', 'sys:userproductfreereceive:update', 1495016519592, 1495016519592, 1),
  (138, '免费领用户与免费领的商品中间表查询', 'sys:userproductfreereceive:query', 1495016519592, 1495016519592, 1);

INSERT INTO `t_sys_authority_interface_permission` (`sys_authority_id`, `sys_interface_permission_id`) VALUES
  (3, 127),
  (3, 128),
  (3, 129),
  (3, 130),
  (3, 131),
  (3, 132),
  (3, 133),
  (3, 134),
  (3, 135),
  (3, 136),
  (3, 137),
  (3, 138);

# 接口权限 优惠券、优惠券分类和用户优惠券
INSERT INTO `t_sys_interface_permission` (`id`, `name`, `permission`, `create_time`, `update_time`, `enabled`) VALUES
  (139, '优惠券新增', 'sys:coupon:add', 1543464635000, 1543464635000, 1),
  (140, '优惠券删除', 'sys:coupon:delete', 1543464635000, 1543464635000, 1),
  (141, '优惠券修改', 'sys:coupon:update', 1543464635000, 1543464635000, 1),
  (142, '优惠券查询', 'sys:coupon:query', 1543464635000, 1543464635000, 1),
  (143, '优惠券分类新增', 'sys:couponcategory:add', 1543464635000, 1543464635000, 1),
  (144, '优惠券分类删除', 'sys:couponcategory:delete', 1543464635000, 1543464635000, 1),
  (145, '优惠券分类修改', 'sys:couponcategory:update', 1543464635000, 1543464635000, 1),
  (146, '优惠券分类查询', 'sys:couponcategory:query', 1543464635000, 1543464635000, 1),
  (147, '用户优惠券新增', 'sys:usercoupon:add', 1543464635000, 1543464635000, 1),
  (148, '用户优惠券删除', 'sys:usercoupon:delete', 1543464635000, 1543464635000, 1),
  (149, '用户优惠券修改', 'sys:usercoupon:update', 1543464635000, 1543464635000, 1),
  (150, '用户优惠券查询', 'sys:usercoupon:query', 1543464635000, 1543464635000, 1);

INSERT INTO `t_sys_authority_interface_permission` (`sys_authority_id`, `sys_interface_permission_id`) VALUES
  (3, 139),
  (3, 140),
  (3, 141),
  (3, 142),
  (3, 143),
  (3, 144),
  (3, 145),
  (3, 146),
  (3, 147),
  (3, 148),
  (3, 149),
  (3, 150);

INSERT INTO `t_sys_interface_permission` (`id`, `name`, `permission`, `create_time`, `update_time`, `enabled`) VALUES
  (151, '签到新增', 'sys:usersignlog:add', 1543464635000, 1543464635000, 1),
  (152, '签到删除', 'sys:usersignlog:delete', 1543464635000, 1543464635000, 1),
  (153, '签到修改', 'sys:usersignlog:update', 1543464635000, 1543464635000, 1),
  (154, '签到查询', 'sys:usersignlog:query', 1543464635000, 1543464635000, 1);

INSERT INTO `t_sys_authority_interface_permission` (`sys_authority_id`, `sys_interface_permission_id`) VALUES
  (3, 151),
  (3, 152),
  (3, 153),
  (3, 154);


INSERT INTO `t_sys_interface_permission` (`id`, `name`, `permission`, `create_time`, `update_time`, `enabled`) VALUES
  (155, '助力购商品新增', 'sys:boostproduct:add', 1543464635000, 1543464635000, 1),
  (156, '助力购商品删除', 'sys:boostproduct:delete', 1543464635000, 1543464635000, 1),
  (157, '助力购商品修改', 'sys:boostproduct:update', 1543464635000, 1543464635000, 1),
  (158, '助力购商品查询', 'sys:boostproduct:query', 1543464635000, 1543464635000, 1),
  (159, '助力购商品详情新增', 'sys:boostproductdetail:add', 1543464635000, 1543464635000, 1),
  (160, '助力购商品详情删除', 'sys:boostproductdetail:delete', 1543464635000, 1543464635000, 1),
  (161, '助力购商品详情修改', 'sys:boostproductdetail:update', 1543464635000, 1543464635000, 1),
  (162, '助力购商品详情查询', 'sys:boostproductdetail:query', 1543464635000, 1543464635000, 1);

INSERT INTO `t_sys_authority_interface_permission` (`sys_authority_id`, `sys_interface_permission_id`) VALUES
  (3, 155),
  (3, 156),
  (3, 157),
  (3, 158),
  (3, 159),
  (3, 160),
  (3, 161),
  (3, 162);

INSERT INTO `t_sys_interface_permission` (`id`, `name`, `permission`, `create_time`, `update_time`, `enabled`) VALUES
  (167, '红包新增', 'sys:redenvelope:add', 1544411189000, 1544411189000, 1),
  (168, '红包删除', 'sys:redenvelope:delete', 1544411189000, 1544411189000, 1),
  (169, '红包修改', 'sys:redenvelope:update', 1544411189000, 1544411189000, 1),
  (170, '红包查询', 'sys:redenvelope:query', 1544411189000, 1544411189000, 1),
  (171, '红包团队新增', 'sys:redenvelopeteam:add', 1544411189000, 1544411189000, 1),
  (172, '红包团队删除', 'sys:redenvelopeteam:delete', 1544411189000, 1544411189000, 1),
  (173, '红包团队修改', 'sys:redenvelopeteam:update', 1544411189000, 1544411189000, 1),
  (174, '红包团队查询', 'sys:redenvelopeteam:query', 1544411189000, 1544411189000, 1),
  (175, '红包参与人员新增', 'sys:userredenvelopeteam:add', 1544411189000, 1544411189000, 1),
  (176, '红包参与人员删除', 'sys:userredenvelopeteam:delete', 1544411189000, 1544411189000, 1),
  (177, '红包参与人员修改', 'sys:userredenvelopeteam:update', 1544411189000, 1544411189000, 1),
  (178, '红包参与人员查询', 'sys:userredenvelopeteam:query', 1544411189000, 1544411189000, 1);

INSERT INTO `t_sys_authority_interface_permission` (`sys_authority_id`, `sys_interface_permission_id`) VALUES
  (3, 167),
  (3, 168),
  (3, 169),
  (3, 170),
  (3, 171),
  (3, 172),
  (3, 173),
  (3, 174),
  (3, 175),
  (3, 176),
  (3, 177),
  (3, 178);

INSERT INTO `t_sys_interface_permission` (`id`, `name`, `permission`, `create_time`, `update_time`, `enabled`) VALUES
  (191, '助力购团队新增', 'sys:boostteam:add', 1543464635000, 1543464635000, 1),
  (192, '助力购团队删除', 'sys:boostteam:delete', 1543464635000, 1543464635000, 1),
  (193, '助力购团队修改', 'sys:boostteam:update', 1543464635000, 1543464635000, 1),
  (194, '助力购团队查询', 'sys:boostteam:query', 1543464635000, 1543464635000, 1),
  (195, '助力购团队用户新增', 'sys:userboostteam:add', 1543464635000, 1543464635000, 1),
  (196, '助力购团队用户删除', 'sys:userboostteam:delete', 1543464635000, 1543464635000, 1),
  (197, '助力购团队用户修改', 'sys:userboostteam:update', 1543464635000, 1543464635000, 1),
  (198, '助力购团队用户查询', 'sys:userboostteam:query', 1543464635000, 1543464635000, 1);

INSERT INTO `t_sys_authority_interface_permission` (`sys_authority_id`, `sys_interface_permission_id`) VALUES
  (3, 191),
  (3, 192),
  (3, 193),
  (3, 194),
  (3, 195),
  (3, 196),
  (3, 197),
  (3, 198);

ALTER TABLE t_shop ADD COLUMN membership_grade_id BIGINT UNSIGNED NOT NULL DEFAULT 0 COMMENT '会员等级表主键';
ALTER TABLE t_shop ADD COLUMN shop_level INT NOT NULL DEFAULT 0 COMMENT '会员等级';

ALTER TABLE `t_order_detail` ADD COLUMN `membership_grade_id` BIGINT UNSIGNED COMMENT '会员等级表主键';
ALTER TABLE `t_order_detail` ADD COLUMN `boost_product_detail_id` BIGINT UNSIGNED COMMENT '助力购商品详情主键';
ALTER TABLE `t_order` MODIFY `customer_consignee_string` LONGTEXT COMMENT '用户收货信息';

INSERT INTO t_content (id, create_time, update_time, enabled, title, sub_title, author, content, release_time, weight, share_title, share_desc, share_img, create_user, modify_user) VALUES (1, 1543830691353, 1543830832377, 2, '化淡妆必备的化妆品', null, '', '<p>【导读】女人要学会化淡妆，这是保持女人尊严的一种方法，那么，化淡妆需要的化妆品有哪些呢?现在就仔细来看看陈泽茜老师的介绍吧。</p><p><br/></p><p><img class="loadingclass" id="loading_jp850jhx" src="/dist/ueditor/themes/default/images/spacer.gif" title="正在上传..."/></p><p><br/></p><p>淡妆是女人必须要学会的基本化妆方法，那么，化淡妆必备的化妆品有哪些呢?</p><p><br/></p><p>步骤：护肤(隔离)→粉底液→散粉→画眉→眼线→腮红→唇彩→发型→喷雾→卸妆</p><p><br/></p><p>1.先了解自己的肤质(中性、干性、油性、混合性、敏感性)，选择适合的护肤品，也是完美裸妆必备的打底工作;</p><p><br/></p><p>2.干性护肤步骤：洗面奶清洁→补水(保湿水二次清洁吸收的重要性5)→乳液(保湿锁水)→保湿霜(霜不要过于水状)→隔离霜(隔离皮肤与粉底液，让妆容更自然)</p><p><br/></p><p>3.如果你皮肤不是特别干，颜色不是特别黄，可以选择粉底液，用自然色，如果你皮肤底色有些深，建议不要使用这个颜色(可以用手抹匀也可以用粉拍，买粉饼中用的那种粉拍)</p><p><br/></p><p>4.定妆粉可以使抹过粉底液的面部呈现自然清透的肤质，也是BB颜</p><p><br/></p><p>5.眉笔(建议还是不要选择眉粉，晕染出来很怪)画之前先到一个手艺好的地方修个漂亮的眉形，不要修的太细太窄;选用黑色，拔线的那种，洋气选用咖啡色发点红的那种，至于画法自己上网上搜索。</p><p><br/></p><p>6.眼线笔(如果你用铅锌眼线笔容易化妆，那就选用眼线液或软芯眼线笔)</p><p><br/></p><p>7.眼影(如果眼皮不肿，想要素颜淡妆，那么就不用画眼影，画眼线就好了)</p><p><br/></p><p>8.睫毛夹得选择，不建议选择塑料那种，很容易坏，虽然能夹侧睫毛，建议烫一下;眼睛大不肿可以选用最普通那种金属夹子，如果单眼皮且肿可以选用金属夹中间带弹簧的那种;</p><p><br/></p><p>9.腮红，建议粉嫩比较可爱</p><p><br/></p><p>10.唇彩相当关键，选用滋润淡菜唇膏草莓味，想俏丽一些选择璀璨唇膏而且很耐用，到专柜试颜色，到个体护肤品店买有折扣;选择适合自己唇色就可以，不要选择发白发暗的那种;女性包里最少应该有3种颜色的唇彩和1支滋润唇膏。</p><p><br/></p><p>11.发型，一个发型可以体现一个人的气质*(可以上网搜搜相关发型)，头发颜色建议黑色;</p><p><br/></p><p>12.如果想要妆容一天清透无暇，那么就去选用一款保湿喷雾</p><p><br/></p>', 1543830832158, 0, null, null, null, 'admin', 'admin');
INSERT INTO t_content (id, create_time, update_time, enabled, title, sub_title, author, content, release_time, weight, share_title, share_desc, share_img, create_user, modify_user) VALUES (2, 1543830740618, 1543830809361, 2, '美白护肤小窍门', null, '', '<p>由于受紫外线影响，黑色素沉淀，皮肤变得灰暗没有光泽，还长痘长斑，洁白的脸蛋是美丽的突出表现，脸蛋上肤色白皙，对于女人来说简直就是美丽的代名词，今天就为你分享五个美容护肤小知识。</p><p><br/></p><p><img class="loadingclass" id="loading_jp8501q2" src="/dist/ueditor/themes/default/images/spacer.gif" title="正在上传..."/></p><p><br/></p><p>1、保持肌肤的清洁</p><p><br/></p><p>保持肌肤的清洁，说是容易，但却很多美眉做不到，很多美眉都喜欢化妆来掩盖脸部的瑕疵，但化妆容易堵塞毛孔，因此，洁面、卸妆对保持肌肤清洁起着关键的作用，所以，无论是早上，还是晚上，或是健身后，都要适当的清洁脸部的肌肤。</p><p><br/></p><p>敏感皮肤一定选对每天用的洗面奶，可直接用西缺舒缓保湿洗面奶，统计反馈，这款洗面奶全国各高校敏感皮肤大学生用的最多，效果最好。具有：提升肌肤含水量，不含香料，舒缓保湿，修护皮肤屏障，等等。超温和程度可睁开眼睛洁面，洁面后滋润清爽，细嫩舒美。使用后面部不会出现紧绷的状态，质地非常的温和，接近人体肌肤正常的PH值。是小编强烈推荐的一款洁面产品。</p><p><br/></p><p>2、补水面膜</p><p><br/></p><p>干燥也会导致皮肤暗黄？没错！水能促进细胞代谢运转，缺水时循环不畅，肤色就会变得暗沉。平时要注意做足补水保湿功课，爽肤水、乳液一步都不能少，如果可以，最好在爽肤水之后乳液前加一项保湿精华。此外，面膜也是速效补水的好帮手，当感到肤色暗淡时，来一篇美白保湿面膜，肌肤立马变得水亮白嫩。</p><p><br/></p><p>这款西缺金盏花舒缓保湿面膜专为亚洲敏感肌肤设计。富含金盏花，可以提高皮肤抵御力，为肌底补水修复，舒缓皮肤，预防过敏问题。也是西缺舒缓家族的首款片装面膜。</p><p><br/></p><p>面膜材质采用有&quot;人工皮肤&quot;著称的隐形薄膜，令面膜中的活性成分充分浸入面膜牢牢锁住。是一款三效合一面膜，包含面膜、眼膜和肌底导入液，拥有超高性价比的同时省却保养三次皮肤的繁琐。</p><p><br/></p><p>成分均为天然成分提取物，安全可靠。恢复敏感肌肤健康微循环，加速舒缓皮肤，控制水油平衡，改善面部绯红状态。</p><p><br/></p><p><br/></p><p><br/></p><p>3、维生素摄取对皮肤至关重要</p><p><br/></p><p>维生素A、维生素C和维生素E是美白的关键，不仅可以调节人体的身体机能和免疫力还能改善体内皮肤组织，抑制黑色素的生成，因此多吃这类的蔬果是有益无害的。</p><p><br/></p><p>4、去角质</p><p><br/></p><p>定期去角质，肌肤的表层每周都会新陈代谢，过老的废角质堆积在肌肤上，会给肌肤造成负担，所以每两周去一次角质，可以让肌肤重新呼吸，对美白很重要。你的皮肤可以使皮肤表面的死皮细胞的堆积显得有些沉闷。提亮你的皮肤，一定要定期去角质是非常重要的。</p><p><br/></p><p><br/></p><p><br/></p><p>5、调节情绪</p><p><br/></p><p>高管白领们想要改变暗黄肌肤，就得先学会调节情绪，懂得如何减压，心情好了，身体的内分泌就会自动调节，另外，最好选择有自我修复功能的美白产品，这样可以令肌肤恢复代谢功能，令美白之路走得更畅顺。</p><p><br/></p><p>最后就是，出门时一定记得擦防晒霜，保护好自己的皮肤，有了防晒霜隔离，紫外线对皮肤的损害也小了，也不容易被晒黑了。</p><p><br/></p><p>160商城，一站式护肤知名品牌。您身边的皮肤管理顾问，专业解决您的皮肤难题。助您轻松绽放自然美！</p><p><br/></p><p>您与您的美肌之间就差一个专业美肌顾问</p><p><br/></p>', 1543830809222, 0, null, null, null, 'admin', 'admin');
INSERT INTO t_content (id, create_time, update_time, enabled, title, sub_title, author, content, release_time, weight, share_title, share_desc, share_img, create_user, modify_user) VALUES (3, 1543830793811, 1543830798269, 2, '如何正确使用化妆品', null, '', '<p><img class="loadingclass" id="loading_jp84znur" src="/dist/ueditor/themes/default/images/spacer.gif" title="正在上传..."/></p><p>1、修眉：先用眉笔画出正确眉型，再拔除多余的眉毛，描画出来的眉型会更精确。用眉刷轻刷双眉，把过长的眉毛修剪短，然后用化妆水擦拭以作清洁。</p><p><br/></p><p>2、防晒隔离霜：隔离彩妆、粉尘污染等对皮肤的伤害，相当于一层皮肤的保护膜。妆前乳的用量不需要太多，顺着毛孔方向轻拍均匀即可。</p><p><br/></p><p>3、修颜液：调整不均肤色，白色的修颜液可以增加肤色的透明度，绿色可以修饰肌肤局部泛红状态，紫色或蓝色修颜液可以修饰偏黄肤色，而珠光感的修颜液局部运用在T字部位，可以让脸型立体。</p><p><br/></p><p>4、粉底：选择与肤色相接近的粉底，无论是用手指、海绵还是粉底刷上妆，都要遵循少量多取的原则。</p><p><br/></p><p>5、粉饼：可以在T区按压上薄薄的粉饼可令底妆更持久，此步骤也可以忽略。</p><p><br/></p><p>6、画眉：眉笔和眉粉颜色尽量与发色一致，画出眉型轮廓，最后眉刷沿“眉头→眉峰→眉尾”轻轻平刷修饰，使眉色均匀。</p><p><br/></p><p>7、眼影：最普通也最简单的一种方法：同一色彩以不同深、浅的色彩，自眼睑下方至上方、由深至浅渐次画上，可以塑造目光深邃的效果。眼睛看起来会变大至少1/3，且很有神、很亮。</p><p><br/></p><p>8、眼线：镜子放在眼睛下方，眼睛往下看，无名指拉起眼皮贴着睫毛根部画眼线，可以根据眼线调整眼线的粗细与长度。</p><p><br/></p><p>9、唇线口红：唇部化妆一般用唇彩就可以了，唇彩的颜色最好跟服装的主题色一致。如果想更加精致，可以用唇线笔画出清晰的唇部线条。</p><p><br/></p><p>10、腮红：选出适合色系的腮红，对着镜子微笑，颧骨的部位就是腮红可以打上的部位。使用时每次的腮红量要少、要淡；可多刷几次直至效果完美。</p><p><br/></p><p>11、定妆：扫上蜜粉能将妆容固定，化妆品不会轻易移位或剥落，令妆容保持光泽，延长妆容的持久度。再者，以粉扑拍打散粉渗入肌肤，有助于提高彩妆对皮肤的附着度，粗大的毛孔也可以得到一定程度的藏匿。</p><p><br/></p><p>12、夹睫毛：夹睫毛分三段式，分别从根部、中部，到睫毛梢夹出自然弧度。</p><p><br/></p><p>13、假睫毛：根据自己的眼睛长度修剪好假睫毛的长度，沿着假睫毛的梗涂上一层胶水，在两端的部分可以多涂一些，在胶水半干状态贴在睫毛根部的上方。</p><p><br/></p><p>14、睫毛膏：睫毛膏的涂抹方法是“Z”字型向上刷，戴了假睫毛后刷一层睫毛膏，可把真假睫毛融合在一起。</p><p><br/></p>', 1543830793618, 0, null, null, null, 'admin', 'admin');
INSERT INTO t_content (id, create_time, update_time, enabled, title, sub_title, author, content, release_time, weight, share_title, share_desc, share_img, create_user, modify_user) VALUES (4, 1543830863960, 1543830863960, 1, '晚上护肤的正确顺序', null, '', '<p><img class="loadingclass" id="loading_jp85188o" src="/dist/ueditor/themes/default/images/spacer.gif" title="正在上传..."/></p><p>夜间护肤第一步：卸妆</p><p>卸妆是护肤最重要部分之一。因为空气中游离的重金属、油性污染物，会在白天侵蚀肌肤，进而加速肌肤老化。更可怕的是，它是无法被普通的洁面产品所清洁的。因为它和化妆品一样，只溶于油，不溶于水，要想把这些脏东西从脸上弄走，就必须用卸妆产品。况且有些追求裸妆的MM，会用粉底液或BB霜等美白产品进行脸部基本遮瑕，那么如果不卸妆，想想这些肌肤残留污垢存在毛孔里，那么小编祝福你，毛孔堵塞“指日可待”!</p><p><br/></p><p>夜间护理第二步：清洁</p><p>卸完妆后，就要用洗面奶清洁脸部肌肤，把皮肤上的污垢和卸妆油残留物质全部彻底清洗干净。夜间建议使用温和的洗面奶，减少清洁护肤品带给肌肤的伤害。洗面奶最好不要直接用于脸部，可现在手上轻轻揉搓，起泡后再擦于面部，这样可把化妆品残留物彻底清除掉，确保皮肤的真正清洁。</p><p><br/></p><p>夜间护理第三步：去角质</p><p>角质层位于肌肤的最外层，具有保护肌肤、锁住水分的功能。原本该自然代谢的角质，会因皮肤老化、清洁不彻底、日晒、出油、作息改变、天气变化等原因，变得无法正常代谢。适度的去角质可让护肤品成分更容易被肌肤吸收，进而维持皮肤的新陈代谢。</p><p>去角质的工作不用天天做，但最好一个星期一次。使用温和的去角质素，去完后，一定要清洗干净，不能让它残留在皮肤上。</p><p>敏感肌肤的MM不用去角质，或者每个月去一次角质就可以了。</p><p><br/></p><p>夜间护理第四步：化妆水</p><p>昨晚之前的准备工作，接下来就是补水了，作为基础护肤三步曲中“承前启后”的关键角色，薄薄的一层化妆水，被赋予了重要的护肤使命。使用化妆水可以滋润肌肤，打通肌肤的吸收通道，让后续的护肤步骤得以顺利的开展。</p><p>不同肤质应选择不同化妆水：</p><p>油性的肌肤：紧肤效果的收敛型化妆水;</p><p>中性肌肤就使用爽肤水;</p><p>干性肌肤就使用补水效果好的柔肤水。</p><p><br/></p><p>夜间护理第五步：精华</p><p>知道吗?如果你的精华没有特殊指定在什么时候用，一般都在晚上使用是最好的，因为精华一般质地很好吸收含有丰富的营养成分，并且很多抗老化成分都有软化角质或抗氧化成分都在晚上才能不被分解，所以晚上用精华是最好的。</p><p><br/></p><p>夜间护理第六步：眼霜</p><p>眼周的部位非常脆弱，也是最容易出现问题的部位，黑眼圈、眼袋。鱼尾纹时时提醒着你该加强眼周的保养工作了!眼霜是千万不少的护肤品，在涂抹眼霜的同时，配合正确的按摩手法，可促进眼霜的吸收，还能加速眼部肌肤血液循环。</p><p><br/></p><p>夜间护理第七步：睡眠面膜</p><p>夜间面膜可是非常有力的武器，它也可以理解为按摩霜的升级版本，在睡眠面膜没问世之前，补水就取自于按摩霜，但大部分女性都不爱使用按摩霜，因为使用较麻烦和浪费时间，为了配合现代女性的时间钟，就演变成为今日的睡眠面膜了。</p><p>睡眠面膜的特点就是免洗，可以擦着过夜，弥补了按摩霜不能频繁使用的缺陷，有效舒缓身心疲劳并提升睡眠质量，从而更好地促进肌肤在夜间的新陈代谢。但同样需要注意的是，天天使用睡眠面膜还是会对肌肤造成一定的负担，甚至影响皮肤的自我修复能力，所以建议一周做2～3次。</p><p><br/></p><p>夜间护理第八步：晚霜</p><p>如果蜜友们觉得使用睡眠面膜完不用再擦任何护肤品了，那你就大错特错了!虽然睡眠面膜比较滋润，但并不代表它可以代替晚霜。大部分睡眠面膜都是补水保湿的功效，因此里面含有的营养物质不会太过丰富，长时间使用对皮肤的改观也不明显，因此晚霜还是要照用不误的。特别是对干性皮肤的女孩来说，睡眠面膜一定不可以代替晚霜。</p><p>晚间护肤顺序最后一步一定要是晚霜。</p><p>给肌肤选择一款营养的晚霜，可以让肌肤在睡眠中好好的吸收营养，以此修复白天给肌肤带来的伤害。</p><p>中性肌肤可选择保湿型修护晚霜来保护肌肤，但易干燥的肌肤就要使用大量柔肤水后再使用保湿晚霜。晚霜中含有油溶性成分，它容易被溶解在毛孔皮脂中，并会在皮肤的深层中扩散开来。</p><p><br/></p>', 1543830863770, 0, null, null, null, 'admin', 'admin');

ALTER TABLE t_order MODIFY order_type INT NOT NULL COMMENT '订单类型 0：普通商品订单 1：拼团订单 2：购买会员订单 3：免费领 4：助力购';
ALTER TABLE `t_order` ADD COLUMN `boost_team_id` BIGINT UNSIGNED COMMENT '助力购团队id';

ALTER TABLE t_product_detail_snapshot MODIFY `product_detail_id` BIGINT unsigned COMMENT '商品详情id';
ALTER TABLE t_product_snapshot MODIFY `product_id` BIGINT unsigned COMMENT '商品id';
ALTER TABLE `t_product_snapshot` ADD COLUMN `product_type` INT NOT NULL DEFAULT 0 COMMENT '商品类型;0：普通商品 1：拼团商品 2：会员 3：免费领商品 4：助力购商品';
ALTER TABLE `t_product_snapshot` ADD COLUMN `membership_grade_id` BIGINT UNSIGNED COMMENT '会员等级表主键';
ALTER TABLE `t_product_snapshot` ADD COLUMN `product_free_receive_id` BIGINT UNSIGNED COMMENT '免费领商品表主键';
ALTER TABLE `t_product_detail_snapshot` ADD COLUMN `product_free_receive_detail_id` BIGINT UNSIGNED COMMENT '免费领商品详情表主键';
ALTER TABLE `t_order` ADD COLUMN `last_total_fee` INT NOT NULL COMMENT '最后一次修改时的总价';

# 修改助力购
ALTER TABLE t_boost_team CHANGE boost_product_id product_id BIGINT(20) unsigned NOT NULL COMMENT '助力购商品id';
ALTER TABLE t_boost_team CHANGE boost_product_detail_id product_detail_id BIGINT(20) unsigned NOT NULL COMMENT '助力购商品详情id';
ALTER TABLE t_boost_team CHANGE boost_product_name product_name VARCHAR(512) COMMENT '商品名字';
ALTER TABLE t_boost_team ADD COLUMN `user_id` BIGINT NOT NULL COMMENT '发起人用户id';
ALTER TABLE t_order_detail DROP boost_product_detail_id;
ALTER TABLE t_product ADD COLUMN `boost_number` INT COMMENT '可砍价人数';
ALTER TABLE t_product ADD COLUMN `valid_day` INT COMMENT '有效时间，即多少天内有效';
ALTER TABLE t_product ADD COLUMN `boost_amount` INT COMMENT '可砍价金额';

#新增菜单权限
INSERT INTO `t_sys_menu_permission` (`id`, `parent_id`, `name`, `description`, `create_time`, `update_time`, `enabled`) VALUES
  (21, 16, '优惠券分类管理', '优惠券分类管理', 1495016519592, 1495016519592, 1);

#新增管理员权限关联菜单权限
INSERT INTO `t_sys_authority_menu_permission` (`sys_authority_id`, `sys_menu_permission_id`) VALUES
  (3, 21);

##################### 新增表 #####################
DROP TABLE IF EXISTS `t_index_product_type`;
CREATE TABLE IF NOT EXISTS `t_index_product_type` (
  `id` BIGINT UNSIGNED AUTO_INCREMENT COMMENT '表主键',
  `create_time` BIGINT DEFAULT 0 COMMENT '添加时间',
  `update_time` BIGINT DEFAULT 0 COMMENT '更新时间',
  `enabled` INT(1) DEFAULT '1' COMMENT '是否可见，0为不可见，1为可见',
  `name` VARCHAR(255) NOT NULL COMMENT '名称',
  `description` VARCHAR(512) COMMENT '描述',

  PRIMARY KEY (`id`)
) ENGINE=INNODB DEFAULT CHARSET=utf8 COMMENT='首页商品类型表' AUTO_INCREMENT=1;

INSERT INTO `t_index_product_type` (id, create_time, update_time, enabled, name, description) VALUES
  (1, 1543288042000, 1543288042000, 1, '热门活动', '首页热门活动'),
  (2, 1543288042000, 1543288042000, 1, '真爱新品', '首页真爱新品'),
  (3, 1543288042000, 1543288042000, 1, '精选优品', '首页精选优品');

DROP TABLE IF EXISTS `t_index_product_type_product`;
CREATE TABLE IF NOT EXISTS `t_index_product_type_product` (
  `id` BIGINT UNSIGNED AUTO_INCREMENT COMMENT '表主键',
  `create_time` BIGINT DEFAULT 0 COMMENT '添加时间',
  `update_time` BIGINT DEFAULT 0 COMMENT '更新时间',
  `enabled` INT(1) DEFAULT '1' COMMENT '是否可见，0为不可见，1为可见',
  `index_product_type_id` BIGINT NOT NULL COMMENT '首页商品类型主键',
  `product_id` BIGINT NOT NULL COMMENT '商品主键',
  `weight` INT COMMENT '排序，从小到大',
  `name` VARCHAR(512) COMMENT '商品名称',
  `title` VARCHAR(512) COMMENT '商品简述',


  PRIMARY KEY (`id`)
) ENGINE=INNODB DEFAULT CHARSET=utf8 COMMENT='首页商品类型和商品关联表' AUTO_INCREMENT=1;

DROP TABLE IF EXISTS `t_coupon_category`;
CREATE TABLE IF NOT EXISTS `t_coupon_category` (
  `id` BIGINT UNSIGNED AUTO_INCREMENT COMMENT '表主键',
  `create_time` BIGINT DEFAULT 0 COMMENT '添加时间',
  `update_time` BIGINT DEFAULT 0 COMMENT '更新时间',
  `enabled` INT(1) DEFAULT '1' COMMENT '是否可见，0为不可见，1为可见',
  `name` VARCHAR(50) NOT NULL COMMENT '分类名称',
  `description` VARCHAR(512) COMMENT '分类说明',

  PRIMARY KEY (`id`)
) ENGINE=INNODB DEFAULT CHARSET=utf8 COMMENT='优惠券分类表' AUTO_INCREMENT=1;


DROP TABLE IF EXISTS `t_coupon`;
CREATE TABLE IF NOT EXISTS `t_coupon` (
  `id` BIGINT UNSIGNED AUTO_INCREMENT COMMENT '表主键',
  `create_time` BIGINT DEFAULT 0 COMMENT '添加时间',
  `update_time` BIGINT DEFAULT 0 COMMENT '更新时间',
  `enabled` INT(1) DEFAULT '1' COMMENT '是否可见，0为不可见，1为可见',
  `name` VARCHAR(50) NOT NULL COMMENT '优惠券名称',
  `total_number` INT NOT NULL COMMENT '优惠券总数量',
  `remaining_number` INT NOT NULL COMMENT '优惠券剩余数量',
  `coupon_type` INT(1) NOT NULL DEFAULT 0 COMMENT '优惠券类型，0是面值 1是折扣',
  `discount_amount` INT NOT NULL DEFAULT 0 COMMENT '面值（分）/折扣（% 如95折）',
  `usable_amount` INT NOT NULL DEFAULT 0 COMMENT '使用门槛，即满多少元使用。单位为分',
  `is_release_to_wechat` INT(1) NOT NULL DEFAULT 0 COMMENT '是否同步发布到微信卡包 0：否，1：是',
  `card_coupon_color` VARCHAR(10) COMMENT '微信卡券颜色',
  `card_coupon_title` VARCHAR(50) COMMENT '微信卡券标题',
  `card_coupon_sub_title` VARCHAR(50) COMMENT '微信卡券副标题',
  `membership_grade_id` BIGINT NOT NULL DEFAULT 0 COMMENT '会员等级表的主键，0 为所有用户可用',
  `available_number` INT NOT NULL DEFAULT 1 COMMENT '可领取数量',
  `valid_period_type` INT NOT NULL DEFAULT 0 COMMENT '有效期类型，0：固定日期 1：领到券当日开始N天内有效 2：领到券次日开始N天内有效',
  `start_time` BIGINT NOT NULL COMMENT '开始时间',
  `end_time` BIGINT NOT NULL COMMENT '结束时间',
  `valid_day` INT COMMENT '有效时间，即多少天内有效',
  `is_expired_reminder` INT NOT NULL DEFAULT 0 COMMENT '过期前4天是否提醒 0：否 1：是',
  `usable_scope` INT NOT NULL DEFAULT 0 COMMENT '适用范围 0：全店可用 1：指定商品可用 2：指定商品不可用',
  `use_introduction` TEXT COMMENT '使用说明',
  `coupon_category_id` BIGINT NOT NULL COMMENT '优惠券分类id',

  PRIMARY KEY (`id`)
) ENGINE=INNODB DEFAULT CHARSET=utf8 COMMENT='优惠券表' AUTO_INCREMENT=1;

DROP TABLE IF EXISTS `t_user_coupon`;
CREATE TABLE IF NOT EXISTS `t_user_coupon` (
  `id` BIGINT UNSIGNED AUTO_INCREMENT COMMENT '表主键',
  `create_time` BIGINT DEFAULT 0 COMMENT '添加时间',
  `update_time` BIGINT DEFAULT 0 COMMENT '更新时间',
  `enabled` INT(1) DEFAULT '1' COMMENT '是否可见，0为不可见，1为可见',
  `user_id` BIGINT NOT NULL COMMENT '用户id',
  `coupon_id` BIGINT NOT NULL COMMENT '优惠券id',
  `expired_time` BIGINT NOT NULL COMMENT '到期时间',
  `status` INT NOT NULL DEFAULT 0 COMMENT '状态，0：未使用，1：已使用，2：已过期',
  PRIMARY KEY (`id`)
) ENGINE=INNODB DEFAULT CHARSET=utf8 COMMENT='用户优惠券关联表' AUTO_INCREMENT=1;

# xujianhao 20181128
# 免费领商品表
DROP TABLE IF EXISTS `t_product_free_receive`;
CREATE TABLE `t_product_free_receive` (
  `id` bigint unsigned NOT NULL AUTO_INCREMENT COMMENT '表主键',
  `create_time` bigint DEFAULT NULL COMMENT '添加时间',
  `update_time` bigint DEFAULT NULL COMMENT '更新时间',
  `enabled` int(1) DEFAULT '1' COMMENT '是否可见，0为不可见，1为可见',
  `name` varchar(512) DEFAULT NULL COMMENT '商品名称',
  `title` varchar(512) DEFAULT NULL COMMENT '商品简述',
  `image_url` longtext COMMENT '商品图片url',
  `image_url1` longtext COMMENT '商品图片url',
  `image_url2` longtext COMMENT '商品图片url',
  `small_image_url` longtext COMMENT '商品小图url',
  `description` longtext COMMENT '商品描述',
  `size_desc` longtext COMMENT '尺码表',
  `category_id` bigint(20) unsigned NOT NULL COMMENT '商品分类ID',
  `type` int(11) DEFAULT '0' COMMENT '0为普通商品，1为拼团，2为兼有',
  `start_time` mediumtext COMMENT '免费领开始时间',
  `expired_time` mediumtext COMMENT '过期时间',
  `count` int(11) DEFAULT '0' COMMENT '已领取数',
  `weight` int(11) DEFAULT '0' COMMENT '权重',
  PRIMARY KEY (`id`) USING BTREE,
  KEY `fk_product_category` (`category_id`) USING BTREE,
  CONSTRAINT `t_product_free_receive_ibfk_1` FOREIGN KEY (`category_id`) REFERENCES `t_category` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC COMMENT='商品表';

# 免费领商品详情表
DROP TABLE IF EXISTS `t_product_free_receive_detail`;
CREATE TABLE `t_product_free_receive_detail` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT '表主键',
  `create_time` bigint(20) DEFAULT NULL COMMENT '添加时间',
  `update_time` bigint(20) DEFAULT NULL COMMENT '更新时间',
  `enabled` int(1) DEFAULT '1' COMMENT '是否可见，0为不可见，1为可见',
  `size` varchar(255) DEFAULT NULL COMMENT '尺码',
  `price` bigint(20) DEFAULT NULL COMMENT '建议价格',
  `uppest_price` bigint(20) DEFAULT NULL COMMENT '市场价格',
  `color` varchar(255) DEFAULT NULL COMMENT '颜色',
  `inventory` bigint(20) DEFAULT NULL COMMENT '库存',
  `commission` bigint(20) DEFAULT NULL COMMENT '佣金',
  `product_free_receive_id` bigint(20) unsigned NOT NULL COMMENT '免费领商品id',
  PRIMARY KEY (`id`) USING BTREE,
  KEY `t_product_free_receive_detail` (`product_free_receive_id`) USING BTREE,
  CONSTRAINT `t_product_free_receive_detail_ibfk_1` FOREIGN KEY (`product_free_receive_id`) REFERENCES `t_product_free_receive` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC COMMENT='商品详情表';

#免费领用户与商品关联中间表
DROP TABLE IF EXISTS `t_user_product_free_receive`;
CREATE TABLE `t_user_product_free_receive` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT '表主键',
  `create_time` bigint(20) DEFAULT NULL COMMENT '添加时间',
  `update_time` bigint(20) DEFAULT NULL COMMENT '更新时间',
  `enabled` int(1) DEFAULT '1' COMMENT '是否可见，0为不可见，1为可见',
  `product_free_receive_id` bigint(20) unsigned DEFAULT NULL COMMENT '免费领商品id',
  `share_user_id` bigint(20) unsigned DEFAULT NULL COMMENT '分享者用户id',
  `help_user_id` bigint(20) unsigned DEFAULT NULL COMMENT '被分享者用户id',
  `status` INT DEFAULT '0' COMMENT '状态，0分享免费领，1免费领成功，2下单成功',
  `product_free_receive_detail_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=20 DEFAULT CHARSET=utf8 COMMENT='免费领商品用户中间表';

# 用户签到表
DROP TABLE IF EXISTS `t_user_sign_log`;
CREATE TABLE IF NOT EXISTS `t_user_sign_log` (
  `id` BIGINT UNSIGNED AUTO_INCREMENT COMMENT '表主键',
  `create_time` BIGINT NOT NULL DEFAULT '0' COMMENT '添加时间',
  `update_time` BIGINT NOT NULL DEFAULT '0' COMMENT '更新时间',
  `enabled` int(1) DEFAULT '1' COMMENT '是否可见，0为不可见，1为可见',
  `sign_user_id` BIGINT UNSIGNED NOT NULL COMMENT '签到用户Id',
  `help_user_id` BIGINT UNSIGNED COMMENT '助签用户Id',
  `sign_time` BIGINT NOT NULL COMMENT '签到时间',
  `status` INT NOT NULL DEFAULT 0 COMMENT '签到状态 0：签到未完成 1：签到完成',

  PRIMARY KEY (`id`)
)ENGINE=INNODB DEFAULT CHARSET=utf8 COMMENT='签到记录表' AUTO_INCREMENT=1;

# 助力购商品表
DROP TABLE IF EXISTS `t_boost_product`;
CREATE TABLE `t_boost_product` (
  `id` BIGINT UNSIGNED AUTO_INCREMENT COMMENT '表主键',
  `create_time` BIGINT NOT NULL DEFAULT '0' COMMENT '添加时间',
  `update_time` BIGINT NOT NULL DEFAULT '0' COMMENT '更新时间',
  `enabled` int(1) DEFAULT '1' COMMENT '是否可见，0为不可见，1为可见',
  `name` VARCHAR(512) NOT NULL COMMENT '商品名称',
  `title` VARCHAR(512) DEFAULT NULL COMMENT '商品简述',
  `image_url` TEXT COMMENT '商品图片url',
  `image_url1` TEXT COMMENT '商品图片url',
  `image_url2` TEXT COMMENT '商品图片url',
  `small_image_url` TEXT COMMENT '商品小图url',
  `description` LONGTEXT COMMENT '商品描述',
  `size_desc` LONGTEXT COMMENT '尺码表',
  `start_time` BIGINT NOT NULL COMMENT '助力购开始时间',
  `expired_time` BIGINT NOT NULL COMMENT '助力购结束时间',
  `boost_number` INT NOT NULL DEFAULT 1 COMMENT '可砍价人数',
  `boost_amount` INT NOT NULL DEFAULT 0 COMMENT '可砍价金额',
  `valid_day` INT NOT NULL DEFAULT 1 COMMENT '可砍价时间/天数 ',
  `weight` int DEFAULT '0' COMMENT '权重',
  `category_id` BIGINT UNSIGNED NOT NULL COMMENT '商品分类ID',

  PRIMARY KEY (`id`) USING BTREE
) ENGINE=INNODB DEFAULT CHARSET=utf8 COMMENT='助力购商品表' AUTO_INCREMENT=1;

# 助力购商品详情表
DROP TABLE IF EXISTS `t_boost_product_detail`;
CREATE TABLE `t_boost_product_detail` (
  `id` BIGINT UNSIGNED AUTO_INCREMENT COMMENT '表主键',
  `create_time` BIGINT NOT NULL DEFAULT '0' COMMENT '添加时间',
  `update_time` BIGINT NOT NULL DEFAULT '0' COMMENT '更新时间',
  `enabled` int(1) DEFAULT '1' COMMENT '是否可见，0为不可见，1为可见',
  `size` varchar(255) DEFAULT NULL COMMENT '尺码',
  `price` BIGINT DEFAULT NULL COMMENT '建议价格',
  `uppest_price` BIGINT DEFAULT NULL COMMENT '市场价格',
  `color` varchar(255) DEFAULT NULL COMMENT '颜色',
  `inventory` BIGINT DEFAULT NULL COMMENT '库存',
  `commission` BIGINT DEFAULT NULL COMMENT '佣金',
  `boost_product_id` BIGINT unsigned NOT NULL COMMENT '商品id',

  PRIMARY KEY (`id`) USING BTREE,
  FOREIGN KEY (`boost_product_id`) REFERENCES t_boost_product(`id`) ON DELETE CASCADE
) ENGINE=INNODB DEFAULT CHARSET=utf8 COMMENT='助力购商品详情表' AUTO_INCREMENT=1;

# 价格表
DROP TABLE IF EXISTS `t_boost_product_detail_price`;
CREATE TABLE IF NOT EXISTS `t_boost_product_detail_price` (
  `id` BIGINT UNSIGNED AUTO_INCREMENT COMMENT '表主键',
  `create_time` BIGINT COMMENT '添加时间',
  `update_time` BIGINT COMMENT '更新时间',
  `enabled` INT(1) DEFAULT '1' COMMENT '是否可见，0为不可见，1为可见',
  `price` BIGINT COMMENT '原价格',
  `discount_price` BIGINT COMMENT '折扣价',
  `type` INT DEFAULT '0' COMMENT '会员等级',
  `boost_product_detail_id` BIGINT UNSIGNED NOT NULL COMMENT '商品详情id',

  PRIMARY KEY (`id`),
  FOREIGN KEY (`boost_product_detail_id`) REFERENCES t_boost_product_detail(`id`) ON DELETE CASCADE
) ENGINE=INNODB DEFAULT CHARSET=utf8 COMMENT='助力购商品详情价格表' AUTO_INCREMENT=1;

DROP TABLE IF EXISTS `t_boost_team`;
CREATE TABLE IF NOT EXISTS `t_boost_team` (
  `id` BIGINT UNSIGNED AUTO_INCREMENT COMMENT '表主键',
  `create_time` BIGINT COMMENT '添加时间',
  `update_time` BIGINT COMMENT '更新时间',
  `enabled` INT(1) DEFAULT '1' COMMENT '是否可见，0为不可见，1为可见',
  `boost_product_id` BIGINT UNSIGNED NOT NULL COMMENT '助力购商品id',
  `boost_product_detail_id` BIGINT UNSIGNED NOT NULL COMMENT '助力购商品详情id',
  `expired_time` BIGINT NOT NULL DEFAULT 0 COMMENT '过期时间',
  `boost_product_name` VARCHAR(512)  COMMENT '商品名字',
  `boost_number` INT NOT NULL COMMENT '可砍价人数',

  PRIMARY KEY (`id`)
) ENGINE=INNODB DEFAULT CHARSET=utf8 COMMENT='助力购开团表' AUTO_INCREMENT=1;

DROP TABLE IF EXISTS `t_user_boost_team`;
CREATE TABLE IF NOT EXISTS `t_user_boost_team` (
  `id` BIGINT UNSIGNED AUTO_INCREMENT COMMENT '表主键',
  `create_time` BIGINT COMMENT '添加时间',
  `update_time` BIGINT COMMENT '更新时间',
  `enabled` INT(1) DEFAULT '1' COMMENT '是否可见，0为不可见，1为可见',
  `user_id` BIGINT UNSIGNED NOT NULL COMMENT '用户id',
  `boost_team_id` BIGINT UNSIGNED NOT NULL COMMENT '助力购团队id',
  `type` INT(1) DEFAULT 0 COMMENT '是否发起人，0为不是，1为是',
  `discount_amount` INT NOT NULL COMMENT '砍价金额',

  PRIMARY KEY (`id`)
) ENGINE=INNODB DEFAULT CHARSET=utf8 COMMENT='助力购砍价人员表' AUTO_INCREMENT=1;

DROP TABLE IF EXISTS `t_membership_grade`;
CREATE TABLE `t_membership_grade`(
  `id` BIGINT UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '表主键',
  `create_time` BIGINT COMMENT '添加时间',
  `update_time` BIGINT COMMENT '更新时间',
  `enabled` INTEGER COMMENT '是否启用0：不启用，1：启用',
  `name` varchar(20) COMMENT '会员名称',


  `price` BIGINT COMMENT '价格',
  `discount` BIGINT COMMENT '折扣',
  `grade` INTEGER comment '会员等级',
  `percent_1` INTEGER COMMENT '一级分配',
  `percent_2` INTEGER COMMENT '二级分配',
  `percent_3` INTEGER COMMENT '三级分配',
  `commission` BIGINT NOT NULL COMMENT '佣金',
  `benefit` LONGTEXT COMMENT '权益',

  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='会员等级表' AUTO_INCREMENT=1;

DROP TABLE IF EXISTS `t_red_envelope`;
CREATE TABLE IF NOT EXISTS `t_red_envelope` (
  `id` BIGINT UNSIGNED AUTO_INCREMENT COMMENT '表主键',
  `create_time` BIGINT COMMENT '添加时间',
  `update_time` BIGINT COMMENT '更新时间',
  `enabled` INT(1) DEFAULT '1' COMMENT '是否可见，0为不可见，1为可见',
  `name` VARCHAR(512) NOT NULL COMMENT '名称',
  `expired_time` BIGINT NOT NULL DEFAULT 0 COMMENT '过期时间',
  `valid_day` INT NOT NULL DEFAULT 1 COMMENT '可抢红包时间/天数 ',
  `coupon_category_id` BIGINT UNSIGNED  COMMENT '优惠券分类id',
  `take_number` INT NOT NULL COMMENT '可拆红包人数',
  `total_amount` INT COMMENT '红包总金额',
  `type` INT(1) NOT NULL DEFAULT 0 COMMENT '类型 0：优惠券红包 1：现金红包',

  PRIMARY KEY (`id`)
) ENGINE=INNODB DEFAULT CHARSET=utf8 COMMENT='现金红包表' AUTO_INCREMENT=1;

DROP TABLE IF EXISTS `t_red_envelope_team`;
CREATE TABLE IF NOT EXISTS `t_red_envelope_team` (
  `id` BIGINT UNSIGNED AUTO_INCREMENT COMMENT '表主键',
  `create_time` BIGINT COMMENT '添加时间',
  `update_time` BIGINT COMMENT '更新时间',
  `enabled` INT(1) DEFAULT '1' COMMENT '是否可见，0为不可见，1为可见',
  `red_envelope_id` BIGINT UNSIGNED NOT NULL COMMENT '红包id',
  `expired_time` BIGINT NOT NULL DEFAULT 0 COMMENT '过期时间',
  `red_envelope_name` VARCHAR(512)  COMMENT '红包名称',
  `take_number` INT NOT NULL COMMENT '可拆红包人数',
  `red_envelope_type` INT(1) NOT NULL DEFAULT 0 COMMENT '红包类型 0：优惠券红包 1：现金红包',
  `user_id` BIGINT UNSIGNED NOT NULL COMMENT '发起人的用户id',

  PRIMARY KEY (`id`)
) ENGINE=INNODB DEFAULT CHARSET=utf8 COMMENT='拆红包团表' AUTO_INCREMENT=1;

DROP TABLE IF EXISTS `t_user_red_envelope_team`;
CREATE TABLE IF NOT EXISTS `t_user_red_envelope_team` (
  `id` BIGINT UNSIGNED AUTO_INCREMENT COMMENT '表主键',
  `create_time` BIGINT COMMENT '添加时间',
  `update_time` BIGINT COMMENT '更新时间',
  `enabled` INT(1) DEFAULT '1' COMMENT '是否可见，0为不可见，1为可见',
  `user_id` BIGINT UNSIGNED NOT NULL COMMENT '用户id',
  `red_envelope_team_id` BIGINT UNSIGNED NOT NULL COMMENT '拆红包团队id',
  `type` INT(1) DEFAULT 0 COMMENT '是否发起人，0为不是，1为是',
  `take_amount` INT COMMENT '领取金额，单位分',

  PRIMARY KEY (`id`)
) ENGINE=INNODB DEFAULT CHARSET=utf8 COMMENT='红包领取人员表' AUTO_INCREMENT=1;

DROP TABLE IF EXISTS `t_coupon_product`;
CREATE TABLE IF NOT EXISTS `t_coupon_product` (
  `id` BIGINT UNSIGNED AUTO_INCREMENT COMMENT '表主键',
  `create_time` BIGINT COMMENT '添加时间',
  `update_time` BIGINT COMMENT '更新时间',
  `enabled` INT(1) DEFAULT '1' COMMENT '是否可见，0为不可见，1为可见',
  `coupon_id` BIGINT UNSIGNED NOT NULL COMMENT '优惠券id',
  `product_id` BIGINT UNSIGNED NOT NULL COMMENT '商品id',
  `type` INT(1) NOT NULL DEFAULT '1' COMMENT '0：指定商品不可用 1：指定商品可用',

  PRIMARY KEY (`id`)
) ENGINE=INNODB DEFAULT CHARSET=utf8 COMMENT='优惠券商品关联表' AUTO_INCREMENT=1;

#xujianhao20181211
#会员管理模块接口的权限增加
INSERT INTO `t_sys_interface_permission` (`id`, `name`, `permission`, `create_time`, `update_time`, `enabled`) VALUES
  (163, '会员新增', 'sys:usersignlog:add', 1543464635000, 1543464635000, 1),
  (164, '会员删除', 'sys:usersignlog:delete', 1543464635000, 1543464635000, 1),
  (165, '会员修改', 'sys:usersignlog:update', 1543464635000, 1543464635000, 1),
  (166, '会员查询', 'sys:usersignlog:query', 1543464635000, 1543464635000, 1);

INSERT INTO `t_sys_authority_interface_permission` (`sys_authority_id`, `sys_interface_permission_id`) VALUES
  (3, 163),
  (3, 164),
  (3, 165),
  (3, 166);


INSERT INTO `t_sys_menu_permission` (`id`, `parent_id`, `name`, `description`, `create_time`, `update_time`, `enabled`) VALUES
  (14, 19, '首页设置', '首页设置', 1495016519592, 1495016519592, 1);

#新增管理员权限关联菜单权限
INSERT INTO `t_sys_authority_menu_permission` (`sys_authority_id`, `sys_menu_permission_id`) VALUES
  (3, 14);

#xujianhao20181217
#供应商管理菜单权限添加
INSERT INTO `t_sys_menu_permission` (`id`, `parent_id`, `name`, `description`, `create_time`, `update_time`, `enabled`) VALUES
  (22, 22, '供应商管理', '供应商管理', 1495016519592, 1495016519592, 1);

#新增管理员权限关联菜单权限
INSERT INTO `t_sys_authority_menu_permission` (`sys_authority_id`, `sys_menu_permission_id`) VALUES
  (3, 22);

#xujianhao20181217
#意见反馈添加权限
INSERT INTO `t_sys_menu_permission` (`id`, `parent_id`, `name`, `description`, `create_time`, `update_time`, `enabled`) VALUES
  (10, 10, '意见反馈', '意见反馈', 1495016519592, 1495016519592, 1);

#新增管理员权限关联菜单权限
INSERT INTO `t_sys_authority_menu_permission` (`sys_authority_id`, `sys_menu_permission_id`) VALUES
  (3, 10);


#xujianhao20181211
#商品分类图片上传接口的权限增加
INSERT INTO `t_sys_interface_permission` (`id`, `name`, `permission`, `create_time`, `update_time`, `enabled`) VALUES
  (199, '商品分类图片上传', 'sys:category:upload', 1543464635000, 1543464635000, 1);

INSERT INTO `t_sys_authority_interface_permission` (`sys_authority_id`, `sys_interface_permission_id`) VALUES
  (3, 199);
