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
        Membre m2 = Membre.find("byEmail", "yann@gmail.com").first();
        if(m2==null)
            m2 = new Membre("laforest", "yann", "123456", new Date(), "yann@gmail.com", "M").save();
        Membre m3 = Membre.find("byEmail", "alice@gmail.com").first();
        if(m3==null)
            m3 = new Membre("grange", "alice", "123456", new Date(), "alice@gmail.com", "F").save();

        Ville v1 = new Ville("Perpignan","66000").save();
        Ville v2 = new Ville("Carquefou","44470").save();
        Date d1 = new Date();
        assertNotNull(m1);
        Parcours p1 = new Parcours(m1, v1, v2, 10, 4, d1, 10, 30).save();
        assertNotNull(p1);
    }

    @Test
    public void parcours_assert_creation() {
        Date d1 = new Date();
        Membre m1 = Membre.find("byEmail", "lei@gmail.com").first();
        Ville v = Ville.find("byNom","Perpignan").first();
        Parcours p1 = Parcours.find("byDepart",v).first();
        assertNotNull(m1);
        assertNotNull(p1);
        assertEquals(p1.createur, m1);
        assertEquals(p1.depart.nom, "Perpignan");
        assertEquals(p1.arrivee.nom, "Carquefou");
        assertEquals(p1.heure, 10);
        assertEquals(p1.min, 30);
        assertEquals(p1.nbPlacesInitiales, 4);
        assertEquals(p1.prix, 10, 0);
    }


    @Test
    public void parcours_ajouterMembreInscrit() {
        Membre m2 = Membre.find("byEmail", "alice@gmail.com").first();
        Ville v = Ville.find("byNom","Perpignan").first();
        Parcours p1 = Parcours.find("byDepart",v).first();
        int nbpassagersavant = p1.membresInscrits.size();

        p1.ajouterMembreInscrit(m2);
        assert(p1.membresInscrits.size() == nbpassagersavant+1);
    }


    @Test
    public void parcours_supprimerMembreInscrit() {
        Membre m2 = Membre.find("byEmail", "alice@gmail.com").first();
        Ville v = Ville.find("byNom","Perpignan").first();
        Parcours p1 = Parcours.find("byDepart",v).first();
        int nbpassagersavant = p1.membresInscrits.size();

        p1.supprimerMembreInscrit(m2);
        assert(p1.membresInscrits.size() == nbpassagersavant - 1);
    }

    @Test
    public void parcours_verifieDispoPlaces() {
        Membre m1 = Membre.find("byEmail", "alice@gmail.com").first();
        Membre m2 = Membre.find("byEmail", "lei@gmail.com").first();
        Membre m3 = Membre.find("byEmail", "yann@gmail.com").first();

        Ville v1 = Ville.find("byNom","Perpignan").first();
        Ville v2 = Ville.find("byNom","Carquefou").first();

        Parcours p1 = new Parcours(m1,v1,v2,12,2,new Date(),14,50);
        assert(p1.membresInscrits.size() == 0);
        assert(p1.nbPlacesInitiales == 2);
        p1.ajouterMembreInscrit(m2);
        p1.ajouterMembreInscrit(m3);
        assert(p1.membresInscrits.size()==3);
    }

    @Test
    public void parcours_supprimerParcoursCree() {
        Membre m2 = Membre.find("byEmail", "alice@gmail.com").first();
        Ville v = Ville.find("byNom","Perpignan").first();
        Parcours p1 = Parcours.find("byDepart",v).first();

        assert(p1.supprime == false);
        p1.supprimerParcoursCree();
        assert(p1.supprime == true);
        p1.reactiverParcoursCree();
        assert(p1.supprime == false);
    }

}


