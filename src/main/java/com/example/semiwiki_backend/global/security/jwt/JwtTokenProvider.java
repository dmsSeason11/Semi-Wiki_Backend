package com.example.semiwiki_backend.global.security.jwt;

import com.example.semiwiki_backend.global.security.auth.CustomUserDetails;
import com.example.semiwiki_backend.global.security.auth.CustomUserDetailsService;
import com.example.semiwiki_backend.global.security.exception.JwtExpiredException;
import com.example.semiwiki_backend.global.security.exception.JwtInvalidException;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.concurrent.TimeUnit;

@Slf4j
@Component
@RequiredArgsConstructor
public class JwtTokenProvider{

  private final JwtProperties jwtProperties;
  private final CustomUserDetailsService customUserDetailsService;
  private final RedisTemplate<String,String> redisTemplate;
  private final static String ACCESS_TOKEN="access_token";
  private final static String REFRESH_TOKEN="refresh_token";
  private static final String REDIS_PREFIX = "RT:";

  private SecretKey secretKey;

  @PostConstruct
  public void init() {
    this.secretKey = Keys.hmacShaKeyFor(jwtProperties.getSecretKey().getBytes(StandardCharsets.UTF_8));
  }



  public String generateAccessToken(String accountId) {
    return generateToken(accountId,ACCESS_TOKEN,jwtProperties.getAccessTokenExpiresIn());
  }

  public String generateRefreshToken(String accountId) {

    String refreshToken = generateToken(accountId,REFRESH_TOKEN,jwtProperties.getRefreshTokenExpiresIn());
    String key = REDIS_PREFIX + accountId;
    redisTemplate.opsForValue()
            .set(key, refreshToken, jwtProperties.getRefreshTokenExpiresIn(), TimeUnit.MILLISECONDS);
    return refreshToken;
  }

  private String generateToken(String accountId,String type, Long time) {

    Date now = new Date();
    return Jwts.builder()
        .signWith(SignatureAlgorithm.HS256,secretKey)
        .setSubject(accountId)
        .setIssuedAt(now)
        .setExpiration(new Date(now.getTime()+time))
        .compact();
  }

  //토큰 유효성 검사
  public boolean validateToken(String token) {

    try{
      Jwts.parserBuilder()
          .setSigningKey(secretKey)
          .build()
          .parseClaimsJws(token)
          .getBody();
      return true;
    }catch (ExpiredJwtException e){
      throw new JwtExpiredException();

    } catch (JwtException e) {
      throw new JwtInvalidException();
    }

  }

  // 리프레시 토큰 유효성 검사
  // 유효성 검사 시 리프레시 토큰이 만료되었다면 로그인 화면으로 이동
  public boolean validateRefreshToken(String accountId,String refreshToken) {

    String key = REDIS_PREFIX + accountId;
    String storedRefreshToken = redisTemplate.opsForValue().get(key);

    if(storedRefreshToken==null) throw  new JwtExpiredException();

    if(!refreshToken.equals(storedRefreshToken)) throw new JwtInvalidException();

    return validateToken(refreshToken);
  }

  //AccessToken 재발급
  public String reissueAccessToken(String accountId,String refreshToken) {
    if(validateRefreshToken(accountId,refreshToken)){
      return generateAccessToken(accountId);
    }
    throw new JwtInvalidException();
  }

  //로그아웃
  public void deleteRefreshToken(String accountId) {
    String key = REDIS_PREFIX + accountId;
    redisTemplate.delete(key);
  }

  //토큰 값 가져오기
  public String resolveToken(HttpServletRequest httpServletRequest) {
    String bearerToken = httpServletRequest.getHeader("Authorization");
    if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
      return bearerToken.substring(7);
    }
    return null;
  }


  public UsernamePasswordAuthenticationToken getAuthentication(String token) {
    Claims claims =getClaims(token);
    CustomUserDetails customUserDetails =(CustomUserDetails)customUserDetailsService.loadUserByUsername(claims.getSubject());
    return new UsernamePasswordAuthenticationToken(customUserDetails,null,customUserDetails.getAuthorities());

  }

  private Claims getClaims(String token) {
    try {
      return Jwts.parserBuilder()
          .setSigningKey(secretKey)
          .build()
          .parseClaimsJws(token)
          .getBody();

    }catch (ExpiredJwtException e){
      log.warn("jwt expired",e);
      throw new JwtExpiredException();

    }catch (JwtException e) {
      log.warn("jwt error",e);
      throw new JwtInvalidException();
    }
  }

}
