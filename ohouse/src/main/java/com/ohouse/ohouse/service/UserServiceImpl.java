package com.ohouse.ohouse.service;

import com.ohouse.ohouse.domain.UserDTO;
import com.ohouse.ohouse.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
  private final UserRepository userRepository;

  @Override
  public UserDTO getUserByEmail(String email) {
    return userRepository.findByEmailToDTO(email).orElseThrow();
  }
}
