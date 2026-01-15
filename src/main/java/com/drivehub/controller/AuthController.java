package com.drivehub.controller;

import com.drivehub.model.dto.*;
import com.drivehub.security.CookieUtil;
import com.drivehub.service.AuthService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@CrossOrigin
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @GetMapping("/me")
    public ResponseEntity<?> me() {
        return ResponseEntity.ok(authService.me());
    }


    @PostMapping("/register")
    public ResponseEntity<?> register(@Valid @RequestBody RegisterRequest request) {
        return ResponseEntity.ok(authService.register(request));
    }

    // ✅ login -> set cookies
    @PostMapping("/login")
    public ResponseEntity<?> login(@Valid @RequestBody LoginRequest request) {

        AuthTokens tokens = authService.login(request);

        return ResponseEntity.ok()
                .header("Set-Cookie", CookieUtil.accessCookie(tokens.getAccessToken()).toString())
                .header("Set-Cookie", CookieUtil.refreshCookie(tokens.getRefreshToken()).toString())
                .body("Login success");
    }

    @PostMapping("/refresh")
    public ResponseEntity<?> refresh(HttpServletRequest request) {

        AuthTokens tokens = authService.refresh(request);

        return ResponseEntity.ok()
                .header("Set-Cookie", CookieUtil.accessCookie(tokens.getAccessToken()).toString())
                .body("Access token refreshed");
    }

    @PostMapping("/logout")
    public ResponseEntity<?> logout() {

        authService.logout();

        return ResponseEntity.ok()
                .header("Set-Cookie", CookieUtil.clearAccess().toString())
                .header("Set-Cookie", CookieUtil.clearRefresh().toString())
                .body("Logged out");
    }
}
