package utils;

import controllers.actions.SecuredAPI;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.slf4j.LoggerFactory;

import javax.crypto.SecretKey;
import javax.inject.Inject;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.Calendar;
import java.util.Date;
import java.util.Map;

public class JWTUtils {

    private static boolean firstRun = true;
    private static SecretKey key;
    private static JWTUtils instance;

    private final static org.slf4j.Logger logger = LoggerFactory.getLogger(JWTUtils.class);

    @Inject
    private JWTUtils(){
         key = Keys.secretKeyFor(SignatureAlgorithm.HS512);
    }

    public static JWTUtils getInstance(){
        if (firstRun){
            firstRun = false;
            instance = new JWTUtils();
        }

        return instance;
    }

    public String createJWTToken(String userEmail) throws NoSuchAlgorithmException, InvalidKeySpecException {

        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DAY_OF_MONTH, 30);

        String jws = Jwts.builder()
                .setSubject("accessToMySystem")
                .setIssuedAt(new Date())
                .setExpiration(cal.getTime()) // now() + 30 days
                .setId("123456789")
                .setIssuer("MyProject - Play framework by example")
                .claim(SecuredAPI.USERNAME, userEmail)
                .signWith(key)
                .compact();

        return jws;
    }

    public boolean validateJWTToken(String jws){

        try {
            Jws<Claims> valid = Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(jws);
        } catch (JwtException e) {
            return  false;
        }

        return true;
    }

    public Map<String,Object> getJWTClaims(String jws) throws JwtException {

        Jws<Claims> JWSclaims;

        try {
            JWSclaims = Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(jws);
        } catch (JwtException e) {
            throw new JwtException("Invalid JWT token");
        }

        return JWSclaims.getBody();
    }

}

