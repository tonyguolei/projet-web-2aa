package controllers;

import play.*;
import play.mvc.*;

public class Membre extends Controller {

    public static void index() {
        render();
    }

    public static void sinscrire(String nom,String prenom,int age,String email) {
        //TODO création du membre
        Application.index();
    }

}