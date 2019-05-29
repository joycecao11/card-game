/** This is a costume Exception class extend Exception class
 * @author   Xuechun  Cao
 * Student ID Number: 100271794
 * @version 1.0
 * Course: CPSC 1181
 * Section: 002
 */
public class PlayingException extends Exception{
/* constructor
 *  construct an exception with the name of Exception class
*/   
   public PlayingException(){ 
      super("PlayingException"); 
   }
/* constructor
 *  construct an exception with the given String
* @param msg   the String that will be shown if such exception occur
*/
   public PlayingException(String msg){ 
      super(msg); 
   }
}