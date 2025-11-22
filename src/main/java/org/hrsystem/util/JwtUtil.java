package org.hrsystem.util;

import io.jsonwebtoken.*;
import org.hrsystem.dto.JwtDTO;
import org.hrsystem.enums.Role;

import javax.crypto.spec.SecretKeySpec;
import java.util.Date;

public class JwtUtil {
    private static final int tokenLiveTime = 1000 * 3600 * 24; // 1-day
    private static final int emailTokenLiveTime = 3600_000; // 1-hour
    private static final String secretKey = "blabl3abl3ablab5abl4abla8blab56labl0ablab8lablabla734dsbucsdj9024545g8sdb98hn23vdf0df20sjc987sdvd324shv";

    public static String encode(String email, Role role) { //Shifrlash
        JwtBuilder jwtBuilder = Jwts.builder();
        jwtBuilder.issuedAt(new Date());

        SignatureAlgorithm sa = SignatureAlgorithm.HS512;
        SecretKeySpec secretKeySpec = new SecretKeySpec(secretKey.getBytes(), sa.getJcaName());

        jwtBuilder.signWith(secretKeySpec);

        jwtBuilder.claim("email", email);
        jwtBuilder.claim("role", role);

        jwtBuilder.expiration(new Date(System.currentTimeMillis() + (tokenLiveTime)));
        jwtBuilder.issuer("HR-system");
        return jwtBuilder.compact();
    }

    public static String encodeForEmail(Integer profileId) {
        JwtBuilder jwtBuilder = Jwts.builder();
        jwtBuilder.issuedAt(new Date());
        SignatureAlgorithm sa = SignatureAlgorithm.HS512;
        SecretKeySpec secretKeySpec = new SecretKeySpec(secretKey.getBytes(), sa.getJcaName());
        jwtBuilder.signWith(secretKeySpec);
        jwtBuilder.claim("id", profileId);
        jwtBuilder.expiration(new Date(System.currentTimeMillis() + (emailTokenLiveTime)));
        jwtBuilder.issuer("HR-system");
        return jwtBuilder.compact();
    }

    public static JwtDTO decodeForSpringSecurity(String token) {
        SignatureAlgorithm sa = SignatureAlgorithm.HS512;
        SecretKeySpec secretKeySpec = new SecretKeySpec(secretKey.getBytes(), sa.getJcaName());
        JwtParser jwtParser = Jwts.parser()
                .verifyWith(secretKeySpec)
                .build();
        Jws<Claims> jws = jwtParser.parseSignedClaims(token);
        Claims claims = jws.getPayload();

        String email = (String) claims.get("email");
        String role = (String) claims.get("role");
        Role profileRole = Role.valueOf(role);
        return new JwtDTO(email, profileRole);
    }

    public static JwtDTO decode(String token) {
        SignatureAlgorithm sa = SignatureAlgorithm.HS512;
        SecretKeySpec secretKeySpec = new SecretKeySpec(secretKey.getBytes(), sa.getJcaName());
        JwtParser jwtParser = Jwts.parser()
                .verifyWith(secretKeySpec)
                .build();

        Jws<Claims> jws = jwtParser.parseSignedClaims(token);
        Claims claims = jws.getPayload();

        Integer id = (Integer) claims.get("id");
        String role = (String) claims.get("role");

        if (role != null) {
            Role profileRole = Role.valueOf(role);
            return new JwtDTO(id, profileRole);
        }
        return new JwtDTO(id);
    }
}
