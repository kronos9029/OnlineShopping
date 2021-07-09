package com.phatpt.springExercise.Security.Service;

import com.phatpt.springExercise.Entity.Account;
import com.phatpt.springExercise.Repository.AccountRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AccountDetailService implements UserDetailsService {

    private final AccountRepository accountRepository;

    @Autowired
    public AccountDetailService(com.phatpt.springExercise.Repository.AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Account account = accountRepository.findByUsername(username)
                            .orElseThrow(() -> new UsernameNotFoundException("User Not Found with -> username or email : " + username));
        return AccountDetailImpl.build(account);
    }
    
}
