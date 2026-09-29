package com.chatop.api.service;

import java.time.Instant;

import org.springframework.security.oauth2.jose.jws.MacAlgorithm;
import org.springframework.security.oauth2.jwt.JwsHeader;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.stereotype.Service;

import com.chatop.api.config.JwtProperties;
import com.chatop.api.entity.User;

/**
 * Issues the JSON Web Tokens that authenticate the users.
 */
@Service
public class JwtService {

    private final JwtProperties jwtProperties;
    private final JwtEncoder jwtEncoder;

    public JwtService(JwtProperties jwtProperties, JwtEncoder jwtEncoder) {
        this.jwtProperties = jwtProperties;
        this.jwtEncoder = jwtEncoder;
    }

    /**
     * Generates a signed token identifying the given user.
     *
     * <p>The token carries the user's id as subject and expires after the
     * duration set by the {@code jwt.expiration} property.
     *
     * @param user the user, already saved in the database
     * @return the token value
     */
    public String generateToken(User user) {
        JwsHeader header = JwsHeader.with(MacAlgorithm.HS256).build();

        Instant now = Instant.now();
        JwtClaimsSet claims = JwtClaimsSet.builder()
                .subject(String.valueOf(user.getId()))
                .issuedAt(now)
                .expiresAt(now.plus(jwtProperties.expiration()))
                .build();

        return jwtEncoder.encode(JwtEncoderParameters.from(header, claims)).getTokenValue();
    }

}
