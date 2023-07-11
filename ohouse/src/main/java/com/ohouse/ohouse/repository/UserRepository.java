package com.ohouse.ohouse.repository;

import com.ohouse.ohouse.domain.UserDTO;
import com.ohouse.ohouse.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Integer> {
  Optional<User> findByEmail(String email);

  @Query("SELECT COUNT(u.userId) FROM User u WHERE u.userId = :userId AND u.isPhoneVerified = true")
  int countVerifiedPhoneByUserId(int userId);

  @Query("SELECT u.phoneNumber FROM User u WHERE u.userId = :userId")
  String findPhoneNumberById(int userId);
}
