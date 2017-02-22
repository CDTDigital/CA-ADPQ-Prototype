package com.intimetec.crns.core.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.BeanIds;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import com.intimetec.crns.core.authentication.AuthFailureHandler;
import com.intimetec.crns.core.authentication.AuthFilter;
import com.intimetec.crns.core.authentication.AuthSuccessHandler;
import com.intimetec.crns.core.authentication.HttpAuthenticationEntryPoint;
import com.intimetec.crns.core.authentication.HttpLogoutSuccessHandler;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
@ComponentScan(value = "com.intimetec.crns.core.authentication")
@Order(SecurityProperties.ACCESS_OVERRIDE_ORDER)
class SecurityConfig extends WebSecurityConfigurerAdapter {
	private static final String LOGIN_PATH = "/login";
	private static final String USERNAME = "username";
	private static final String PASSWORD = "password";

	@Autowired
	private UserDetailsService userDetailsService;
    @Autowired
    private HttpAuthenticationEntryPoint authenticationEntryPoint;
    @Autowired
    private AuthSuccessHandler authSuccessHandler;
    @Autowired
    private AuthFailureHandler authFailureHandler;
    @Autowired
    private HttpLogoutSuccessHandler logoutSuccessHandler;
    
    @Bean(name = BeanIds.AUTHENTICATION_MANAGER)
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }


	@Override
    protected void configure(HttpSecurity http) throws Exception {       
		http.csrf().disable()
        .authorizeRequests()
        .antMatchers("/").permitAll()
        .antMatchers("/*", "/images/**", "/css/**", "/js/**").permitAll()
        .antMatchers("/images/favicon.ico").permitAll()
        .antMatchers("/login").permitAll()
        .anyRequest().authenticated()
        .and()
        .exceptionHandling()
        .authenticationEntryPoint(authenticationEntryPoint)
        .and().addFilterAt(new AuthFilter(), UsernamePasswordAuthenticationFilter.class)
        .formLogin()
        .permitAll()
        .loginProcessingUrl(LOGIN_PATH)
        .usernameParameter(USERNAME)
        .passwordParameter(PASSWORD)
        .successHandler(authSuccessHandler)
        .failureHandler(authFailureHandler)
        .and()
        .logout()
        .permitAll()
        .logoutRequestMatcher(new AntPathRequestMatcher(LOGIN_PATH, "DELETE"))
        .logoutSuccessHandler(logoutSuccessHandler)
        .and()
        .sessionManagement()
        .maximumSessions(1);

		http.authorizeRequests().anyRequest().authenticated();
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
