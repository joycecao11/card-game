import javax.swing.JButton;
import javax.swing.ImageIcon;
/** This is a class for frame to look pictures
 * @author   Xuechun  Cao
 * Student ID Number: 100271794
 * @version 1.0
 * Course: CPSC 1181
 * Section: 002
 */
public class Card extends JButton{
   public static final String DEFAULT = "CPSC 1181";
   private boolean hasBeenRevealed;
   private ImageIcon icon;
   private int cardNum;
   public Card(int i){
      super(new ImageIcon("0.jpg"));
      this.hasBeenRevealed = false;
      cardNum = i;
   }
   public void display(int num){
      if(num == 0){
         this.setIcon(new ImageIcon("0.jpg"));
      }
      else if(num == 1){
         this.setIcon(new ImageIcon("1.jpg"));
      }
      else if(num == 2){
         this.setIcon(new ImageIcon("2.jpg"));
      }
   }
   public void coverCard(){
      this.setIcon(new ImageIcon("0.jpg"));
   }
   public boolean hasBeenRevealed(){
      return hasBeenRevealed;
   }
   public int getIndex(){
      return cardNum;
   }
   public void revealCard(){
      hasBeenRevealed = true;
   }
   public void notRevealCard(){
      hasBeenRevealed = false;
   }
}
   