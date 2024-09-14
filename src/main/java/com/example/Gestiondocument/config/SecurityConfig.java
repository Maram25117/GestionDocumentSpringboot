/*package com.example.Gestiondocument.config;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class SecurityConfig {

    @Bean
    public UserDetailsService userDetailsService() {
        InMemoryUserDetailsManager manager = new InMemoryUserDetailsManager();
        // Admin user
        manager.createUser(User.withUsername("admin")
                .password("admin123")
                .roles("ADMIN").build());
        // Normal user
        manager.createUser(User.withUsername("user")
                .password("user123")
                .roles("USER").build());
        return manager;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf().disable()
                .authorizeRequests()
                // Routes accessibles seulement par l'ADMIN (ajout, suppression de documents)
                .antMatchers("/api/documents/add", "/api/documents/delete/**").hasRole("ADMIN")
                // Routes accessibles à l'ADMIN et USER (affichage des documents)
                .antMatchers("/api/documents", "/api/documents/view/**").hasAnyRole("ADMIN", "USER")
                .anyRequest().authenticated()
                .and()
                .formLogin().permitAll()
                .and()
                .logout().permitAll();
        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return NoOpPasswordEncoder.getInstance(); // Mots de passe en texte clair (pour les tests)
    }
}*/

/*package com.example.Gestiondocument.config;

import com.example.Gestiondocument.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Autowired
    private UserService userService;

   /* @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .authorizeRequests()
                //.antMatchers("/document/add").hasRole("ADMIN")  // Admin can create, delete, and display documents
                //.antMatchers("/document/delete/{id}").hasRole("ADMIN")
                .antMatchers("/**").permitAll()
                .antMatchers("/user/register").permitAll()// Register endpoint accessible to all
                .antMatchers("/user/login").permitAll()
                .anyRequest().authenticated()
                .and()
                .formLogin()
                .and()
                .logout().permitAll();

        return http.build();
    }

    @Configuration
    public class WebConfig implements WebMvcConfigurer {
        @Override
        public void addCorsMappings(CorsRegistry registry) {
            registry.addMapping("/**")
                    .allowedOrigins("http://localhost:4200") // URL de votre frontend Angular
                    .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
                    .allowedHeaders("*")
                    .allowCredentials(true);
        }
    }


    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();  // Use BCrypt for password encoding
    }

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userService).passwordEncoder(passwordEncoder());
    }
}*/
/*package com.example.Gestiondocument.config;

import com.example.Gestiondocument.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(securedEnabled = true) // Active @Secured pour les méthodes
public class SecurityConfig {

    @Autowired
    private UserService userService;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .cors()
                .and()
                .authorizeRequests()
                .antMatchers("/user/register", "/user/login").permitAll() // Endpoints accessibles à tous
                .antMatchers("/document/**").authenticated() // Accessible uniquement aux utilisateurs authentifiés
                .anyRequest().authenticated(); // Les autres requêtes nécessitent une authentification

        return http.build();
    }

    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**")
                        .allowedOrigins("http://localhost:4200") // URL de votre frontend Angular
                        .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
                        .allowedHeaders("*")
                        .allowCredentials(true);
            }
        };
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();  // Utilise BCrypt pour le hachage des mots de passe
    }

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userService).passwordEncoder(passwordEncoder());
    }
}*/
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


/*Ce fichier SecurityConfig est une configuration de sécurité pour une application Spring Boot qui utilise Spring Security.
 Il configure les règles de sécurité,le service d'authentification, et le support pour CORS (Cross-Origin Resource Sharing).*/


/*@Configuration /*Indique que cette classe définit des configurations pour Spring.
Elle sera détectée et utilisée au démarrage de l'application.*/
/*@EnableWebSecurity /*Active la configuration de la sécurité Web avec Spring Security,
 //permettant de définir des règles de sécurité comme l'authentification et l'autorisation.*/
/*public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired /*Injecte une instance de UserService, qui implémente UserDetailsService.
    Ce service sera utilisé pour charger les détails des utilisateurs lors de l'authentification.*/
   /* private UserService userService;

    @Override /*Cette méthode redéfinit la méthode configure de la classe WebSecurityConfigurerAdapter.
     Par défaut, cette méthode n'a pas de comportement spécifique, mais vous la redéfinissez pour configurer la gestion
     de l'authentification en indiquant à Spring Security d'utiliser votre UserService
     personnalisé pour gérer l'authentification des utilisateurs.*/
    /*protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        // Supprimez passwordEncoder()
        auth.userDetailsService(userService);
    }

    @Override /*Cette méthode redéfinit également la méthode configure de la classe WebSecurityConfigurerAdapter,
     mais cette fois-ci pour configurer la sécurité des requêtes HTTP.
     Par défaut, Spring Security protège toutes les requêtes, mais ici vous personnalisez ce comportement.*/
    /*protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable()
                .cors()
                .and()
                .authorizeRequests()
                .antMatchers("/**").permitAll()
                .anyRequest().authenticated();

         /*Cette méthode définit les règles de sécurité pour les requêtes HTTP :
         http.csrf().disable() : Désactive la protection CSRF (Cross-Site Request Forgery). Cette option est désactivée ici,
         probablement pour simplifier les requêtes pendant le développement, mais elle devrait être activée pour
         protéger l'application en production.
         cors() : Active la prise en charge de CORS pour les échanges entre des domaines différents
         (comme entre l'API backend et une interface utilisateur Angular située sur un autre serveur).
         authorizeRequests() : Définit des règles d'autorisation :
         antMatchers("/**").permitAll() : Permet l'accès à toutes les routes (indiqué par le /**) sans authentification.
         anyRequest().authenticated() : Toutes les autres requêtes nécessitent une authentification.*/
  //  }

    /*@Bean /*L'annotation @Bean dans ce cas enregistre un bean de configuration CORS qui
     permet à l'application de définir explicitement quelles origines (ici http://localhost:4200)
     sont autorisées à faire des requêtes à votre API Spring Boot. Ce bean est essentiel si
     votre frontend (par exemple Angular) et backend sont sur des serveurs différents,
     car il permet de résoudre les problèmes de partage des ressources entre les domaines.*/

    /*public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**")
                        .allowedOrigins("http://localhost:4200")
                        .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
                        .allowedHeaders("*")
                        .allowCredentials(true);
            }
             /*Cette méthode définit les règles CORS pour permettre les requêtes venant
         de l'interface utilisateur Angular ou d'un autre frontend :
         allowedOrigins("http://localhost:4200") : Autorise les requêtes provenant
         de localhost:4200 (souvent utilisé pour une application Angular).
         allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS") : Permet les méthodes HTTP spécifiques.
         allowedHeaders("*") : Autorise tous les types d'en-têtes dans les requêtes.
         allowCredentials(true) : Autorise l'envoi de cookies (ou d'informations d'authentification).*/

       // };
    //}
//}

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
