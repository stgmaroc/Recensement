package com.example.recensement.Model;

public class Token {

    private String jwt;

    public Token(String jwt) {
        this.jwt = jwt;
    }

    public String getJwt() {
        return jwt;
    }
}
