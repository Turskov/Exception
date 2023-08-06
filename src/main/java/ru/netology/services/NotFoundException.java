package ru.netology.services;

public class NotFoundException extends RuntimeException {
    public NotFoundException(String s) {
        super(s);
    }
}