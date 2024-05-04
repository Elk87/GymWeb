package com.example.gymweb.Secure;

import com.example.gymweb.Secure.jwt.JwtRequestFilter;
import com.example.gymweb.Secure.jwt.UnauthorizedHandlerJwt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig {
    @Autowired
    public RepositoryUserDetailsService userDetailsService;
    @Autowired
    private JwtRequestFilter jwtRequestFilter;
    @Autowired
    private UnauthorizedHandlerJwt unauthorizedHandlerJwt;

    @Bean
    public AuthenticationManager authenticationManagerBean(AuthenticationConfiguration authConfig) throws Exception {
        return authConfig.getAuthenticationManager();
    }

    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService);
        authProvider.setPasswordEncoder(passwordEncoder());
        return authProvider;
    }

    @Bean
    @Order(2)
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.authenticationProvider(authenticationProvider());
        http
                .authorizeHttpRequests(authorize -> authorize
                        //static resources
                        .requestMatchers("/css/**", "/js/**", "/img/**", "/bootstrap/**", "/favicon.ico").permitAll()
                        //public pages
                        .requestMatchers("/").permitAll()
                        .requestMatchers("/login/**").permitAll()
                        .requestMatchers("/register").permitAll()
                        .requestMatchers("/error").permitAll()
                        .requestMatchers("/training").permitAll()
                        .requestMatchers("/lesson").permitAll()
                        .requestMatchers("/admin/**").hasRole("ADMIN")
                        .requestMatchers("/profile/**").hasAnyRole("ADMIN", "USER")
                        .requestMatchers("/ranking/**").authenticated()
                        .requestMatchers("/lessons/**").authenticated())

                //login
                .formLogin(formLogin -> formLogin
                        .loginPage("/login")
                        .failureUrl("/loginIncorrect")
                        .defaultSuccessUrl("/profile") //pagina privada
                        .permitAll())

                //logout
                .logout(logout -> logout
                        .logoutUrl("/logout")
                        .logoutSuccessUrl("/")
                        .permitAll())
                .addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class)
                .exceptionHandling(exceptionHandling -> exceptionHandling
                        .authenticationEntryPoint(unauthorizedHandlerJwt)
                ); //punto y coma cierra todo*/
        http.sessionManagement(management -> management.sessionCreationPolicy(SessionCreationPolicy.ALWAYS));
        return http.build();
    }
}

   /* @Bean
    @Order(1)
    public SecurityFilterChain apiFilterChain(HttpSecurity http, UnauthorizedHandlerJwt unauthorizedHandlerJwt) throws Exception {
        http.authenticationProvider(authenticationProvider());
        http
                .securityMatcher("/api/**")
                .exceptionHandling(handling -> handling.authenticationEntryPoint(unauthorizedHandlerJwt));
        /*http
                .authorizeHttpRequests(authorize -> authorize


                );
    }
}*/
