import java.net.Socket;

/**
 * Created by tonyguolei on 10/20/2014.
 */
enum Status { WAIT, PLAY, DISCONNECT };

public class User {
    //les parametres d'user
    private String pseudo;
    private Socket socket;
    private Status status;

    //le constructeur d'User
    public User(String pseudo) {
        this.pseudo = pseudo;
    }

    public String getPseudo() {
        return pseudo;
    }

    //les methodes d'User
    public void setPseudo(String pseudo) {
        this.pseudo = pseudo;
    }

    public Socket getSocket() {
        return socket;
    }

    public void setSocket(Socket socket) {
        this.socket = socket;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }
}
