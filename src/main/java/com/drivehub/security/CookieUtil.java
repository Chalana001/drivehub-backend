package com.drivehub.security;

import org.springframework.http.ResponseCookie;

public class CookieUtil {

    public static ResponseCookie accessCookie(String token) {
        return ResponseCookie.from("access_token", token)
                .httpOnly(true)
                .secure(true) // ✅ set true in production HTTPS
                .path("/")
                .maxAge(15 * 60)
                .sameSite("Lax")
                .build();
    }

    public static ResponseCookie refreshCookie(String token) {
        return ResponseCookie.from("refresh_token", token)
                .httpOnly(true)
                .secure(true)
                .path("/auth") // refresh only used on auth routes
                .maxAge(7 * 24 * 60 * 60)
                .sameSite("Lax")
                .build();
    }

    public static ResponseCookie clearAccess() {
        return ResponseCookie.from("access_token", "")
                .httpOnly(true)
                .secure(true)
                .path("/")
                .maxAge(0)
                .build();
    }

    public static ResponseCookie clearRefresh() {
        return ResponseCookie.from("refresh_token", "")
                .httpOnly(true)
                .secure(true)
                .path("/auth")
                .maxAge(0)
                .build();
    }
}
