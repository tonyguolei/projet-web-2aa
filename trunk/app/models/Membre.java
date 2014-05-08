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
    @ManyToMany()
    public List<Parcours> lesParcours;

    public Membre(String nom, String prenom,String motDePasse, int age, String email) {
        this.nom = nom;
        this.prenom = prenom;
        this.age = age;
        this.email = email;
        this.dateInscription = new Date();
        this.desinscrit = false;
        this.lesParcours = null;
    }

}