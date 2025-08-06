package com.example.semiwiki_backend.global.security.jwt;

import com.example.semiwiki_backend.global.security.auth.CustomUserDetails;
import com.example.semiwiki_backend.global.security.auth.CustomUserDetailsService;
import com.example.semiwiki_backend.global.security.exception.JwtExpiredException;
import com.example.semiwiki_backend.global.security.exception.JwtValidationException;
import io.jsonwebtoken.*;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Component;

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
          .parseClaimsJws(token)
          .getBody();
      return true;
    }catch (ExpiredJwtException e){
      log.warn("jwt expired",e);
      throw new JwtExpiredException("jwt expired",e);
    } catch (JwtException e) {
      log.warn("jwt validate",e);
      throw new JwtValidationException("jwt validate",e);
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


  public UsernamePasswordAuthenticationToken getAuthentication(String token) {
    Claims claims =getClaims(token);
    CustomUserDetails customUserDetails =(CustomUserDetails)customUserDetailsService.loadUserByUsername(claims.getSubject());
    return new UsernamePasswordAuthenticationToken(customUserDetails,null,customUserDetails.getAuthorities());

  }

  private Claims getClaims(String token) {
    try {
      return Jwts.parserBuilder()
          .setSigningKey(jwtProperties.getSecretKey())
          .build()
          .parseClaimsJws(token)
          .getBody();

    }catch (ExpiredJwtException e){
      log.warn("jwt expired",e);
      throw new JwtExpiredException("jwt expired",e);

    }catch (JwtException e) {
      log.warn("jwt error",e);
      throw new JwtValidationException("jwt error",e);
    }
  }




}
