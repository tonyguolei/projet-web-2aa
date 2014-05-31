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

    @Catch(IllegalStateException.class)
    public static void logIllegalState(Throwable throwable) {
        Logger.error("Illegal state %s…", throwable);
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
    //TODO verifier si utilisé
    public static void recupererMembreInfo() {
        Membre m = Membre.find("byEmail", session.get("username")).first();
        JSONSerializer serializer = new JSONSerializer();
        renderJSON(serializer.include("lesParcoursCrees", "lesParcoursChoisis").exclude("*.class").
                transform(new DateTransformer("dd/MM/yyyy"), "dateInscription").serialize(m));
    }
    //TODO Verifier si utilisé
    public static void recupererMembreInfoPerso() {
        Membre m = Membre.find("byEmail", session.get("username")).first();
        JSONSerializer serializer = new JSONSerializer();
        renderJSON(serializer.transform(new DateTransformer("dd/MM/yyyy"), "dateInscription").serialize(m));
    }

    //------------------GESTION DES PARCOURS RESERVES-----------------------//

    /**
     * Annule la réservation d'un parcours faite par le membre connecté
     */
    public static void annulerReservation() {
        String id = params.get("id");
        Membre m = Membre.find("byEmail", session.get("username")).first();
        Parcours p = Parcours.findById(Long.parseLong(id, 10));
        m.seDesinscrireParcours(p);
        JSONSerializer serializer = new JSONSerializer();
        renderJSON(serializer.include("membresInscrits").serialize(m.lesParcoursChoisis));
    }

    /**
     * Permet de valider la réservation d'un parcours faite par le membre connecté
     */
    public static void reserverParcours() {
        String id = params.get("id");
        Membre m = Membre.find("byEmail", session.get("username")).first();
        Parcours p = Parcours.findById(Long.parseLong(id, 10));
        p.ajouterMembreInscrit(m);
        JSONSerializer serializer = new JSONSerializer();
        renderJSON(serializer.transform(new DateTransformer("dd/MM/yyyy"), "dateInscription").serialize(m));
    }

    /**
     * Recupere tous les parcours réservés par le membre
     */
    public static void recupererParcoursChoisis() {
        Membre m = Membre.find("byEmail", session.get("username")).first();
        JSONSerializer serializer = new JSONSerializer();
        renderJSON(serializer.transform(new DateTransformer("dd/MM/yyyy"), "dateParcours")
                .include("membresInscrits").serialize(m.lesParcoursChoisis));
    }

    //-------------------GESTION DES PARCOURS CREES---------------------------//

    /**
     * Supprime temporairement le parcours créé par le membre connecté
     */
    public static void supprimerParcours() {
        String id = params.get("id");
        Membre m = Membre.find("byEmail", session.get("username")).first();
        Parcours p = Parcours.findById(Long.parseLong(id, 10));
        m.supprimerParcours(p);
        JSONSerializer serializer = new JSONSerializer();
        renderJSON(serializer.include("membresInscrits").serialize(m.lesParcoursCrees));
    }

    /**
     * Remet en ligne le parcours annulé précédemment par le membre connecté
     */
    public static void reactiverParcours() {
        String id = params.get("id");
        Membre m = Membre.find("byEmail", session.get("username")).first();
        Parcours p = Parcours.findById(Long.parseLong(id, 10));
        m.reactiverParcours(p);
        JSONSerializer serializer = new JSONSerializer();
        renderJSON(serializer.include("membresInscrits").serialize(m.lesParcoursCrees));
    }

    /**
     * Recupere les parcours créés par le membre connecté
     */
    public static void recupererParcoursCrees() {
        Membre m = Membre.find("byEmail", session.get("username")).first();
        JSONSerializer serializer = new JSONSerializer();
        renderJSON(serializer.transform(new DateTransformer("dd/MM/yyyy"), "dateParcours")
                .include("membresInscrits").serialize(m.lesParcoursCrees));
    }

    /* COTE PARCOURS ---------------------------------*/

    /**
     * Recupere les informations définies pour le parcours
     */
    public static void recupererParcoursInfo() {
        String id = params.get("id");
        Parcours p = Parcours.findById(Long.parseLong(id, 10));
        JSONSerializer serializer = new JSONSerializer();
        renderJSON(serializer.include("membresInscrits").exclude("*.class").
                transform(new DateTransformer("dd/MM/yyyy"), "dateParcours").serialize(p));
    }

    /**
     * Permet de valider la création d'un parcours par le membre connecté
     */
    public static void proposerParcours() {
        Membre createur = Membre.find("byEmail", session.get("username")).first();
        Ville depart = Ville.find("byNom", params.get("depart")).first();
        Ville arrivee = Ville.find("byNom", params.get("arrivee")).first();

        //if (depart == arrivee)
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

    /**
     * Modifie les informations personnelles du membre
     * @param email
     * @param prenom
     * @param nom
     * @param date
     * @param sexe
     * @param new_password
     */
    public static void modifierMonProfil(String email, String prenom, String nom, String date, String sexe, String new_password) {
        validation.required(email);
        validation.email(email);
        validation.required(prenom);
        validation.required(nom);
        validation.required(date);
        validation.past(Application.convertirStringDate(date));
        validation.required(sexe);
        validation.required(new_password);
        validation.minSize(new_password,6);
        if(validation.hasErrors()){
            throw new IllegalStateException("modifier mon profil erreur");
        }else{
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

    /**
     * Gestion de la partie admin
     */
    public static void admin() {
        redirect("http://localhost:9000/admin");
    }
}