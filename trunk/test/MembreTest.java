/**
 * Created with IntelliJ IDEA.
 * User: lepeteil
 * Date: 26/05/14
 * Time: 23:57
 * To change this template use File | Settings | File Templates.
 */
import org.junit.*;
import java.util.*;
import play.test.*;
import models.*;

public class MembreTest extends UnitTest  {

        @Test
        public void membre_creation(){

            Membre m1 = Membre.find("byEmail", "lei@gmail.com").first();
            if(m1==null)
                m1 = new Membre("guo", "lei", "123456", new Date(), "lei@gmail.com", "M").save();
            Membre m2 = Membre.find("byEmail", "yann@gmail.com").first();
            if(m2==null)
                m2 = new Membre("laforest", "yann", "123456", new Date(), "yann@gmail.com", "M").save();
            Membre m3 = Membre.find("byEmail", "alice@gmail.com").first();
            if(m3==null)
                m3 = new Membre("grange", "alice", "123456", new Date(), "alice@gmail.com", "F").save();
        }

        @Test
        public void membre_assert_creation(){
            Membre m1 = Membre.find("byEmail","lei@gmail.com").first();
            assertNotNull(m1);
            assertEquals(m1.nom,"guo");
            assertEquals(m1.prenom,"lei");
            assertEquals(m1.email,"lei@gmail.com");
            //assertEquals(m1.motDePasse,"");
            assertEquals(m1.sexe,"M");
        }

        @Test
        public void membre_parcoursCrees(){
            Membre m2 = Membre.find("byEmail","alice@gmail.com").first();
            Ville v1 = new Ville("Annecy", "74000").save();
            Ville v2 = new Ville("Gap", "05000").save();
            assertNotNull(m2);
            Parcours p1 = new Parcours(m2, v1, v2, 8, 1,14,00).save();
            Parcours p2 = new Parcours(m2, v2, v2, 8, 1,14,00).save();
            Parcours p3 = new Parcours(m2, v1, v2, 8, 1,14,00).save();
            assertNotNull(p1);
            assertNotNull(p2);
            assertNotNull(p3);
            assert(m2.lesParcoursCrees.size()==3);

            m2.supprimerParcours(p3);
            assert(p3.supprime == true);

        }

        @Test
        public void membre_parcoursChoisis(){
            Membre m2 = Membre.find("byEmail","alice@gmail.com").first();
            Membre m3 = Membre.find("byEmail","yann@gmail.com").first();
            Ville v1 = new Ville("Nice", "06000").save();
            Ville v2 = new Ville("Digne", "04000").save();
            Parcours p4 = new Parcours(m2, v1, v2, 8, 1,14,00).save();
            m3.ajouterParcoursChoisi(p4);

            Boolean res = false;
            Iterator<Parcours> itr = m3.lesParcoursChoisis.iterator();
            while(itr.hasNext()) {
                if(itr.next().equals(p4))
                    res = true;
            }
            assert(res == true);

            m3.seDesinscrireParcours(p4);
            Boolean res2 = false;
            itr = m3.lesParcoursChoisis.iterator();
            while(itr.hasNext()) {
                if(itr.next().equals(p4))
                res2 = true;
            }
            assert(res2 == false);
        }

        @Test
        public void membre_suppressioncompte(){
            Membre m3 = Membre.find("byEmail","yann@gmail.com").first();
            m3.supprimerCompte();
            assert(m3.desinscrit==true);
        }


}
