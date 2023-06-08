package com.miniproject.interviewcode.auth.sercurity;

import io.jsonwebtoken.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.util.Base64;
import java.util.Date;


@Component
public class JwtTokenProvider {

    private static final Logger logger = LoggerFactory.getLogger(JwtTokenProvider.class);

    @Value("${app.jwtSecret}")
    private String jwtSecret;

    @Value("${app.jwtExpirationInMs}")
    private int jwtExpirationInMs;

    public String generateToken(Authentication authentication) throws NoSuchAlgorithmException, InvalidKeySpecException {

        UserPrincipal userPrincipal = (UserPrincipal) authentication.getPrincipal();

        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + jwtExpirationInMs);
        
        PrivateKey privateKey = extractedPrivateKey();

        return Jwts.builder()
                .setSubject(userPrincipal.getUsername())
                .setIssuedAt(new Date())
                .setExpiration(expiryDate)
                .signWith(SignatureAlgorithm.RS256, privateKey)
                .compact();
    }
    

    public String getUserIdFromJWT(String token) throws NoSuchAlgorithmException, InvalidKeySpecException {
    	PrivateKey privateKey = extractedPrivateKey();
    	
        Claims claims = Jwts.parser()
                .setSigningKey(privateKey)
                .parseClaimsJws(token)
                .getBody();

        return claims.getSubject();
    }

    public boolean validateToken(String authToken) throws NoSuchAlgorithmException, InvalidKeySpecException {
        try {
        	PrivateKey privateKey = extractedPrivateKey();
            
            Jwts.parser().setSigningKey(privateKey).parseClaimsJws(authToken);
            return true;
        } catch (SignatureException ex) {
            logger.error("Invalid JWT signature");
        } catch (MalformedJwtException ex) {
            logger.error("Invalid JWT token");
        } catch (ExpiredJwtException ex) {
            logger.error("Expired JWT token");
        } catch (UnsupportedJwtException ex) {
            logger.error("Unsupported JWT token");
        } catch (IllegalArgumentException ex) {
            logger.error("JWT claims string is empty.");
        }
        return false;
    }


	private PrivateKey extractedPrivateKey() throws NoSuchAlgorithmException, InvalidKeySpecException {
		byte[] privateKeyDecoded = Base64.getDecoder()
		        .decode(jwtSecret);
		
        //create key spec
		PKCS8EncodedKeySpec spec =
		                new PKCS8EncodedKeySpec(privateKeyDecoded);
		
       // create key form spec
		KeyFactory keyFactory = KeyFactory.getInstance("RSA");
		PrivateKey privateKey = keyFactory.generatePrivate(spec);
		return privateKey;
	}
}
