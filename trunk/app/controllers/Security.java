package controllers;

import models.*;

public class Security extends Secure.Security {

    static boolean authenticate(String username, String password) {
        return Membre.connect(username, password);
    }

    static boolean check(String profile) {
        if("admin".equals(profile)) {
            return Membre.find("byEmail", connected()).<Membre>first().isAdmin;
        }
        return false;
    }
}