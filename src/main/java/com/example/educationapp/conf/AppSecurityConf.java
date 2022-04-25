package com.example.educationapp.conf;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

import static com.example.educationapp.enums.UserPermissions.*;
import static com.example.educationapp.enums.UserPermissions.USER_UPDATE;
import static com.example.educationapp.enums.UserRoles.ADMIN;
import static com.example.educationapp.enums.UserRoles.STUDENT;

@Configuration
@EnableWebSecurity
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
                .antMatchers(HttpMethod.GET, "/getUsers","/getUser/**").hasAuthority(USER_READ.getPermission())
                .antMatchers(HttpMethod.DELETE, "/delUser/**").hasAuthority(USER_DELETE.getPermission())
                .antMatchers(HttpMethod.POST, "/addUser/**").hasAuthority(USER_WRITE.getPermission())
                .antMatchers(HttpMethod.PUT, "/updateUser/**").hasAuthority(USER_UPDATE.getPermission())
                .anyRequest()
                .authenticated()
                .and()
                .httpBasic();
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