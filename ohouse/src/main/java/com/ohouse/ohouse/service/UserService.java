package com.ohouse.ohouse.service;

import com.ohouse.ohouse.domain.UserDTO;

public interface UserService {

  UserDTO getUserByEmail(String email);
}
