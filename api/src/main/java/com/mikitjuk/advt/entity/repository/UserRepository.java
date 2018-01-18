package com.mikitjuk.advt.entity.repository;

import com.mikitjuk.advt.entity.User;
import com.mikitjuk.advt.entity.types.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

public interface UserRepository extends JpaRepository<User, Integer>, JpaSpecificationExecutor {
	User findByEmail(String email);
	List<User> findByRole(UserRole role);
}
