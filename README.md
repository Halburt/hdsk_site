# 项目说明
##技术选型
<ul>
<li>Spring Boot</li>
<li>shiro</li>
<li>redis</li>
<li>dubbox</li>
<li>activeMQ</li>
<li>MySQL</li>
<li>druid</li>
<li>zookeeper</li>
<li>Mybatis</li>
<li>swagger</li>
</ul>

##项目模块说明
<p>
<b>service</b> 为业务层 即服务提供者（包含 service实现及facadeService实现、Dao）
</p>
 <p>
<b>facade</b>为接口层（只定义 接口类 与 实体类）
</p>
<p>
<b>web</b>为web端服务消费者 (通过facade层   调用service层 )
</p>
<p>
<b>api</b>为移动端服务消费者 (通过facade层   调用service层 )
</p>

##具体分包说明
**auth-jwt  组件:基础JWT插件（待完善）**<br>
**auth-shiro 组件：认证shiro模块**
**auth-user 组件：认证使用的对象** <br>
**common 组件：提供基础代码Base层**
**common-activemq 组件：MQ集成**
**common-base-service 快速开发模块：微服务模块，直接依赖+简单配置就可以开发运行**
**common-base-web 快速开发模块：Web模块，直接依赖+简单配置就可以开发运行**
common-db
common-redis
common-spring
common-swagger
common-utils
generate-code
logs
sys-user
test-address
user-facade
user-service
web

##准备工作
### 安装Redis
### 安装Zookeeper
### 安装MySQL


