package com.mikitjuk.advt.domain.repository;

import com.mikitjuk.advt.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Integer> {
}
