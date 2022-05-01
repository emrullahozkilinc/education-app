package com.example.educationapp.jwt;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.Date;
import java.util.stream.Collectors;

public class JWTUserPassAuthFilter extends UsernamePasswordAuthenticationFilter {

    private final AuthenticationManager authManager;

    public JWTUserPassAuthFilter(AuthenticationManager authenticationManager) {
        authManager = authenticationManager;
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request,
                                                HttpServletResponse response) throws AuthenticationException {
        try {
            /* For chrome requests.
            byte[] b = request.getInputStream().readAllBytes();
            String[] reqbody = new String(b).split("&");
            Arrays.stream(reqbody).forEach(System.out::println);
            String username = reqbody[0].substring(9);
            String password = reqbody[1].substring(9);
            UserPassAuthReq authReq = new UserPassAuthReq(username, password);
            */

            UserPassAuthReq authReq = new ObjectMapper()
                    .readValue(request.getInputStream(), UserPassAuthReq.class);

            Authentication auth = new UsernamePasswordAuthenticationToken(
                    authReq.getUsername(),
                    authReq.getPassword()
            );

            return authManager.authenticate(auth);
        }catch (IOException e) {
            System.out.println(e.getMessage());
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request,
                                            HttpServletResponse response,
                                            FilterChain chain,
                                            Authentication authResult)
            throws IOException, ServletException {
        String token = Jwts.builder()
                .setSubject(authResult.getName())
                .claim("authorities", authResult.getAuthorities())
                .setIssuedAt(new Date())
                .setExpiration(java.sql.Date.valueOf(LocalDate.now().plusWeeks(2)))
                .signWith(Keys.hmacShaKeyFor("knjksufhlıweuhndsbnfakbdsfnluasbfdıasbfeufbnjuafbsd".getBytes()))
                .compact();
        response.addHeader("Authorization", "Bearer " + token);
    }
}
