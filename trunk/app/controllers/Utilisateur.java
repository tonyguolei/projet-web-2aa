package controllers;

import models.*;
import play.*;
import play.db.jpa.*;
import play.mvc.*;

import javax.servlet.http.*;

public class Utilisateur extends Controller {

    /*
    @Before
    public static boolean verifierSession(){
        if (session.get("email")!=""){
            return true;
        }
    }*/

    public static void index() {
        render();
    }

    public static void conduire() {
        render();
    }

    public static void sefaireconduire() {
        render();
    }

    public static void nous() {
        render();
    }

    public static void contact() {
        render();
    }

}