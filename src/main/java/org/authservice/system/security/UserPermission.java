package org.authservice.system.security;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum UserPermission {

    USER_READ("user:read"),
    USER_WRITE("user:write");


    private final String permission;

    public String getPermission()
    {
        return permission;
    }

}

