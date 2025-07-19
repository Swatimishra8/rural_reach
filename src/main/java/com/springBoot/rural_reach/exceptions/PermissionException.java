package com.springBoot.rural_reach.exceptions;


public class PermissionException extends RuntimeException {
    public PermissionException() {
        super("Permission Not Found!!");
    }
}