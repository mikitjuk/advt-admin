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
    }

    interface Apps {
        String APP = "/app";
        String APPS = "/apps";
        String APPS_BY_ID = APPS + ID;
    }
}
