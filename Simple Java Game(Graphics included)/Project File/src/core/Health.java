package core;

/**
 * Gets health and checks to see if dead
 * @author panth
 *@version 1.0
 */
public class Health {

	
	public int lives = 3;
	
	public boolean damage(){
		//Update once you have all the variables
		double timeAtCollision = System.currentTimeMillis()*60;
		//if ()
			--lives;
		return isDead();
	}
	public boolean isDead(){
		return lives < 1;
	}
}
