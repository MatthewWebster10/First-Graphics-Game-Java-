package io;

import java.util.Vector;

/**
 * This class loads a level file using the FileLoader class that is used to 
 * determine the texture and other attributes of the next level
 * @author Jesse Melia
 * @version 1.0
 */
public class LevelLoader {

	FileLoader loadFile = new FileLoader();
	static Vector<String> fileContents = new Vector<String>();
	int numBlocks;
	int spawnRate;
	
	public LevelLoader () {
		
	}
	
	/**
	 * This method gets the name of the background texture
	 * @param level 
	 * @return name of the background texture
	 */
	public String getBackround(int level){
		fileContents = loadFile.LoadFile("level" + level + ".txt");
	
		return fileContents.get(0);
	}
	
	/**
	 * This method gets the name of the ground texture
	 * @param level
	 * @return name of the ground texture
	 */
	public String getGround(int level){
		fileContents = loadFile.LoadFile("level" + level + ".txt");
		
		return fileContents.get(1);
	}
	
	/**
	 * This method gets the name of the block colour
	 * @param level
	 * @return name of the block colour
	 */
	public String getBlockColour(int level){
		fileContents = loadFile.LoadFile("level" + level + ".txt");
		
		return fileContents.get(2);
	}
	
	/**
	 * This method gets the total number of blocks to spawn in a level
	 * @param level
	 * @return number of blocks to spawn
	 */
	public int getNumBlocks(int level){
		fileContents = loadFile.LoadFile("level" + level + ".txt");
		
		numBlocks = Integer.parseInt(fileContents.get(3));
		
		return numBlocks;
	}
	
	/**
	 * This method gets the spawn rate per second of the blocks
	 * @param level
	 * @return spawn rate per second
	 */
	public int getSpawnRate(int level) {
		fileContents = loadFile.LoadFile("level" + level + ".txt");
		
		spawnRate = Integer.parseInt(fileContents.get(4));
		
		return spawnRate;
	}
	
	
}
