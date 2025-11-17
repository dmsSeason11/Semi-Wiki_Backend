package com.example.semiwiki_backend.domain.auth.service;

import com.example.semiwiki_backend.domain.auth.exception.AccountNotFoundException;
import com.example.semiwiki_backend.domain.auth.exception.NotAccountOwnerException;
import com.example.semiwiki_backend.domain.user.entity.User;
import com.example.semiwiki_backend.domain.user.repository.UserRepository;
import com.example.semiwiki_backend.global.security.auth.CustomUserDetails;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DeleteUserService {

  private final UserRepository userRepository;

  @Transactional
  public void execute (String accountId, Authentication authentication) {
    CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
    Integer userId = userDetails.getId();

    User user = userRepository.findById(userId).orElseThrow(AccountNotFoundException::new);

    User targetUser = userRepository.findByAccountId(accountId).orElseThrow(AccountNotFoundException::new);

    if(user.getId() != targetUser.getId()) {
      throw new NotAccountOwnerException();
    }
    userRepository.deleteByAccountId(accountId);
  }
}
