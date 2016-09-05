/**
 * 
 */
package com.merlot.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

/**
 * @author Robert Tu - (Sep 1, 2016 2:35:38 PM)
 *
 */
@Configuration
@EnableWebSecurity
@Order(SecurityProperties.ACCESS_OVERRIDE_ORDER)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
	@Autowired
    private UserDetailsService userDetailsService;
	 
	@Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
        		.antMatchers("/", "/merlot", "/signup", "/signon", "/submitLogin", "/error", "/js/(js}", "/css/{css}").permitAll()
                .anyRequest().authenticated()
                .and()
            .formLogin()
                .loginPage("/login")
                .successForwardUrl("/")
                .failureUrl("/login?error")
                .usernameParameter("username")
                .permitAll()
                .and()
            .logout()
            	.logoutUrl("/logout")
            	.deleteCookies("remember-me")
            	.logoutSuccessUrl("/login")
                .permitAll();
        http.csrf().disable();
    }
	
	@Autowired
	public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(userDetailsService).passwordEncoder(new BCryptPasswordEncoder());
	}
}
