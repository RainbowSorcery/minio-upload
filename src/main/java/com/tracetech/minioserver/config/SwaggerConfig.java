package com.tracetech.minioserver.config;

import com.github.xiaoymin.knife4j.spring.annotations.EnableKnife4j;
import com.google.common.base.Predicates;
import io.swagger.annotations.Api;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.ApiKey;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.List;

import static com.google.common.collect.Lists.newArrayList;

@Configuration
@EnableSwagger2
@EnableKnife4j
public class SwaggerConfig implements WebMvcConfigurer {

//    @Bean
//    public Docket createRestApi() {
//        return new Docket(DocumentationType.SWAGGER_2)
//                .groupName("APP应用模块")
//                .apiInfo(apiInfo())
//                .select()
//                //加了ApiOperation注解的类，才生成接口文档
//                .apis(RequestHandlerSelectors.withClassAnnotation(Api.class))
//                //包下的类，才生成接口文档
//                .apis(Predicates.or(
//                        RequestHandlerSelectors.basePackage("cn.tracetech.envprotection.modules.app.controller")
////                        RequestHandlerSelectors.basePackage("cn.tracetech.modules.job.controller"),
////                        RequestHandlerSelectors.basePackage("cn.tracetech.modules.oss.controller"),
////                        RequestHandlerSelectors.basePackage("cn.tracetech.modules.sys.controller")
//                        )
//                )
//                .paths(PathSelectors.any())
//                .build()
//                .securitySchemes(security());
//    }

    @Bean
    public Docket createRestApi2() {
        return new Docket(DocumentationType.SWAGGER_2)
                .groupName("其他模块")
                .apiInfo(apiInfo())
                .select()
                //加了ApiOperation注解的类，才生成接口文档
                .apis(RequestHandlerSelectors.withClassAnnotation(Api.class))
                //包下的类，才生成接口文档
                .apis(Predicates.or(
                        RequestHandlerSelectors.basePackage("com.tracetech.minioserver.controller")
//                        RequestHandlerSelectors.basePackage("cn.tracetech.modules.oss.controller"),
//                        RequestHandlerSelectors.basePackage("cn.tracetech.modules.sys.controller")
                        )
                )
                .paths(PathSelectors.any())
                .build()
                .securitySchemes(security());
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("浙江全世科技")
                .description("文件服务")
                .termsOfServiceUrl("https://www.tracetech.cn")
                .version("1.0.0")
                .build();
    }

    private List<ApiKey> security() {
        return newArrayList(
//                new ApiKey("token", "token", "header")
        );
    }

}