package demo.reactAdmin.auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    private static final String LOGIN_ENDPOINT = "/api/v1/auth/login";

    private static final String FILE_ENDPOINT = "/api/v1/file/**";

    private static final String SWAGGER_UI_PATH = "/api/v1/swagger-ui.html";

    @Autowired
    private MyUserDetailsService userDetailsService;

    @Autowired
    private PasswordEncoderProvider passwordEncoderProvider;


    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers(FILE_ENDPOINT, SWAGGER_UI_PATH);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http    .cors().and()
                .csrf().disable().authorizeRequests()
                .antMatchers("/").permitAll()
                .antMatchers(HttpMethod.POST, LOGIN_ENDPOINT).permitAll()
                .antMatchers(HttpMethod.GET, FILE_ENDPOINT).permitAll()
                .anyRequest().authenticated()
                .and()
                // We filter the api/login requests
                .addFilterBefore(new JWTLoginFilter(LOGIN_ENDPOINT, authenticationManager()),
                        UsernamePasswordAuthenticationFilter.class)
                // And filter other requests to check the presence of JWT in header
                .addFilterBefore(new JWTAuthenticationFilter(),
                        UsernamePasswordAuthenticationFilter.class);
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {

        auth.authenticationProvider(authenticationProvider());
    }


    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider
                = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService);
        authProvider.setPasswordEncoder(encoder());
        return authProvider;
    }

    @Bean
    public PasswordEncoder encoder() {
        return passwordEncoderProvider.getEncoder();
    }

}