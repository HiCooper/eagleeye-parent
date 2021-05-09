package com.berry.eagleeye.management.security;

import com.alibaba.fastjson.JSON;
import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.Claim;
import com.berry.eagleeye.management.security.dto.UserInfoDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.io.UnsupportedEncodingException;
import java.util.Arrays;
import java.util.Collection;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author Berry_Cooper.
 * Description:
 * Date: 2018-03-31
 * Time: 14:42
 */
public class TokenParse {

    private final static Logger logger = LoggerFactory.getLogger(TokenParse.class);

    /**
     * token中用户权限key
     */
    private static final String AUTHORITIES_KEY = "auth_com";

    private static final String USER_ID_KEY = "userId";

    private static final String SECRET = "com.berry.secret";

    private static final String ISSUER = "okmnji123";
    /**
     * Verify a Token
     *
     * @param token 待验证token
     * @return boolean
     */
    public static boolean validateToken(String token) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(SECRET);
            JWTVerifier verifier = JWT.require(algorithm)
                    .withIssuer(ISSUER)
                    .build(); //Reusable verifier instance
            verifier.verify(token);
            return true;
        } catch (Exception e) {
            logger.error("JWT 验证失败");
        }
        return false;
    }

    /**
     * 从 jwt 获取权限信息
     *
     * @param jwt
     * @return 授权凭证
     */
    public static Authentication getAuthentication(String jwt) throws UnsupportedEncodingException {
        Map<String, Claim> claimMap = getClaimMap(jwt);
        logger.debug("claimMap: {}", JSON.toJSONString(claimMap));
        Collection<? extends GrantedAuthority> authorities =
                Arrays.stream(claimMap.get(AUTHORITIES_KEY).asString().split(","))
                        .map(SimpleGrantedAuthority::new)
                        .collect(Collectors.toList());

        UserInfoDTO principal = new UserInfoDTO();
        principal.setUsername(claimMap.get("sub").asString());
        principal.setId(claimMap.get(USER_ID_KEY).asLong());
        return new UsernamePasswordAuthenticationToken(principal, jwt, authorities);
    }

    private static Map<String, Claim> getClaimMap(String jwt) throws UnsupportedEncodingException {
        Algorithm algorithm = Algorithm.HMAC256(SECRET);
        JWTVerifier verifier = JWT.require(algorithm)
                .withIssuer(ISSUER)
                .build(); //Reusable verifier instance
        return verifier.verify(jwt).getClaims();
    }
}
