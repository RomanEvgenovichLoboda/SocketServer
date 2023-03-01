import Models.MyData;
import Models.User;
import Services.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.JSONObject;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.ClassNotFoundException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.List;

public class SocketServerExample {

    //static ServerSocket variable
    private static ServerSocket server;
    //socket server port on which it will listen
    private static int port = 9876;

    public static void main(String args[]) throws IOException, ClassNotFoundException{
        //create the socket server object
        server = new ServerSocket(port);
        Socket socket;
        UserService userService = new UserService();
        //keep listens indefinitely until receives 'exit' call or program terminates
        while(true){
            System.out.println("\n\t\tWaiting for the client request");
            //creating socket and waiting for client connection
            socket = server.accept();
            //read from socket to ObjectInputStream object
            ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
            ObjectOutputStream oos = null;
            //convert ObjectInputStream object to String
            JSONObject message;
            try{
                 message = new JSONObject(ois.readObject().toString());//(String) ois.readObject();
                System.out.println("\nMessage Received: " + message);
                ObjectMapper objectMapper = new ObjectMapper();
                MyData md = objectMapper.readValue(message.toString(), MyData.class);
                System.out.println("User: " + md.getUsr().getInfo());
                if(md.isRegistr()){
                    List<User> usList = userService.findAllUsers();
                    boolean isExists=false;
                    for (User us:usList ) {
                        if(us.getLogin().equals(md.getUsr().getLogin())){
                            System.out.println("\n\tUser Login Already Exists!");
                            oos = new ObjectOutputStream(socket.getOutputStream());
                            //write object to Socket
                            oos.writeObject("\n\tUser Login Already Exists!");
                            isExists=true;
                            break;
                        }
                    }
                    if(!isExists){
                        userService.saveUser(md.getUsr());
                        System.out.println("\n\tRegistration is Ok!");
                        oos = new ObjectOutputStream(socket.getOutputStream());
                        //write object to Socket
                        oos.writeObject("\n\tRegistration is Ok!");
                    }
                }
                else{
                    List<User> usList = userService.findAllUsers();
                    boolean autorise=false;
                    for (User us:usList) {
                        if(us.getLogin().equals(md.getUsr().getLogin())&&us.getPassword().equals(md.getUsr().getPassword())){
                            System.out.println("\n\tAutorisation is Ok!");
                            oos = new ObjectOutputStream(socket.getOutputStream());
                            //write object to Socket
                            oos.writeObject("\n\tAutorisation is Ok!");
                            autorise=true;
                            break;
                        }
                    }
                    if(!autorise){
                        System.out.println("\n\tWrong Data!");
                        oos = new ObjectOutputStream(socket.getOutputStream());
                        //write object to Socket
                        oos.writeObject("\n\tWrong Data!");
                    }
                }
                ois.close();
                oos.close();
                socket.close();

            }
            catch (Exception ex){
                System.out.println(ex.getMessage());
                break;
            }
        }
        System.out.println("Shutting down Socket server!!");
        //close the ServerSocket object
        server.close();
    }
}