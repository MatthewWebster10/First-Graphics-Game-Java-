package io;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

/**
 * This class is called when the user loads a save file at the beginning of the game
 * it is used to return the score and level at last save.
 * @author Jesse Melia
 * @version 1.0
 */
public class ReadSaveFile {

	String getSaveTime;
	int getLevel;
	int getScore;
	public static String[] saveFileContents = new String[10];
	
	/**
	 * This calls the method to read the save file.
	 * @param saveSlot The slot to find player information
	 */
	public ReadSaveFile (int saveSlot) {
		ReadSave(saveSlot);
	}
	
	/**
	 * This calls the method to read the save file, and returns an array
	 * containing the information.
	 * @param saveSlot
	 * @return
	 */
	public String[] ReadSave (int saveSlot) {
		if (saveSlot == 1){
			try{
				readFile("saveSlot1.txt");
			}catch(IOException x)
			{
				System.out.println("Something went wrong");
				System.out.println(x);
			}
		}
		else if (saveSlot == 2) {
			try{
				readFile("saveSlot2.txt");
			}catch(IOException x)
			{
				System.out.println("Something went wrong");
				System.out.println(x);
			}
		}
		else if (saveSlot == 3) {
			try{
				readFile("saveSlot3.txt");
			}catch(IOException x)
			{
				System.out.println("Something went wrong");
				System.out.println(x);
			}
		}
		return saveFileContents;
	}
	
	/**
	 * This method reads player information and stores it in an array.
	 * @param fileName The name of the file to be read.
	 * @throws IOException
	 */
	public static void readFile (String fileName) throws IOException{
		//use "." to get current directory
		File dir = new File(".");
		File fin = new File(dir.getCanonicalPath() + File.separator +  fileName);
				
		//construct bufferedReader from fileReader
		BufferedReader br = new BufferedReader(new FileReader(fin));
				
		String line = null;
		int i = 0;
		while((line = br.readLine()) != null){
			saveFileContents[i] = line;
			i++;
		}
			
		br.close();
	}
	
	/**
	 * This method gets the level that the player saved on
	 * @param saveSlot
	 * @return level that the player was on at last save
	 */
	public int getLevel(int saveSlot) {
		
		saveFileContents = ReadSave(saveSlot);
		
		getLevel = Integer.parseInt(saveFileContents[0]);
		
		return getLevel;
		
	}
	
	/**
	 * This method gets the score that the player had at last save
	 * @param saveSlot
	 * @return score the player had at last save
	 */
	public int getScore(int saveSlot){
		
		saveFileContents = ReadSave(saveSlot);
		
		getScore = Integer.parseInt(saveFileContents[1]);
		
		return getScore;
	}
	
	/**
	 * This method gets the time that the player saved the game
	 * @param saveSlot
	 * @return time of last save
	 */
	public String getSaveTime(int saveSlot){
		
		saveFileContents = ReadSave(saveSlot);
		
		getSaveTime = saveFileContents[2];
		
		return getSaveTime;
	}
}

