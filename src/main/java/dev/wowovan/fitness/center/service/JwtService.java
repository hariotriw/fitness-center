package dev.wowovan.fitness.center.service;

import java.util.Date;

import javax.enterprise.context.ApplicationScoped;

import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;

@ApplicationScoped
public class JwtService {

    private static final Logger LOG = LoggerFactory.getLogger(DummyService.class);

    @ConfigProperty(name = "jwt.secret")
    String jwtSecret;

    @ConfigProperty(name = "jwt.expire.time")
    long jwtExpTime;

    public DecodedJWT validateToken(String token) {
        try {
            return JWT.require(Algorithm.HMAC256(jwtSecret)).build().verify(token);
        } catch (Exception e) {
            // Token validation failed
            return null;
        }
    }

    public String generateToken(String subject) {
        Date issuedAt = new Date();
        Date expiresAt = new Date(issuedAt.getTime() + jwtExpTime);

        return JWT.create()
                .withSubject(subject)
                .withIssuedAt(issuedAt)
                .withExpiresAt(expiresAt)
                .sign(Algorithm.HMAC256(jwtSecret));
    }
    
}
