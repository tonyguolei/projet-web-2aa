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
        //redirection si le membre n'est plus en ligne
        if(!Security.isConnected()){
            Application.index();
        }
    }

    public static void deconnexion(){
        session.clear();
        Application.index();
    }

    public static void index() {
        Membre m = Membre.find("byEmail",session.get("username")).first();
        render(m);
    }

    public static void conduire() {
        Membre m = Membre.find("byEmail",session.get("username")).first();
        render(m);
    }

    public static void sefaireconduire() {
        Membre m = Membre.find("byEmail",session.get("username")).first();
        render(m);
    }

    public static void nous() {
        Membre m = Membre.find("byEmail",session.get("username")).first();
        render(m);
    }

    public static void contact() {
        Membre m = Membre.find("byEmail",session.get("username")).first();
        render(m);
    }

    public static void monprofil() {
        Membre m = Membre.find("byEmail",session.get("username")).first();
        render(m);
    }

    public static void mesparcours() {
        Membre m = Membre.find("byEmail",session.get("username")).first();
        render(m);
    }

    /* COTE MEMBRE ----------------------------------*/
    public static void recupererMembreInfo() {
        Membre m = Membre.find("byEmail",session.get("username")).first();
        JSONSerializer serializer = new JSONSerializer();
        renderJSON(serializer.include("lesParcoursCrees", "lesParcoursChoisis").exclude("*.class").transform(new DateTransformer("dd/MM/yyyy"), "dateInscription").serialize(m));
    }

    public static void recupererMembreInfoPerso() {
        Membre m = Membre.find("byEmail",session.get("username")).first();
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