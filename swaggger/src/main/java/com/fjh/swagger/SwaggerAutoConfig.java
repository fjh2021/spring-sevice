package com.fjh.swagger;

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

/**
 * <p>
 *
 * </p>
 *
 * @author fjh
 * @since 2022/1/5 17:06
 */
@Configuration
public class SwaggerAutoConfig {

    @Value("${swagger.enable:true}")
    private boolean swaggerEnable;
    @Value("${swagger.title}")
    private String swaggerTitle;
    @Value("${swagger.desc}")
    private String swaggerDesc;
    @Value("${swagger.version}")
    private String swaggerVersion;
    @Bean
    public Docket createRestApi(Environment environment) {

        return new Docket(DocumentationType.SWAGGER_2).apiInfo(apiInfo())
                .enable(swaggerEnable)
                .select()
                .apis(RequestHandlerSelectors.any())
                .paths(PathSelectors.any()).build();
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title(swaggerTitle)
                .description(swaggerDesc)
                .version(swaggerVersion)
                .build();
    }

}
