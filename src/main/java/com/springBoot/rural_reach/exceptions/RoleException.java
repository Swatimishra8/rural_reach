package com.springBoot.rural_reach.exceptions;

public class RoleException extends RuntimeException {
    public RoleException() {
        super("Role Not Found");
    }
}