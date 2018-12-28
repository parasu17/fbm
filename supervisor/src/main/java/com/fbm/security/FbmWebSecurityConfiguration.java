package com.fbm.security;

import com.fasterxml.jackson.databind.ObjectMapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.preauth.RequestHeaderAuthenticationFilter;
import org.springframework.security.web.authentication.session.ChangeSessionIdAuthenticationStrategy;
import org.springframework.security.web.authentication.session.CompositeSessionAuthenticationStrategy;
import org.springframework.security.web.authentication.session.SessionAuthenticationStrategy;
import org.springframework.security.web.csrf.CsrfAuthenticationStrategy;
import org.springframework.security.web.csrf.CsrfTokenRepository;
import org.springframework.security.web.csrf.HttpSessionCsrfTokenRepository;
import org.springframework.security.web.csrf.LazyCsrfTokenRepository;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import java.util.Arrays;

import javax.sql.DataSource;

@Configuration
@EnableWebSecurity
public class FbmWebSecurityConfiguration extends WebSecurityConfigurerAdapter {
	
	private static final String SELECT_USER = "select username, password, 'true' as enabled from users where username=?";
	private static final String SELECT_AUTHORITY = "select username, 'ROLE_USER' as authority from users where username=?";

	@Autowired
	private DataSource dataSource;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
    	auth.jdbcAuthentication().dataSource(dataSource)
    		.usersByUsernameQuery(SELECT_USER)
    		.authoritiesByUsernameQuery(SELECT_AUTHORITY)
    		.passwordEncoder(passwordEncoder());
//        auth.inMemoryAuthentication()
//            .withUser("parasu").password(passwordEncoder().encode("123")).roles("USER").and()
//            .withUser("parasuadmin").password(passwordEncoder().encode("123")).roles("ADMIN");
    }
    
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
   
	/*
	
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.antMatcher("/**")
            .addFilterAfter(customAuthFilter(), RequestHeaderAuthenticationFilter.class)
            .authorizeRequests()
                .antMatchers("/user/login", "/csrf", "/index.html", "/js/**", "/css/**", "/dist/**").permitAll()
                .anyRequest().authenticated()
            .and()
                .csrf().csrfTokenRepository(csrfTokenRepository())
            .and()
                .formLogin().disable()
                ;
    }
    
    @Override
    protected void configure(final HttpSecurity http) throws Exception {
        http
          .csrf().disable()
          .authorizeRequests()
          .antMatchers("/admin/**").hasRole("ADMIN")
          .antMatchers("/anonymous*").anonymous()
          .antMatchers("/user/login1").permitAll()
          .anyRequest().authenticated()
          .and()
          .formLogin()
          //.loginPage("/")
          //.loginProcessingUrl("/user/login1")
          .defaultSuccessUrl("/", true)
          //.failureUrl("/login.html?error=true")
          //.failureHandler(authenticationFailureHandler())
          .and()
          .logout()
          .logoutUrl("/perform_logout")
          .deleteCookies("JSESSIONID")
          //.logoutSuccessHandler(logoutSuccessHandler())
          ;
    }

    
    @Bean
    public UsernamePasswordAuthenticationFilter customAuthFilter() throws Exception {
        UsernamePasswordAuthenticationFilter authenticationFilter = new CustomAuthenticationFilter(objectMapper());
        authenticationFilter.setRequiresAuthenticationRequestMatcher(new AntPathRequestMatcher("/user/login", "POST"));
        authenticationFilter.setUsernameParameter("username");
        authenticationFilter.setPasswordParameter("password");
        authenticationFilter.setAuthenticationManager(authenticationManagerBean());
        authenticationFilter.setAuthenticationSuccessHandler(authenticationSuccessHandler());
        authenticationFilter.setSessionAuthenticationStrategy(sessionAuthenticationStrategy());

        return authenticationFilter;
    }
    



    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Bean
    public AuthenticationSuccessHandler authenticationSuccessHandler() {
        return new SimpleUrlAuthenticationSuccessHandler("/");
    }

    @Bean
    public SessionAuthenticationStrategy sessionAuthenticationStrategy() {
        return new CompositeSessionAuthenticationStrategy(Arrays.asList(
            new ChangeSessionIdAuthenticationStrategy(),
            new CsrfAuthenticationStrategy(csrfTokenRepository())
        ));
    }

    @Bean
    public CsrfTokenRepository csrfTokenRepository() {
        return new LazyCsrfTokenRepository(new HttpSessionCsrfTokenRepository());
    }

    @Bean
    public ObjectMapper objectMapper() {
        return new ObjectMapper();
    }
    
    */

}
