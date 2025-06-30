package com.sqs.pokesearch.service;

public record LoginRequest(
        String username,
        String password
) {}
