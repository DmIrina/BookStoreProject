package com.example.bookshop;

import com.example.bookshop.models.Role;
import com.example.bookshop.services.MyUserDetailsService;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import java.io.IOException;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig {

    private final MyUserDetailsService myUserDetailsService;

    public WebSecurityConfig(MyUserDetailsService myUserDetailsService) {
        this.myUserDetailsService = myUserDetailsService;
    }


    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(auth -> auth
//                                .requestMatchers("/css/**", "/js/**", "/images/**").permitAll()  // дозволяє доступ до CSS/JS/Images
//                                .requestMatchers("/book/?", "/", "/home", "/user/**", "/registration", "/books", "/default").permitAll()
//                                .requestMatchers("/bower_components/**").permitAll()
//                                .requestMatchers("/cabinet", "/principle/**").authenticated()
//                        .requestMatchers("/**").hasAuthority(String.valueOf(Role.ADMIN))
//                                .requestMatchers("/**").authenticated()
//                                .anyRequest().authenticated()
                                .requestMatchers("/css/**", "/js/**", "/images/**", "/bower_components/**").permitAll()
                                .requestMatchers("/book/**", "/", "/home", "/user/**", "/registration", "/books", "/default", "/login").permitAll()
                                .requestMatchers("/cabinet", "/principle/**").authenticated()
                                .requestMatchers("/admin/**").hasAuthority(String.valueOf(Role.ADMIN))
                                .anyRequest().authenticated()
                )

                .formLogin(login -> login
                        .loginPage("/login")
                        .defaultSuccessUrl("/default", true) // Завжди перенаправляти на /default після входу
                        .permitAll()
                )
                .logout(logout -> logout
                        .permitAll()
                        .deleteCookies("JSESSIONID")
                )
                .exceptionHandling(ex -> ex
                        .accessDeniedHandler((request, response, accessDeniedException) -> {
                            response.sendRedirect("/accessDenied");
                        })
                );


        return http.build();
    }


//    @Bean
//    public DaoAuthenticationProvider authenticationProvider() {
//        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
//        authProvider.setUserDetailsService(myUserDetailsService);
//        authProvider.setPasswordEncoder(passwordEncoder());
//        return authProvider;
//    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authConfig) throws Exception {
        return authConfig.getAuthenticationManager();
    }

    @Bean
    public AuthenticationSuccessHandler authenticationSuccessHandler() {
        return new AuthenticationSuccessHandler() {
            @Override
            public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                                Authentication authentication) throws IOException, ServletException {
                System.out.println("Authentication successful for user: " + authentication.getName());
                response.sendRedirect("/default");  // Направляє користувача на сторінку після успішного входу
            }
        };
    }
}
