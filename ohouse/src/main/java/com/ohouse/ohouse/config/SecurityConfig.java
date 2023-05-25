package com.ohouse.ohouse.config;

import com.ohouse.ohouse.enums.Role;
import com.ohouse.ohouse.security.CustomAuthenticationSuccessHandler;
import com.ohouse.ohouse.security.auth.CustomOAuth2UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@RequiredArgsConstructor
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
  private final CustomOAuth2UserService customOAuth2UserService;

  @Override
  protected void configure(HttpSecurity http) throws Exception {
    http
            .csrf().disable()
            .headers().frameOptions().disable()
            .and()
            .authorizeRequests()
            .antMatchers("/", "/menus/**", "/css/**", "/js/**", "/images/**","/policies/**","/contacts","/about").permitAll()
            .antMatchers("/mypage").hasRole(Role.USER.name())
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
