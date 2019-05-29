import java.io.*;
import java.net.*;
import java.util.*;
import java.awt.*;
import javax.swing.*;

public class MultiThreadMemoryServer extends JFrame implements MemoryConstants{
   private JTextArea jta = new JTextArea();
   public static void main(String[] args){
      new MultiThreadMemoryServer();
   }
   public void buildGUI(){
      setLayout(new BorderLayout());
      add(new JScrollPane(jta), BorderLayout.CENTER);
      setTitle("MultiThreadMemoryServer");
      setSize(500, 500);
      setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      setVisible(true);
   }
   public MultiThreadMemoryServer(){
      buildGUI();
      try(ServerSocket serverSocket = new ServerSocket(PORT)){
         jta.append("MultiThreadMemotyServer started at " + new Date() + '\n');
         int clientNum = 0;
         while (true) {
            clientNum ++;
            Socket socket1 = serverSocket.accept();
            //Socket socket2 = serverSocket.accept();
            jta.append("Starting thread for client " + clientNum);
            InetAddress inetAddress1 = socket1.getInetAddress();
            //InetAddress inetAddress2 = socket2.getInetAddress();
            jta.append("Client " + clientNum + "'s host name is " + inetAddress1.getHostName() + "\n");
            //jta.append("Client " + clientNum + "'s IP Address is " + inetAddress2.getHostAddress() + "\n");
            Runnable memoryService = new MemoryService(socket1,jta,clientNum);
            (new Thread(memoryService)).start();
         }
      }
      catch(IOException e){
         System.err.println(e.getMessage());
      }
   }
}