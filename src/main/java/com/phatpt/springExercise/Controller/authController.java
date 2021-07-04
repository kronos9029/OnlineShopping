package com.phatpt.springExercise.Controller;

import javax.validation.Valid;

import com.phatpt.springExercise.Service.authService;
import com.phatpt.springExercise.payload.request.loginRequest;
import com.phatpt.springExercise.payload.request.registerRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/auth")
public class authController {
    
    private final authService authService;

    @Autowired
    public authController(authService authService) {
        this.authService = authService;
    }

    @PostMapping("/signin")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody loginRequest request){
        return authService.authenticateUser(request);
    }

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@Valid @RequestBody registerRequest request){
        return authService.registerUser(request);
    }
    
}
