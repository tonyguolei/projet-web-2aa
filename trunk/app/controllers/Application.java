package controllers;

import flexjson.JSONSerializer;
import flexjson.transformer.DateTransformer;
import models.Ville;
import models.Membre;
import models.Parcours;
import models.*;
import oauth.signpost.http.HttpRequest;
import org.apache.commons.lang.StringUtils;
import play.*;
import play.mvc.*;
import org.apache.commons.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import com.google.gson.*;
import play.test.Fixtures;

public class Application extends Controller {

    private static void verifieNonConnexion(){
        if (Security.isConnected()){
            session.clear();
        }
    }

    /*---------------------Initialisation de la base--------------------*/
    public static void initBase() {
        Fixtures.deleteDatabase();

        Ville v1 = new Ville("Annecy", "74000").save();
        Ville v2 = new Ville("Gap", "05000").save();
        Ville v3 = new Ville("Marseille", "13000").save();
        Ville v4 = new Ville("Grenoble", "38000").save();
        Ville v5 = new Ville("Lyon", "69000").save();

        Membre m1 = new Membre("lei", "guo", "123456", new Date(), "lei@gmail.com", "M").save();
        Membre m2 = new Membre("yann", "laforest", "123456", new Date(), "yann@gmail.com", "M").save();
        Membre m3 = new Membre("alice", "grange", "123456", new Date(), "alice@gmail.com", "F", true).save();

        Parcours p1 = new Parcours(m1, v1, v2, 8, 1,convertirStringDate("15/05/2014"),14,00).save();
        Parcours p2 = new Parcours(m2, v5, v3, 14, 2,convertirStringDate("10/05/2014"),13,50).save();
        Parcours p3 = new Parcours(m3, v4, v1, 15, 3,convertirStringDate("01/05/2014"),8,15).save();
        Parcours p4 = new Parcours(m1, v1, v4, 4, 1,22,18).save();
        Parcours p5 = new Parcours(m2, v2, v5, 17, 2,14,17).save();
        Parcours p6 = new Parcours(m3, v3, v1, 18, 3,17,30).save();
        Parcours p7 = new Parcours(m2, v5, v4, 13, 3,17,30).save();
        Parcours p8 = new Parcours(m1, v5, v4, 13, 3,17,30).save();
        Parcours p9 = new Parcours(m1, v5, v4, 13, 3,17,30).save();
        p1.ajouterMembreInscrit(m2);

        p2.ajouterMembreInscrit(m1);
        p2.ajouterMembreInscrit(m3);

        p3.ajouterMembreInscrit(m1);
        p3.ajouterMembreInscrit(m2);

        p4.ajouterMembreInscrit(m2);

        p5.ajouterMembreInscrit(m1);
        p5.ajouterMembreInscrit(m3);

        p6.ajouterMembreInscrit(m2);

        p7.ajouterMembreInscrit(m3);
        m2.supprimerParcours(p7);

    }

    /*----------------Affichage des pages coté public -----------------*/
    public static void index() {
        verifieNonConnexion();
        render();
    }
    /*---------------Methodes contenu des pages -----------------------*/

    /**
     * Renvoie tous les parcours enregistrés mais non supprimés
     */
    private static void tousLesParcoursActuels() {
        //TODO Regle pb dateParcours => date d'Aujourdhui
        //TODO hors parcours créé par le membre
        //List<Parcours> listp = Parcours.find("supprime = ? and dateParcours >= ?",false,new Date()).fetch();
        List<Parcours> listp = null;
        if(Security.isConnected()){
            //ne pas renvoyer les parcours deja réservés
            Membre m = Membre.find("byEmail", session.get("username")).first();
            listp = Parcours.find("supprime = ? and ? not in elements(membresInscrits) and createur != ?",false,m,m).fetch();
        }
        else{
            listp = Parcours.find("supprime = ? ",false).fetch();
        }
        JSONSerializer serializer = new JSONSerializer();
        renderJSON(serializer.exclude("*.class").exclude("createur").
                include("membresInscrits").transform(new DateTransformer("dd/MM/yyyy"),
                "dateParcours").serialize(listp));
    }

    /**
     * Renvoie les parcours enregistrés non supprimés satisfaisants les critères
     * @param depart
     * @param arrivee
     * @param date
     */
    private static void certainsParcoursActuels(String depart,String arrivee,String date){
        String textfind = "";

        if(depart.matches("[0-9]+")){
            //code postal saisi pour la ville de départ
            textfind = "depart.codePostal like ? ";
        }
        else{
            //nom saisi pour la ville de depart
            textfind = "depart.nom like ? ";
            depart = StringUtils.capitalize(depart);;
        }
        if(arrivee.matches("[0-9]+")){
            //code postal saisi pour la ville darrivee
            textfind = textfind + "and arrivee.codePostal like ? ";
        }
        else{
            //nom saisi pour la ville darrivee
            textfind = textfind +"and arrivee.nom like ? ";
            arrivee = StringUtils.capitalize(arrivee);
        }
        textfind = textfind+ " and supprime = ? ";
        textfind = textfind+ " and dateParcours = ? ";
        List<Parcours> listp = null;

        if(Security.isConnected()){
            //ne pas renvoyer les parcours déjà réservés ni créés par lui meme
            //TODO ne pas renvoyer les parcours créé par lui m
            Membre m = Membre.find("byEmail", session.get("username")).first();
            listp = Parcours.find(textfind+" and ? not in elements(membresInscrits) and createur != ?)",
                    "%"+ depart+"%","%"+ arrivee+"%",false,convertirStringDate(date),m,m).fetch();
        }
        else{
            listp = Parcours.find(textfind,"%"+ depart+"%","%"+ arrivee+"%",false,convertirStringDate(date)).fetch();
        }

        JSONSerializer serializer = new JSONSerializer();
        renderJSON(serializer.exclude("*.class").include("membresInscrits").exclude("createur").
                transform(new DateTransformer("dd/MM/yyyy"), "dateParcours").serialize(listp));
    }

    /**
     * Cherche les parcours avec ou sans critères de sélection
     */
    public static void chercherParcours() {
        String depart = params.get("depart");
        String arrivee = params.get("arrivee");
        String date = params.get("date");

        if(depart.equalsIgnoreCase("") && arrivee.equalsIgnoreCase("")){
            tousLesParcoursActuels();
        }
        else{
            certainsParcoursActuels(depart,arrivee,date);
        }
    }

    /**
     * Gère l'inscription d'un nouveau membre
     */
    public static void sinscrire() {
        //TODO gérer date de naissance => + 18ans

        String nom = params.get("nom");
        String prenom = params.get("prenom");
        String email = params.get("email");
        String datenaissance = params.get("date");
        String sexe = params.get("sexe");
        String motdepasse = params.get("motdepasse");
        Date daten = convertirStringDate(datenaissance);

        if(nom!="" & prenom!="" & email != "" & motdepasse!="" & sexe!="" & datenaissance!=""){
            Membre tmp = Membre.find("byEmail",email).first();
            if(tmp==null) {
                //Aucun membre existant avec cet email
                Membre m = new Membre(nom, prenom, motdepasse, daten, email, sexe).save();
                if(m!=null)
                    //Creation reussie
                    Application.seconnecter(email,motdepasse);
            }
        }
        Application.index();
    }

    /**
     * Gère la demande de connexion d'un utilisateur
     * @param emailform
     * @param motdepasseform
     */
    public static void seconnecter(String emailform,String motdepasseform) {
        if (Security.authenticate(emailform, motdepasseform)) {
            session.put("username",emailform);
            Utilisateur.index();
        } else {
            Application.index();
        }
    }

    /**
     * Convertir la chaine de caracteres en format Date
     * @param date
     * @return Date
     */
    public static Date convertirStringDate(String date){
        try {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yy");
            Date dater = simpleDateFormat.parse(date);
            return dater;
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return new Date();
    }

}