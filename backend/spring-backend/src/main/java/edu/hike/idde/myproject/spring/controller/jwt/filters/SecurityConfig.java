package edu.hike.idde.myproject.spring.controller.jwt.filters;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
public class SecurityConfig {

    @Autowired
    JwtAuthenticationFilter jwtAuthenticationFilter;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable)
                .cors(cors -> {})
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/auth/**").permitAll()

                        // 1. Everyone (Authenticated) can view and create
                        .requestMatchers(HttpMethod.PATCH, "/hikes/*/stats/**").authenticated()
                        .requestMatchers(HttpMethod.GET, "/hikes/**").authenticated()
                        .requestMatchers(HttpMethod.POST, "/hikes/**").authenticated()

                        // 2. ONLY Admins can modify or delete
                        .requestMatchers(HttpMethod.PUT, "/hikes/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.PATCH, "/hikes/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.DELETE, "/hikes/**").hasRole("ADMIN")
                        .anyRequest().authenticated()


                )
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

}
