package controllers;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import models.Membre;
import models.Parcours;
import models.Ville;
import play.mvc.Controller;

import java.util.List;

/**
 * Created by tonyguolei on 5/9/14.
 */
public class Conduire extends Controller {
    // Pour resoudre probleme de JSON
    public static Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();

    public static void testAjax(){
        Membre m1 = new Membre("Guo","Lei","123456",23,"tonyguolei@gmail.com").save();
        Ville v1 = new Ville("Grenoble",38000).save();
        Ville v2 = new Ville("Lyon",69000).save();
        Parcours p1 = new Parcours(m1,v1,v2,28,1).save();
        Parcours p2 = new Parcours(m1,v1,v2,28,1).save();
        m1.addParcoursCree(p1);
        m1.addParcoursCree(p2);
        List<Parcours> p = m1.lesParcoursCrees;
        System.out.println(p);
        renderJSON(gson.toJson(p));
    }
}
