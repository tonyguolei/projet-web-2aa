/**
 * Created by tonyguolei on 10/15/2014.
 */
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class Server {

    //les parametres d'user
    private ServerSocket server;
    private int sId;
    private List<User> UsersWait = new ArrayList<User>();
    private List<User> UsersPlay = new ArrayList<User>();
    private int neighborServer;

    //TODO liste db

    public Server(int sId, int port) {
        this.sId = sId;

        try {
            createSocketServer(port);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public ServerSocket getServer() {
        return server;
    }

    public void setServer(ServerSocket server) {
        this.server = server;
    }

    private void createSocketServer(int port) throws IOException {

        setServer(new ServerSocket(port));

        while (true) {
            Socket socket = server.accept();
            handleUser(socket);
        }
    }

    private void handleUser(final Socket user) throws IOException {
        new Thread(new Runnable() {
            public void run() {
                BufferedReader in = null;
                PrintWriter out = null;
                try {
                    in = new BufferedReader(new InputStreamReader(user.getInputStream()));
                    out = new PrintWriter(user.getOutputStream());

                    while (in != null && out != null) {
                        String msg = in.readLine();
                        System.out.println(msg);
                        out.println("Server received " + msg);
                        out.flush();
                        if (msg.equals("bye")) {
                            break;
                        }
                    }
                } catch(IOException ex) {
                    //ex.printStackTrace();
                    //Todo envelever l'utilisateur quand il est disconnected
                    System.out.print("User disconnected");
                } finally {
                    try {
                        in.close();
                    } catch (Exception e) {}
                    try {
                        out.close();
                    } catch (Exception e) {}
                    try {
                        user.close();
                    } catch (Exception e) {}
                }
            }
        }).start();
    }

    public static void main(String[] args) {
            new Server(Integer.parseInt(args[0]), 10000);
    }
}