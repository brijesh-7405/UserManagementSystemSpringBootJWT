package com.UserManagementSystem.UserManagementSystemSpringBoot;

import javax.sql.DataSource;




import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import com.UserManagementSystem.UserManagementSystemSpringBoot.JWTSecurity.JwtFilter;
import com.UserManagementSystem.UserManagementSystemSpringBoot.Service.CustomUserDetailsService;
import com.fasterxml.jackson.core.format.MatchStrength;


@Configuration
@EnableWebSecurity
public class Congif extends WebSecurityConfigurerAdapter {

	@Autowired
	   private JwtFilter filter;

	@Bean
	 public UserDetailsService userDetailsService() {
	        return new CustomUserDetailsService();
	 }
	     
	    @Bean
	    public BCryptPasswordEncoder passwordEncoder() {
	        return new BCryptPasswordEncoder();
	    }
	    
	    @Bean
	    public DaoAuthenticationProvider authenticationProvider() {
	        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
	        authProvider.setUserDetailsService(userDetailsService());
	        authProvider.setPasswordEncoder(passwordEncoder());
	         
	        return authProvider;
	    }
	    
	    @Override
	    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
	        auth.authenticationProvider(authenticationProvider());
	    }
	    
	    @Override
		protected void configure(HttpSecurity http) throws Exception {
			http
				.csrf().disable()
				.authorizeRequests()
				.antMatchers("/","/login","/index","/registration","/forgotpwd","/forgotPwd","/resetpwd","/resetPassword","/userRegistration","/userExist","/checkUserExistDone").permitAll()
				.antMatchers("/assets/**").permitAll()
				.antMatchers("/adminWork").hasRole("ADMIN")
				.antMatchers("/authenticate").permitAll()
				.anyRequest()
				.authenticated()
				.and()
				.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
				.and()
				.logout()
				.logoutUrl("/logOut")
				.deleteCookies("Authorization")
				 .logoutSuccessUrl("/");
			http.addFilterBefore(filter, UsernamePasswordAuthenticationFilter.class);
//				.httpBasic();
//				.and()
//				.formLogin()
//				.loginPage("/index")
//				.loginProcessingUrl("/login")
//				.defaultSuccessUrl("/loginServlet")
//				.failureUrl("/failure")
//				.permitAll()
//				.and()
//				.logout()
//	            .logoutRequestMatcher(new AntPathRequestMatcher("/logOut"))
//	            .invalidateHttpSession(true)
//	            .logoutSuccessUrl("/")
//	            .permitAll();
		}
	    
	    @Bean
		 @Override
		public AuthenticationManager authenticationManagerBean() throws Exception {
			return super.authenticationManagerBean();
		}
		   
	    
	    
//	@Override
//	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
//		auth.inMemoryAuthentication().withUser("Admin@gmail.com").password(this.passwordEncoder().encode("Admin123@")).roles("admin");
//		auth.inMemoryAuthentication().withUser("Brijesh").password(this.passwordEncoder().encode("1234")).roles("user");
//	}
//	@SuppressWarnings("deprecation")
//	@Bean
//	public PasswordEncoder passwordEncoder()
//	{
//		return new BCryptPasswordEncoder(11);
////		return NoOpPasswordEncoder.getInstance();
//	}

	 
//	    private  BackButtonPrevention myFilter;
//	    private  CheckUserRole myFilter1;
//	    @Autowired
//	    public void filterConfiguration(BackButtonPrevention myFilter) {
//	        this.myFilter = myFilter;
//	    }
//	    @Autowired
//	    public void filterConfiguration(CheckUserRole myFilter1) {
//	        this.myFilter1 = myFilter1;
//	    }
//	    @Bean
//	    public FilterRegistrationBean<BackButtonPrevention> someFilterRegistration() {
//
//	        FilterRegistrationBean<BackButtonPrevention> registration = new FilterRegistrationBean<BackButtonPrevention>();
//	        registration.setFilter(myFilter);
//	        registration.addUrlPatterns("/userDetails","/userDashBoard","/editServlet","/adminWork","/userData");
//	        return registration;
//	    }
//	    @Bean
//	    public FilterRegistrationBean<CheckUserRole> someFilterRegistration1() {
//
//	        FilterRegistrationBean<CheckUserRole> registration = new FilterRegistrationBean<CheckUserRole>();
//	        registration.setFilter(myFilter1);
//	        registration.addUrlPatterns("/adminDashBoard","/adminWork");
//	        return registration;
//	    }
}