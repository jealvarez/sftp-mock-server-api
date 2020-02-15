package org.jealvarez.sftpmockserver.configuration;

import com.google.common.base.Predicate;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import static com.google.common.base.Predicates.not;
import static com.google.common.base.Predicates.or;
import static java.util.Arrays.asList;
import static springfox.documentation.builders.PathSelectors.regex;
import static springfox.documentation.spi.DocumentationType.SWAGGER_2;

@Configuration
@EnableSwagger2
public class SwaggerConfiguration {

    @Bean
    public Docket api() {
        return new Docket(SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.any())
                .paths(paths())
                .build()
                .apiInfo(getApiInfo());
    }

    @SuppressWarnings("Guava")
    private Predicate<String> paths() {
        return not(or(asList(
                regex("/oauth.*"),
                regex("/actuator.*"),
                regex("/internal.*"),
                regex("/error.*"),
                regex("/"))
        ));
    }

    private ApiInfo getApiInfo() {
        return new ApiInfoBuilder()
                .title("SFTP Mock Server")
                .description("Handles SFTP connections")
                .version("1.0")
                .build();
    }

    @Controller
    public class SwaggerController {

        @GetMapping("/")
        public String redirectToSwaggerUi() {
            return "redirect:/swagger-ui.html";
        }

    }

}
