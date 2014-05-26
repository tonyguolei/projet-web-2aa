/**
 * Created by laforesy on 19/05/14.
 */
import org.junit.*;
import java.util.*;
import play.test.*;
import models.*;

public class ParcoursTest extends UnitTest {

    @Before
    public void parcours_creation() {
        Membre m1 = Membre.find("byEmail", "al@clu.fr").first();
        Ville v1 = Ville.find("byCodePostal", "69680").first();
        Ville v2 = Ville.find("byCodePostal", "69006").first();
        Date d1 = new Date();
        Parcours p1 = new Parcours(m1, v1, v2, 10, 4, d1, 10, 30).save();
    }

    @Test
    public void parcours_assert_creation() {
        Date d1 = new Date();
        Membre m1 = Membre.find("byEmail", "al@clu.fr").first();
        Ville v1 = Ville.find("byCodePostal", "69680").first();
        Ville v2 = Ville.find("byCodePostal", "69006").first();
        Parcours p1 = Parcours.find("byHeure", 10).first();

        assertEquals(p1.createur, m1);
        assertEquals(p1.depart, v1);
        assertEquals(p1.arrivee, v2);
        assertEquals(p1.heure, 10);
        assertEquals(p1.min, 30);
        assertEquals(p1.nbPlacesInitiales, 4);
        assertEquals(p1.prix, 10, 0);
    }


    @Test
    public void parcours_ajouterMembreInscrit() {
    }


    @Test
    public void parcours_supprimerMembreInscrit() {
    }

    @Test
    public void parcours_verifieDispoPlaces() {
    }

    @Test
    public void parcours_modifierPrix() {
    }

    @Test
    public void parcours_modifierNbPlaces() {
    }

    @Test
    public void parcours_supprimerParcoursCree() {
    }

    @Test
    public void parcours_reactiverParcoursCree() {
    }
}


