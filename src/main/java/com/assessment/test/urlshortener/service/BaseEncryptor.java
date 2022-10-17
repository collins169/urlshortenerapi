package com.assessment.test.urlshortener.service;

import org.springframework.stereotype.Service;

@Service
public class BaseEncryptor {
    private static final String allowedString = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
    private final char[] allowedCharacters = allowedString.toCharArray();
    private final int base = allowedCharacters.length;

    public String encode(Long input){
        StringBuilder encodedString = new StringBuilder();

        if(input == 0) {
            return String.valueOf(allowedCharacters[0]);
        }

        while (input > 0) {
            encodedString.append(allowedCharacters[Math.toIntExact((input % base))]);
            input = input / base;
        }

        return encodedString.reverse().toString();
    }

    public Long decode(String input) {
        char[] characters = input.toCharArray();
        int length = characters.length;

        long decoded = 0;

        //counter is used to avoid reversing input string
        var counter = 1;
         for (char character : characters) {
            decoded += allowedString.indexOf(character) * Math.pow(base, length - counter);
            counter++;
        }
        return decoded;
    }
}
