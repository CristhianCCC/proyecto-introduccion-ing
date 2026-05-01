package com.user.security.common;


// this class will have a filter that intercepts all HTTP requests before they reach the controller
// and it extracts JWT from authorization header, verifies if token is valid, creates an authentication
// to the user and it saves it in the security context

// this filter will be executed once per HTTP request

import com.user.security.service.JwtService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtService jwtService;
    private final UserDetailsService userDetailsService;

    // injecting dependencies
    public JwtAuthenticationFilter(JwtService jwtService, UserDetailsService  userDetailsService) {
        this.jwtService = jwtService;
        this.userDetailsService = userDetailsService;
    }

    @Override
    protected void doFilterInternal (HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws ServletException, IOException {

        // Normalize path to avoid trailing slash issues
        String path = request.getServletPath().replaceAll("/+$", "");
        String method = request.getMethod();

        // Skip JWT validation for public endpoints like /auth/login and POST /users
        if ((path.equals("/auth/login") || (path.equals("/users") && method.equals("POST")))) {

            chain.doFilter(request, response);
            return;
        }
        // get authorization header
        final String authHeader = request.getHeader("Authorization");

        //if there's no token or it's not a Bearer token, skip filter
        if(authHeader == null || !authHeader.startsWith("Bearer")){
            chain.doFilter(request, response);
            return;
        }

        //extract token from header
        String jwt = authHeader.substring(7);
        String username = jwtService.extractUsername(jwt);
        // if user is not yet authenticated in this request
        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {

            // load user details using provided username
            UserDetails userDetails = userDetailsService.loadUserByUsername(username);

            // validate token and user
            if (jwtService.isTokenValid(jwt, userDetails)) {

                // create authentication token and set it in the security context
                UsernamePasswordAuthenticationToken authToken =
                        new UsernamePasswordAuthenticationToken(
                                userDetails,
                                null,
                                userDetails.getAuthorities()
                        );

                authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                // set the authentication to the SecurityContext
                SecurityContextHolder.getContext().setAuthentication(authToken);
            }
        }

        // continue the filter chain
        chain.doFilter(request, response);
    }
}