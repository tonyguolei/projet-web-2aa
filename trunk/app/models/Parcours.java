package models;

import java.util.*;
import javax.persistence.*;

import com.google.gson.annotations.Expose;
import org.joda.time.Hours;
import play.db.jpa.*;
import play.data.validation.*;
import java.text.DateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Created with IntelliJ IDEA.
 * User: lepeteil
 */

@Entity
@Table(name="Parcours")
public class Parcours extends Model {
    @ManyToOne
    public Ville depart;
    @ManyToOne
    public Ville arrivee;
    @Required
    public double prix;
    @Required
    public int nbPlacesInitiales;
    @Required
    public Date dateParcours;
    @Required
    public int heure;
    @Required
    public int min;
    public boolean supprime;
    @ManyToOne
    public Membre createur;
    @ManyToMany(mappedBy ="lesParcoursChoisis")
    public Set<Membre> membresInscrits = new HashSet();

    //ON DOIT TOUJOURS PARTIR DU PARCOURS POUR FAIRE LE LIEN AVEC UN MEMBRE (géré en auto aprés)
    public Parcours(Membre createur, Ville dep, Ville arr,float prix,int nbPlacesInitiales,
                    Date dateParcours, int heure,int min) {
        this.createur = createur;
        this.depart = dep;
        this.arrivee = arr;
        this.prix = prix;
        this.nbPlacesInitiales = nbPlacesInitiales;
        this.heure = heure;
        this.min = min;
        this.dateParcours = dateParcours;
        this.supprime = false;
        createur.ajouterParcoursCree(this);
        createur.save();
    }

    public Parcours(Membre createur, Ville dep, Ville arr,float prix,int nbPlacesInitiales,
                    int heure,int min) {
        this.createur = createur;
        this.depart = dep;
        this.arrivee = arr;
        this.prix = prix;
        this.nbPlacesInitiales = nbPlacesInitiales;
        this.heure = heure;
        this.min = min;
        this.dateParcours = new Date();
        this.supprime = false;
        createur.ajouterParcoursCree(this);
        createur.save();
    }

    public void ajouterMembreInscrit(Membre m) {
        if(verifieDispoPlaces()){
            this.membresInscrits.add(m);
            m.ajouterParcoursChoisi(this);
            this.save();
            m.save();
        }
    }

    private boolean verifieDispoPlaces(){
         return(this.membresInscrits.size() < this.nbPlacesInitiales);
    }

    public void modifierPrix(double prix) {
        this.prix = prix;
    }

    public void modifierNbPlaces(int nbPlaces) {
        this.nbPlacesInitiales = nbPlaces;
    }

    public void annulerParcours(){
        this.supprime = true;
    }

}
