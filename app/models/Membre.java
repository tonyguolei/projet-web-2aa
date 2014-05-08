package models;

import javax.persistence.*;
import play.db.jpa.*;
import java.util.*;

/**
 * Created with IntelliJ IDEA.
 * User: lepeteil
 */

@Entity
public class Membre extends Model {

    public String nom;
    public String prenom;
    public String motDePasse;
    public int age;
    public String email;
    public Date dateInscription;
    public boolean desinscrit;
    @OneToMany
    public List<Parcours> lesParcours = new ArrayList<Parcours>();

    public Membre(String nom, String prenom,String motDePasse, int age, String email) {
        this.nom = nom;
        this.prenom = prenom;
        this.age = age;
        this.email = email;
        this.dateInscription = new Date();
        this.desinscrit = false;
    }

    public void setLesParcours(Parcours p) {
        this.lesParcours.add(p);
    }

    public void setDesinscrit(boolean desinscrit) {
        this.desinscrit = desinscrit;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public void setMotDePasse(String motDePasse) {
        this.motDePasse = motDePasse;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }
}