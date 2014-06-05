import org.junit.*;
import play.test.*;
import play.mvc.*;
import play.mvc.Http.*;
import models.*;

public class ApplicationTest extends FunctionalTest {

    @Test
    public void index() {
        Response response1 = GET("/");
        assertStatus(200, response1);
        Response response2 = GET("/index");
        assertStatus(200, response2);
    }

    @Test
    public void chercherParcours(){
        Response response = GET("/chercherParcours");
        assertStatus(200, response);
        response = GET("/chercherParcours?depart=a&arrivee=g&date=29/07/2014");
        assertStatus(200, response);
    }

    @Test
    public void sinscrire(){
        Response response = GET("/sinscrire");
        assertStatus(200, response);
    }
    
}