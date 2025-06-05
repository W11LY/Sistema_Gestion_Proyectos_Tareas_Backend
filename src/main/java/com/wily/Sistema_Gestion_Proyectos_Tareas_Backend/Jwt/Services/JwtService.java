package com.wily.Sistema_Gestion_Proyectos_Tareas_Backend.Jwt.Services;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;
import java.util.function.Function;

@Service
public class JwtService {

    private static final String SECRET_KEY = "mi_clave_secreta_12345678901234567890123456789012"; // debe tener al menos 32 bytes
    private static final long EXPIRATION_TIME = 1000 * 60 * 60 * 24;

//    Transforma la secret key a una clave criptografica para firmar
    private Key getSignInKey() {
        return Keys.hmacShaKeyFor(SECRET_KEY.getBytes());
    }

//    Extrae email de jwt
    public String extractEmail(String token) {
        return extractClaim(token, Claims::getSubject);
    }

//    Extrae cualquier informacion del jwt
    public <T> T extractClaim(String token, Function<Claims, T> resolver) {
        final Claims claims = extractAllClaims(token);
        return resolver.apply(claims);
    }

//    Estrae toda la informacion del jwt
    private Claims extractAllClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(getSignInKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

//    Genera el Jwt  con HS256 y secret key
    public String generateToken(String email) {
        return Jwts.builder()
                .setSubject(email)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .signWith(getSignInKey(), SignatureAlgorithm.HS256)
                .compact();
    }

//    Valida token y email
    public boolean isTokenValid(String token, String email) {
        final String extractedEmail = extractEmail(token);
        return extractedEmail.equals(email) && !isTokenExpired(token);
    }

//    Valida qque el token no a expirado
    private boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

//    Extrae la fecha de expiracion del token
    private Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }


}
