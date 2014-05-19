package controllers;

import flexjson.JSONSerializer;
import flexjson.transformer.DateTransformer;
import models.*;
import play.*;
import play.db.jpa.*;
import play.mvc.*;

import javax.servlet.http.*;

public class Utilisateur extends Controller  {

    @Before
    public static void isConnected(){
        System.out.println("session" + session.get("username"));
        if(!Security.isConnected())
            //redirection
            Application.index();
    }

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

    public static void monprofil() {
        render();
    }

    public static void mesparcours() {
        render();
    }

    /* COTE MEMBRE ----------------------------------*/
    public static void recupererMembreInfo() {
        String email = params.get("email");
        Membre m = Membre.find("byEmail", email).first();
        JSONSerializer serializer = new JSONSerializer();
        renderJSON(serializer.include("lesParcoursCrees", "lesParcoursChoisis").exclude("*.class").transform(new DateTransformer("dd/MM/yyyy"), "dateInscription").serialize(m));
    }

    public static void recupererMembreInfoPerso() {
        String email = params.get("email");
        Membre m = Membre.find("byEmail", email).first();
        JSONSerializer serializer = new JSONSerializer();
        renderJSON(serializer.transform(new DateTransformer("dd/MM/yyyy"), "dateInscription").serialize(m));
    }

    /* COTE PARCOURS ---------------------------------*/
    public static void recupererParcoursInfo() {
        String id = params.get("id");
        Parcours p = Parcours.findById(Long.parseLong(id, 10));
        JSONSerializer serializer = new JSONSerializer();
        renderJSON(serializer.include("membresInscrits").exclude("*.class").transform(new DateTransformer("yyyy/MM/dd hh:mm:ss"), "dateParcours").serialize(p));
    }

    public static void recupererMembresInscrits() {
        /*String id = params.get("id");
        Parcours p = Parcours.findById(Long.parseLong(id, 10));
        JSONSerializer serializer = new JSONSerializer();
        //renderJSON(serializer.include("membresInscrits").exclude("*.class").transform(new DateTransformer("yyyy/MM/dd hh:mm:ss"), "dateParcours").serialize(p));
        renderJSON(serializer.serialize(p.membresInscrits));*/

        String id = params.get("id");
        Parcours p = Parcours.findById(Long.parseLong(id, 10));

        JSONSerializer serializer = new JSONSerializer();
        renderJSON(serializer.include("membresInscrits").exclude("*.class").transform(new DateTransformer("yyyy/MM/dd hh:mm:ss"), "dateParcours").serialize(p));


    }

    public static void recupererMembreCreateur() {
        String id = params.get("id");
        Parcours p = Parcours.findById(Long.parseLong(id, 10));
        JSONSerializer serializer = new JSONSerializer();
        renderJSON(serializer.exclude("*.class").serialize(p.createur));
    }

}