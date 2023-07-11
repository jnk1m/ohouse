package com.ohouse.ohouse.service;

import com.ohouse.ohouse.entity.User;

public interface UserService {

  User getUserById(int userId);

  void savePhoneNumberAndMarkVerified(int userId, String phoneNumber);

  boolean checkPhoneVerificationStatus(int userId);

  String getPhoneById(int userId);

}
