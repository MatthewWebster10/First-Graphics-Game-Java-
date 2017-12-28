package io;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

/**
 * This class saves player information to one of 3 save files to be accessed later.
 * @author Jesse
 * @version 1.0
 */
public class SaveFileIO {

	/**
	 * This method recieves player information when it is called and calls a method
	 * to write this information to the corresponding file
	 * @param saveSlot The slot that the information is being saved in.
	 * @param level The level the player is at.
	 * @param score The score the player has.
	 */
	public SaveFileIO (int saveSlot, int level, int score) {
		
		if(saveSlot == 1){
			try{
				saveFile1(level, score);
			}catch(IOException x)
			{
				System.out.println("Something went wrong when saving file 1.");
			}
		}
		else if (saveSlot == 2) {
			try{
				saveFile2(level, score);
			}catch(IOException x)
			{
				System.out.println("Something went wrong when saving file 2.");
			}
		}
		else if (saveSlot == 3) {
			try{
				saveFile3(level, score);
			}catch(IOException x)
			{
				System.out.println("Something went wrong when saving file 3.");
			}
		}
	}
	
	/**
	 * This writes save information for save slot 1
	 * @param level
	 * @param score
	 * @throws IOException
	 */
	public static void saveFile1 (int level, int score) throws IOException {
		
		PrintWriter pw = new PrintWriter(new FileWriter("saveSlot1.txt", false));
		
		DateFormat df = new SimpleDateFormat("dd/MM/yy HH:mm:ss");
	    Date dateobj = new Date();
	    
	    pw.println(level);
	    pw.println(score);
	    pw.println(df.format(dateobj));
		pw.close();
	}
	
	/**
	 * This writes save information for save slot 2.
	 * @param level
	 * @param score
	 * @throws IOException
	 */
	public static void saveFile2 (int level, int score) throws IOException {
		
		PrintWriter pw = new PrintWriter(new FileWriter("saveSlot2.txt", false));
		
		DateFormat df = new SimpleDateFormat("dd/MM/yy HH:mm:ss");
	    Date dateobj = new Date();
	    
	    pw.println(level);
	    pw.println(score);
	    pw.println(df.format(dateobj));
		pw.close();
	}
	
	/**
	 * This writes save information for save slot 3.
	 * @param level
	 * @param score
	 * @throws IOException
	 */
	public static void saveFile3 (int level, int score) throws IOException {
		
		PrintWriter pw = new PrintWriter(new FileWriter("saveSlot3.txt", false));
		
		DateFormat df = new SimpleDateFormat("dd/MM/yy HH:mm:ss");
	    Date dateobj = new Date();
	    
	    pw.println(level);
	    pw.println(score);
	    pw.println(df.format(dateobj));
		pw.close();
	}
}
