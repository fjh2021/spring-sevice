package com.fjh.swagger;

import com.github.xiaoymin.knife4j.spring.annotations.EnableKnife4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2WebMvc;

/**
 * <p>
 *
 * </p>
 *
 * @author fjh
 * @since 2022/1/5 17:06
 */
@Configuration
@EnableSwagger2WebMvc
@EnableKnife4j
public class SwaggerAutoConfig {

    @Value("${swagger.enable:true}")
    private boolean swaggerEnable;
    @Value("${swagger.title}")
    private String swaggerTitle;
    @Value("${swagger.desc}")
    private String swaggerDesc;
    @Value("${swagger.version}")
    private String swaggerVersion;
    @Value("${swagger.group:默认}")
    private String swaggerGroup;

    @Bean
    public Docket createRestApi(Environment environment) {
        return new Docket(DocumentationType.SWAGGER_2).apiInfo(apiInfo())
                .enable(swaggerEnable)
                .select()
                .apis(RequestHandlerSelectors.any())
                .paths(PathSelectors.any()).build().groupName(swaggerGroup);
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title(swaggerTitle)
                .description(swaggerDesc)
                .version(swaggerVersion)
                .build();
    }

}
