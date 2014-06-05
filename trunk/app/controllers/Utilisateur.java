package controllers;

import flexjson.JSONSerializer;
import flexjson.transformer.DateTransformer;
import models.*;
import org.apache.commons.mail.DefaultAuthenticator;
import org.apache.commons.mail.Email;
import org.apache.commons.mail.SimpleEmail;
import org.joda.time.LocalDate;
import org.joda.time.Period;
import org.joda.time.PeriodType;
import play.*;
import play.mvc.*;
import play.libs.Mail;

import java.util.Date;
import java.util.List;
import org.apache.commons.mail.EmailException;

public class Utilisateur extends Controller {

    @Before
    public static void isConnected() {
        //redirection si le membre n'est plus en ligne
        if (!Security.isConnected()) {
            Application.index(null);
        }
    }

    @Catch(IllegalStateException.class)
    public static void logIllegalState(Throwable throwable) {
        Logger.error("Illegal state %s…", throwable);
    }

    public static void deconnexion() {
        session.clear();
        Application.index(null);
    }

    public static void index() {
        Membre m = Membre.find("byEmail", session.get("username")).first();
        renderArgs.put("prenom", m.prenom);
        renderArgs.put("nom", m.nom);
        renderArgs.put("dateInscription",m.dateInscription);
        renderArgs.put("nbparcourschoisis",m.lesParcoursChoisis.size());
        renderArgs.put("nbparcourscrees",m.lesParcoursCrees.size());
        renderArgs.put("nbmembres",Membre.findAll().size());
        render();
    }

    public static void conduire() {
        Membre m = Membre.find("byEmail", session.get("username")).first();
        renderArgs.put("prenom", m.prenom);
        renderArgs.put("nom", m.nom);
        render();
    }

    public static void sefaireconduire() {
        Membre m = Membre.find("byEmail", session.get("username")).first();
        renderArgs.put("prenom", m.prenom);
        renderArgs.put("nom", m.nom);
        render();
    }

    public static void nous() {
        Membre m = Membre.find("byEmail", session.get("username")).first();
        renderArgs.put("prenom", m.prenom);
        renderArgs.put("nom", m.nom);
        render();
    }

    public static void contact() {
        Membre m = Membre.find("byEmail", session.get("username")).first();
        renderArgs.put("prenom", m.prenom);
        renderArgs.put("nom", m.nom);
        render();
    }

    public static void monprofil() {
        Membre m = Membre.find("byEmail", session.get("username")).first();
        renderArgs.put("prenom", m.prenom);
        renderArgs.put("nom", m.nom);
        renderArgs.put("email", m.email);
        renderArgs.put("dateNaissance", m.dateNaissance);
        renderArgs.put("motDePasse", m.motDePasse);
        renderArgs.put("sexe", m.sexe);
        render();
    }

    public static void mesparcours() {
        Membre m = Membre.find("byEmail", session.get("username")).first();
        renderArgs.put("prenom", m.prenom);
        renderArgs.put("nom", m.nom);
        renderArgs.put("lesParcoursChoisis", m.lesParcoursChoisis);
        renderArgs.put("lesParcoursCrees", m.lesParcoursCrees);
        render();
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

        //recuperer ses futurs parcours réservés
        List<Parcours> listp = Parcours.find(
                "? in elements(membresInscrits) " +
                "and dateParcours >= current_date() " +
                "and createur != ? ",m,m).fetch();
        JSONSerializer serializer = new JSONSerializer();
        renderJSON(serializer.include("membresInscrits").serialize(listp));
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

        //recuperer ses futurs parcours créés
        List<Parcours> listp = Parcours.find(
                "? not in elements(membresInscrits) " +
                        "and dateParcours >= current_date() " +
                        "and createur = ? ",m,m).fetch();
        JSONSerializer serializer = new JSONSerializer();
        renderJSON(serializer.include("membresInscrits").serialize(listp));
    }

    /**
     * Remet en ligne le parcours annulé précédemment par le membre connecté
     */
    public static void reactiverParcours() {
        String id = params.get("id");
        Membre m = Membre.find("byEmail", session.get("username")).first();
        Parcours p = Parcours.findById(Long.parseLong(id, 10));
        m.reactiverParcours(p);

        //recuperer ses futurs parcours créés
        List<Parcours> listp = Parcours.find(
                "? not in elements(membresInscrits) " +
                        "and dateParcours >= current_date() " +
                        "and createur = ? ",m,m).fetch();
        JSONSerializer serializer = new JSONSerializer();
        renderJSON(serializer.include("membresInscrits").serialize(listp));
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
     * @param depart
     * @param arrivee
     * @param date
     * @param heure
     * @param heure
     * @param min
     * @param prix
     * @param nbplaces
     */
    public static void proposerParcours(String depart, String departcp, String arrivee, String arriveecp,
                                        String date, String heure, String min, String prix, String nbplaces) {
        validation.required(depart);
        validation.required(arrivee);
        validation.required(date);
        validation.future(Application.convertirStringDate(date));
        validation.required(heure);
        validation.range(heure, 0, 23);
        validation.required(min);
        validation.range(heure, 0, 59);
        validation.required(prix);
        validation.min(prix,0);
        validation.required(nbplaces);
        validation.min(nbplaces, 1);
        if(validation.hasErrors() || depart.equals(arrivee)){
            throw new IllegalStateException("proposer parcours erreur");
        }
        else{
            Membre createur = Membre.find("byEmail", session.get("username")).first();
            Ville depart1 = Ville.find("byNom", depart).first();
            Ville arrivee1 = Ville.find("byNom", arrivee).first();

            if (depart1 == null) {
                depart1 = new Ville(depart, departcp).save();
            }
            if (arrivee1 == null) {
                arrivee1 = new Ville(arrivee, arriveecp).save();
            }
            Date date1 = Application.convertirStringDate(date);
            Parcours p = new Parcours(createur, depart1, arrivee1, Float.parseFloat(prix),
                    Integer.parseInt(nbplaces), date1, Integer.parseInt(heure),
                    Integer.parseInt(min)).save();
            JSONSerializer serializer = new JSONSerializer();
            renderJSON(serializer.exclude("*.class").
                    transform(new DateTransformer("dd/MM/yyyy"), "dateInscription").serialize(p));
        }
    }

    /**
     * Calcule l'age du membre
     * @param date
     */
    public static Integer getAge(Date date) {
        Integer returnAge = null;
        try {
            LocalDate personBirthdate = new LocalDate(date);
            LocalDate sysDateDate = new LocalDate(new Date());
            Period period = new Period(personBirthdate, sysDateDate, PeriodType
                    .yearMonthDay());
            returnAge = new Integer(period.getYears());
        } catch (Exception e) {
            System.out.println("Error while calculating Age … " + e);
        }
        return returnAge;
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
    public static void modifierMonProfil(String email, String prenom, String nom, String date,
                                         String sexe, String new_password) {
        validation.required(email);
        validation.email(email);
        validation.required(prenom);
        validation.required(nom);
        validation.required(date);
        validation.min(getAge(Application.convertirStringDate(date)), 18);
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