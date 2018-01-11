# myweb-admin
基本的java-web admin后台框架，使用Spring, springmvc, mybatis +layUI框架来实现。使用maven构建。

使用Spring + SpringMVC + myBatis作为框架
支持连接memcached服务，用作tomcat 多节点分布式应用。
使用LayUI框架来进行前后端分离处理。

使用maven来进行构建。

数据库初始化SQL保存在：src/main/resource/sql/mycom-mgr-init.sql

访问入口为：
http://localhost:8080/mycom-mgr/
提供有admin/admin登录用户
