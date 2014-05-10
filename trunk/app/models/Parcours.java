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
    public List<Membre> membresInscrits = new ArrayList<Membre>();
//    @ManyToMany
//    public List<Membre> lesCovoitures;

    //ON DOIT TOUJOURS PARTIR DU PARCOURS POUR FAIRE LE LIEN AVEC UN MEMBRE (géré en auto aprés)
    public Parcours(Membre createur, Ville dep, Ville arr,float prix,int nbPlaces) {
        this.createur = createur;
        this.dep = dep;
        this.arr = arr;
        this.prix = prix;
        this.nbPlaces = nbPlaces;
        this.dateParcours = new Date();
        this.supprime = false;
        createur.ajouterParcoursCree(this);
    }

    public void ajouterMembreInscrit(Membre m) {
        //TODO Verifier le nombre de places restantes
        this.membresInscrits.add(m);
        m.ajouterParcoursChoisi(this);
    }

    public void modifierPrix(float prix) {
        this.prix = prix;
    }

    public void modifierNbPlaces(int nbPlaces) {
        this.nbPlaces = nbPlaces;
    }

    public void modifierSupprime(boolean supprime) {
        this.supprime = supprime;
    }

    @Override
    public String toString() {
        return "Parcours{" +
                "dep=" + dep +
                ", arr=" + arr +
                ", prix=" + prix +
                ", nbPlaces=" + nbPlaces +
                ", dateParcours=" + dateParcours +
                ", supprime=" + supprime +
                ", createur=" + createur +
                ", membresInscrits=" + membresInscrits +
                '}';
    }
}
