package com.workloom.workloom;

import com.workloom.workloom.security.JwtAuthFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
public class SecurityConfig {

    private final JwtAuthFilter jwtAuthFilter;

    public SecurityConfig(JwtAuthFilter jwtAuthFilter) {
        this.jwtAuthFilter = jwtAuthFilter;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            // Disable CSRF since tokens are immune to it
            .csrf(csrf -> csrf.disable())
            
            // Define authorization rules
            .authorizeHttpRequests(auth -> auth
                // Allow unauthenticated access to registration and login endpoints
                .requestMatchers("/api/auth/register", "/api/auth/login").permitAll()
                .requestMatchers("/api/profile/**").authenticated()
                

                // Allow unauthenticated access to swagger or other public endpoints if you add any
                // .requestMatchers("/swagger-ui/**", "/v3/api-docs/**").permitAll() // Example
                
                // All other endpoints require authentication
                .anyRequest().authenticated()
            )
            
            // Stateless session management since we use JWT
            .sessionManagement(session -> session
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            )
            
            // Disable default form login
            .formLogin(form -> form.disable());
        
        // Add your JWT token filter before the UsernamePasswordAuthenticationFilter
        http.addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    // Expose the AuthenticationManager bean (needed for authentication processing)
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }
}
