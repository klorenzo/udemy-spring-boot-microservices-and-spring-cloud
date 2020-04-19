package com.kevinlorenzo.api.users.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.kevinlorenzo.api.users.service.UsersService;

@Configuration
@EnableWebSecurity
public class WebSecurity extends WebSecurityConfigurerAdapter {

	private Environment environment;

	private UsersService usersService;

	private BCryptPasswordEncoder bCryptPasswordEncoder;

	@Autowired
	public WebSecurity(Environment environment, UsersService usersService,
			BCryptPasswordEncoder bCryptPasswordEncoder) {
		this.environment = environment;
		this.usersService = usersService;
		this.bCryptPasswordEncoder = bCryptPasswordEncoder;
	}

	@Override
	protected void configure(HttpSecurity httpSecurity) throws Exception {
		httpSecurity.csrf().disable();
		// httpSecurity.authorizeRequests().antMatchers("/users/**").permitAll();
		/* httpSecurity.authorizeRequests().antMatchers("/**").hasIpAddress(environment.getProperty("gateway.ip")).and()
		.addFilter(getAuthenticationFilter()); */
		httpSecurity.addFilter(getAuthenticationFilter());
		httpSecurity.headers().frameOptions().disable();
	}

	private AuthenticationFilter getAuthenticationFilter() throws Exception {
		AuthenticationFilter authenticationFilter = new AuthenticationFilter(usersService, environment,
				authenticationManager());
		// authenticationFilter.setAuthenticationManager(authenticationManager());
		return authenticationFilter;
	}

	@Override
	protected void configure(AuthenticationManagerBuilder authenticationManagerBuilder) throws Exception {
		authenticationManagerBuilder.userDetailsService(usersService).passwordEncoder(bCryptPasswordEncoder);
	}

}
