package com.drivehub.security;

import org.springframework.http.ResponseCookie;

public class CookieUtil {

    public static ResponseCookie accessCookie(String token) {
        return ResponseCookie.from("access_token", token)
                .httpOnly(true)
                .secure(true) // Required for SameSite=None
                .path("/")
                .maxAge(15 * 60)
                .sameSite("None") // ONLY use this one
                .build();
    }

    public static ResponseCookie refreshCookie(String token) {
        return ResponseCookie.from("refresh_token", token)
                .httpOnly(true)
                .secure(true)
                .path("/auth")
                .maxAge(7 * 24 * 60 * 60)
                .sameSite("None") // ONLY use this one
                .build();
    }

    // Clear methods (Ensure these match too)
    public static ResponseCookie clearAccess() {
        return ResponseCookie.from("access_token", "")
                .httpOnly(true)
                .secure(true)
                .path("/")
                .maxAge(0)
                .sameSite("None") // Good practice to match creation settings even on clear
                .build();
    }

    public static ResponseCookie clearRefresh() {
        return ResponseCookie.from("refresh_token", "")
                .httpOnly(true)
                .secure(true)
                .path("/auth")
                .maxAge(0)
                .sameSite("None")
                .build();
    }
}