package com.sqs.pokesearch.service;

public record RegisterRequest(
        String username,
        String password,
        String firstName,
        String lastName

) {}
