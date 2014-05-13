package controllers;

import flexjson.JSONSerializer;
import flexjson.transformer.DateTransformer;
import models.*;
import oauth.signpost.http.HttpRequest;
import play.*;
import play.mvc.*;
import java.util.*;
import models.*;
import com.google.gson.*;
import play.test.Fixtures;

public class Application extends Controller {

    public static void index() {
        //initBase();
        render();
    }

    public static void initBase() {
        Fixtures.deleteDatabase();

        Ville v1 = new Ville("Annecy",74000).save();
        Ville v2 = new Ville("Gap",05000).save();
        Ville v3 = new Ville("Marseille",13000).save();

        Membre m1 = new Membre("jack","pom","123456",23,"al@clu.fr").save();
        Membre m2 = new Membre("harry","ruse","123456",22,"lg@clu.fr").save();
        Membre m3 = new Membre("mel","soun","123456",20,"yl@clu.fr").save();

        Parcours p1 = new Parcours(m1,v1,v2,28,1).save();
        Parcours p2 = new Parcours(m2,v2,v3,28,2).save();
        Parcours p3 = new Parcours(m3,v3,v1,28,3).save();
        Parcours p4 = new Parcours(m1,v1,v2,28,1).save();

        p1.ajouterMembreInscrit(m2);
        p1.ajouterMembreInscrit(m3);
        p2.ajouterMembreInscrit(m1);

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

    public static void tousLesParcours(){
        List<Parcours> listp = Parcours.findAll();
        JSONSerializer serializer = new JSONSerializer();
        //renderJSON(serializer.include("membresInscrits").exclude("*.class").transform(new DateTransformer("yyyy/MM/dd hh:mm:ss"), "dateParcours").serialize(listp));
        renderJSON(serializer.exclude("*.class").transform(new DateTransformer("yyyy/MM/dd hh:mm:ss"), "dateParcours").serialize(listp));
    }

    public static void chercherParcours(String depart,String arrivee,String date){
        //TODO Rechercher approximativement ?

                //TODO Si chiffres = codepostal
                List<Ville> dep = Ville.find("byNomLike","%"+depart+"%").fetch();
                List<Ville> arr = Ville.find("byNomLike","%"+arrivee+"%").fetch();
                List<Parcours> listp = Parcours.find("byDepartAndArrivee", dep, arr).fetch();

                //TODO Rechercher avec la date
                if(date!=""){
                    //Parcours.find("byDateParcours",date).fetch();
                }

                JSONSerializer serializer = new JSONSerializer();
                //renderJSON(serializer.include("membresInscrits").exclude("*.class").transform(new DateTransformer("yyyy/MM/dd hh:mm:ss"), "dateParcours").serialize(listp));
                renderJSON(serializer.exclude("*.class").transform(new DateTransformer("yyyy/MM/dd hh:mm:ss"), "dateParcours").serialize(listp));

    }

    public static void toutesLesVilles(){
        List<Ville> listv = Ville.findAll();
        JSONSerializer serializer = new JSONSerializer();
        renderJSON(serializer.exclude("*.class").serialize(listv));
    }

    public static void sinscrire(String nom,String prenom,int age,String email,String mdp) {
        new Membre(nom,prenom,mdp,age,email).save();
        Application.index();
    }

}