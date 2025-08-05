package com.example.semiwiki_backend.global.security.jwt;

import com.example.semiwiki_backend.global.security.auth.CustomUserDetailsService;
import com.example.semiwiki_backend.global.security.config.JwtExpiredException;
import io.jsonwebtoken.*;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.rsocket.RSocketSecurity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import javax.management.loading.PrivateClassLoader;
import java.net.http.HttpRequest;
import java.util.Date;

@Slf4j
@Component
@RequiredArgsConstructor
public class JwtTokenProvider{

  private final JwtProperties jwtProperties;
  private final CustomUserDetailsService customUserDetailsService;
  private final static String ACCESS_TOKEN="access_token";
  private final static String REFRESH_TOKEN="refresh_token";

  public String generateAccessToken(String accountId) {
    return generateToken(accountId,ACCESS_TOKEN,jwtProperties.getAccessTokenExpiresIn());
  }

  public String generateRefreshToken(String accountId) {
    return generateToken(accountId,REFRESH_TOKEN,jwtProperties.getRefreshTokenExpiresIn());
  }

  private String generateToken(String accountId,String type, Long time) {

    Date now = new Date();
    return Jwts.builder()
        .signWith(SignatureAlgorithm.HS256,jwtProperties.getSecretKey())
        .setSubject(accountId)
        .setIssuedAt(now)
        .setExpiration(new Date(now.getTime()+time))
        .setHeaderParam("typ",type)
        .compact();
  }

  //토큰 유효성 검사
  public boolean validateToken(String token) {
    try{
      Claims claims = Jwts.parserBuilder()
          .setSigningKey(jwtProperties.getSecretKey())
          .build()
          .parseClaimsJwt(token)
          .getBody();
      return true;
    }catch (ExpiredJwtException e){
      log.warn("jwt expired",e);
      throw new JwtExpiredException("jwt expired");
    } catch (RuntimeException e) {
      log.warn("jwt error",e);
      throw new RuntimeException("jwt error");
    }
  }

  //토큰 값 가져오기
  public String resolveToken(HttpServletRequest httpServletRequest) {
    String bearerToken = httpServletRequest.getHeader("Authorization");
    if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
      return bearerToken.substring(7);
    }
    return null;
  }

  //아직 구현안됨 우현이 기다려
//  public UsernamePasswordAuthenticationToken getAuthentication(String token) {
//    Claims claims =getClaims(token);
//    String username = claims.getSubject();
//
//  }

  private Claims getClaims(String token) {
    try {
      return Jwts.parserBuilder()
          .setSigningKey(jwtProperties.getSecretKey())
          .build()
          .parseClaimsJws(token)
          .getBody();

    }catch (ExpiredJwtException e){
      log.warn("jwt expired",e);
      throw new JwtExpiredException("jwt expired");

    }catch (JwtException e) {
      log.warn("jwt error",e);
      throw new RuntimeException("jwt error");
    }
  }




}
