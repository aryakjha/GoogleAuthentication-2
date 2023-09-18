package com.venture.demo.repository;

import org.springframework.boot.autoconfigure.security.SecurityProperties.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<Long,User>{
		com.venture.demo.model.User findByusername(String username);
}
