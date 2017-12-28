package core;
import javax.vecmath.Vector3d;

import graphics.BoxObject;
import graphics.Camera;
import graphics.Graphics3D;

public class Player extends Character 
{
	private Camera playerCamera = new Camera();
	private Graphics3D graphicsHandler;
	
	public Player()
	{
		
	}
	
	public Player(Graphics3D graphicsHandlerIn)
	{
		playerCamera.setUniverse(graphicsHandlerIn.getUniverse());
		graphicsHandler = graphicsHandlerIn;
		update();
	}
	
	public void addToScene()
	{
		characterObject = new BoxObject(new Vector3d(0, 10, 0), new Vector3d(0, 0, 0), new Vector3d(1.0, 1.0, 1.0), "Character");
		characterObject.setMaterial("Images//TestImage.png");
		graphicsHandler.AddObject(characterObject);
		graphicsHandler.setObjectPosition("Character", new Vector3d(0, 1, 0));
		playerCamera.SetPosition(new Vector3d(0, 0, 0));
	}
	
	public BoxObject getCharacterObject()
	{
		return characterObject;
	}
	
	@Override
	public void move(double x, double y, double z)
	{
		playerCamera.move(x, y, z);
		graphicsHandler.moveObject("Character", new Vector3d(x * 0.25, y * 0.25, z * 0.25));
	}
	
	public void update()
	{
		playerCamera.SetRotation(getRotation().y);
		playerCamera.Update();
	}
}
