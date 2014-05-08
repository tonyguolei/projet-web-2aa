package controllers;

import models.*;
import play.*;
import play.mvc.*;
import java.util.*;
import models.*;

public class Application extends Controller {

    public static void index() {
        initBase();
        //render();
    }

    private static void initBase() {

        /*Ville.deleteAll();
        Membre.deleteAll();
        Parcours.deleteAll(); */

        Ville v1 = new Ville("Grenoble",38000).save();
        Ville v2 = new Ville("Lyon",69000).save();
        Ville v3 = new Ville("Marseille",13000).save();

        Membre m1 = new Membre("grange","alice","123456",23,"al@clu.fr").save();
        Membre m2 = new Membre("guo","lei","123456",22,"lg@clu.fr").save();
        Membre m3 = new Membre("laforest","yann","123456",20,"yl@clu.fr").save();

        Parcours p1 = new Parcours(m1,v1,v2,28,1).save();
        Parcours p2 = new Parcours(m2,v2,v3,28,2).save();
        Parcours p3 = new Parcours(m3,v3,v1,28,3).save();

        m1.setLesParcours(p2);
        p2.setLesCovoitures(m1);
        /*m1.setLesParcours(p3);
        p3.setLesCovoitures(m1);

        m2.setLesParcours(p3);
        p3.setLesCovoitures(m2);
        m2.setLesParcours(p1);
        p1.setLesCovoitures(m2);

        m3.setLesParcours(p1);
        p1.setLesCovoitures(m3);
        m3.setLesParcours(p2);
        p2.setLesCovoitures(m3);
        */
        m1.save();
        m2.save();
        m3.save();
        p1.save();
        p2.save();
        p3.save();

        render(m1);
    }

    public static void conduire() {
        render();
    }

    public static void sefaireconduire(String villeDépart,String villeArrivee,String Date) {
        List<Parcours> p = Parcours.findAll();
        render(p);
    }

    public static void nous() {
        render();
    }

    public static void contact() {
        render();
    }

}