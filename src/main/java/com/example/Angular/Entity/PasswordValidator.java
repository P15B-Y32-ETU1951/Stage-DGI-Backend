package com.example.Angular.Entity;

import lombok.Data;

@Data
public class PasswordValidator {

    public static boolean isPasswordComplex(String password) {
        if (password == null || password.length() < 12) {
            return false;
        }

        String regex = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[!@#$%^&*(),.?\":{}|<>]).{12,}$";
        return password.matches(regex) && !isPredictable(password);
    }

    public static boolean isPredictable(String password) {
        String[] predictablePatterns = { "123", "abc", "password", "qwerty", "admin" };
        for (String pattern : predictablePatterns) {
            if (password.toLowerCase().contains(pattern)) {
                return true;
            }
        }
        return false;
    }
}