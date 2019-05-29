import javax.swing.JFrame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;
import java.util.*;
import java.awt.*;
/** This is a class to display GUI
 * @author   Xuechun  Cao
 * Student ID Number: 100271794
 * @version 1.0
 * Course: CPSC 1181
 * Section: 002
 */
public class PlayCardViewer{
   public static void main(String[] args){
      JFrame cardFrame = new PlayCardFrame();
   
      cardFrame.setTitle("Command");
      cardFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      cardFrame.setVisible(true); 
   }
}