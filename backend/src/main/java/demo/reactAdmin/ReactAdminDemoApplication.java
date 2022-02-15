package demo.reactAdmin;

import demo.reactAdmin.crud.services.DataInitService;
import com.fasterxml.jackson.databind.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.annotation.Bean;
import org.springframework.context.event.EventListener;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.view.InternalResourceViewResolver;
import springboot.rest.providers.ObjectMapperProvider;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.paths.RelativePathProvider;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import javax.servlet.ServletContext;

@SpringBootApplication(scanBasePackages = {"demo.reactAdmin", "springboot.rest"})
@EnableSwagger2
public class ReactAdminDemoApplication {


    @Autowired
    private DataInitService dataInitService;

    @Autowired
    private ServletContext servletContext;

    @Autowired
    private ObjectMapperProvider objMapperProvider;

    @Bean
    public CorsFilter corsFilter() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowCredentials(true);
        config.addAllowedOrigin("*");
        config.addAllowedHeader("*");
        config.addAllowedHeader("Content-Range");
        config.addExposedHeader("X-Total-Count");
        config.addExposedHeader("Content-Range");
        config.addExposedHeader("Content-Type");
        config.addExposedHeader("Accept");
        config.addExposedHeader("X-Requested-With");
        config.addExposedHeader("remember-me");
        config.addAllowedMethod("*");
        source.registerCorsConfiguration("/**", config);
        return new CorsFilter(source);
    }

    public static void main(String[] args) {
        SpringApplication.run(ReactAdminDemoApplication.class, args);
    }

    @EventListener(ApplicationReadyEvent.class)
    public void afterReady() {
        dataInitService.init();
    }

    @Bean
    public ViewResolver internalResourceViewResolver() {
        InternalResourceViewResolver bean = new InternalResourceViewResolver();
        //bean.setViewClass(JstlView.class);
        bean.setPrefix("/uploaded/");
        //bean.setSuffix(".jsp");
        return bean;
    }

    @Bean
    public ObjectMapper objectMapper() {
        return objMapperProvider.getObjectMapper();
    }

    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2)
                .pathProvider(new RelativePathProvider(servletContext) {
                    @Override
                    public String getApplicationBasePath() {
                        return "/api/v1";
                    }
                })
                .select()
                .apis(RequestHandlerSelectors.any())
                .paths(PathSelectors.any())

                .build();
    }

}
