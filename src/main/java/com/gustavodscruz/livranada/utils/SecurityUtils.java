package com.gustavodscruz.livranada.utils;

import at.favre.lib.crypto.bcrypt.BCrypt;

public class SecurityUtils {
    private SecurityUtils () {}
    public static String hashString(String value) {
        if (value == null) return null;
        return BCrypt.withDefaults().hashToString(12, value.toCharArray());
    }
}
