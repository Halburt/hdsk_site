package com.houdask.site.common.swagger;
//swagger2的配置文件，在项目的启动类的同级文件建立
 
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;
 
@Configuration
@EnableSwagger2
public class Swagger2 {
//swagger2的配置文件，这里可以配置swagger2的一些基本的内容，比如扫描的包等等
    @Bean
    public Docket createRestApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .select()
                //为当前包路径
                .apis(RequestHandlerSelectors.basePackage("com.houdask.site"))
                .paths(PathSelectors.regex("(/api/.*)|(/user/login)|(/userTest/getUser2)"))
                .build();
    }

    //构建 api文档的详细信息函数,注意这里的注解引用的是哪个
    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("--厚大系统API文档--")
                .description("如果API文档不全，请及时联系开发者补全。点击链接进群【厚大信息中心】：https://jq.qq.com/?_wv=1027&k=5W6O7lO")
//                .termsOfServiceUrl("http://blog.didispace.com/")
                .contact("Halburt")
                .version("1.0")
                .build();
    }
 
 
}