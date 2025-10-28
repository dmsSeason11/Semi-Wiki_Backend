package com.example.semiwiki_backend.domain.auth.service;

import com.example.semiwiki_backend.domain.user.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DeleteUserService {

  private final UserRepository userRepository;

  @Transactional
  public void execute (String accountId){
    userRepository.deleteByAccountId(accountId);
  }
}
