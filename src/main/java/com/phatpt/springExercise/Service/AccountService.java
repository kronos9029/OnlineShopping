package com.phatpt.springExercise.Service;

import java.util.Optional;

import com.phatpt.springExercise.Entity.Account;
import com.phatpt.springExercise.Repository.AccountRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AccountService {
    private final AccountRepository accountRepository;

    @Autowired
    public AccountService(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    public Optional<Account> getAccountByUsername(String username){
        return accountRepository.findByUsername(username);
    }

    
}
