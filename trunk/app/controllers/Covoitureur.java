package controllers;

import models.*;
import play.*;
import play.db.jpa.*;
import play.mvc.*;

public class Covoitureur extends Controller {

    public static void index() {
        render();
    }

    public static void sinscrire(String nom,String prenom,int age,String email,String mdp) {
        //TODO création du membre
        new Membre(nom,prenom,mdp,age,email).save();
        Application.index();
    }

}