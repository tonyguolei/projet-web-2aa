package models;

import java.util.*;
import javax.persistence.*;

import com.google.gson.annotations.Expose;
import play.data.validation.Required;
import play.db.jpa.*;

/**
 * Created with IntelliJ IDEA.
 * User: lepeteil
 */

@Entity
public class Ville extends Model {
    @Required
    public String nom;
    @Required
    public String codePostal;

    /**
     * Créé une ville
     * @param nom
     * @param codePostal
     */
    public Ville(String nom, String codePostal) {
        this.nom = nom;
        this.codePostal = codePostal;
    }

}
