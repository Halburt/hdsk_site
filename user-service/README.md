#提供者注解（实现类上）:
 @Service(包：com.alibaba.dubbo.config.annotation.Service)
 @Component（包：org.springframework.stereotype.Component）
 ###eg:
<pre><code>
 @Component
 @Service(version="1.0.0")
 public class TestServiceImpl implements TestService {

</code></pre>

#消费者注解（Interface上）：
@Reference（包：com.alibaba.dubbo.config.annotation.Reference）
###eg:
<pre><code>
@RestController
public class TestController {

    @Reference(version="1.0.0")
    private TestService testService;

    @RequestMapping("/abc")
    public String test() {
        System.out.println("controller.sucess");
        testService.test();
        return "success";
    }

}
</code></pre>