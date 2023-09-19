package com.kagoji.atfadashboard.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import com.kagoji.atfadashboard.service.CustomUserDetailsServices;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Autowired
    private CustomUserDetailsServices customUserDetailsServices;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(11);
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .authorizeRequests(authorizeRequests ->
                authorizeRequests
                    .requestMatchers("/sign-up").permitAll()
                    .requestMatchers("/temp").permitAll()
                    .requestMatchers("/static/**", "/assets/**", "/templates/**", "/h2-console/**","/sign-in/**").permitAll()
                    .requestMatchers("/admin/**").hasAnyRole("ADMIN")
                    .anyRequest().authenticated()
            )
            .formLogin()
            .loginPage("/sign-in")
            .loginProcessingUrl("/sign-in")
            .failureUrl("/sign-in?errorMessage=Invalide credential")
            .defaultSuccessUrl("/admin/dashboard",true)
            .usernameParameter("userName")
            .passwordParameter("password")
            .permitAll()
            .and()
            .logout()
            .invalidateHttpSession(true)
            .clearAuthentication(true)
            .logoutRequestMatcher(new AntPathRequestMatcher("/sign-out"))
            .logoutSuccessUrl("/sign-in?message=Succesfully logged out")
            .permitAll();

        return http.build();
    }

    public void configure(AuthenticationManagerBuilder authenticationManagerBuilder) throws Exception {
        authenticationManagerBuilder.userDetailsService(customUserDetailsServices).passwordEncoder(passwordEncoder());
    }
}
