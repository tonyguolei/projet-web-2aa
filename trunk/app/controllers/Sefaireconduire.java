package controllers;

import com.google.gson.*;
import flexjson.transformer.DateTransformer;
import models.*;
import play.mvc.Controller;
import java.util.List;
import flexjson.*;
import play.test.Fixtures;

/**
 * Created with IntelliJ IDEA.
 * User: lepeteil
 * Date: 10/05/14
 */

public class Sefaireconduire extends Controller {

    public static Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();

    public static void initBase(){
        Fixtures.deleteDatabase();

        Membre m1 = new Membre("ag","Lei","123456",23,"ag@gmail.com").save();
        Membre m2 = new Membre("ag1","Lei","123456",23,"ag@gmail.com").save();
        Membre m3 = new Membre("ag2","Lei","123456",23,"ag@gmail.com").save();

        Ville v1 = new Ville("gap",05000).save();
        Ville v2 = new Ville("gre",38000).save();

        Parcours p1 = new Parcours(m1,v1,v2,12,1).save();
        Parcours p2 = new Parcours(m1,v1,v2,13,2).save();
        Parcours p3 = new Parcours(m2,v1,v2,14,3).save();
    }


}
