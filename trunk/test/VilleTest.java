/**
 * Created by laforesy on 19/05/14.
 */
import org.junit.*;
import java.util.*;
import play.test.*;
import models.*;

public class VilleTest extends UnitTest {

    @Test
    public void ville_creation(){
        Ville v1 = new Ville("Chassieu","69680").save();
        Ville v2 = new Ville("Lyon","69006").save();
    }

    @Test
    public void ville_assert_creation(){
        Ville v1 = Ville.find("byCodePostal","69680").first();
        Ville v2 = Ville.find("byCodePostal","69006").first();
        assertEquals(v1.nom,"Chassieu");
        assertEquals(v1.codePostal,"69680");
        assertEquals(v2.nom,"Lyon");
        assertEquals(v2.codePostal,"69006");
    }
    @Test
    public void ville_to_string(){
        Ville v1 = Ville.find("byCodePostal","69680").first();
        Ville v2 = Ville.find("byCodePostal","69006").first();
        v1.toString();
        v2.toString();

    }
}
