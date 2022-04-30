package com.example.educationapp.conf;

import com.example.educationapp.exceptionhandler.AuthFailHandle;
import com.example.educationapp.jwt.JWTUserPassAuthFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.configuration.EnableGlobalAuthentication;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
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

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .addFilter(new JWTUserPassAuthFilter(authenticationManager()))
                .authorizeRequests()
                .antMatchers("/", "index", "/css/*", "/js/*", "/login", "/logout").permitAll()
                .antMatchers("/getStudents").hasAuthority(USER_READ.getPermission())
                .anyRequest()
                .authenticated();

                /*
                .antMatchers(HttpMethod.GET, "/getUsers","/getUser/**").hasAuthority(USER_READ.getPermission())  // If any permission conflict each other spring applies above.
                .antMatchers(HttpMethod.DELETE, "/delUser/**").hasAuthority(USER_DELETE.getPermission())
                .antMatchers(HttpMethod.POST, "/addUser/**").hasAuthority(USER_WRITE.getPermission())
                .antMatchers(HttpMethod.PUT, "/updateUser/**").hasAuthority(USER_UPDATE.getPermission())
                .anyRequest()
                .authenticated()
                .and()
                    .formLogin()
                    .loginPage("/login").permitAll()
                    .defaultSuccessUrl("/users", true)
                    .usernameParameter("username")  //Name of username input in login form.
                    .passwordParameter("password")  //Name of password input in login form.
                .and()
                    .rememberMe() //session will expire after 2 weeks.
                    .tokenValiditySeconds((int) TimeUnit.DAYS.toSeconds(21))
                    .key("secured")
                    .rememberMeParameter("remember-me") //Name of remember-me checkbox in login form.
                .and()
                    .logout()
                    .logoutUrl("/logout")
                    .logoutRequestMatcher(new AntPathRequestMatcher("/logout", "GET"))                  // Because of csrf is disable we should send logout with GET method.
                    .clearAuthentication(true)                                                          // If csrf was enabled we must change this POST. Because our request
                    .invalidateHttpSession(true)                                                        // should send a CSRF token with request. And in logout button in Users.html page.
                    .deleteCookies("JSESSIONID", "remember-me")
                    .logoutSuccessUrl("/login");
                 */
    }

}
