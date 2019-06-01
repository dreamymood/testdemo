package com.example.securityjpademo.repository;

import com.example.securityjpademo.pojo.FKUser;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<FKUser, Long> {
    FKUser findByLoginName(String loginName);
}
