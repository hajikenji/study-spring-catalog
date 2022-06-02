package com.example.study.config;

import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configurable
@EnableWebSecurity
public class SecurityConfig {

  @Bean
  public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }

  @Bean
  public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
    http.formLogin(login -> login
        // .loginProcessingUrl("/login")
        // .loginPage("/login")
        .defaultSuccessUrl("/")
        .failureUrl("/login?error")
        .permitAll()).logout(logout -> logout
            .logoutSuccessUrl("/"))
        .authorizeHttpRequests(authz -> authz
            .antMatchers("login", "/").permitAll()
            // .mvcMatchers("/").permitAll()
            // .mvcMatchers("/general").hasRole("GENERAL")
            // .mvcMatchers("/admin").hasRole("ADMIN")
            .anyRequest().authenticated());
    return http.build();
  }

  // public void configure(AuthenticationManagerBuilder auth) throws Exception {
  // auth.inMemoryAuthentication()
  // .withUser("user")
  // .password(passwordEncoder().encode("aaaa"))
  // .authorities("USER");
  // }

  @Bean
  public UserDetailsService users() {
    UserDetails user = User.builder()
        .username("user")
        .password(passwordEncoder().encode("aaaa"))
        .roles("USER")
        .build();
    UserDetails admin = User.builder()
        .username("admin")
        .password(passwordEncoder().encode("aaaa"))
        .roles("USER", "ADMIN")
        .build();
    return new InMemoryUserDetailsManager(user, admin);
  }

}