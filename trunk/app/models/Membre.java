package models;

import java.util.*;
import javax.persistence.*;
import play.db.jpa.*;
import java.text.DateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Created with IntelliJ IDEA.
 * User: lepeteil
 */

@Entity
public class Membre extends Model {

    public String nom;
    public String prenom;
    public int age;
    public String email;
    public Date dateInscription;
    public boolean desinscrit;
    public List<Parcours> lesParcours;

    public Membre(String nom, String prenom, int age, String email) {
        this.nom = nom;
        this.prenom = prenom;
        this.age = age;
        this.email = email;
        this.dateInscription = new Date();
        this.desinscrit = false;
    }

}