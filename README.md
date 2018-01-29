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
<b>web</b>为服务消费者 (通过facade层   调用service层 )
</p>

