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

    @OneToOne
    public Membre createur;
    @ManyToOne
    public Ville dep;
    @ManyToOne
    public Ville arr;
    public float prix;
    public int nbPlaces;
    public Date dateParcours;
    public boolean supprime;
    @OneToMany
    public List<Membre> lesCovoitures = new ArrayList<Membre>();

    public Parcours(Membre createur, Ville dep, Ville arr,float prix,int nbPlaces) {
        this.createur = createur;
        this.dep = dep;
        this.arr = arr;
        this.prix = prix;
        this.nbPlaces = nbPlaces;
        this.dateParcours = new Date();
        this.supprime = false;
    }

    public void setPrix(float prix) {
        this.prix = prix;
    }

    public void setNbPlaces(int nbPlaces) {
        this.nbPlaces = nbPlaces;
    }

    public void setSupprime(boolean supprime) {
        this.supprime = supprime;
    }

    public void setLesCovoitures(Membre covoiture) {
        this.lesCovoitures.add(covoiture);
    }
}
