docker network create --driver bridge --subnet 192.169.0.0/16 --gateway 192.169.0.1 redis-sentinel

docker run --restart=always --name redis-master --net redis-sentinel --ip 192.169.0.2 -v /Users/cgh/Tool/redis/redis.conf:/etc/redis/redis.conf -p 63790:6379 -d redis:latest redis-server /etc/redis/redis.conf
docker run --restart=always --name redis-slave1 --net redis-sentinel --ip 192.169.0.3 -v /Users/cgh/Tool/redis/redis-s.conf:/etc/redis/redis.conf -p 63791:6379 -d redis:latest redis-server /etc/redis/redis.conf
docker run --restart=always --name redis-slave2 --net redis-sentinel --ip 192.169.0.4 -v /Users/cgh/Tool/redis/redis-s.conf:/etc/redis/redis.conf -p 63792:6379 -d redis:latest redis-server /etc/redis/redis.conf

docker run --restart=always --name redis-sentinel1 --net redis-sentinel --ip 192.169.0.5 -v /Users/cgh/Tool/redis/sentinel.conf:/etc/redis/sentinel.conf -p 63793:26379 -d redis:latest redis-server /etc/redis/sentinel.conf --sentinel
docker run --restart=always --name redis-sentinel2 --net redis-sentinel --ip 192.169.0.6 -v /Users/cgh/Tool/redis/sentinel.conf:/etc/redis/sentinel.conf -p 63794:26379 -d redis:latest redis-server /etc/redis/sentinel.conf --sentinel
docker run --restart=always --name redis-sentinel3 --net redis-sentinel --ip 192.169.0.7 -v /Users/cgh/Tool/redis/sentinel.conf:/etc/redis/sentinel.conf -p 63795:26379 -d redis:latest redis-server /etc/redis/sentinel.conf --sentinel

