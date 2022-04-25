package com.example.educationapp.enums;

public enum UserPermissions {

    USER_WRITE("user:write"),
    USER_READ("user:read"),
    USER_DELETE("user:delete"),
    USER_UPDATE("user:update");

    private String permission;

    UserPermissions(String permission){
        this.permission = permission;
    }

    public void setPermission(String permission) {
        this.permission = permission;
    }

    public String getPermission() {
        return permission;
    }
}
