package app.auth.util;

import java.util.Arrays;
import java.util.Date;


import app.auth.model.Role;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

public class ApiKeyUtils {
	private static final String APP_NAME = "spring-security-example";
    public static final String SECRET = "thisIsTheTOPSecretKeyYouWillFindOnlyHere";
    private static final long EXPIRES_IN = 900000000;
    private static final String AUTH_HEADER = "ApiKey";
    private static final String AUDIENCE_WEB = "web";
    private static final SignatureAlgorithm SIGNATURE_ALGORITHM = SignatureAlgorithm.HS512;

    public static String generateToken(String username) {
        return Jwts.builder()
                .setIssuer(APP_NAME)
                .setAudience(AUDIENCE_WEB)
                .setIssuedAt(new Date())
                .setExpiration(generateExpirationDate())
                .claim("username", username)
                .signWith(SIGNATURE_ALGORITHM, SECRET).compact();
    }

    public static String getToken(String authHeader) {
        return authHeader;
    }

    private static Boolean isTokenExpired(String token) {
        final Date expiration = getExpirationDateFromToken(token);
        return expiration.before(new Date());
    }

    private static Date generateExpirationDate() {
        return new Date(new Date().getTime() + EXPIRES_IN);
    }

    private static Claims getAllClaimsFromToken(String token) {
        Claims claims;
        try {
            claims = Jwts.parser()
                    .setSigningKey(SECRET)
                    .parseClaimsJws(token)
                    .getBody();
        } catch (Exception e) {
            claims = null;
        }
        return claims;
    }

    public static String verify(String header) {
        String token = getToken(header);
        String username = getUsernameFromToken(token);
        if(username != null && !isTokenExpired(token))
            return username;
        else
            return null;
    }

    public static Date getExpirationDateFromToken(String token) {
        Date expiration;
        try {
            final Claims claims = getAllClaimsFromToken(token);
            expiration = claims.getExpiration();
        } catch (Exception e) {
            expiration = null;
        }
        return expiration;
    }

    public static String getUsernameFromToken(String token) {
        String username;
        try {
            final Claims claims = getAllClaimsFromToken(token);
            username = (String) claims.get("username");
        } catch (Exception e) {
            username = null;
        }
        return username;
    }
}
