package controllers;

import flexjson.JSONSerializer;
import flexjson.transformer.DateTransformer;
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
        if(Security.isConnected()){
            System.out.println("test") ;
            render();
        }else
            Application.index();
    }

    public static void conduire() {
        if(Security.isConnected())
            render();
        else
            Application.index();
    }

    public static void sefaireconduire() {
        if(Security.isConnected())
            render();
        else
            Application.index();
    }

    public static void nous() {
        if(Security.isConnected())
            render();
        else
            Application.index();
    }

    public static void contact() {
        if(Security.isConnected())
            render();
        else
            Application.index();
    }

    public static void monprofil() {
        if(Security.isConnected())
            render();
        else
            Application.index();
    }

    public static void mesparcours() {
        if(Security.isConnected())
            render();
        else
            Application.index();
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