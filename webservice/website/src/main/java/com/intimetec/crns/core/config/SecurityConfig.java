package com.intimetec.crns.core.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true)
@Order(SecurityProperties.ACCESS_OVERRIDE_ORDER)
class SecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	private UserDetailsService userDetailsService;

	@Override
    protected void configure(HttpSecurity http) throws Exception {       
        http.authorizeRequests().antMatchers("/register*", "/login", "/forgotPassword", "/signup", "/**").anonymous()
        .antMatchers("/admin","/admin**").hasRole("ADMIN")
        .antMatchers("/user", "/user/**", "/notification", "/notification/**").hasAnyRole("USER", "ADMIN")
        .anyRequest().authenticated()
        .and()
        .logout()
        .logoutUrl("/logout")
        .deleteCookies("remember-me")
        .logoutSuccessUrl("/")
        .permitAll()
        .and()
        .rememberMe();
        
        http.logout().logoutUrl("/logout").invalidateHttpSession(true).logoutSuccessUrl("/");
    }
	
	@Override
	public void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(userDetailsService).passwordEncoder(new BCryptPasswordEncoder());
	}

	@Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers("/v2/api-docs", "/configuration/ui", "/swagger-resources", "/configuration/security", "/swagger-ui.html", "/webjars/**");
    }
}
