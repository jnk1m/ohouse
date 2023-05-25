package com.ohouse.ohouse.service;

import com.ohouse.ohouse.domain.UserDTO;
import com.ohouse.ohouse.entity.User;
import com.ohouse.ohouse.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
  private final UserRepository userRepository;

  @Override
  public UserDTO getUserByEmail(String email) {
    User user = userRepository.findByEmail(email).orElseThrow();
    return new UserDTO(user.getUserId(), user.getUserName(), user.getPhoneNumber(), user.getEmail(), user.isPhoneVerified(), user.getRole(), user.getOrderCount(), user.isFreeSide());
  }
}
