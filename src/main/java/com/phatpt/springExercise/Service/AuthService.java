package com.phatpt.springExercise.Service;

import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.phatpt.springExercise.Entity.Account;
import com.phatpt.springExercise.Entity.Role;
import com.phatpt.springExercise.Entity.RoleName;
import com.phatpt.springExercise.Repository.AccountRepository;
import com.phatpt.springExercise.Repository.RoleRepository;
import com.phatpt.springExercise.Security.Service.AccountDetailImpl;
import com.phatpt.springExercise.Security.jwt.JwtUtils;
import com.phatpt.springExercise.payload.request.loginRequest;
import com.phatpt.springExercise.payload.request.registerRequest;
import com.phatpt.springExercise.payload.response.jwtResponse;
import com.phatpt.springExercise.payload.response.messageResponse;

import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {
    
    private final AuthenticationManager authenticationManager;
    private final AccountRepository accountRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder encoder;
    private final JwtUtils jwtUtils;

    public AuthService(AuthenticationManager authenticationManager,
                        AccountRepository accountRepository,
                        RoleRepository roleRepository, PasswordEncoder encoder,
                        JwtUtils jwtUtils) {
        this.authenticationManager = authenticationManager;
        this.accountRepository = accountRepository;
        this.roleRepository = roleRepository;
        this.encoder = encoder;
        this.jwtUtils = jwtUtils;
    }

    public ResponseEntity<?> authenticateUser(loginRequest request, HttpServletRequest seRequest) {
        Authentication authentication = authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword()));

        
        SecurityContextHolder.getContext().setAuthentication(authentication);
        
        String jwt = jwtUtils.generateJwtToken(authentication);

        AccountDetailImpl userDetails = (AccountDetailImpl) authentication.getPrincipal();
        List<String> roles = userDetails.getAuthorities().stream()
                                                         .map(item -> item.getAuthority())
                                                         .collect(Collectors.toList());

        
        
        seRequest.getSession().setAttribute("currentUsername", request.getUsername());
        return ResponseEntity.ok(new jwtResponse(jwt,
                                                 userDetails.getId(),
                                                 userDetails.getUsername(),
                                                 userDetails.getEmail(),
                                                 roles));
    }

    public ResponseEntity<?> registerUser(registerRequest request) {
        if (accountRepository.existsByUsername(request.getUsername())) {
            return ResponseEntity
                .badRequest()
                .body(new messageResponse("Error: Username is already taken!"));
        }

        if (accountRepository.existsByEmail(request.getEmail())) {
            return ResponseEntity
                .badRequest()
                .body(new messageResponse("Error: Email is already in use!"));
        }

        Account account = new Account(request.getUsername(), 
                                      request.getEmail(), 
                                      encoder.encode(request.getPassword()), 
                                      request.getFullName(), 
                                      request.getAddress(), 
                                      request.getPhone(), 
                                      new Date());

        Set<String> strRoles = request.getRole();
        Set<Role> roles = new HashSet<>();

        if (strRoles == null) {
            Role userRole = roleRepository.findByName(RoleName.ROLE_USER)
                .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
            roles.add(userRole);
        } else {
            strRoles.forEach(role -> {
                switch (role.toLowerCase()) {
                    case "admin":
                        Role adminRole = roleRepository.findByName(RoleName.ROLE_ADMIN)
                            .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                        roles.add(adminRole);

                        break;
                    default:
                        Role userRole = roleRepository.findByName(RoleName.ROLE_USER)
                            .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                        roles.add(userRole);
                }
            });
        }

        account.setRole(roles);
        accountRepository.save(account);

        return ResponseEntity.ok(new messageResponse("User registered successfully!"));
    }
    
    public void deleteSession(HttpSession session){
        session.invalidate();
    }

}
