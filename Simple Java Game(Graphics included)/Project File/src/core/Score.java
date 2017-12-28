package core;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
/**
 * This gets the score and compares it to the highscore
 * The score tell you how much you got
 * @author Panth
 * @version 1.5
 * 
 */
public class Score {
	public double score;
	public String highScore = "";
	public double startTime;
	public double endTime;
	
	//gets the start Time
	public double getStartTime(){
		startTime = System.currentTimeMillis()*1000;
		return startTime;
	}
	//gets the end time
	public double getEndTime(){
		endTime = System.currentTimeMillis();
		return endTime;
	}
	//Gets the score
	public double getScore(){
		score  =  Math.max(0, 20000 - ((endTime - startTime) / 100000));
		return score;
	}
	//Initializes HighScore here
	public void UpdateHighScore(){
		if (highScore.equals("")){
			highScore = this.GetHighScore();
		}
	}
	
	//Gets high Score
	public String GetHighScore(){
		FileReader readFile = null;
		BufferedReader reader = null;
		try {
			readFile = new FileReader("highscore.dat");
			reader = new BufferedReader(readFile);
			return reader.readLine();
		} catch (Exception e) {
			return "0";
		}
		finally{
			try {
				reader.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}
