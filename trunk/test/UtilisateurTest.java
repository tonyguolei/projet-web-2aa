import controllers.Application;
import controllers.Utilisateur;
import models.Membre;
import models.Parcours;
import org.junit.Test;
import play.mvc.Http;
import play.test.FunctionalTest;
import play.mvc.Scope.Session;
import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 * User: lepeteil
 * Date: 05/06/14
 * Time: 13:37
 * To change this template use File | Settings | File Templates.
 */
public class UtilisateurTest extends FunctionalTest {

    private Session s = new Session();

    private void simuConnexion(String email){
        s.put("username", email);
    }
    private void simuDeconnexion(String email){
        s.remove(email);
    }
    private void creationUtilisateur(){
        Membre m = Membre.find("byEmail","alice2@gmail.com").first();
        if(m==null)
            m = new Membre("grange", "alice", "123456", new Date(), "alice2@gmail.com", "F").save();
    }

    /*
    @Test
    public void index(){
        creationUtilisateur();
        simuConnexion("alice2@gmail.com");
        Http.Response response = GET("/utilisateur/index");
        assertStatus(302, response);
        simuDeconnexion("alice2@gmail.com");
    }

    @Test void conduire(){
        creationUtilisateur();
        simuConnexion("alice2@gmail.com");
        Http.Response response = GET("utilisateur/conduire");
        assertStatus(302, response);
        simuDeconnexion("alice2@gmail.com");
    }

    @Test void sefaireconduire(){
        creationUtilisateur();
        simuConnexion("alice2@gmail.com");
        Http.Response response = GET("sefaireconduire");
        assertStatus(302, response);
        simuDeconnexion("alice2@gmail.com");
    }

    @Test void nous(){
        creationUtilisateur();
        simuConnexion("alice2@gmail.com");
        Http.Response response = GET("/utilisateur/nous");
        assertStatus(302, response);
        simuDeconnexion("alice2@gmail.com");
    }

    @Test void contact(){
        creationUtilisateur();
        simuConnexion("alice2@gmail.com");
        Http.Response response = GET("/utilisateur/contact");
        assertStatus(200, response);
        simuDeconnexion("alice2@gmail.com");
    }

    @Test void monprofil(){
        creationUtilisateur();
        simuConnexion("alice2@gmail.com");
        Http.Response response = GET("/utilisateur/monprofil");
        assertStatus(302, response);
        simuDeconnexion("alice2@gmail.com");
    }

    @Test void mesparcours(){
        creationUtilisateur();
        simuConnexion("alice2@gmail.com");
        Http.Response response = GET("/utilisateur/mesparcours");
        assertStatus(302, response);
        simuDeconnexion("alice2@gmail.com");
    }
    */
    @Test
    public void annulerReservation(){
        creationUtilisateur();
        simuConnexion("alice2@gmail.com");
        Http.Response response = GET("/annulerReservation");
        assertStatus(302, response);
        simuDeconnexion("alice2@gmail.com");
    }

    @Test
    public void supprimerParcours(){
        creationUtilisateur();
        simuConnexion("alice2@gmail.com");
        Http.Response response = GET("/supprimerParcours");
        assertStatus(302, response);
        simuDeconnexion("alice2@gmail.com");
    }

    @Test
    public void reserverParcours(){
        creationUtilisateur();
        simuConnexion("alice2@gmail.com");
        Http.Response response = GET("/reserverParcours");
        assertStatus(302, response);
        simuDeconnexion("alice2@gmail.com");
    }

    @Test
    public void reactiverParcours(){
        creationUtilisateur();
        simuConnexion("alice2@gmail.com");
        Http.Response response = GET("/reactiverParcours");
        assertStatus(302, response);
        simuDeconnexion("alice2@gmail.com");
    }

    @Test
    public void recupererParcoursCrees(){
        creationUtilisateur();
        simuConnexion("alice2@gmail.com");
        Http.Response response = GET("/recupererParcoursCrees");
        assertStatus(302, response);
        simuDeconnexion("alice2@gmail.com");
    }

    @Test
    public void recupererParcoursChoisis(){
        creationUtilisateur();
        simuConnexion("alice2@gmail.com");
        Http.Response response = GET("/recupererParcoursChoisis");
        assertStatus(302, response);    }

    @Test
    public void recupererParcoursInfo(){
        creationUtilisateur();
        simuConnexion("alice2@gmail.com");
        Http.Response response = GET("/recupererParcoursInfo");
        assertStatus(302, response);
        simuDeconnexion("alice2@gmail.com");
    }

    @Test
    public void proposerParcours(){
        /*Membre m = Membre.find("byEmail","alice2@gmail.com").first();
        if(m==null) {
            m = new Membre("grangé", "alice", "7c4a8d09ca3762af61e59520943dc26494f8941b",
                    Application.convertirStringDate("05/01/1991"), "alice2@gmail.com", "F", true).save();
        }
        assertNotNull(m);*/
        //Application.seconnecter("alice2@gmail.com","7c4a8d09ca3762af61e59520943dc26494f8941b");
        Http.Response response = POST("/proposerParcours?depart=Grenoble&departcp=38000&arrivee=Gap&arriveecp=05000" +
                "&date=15/10/2014&heure=20&min=14&prix=11&nbplaces=2");
        assertStatus(302, response);

        /*assertNotNull(renderArgs("p"));
        Parcours p = (Parcours)renderArgs("p");
        assert(p.depart.nom == "Grenoble" && p.arrivee.nom == "Gap");*/
        //Utilisateur.deconnexion();
    }

    @Test
    public void modifierMonProfil(){
        /*Membre m = Membre.find("byEmail","alice2@gmail.com").first();
        if(m==null) {
            m = new Membre("grangé", "alice", "7c4a8d09ca3762af61e59520943dc26494f8941b",
                    Application.convertirStringDate("05/01/1991"), "alice2@gmail.com", "F", true).save();
        }
        assertNotNull(m);*/
        //Application.seconnecter("alice2@gmail.com","7c4a8d09ca3762af61e59520943dc26494f8941b");
        Http.Response response = POST("/modifierMonProfil?email=alice2@gmail.com&nom=grangé&prenom=alice" +
                "&date=05/01/1991&sexe=F&new_password=7c4a8d09ca3762af61e59520943dc26494f8941b");
        assertStatus(302, response);
        /*assertNotNull(renderArgs("m"));
        Membre m1 = (Membre)renderArgs("m");
        assert(m.nom=="alice");*/
        //Utilisateur.deconnexion();
    }
}
