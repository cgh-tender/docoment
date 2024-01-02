```bash
docker pull mysql:8.0.11
docker run -itd --name mysql-test -p 3306:3306 -e MYSQL_ROOT_PASSWORD=123456 mysql:8.0.11


```

## redis master slave1 slave2
### redis_master.config
```text
# 绑定
bind 0.0.0.0
# 连接保护模式关闭
protected-mode no
# 端口
port 6379
# 数据库数量
databases 16
# rdb快照规则
# 在900秒（15分钟）后，如果至少更改了1个键
save 900 1
save 300 10
save 60 10000
# rdb快照文件名称
dbfilename dump.rdb
# 客户端连接数量设置
maxclients 10000
# AOF模式开启，默认关闭
appendonly yes
# 文件名
appendfilename "appendonly.aof"
# AOF同步机制 
# 每秒同步
appendfsync everysec
# 文件大小切割
auto-aof-rewrite-percentage 100
auto-aof-rewrite-min-size 64mb
# 淘汰机制设置
maxmemory-policy noeviction

#密码设置
requirepass 123456
```
### redis_slave.conf
```text
replicaof 172.17.0.4 6379
port 6379
```

```bash
docker run  --restart=always  --name redis-master  -v F:\TOOLS\redisms\config\redis_master.conf:/etc/redis/redis.conf  -v F:\TOOLS\redisms\config\data:/data  -p 63790:6379  -d redis:latest redis-server /etc/redis/redis.conf

docker ps 
docker inspect 1513d8e488a2
172.17.0.4


docker run  --restart=always  --name redis-slave-1  -v F:\TOOLS\redisms\config\redis_slave.conf:/etc/redis/redis.conf  -v F:\TOOLS\redisms\config\data:/data  -p 63791:6379  -d redis:latest redis-server /etc/redis/redis.conf
docker run  --restart=always  --name redis-slave-2  -v F:\TOOLS\redisms\config\redis_slave.conf:/etc/redis/redis.conf  -v F:\TOOLS\redisms\config\data:/data  -p 63792:6379  -d redis:latest redis-server /etc/redis/redis.conf
// slaveof 172.17.0.4 6379

docker exec -it redis-master bash -c "redis-cli -h 127.0.0.1 -p 6379"
docker exec -it redis-slave-1 bash -c "redis-cli -h 127.0.0.1 -p 6379"
docker exec -it redis-slave-2 bash -c "redis-cli -h 127.0.0.1 -p 6379"

```