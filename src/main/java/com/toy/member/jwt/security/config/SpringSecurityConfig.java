package com.toy.member.jwt.security.config;

import com.toy.member.jwt.security.handler.CustomAccessDeniedHandler;
import com.toy.member.jwt.security.handler.CustomAuthenticationFailureHandler;
import com.toy.member.jwt.security.handler.CustomAuthenticationSuccessHandler;
import com.toy.member.jwt.security.provider.CustomAuthenticationProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationDetailsSource;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SpringSecurityConfig extends WebSecurityConfigurerAdapter{

	@Autowired
	private AuthenticationDetailsSource authenticationDetailsSource;

	@Autowired
	private AuthenticationSuccessHandler authenticationSuccessHandler;

	@Autowired
	private AuthenticationFailureHandler authenticationFailureHandler;

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.authenticationProvider(authenticationProvider());
	}

	@Override
	public void configure(WebSecurity web) {
		web.ignoring().requestMatchers(PathRequest.toStaticResources().atCommonLocations());
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {

		http
				.csrf()
				.disable();
		http
				.authorizeRequests()
				.antMatchers("/login","/login**").permitAll()
				.antMatchers("/sign-up").permitAll()
				.antMatchers("/api/sign-up").permitAll()
				.antMatchers("/my-page", "/main").authenticated()
				.antMatchers("/my-page").hasRole("ADMIN")
				.anyRequest().authenticated();

		http.formLogin()
				.loginPage("/login")
				.loginProcessingUrl("/api/login/process")
				.authenticationDetailsSource(authenticationDetailsSource)
				.defaultSuccessUrl("/main")
				.successHandler(authenticationSuccessHandler)
				.failureHandler(authenticationFailureHandler)
				.permitAll();
		http
				.exceptionHandling()
				.accessDeniedHandler(accessDeniedHandler());

	}
	@Bean
	public AccessDeniedHandler accessDeniedHandler(){
		return new CustomAccessDeniedHandler("/access-deny");
	}
	@Bean
	public AuthenticationProvider authenticationProvider(){
		return new CustomAuthenticationProvider();
	}

	@Bean
	public PasswordEncoder passwordEncoder() {

		return new BCryptPasswordEncoder();
	}
	@Bean
	public AuthenticationSuccessHandler customAuthenticationSuccessHandler(){
		return new CustomAuthenticationSuccessHandler();
	}
	@Bean
	public AuthenticationFailureHandler customAuthenticationFailureHandler(){
		return new CustomAuthenticationFailureHandler();
	}

}