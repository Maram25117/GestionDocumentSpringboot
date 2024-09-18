package com.example.Gestiondocument.config;
import org.springframework.context.annotation.Bean;
import com.example.Gestiondocument.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;


@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private UserService userService;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userService);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable()
                .cors() // Ensure CORS settings are configured if needed
                .and()
                .authorizeRequests()
                //.antMatchers("/file/upload", "/file/delete/**").hasRole("ADMIN") // Only admins can upload and delete files
                .antMatchers("/file/**").permitAll() // Both users and admins can download and open files
                .antMatchers("/user/**").permitAll() // Allow all users to access user endpoints
                .antMatchers("/matiere/**").permitAll() // Allow all users to access user endpoints
                .anyRequest().authenticated() // All other requests require authentication
                .and()
                .httpBasic(); // Basic authentication for simplicity
    }


    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**")
                        .allowedOrigins("http://localhost:4200")
                        .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
                        .allowedHeaders("*")
                        .allowCredentials(true);
            }
        };
    }
}
