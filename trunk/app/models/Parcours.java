package models;

import java.util.*;
import javax.persistence.*;

import com.google.gson.annotations.Expose;
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
    @ManyToOne
    public Membre createur;
    @ManyToMany(mappedBy ="lesParcoursChoisis")
    public List<Membre> membresInscrit = new ArrayList<Membre>();
//    @ManyToMany
//    public List<Membre> lesCovoitures;

    public Parcours(Membre createur, Ville dep, Ville arr,float prix,int nbPlaces) {
        this.createur = createur;
        this.dep = dep;
        this.arr = arr;
        this.prix = prix;
        this.nbPlaces = nbPlaces;
        this.dateParcours = new Date();
        this.supprime = false;
    }

    public void addmembreInscrit(Membre m) {
        this.membresInscrit.add(m);
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

//    public void addCovoiture(Membre covoiture) {
//        this.lesCovoitures.add(covoiture);
//        covoiture.addParcoursChoisi(this);
//    }
}
