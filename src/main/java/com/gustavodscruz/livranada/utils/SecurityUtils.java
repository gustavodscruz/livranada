package com.gustavodscruz.livranada.utils;

import at.favre.lib.crypto.bcrypt.BCrypt;

public class SecurityUtils {
    private SecurityUtils () {}

    /**
     * @param value the string to be hashed
     * @return a bcrypt hash of the input string with a cost factor of 12, or null if the input is null
     */
    public static String hashString(String value) {
        if (value == null) return null;
        return BCrypt.withDefaults().hashToString(12, value.toCharArray());
    }
}
