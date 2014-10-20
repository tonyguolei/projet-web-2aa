/**
 * Created by tonyguolei on 10/15/2014.
 */
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class User {

    // les paremetres d'user
    private String pseudo;
    private String addressServer;
    private int port;
    private Socket socket;

    //constructeur
    public User(String addressServer, int port) throws Exception{
        this.addressServer = addressServer;
        this.port = port;

        socket = new Socket(addressServer, port);
        BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        PrintWriter out = new PrintWriter(socket.getOutputStream());
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

        while (true) {
            String msg = reader.readLine();
            out.println(msg);
            out.flush();
            if (msg.equals("bye")) {
                break;
            }
            System.out.println(in.readLine());
        }
        socket.close();
    }

    //les methodes de'User
    public String getPseudo() {
        return pseudo;
    }

    public void setPseudo(String pseudo) {
        this.pseudo = pseudo;
    }

    public String getAddressServer() {
        return addressServer;
    }

    public void setAddressServer(String addressServer) {
        this.addressServer = addressServer;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public void createPseudo(){

    }

    public void connectServer(){

    }

    public static void main(String[] args) throws Exception {

        new User("localhost", 10000);
    }
}