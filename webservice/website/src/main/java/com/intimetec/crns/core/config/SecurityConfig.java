package com.intimetec.crns.core.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
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

	@Autowired
	private UserDetailsService userDetailsService;
    @Autowired
    private HttpAuthenticationEntryPoint authenticationEntryPoint;
    @Autowired
    private AuthSuccessHandler successHandler;
    @Autowired
    private AuthFailureHandler failureHandler;
    @Autowired
    private HttpLogoutSuccessHandler logoutSuccessHandler;
    @Autowired
    private AuthenticationProvider authProvider;

    @Bean
    public AuthFilter authFilter() throws Exception {
    	AuthFilter authFilter = new AuthFilter();
    	authFilter.setAuthenticationManager(authenticationManagerBean());
    	authFilter.setAuthenticationSuccessHandler(successHandler);
    	authFilter.setAuthenticationFailureHandler(failureHandler);
    	return authFilter;
    }
    
    @Bean
    public AuthenticationProvider authProvider() {
    	DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setPasswordEncoder(new BCryptPasswordEncoder());
        provider.setUserDetailsService(userDetailsService);
        return provider;
    }

	@Override
    protected void configure(HttpSecurity http) throws Exception {  
		http.csrf().disable()
        .authorizeRequests()
        .antMatchers("/", "/user/register").permitAll()
        .antMatchers("/swagger-ui.html", "/webjars/**").fullyAuthenticated()
        .antMatchers("/*", "/images/**", "/css/**", "/js/**", "/swagger-ui.html").permitAll()
        .antMatchers("/images/favicon.ico").permitAll()
        .antMatchers("/login").permitAll()
        .anyRequest().authenticated()
        .and()
        .exceptionHandling()
        .authenticationEntryPoint(authenticationEntryPoint)
        .and()
        .addFilterBefore(authFilter(), UsernamePasswordAuthenticationFilter.class)
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
		auth.authenticationProvider(authProvider);
	}

	@Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers("/v2/api-docs", "/configuration/ui", "/swagger-resources", "/configuration/security", "/swagger-ui.html", "/webjars/**");
    }
    
    @Bean(name = BeanIds.AUTHENTICATION_MANAGER)
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }
	
}
