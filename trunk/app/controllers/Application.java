package controllers;

import models.*;
import play.*;
import play.mvc.*;
import java.util.*;
import models.*;

public class Application extends Controller {

    public static void index() {
        initBase();
        render();
    }

    private static void initBase() {
        Ville v1 = new Ville("Grenoble",38000).save();
        Ville v2 = new Ville("Lyon",69000).save();
        Ville v3 = new Ville("Marseille",13000).save();

        Membre m1 = new Membre("grange","alice","123456",23,"al@clu.fr").save();
        Membre m2 = new Membre("guo","lei","123456",22,"lg@clu.fr").save();
        Membre m3 = new Membre("laforest","yann","123456",20,"yl@clu.fr").save();

        Parcours p1 = new Parcours(m1,v1,v2,28,1).save();
        Parcours p2 = new Parcours(m1,v2,v3,28,2).save();
        Parcours p3 = new Parcours(m1,v3,v1,28,3).save();

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