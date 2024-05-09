package com.example.SocialMedia.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.SocialMedia.entity.Account;

@Repository
public interface AccountRepository extends JpaRepository<Account,Integer> {

    Account findByUsernameAndPassword(String username, String password);
}
