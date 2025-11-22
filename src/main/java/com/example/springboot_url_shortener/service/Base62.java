package com.example.springboot_url_shortener.service;

final class Base62 {
    private static final String ALPHABET = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
    private static final int BASE = ALPHABET.length();

    private Base62() {}

    public static String encode(long num) {
        if (num == 0) return "0";
        StringBuilder sb = new StringBuilder();
        while (num > 0) {
            int rem = (int)(num % BASE);
            sb.append(ALPHABET.charAt(rem));
            num /= BASE;
        }
        return sb.reverse().toString();
    }

    public static long decode(String str) {
        long num = 0;
        for (char c : str.toCharArray()) {
            num = num * BASE + ALPHABET.indexOf(c);
        }
        return num;
    }
}
