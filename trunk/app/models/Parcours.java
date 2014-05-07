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
public class Parcours extends Model {

    public Membre createur;
    public Ville dep;
    public Ville arr;
    public Date dateParcours;
    public float prix;
    public boolean supprime;

    public Parcours(Membre createur, Ville dep, Ville arr,float prix) {
        this.createur = createur;
        this.dep = dep;
        this.arr = arr;
        this.prix = prix;
        this.dateParcours = new Date();
        this.supprime = false;
    }
}
