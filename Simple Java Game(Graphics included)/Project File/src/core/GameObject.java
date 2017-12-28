package core;

import javax.media.j3d.Transform3D;
import javax.vecmath.Vector3d;
import com.sun.j3d.utils.geometry.Box;
import com.sun.j3d.utils.geometry.Primitive;

import graphics.BoxObject;

public class GameObject 
{
	private Transform3D transform = new Transform3D();
	private Vector3d position = new Vector3d(0, 0, 0);
	private Vector3d rotation = new Vector3d(0, 0, 0);
	private Vector3d scale = new Vector3d(0, 0, 0);
	private boolean currentColliding = false;
	static double timeAtCollision;
	double changeOfYVelocity;
	double yInitialVelocity = 18.04;
	
	public GameObject() 
	{
		position = new Vector3d(0, 0, 0);
	}
	
	public void move(double x, double y, double z)
	{
		position.x += x;
		position.y += y;
		position.z += z;
		rebuildTransform();
	}
	
	public void rotate(double x, double y, double z)
	{
		rotation.x += x;
		rotation.y += y;
		rotation.z += z;
		rebuildTransform();
	}
	
	public void scale(double x, double y, double z)
	{
		scale.x += x;
		scale.y += y;
		scale.z += z;
		rebuildTransform();
	}
	
	public void setPosition(double x, double y, double z)
	{
		position.x = x;
		position.y = y;
		position.z = z;
		rebuildTransform();
	}
	
	public void setRotation(double x, double y, double z)
	{
		rotation.x = x;
		rotation.y = y;
		rotation.z = z;
		rebuildTransform();
	}
	
	public void setScale(double x, double y, double z)
	{
		scale.x = x;
		scale.y = y;
		scale.z = z;
		rebuildTransform();
	}
	
	public void move(Vector3d toMove)
	{
		position.x += toMove.x;
		position.y += toMove.y;
		position.z += toMove.z;
		rebuildTransform();
	}
	
	public void setPosition(Vector3d newPosition)
	{
		position.x = newPosition.x;
		position.y = newPosition.y;
		position.z = newPosition.z;
		rebuildTransform();
	}
	
	public Vector3d getScale()
	{
		return scale;
	}
	
	private void rebuildTransform()
	{
	
	}
	
	public Transform3D getTransform()
	{
		return transform;
	}
	
	public Vector3d getPosition()
	{
		return position;
	}
	
	public Vector3d getRotation()
	{
		return rotation;
	}
	
	public boolean getIsCurrentlyColliding()
	{
		return currentColliding;
	}
	
	public void setIsCurrentlyColliding(boolean toSet)
	{
		currentColliding = toSet;
	}
	
	/**
	 * This method deals with collision detection
	 * @param enemyObject is the invisible box around enemies that is made to easily detect position
	 * @param characterObject is the invisible box around the player made to easily detect position
	 * @param min is the minimum scalar distance in between the player and enemy that does not cause a collision
	 * @return a boolean for the question: Did the player collide with an enemy. 
	 */
	public static boolean getIsColliding(BoxObject enemyObject, BoxObject characterObject, double min)
	{			
		Vector3d objPosEnemy = enemyObject.getPosition();
		Vector3d objPosPlayer = characterObject.getPosition();
		
		double dist = 0;
		dist = Math.sqrt((Math.pow(objPosEnemy.x - objPosPlayer.x,2))+(Math.pow(objPosEnemy.y - objPosPlayer.y,2))+(Math.pow(objPosEnemy.z - objPosPlayer.z,2)));
		
		if (dist < min){
			timeAtCollision = System.currentTimeMillis();
			return true;
		}
		else
			return false;
	}
	
	/**
	 * This method deals with the event of a collision
	 * @param enemyObject is the invisible box around enemies that is made to easily detect position
	 * @param min is the minimum scalar distance in between the player and enemy that does not cause a collision
	 * @param velocity of the object coming at the player.
	 * @param xAtCollision is the x-coordinate upon collision. 
	 * @param yAtCollision is the y-coordinate upon collision.
	 * @param zAtCollision is the z-coordinate upon collision.
	 */
	public void collision(BoxObject enemyObject, double min, double xAtCollision, double yAtCollision, double zAtCollision, double xVelocity)
	{
		Vector3d objPosEnemy = enemyObject.getPosition();
		
		double time = (System.currentTimeMillis()*60)-timeAtCollision;
		double gravityAccel = 9.81;
		yInitialVelocity = changeOfYVelocity;
		double ChangeOfNewYVelocity = yInitialVelocity - gravityAccel*(time);
		//paraDist = Math.sqrt((Math.pow(xAtCollision-xOrigin,2))+(Math.pow(zAtCollision-zOrigin,2));
		double aDistance = Math.sqrt((Math.pow(-xVelocity*(time)-xAtCollision,2))+Math.pow(ChangeOfNewYVelocity*(time)-yAtCollision, 2));
		// to be called if(getIsColliding(enemyObject, Character.characterObject, min) == true)
		//double xOfnewVector = objPosEnemy.x((aDistance*paraDist)/(Math.sqrt(2)*(xAtCollision+1)));
		//double yofNewVector = ((aDistance*paraDist)/(Math.sqrt(2)*(yAtCollision+1)));

			
		//negative velocity in every direction except z which is constant
		//enemyObject.setPosition(xOfnewVector, yofNewVector, zAtCollision);
		//Change background to red
	}
	
	/**
	 * This method handles the projectile motion of the enemy objects
	 * @param enemyObject is the invisible box around the enemy object
	 * @param xVelocity the constant x velocity at which the enemy object is moving at.
	 * @param timeAtSpawn is the time at which the enemy object is spawned and starts moving. 
	 */
	public void InitialtrajectoryOfEnemyObjects(BoxObject enemyObject, double xVelocity, double timeAtSpawn)
	{
		//y axis movement
		double time = (System.currentTimeMillis()*60)-timeAtSpawn;
		double gravityAccel = 9.81; 
		changeOfYVelocity = yInitialVelocity - gravityAccel*(time);
		
		enemyObject.move(xVelocity, changeOfYVelocity, 0);
	}
}