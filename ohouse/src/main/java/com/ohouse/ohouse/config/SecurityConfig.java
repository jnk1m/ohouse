package com.ohouse.ohouse.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

  @Bean
  public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
    http
            .authorizeHttpRequests((authz) -> authz
                    .antMatchers("/**").permitAll()
                    .anyRequest().permitAll()
            )
            .formLogin()
            .and()
            .httpBasic()
            .and()
            .csrf().disable()
            .anonymous()
            .authorities("ROLE_ANONYMOUS");

    return http.build();

  }

}
