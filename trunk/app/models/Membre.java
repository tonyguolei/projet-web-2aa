package models;

import javax.persistence.*;
import com.google.gson.annotations.Expose;
import play.db.jpa.*;
import play.data.validation.*;
import java.util.*;
import java.text.*;

/**
 * Created with IntelliJ IDEA.
 * User: lepeteil
 */

@Entity
@Table(uniqueConstraints =
        {@UniqueConstraint(
                columnNames = {"email"
                })},
        name="Membre")
public class Membre extends Model {
    @Required
    public String nom;
    @Required
    public String prenom;
    @Required
    public int age;
    @Required
    public String sexe;
    @Required
    public String motDePasse;
    @Required
    public String email;
    public Date dateInscription;
    public boolean desinscrit;
    @OneToMany(mappedBy="createur", cascade=CascadeType.ALL)
    public Set<Parcours> lesParcoursCrees = new HashSet();
    @ManyToMany
    public Set<Parcours> lesParcoursChoisis =new HashSet();

    public Membre(String nom, String prenom,String motDePasse, int age, String email,String sexe) {
        this.nom = nom;
        this.prenom = prenom;
        this.motDePasse = motDePasse;
        this.age = age;
        this.email = email;
        this.sexe = sexe;
        //TODO Mettre la date en format FR ???
        this.dateInscription = new Date();
        this.desinscrit = false;
    }

    public static boolean connect(String username, String password) {
        System.out.println(Membre.find("email = ? and motDePasse = ?",username, password).first());
        if(Membre.find("email = ? and motDePasse = ?",username, password).first() != null){
            return true;
        }
        else {
            return false;
        }
    }

    /*
    Creation d'un parcours en tant que conducteur
     */
    public void ajouterParcoursCree(Parcours p) {
        this.lesParcoursCrees.add(p);
        this.save();
    }

    /*
    Selection d'un parcours comme passager
     */
    public void ajouterParcoursChoisi(Parcours p) {
        this.lesParcoursChoisis.add(p);
        this.save();
    }

    /*
    Desinscription du membre
     */
    public void supprimerCompte() {
        this.desinscrit = true;
        this.annulerTousLesParcours();
        this.save();
    }

    /*
    Annulation d'une reservation pour un parcours donne
     */
    public void seDesinscrireParcours(Parcours p){
        if(verifierParcoursChoisiExiste(p)){
            this.lesParcoursChoisis.remove(p);
            p.supprimerMembreInscrit(this);
            this.save();
            p.save();
        }
    }

    /*
    Suppression d'un parcours créé au préalable
     */
    public void supprimerParcours(Parcours p){

        if(verifierParcoursCreeExiste(p)){
            p.supprimerParcoursCree();
            p.save();
            this.save();
        }
    }

    /*
    Réactivation du parcours créé mais annulé précédemment
     */
    public void reactiverParcours(Parcours p){
        if(verifierParcoursCreeExiste(p)){
            p.reactiverParcoursCree();
            p.save();
            this.save();
        }
    }

    /*
    Verification du lien entre le membre et le parcours créé
     */
    private boolean verifierParcoursCreeExiste(Parcours p){
        Iterator<Parcours> itr = lesParcoursCrees.iterator();
        while(itr.hasNext()) {
            if(itr.next().equals(p))
                return true;
        }
        return false;
    }

    /*
    Verification du lien entre le membre et le parcours reservé
     */
    private boolean verifierParcoursChoisiExiste(Parcours p){
        Iterator<Parcours> itr = lesParcoursChoisis.iterator();
        while(itr.hasNext()) {
            if(itr.next().equals(p))
                return true;
        }
        return false;
    }

    private void annulerTousLesParcours(){
        //Gestion des parcours crees
        Iterator<Parcours> itr = lesParcoursCrees.iterator();
        while(itr.hasNext()) {
            Parcours p = itr.next();
            //TODO Prendre les parcours avec la dateParcours < dateAujourdhui
            p.supprime = true;
            p.save();
        }

        //Gestion des parcours choisis
        Iterator<Parcours> itr1 = lesParcoursChoisis.iterator();
        while(itr.hasNext()) {
            Parcours p = itr.next();
            //TODO Prendre les parcours avec la dateParcours < dateAujourdhui
            p.membresInscrits.remove(this);
            p.save();
        }
        this.save();
    }

    public void modifierAge(int age) {
        this.age = age;
    }

    public void modifierMotDePasse(String motDePasse) {
        this.motDePasse = motDePasse;
    }

    public void modifierPrenom(String prenom) {
        this.prenom = prenom;
    }

    public void modifierNom(String nom) {
        this.nom = nom;
    }

    public void modifierSexe(String sexe){
        this.sexe = sexe;
    }
}