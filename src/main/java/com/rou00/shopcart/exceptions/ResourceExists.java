package com.rou00.shopcart.exceptions;

public class ResourceExists extends RuntimeException{
    public ResourceExists(String message) {
        super(message);
    }
}
