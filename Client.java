import java.io.*;
import java.net.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

/**
 * Client for the Encryption and Decryption program. <br />
 * Send commands to the Server in response to the GUI.  <br />
 * Written for CpSc 1181.  <br />
 * based on http://www.cs.armstrong.edu/liang/intro8e/book/Client.java
 * @author ******** your name ********
 * @version March 31, 2017
 */
public class Client extends JFrame implements Runnable, Encryptable 
{   

   // the instance variables socket, toServer, and fromServer are 
   // needed because of the interface Runnable that has a method run 
   // that takes no arguments   
   private Socket socket;
   private DataOutputStream toServer;
   private DataInputStream fromServer;
   private JLabel firstLabel;
   private JLabel secondLabel;
   private JButton encrypt;
   private JButton decipher;
   private JButton stop;
   private JTextField firstTF;
   private JTextField secondTF;
   
   // display the blank as a dot
   private char BLANK = '.';
   
   /**
   * Set up the GUI, connect to the server and decrypt and encrypt.
   * @param args the line commands -- not used
   */
   public static void main(String[] args)
   {
      new Client(HOST);
   }

   /**
   * Sets up the Graphical User Interface s and it sets ups the I/O streams 
   * from a socket.
   * It then starts a thread if the socket to the server worked. 
   * @param serverHost IPAddress of server that is running already
   */   
   public Client(String serverHost)
   {
      buildGUI();
      openConnection(serverHost);
      if(socket != null && !socket.isClosed()){
         (new Thread(this)).start();
      }
         
   } // Client
   private void openConnection(String severHost){
      try{
         this.socket = new Socket(severHost,PORT);
         this.fromServer = new DataInputStream(socket.getInputStream());
         this.toServer = new DataOutputStream(socket.getOutputStream());
      }
      catch(SecurityException e){
         System.err.println("Open socket fails, it is not safe");
      }
      catch(UnknownHostException e){
         System.err.println("Open socket fails, unknown host");
      }
      catch(IOException e){
         System.err.println(e.getMessage());
      }
   }

   /** 
   * Continues sending and receiving data as long as the server does 
   * not send a DONE
   */  
   @Override
   public void run()
   {
      boolean done = false;
      try{
         while(!done){
            int msg = fromServer.readInt();
            if(msg == DONE){
               done = true;
            }
            else if(msg == NAME){
               int name = fromServer.readInt();
               setTitle(""+ cmdToString(NAME) + " " + name);
            }
            else if(msg == PLAIN){
               String s = "";
               int numOfString = fromServer.readInt();
               for(int i = 0; i < numOfString; i ++){
                  s += fromServer.readChar();
               }
               firstTF.setText(s);
            }
            else if(msg == ENCODED){
               String s = "";
               int numOfString = fromServer.readInt();
               for(int i = 0; i < numOfString; i ++){
                  s += fromServer.readChar();
               }
               System.err.println(s);
            }
         }
      }
      catch(IOException e){
         System.err.println(e.getMessage());
      }
   }

   /**
   * 
   */
   public void buildGUI()
   {
      final int FRAME_WIDTH = 1200;
      final int FRAME_HEIGHT = 300;
           
      setTitle("Encoder and Decoder");
      setSize(FRAME_WIDTH, FRAME_HEIGHT);
      setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      setVisible(true);
      firstLabel = new JLabel("Enter plain");
      secondLabel = new JLabel("Enter encrypt");
      encrypt = new JButton("encrypt");
      encrypt.addActionListener(new EncryptListener());
      decipher = new JButton("decipher");
      decipher.addActionListener(new DecipherListener());
      stop = new JButton("stop");
      stop.addActionListener(E->{try{toServer.writeInt(QUIT);}
                              catch(IOException e){System.err.println(e.getMessage());
                                 }});
      firstTF = new JTextField("MY SECRET MESSAGE");
      secondTF = new JTextField(" ");
      
      setLayout(new BorderLayout());
      JPanel p1 = new JPanel();
      p1.setPreferredSize(new Dimension(1200,100));
      p1.setLayout(new GridLayout(1,2));
      p1.add(firstLabel);
      p1.add(firstTF);
      JPanel p2 = new JPanel();
      p2.setPreferredSize(new Dimension(1200,100));
      p2.setLayout(new GridLayout(1,2));
      p2.add(secondLabel);
      p2.add(secondTF);
      JPanel p3 = new JPanel();
      p3.setLayout(new GridLayout(1,3));
      p3.setPreferredSize(new Dimension(1200,100));
      p3.add(encrypt);
      p3.add(decipher);
      p3.add(stop);
      
      add(p1,BorderLayout.NORTH);
      add(p2,BorderLayout.CENTER);
      add(p3,BorderLayout.SOUTH);
      
   }  //buildGUI
   class EncryptListener implements ActionListener{
      public void actionPerformed(ActionEvent event){
         try{
            toServer.writeInt(ENCRYPT);
            String msgToEncrypted = firstTF.getText().trim();
            toServer.writeInt(msgToEncrypted.length());
            for(int i = 0; i < msgToEncrypted.length(); i ++){
               toServer.writeChar(msgToEncrypted.charAt(i));
            }
            toServer.flush();
         }
         catch(IOException e){
            System.err.println(e.getMessage());
         }
      }
   }
   class DecipherListener implements ActionListener{
      public void actionPerformed(ActionEvent event){
         try{
            toServer.writeInt(DECRYPT);
            String msgToDecrypted = secondTF.getText().trim();
            toServer.writeInt(msgToDecrypted.length());
            for(int i = 0; i < msgToDecrypted.length(); i ++){
               toServer.writeChar(msgToDecrypted.charAt(i));
            }
            toServer.flush(); 
         }
         catch(IOException e){
            System.err.println(e.getMessage());
         }
      }
   }      
} // Client 