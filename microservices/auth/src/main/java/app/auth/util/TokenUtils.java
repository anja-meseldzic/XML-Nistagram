package app.auth.util;

import app.auth.model.Role;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

@Component
public class TokenUtils {
    private static final String APP_NAME = "spring-security-example";
    public static final String SECRET = "thisIsTheTOPSecretKeyYouWillFindOnlyHere";
    private static final int EXPIRES_IN = 90000000;
    private static final String AUTH_HEADER = "Authorization";
    private static final String AUDIENCE_WEB = "web";
    private static final SignatureAlgorithm SIGNATURE_ALGORITHM = SignatureAlgorithm.HS512;

    public static String generateToken(Long id, Role role) {
        return Jwts.builder()
                .setIssuer(APP_NAME)
                .setAudience(AUDIENCE_WEB)
                .setIssuedAt(new Date())
                .setExpiration(generateExpirationDate())
                .claim("id", id)
                .claim("role", role)
                .signWith(SIGNATURE_ALGORITHM, SECRET).compact();
    }

    public static String getToken(String authHeader) {
//        String authHeader = getAuthHeaderFromHeader(request);
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            return authHeader.substring(7);
        }
        return null;
    }

    public static String getAuthHeaderFromHeader(HttpServletRequest request) {
        return request.getHeader(AUTH_HEADER);
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

//    public static Boolean validateToken(String token, UserDetails userDetails) {
//        final String username = "" + getIdFromToken(token);
//        return (username.equals(userDetails.getUsername())
//                && !isTokenExpired(token));
//    }

    public static boolean verify(String header, String... roles) {
        String token = getToken(header);
        Role role = getRoleFromToken(token);
        return role != null && !isTokenExpired(token) && Arrays.stream(roles).anyMatch(r -> r.equals(role.name()));
    }

    private static Boolean isTokenExpired(String token) {
        final Date expiration = getExpirationDateFromToken(token);
        return expiration.before(new Date());
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

//    public static Long getIdFromToken(String token) {
//        Long id;
//        try {
//            final Claims claims = getAllClaimsFromToken(token);
//            id = Long.valueOf("" + claims.get("id"));
//        } catch (Exception e) {
//            id = null;
//        }
//        return id;
//    }

    public static Role getRoleFromToken(String token) {
        Role type;
        try {
            final Claims claims = getAllClaimsFromToken(token);
            type = Role.valueOf("" + claims.get("role"));
        } catch (Exception e) {
            type = null;
        }
        return type;
    }
//    public static Date getIssuedAtDateFromToken(String token) {
//        Date issueAt;
//        try {
//            final Claims claims = this.getAllClaimsFromToken(token);
//            issueAt = claims.getIssuedAt();
//        } catch (Exception e) {
//            issueAt = null;
//        }
//        return issueAt;
//    }



}
