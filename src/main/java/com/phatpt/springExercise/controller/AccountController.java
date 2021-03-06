package com.phatpt.springExercise.controller;

import java.util.Optional;

import javax.servlet.http.HttpSession;

import com.phatpt.springExercise.entity.Account;
import com.phatpt.springExercise.service.AccountService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/accounts")
@CrossOrigin(origins = "*", maxAge = 3600)
public class AccountController {
    private final AccountService accountService;

    @Autowired
    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    @GetMapping("/profile")
    public Optional<Account> getAccountByUsername(HttpSession session){
        return this.accountService.findAccountByUsername(session);
    }
    
}
