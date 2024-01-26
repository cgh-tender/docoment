## vue Test
[testDocument](https://cn.vitest.dev/guide/test-context.html)

## create databases
```sql
create schema cgh1 collate utf8mb4_general_ci;
```

## docker 安装nacos

```bash
docker pull nacos/nacos-server
docker run -p 8848:8848 -p 9848:9848 -p 9849:9849 -p 7848:7848 -e MODE=standalone -e NACOS_AUTH_ENABLE=true -e PREFER_HOST_MODE=hostname -e MYSQL_SERVICE_HOST=172.17.0.3 -v /Users/cgh/Tool/nacos/logs:/home/nacos/logs --restart always --name nacos -d nacos/nacos-server
```
## nacos application.properties 
```properties
# spring 
server.servlet.contextPath=${SERVER_SERVLET_CONTEXTPATH:/nacos}
server.contextPath=/nacos
server.port=${NACOS_APPLICATION_PORT:8848}
server.tomcat.accesslog.max-days=30
server.tomcat.accesslog.pattern=%h %l %u %t "%r" %s %b %D %{User-Agent}i %{Request-Source}i
server.tomcat.accesslog.enabled=${TOMCAT_ACCESSLOG_ENABLED:false}
server.error.include-message=ALWAYS
# default current work dir
server.tomcat.basedir=file:.
#*************** Config Module Related Configurations ***************#
### Deprecated configuration property, it is recommended to use `spring.sql.init.platform` replaced.
#spring.datasource.platform=${SPRING_DATASOURCE_PLATFORM:}
spring.sql.init.platform=${SPRING_DATASOURCE_PLATFORM:}
nacos.cmdb.dumpTaskInterval=3600
nacos.cmdb.eventTaskInterval=10
nacos.cmdb.labelTaskInterval=300
nacos.cmdb.loadDataAtStart=false
db.num=${MYSQL_DATABASE_NUM:1}
db.url.0=jdbc:mysql://${MYSQL_SERVICE_HOST}:${MYSQL_SERVICE_PORT:3306}/${MYSQL_SERVICE_DB_NAME:nacos}?${MYSQL_SERVICE_DB_PARAM:characterEncoding=utf8&connectTimeout=1000&socketTimeout=3000&autoReconnect=true&useSSL=false}
db.user.0=${MYSQL_SERVICE_USER:root}
db.password.0=${MYSQL_SERVICE_PASSWORD:123456}
## DB connection pool settings
db.pool.config.connectionTimeout=${DB_POOL_CONNECTION_TIMEOUT:30000}
db.pool.config.validationTimeout=10000
db.pool.config.maximumPoolSize=20
db.pool.config.minimumIdle=2
### The auth system to use, currently only 'nacos' and 'ldap' is supported:
nacos.core.auth.system.type=${NACOS_AUTH_SYSTEM_TYPE:nacos}
### worked when nacos.core.auth.system.type=nacos
### The token expiration in seconds:
nacos.core.auth.plugin.nacos.token.expire.seconds=${NACOS_AUTH_TOKEN_EXPIRE_SECONDS:18000}
### The default token:
nacos.core.auth.plugin.nacos.token.secret.key=${NACOS_AUTH_TOKEN:}
### Turn on/off caching of auth information. By turning on this switch, the update of auth information would have a 15 seconds delay.
nacos.core.auth.caching.enabled=${NACOS_AUTH_CACHE_ENABLE:false}
nacos.core.auth.enable.userAgentAuthWhite=${NACOS_AUTH_USER_AGENT_AUTH_WHITE_ENABLE:false}
nacos.core.auth.server.identity.key=${NACOS_AUTH_IDENTITY_KEY:}
nacos.core.auth.server.identity.value=${NACOS_AUTH_IDENTITY_VALUE:}
## spring security config
### turn off security
nacos.security.ignore.urls=${NACOS_SECURITY_IGNORE_URLS:/,/error,/**/*.css,/**/*.js,/**/*.html,/**/*.map,/**/*.svg,/**/*.png,/**/*.ico,/console-fe/public/**,/v1/auth/**,/v1/console/health/**,/actuator/**,/v1/console/server/**}
# metrics for elastic search
management.metrics.export.elastic.enabled=false
management.metrics.export.influx.enabled=false
nacos.naming.distro.taskDispatchThreadCount=10
nacos.naming.distro.taskDispatchPeriod=200
nacos.naming.distro.batchSyncKeyCount=1000
nacos.naming.distro.initDataRatio=0.9
nacos.naming.distro.syncRetryDelay=5000
nacos.naming.data.warmup=true
nacos.console.ui.enabled=true
nacos.core.param.check.enabled=true
nacos.core.auth.enabled=true
nacos.core.auth.server.identity.key=example
nacos.core.auth.server.identity.value=example
nacos.core.auth.plugin.nacos.token.secret.key=VGhpc0lzTXlDdXN0b21TZWNyZXRLZXkwMTIzNDU2Nzg=
```

## 安装 dcoker OceanBase
```bash
docker pull oceanbase/oceanbase-ce
docker run -p 2885:2885 -p 2881:2881 --name myOceanBase -e Mini_mode=0 -d oceanbase/oceanbase-ce
docker exec -it myOceanBase bash
docker logs myOceanBase | tail -1

docker exec -it myOceanBase ob-mysql sys
docker exec -it myOceanBase ob-mysql root


SELECT * FROM oceanbase.DBA_OB_UNIT_CONFIGS;

CREATE RESOURCE UNIT S1_unit_config
                MEMORY_SIZE = '5G',
                MAX_CPU = 3, MIN_CPU = 3,
                LOG_DISK_SIZE = '6G',
                MAX_IOPS = 10000, MIN_IOPS = 10000, IOPS_WEIGHT=1;

SELECT * FROM oceanbase.DBA_OB_UNIT_CONFIGS;


SELECT * FROM oceanbase.DBA_OB_RESOURCE_POOLS;

CREATE RESOURCE POOL mq_pool_01 
                UNIT='S1_unit_config', 
                UNIT_NUM=1, 
                ZONE_LIST=('zone1','zone2'); 


SELECT * FROM oceanbase.DBA_OB_TENANTS;

CREATE TENANT IF NOT EXISTS test1 
                PRIMARY_ZONE='zone1', 
                RESOURCE_POOL_LIST=('mq_pool_01')
                set OB_TCP_INVITED_NODES='%';

>sys
ALTER USER root IDENTIFIED BY '123456'
>root
ALTER USER test  IDENTIFIED BY '123456';

```

### ping 通docker 容器
```text
https://github.com/wenjunxiao/mac-docker-connector/blob/master/README-ZH.md

mac
curl -sSL -o- https://github.com/wenjunxiao/mac-docker-connector/releases/download/v1.0/docker-connector-mac.tar.gz | tar -zxf - -C /usr/local/bin/
brew install wenjunxiao/brew/docker-connector
sudo brew services start docker-connector
docker network ls --filter driver=bridge --format "{{.ID}}" | xargs docker network inspect --format "route {{range .IPAM.Config}}{{.Subnet}}{{end}}" >> "$(brew --prefix)/etc/docker-connector.conf"
    
sudo brew services start docker-connector

docker run -it -d --restart always --net host --cap-add NET_ADMIN --name connector wenjunxiao/mac-docker-connector

如果需要共享给其它人使用，加上expose
route 172.17.0.0/16 expose
并增加监听端口、token 和 ip 分配
expose 0.0.0.0:2512
token user1 192.168.251.10
token user2 192.168.251.11

windows
从 openVPN下载 https://build.openvpn.net/downloads/releases/
https://build.openvpn.net/downloads/releases/latest/openvpn-latest-stable-amd64.msi
https://github.com/wenjunxiao/mac-docker-connector/releases/download/v3.2/docker-accessor-win-x86_64.zip
start-connector.bat
or
install-service.bat
start-service.bat


docker pull wenjunxiao/desktop-docker-connector
docker run -it -d --restart always --net host --cap-add NET_ADMIN --name desktop-connector wenjunxiao/desktop-docker-connector

```


## docker安装mysql
```bash
docker pull mysql:8.0.11
docker run --restart=always -itd --name mysql-test -p 3306:3306 -e MYSQL_ROOT_PASSWORD=123456 mysql:8.0.11 
```
## docker 命令
```bash
http://download.redis.io/redis-stable/

docker network create --driver bridge --subnet 192.167.0.0/16 --gateway 192.167.0.1 mynet

docker ps -a
docker start mysql-test
docker update --restart=always mysql-test
docker inspect 1513d8e488a2

docker exec -it redis-master bash -c "redis-cli -h 127.0.0.1 -p 6379"
docker exec -it redis-slave-1 bash -c "redis-cli -h 127.0.0.1 -p 6379"
docker exec -it redis-slave-2 bash -c "redis-cli -h 127.0.0.1 -p 6379"

https://yiming_chang.gitee.io/vue-pure-admin/#/login
```

## docker 安装 rabbitMQ
```bash
docker run --restart=always -d -p 5672:5672 -p 15672:15672 --name rabbitmq rabbitmq:management
username: guest
password: guest
```
[rabitMq](http://127.0.0.1:15672/#/)  
[docement](https://blog.csdn.net/qq_35387940/article/details/100514134)  

docker run -it --name redis-master -d -p 6300:6300 redis redis-server --requirepass redispassword --port 6300
