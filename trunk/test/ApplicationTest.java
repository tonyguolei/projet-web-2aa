import org.junit.*;
import play.test.*;
import play.mvc.*;
import play.mvc.Http.*;
import models.*;

public class ApplicationTest extends FunctionalTest {

    @Test
    public void index() {
        Response response1 = GET("http://localhost:9000/");
        assertStatus(200, response1);
        assertIsOk(response1);
    }

    @Test
    public void chercherParcours(){
        Response response = GET("/chercherParcours?arrivee=&depart=&date=");
        assertStatus(200, response);
        assertIsOk(response);
        assertContentType("application/json",response);
    }

    @Test
    public void sinscrire(){
        Response response = GET("http://localhost:9000/application/sinscrire?" +
                "date=01/02/1990&email=abc@sfr.fr&" +
                "motdepasse=7c4a8d09ca3762af61e59520943dc26494f8941b&" +
                "nom=bbb&prenom=aaa&sexe=F");
        assertStatus(200, response);
        assertIsOk(response);

        Response response2 = POST("/seconnecter?" +
                "emailform=ab@sfr.fr&" +
                "motdepasseform=7c4a8d09ca3762af61e59520943dc26494f8941b");
        assertStatus(200, response2);
        assertIsOk(response2);

        Response response3 = GET("http://localhost:9000/utilisateur/index");
        assertStatus(200, response3);
        assertIsOk(response3);
    }

}