package models;

import java.util.*;
import javax.persistence.*;
import play.db.jpa.*;

/**
 * Created with IntelliJ IDEA.
 * User: lepeteil
 */

@Entity
public class Ville extends Model {

    public String nom;
    public int codePostal;

    public Ville(String nom, int codePostal) {
        this.nom = nom;
        this.codePostal = codePostal;
    }
}
