package com.phatpt.springExercise.Controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import com.phatpt.springExercise.Service.AuthService;
import com.phatpt.springExercise.Validate.Validate;
import com.phatpt.springExercise.payload.request.LoginRequest;
import com.phatpt.springExercise.payload.request.RegisterRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/public")
public class AuthController {
    
    private final AuthService authService;

    @Autowired
    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/login")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest request, HttpServletRequest seRequest){
        Validate.validateLogin(request);
        return authService.authenticateUser(request, seRequest);
        
    }

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@Valid @RequestBody RegisterRequest request){
        return authService.registerUser(request);
    }

    @PostMapping("/deleteSession")
    public void deleteSession(HttpSession session){
         authService.deleteSession(session);
    }
    
}
