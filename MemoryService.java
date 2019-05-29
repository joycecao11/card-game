import java.io.*;
import java.net.*;
import java.util.*;
import java.awt.*;
import javax.swing.*;

public class MemoryService implements MemoryConstants, Runnable{
	private Socket player1;
	private DataInputStream fromClient1;
	private DataOutputStream toClient1;
	private JTextArea textArea;
	private int playerNum;
	private int[] cardNum;
	
	public MemoryService(Socket s1, JTextArea ta, int number){
		this.player1 = s1;
		this.textArea = ta;
		this.playerNum = number;
		int[] original = {1,1,2,2,3,3};
		Random r = new Random();
		int index = 0;
		while(index < row*column){
			int x = r.nextInt(6);
			if(cardNum[x] == 0){
				cardNum[x] = original[index];
				index ++;
			}
		}
		for(int i = 0; i < row*column; i ++){
			System.out.print(cardNum[i] + " ");
		}
	}
	private void report(String msg){
		textArea.append(msg + "\n");
	}
	public void run(){
		try{
			try{
				fromClient1 = new DataInputStream(player1.getInputStream());
				toClient1 = new DataOutputStream(player1.getOutputStream());
				executeCmds();
			}
			finally{
				player1.close();
			}
		}
		catch(IOException e){
			report(e.getCause().getMessage() + "\n");
		}
	}
	public void executeCmds() throws IOException{
		int cmd;
		boolean done = false;
		while(!done){
			cmd = fromClient1.readInt();
			report(cmdToString(cmd));
			if(cmd == QUIT){
				report("Client -> Server : Server receives QUIT");
				toClient1.write(DONE);
				toClient1.flush();
			}
		}
	}
}