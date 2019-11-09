package com.anan.rbac.utils;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class Test {

    /**
     * $2a$10$6UzMN/VMh73bAHmkMwgFZukOThm/EPzu/q.kTqCCpKUMFtljeKBaq
     * $2a$10$y2ePh8Obii95Ko.nMuGU2OyVyMbk892.05OpMK5Oz5qRKiQLLjJYC
     * @param args
     */
    public static void main(String[] args) {
        BCryptPasswordEncoder bce = new BCryptPasswordEncoder();

        String encode = bce.encode("1000000");
        System.out.println(encode);

        boolean matches = bce.matches("1000000", encode);
        System.out.println(matches);
    }
}
