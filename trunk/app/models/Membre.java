package models;

import javax.persistence.*;

import com.google.gson.annotations.Expose;
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
    @OneToMany(mappedBy="createur", cascade=CascadeType.ALL)
    public List<Parcours> lesParcoursCrees = new ArrayList<Parcours>();
    @ManyToMany
    public List<Parcours> lesParcoursChoisis = new ArrayList<Parcours>();

    public Membre(String nom, String prenom,String motDePasse, int age, String email) {
        this.nom = nom;
        this.prenom = prenom;
        this.motDePasse = motDePasse;
        this.age = age;
        this.email = email;
        this.dateInscription = new Date();
        this.desinscrit = false;
        this.lesParcoursCrees = new ArrayList<Parcours>();
    }

    public void addParcoursCree(Parcours p) {
        this.lesParcoursCrees.add(p);
    }

    public void addParcoursChoisi(Parcours p) {
        this.lesParcoursChoisis.add(p);
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