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
    @ManyToOne
    public Ville dep;
    @ManyToOne
    public Ville arr;
    public float prix;
    public int nbPlaces;
    public Date dateParcours;
    public boolean supprime;

    public Parcours(Membre createur, Ville dep, Ville arr,float prix,int nbPlaces) {
        this.dep = dep;
        this.arr = arr;
        this.prix = prix;
        this.nbPlaces = nbPlaces;
        this.dateParcours = new Date();
        this.supprime = false;
    }
}
