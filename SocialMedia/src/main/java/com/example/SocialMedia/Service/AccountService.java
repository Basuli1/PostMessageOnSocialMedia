package com.example.SocialMedia.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;

import com.example.SocialMedia.Exception.DuplicateUsernameException;
import com.example.SocialMedia.Exception.InvalidRegistrationException;
import com.example.SocialMedia.Exception.UserNotFoundException;
import com.example.SocialMedia.Repository.AccountRepository;
import com.example.SocialMedia.entity.Account;



@Service
public class AccountService {
    private AccountRepository accountRepository;

    @Autowired
    public AccountService(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }


    //register
    public Account registerAccount(Account account) {
        if (account.getUsername().isBlank() || account.getPassword().length() < 4) {
            throw new InvalidRegistrationException("Username cannot be blank and password must be at least 4 characters long.");
        }
        try {
            return accountRepository.save(account);
        } catch (DuplicateKeyException e) {
            throw new DuplicateUsernameException("Username already exists.");
        }
    }


    //login
    public Account authenticateAccount(Account account) {
        String username = account.getUsername();
        String password = account.getPassword();
        return accountRepository.findByUsernameAndPassword(username, password);
    }


    //find by username
    public Account findAccount(int accountId) throws UserNotFoundException{
        return accountRepository.findById(accountId).orElseThrow(()->new UserNotFoundException("user not found"));
        
    }
}