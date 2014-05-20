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
import java.util.*;
import com.google.gson.*;
import play.test.Fixtures;

public class Application extends Controller {

    /*---------------------Initialisation de la base--------------------*/
    public static void initBase() {
        Fixtures.deleteDatabase();

        Ville v1 = new Ville("Annecy", "74000").save();
        Ville v2 = new Ville("Gap", "05000").save();
        Ville v3 = new Ville("Marseille", "13000").save();
        Ville v4 = new Ville("Grenoble", "38000").save();
        Ville v5 = new Ville("Lyon", "69000").save();

        Membre m1 = new Membre("jack", "pom", "123456", 23, "al@clu.fr", "M").save();
        Membre m2 = new Membre("harry", "ruse", "123456", 22, "lg@clu.fr", "M").save();
        Membre m3 = new Membre("alice", "grange", "123456", 20, "alice@gmail.com", "F").save();

        Parcours p1 = new Parcours(m1, v1, v2, 120, 1,14,00).save();
        Parcours p2 = new Parcours(m2, v5, v3, 14, 2,13,50).save();
        Parcours p3 = new Parcours(m3, v4, v1, 157, 3,8,15).save();
        Parcours p4 = new Parcours(m1, v1, v4, 16, 1,22,18).save();
        Parcours p5 = new Parcours(m2, v2, v5, 17, 2,14,17).save();
        Parcours p6 = new Parcours(m3, v3, v1, 118, 3,17,30).save();
        Parcours p7 = new Parcours(m2, v5, v4, 13, 3,17,30).save();

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
        m2.annulerParcours(p7);

    }

    /*----------------Affichage des pages coté public -----------------*/
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
    /*---------------Methodes contenu des pages -----------------------*/

    private static void tousLesParcoursActuels() {
        //TODO Renvoyer seulement les parcours non supprimes à partir de la date actuelle
        List<Parcours> listp = Parcours.find("supprime = ?",false).fetch();
        JSONSerializer serializer = new JSONSerializer();
        renderJSON(serializer.exclude("*.class").exclude("createur").include("membresInscrits").transform(new DateTransformer("yyyy/MM/dd hh:mm:ss"),
                "dateParcours").serialize(listp));
    }

    public static void chercherParcours() {
        //TODO Gerer la recherche avec la date
        String depart = params.get("depart");
        String arrivee = params.get("arrivee");
        String date = params.get("date");
        String textfind = "";

        if(depart=="" && arrivee=="" && date=="")
            tousLesParcoursActuels();
        else{
            if(depart.matches("[0-9]+")){
                //code postal saisi pour la ville de départ
                textfind = "depart.codePostal like ? ";
            }
            else{
                //nom saisi pour la ville de depart
                textfind = "depart.nom like ? ";
                depart = StringUtils.capitalize(depart);
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

            List<Parcours> listp = Parcours.find(textfind,"%"+ depart+"%","%"+ arrivee+"%").fetch();
            JSONSerializer serializer = new JSONSerializer();
            renderJSON(serializer.exclude("*.class").include("membresInscrits").exclude("createur").transform(new DateTransformer("yyyy/MM/dd hh:mm:ss"), "dateParcours").serialize(listp));
        }

    }

    public static void sinscrire(String nom, String prenom, int age, String email, String motdepasse, String sexe) {
        if(nom!="" & prenom!="" & age>17 & age<99 & email != "" & motdepasse!="" & sexe!=""){
            Membre tmp = Membre.find("byEmail",email).first();
            if(tmp==null) {
                //Aucun membre existant avec cet email
                Membre m = new Membre(nom, prenom, motdepasse, age, email, sexe).save();
                if(m!=null)
                    //Creation reussie
                    Application.seconnecter(email,motdepasse);
            }
        }
        Application.index();
    }

    public static void seconnecter(String emailform,String motdepasseform) {
        if (Security.authenticate(emailform, motdepasseform)) {
            session.put("username",emailform);
            Utilisateur.index();
        } else {
            Application.index();
        }
    }

}