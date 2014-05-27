package controllers;

import flexjson.JSONSerializer;
import flexjson.transformer.DateTransformer;
import models.*;
import play.*;
import play.db.jpa.*;
import play.mvc.*;

import javax.servlet.http.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Utilisateur extends Controller {

    @Before
    public static void isConnected() {
        //redirection si le membre n'est plus en ligne
        if (!Security.isConnected()) {
            Application.index();
        }
    }

    public static void deconnexion() {
        session.clear();
        Application.index();
    }

    public static void index() {
        Membre m = Membre.find("byEmail", session.get("username")).first();
        render(m);
    }

    public static void conduire() {
        Membre m = Membre.find("byEmail", session.get("username")).first();
        render(m);
    }

    public static void sefaireconduire() {
        Membre m = Membre.find("byEmail", session.get("username")).first();
        render(m);
    }

    public static void nous() {
        Membre m = Membre.find("byEmail", session.get("username")).first();
        render(m);
    }

    public static void contact() {
        Membre m = Membre.find("byEmail", session.get("username")).first();
        render(m);
    }

    public static void monprofil() {
        Membre m = Membre.find("byEmail", session.get("username")).first();
        render(m);
    }

    public static void mesparcours() {
        Membre m = Membre.find("byEmail", session.get("username")).first();
        render(m);
    }

    /* COTE MEMBRE ----------------------------------*/
    public static void recupererMembreInfo() {
        Membre m = Membre.find("byEmail", session.get("username")).first();
        JSONSerializer serializer = new JSONSerializer();
        renderJSON(serializer.include("lesParcoursCrees", "lesParcoursChoisis").exclude("*.class").
                transform(new DateTransformer("dd/MM/yyyy"), "dateInscription").serialize(m));
    }

    public static void recupererMembreInfoPerso() {
        Membre m = Membre.find("byEmail", session.get("username")).first();
        JSONSerializer serializer = new JSONSerializer();
        renderJSON(serializer.transform(new DateTransformer("dd/MM/yyyy"), "dateInscription").serialize(m));
    }

    //------------------GESTION DES PARCOURS RESERVES-----------------------//
    public static void annulerReservation() {
        String id = params.get("id");
        Membre m = Membre.find("byEmail", session.get("username")).first();
        Parcours p = Parcours.findById(Long.parseLong(id, 10));
        m.seDesinscrireParcours(p);
        JSONSerializer serializer = new JSONSerializer();
        renderJSON(serializer.include("membresInscrits").serialize(m.lesParcoursChoisis));
    }

    public static void reserverParcours() {
        String id = params.get("id");
        Membre m = Membre.find("byEmail", session.get("username")).first();
        Parcours p = Parcours.findById(Long.parseLong(id, 10));
        p.ajouterMembreInscrit(m);
        JSONSerializer serializer = new JSONSerializer();
        renderJSON(serializer.transform(new DateTransformer("dd/MM/yyyy"), "dateInscription").serialize(m));
    }

    public static void recupererParcoursChoisis() {
        Membre m = Membre.find("byEmail", session.get("username")).first();
        JSONSerializer serializer = new JSONSerializer();
        renderJSON(serializer.include("membresInscrits").serialize(m.lesParcoursChoisis));
    }

    //-------------------GESTION DES PARCOURS CREES---------------------------//
    public static void supprimerParcours() {
        String id = params.get("id");
        Membre m = Membre.find("byEmail", session.get("username")).first();
        Parcours p = Parcours.findById(Long.parseLong(id, 10));
        m.supprimerParcours(p);
        JSONSerializer serializer = new JSONSerializer();
        renderJSON(serializer.include("membresInscrits").serialize(m.lesParcoursCrees));
    }

    public static void reactiverParcours() {
        String id = params.get("id");
        Membre m = Membre.find("byEmail", session.get("username")).first();
        Parcours p = Parcours.findById(Long.parseLong(id, 10));
        m.reactiverParcours(p);
        JSONSerializer serializer = new JSONSerializer();
        renderJSON(serializer.include("membresInscrits").serialize(m.lesParcoursCrees));
    }

    public static void recupererParcoursCrees() {
        Membre m = Membre.find("byEmail", session.get("username")).first();
        JSONSerializer serializer = new JSONSerializer();
        renderJSON(serializer.include("membresInscrits").serialize(m.lesParcoursCrees));
    }

    /* COTE PARCOURS ---------------------------------*/
    public static void recupererParcoursInfo() {
        String id = params.get("id");
        Parcours p = Parcours.findById(Long.parseLong(id, 10));
        JSONSerializer serializer = new JSONSerializer();
        renderJSON(serializer.include("membresInscrits").exclude("*.class").
                transform(new DateTransformer("dd//MM/yyyy"), "dateParcours").serialize(p));
    }

    public static void proposerParcours() {
        Membre createur = Membre.find("byEmail", session.get("username")).first();
        Ville depart = Ville.find("byNom", params.get("depart")).first();
        Ville arrivee = Ville.find("byNom", params.get("arrivee")).first();

        if (depart == arrivee)
            //TODO Impossible => refuser la création
            if (depart == null) {
                depart = new Ville(params.get("depart"), params.get("departcp")).save();
            }
        if (arrivee == null) {
            arrivee = new Ville(params.get("arrivee"), params.get("arriveecp")).save();
        }
        Date date = Application.convertirStringDate(params.get("date"));
        Parcours p = new Parcours(createur, depart, arrivee, Float.parseFloat(params.get("prix")),
                Integer.parseInt(params.get("nbplaces")), date, Integer.parseInt(params.get("heure")),
                Integer.parseInt(params.get("min"))).save();
        JSONSerializer serializer = new JSONSerializer();
        renderJSON(serializer.exclude("*.class").
                transform(new DateTransformer("dd/MM/yyyy"), "dateInscription").serialize(p));
    }

    //TODO => Inutilisé
    public static void recupererMembresInscrits() {
        String id = params.get("id");
        Parcours p = Parcours.findById(Long.parseLong(id, 10));

        JSONSerializer serializer = new JSONSerializer();
        renderJSON(serializer.include("membresInscrits").exclude("*.class").
                transform(new DateTransformer("dd/MM/yyyy"), "dateParcours").serialize(p));
    }

    //TODO => inutilisé
    public static void recupererMembreCreateur() {
        String id = params.get("id");
        Parcours p = Parcours.findById(Long.parseLong(id, 10));
        JSONSerializer serializer = new JSONSerializer();
        renderJSON(serializer.exclude("*.class").serialize(p.createur));
    }


    public static void modifierMonProfil() {
        Membre m = Membre.find("byEmail", session.get("username")).first();
        m.prenom = params.get("prenom");
        m.nom = params.get("nom");
        m.dateNaissance = Application.convertirStringDate(params.get("date"));
        m.sexe = params.get("sexe");
        m.motDePasse = params.get("new_password");
        m.save();
        JSONSerializer serializer = new JSONSerializer();
        renderJSON(serializer.serialize(m));
    }
}