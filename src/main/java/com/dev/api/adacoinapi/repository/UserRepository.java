package com.dev.api.adacoinapi.repository;

import com.dev.api.adacoinapi.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {

}
