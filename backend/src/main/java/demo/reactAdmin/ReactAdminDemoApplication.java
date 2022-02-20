package demo.reactAdmin;

import java.util.Arrays;
import java.util.Collections;

import javax.servlet.ServletContext;

import com.fasterxml.jackson.databind.ObjectMapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.Banner;
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

import demo.reactAdmin.crud.services.DataInitService;
import demo.reactAdmin.providers.ObjectMapperProvider;

@SpringBootApplication(scanBasePackages = {"demo.reactAdmin", "springboot.rest"})
public class ReactAdminDemoApplication {


    @Autowired
    private DataInitService dataInitService;

    @Autowired
    private ObjectMapperProvider objMapperProvider;

    @Bean
    public CorsFilter corsFilter() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowCredentials(true);
        config.setAllowedOriginPatterns(Collections.singletonList("*")); // Insecure, but for demo purposes it's ok
        config.setAllowedHeaders(Arrays.asList("Origin", "Content-Type", "Accept", "responseType", "Authorization", "x-authorization", "content-range"));
        config.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "OPTIONS", "DELETE", "PATCH"));
        config.setExposedHeaders(Arrays.asList("X-Total-Count", "X-Total-Count", "content-range", "Content-Type", "Accept", "X-Requested-With", "remember-me"));
        source.registerCorsConfiguration("/**", config);
        return new CorsFilter(source);
    }

    public static void main(String[] args) {
        SpringApplication application = new SpringApplication(ReactAdminDemoApplication.class);
        application.setBannerMode(Banner.Mode.OFF);
        application.run(args);
    }

    @EventListener(ApplicationReadyEvent.class)
    public void afterReady() {
        dataInitService.init();
    }

    @Bean
    public ViewResolver internalResourceViewResolver() {
        InternalResourceViewResolver bean = new InternalResourceViewResolver();
        bean.setPrefix("/uploaded/");
        return bean;
    }

    @Bean
    public ObjectMapper objectMapper() {
        return objMapperProvider.getObjectMapper();
    }

}
