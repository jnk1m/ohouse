package com.ohouse.ohouse.service;

import com.ohouse.ohouse.entity.User;
import com.ohouse.ohouse.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
  private final UserRepository userRepository;

  @Transactional
  @Override
  public User getUserById(int userId) {
    return userRepository.findById(userId).orElseThrow(() -> new IllegalArgumentException("User not found with id " + userId));
  }

  @Override
  @Transactional
  public void savePhoneNumberAndMarkVerified(int userId, String phoneNumber) {
    User user = userRepository.findById(userId).orElseThrow(() -> new IllegalArgumentException("No user found with id: " + userId));
    user.updateVerifiedPhoneNumber(phoneNumber);
  }

  @Override
  public boolean checkPhoneVerificationStatus(int userId) {
    return userRepository.countVerifiedPhoneByUserId(userId) > 0;
  }

  @Override
  public String getPhoneById(int userId) {
    return userRepository.findPhoneNumberById(userId);
  }

}
