package com.apgjavadev.core;

import com.apgjavadev.user.DetailsService;
import com.apgjavadev.user.User;
import net.minidev.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.AuthenticationEntryPoint;

import java.time.LocalDateTime;
import javax.servlet.http.HttpServletResponse;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfiguration extends WebSecurityConfigurerAdapter {

  DetailsService userDetailsService;

  @Autowired
  public WebSecurityConfiguration(DetailsService userDetailsService) {
    this.userDetailsService = userDetailsService;
  }

  @Override
  protected void configure(AuthenticationManagerBuilder auth) throws Exception {
    auth.userDetailsService(userDetailsService)
        .passwordEncoder(User.PASSWORD_ENCODER);
  }

  @Override
  protected void configure(HttpSecurity http) throws Exception {
    http
        .authorizeRequests()
          .anyRequest().authenticated()
        .and()
        .httpBasic()
        .and()
        .exceptionHandling()
        .authenticationEntryPoint(exceptionHandler())
        .and()
        .csrf().disable();
  }

  private AuthenticationEntryPoint exceptionHandler() {
    return (((request, response, authException) -> {
      response.setContentType("application/json;charset=UTF-8");
      response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
      response.getWriter().write(new JSONObject()
          .appendField("timestamp", LocalDateTime.now().toString())
          .appendField("message", "Full authentication is required to access this resource")
          .appendField("path", request.getRequestURI())
          .appendField("status", 401)
          .appendField("error", "Unauthorized")
          .toString());
    }));
  }

}
