package com.wily.Sistema_Gestion_Proyectos_Tareas_Backend.Jwt.Filter;

import com.wily.Sistema_Gestion_Proyectos_Tareas_Backend.Jwt.Services.JwtService;
import com.wily.Sistema_Gestion_Proyectos_Tareas_Backend.Model.Client;
import com.wily.Sistema_Gestion_Proyectos_Tareas_Backend.Service.iServices.iClientServices;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class JwtAuthFilter extends OncePerRequestFilter {

//    inyeccion por constructor de jwt services y client
    final private JwtService jwtService;
    final private iClientServices clientServices;

    public JwtAuthFilter(JwtService jwtService, iClientServices clientServices) {
        this.jwtService = jwtService;
        this.clientServices = clientServices;
    }

//    filtro de seguridad para jwt validar y autentificar por email almacenado en el token
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
        final String clientEmail = jwtService.extractEmail(jwt);

        if (clientEmail != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            Client client = clientServices.getClientByEmail(clientEmail);

            if (client != null && jwtService.isTokenValid(jwt, clientEmail)) {
                UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                        client, null, null
                );
                authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                SecurityContextHolder.getContext().setAuthentication(authToken);
            }
        }

        filterChain.doFilter(request, response);
    }
}
