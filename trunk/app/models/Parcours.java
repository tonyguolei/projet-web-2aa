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

    /**
     * Crée un parcours
     * @param  createur,ville de départ,ville d'arrivée, prix, nombre de places proposées, date, heure, minute
     */
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

    /**
     * Crée un parcours à la date du jour
     * @param  createur,ville de départ,ville d'arrivée, prix, nombre de places proposées, heure, minute
    */
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

    /**
     * Inscrit un membre sur le parcours
     * @param membre
    */
    public void ajouterMembreInscrit(Membre membre) {
        if(verifieDispoPlaces() && this.createur != membre){
            this.membresInscrits.add(membre);
            membre.ajouterParcoursChoisi(this);
            this.save();
            membre.save();
        }
    }

    /**
     * Desinscrit un membre sur le parcours
     * @param membre
     */
    public void supprimerMembreInscrit(Membre membre){
        if(this.membresInscrits.contains(membre)){
            this.membresInscrits.remove(membre);
        }
    }

    /**
     * Vérifie la disponibilité sur le parcours par rapport au nombre de places initiales
     * @return vrai s'il reste une disponibilité, faux sinon
     */
    private boolean verifieDispoPlaces(){
         return(this.membresInscrits.size() < this.nbPlacesInitiales && !this.supprime);
    }

    /**
     * Modifie le prix fixé par le créateur
     * @param prix
     */
    public void modifierPrix(double prix) {
        this.prix = prix;
        this.save();
    }

    /**
     * Modifie le nombre de places fixé par le créateur
     * @param nbPlaces
     */
    public void modifierNbPlaces(int nbPlaces) {
        this.nbPlacesInitiales = nbPlaces;
        this.save();
    }

    /**
     * Supprime temporairement le parcours créé
     */
    public void supprimerParcoursCree(){
        this.supprime = true;
        this.save();
    }

    /**
     * Réactiver le parcours créé précédemment
     */
    public void reactiverParcoursCree(){
        this.supprime = false;
        this.save();
    }

}
