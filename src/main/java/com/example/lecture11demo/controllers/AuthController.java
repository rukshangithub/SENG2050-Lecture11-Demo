package com.example.lecture11demo.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;


import com.example.lecture11demo.util.JwtUtil;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import jakarta.servlet.http.HttpServletRequest;

import java.util.Map;
import org.springframework.web.bind.annotation.GetMapping;
import com.example.lecture11demo.dtos.LoginDto;


@RestController
@RequestMapping("/api")
public class AuthController {


    
    // @PostMapping("/login")
    // public ResponseEntity<Void> loginStudent(@Valid @RequestBody LoginDto loginInfo)
    // {
    //     System.out.println("loginInfo: " + loginInfo.toString());

    //     return ResponseEntity.status(HttpStatus.OK).build();
    // }

    @PostMapping("/login")
    public ResponseEntity<?> login(@Valid @RequestBody LoginDto loginInfo, HttpServletResponse response) {
        // NOTE: This is for demo purposes only 
        // You should not put the logic in controller class (rather service class) 

        String studentNo = loginInfo.getStudentNo();
        String password = loginInfo.getPassword();

        System.out.println("Student No: " + studentNo);
        System.out.println("Password: " + password);
        
          // NOTE: Demo purposes - Need to read from database for authentication (this is for demo purposes)
        if ("user".equals(studentNo) && "pass".equals(password)) {
            String accessToken = JwtUtil.generateAccessToken(studentNo);
            String refreshToken = JwtUtil.generateRefreshToken(studentNo);
            
            // Storing refresh token in an HttpOnly cookie
            Cookie cookie = new Cookie("refreshToken", refreshToken);
            cookie.setHttpOnly(true);   // HttpOnly cookie
            cookie.setSecure(true);         // sent only via HTTPS (encrypted channels)
            cookie.setPath("/api/refresh");  // where the cookie can be sent to
            cookie.setMaxAge(60 * 60);           // 1 hour
            response.addCookie(cookie);          // add the cookie to the response
                 
            return ResponseEntity.ok(Map.of("token", accessToken));
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid credentials");
        }
    }
    

    @PostMapping("/refresh")
    public ResponseEntity<?> refresh(HttpServletRequest request) {
        
        // Find the refreshToken cookie
        String refreshToken = null;
        for (Cookie cookie : request.getCookies()) {
            if (cookie.getName().equals("refreshToken")) {
                refreshToken = cookie.getValue();
                break;
            }
        }

        if (refreshToken == null) return ResponseEntity.status(401).body("Missing refresh token");

        // Check whether refresh token in valid
        String username = JwtUtil.validateRefreshToken(refreshToken);
        if (username == null) return ResponseEntity.status(401).body("Invalid refresh token");

        String newAccessToken = JwtUtil.generateAccessToken(username);
        return ResponseEntity.ok(Map.of("token", newAccessToken));
    }

    @PostMapping("/logout")
    public ResponseEntity<?> logout(HttpServletResponse response, HttpServletRequest request) {
        for (Cookie cookie : request.getCookies()) {
            if (cookie.getName().equals("refreshToken")) {
                JwtUtil.invalidateRefreshToken(cookie.getValue());
                cookie.setMaxAge(0);
                cookie.setPath("/api");
                response.addCookie(cookie);
                break;
            }
        }
        return ResponseEntity.ok("Logged out");
    }

    @PostMapping("/validate")
    public ResponseEntity<?> validateToken(@RequestHeader("Authorization") String authToken) {
        
        var token  = authToken.replace("Bearer ", "");

        if (JwtUtil.validateTokenAndGetUsername(token)==null)
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Map.of("message", "Invalid token"));
        else
            return ResponseEntity.ok(Map.of("message", "Valid token"));
            
    }

    // @GetMapping("/me")
    // public ResponseEntity<?> me(){
     
    //     var authentication = SecurityContextHolder.getContext().getAuthentication();
    //     var user = authentication.getPrincipal();
    //     return ResponseEntity.ok(new UserDto(user.toString()));
    // }
        
}