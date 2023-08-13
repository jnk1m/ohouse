package com.ohouse.ohouse.config;

import com.ohouse.ohouse.security.CustomAuthenticationSuccessHandler;
import com.ohouse.ohouse.security.auth.CustomOAuth2UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;

@RequiredArgsConstructor
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
  private final CustomOAuth2UserService customOAuth2UserService;

  @Override
  protected void configure(HttpSecurity http) throws Exception {
    http
            .sessionManagement()
            .invalidSessionUrl("/")
            .sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED)
            .and()
            .csrf()
            .csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse())
            .and()
            .headers().frameOptions().disable()
            .and()
            .authorizeRequests()
            .antMatchers("/", "/menus/**", "/css/**", "/js/**", "/images/**", "/policies/**", "/contacts", "/about").permitAll()
            .antMatchers("/accounts", "/carts").access("hasRole('ROLE_USER') or hasRole('ROLE_ADMIN')")
            .antMatchers("/admin").access("hasRole('ROLE_ADMIN')")
            .anyRequest().authenticated()
            .and()
            .formLogin()
            .loginPage("/oauth2/authorization/google")
            .successHandler(new CustomAuthenticationSuccessHandler())
            .and()
            .logout()
            .logoutSuccessUrl("/")
            .and()
            .oauth2Login()
            .userInfoEndpoint()
            .userService(customOAuth2UserService);

  }


}
