package dev.wowovan.fitness.center.service;

import java.util.Date;

import javax.enterprise.context.ApplicationScoped;

import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;

import dev.wowovan.fitness.center.constant.ErrorListConstant;
import io.vertx.core.json.JsonObject;

@ApplicationScoped
public class JwtService {

    private static final Logger LOG = LoggerFactory.getLogger(DummyService.class);

    @ConfigProperty(name = "jwt.secret")
    String jwtSecret;

    @ConfigProperty(name = "jwt.expire.time")
    long jwtExpTime;

    public JsonObject validateToken(String token) throws Exception {
        try {
            DecodedJWT jwt = JWT.require(Algorithm.HMAC256(jwtSecret)).build().verify(token);
            
            return new JsonObject().put("token", jwt.getToken()).put("subject", jwt.getSubject()); 
        } catch (Exception e) {
            // Token validation failed
            throw new Exception(ErrorListConstant.ERROR_UNAUTHORIZED.encode(), e.getCause());
        }
    }

    public String extractBearerToken(String authorizationHeader) {
        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            return authorizationHeader.substring("Bearer ".length());
        }
        throw new RuntimeException("Invalid Authorization header format");
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
