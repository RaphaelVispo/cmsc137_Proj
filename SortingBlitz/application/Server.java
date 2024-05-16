package application;
import java.io.*;
import java.util.*;
import java.net.*;
 
// Server class
public class Server 
{
 
    // Vector to store active clients
    static Vector<ClientHandler> ar = new Vector<>();
     
    // counter for clients
    static int i = 0;
 
    public Server (String name, int port) throws IOException 
    {
        // server is listening on port 1234
    	System.out.println("hellooo");
;        ServerSocket ss = new ServerSocket(port);
         
        Socket s;
         
        // running infinite loop for getting
        // client request
        while (true) 
        {
            // Accept the incoming request
            s = ss.accept();
 
            System.out.println("New client request received : " + s);
             
            // obtain input and output streams
            DataInputStream dis = new DataInputStream(s.getInputStream());
            DataOutputStream dos = new DataOutputStream(s.getOutputStream());
             
            System.out.println("Creating a new handler for this client...");
 
            // Create a new handler object for handling this request.
            ClientHandler mtch = new ClientHandler(s, dis, dos);
 
            // Create a new Thread with this object.
            Thread t = new Thread(mtch);
             
            System.out.println("Adding this client to active client list");
 
            // add this client to active clients list
            ar.add(mtch);
 
            // start the thread.
            t.start();
 
            // increment i for new client.
            // i is used for naming only, and can be replaced
            // by any naming scheme
            i++;
 
        }
    }
}
 
// ClientHandler class
class ClientHandler implements Runnable 
{
    final DataInputStream dis;
    final DataOutputStream dos;
    Socket s;
    boolean isloggedin;
     
    // constructor
    public ClientHandler(Socket s,
                            DataInputStream dis, DataOutputStream dos) {

        this.dis = dis;
        this.dos = dos;
        this.s = s;
        this.isloggedin=true;
    }
 
    @Override
    public void run() {
 
        String received;
        while (true) 
        {
            try
            {
                // receive the string
                received = dis.readUTF();
                 
                System.out.println(received);
                 
                if(received.equals("logout")){
                    this.s.close();
                    break;
                }

 
                // search for the recipient in the connected devices list.
                // ar is the vector storing client of active users
                for (ClientHandler mc : Server.ar) 
                {
                    // if the recipient is found, write on its
                    mc.dos.writeUTF (received);
                }
            } catch (IOException e) {
                 
                e.printStackTrace();
            }
             
        }
        try
        {
            // closing resources
            this.dis.close();
            this.dos.close();
             
        }catch(IOException e){
            e.printStackTrace();
        }
    }
}