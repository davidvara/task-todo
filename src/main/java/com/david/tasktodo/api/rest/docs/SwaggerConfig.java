package com.david.tasktodo.api.rest.docs;

import com.google.common.base.Predicates;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import static springfox.documentation.builders.PathSelectors.regex;

@Configuration
@EnableSwagger2
@ComponentScan(basePackages = {
        "com.david.tasktodo.api.rest",
})
public class SwaggerConfig {


    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.any())
                .paths(Predicates.not(regex("/error")))
                .build()
                .apiInfo(apiInfo());
    }


    private ApiInfo apiInfo() {
        String description = "REST task-todo";
        return new ApiInfoBuilder()
                .title("REST task-todo")
                .description(description)
                .termsOfServiceUrl("github")
                .license("David")
                .licenseUrl("")
                .version("1.0")
                .build();
    }

}