package com.example.semiwiki_backend.global.security.auth;

import com.example.semiwiki_backend.domain.user.entity.User;
import com.example.semiwiki_backend.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;


@Component
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

  private final UserRepository userRepository;

  @Override
  public UserDetails loadUserByUsername(String accountId) throws UsernameNotFoundException {
    User user=userRepository.findByAccountId(accountId)
            .orElseThrow(()->new UsernameNotFoundException(accountId));
    return new  CustomUserDetails(user);


  }

}
