package com.wily.task_manager.Jwt.Filter;

import com.wily.task_manager.Jwt.Services.JwtService;
import com.wily.task_manager.Model.User;
import com.wily.task_manager.Repository.iUserRepository;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;

@Component
public class JwtAuthFilter extends OncePerRequestFilter {

    final private JwtService jwtService;
    final private iUserRepository iUserRepository;

    public JwtAuthFilter(JwtService jwtService, iUserRepository iUserRepository) {
        this.jwtService = jwtService;
        this.iUserRepository = iUserRepository;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain)
            throws ServletException, IOException {

        final String authHeader = request.getHeader("Authorization");

        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }

        final String jwt = authHeader.substring(7);
        final String userEmail = jwtService.extractEmail(jwt);

        if (userEmail != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            User user = iUserRepository.findByEmail(userEmail).orElse(null);

            if (user != null && jwtService.isTokenValid(jwt, userEmail)) {
                UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                        user, null, List.of(new SimpleGrantedAuthority(user.getRole().name()))
                );
                authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                SecurityContextHolder.getContext().setAuthentication(authToken);
            }
        }

        filterChain.doFilter(request, response);
    }
}
