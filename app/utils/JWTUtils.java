package utils;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import jakarta.inject.Inject;
import jakarta.inject.Singleton;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.crypto.SecretKey;
import java.util.Calendar;
import java.util.Date;

@Singleton
public class JWTUtils {

    private final SecretKey key;
    private static final Logger logger = LoggerFactory.getLogger(JWTUtils.class);

    @Inject
    public JWTUtils() {
        this.key = Keys.secretKeyFor(SignatureAlgorithm.HS512);
        logger.info("JWTUtils initialized with new secret key");
    }

    public String createJWTToken(String userEmail) {

        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DAY_OF_MONTH, 30);

        return Jwts.builder()
                .setSubject("accessToMySystem")
                .setIssuedAt(new Date())
                .setExpiration(cal.getTime()) // now() + 30 days
                .setId("123456789")
                .setIssuer("MyProject - Play framework by example")
                .claim("username", userEmail)
                .signWith(key)
                .compact();
    }

    public boolean validateJWTToken(String jws){

        try {
            Jws<Claims> valid = Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(jws);
        } catch (JwtException e) {
            return  false;
        }

        return true;
    }

    public Claims getJWTClaims(String jws) throws JwtException {

        Jws<Claims> JWSclaims;

        try {
            JWSclaims = Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(jws);
        } catch (JwtException e) {
            throw new JwtException("Invalid JWT token");
        }

        return JWSclaims.getBody();
    }


}
