package com.example.educationapp.conf;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.configuration.EnableGlobalAuthentication;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.util.matcher.AndRequestMatcher;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import java.util.concurrent.TimeUnit;

import static com.example.educationapp.enums.UserPermissions.*;
import static com.example.educationapp.enums.UserPermissions.USER_UPDATE;
import static com.example.educationapp.enums.UserRoles.ADMIN;
import static com.example.educationapp.enums.UserRoles.STUDENT;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class AppSecurityConf extends WebSecurityConfigurerAdapter {

    private final PasswordEncoder passwordEncoder;

    @Autowired
    public AppSecurityConf(PasswordEncoder passwordEncoder){
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .authorizeRequests()
                .antMatchers("/", "index", "/css/*", "/js/*").permitAll()
//                .antMatchers(HttpMethod.GET, "/getUsers","/getUser/**").hasAuthority(USER_READ.getPermission())  // If any permission conflict each other spring applies above.
//                .antMatchers(HttpMethod.DELETE, "/delUser/**").hasAuthority(USER_DELETE.getPermission())
//                .antMatchers(HttpMethod.POST, "/addUser/**").hasAuthority(USER_WRITE.getPermission())
//                .antMatchers(HttpMethod.PUT, "/updateUser/**").hasAuthority(USER_UPDATE.getPermission())
                .anyRequest()
                .authenticated()
                .and()
                    .formLogin()
                    .loginPage("/login").permitAll()
                    .defaultSuccessUrl("/users", true)
                .and()
                    .rememberMe() //session will expire after 2 weeks.
                    .tokenValiditySeconds((int) TimeUnit.DAYS.toSeconds(21))
                    .key("secured")
                .and()
                    .logout()
                    .logoutUrl("/logout")
                    .logoutRequestMatcher(new AntPathRequestMatcher("/logout", "GET")) // Because of csrf is disable we should send logout with GET method.
                    .clearAuthentication(true)                                                          // If csrf was enabled we must change this POST. Because our request
                    .invalidateHttpSession(true)                                                        // should send a CSRF token with request.
                    .deleteCookies("JSESSIONID", "remember-me")
                    .logoutSuccessUrl("/login");
    }

    @Override
    @Bean
    protected UserDetailsService userDetailsService() {
        UserDetails student = User.builder()
                .username("dossantos27131")
                .password(passwordEncoder.encode("654512"))
//                .roles(STUDENT.name())
                .authorities(STUDENT.getGrantedAuth())
                .build();

        UserDetails admin = User.builder()
                .username("admin")
                .password(passwordEncoder.encode("531415"))
//                .roles(ADMIN.name())
                .authorities(ADMIN.getGrantedAuth())
                .build();

        return new InMemoryUserDetailsManager(
            student, admin
        );
    }
}
