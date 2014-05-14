package controllers;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import models.Membre;
import models.Parcours;
import models.Ville;
import play.mvc.Controller;

import java.util.List;
import flexjson.JSONSerializer;
import flexjson.transformer.DateTransformer;
/**
 * Created by tonyguolei on 5/9/14.
 */
public class Conduire extends Controller {

    /* CECI N EST PLUS UTILISE */
    /* ATTENTION !!!!!!!!!!!!!!!!!*/
    /* cf controllers/Appplication */
    public static void recupererMembreInfo(){
        String email = params.get("email");
        //System.out.println(email);
        Membre m = Membre.find("byEmail", email).first();
        // return reponse en format json
        JSONSerializer serializer = new JSONSerializer();
        //System.out.println("1" +m.lesParcoursChoisis);
        renderJSON(serializer.include("lesParcoursCrees", "lesParcoursChoisis").exclude("*.class").transform(new DateTransformer("dd/MM/yyyy"), "dateInscription").serialize(m));
    }

    /* CECI N EST PLUS UTILISE */
    /* ATTENTION !!!!!!!!!!!!!!!!!*/
    /* cf controllers/Appplication */
    public static void recupererMembresInscrits(){
        String id = params.get("id");
        Parcours p = Parcours.findById(Long.parseLong(id, 10));

        JSONSerializer serializer = new JSONSerializer();
        renderJSON(serializer.include("membresInscrits").exclude("*.class").transform(new DateTransformer("yyyy/MM/dd hh:mm:ss"), "dateParcours").serialize(p));
    }
}
