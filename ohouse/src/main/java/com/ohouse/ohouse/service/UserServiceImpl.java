package com.ohouse.ohouse.service;

import com.ohouse.ohouse.domain.UserDTO;
import com.ohouse.ohouse.entity.User;
import com.ohouse.ohouse.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
  private final UserRepository userRepository;

  @Override
  public UserDTO getUserByEmail(String email) {
    return userRepository.findByEmailToDTO(email).orElseThrow();
  }

  @Override
  @Transactional
  public void savePhoneNumberAndMarkVerified(int userId, String phoneNumber) {
    User user = userRepository.findById(userId).orElseThrow(() -> new IllegalArgumentException("No user found with id: " + userId));
    user.setPhoneNumber(phoneNumber);
    user.setPhoneVerified(true);
  }

}
