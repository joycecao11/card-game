import java.io.*;
import java.net.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.*;

public class MemoryClient extends JFrame implements Runnable, MemoryConstants{
   private static final int CARD_FRAME_WIDTH = 600;
   private static final int CARD_FRAME_HEIGHT = 600;
   
   private JLabel yourTurn;
   private JLabel pair;
   private JLabel warning;
   private JButton quitGame;    
   private JButton startNewGame;
   private ArrayList<Card> cardList;
   
   private DataOutputStream toServer;
   private DataInputStream fromServer;
   private Socket socket;
   public static void main(String[] args){
      new MemoryClient();
   }
   public MemoryClient(){
      String severHost = "localhost";
      bulidGUI();
      openConnection(severHost);
      if(socket!=null && socket.isClosed()){
         (new Thread(this)).start();
      }
   }
   private void bulidGUI(){
      cardList = new ArrayList<Card>();
      setTitle("Card game");
      setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      setVisible(true);
      setSize(CARD_FRAME_WIDTH, CARD_FRAME_HEIGHT);
      createLabel();
      createCardButton();
      createCardPanel();
   }
   public void createCardPanel(){
      setLayout(new BorderLayout());
      
      JPanel p2 = new JPanel();
      p2.setLayout(new GridLayout(1,2));
      p2.add(yourTurn);
      p2.add(pair);
      add(p2,BorderLayout.NORTH);
      
      JPanel p3 = new JPanel();
      p3.setLayout(new GridLayout(2,3));
      for(int i = 0; i < cardList.size(); i ++){
         p3.add(cardList.get(i));
      }
      add(p3,BorderLayout.CENTER);
      
      JPanel p4 = new JPanel();
      p4.setLayout(new GridLayout(1,3));
      p4.add(quitGame);
      p4.add(warning);
      p4.add(startNewGame);
      add(p4,BorderLayout.SOUTH);
   }
   public void createLabel(){
      yourTurn = new JLabel(" ");
      pair = new JLabel("Pairs: 0");
      warning = new JLabel(" ");
   }
   public void createCardButton(){
      for(int i = 0; i < row*column; i++){
         cardList.add(new Card(i));
      }
      for(int i = 0; i < row*column; i++){
         cardList.get(i).addActionListener(new CardButtonListener(i));
      }
      quitGame = new JButton("QUIT");
      quitGame.addActionListener(E->{try{toServer.writeInt(QUIT);System.err.println("client press quit button");
						toServer.flush();}
						catch(IOException e){System.err.println(e.getMessage());
						}});
      startNewGame = new JButton("RESTART");
   }
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
   private void closeConnection(){
      try{
         if(socket!=null && !socket.isClosed()){
            socket.close();
         }
      }
      catch(IOException e){
         System.err.println(e.getMessage());
      }
      socket = null;
   }
   public void run(){
      boolean done = false;
      try{
         while(!done){
            int msg = fromServer.readInt();
		 System.err.println(cmdToString(msg));
            if(msg == DONE){
               done = true;
            }
            else if(msg == SHOWCARD){
               int cardNum = fromServer.readInt();
               int pictureNum = fromServer.readInt();
               cardList.get(cardNum).display(pictureNum);
            }
	    /* else if(msg == COVER){
		int cardNum = fromServer.readInt();
		cardList.get(cardNum).coverCard();
	    }
	    else if(msg == REVEAL){
		int cardNum = fromServer.readInt();
		cardList.get(cardNum).revealCard();
	    }
	    else if(msg == PAIRS){
		int pairNum = fromServer.readInt();
		pair.setText("Pairs: " + pairNum);
	    }
	    else if(msg == WINNER){
		int winnerNum = fromServer.readInt();
		int winner = fromServer.readInt();
		if(winner == ISWINNER){
			yourTurn.setText("You win");
		}
		else if(winner == ISNOTWINNER){
			yourTurn.setText("You lose");
		}
	    }
	    else if(msg == WAIT){
		try{
			Thread.sleep(3000);
		}
		catch(InterruptedException e){
			e.printStackTrace();
		}
	    }
	    else if(msg == cannotChooseMoreThanTwoCards){
		String w = cmdToString(msg);
		warning.setOpaque(true);
		warning.setBackground(Color.RED);
		warning.setText(w);
	    }
	    else if(msg == itIsNotYourTurn){
		String w = cmdToString(msg);
		warning.setOpaque(true);
		warning.setBackground(Color.RED);
		warning.setText(w);
	    }*/
         } 
      }
      catch(IOException e){
         System.err.println(e.getMessage());
      }
   }
	class CardButtonListener implements ActionListener{
		private int index;
		public CardButtonListener(int i){
			this.index = i;
		}
		public void actionPerformed(ActionEvent event){
			try{
				toServer.writeInt(DISPLAY);
				toServer.writeInt(index);
				toServer.flush();
			}
			catch(IOException e){
				System.err.println(e.getMessage());
			}
		}
	}
}