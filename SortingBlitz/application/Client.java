package application;

import java.io.*; 
import java.net.*; 
 
import javafx.scene.control.TextArea;
  
public class Client  
{ 
//    final static int ServerPort = 5000; 
	private DataOutputStream dos;
	private String name;
  
    public Client(String name, int port, TextArea chatDisplay) throws UnknownHostException, IOException  
    {
    	this.name = name;
      
        // getting localhost ip 
        InetAddress ip = InetAddress.getByName("localhost"); 
          
        // establish the connection 
        Socket s = new Socket(ip, port); 
          
        // obtaining input and out streams 
        DataInputStream dis = new DataInputStream(s.getInputStream()); 
        dos = new DataOutputStream(s.getOutputStream()); 
  
        // sendMessage thread 
//        Thread sendMessage = new Thread(new Runnable()  
//        { 
//            @Override
//            public void run() { 
//                while (true) { 
//  
//                        String message = messageInput.getText();
//                        if (!message.isEmpty()) {
//                            // In a real application, you would send the message over the network
//                            chatDisplay.appendText("You: " + message + "\n");
//                            messageInput.clear();
//                        }
//                    
//
//                	    
//
//
//                } 
//            } 
//        }); 
          
        // readMessage thread 
        Thread readMessage = new Thread(new Runnable()  
        { 
            @Override
            public void run() { 
  
                while (true) { 
                    try { 
                        // read the message sent to this client 
                        String msg = dis.readUTF(); 
                        chatDisplay.appendText(msg + "\n");
                        System.out.println(msg); 
                    } catch (IOException e) { 
  
                        e.printStackTrace(); 
                    } 
                } 
            } 
        }); 
  
//        sendMessage.start(); 
        readMessage.start(); 
  
    }
    
    public void sendMessage(String msg){
        try { 
            // write on the output stream 
            dos.writeUTF(name +" : " + msg); 
        } catch (IOException e) { 
            e.printStackTrace(); 
        } 
 	} 
}