package controllers;

import flexjson.JSONSerializer;
import flexjson.transformer.DateTransformer;
import models.*;
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

    private static void initBase() {
        Fixtures.deleteDatabase();

        Ville v1 = new Ville("Grenoble",38000).save();
        Ville v2 = new Ville("Lyon",69000).save();
        Ville v3 = new Ville("Marseille",13000).save();

        Membre m1 = new Membre("grange","alice","123456",23,"al@clu.fr").save();
        Membre m2 = new Membre("guo","lei","123456",22,"lg@clu.fr").save();
        Membre m3 = new Membre("laforest","yann","123456",20,"yl@clu.fr").save();

        Parcours p1 = new Parcours(m1,v1,v2,28,1).save();
        Parcours p2 = new Parcours(m2,v2,v3,28,2).save();
        Parcours p3 = new Parcours(m3,v3,v1,28,3).save();
        Parcours p4 = new Parcours(m1,v1,v2,28,1).save();

        /*p2.addCovoiture(m1);
        p3.addCovoiture(m1); */

        /*p1.addCovoiture(m2);
        p3.addCovoiture(m2);

        p1.addCovoiture(m3);
        p2.addCovoiture(m3);*/

    }

    public static void conduire() {
        render();
    }

    public static void sefaireconduire(String villeDépart,String villeArrivee,String Date) {
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

    public static void toutesLesVilles(){
        List<Ville> listv = Ville.findAll();
        JSONSerializer serializer = new JSONSerializer();
        renderJSON(serializer.exclude("*.class").serialize(listv));
    }


}