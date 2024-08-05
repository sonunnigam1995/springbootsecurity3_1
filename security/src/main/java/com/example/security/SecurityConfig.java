package com.example.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import props.DevToolsProps;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
@EnableConfigurationProperties(DevToolsProps.class)
public class SecurityConfig {

    @Autowired
    DevToolsProps props;

    @Bean
    public SecurityFilterChain configureSpringDocSecurity(HttpSecurity http) throws Exception {
        return http.csrf(AbstractHttpConfigurer::disable)
                    .securityMatcher("/v3/api-docs/**","/swagger-ui/**","/swagger-ui.html")
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/**").permitAll())
                .build();
    }

    @Bean
    public SecurityFilterChain configureAppSecurity(HttpSecurity http) throws Exception {
        return http.csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(auth -> auth
                        .anyRequest().authenticated())
                .httpBasic(Customizer.withDefaults())
                .build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public InMemoryUserDetailsManager userDetails(){
        System.out.println(props.getUserPropos().get(0).getUser());
        System.out.println(props.getUserPropos().get(1).getUser());
        UserDetails user1 = User.withUsername(props.getUserPropos().get(0).getUser())
                .password(passwordEncoder().encode(props.getUserPropos().get(0).getPassword()))
                .roles("ADMIN")
                .build();

        UserDetails user2 = User.withUsername(props.getUserPropos().get(1).getUser())
                .password(passwordEncoder().encode(props.getUserPropos().get(1).getPassword()))
                .roles("USER")
                .build();

        return new InMemoryUserDetailsManager(user1,user2);
    }

}
