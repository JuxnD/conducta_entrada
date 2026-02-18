package com.conducta_entrada.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

/**
 * Servicio para la gestión de tokens JWT (JSON Web Token).
 * Permite generar, validar y extraer información de los tokens.
 *
 * Los valores de configuración (clave secreta y tiempo de expiración)
 * se cargan desde el archivo application.properties.
 */
@Service
public class JwtService {

    /** Clave secreta codificada en Base64 para firmar los tokens */
    @Value("${jwt.secret}")
    private String secretKey;

    /** Tiempo de expiración del token en milisegundos (86400000 = 24 horas) */
    @Value("${jwt.expiration}")
    private long jwtExpiration;

    /**
     * Extrae el nombre de usuario (subject) del token JWT.
     *
     * @param token token JWT del cual extraer el usuario
     * @return nombre de usuario contenido en el token
     */
    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    /**
     * Extrae un claim específico del token usando una función de resolución.
     *
     * @param token          token JWT
     * @param claimsResolver función que define qué claim extraer
     * @return valor del claim solicitado
     */
    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    /**
     * Genera un token JWT con los datos del usuario autenticado.
     * Utiliza claims vacíos (sin información adicional).
     *
     * @param userDetails datos del usuario autenticado
     * @return token JWT generado
     */
    public String generateToken(UserDetails userDetails) {
        return generateToken(new HashMap<>(), userDetails);
    }

    /**
     * Genera un token JWT con claims adicionales y los datos del usuario.
     * El token incluye: subject (usuario), fecha de emisión y fecha de expiración.
     *
     * @param extraClaims claims adicionales a incluir en el token
     * @param userDetails datos del usuario autenticado
     * @return token JWT firmado y compactado
     */
    public String generateToken(Map<String, Object> extraClaims, UserDetails userDetails) {
        return Jwts.builder()
                .claims(extraClaims)
                .subject(userDetails.getUsername())
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + jwtExpiration))
                .signWith(getSignInKey())
                .compact();
    }

    /**
     * Valida si un token JWT es válido para un usuario específico.
     * Verifica que el nombre de usuario coincida y que el token no haya expirado.
     *
     * @param token       token JWT a validar
     * @param userDetails datos del usuario para comparar
     * @return true si el token es válido, false en caso contrario
     */
    public boolean isTokenValid(String token, UserDetails userDetails) {
        final String username = extractUsername(token);
        return (username.equals(userDetails.getUsername())) && !isTokenExpired(token);
    }

    /**
     * Verifica si un token JWT ha expirado comparando su fecha de expiración con la
     * actual.
     *
     * @param token token JWT a verificar
     * @return true si el token ha expirado, false si aún es válido
     */
    private boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    /**
     * Extrae la fecha de expiración del token JWT.
     *
     * @param token token JWT
     * @return fecha de expiración del token
     */
    private Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    /**
     * Extrae todos los claims del token JWT.
     * Verifica la firma del token con la clave secreta antes de extraer los datos.
     *
     * @param token token JWT a analizar
     * @return todos los claims contenidos en el token
     */
    private Claims extractAllClaims(String token) {
        return Jwts.parser()
                .verifyWith(getSignInKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    /**
     * Obtiene la clave secreta de firma decodificando la cadena Base64.
     * La clave se utiliza tanto para firmar como para verificar los tokens JWT.
     *
     * @return clave secreta para firmar tokens
     */
    private SecretKey getSignInKey() {
        byte[] keyBytes = Decoders.BASE64.decode(secretKey);
        return Keys.hmacShaKeyFor(keyBytes);
    }
}
