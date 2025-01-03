package com.example.bookshop;

import com.example.bookshop.models.Role;
import com.example.bookshop.services.MyUserDetailsService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;

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
                        .requestMatchers("/css/**", "/js/**", "/images/**", "/bower_components/**").permitAll()
                        .requestMatchers("/book/**", "/", "/home", "/user/**", "/registration", "/books", "/default").permitAll()
                        .requestMatchers("/cabinet", "/principle/**").authenticated()
//                        .requestMatchers("/admin/**").hasAuthority(String.valueOf(Role.ADMIN))
                                .requestMatchers("/admin/**").hasRole("ADMIN")
                                .anyRequest().authenticated()
                )

                .formLogin(login -> login
                        .loginPage("/login")
                        .defaultSuccessUrl("/default", true)
                        .successForwardUrl("/default")
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

    @ModelAttribute
    public void addAttributes(Model model, HttpServletRequest request) {
        String remoteUser = request.getRemoteUser();
        model.addAttribute("remoteUser", remoteUser != null ? remoteUser : "Guest");
    }


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
        return (request, response, authentication) -> {
            System.out.println("Authentication successful for user: " + authentication.getName());
            response.sendRedirect("/default");
        };
    }
}
