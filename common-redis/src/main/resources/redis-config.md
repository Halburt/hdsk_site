#Redis 配置
## Redis数据库索引（默认为0）
spring.redis.database=0
## Redis服务器地址
spring.redis.host=127.0.0.1
## Redis服务器连接端口
spring.redis.port=6379
## Redis服务器连接密码（默认为空）
spring.redis.password=
## 连接池最大连接数（使用负值表示没有限制）
spring.redis.pool.max-active=8
## 连接池最大阻塞等待时间（使用负值表示没有限制）
spring.redis.pool.max-wait=-1
## 连接池中的最大空闲连接
spring.redis.pool.max-idle=8
## 连接池中的最小空闲连接
spring.redis.pool.min-idle=0
## 连接超时时间（毫秒）
spring.redis.timeout=0



# ====================redis=yml===================
redis:
  # Redis服务器地址
  host: ason-hostname
  # Redis服务器连接端口
  port: 6379
  # Redis服务器连接密码（默认为空）
  password:
  # 连接超时时间（毫秒）
  timeout: 0
  # Redis数据库索引（默认为0）
  database: 0
  pool:
    # 连接池最大连接数（使用负值表示没有限制）
    max-active: 8
    # 连接池最大阻塞等待时间（使用负值表示没有限制）
    max-wait: -1
    # 连接池中的最大空闲连接
    max-idle: 8
    # 连接池中的最小空闲连接
    min-idle: 0