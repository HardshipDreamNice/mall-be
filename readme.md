# 广州都灵源链信息科技有限公司后台开放平台 #

----------

##**介绍**
广州都灵源链信息科技有限公司后端代码基础模型，使用了SpringBoot2+JPA+Hibernate框架。

* JAVA
* Spring
* JPA

**【标准开发环境】**

Idea + maven + jdk8 + mysql5.7

##**域名设置规则**

xxx为项目名字，使用英文来命名。

**www.torinosrc.com** 配置为前台页面，用户可以直接访问该域名

**xxx-admin.torinosrc.com** 配置为管理后台页面访问域名

**xxx-api.torinosrc.com** 配置为后台api能力接口访问域名

**xxx-resource.torinosrc.com** 配置为页面资源（如image, js, mp3等）访问域名

##**Get Started**#

第一步：在mysql手工创建数据库torinosrc-springboot(具体项目按具体项目名称)， 采用UTF-8编码

第二步：执行resources/misc/database.sql来初始化数据库

第三步：使用idea导入maven项目，启动项目

第四步：默认项目访问入口[http://localhost:52013](http://localhost:52013 "http://localhost:52013")

###**【访问SwaggerUI】**

启动项目后，访问[http://localhost:52013/swagger-ui.html](http://localhost:52013/swagger-ui.html "http://localhost:52013/swagger-ui.html")

###**【安全模块Apache Shiro启用步骤】**

默认启动：参考\torinosrc-spring-boot-be\src\main\java\com\torinosrc\security\TorinoSrcShiroConfig.java

###**【UEditor启用步骤】**

1. 修改项目src/main/webapp/ueditor/conf/config.json来配置UEditor。

	**特别注意下面两点：** 
	
	- physicalPathFormat 为 图片上传物理路径。
	
	- %%%UrlPrefix 为 回显图片的域名（%%%为通配符）。
	
	其它可按默认值使用。

2. 配置Nginx/Httpd来避免**跨域**

	通过监听匹配http://ueditor.torinosrc.com/ueditor/** 转发到http://ueditor-api.torinosrc.com/** 来避免UEditor的访问跨域而无法使用单图上传等功能，具体配置如下：

####**Nginx 虚拟主机配置方法**

ueditor.torinosrc.com

	server
	{
	    listen 80;
	    #listen [::]:80;
	    server_name ueditor.torinosrc.com;
	    index index.html index.htm index.php default.html default.htm default.php;
	    root  /www/web/ueditor/fe;
	
	    #error_page   404   /404.html;
	    include proxy-pass-php.conf;
	
	    include proxy-pass-jsp.conf;
	
	    # pass ueditor to backend handling requests
	    location /ueditor 
	    {
	            proxy_pass      http://ueditor-api.torinosrc.com;
	            proxy_redirect  off;
	            proxy_set_header   Host             $host;
	            proxy_set_header   X-Real-IP        $remote_addr;
	            proxy_set_header   X-Forwarded-For  $proxy_add_x_forwarded_for;
	            proxy_cookie_path /weixin4j/ /;
	            proxy_set_header Cookie $http_cookie;
	    }
	
	    location ~ .*\.(gif|jpg|jpeg|png|bmp|swf)$
	    {
	        expires      30d;
	    }
	
	    location ~ .*\.(js|css)?$
	    {
	        expires      12h;
	    }
	
	    location ~ /\.
	    {
	        deny all;
	    }
	
	    access_log  off;
	}
	

ueditor-admin.torinosrc.com

	server
	{
	    listen 80;
	    #listen [::]:80;
	    server_name ueditor-admin.torinosrc.com;
	    index index.html index.htm index.php default.html default.htm default.php;
	    root  /www/web/ueditor/admin;
	
	    #error_page   404   /404.html;
	    include proxy-pass-php.conf;
	
	    include proxy-pass-jsp.conf;
	
	    location ~ .*\.(gif|jpg|jpeg|png|bmp|swf)$
	    {
	        expires      30d;
	    }
	
	    location ~ .*\.(js|css)?$
	    {
	        expires      12h;
	    }
	
	    location ~ /\.
	    {
	        deny all;
	    }
	
	    access_log  off;
	}

ueditor-api.torinosrc.com

	server
	{
	    listen 80;
	
	    server_name ueditor-api.torinosrc.com;
	
	    index index.html index.htm index.php default.html default.htm default.php;
	    root  html;
	
	    location /
	    {
	            proxy_pass      http://localhost:8080;
	            proxy_redirect  off;
	            proxy_set_header   Host             $host;
	            proxy_set_header   X-Real-IP        $remote_addr;
	            proxy_set_header   X-Forwarded-For  $proxy_add_x_forwarded_for;
	            proxy_cookie_path /weixin4j/ /;
	            proxy_set_header Cookie $http_cookie;
	    }
	
	    access_log off;
	}

ueditor-resource.torinosrc.com

	server
	{
	    listen 80;
	    #listen [::]:80;
	    server_name ueditor-resource.torinosrc.com;
	    index index.html index.htm index.php default.html default.htm default.php;
	    root  /www/web/ueditor;
	
	    #error_page   404   /404.html;
	    include proxy-pass-php.conf;
	
	    include proxy-pass-jsp.conf;
	
	    location ~ .*\.(gif|jpg|jpeg|png|bmp|swf)$
	    {
	        expires      30d;
	    }
	
	    location ~ .*\.(js|css)?$
	    {
	        expires      12h;
	    }
	
	    location ~ /\.
	    {
	        deny all;
	    }
	
	    access_log  off;
	}

###**Apache Httpd 虚拟主机配置方法**

	<VirtualHost *:80>	
	    DocumentRoot "C:\ueditor\fe"
	    ServerName ueditor.torinosrc.net
	    ServerAlias torinosrc.net
	    <Directory "C:\ueditor\fe">
	      Options FollowSymLinks ExecCGI
	      AllowOverride All
	      Order allow,deny
	      Allow from all
	      Require all granted	      
	    </Directory>
	    ProxyPass /ueditor http://ueditor-api.torinosrc.net/
	    ProxyPassReverse /ueditor http://ueditor-api.torinosrc.net/
	</VirtualHost>
	
	<VirtualHost *:80>
	    DocumentRoot "C:\www\web\torinosrc\resource"
	    ServerName ueditor-resource.torinosrc.net
	    ServerAlias torinosrc.net	
	    <Directory "C:\ueditor">	
	      Options FollowSymLinks ExecCGI
	      AllowOverride All
	      Order allow,deny
	      Allow from all
	      Require all granted	
	    </Directory>	
	</VirtualHost>
	
	<VirtualHost *:80>	
	    DocumentRoot "C:\ueditor\admin"
	    ServerName ueditor-admin.torinosrc.net
	    ServerAlias torinosrc.net
	    <Directory "C:\ueditor\admin">
	      Options FollowSymLinks ExecCGI
	      AllowOverride All
	      Order allow,deny
	      Allow from all
	      Require all granted	      
	    </Directory>
	</VirtualHost>
	
	<VirtualHost *:80>
	    ServerName ueditor-api.torinosrc.net
	    ProxyPass / http://localhost:8080/
	    ProxyPassReverse / http://localhost:8080/
	</VirtualHost>

## 都灵源链开放API ##

### 授权认证 ###

#### 鉴权方式 ####
开放平台API授权通过Access Token作为接口调用的凭证，在对开放API发起请求时，均需要在HTTP Header加入以下授权参数：

#### 授权流程


#### 获取 Access Token
**接口**

	POST /api/v1/sysusers/login

**参数说明**

	参数				类型			必填			说明
	userName		String			是			用户名
	password		String			是			密码

**返回参数**

Token为我们需要的返回值

	{
	  "messageCode": "1000",
	  "messageStatus": "Success",
	  "messageDescription": "用户登陆成功",
	  "data": {
	    "id": 1,
	    "createTime": 1495016519592,
	    "updateTime": 1495016519592,
	    "enabled": 1,
	    "condition": null,
	    "userName": "lvxin",
	    "password": "881e404b88965981e05e911832c14c96",
	    "name": "吕鑫",
	    "email": "lvxin@torinosrc.com",
	    "mobile": "18027188617",
	    "sysRoles": [
	      {
	        "id": 1,
	        "createTime": 1495016519592,
	        "updateTime": 1495016519592,
	        "enabled": 1,
	        "condition": null,
	        "englishName": "ADMIN",
	        "chineseName": "管理员",
	        "description": "管理员",
	        "sysAuthorities": [
	          {
	            "id": 1,
	            "createTime": 1495016519592,
	            "updateTime": 1495016519592,
	            "enabled": 1,
	            "condition": null,
	            "hasAuthority": false,
	            "name": "后台菜单",
	            "description": "后台菜单",
	            "sysMenuPermissions": [
	              {
	                "id": 2,
	                "createTime": 1495016519592,
	                "updateTime": 1495016519592,
	                "enabled": 1,
	                "condition": null
	              },
	              {
	                "id": 3,
	                "createTime": 1495016519592,
	                "updateTime": 1495016519592,
	                "enabled": 1,
	                "condition": null
	              },
	              {
	                "id": 4,
	                "createTime": 1495016519592,
	                "updateTime": 1495016519592,
	                "enabled": 1,
	                "condition": null
	              },
	              {
	                "id": 1,
	                "createTime": 1495016519592,
	                "updateTime": 1495016519592,
	                "enabled": 1,
	                "condition": null
	              }
	            ],
	            "sysInterfacePermissions": [
	              {
	                "id": 3,
	                "createTime": 1495016519592,
	                "updateTime": 1495016519592,
	                "enabled": 1,
	                "condition": null,
	                "name": "账号修改",
	                "permission": "sys:sysuser:update"
	              },
	              {
	                "id": 2,
	                "createTime": 1495016519592,
	                "updateTime": 1495016519592,
	                "enabled": 1,
	                "condition": null,
	                "name": "账号删除",
	                "permission": "sys:sysuser:delete"
	              },
	              {
	                "id": 1,
	                "createTime": 1495016519592,
	                "updateTime": 1495016519592,
	                "enabled": 1,
	                "condition": null,
	                "name": "账号新增",
	                "permission": "sys:sysuser:add"
	              },
	              {
	                "id": 4,
	                "createTime": 1495016519592,
	                "updateTime": 1495016519592,
	                "enabled": 1,
	                "condition": null,
	                "name": "账号查询",
	                "permission": "sys:sysuser:query"
	              }
	            ]
	          },
	          {
	            "id": 2,
	            "createTime": 1495016519592,
	            "updateTime": 1495016519592,
	            "enabled": 1,
	            "condition": null,
	            "hasAuthority": false,
	            "name": "前端用户接口",
	            "description": "前端用户接口",
	            "sysMenuPermissions": [],
	            "sysInterfacePermissions": []
	          }
	        ]
	      }
	    ],
	    "newPassword": null,
	    "token": "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJpZCI6IjEiLCJleHAiOjE1MjY1NTc0NTQsInVzZXJuYW1lIjoibHZ4aW4ifQ.j8msMeAv7TRehfZ4VMEJSNedSzxy4SWgNo1GtuHblmo",
	    "userRoles": [
	      "1"
	    ],
	    "userAuthorities": [
	      "1",
	      "2"
	    ],
	    "userMenuPermissions": [
	      "1",
	      "2",
	      "3",
	      "4"
	    ],
	    "userInterfacePermissions": [
	      "sys:sysuser:add",
	      "sys:sysuser:update",
	      "sys:sysuser:query",
	      "sys:sysuser:delete"
	    ]
	  }
	}

**代码示例**

参考项目文件\torinosrc-spring-boot-be\src\main\java\com\torinosrc\controller\sysuser\SysUserController.java中的login方法

### 数据操作 ###

#### 查询数据

**接口** 

**参数说明**

Content-Type: application/json

**参数|类型|必填|说明**

	参数			类型	必填	说明
	condition	String	N	查询语句，参数值应经过 JSON 编码为 JSONString 后，再经过 URL 编码,包含两个字段，where说明判断条件，orderBy对资源进行字段排序
	limit		Number	N	限制返回资源的个数，默认为 10 条
	offset		Number	N	设置返回资源的起始偏移值，默认为 0
	field 	String	N	返回字段(base64编码，include表示包含字段，filter表示排除字段)，比如：{"include":"id,name"}或者{"filter":"id,name"}；后端接口增加注解说明“@JSON(type = <clazz>.class),clazz为返回的对象类型”

**代码示例**
1
	{
		"where": {
			"prise":{
				"$eq": 10
			},
			"id":{
				"$ne": 2
			}
		},
		"orderBy": {
			"1":"price:desc",
			"2":"id:asc"
		}
	}

2

	{
		"where": {
			"$or": [
				"prise":{
					"$eq": 10
				},
				"id":{
					"$ne": 2
				}
			]
		},
		"orderBy": {
			"1":"price:desc",
			"2":"id:asc"
		}
	}

该接口完整支持的查询操作符如下：

	查询操作符	含义
	$eq			等于
	$ne			不等于
	$lt			小于
	$lte		小于等于
	$gt			大于
	$gte		大于等于
	$like		模糊查询
	
	暂不支持下面操作符
	$contains	包含任意一个值
	$nin		不包含任意一个数组值
	$in			包含任意一个数组值
	$isnull		是否为 NULL
	$range		包含数组值区间的值

使用以上查询操作符即可完成一些简单的条件查询，同时，你也可以使用 $and 和 $or 查询操作符，对以上查询操作符进行组合使用，完成更复杂的条件查询，如查询 价格为 10 元且产品名称中包含 “包” 的物品 或 价格大于 100 元的物品，其筛选条件为：

	{
		"where": {
			"$or": [
				{
					"$and": [
						{
							"price": {"$eq": 10}
						},
						{
							"name": {"$contains": "包"}
						}
					]
				},
				{
					"price": {"$gt": 100}
				}
			]
		},
		"orderBy": {
			"1":"price:desc",
			"2":"id:asc"
		}
	}

###排序返回查询数据###
查询接口默认按创建时间倒序的顺序来返回数据列表，你也可以通过设置 orderBy 参数来实现。

###验证码模块####

####生成验证码####

验证码生成

**接口**

	GET /api/v1/captcha

**参数说明**

	参数				类型			必填			说明
	captchaType		Integer			是			验证码类型 1：手机验证码 2：传统图片验证码 3：可扩展，填入相对应的数字即可
	contentType		Integer			是			内容类型 1：数字 2：字母 3：混合 4：可扩展，填入相对应的数字即可,手机验证码不需填此项
	length			Integer			是			验证码长度（0 < length < 10）
	phoneNumber		Integer			否			手机号码，传统验证码可不需填此项, 手机验证码必需填
	userName		String			是			用户名

**返回值**

	参数				类型			说明
	captchaImage	base64		base64的验证码图片（手机验证码不返回此项）


####验证码校验####

验证，不会删除数据库保存记录

**接口**

	POST /api/v1/captcha/verify

**参数说明**

	参数				类型			必填			说明
	userName		String			是			用户名
	validateCode	String			是			用户输入验证码

**返回值**

	参数				类型			说明
								返回true或者false
								{
									"messageCode": "1000",
	data			boolean			"messageStatus": "Success",
									"messageDescription": "操作成功",
									"data": false
								}



##**联系我们**

email： lvxin@torinosrc.com

手机：18027188617