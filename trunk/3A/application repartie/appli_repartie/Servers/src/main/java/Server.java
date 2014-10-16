/**
 * Created by tonyguolei on 10/15/2014.
 */
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {

    private int port = 10000;

    public Server() {
        try {
            createSocketServer(port);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void createSocketServer(int port) throws IOException {

        ServerSocket server = new ServerSocket(port);

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

                    while (true) {
                        String msg = in.readLine();
                        System.out.println(msg);
                        out.println("Server received " + msg);
                        out.flush();
                        if (msg.equals("bye")) {
                            break;
                        }
                    }
                } catch(IOException ex) {
                    ex.printStackTrace();
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
            new Server();
    }
}