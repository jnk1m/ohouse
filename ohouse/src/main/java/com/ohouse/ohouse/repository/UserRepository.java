package com.ohouse.ohouse.repository;

import com.ohouse.ohouse.domain.UserDTO;
import com.ohouse.ohouse.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User,Long> {
  Optional<User> findByEmail(String email);

  @Query("SELECT new com.ohouse.ohouse.domain.UserDTO(u.userName, u.phoneNumber, u.email, u.isPhoneVerified) " +
          "FROM User u " +
          "WHERE u.email = :email")
  Optional<UserDTO> findByEmailToDTO (String email);
}
