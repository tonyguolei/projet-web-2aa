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
        Membre m1 = Membre.find("byEmail", "lei@gmail.com").first();
        if(m1==null)
            m1 = new Membre("guo", "lei", "123456", new Date(), "lei@gmail.com", "M").save();

        Ville v1 = new Ville("valence","26000").save();
        Ville v2 = new Ville("lyon","69000").save();
        Date d1 = new Date();
        assertNotNull(m1);
        Parcours p1 = new Parcours(m1, v1, v2, 10, 4, d1, 10, 30).save();
        assertNotNull(p1);
    }

    @Test
    public void parcours_assert_creation() {
        Date d1 = new Date();
        Membre m1 = Membre.find("byEmail", "lei@gmail.com").first();
        Parcours p1 = Parcours.find("byHeure", 10).first();
        assertNotNull(m1);
        assertNotNull(p1);
        assertEquals(p1.createur, m1);
        assertEquals(p1.depart.nom, "valence");
        assertEquals(p1.arrivee.nom, "lyon");
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


