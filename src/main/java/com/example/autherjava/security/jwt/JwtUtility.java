package com.example.autherjava.security.jwt;

import com.example.autherjava.exceptions.TokenExceptions;
import com.example.autherjava.model.entity.Account;

import io.jsonwebtoken.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.Date;

@Component
@Slf4j
public class JwtUtility implements Serializable {
//    private static final Logger logger = LoggerFactory.getLogger(JwtUtility.class);
//    static final String AUTHORITIES_KEY = "scopes";

    private String jwtSecret = "secretkey";
    public String generateJwtToken(String email ) {
        return Jwts.builder()
                .setSubject(email)

                .setIssuedAt(new Date())
                .setExpiration(new Date((new Date()).getTime() + (100 * 60 * 60 * 24)))
                .signWith(SignatureAlgorithm.HS512, jwtSecret)
                .compact();
    }

    public boolean validateJwtToken(String authToken)
    {

        try {
            Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(authToken);
            return true;
        }catch (SignatureException e) {
          ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Token khong hop le: "+ e.getMessage());
            log.error("Invalid JWT signature: {}", e.getMessage());
        } catch (MalformedJwtException e) {
            ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Token khong dung: "+ e.getMessage());
        } catch (ExpiredJwtException e) {
            ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Token da het han: "+ e.getMessage());
            log.error("JWT token is expired: {}", e.getMessage());
        } catch (UnsupportedJwtException e) {
            ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Token khong duoc ho tro: "+ e.getMessage());
            log.error("JWT token is unsupported: {}", e.getMessage());
        } catch (IllegalArgumentException e) {
            ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Chuoi xac nhan quyen so huu trong: "+ e.getMessage());
            log.error("JWT claims string is empty: {}", e.getMessage());
        }
        return false;
    }
    public String getEmailFromJwtToken(String token) {
        return Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token).getBody().getSubject();
    }
    private Claims getAllClaimsFromToken(String token) {
        return Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token).getBody();
    }
    public Long getUserIdFromJWT(String token) {
        Claims claims = Jwts.parser()
                .setSigningKey(jwtSecret)
                .parseClaimsJws(token)
                .getBody();
        return Long.parseLong(claims.getSubject());
    }
}
