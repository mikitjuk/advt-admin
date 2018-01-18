package com.mikitjuk.advt.controller;

public interface Api {

    String ROOT_PATH = "/api";
    String ID = "/{id}";
    String AUTHENTICATE = "/authenticate";

    interface Account {
        String ACCOUNT = "/account";
    }

    interface Users {
        String USER = "/user";
        String USERS = "/users";
        String USERS_BY_ID = USERS + ID;
        String USERS_BLOCK = USERS_BY_ID + "/blocked";
        String USER_CHANGE_PASSWORD = USERS_BY_ID + "/password_changing";
    }

    interface Apps {
        String APP = "/app";
        String APPS = "/apps";
        String APPS_BY_ID = APPS + ID;
    }
}
