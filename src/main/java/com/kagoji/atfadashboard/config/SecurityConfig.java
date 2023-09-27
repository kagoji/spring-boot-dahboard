package com.kagoji.atfadashboard.config;

import org.apache.tomcat.util.descriptor.web.ErrorPage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.server.ErrorPageRegistrar;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import com.kagoji.atfadashboard.service.CustomSuccessHandler;
import com.kagoji.atfadashboard.service.CustomUserDetailsServices;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
	
	 @Autowired
    public CustomSuccessHandler customSuccessHandler; //After successful logged in route handler

    @Autowired
    private CustomUserDetailsServices customUserDetailsServices; //logged in user details get

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(11);
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
    	
    	
    	http.csrf(c -> c.disable())
		
		.authorizeHttpRequests(request -> request
				.requestMatchers("/admin/**").hasAuthority("ADMIN")
				.requestMatchers("/album/**").hasAuthority("ADMIN")
				.requestMatchers("/image/**").hasAuthority("ADMIN")
				.requestMatchers("/static/**", "/assets/css/**", "/assets/js/**","/assets/fonts/**","/assets/images/**","/templates/**", "/h2-console/**","/sign-in/**","/uploads/**").permitAll()
				.requestMatchers("/change-password").authenticated()
				.requestMatchers("/sign-out").authenticated()
				.requestMatchers("/sign-up").permitAll()
				.anyRequest().authenticated())
		
		.formLogin(form -> form.loginPage("/sign-in").loginProcessingUrl("/sign-in")
				.usernameParameter("userName")
				.passwordParameter("password")
				.successHandler(customSuccessHandler)
				.failureUrl("/sign-in?error=true")
				.permitAll())
		
		.logout(form -> form.invalidateHttpSession(true).clearAuthentication(true)
				.logoutRequestMatcher(new AntPathRequestMatcher("/sign-out"))
				.logoutSuccessUrl("/sign-in?message=Succesfully logged out").permitAll());
		
		return http.build();
    	
    }

    public void configure(AuthenticationManagerBuilder authenticationManagerBuilder) throws Exception {
        authenticationManagerBuilder.userDetailsService(customUserDetailsServices).passwordEncoder(passwordEncoder());
    }
    
}
