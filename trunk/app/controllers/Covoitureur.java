package controllers;

import models.*;
import play.*;
import play.db.jpa.*;
import play.mvc.*;

import javax.servlet.http.*;

public class Covoitureur extends Controller {

    public static void index() {
        render();
    }

    public static void sinscrire(String nom,String prenom,int age,String email,String mdp) {
        new Membre(nom,prenom,mdp,age,email).save();
        Application.index();
    }

    public static void seconnecter(String email,String mdp) {
        if(session.get("user") == null){


        }
        Application.index();

    }

    public static void consulterMembres(){
        //List<Membre> m = Membre.findAll();
        //render(p, m);
    }

}