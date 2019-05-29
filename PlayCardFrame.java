import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ActionListener;
import javax.swing.*;
import java.util.*;
import java.awt.*;
/** This is a class for frame to look pictures
 * @author   Xuechun  Cao
 * Student ID Number: 100271794
 * @version 1.0
 * Course: CPSC 1181
 * Section: 002
 */
public class PlayCardFrame extends JFrame implements MemoryConstants{
   //Declare constants
   private static final int COMMAND_FRAME_WIDTH = 500;
   private static final int COMMAND_FRAME_HEIGHT = 100;
   private static final int CARD_FRAME_WIDTH = 600;
   private static final int CARD_FRAME_HEIGHT = 600;
   //Declare instance variable
   private JFrame cardFrame;
   
   private JComboBox<String> combo;
   private JTextField leftField;
   private String leftFieldCommand;
   private JTextField rightField;
   private String rightFieldCommand;
   private JButton commandButton;
   
   private String player;
   private boolean isPlaying;
   private int count;
   private ArrayList<Card> cardList;
   
   private JLabel yourTurn;
   private JLabel pair;
   private JLabel warning;
   private Card one;
   private Card two;
   private Card three;
   private Card four;
   private Card five;
   private Card six;
   private JButton quitGame;    
   private JButton startNewGame;
 /** 
 *Constructs a PlayCardFrame object.
 * <p>
 *Initialize the instance variables.
 * 
 */  
   public PlayCardFrame(){
      isPlaying = false;
      count = 0;
      cardList = new ArrayList<Card>();
      
      cardFrame = new JFrame();
      cardFrame.setTitle("Card game");
      cardFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      cardFrame.setVisible(true);
      cardFrame.setSize(CARD_FRAME_WIDTH, CARD_FRAME_HEIGHT);
      createLabel();
      createCardButton();
      createCardPanel();
      
      setTitle("Command");
      setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      setVisible(true);
      setSize(COMMAND_FRAME_WIDTH, COMMAND_FRAME_HEIGHT);
      createComboBox();
      createLeftField();
      createRightField();
      createCommandButton();
      createCommandPanel();
   }
 /** 
 *create the frame to fisplay cards
   @return this method returns nothing
 */ 
   public void cardPad(){
      createLabel();
      createCardButton();
      createCardPanel();
   }
   /** 
 *create the frame to fisplay cards
   @return this method returns nothing
 */
   public void commandPad(){
      createComboBox();
      createLeftField();
      createRightField();
      createCommandButton();
      createCommandPanel();
   }
/** 
 *create combo text
   @return this method returns nothing
 */
   public void createComboBox(){
      combo = new JComboBox<String>();
      combo.addItem("SETPLAYER");
      combo.addItem("PLAYING");
      combo.addItem("WINNER");
      combo.addItem("PAIRS");
      combo.addItem("WAIT");
      combo.addItem("COVER");
      combo.addItem("DISPLAY");
      combo.addItem("REVEAL");
   }
/** 
 *create the left text field
   @return this method returns nothing
 */
   public void createLeftField(){
      leftField = new JTextField();
      final int FIELD_WIDTH = 5;
      leftField = new JTextField(FIELD_WIDTH);
      leftField.setText("");
   }
/** 
 *create the right text field
   @return this method returns nothing
 */
   public void createRightField(){
      rightField = new JTextField();
      final int FIELD_WIDTH = 5;
      rightField = new JTextField(FIELD_WIDTH);
      rightField.setText("");
   }
/** 
 *create the the command button
   @return this method returns nothing
 */
   public void createCommandButton(){
      commandButton = new JButton("process command");
      commandButton.addActionListener(E->{selectCombo();});
   }
/** 
 *create the command panel
   @return this method returns nothing
 */
   public void createCommandPanel(){
      JPanel p1 = new JPanel();
      p1.add(combo);
      p1.add(leftField);
      p1.add(rightField);
      p1.add(commandButton);
      add(p1);
   }
/** 
 *create the card panel
   @return this method returns nothing
 */
   public void createCardPanel(){
      cardFrame.setLayout(new BorderLayout());
      
      JPanel p2 = new JPanel();
      p2.setLayout(new GridLayout(1,2));
      p2.add(yourTurn);
      p2.add(pair);
      cardFrame.add(p2,BorderLayout.NORTH);
      
      JPanel p3 = new JPanel();
      p3.setLayout(new GridLayout(row,column));
      for(int i = 0; i < cardList.size(); i ++){
         p3.add(cardList.get(i));
      }
      cardFrame.add(p3,BorderLayout.CENTER);
      
      JPanel p4 = new JPanel();
      p4.setLayout(new GridLayout(1,3));
      p4.add(quitGame);
      p4.add(warning);
      p4.add(startNewGame);
      cardFrame.add(p4,BorderLayout.SOUTH);
   }
/** 
 *create labels on the card panel
   @return this method returns nothing
 */
   public void createLabel(){
      yourTurn = new JLabel(" ");
      pair = new JLabel(" ");
      warning = new JLabel(" ");
   }
/** 
 *create the card button
   @return this method returns nothing
 */
   public void createCardButton(){
      for(int i = 0; i < row*column; i++){
         cardList.add(new Card(i));
      }
      for(int i = 0; i < cardList.size(); i ++){
            cardList.get(i).addActionListener(new ClickButtonListener(i));
      }
      quitGame = new JButton("QUIT");
      quitGame.addActionListener(E->{if(isPlaying){
                                       System.exit(0);}
                                    else{System.out.println("Sever --> Client : WARNING : You can only quit when it is your turn");
                                    warning.setOpaque(true);
                                    warning.setBackground(Color.RED);
                                    warning.setText("You can only quit when it is your turn");}});
      startNewGame = new JButton("RESTART");
      startNewGame.addActionListener(E->{if(isPlaying){
                                          isPlaying = false;
                                          count = 0;
                                          yourTurn.setText(" ");
                                          pair.setText(" ");
                                          warning.setOpaque(false);
                                          warning.setText(" ");
                                    for(int i = 0; i < cardList.size(); i ++){
                                       cardList.get(i).coverCard();
                                       cardList.get(i).notRevealCard();
                                    }}
                                    else{System.out.println("Sever --> Client : WARNING : You can only restart when it is your turn");
                                    warning.setOpaque(true);
                                    warning.setBackground(Color.RED);
                                    warning.setText("You can only restart when it is your turn");}});
      
   }
   /**
   *An inner class implements ActionListener
   *to click buttons to choose cards.
   */
      class ClickButtonListener implements ActionListener{
         private int index;
         public ClickButtonListener(int i){
            index = i;
         }
         public void actionPerformed(ActionEvent e){
            if(!isPlaying){
               System.out.println("Sever --> Client : WARNING : It is not your turn");
               warning.setOpaque(true);
               warning.setBackground(Color.RED);
               warning.setText("   It is not your turn");
            }
            else{
               if(count >= 2){
                  System.out.println("Sever --> Client : WARNING : Cannot choose more than two cards once");
                  warning.setOpaque(true);
                  warning.setBackground(Color.RED);
                  warning.setText("Cannot choose more than two cards once");
               }
               else{
                  if(!(cardList.get(index).hasBeenRevealed())){
                     System.out.println("Client -> Sever : click button " + index);
                     count ++;
                  }
                  else{
                     System.out.println("Sever --> Client : WARNING : The card has been revealed");
                     warning.setOpaque(true);
                     warning.setBackground(Color.RED);
                     warning.setText("You cannot choose this card");
                  }
               }
            }
         }
      }
/** 
 *use switch to divide different method when choose different command
* @return this method returns nothing
 */
   public void selectCombo(){
      try{
         String selectedString = (String) combo.getSelectedItem();
         switch(selectedString){
            case "SETPLAYER": setPlayer();
                           break;
            case "PLAYING": playing();
                           break;
            case "WINNER": winner();
                           break;
            case "PAIRS": pairs();
                           break;
            case "WAIT": memorize();
                           break;
            case "COVER": cover();
                           break;
            case "DISPLAY": display();
                           break;
            case "REVEAL": reveal();
                           break;
         }
      }
      catch(Exception e){
         System.out.println("Sever --> client : WARNING : " + e.getMessage());
         warning.setOpaque(true);
         warning.setBackground(Color.RED);
         warning.setText(e.getMessage());
      }
   }
/** 
 *wait fo a moment to memorize the cards
* @return this method returns nothing
 */
   public void memorize(){
      System.out.println("Sever --> : wait");
      try{
         Thread.sleep(3000);
      }
      catch(InterruptedException e){
         e.printStackTrace();
      }
   }
/** 
 *permanently display cards
* @return this method returns nothing
 */   
   public void reveal() throws Exception{
      System.out.println("Sever --> : reveal");
      int revealNum = Integer.parseInt(leftField.getText());
      cardList.get(revealNum).revealCard();
      if(isPlaying){
         System.out.println("Sever --> Client : player " + player + " reveals " + revealNum + " card");
      }
      else{
         System.out.println("Client --> Sever : the other player  reveals " + revealNum + " card");
      }
   }
/** 
 *read user input name to set player
* @return this method returns nothing
 */
   public void setPlayer(){
      player = leftField.getText();
      System.out.println("sever --> player is: " + player);
   }
/** 
 *read user input name to set player who is playing
* @return this method returns nothing
 */
   public void playing() throws Exception{
      System.out.println("sever --> playing is: " +leftField.getText());
      if(player.equals(leftField.getText())){
         isPlaying = true;
         yourTurn.setText("  It is your turn");
         count = 0;
      }
      if(!(player.equals(leftField.getText()))){
         isPlaying = false;
         yourTurn.setText("  It is not your turn");
         System.out.println("Sever --> client : WARNING : It is not your turn");
      }
   }
/** 
 *read user input number of the card that user wants to display and the text that the card will display
* @return this method returns nothing
 */
   public void display() throws Exception{
         yourTurn.setText("  It is your turn");
         int cardNum1 = Integer.parseInt(leftField.getText());
         String cardValue1 = rightField.getText().trim();
      if(!cardList.get(cardNum1).hasBeenRevealed()){
         cardList.get(cardNum1).display(Integer.parseInt(cardValue1));
         if(isPlaying){
            System.out.println("Client --> Sever ; Player " + player + " choose " + cardNum1 + " card to be " + cardValue1);
         }
         else{
            System.out.println("Client --> Sever : the other player  choose " + cardNum1 + " card to be " + cardValue1);
         }
      }
      else{
         throw new PlayingException("This card has been revealed");
      }
   }
/** 
 *read user input name to set winner
* @return this method returns nothing
 */
   public void winner(){
      System.out.println("Sever --> winner");
      if(player.equals(leftField.getText())){
         yourTurn.setText("  You win"); 
         System.out.println("Sever --> client : You win");
      }
      else{
         yourTurn.setText("  You lose");
         System.out.println("Sever --> client : you lose");
      }
   }
/** 
 *read user input name to set current pairs
* @return this method returns nothing
 */
   public void pairs(){
      System.out.println("Sever --> pairs");
      if(isPlaying){
         int pairs = Integer.parseInt(leftField.getText());
         pair.setText("Pairs: " + pairs);
         System.out.println("Sever --> pairs" + pairs);
      }
      else{
         yourTurn.setText("  It is not your turn");
         System.out.println("Sever --> client : WARNING : It is not your turn");
      }
   }
/** 
 *read user input number of the card to cover card
* @return this method returns nothing
 */
   public void cover(){
      System.out.println("Sever --> cover");
      int cardNum1 = Integer.parseInt(leftField.getText());
      System.out.println("Sever -->  cover " + cardNum1 + " card");
      cardList.get(cardNum1).coverCard();
      if(isPlaying){
         System.out.println("Sever --> Client : player " + player + " covers " + cardNum1 + " card");
      }
      else{
         System.out.println("Client --> Sever : the other player  covers " + cardNum1 + " card");
      }
   }
}