package io;

import java.awt.Color;
import java.util.Vector;

import javax.vecmath.Vector3d;

import core.Player;
import graphics.BoxObject;
import graphics.Graphics3D;

/**
 * This class is called when the level is created
 * @author Jesse Melia
 * @version 1.0
 */
public class createLevel {

	LevelLoader levelAttributes;
	public createLevel() {
		
	}
	
	/**
	 * Draws the level gathering texture names using LevelLoader class
	 * @param level
	 * @param graphicsHandler
	 * @param player
	 */
	public void drawLevel(int level, Graphics3D graphicsHandler, Vector<BoxObject> blocks) {
		
		//clear the gui
		graphicsHandler.clearGUI();
		graphicsHandler.clearScene();
		
		levelAttributes = new LevelLoader();
		
		//create ground
		BoxObject ground = new BoxObject(new Vector3d(0.0, 0.0, 0.0), new Vector3d(0.0, 0.0, 0.0), new Vector3d(10.0, 1.0, 1.0), "Ground");
		
		//set ground material
		ground.setMaterial("Images//" + levelAttributes.getGround(level) + ".jpg");
		
		graphicsHandler.AddObject(ground);
		
		//set background image
		graphicsHandler.setBackgroundImage("Images//" + levelAttributes.getBackround(level) + ".jpg");
		
		for(int  i = 0; i < levelAttributes.getSpawnRate(level); i++)
		{
			BoxObject bObject = new BoxObject("Enemy"+i);
			bObject.setMaterial("Images//" + levelAttributes.getBlockColour(level) + ".jpg");
			graphicsHandler.AddObject(bObject);
			graphicsHandler.setObjectPosition("Enemy" + i, new Vector3d((Math.random() * 20) - 10, 2, (Math.random() * 10) - 40));
			blocks.add(bObject);			
		}		
	}
}
