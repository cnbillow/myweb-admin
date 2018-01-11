一、配置 app.properties
包含mysql，Memcached，Redis,Mongo配置信息

二、导入sql  /sql/mycom-mgr-init.sql
默认用户名caixunadmin，密码caixunadmin888

三、spring配置主入口 /config/application-base.xml,默认不包含Redis和Mongo
