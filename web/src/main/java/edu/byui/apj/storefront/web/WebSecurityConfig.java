package edu.byui.apj.storefront.web;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig {
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests((requests) -> requests
                        // This 2 lines ask the authentication for user-profile.html. Any other things than that will be allowed
                        .requestMatchers("user-profile.html").authenticated()
                        .anyRequest().permitAll()
                )
                .formLogin((form) -> form
                        .loginPage("/login.html")
                        .loginProcessingUrl("/perform-login")
                        .failureUrl("/login.html?error=Invalid+Login")
                        .permitAll()
                )
                .logout(logout -> logout
                        .logoutUrl("/perform-logout") // Change logout URL
                        .logoutSuccessUrl("/index.html") // Redirect after logout
                        .invalidateHttpSession(true) // Invalidate session
                        .clearAuthentication(true) // Remove authentication info
                        .deleteCookies("JSESSIONID") )
                .csrf(AbstractHttpConfigurer::disable);

        return http.build();
    }

    @Bean
    public InMemoryUserDetailsManager UserDetailsService(){
        UserDetails user = User.withDefaultPasswordEncoder()
                .username("apj-user")
                .password("password123")
                .roles("USER").build();
        return new InMemoryUserDetailsManager(user);
    }
}
