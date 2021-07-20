package com.phatpt.springExercise.Controller;

import java.util.Optional;

import javax.servlet.http.HttpSession;

import com.phatpt.springExercise.Constant.ErrorCode;
import com.phatpt.springExercise.Constant.SuccessCode;
import com.phatpt.springExercise.Entity.Account;
import com.phatpt.springExercise.Entity.Response;
import com.phatpt.springExercise.Service.AccountService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/accounts")
@CrossOrigin(origins = "*", maxAge = 3600)
public class AccountController {
    private final AccountService accountService;

    private Response responseObj = new Response();

    @Autowired
    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    @GetMapping("/profile")
    public ResponseEntity<Response> getAccountByUsername(HttpSession session) throws Exception{
        try {
            Optional<Account> currentAccount = accountService.findAccountByUsername(session);
            if(currentAccount.isEmpty()){
                responseObj.setErrorCode(ErrorCode.ACCOUNT_NOT_FOUND);
            } else {
                responseObj.setSuccessCode(SuccessCode.ACCOUNT_FOUND);
                responseObj.setData(currentAccount);
            }
        } catch (Exception e) {
            responseObj.setErrorCode(ErrorCode.GET_ACCOUNT_FAIL);
            throw new RuntimeException("ERROR AT Account Controller: "+ e.getMessage());
        }

        return ResponseEntity.ok().body(responseObj);
    }
    
}
