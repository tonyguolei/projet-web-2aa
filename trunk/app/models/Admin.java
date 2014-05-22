package models;

import play.data.validation.Required;
import play.db.jpa.*;

/**
 * Created with IntelliJ IDEA.
 * User: lepeteil
 * Date: 21/05/14
 * Time: 17:58
 * To change this template use File | Settings | File Templates.
 */
public class Admin extends Model {

    @Required
    public String login;
    @Required
    public String password;

    public Admin(String login, String password) {
        this.login = login;
        this.password = password;
    }
}
