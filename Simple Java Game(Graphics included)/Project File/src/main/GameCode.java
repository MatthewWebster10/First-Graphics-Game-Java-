package main;

import java.awt.Color;
import java.io.IOException;
import java.util.Vector;

import javax.vecmath.Vector3d;

import core.Health;
import core.Player;
import graphics.BoxObject;
import graphics.Button;
import graphics.GUI;
import graphics.Graphics3D;
import io.Input;
import io.LevelLoader;
import io.ReadSaveFile;
import io.SaveFileIO;
import io.createLevel;
import core.Score;
import core.GameObject;

/**
 * This class contains the code that defines the behavior of the game itself, as opposed to the framework.
 * The mainGameLoop() function is run 60 times a second and executes all the game logic
 * @author David
 * @version 1.0
 */
public class GameCode 
{
	public static boolean tutorialShown = false;
	public static int onGameLevel = 1;
	public static int gameState = 0; //The state of the game. 0 = on startup. 1 = playing. 2 = paused. 3 = on level completion 4 = on exit
	public static Button gameStartButton = new Button("Images//NewGame.jpg"); //The button that will be shown at the start of the game
	public static Button nextButton = new Button("Images//Next.jpg"); //The button that will be shown at the start of the game
	public static Button loadGameButton = new Button("Images//LoadGame.jpg"); //The button that will be shown at the start of the game
	public static Button saveGameButton = new Button("Images//SaveGame.jpg"); //The button that will be shown at the start of the game
	public static Vector<BoxObject> enemyBlocks = new Vector<BoxObject>(); //A list of the enemy blocks for easy tracking and manipulation
	public static Score score = new Score(); //Score tracker for the player
	public static double levelStartTime = 0; //Time when the level was started
	public static Health health = new Health(); //Health tracker for the player's health
	public static LevelLoader l = new LevelLoader(); //Level loader to access level loading functionality
	public static GUI LiveCount = new GUI ("Images//red_heart.png"); //Image of the first heart
	public static GUI LiveCount2 = new GUI ("Images//red_heart.png"); //Image of the second heart
	public static GUI LiveCount3 = new GUI ("Images//red_heart.png"); //Image of the third heart
	public static boolean currentlyIsColliding = false; //Keep track of whether the player is currently colliding with an object to prevent double collision
	private static double lastCheckTime = 0; //Time variable for use during timed block spawning.
	
	/**
	 * This function is run when the game is first started up. 
	 * It will do all the setup needed to make the game work, as well as load the first level
	 * Note: We should probably put level loading in a separate function
	 * @param graphicsHandler
	 * @param player
	 */
	public static void onGameStart(Graphics3D graphicsHandler, Player player)
	{
		/**
		 * BEGIN SAMPLE LEVEL
		 */
		
		//Load and draw the first level of the game
		createLevel levelLoader = new createLevel();
		levelLoader.drawLevel(1, graphicsHandler, enemyBlocks);
		
		//Add the player to the scene
		player.addToScene();
		
		//This image is shown when the game starts up
		GUI gameStartImage = new GUI("Images//Title.jpg");
		
		//Add the image to the scene so that it is actually drawn
		graphicsHandler.AddGUIImage(gameStartImage);
		
		//Add a button to the scene, so that we can start the game when it is clicked
		graphicsHandler.AddGUIButton(gameStartButton);
		
		//Set the position of the button to be more appropriate
		gameStartButton.setPosition(500, 350);
		
		//Add the load game button to the start screen
		graphicsHandler.AddGUIButton(loadGameButton);
		loadGameButton.setPosition(500, 500);
		
		//Update the level start time
		levelStartTime = System.currentTimeMillis();
		
		//Compile the scene
		graphicsHandler.compileObjects();
		
		//Update the score's start time
		score.getStartTime();
		
		/**
		 * END SAMPLE LEVEL
		 */
	}
	
	/**
	 * This function is run 60 times each second and contains the main game code to be run.
	 * This includes things like updating the player, enemies, etc.
	 * @param graphicsHandler
	 * @param player
	 * @throws IOException 
	 */
	public static void mainGameLoop(Graphics3D graphicsHandler, Player player) throws IOException
	{
		//Redraw the graphics each frame
		graphicsHandler.Repaint();
		
		//This updates the player's position, rotation etc.
		player.update();
		
		//Rotate the player when the mouse moves. This is currently turned off
		player.rotate(0, Input.GetMouseDeltaPosition().getX() / 1000, 0);
		
		//Update the input class. This checks which keys are pressed etc.
		Input.Update();
		
		if(gameState == 0)
		{
			//Check if the player is clicking on the start button. The first argument is mouse position, second is whether it is being clicked
			if(gameStartButton.getIsClicked(Input.GetMousePosition(), Input.IsLeftMouseButtonDown()))
			{
				if(!tutorialShown)
				{
					//Show the tutorial if it was not shown before
					graphicsHandler.clearGUI();
					GUI tutorialImage = new GUI("Images//Tutorial.jpg");
					graphicsHandler.AddGUIImage(tutorialImage);
					nextButton.setPosition(800, 200);
					graphicsHandler.AddGUIButton(nextButton);
					tutorialShown = true;
				} 
				else
				{
					//Change the gamestate to be 1(playing).
					gameState = 1;
					//Remove all the existing GUI images from the scene
					graphicsHandler.clearGUI();
					//Hide the mouse cursor(more polished look)
					graphicsHandler.HideMouse();
					//Lock the mouse cursor in place. This will prevent it from going outside the window when you look around
					Input.lockMouse();
				

					//Sets heart points
					LiveCount.setPosition(200, 0);
					graphicsHandler.AddGUIImage(LiveCount);

					LiveCount2.setPosition(100, 0);
					graphicsHandler.AddGUIImage(LiveCount2);

					LiveCount3.setPosition(0, 0);
					graphicsHandler.AddGUIImage(LiveCount3);
				
					score.getStartTime();
					lastCheckTime = System.currentTimeMillis();
					levelStartTime = System.currentTimeMillis();
				}
			}
			
			if(loadGameButton.getIsClicked(Input.GetMousePosition(), Input.IsLeftMouseButtonDown()))
			{
				ReadSaveFile saveFileReader = new ReadSaveFile(1);
				createLevel levelLoader = new createLevel();
				levelLoader.drawLevel(saveFileReader.getLevel(1), graphicsHandler, enemyBlocks);
				player.addToScene();
				graphicsHandler.compileObjects();
			}
			
			if(nextButton.getIsClicked(Input.GetMousePosition(), Input.IsLeftMouseButtonDown()))
			{
				//Change the gamestate to be 1(playing).
				gameState = 1;
				//Remove all the existing GUI images from the scene
				graphicsHandler.clearGUI();
				//Hide the mouse cursor(more polished look)
				graphicsHandler.HideMouse();
				//Lock the mouse cursor in place. This will prevent it from going outside the window when you look around
				Input.lockMouse();
				
				tutorialShown = true;

				//Sets heart points
				LiveCount.setPosition(200, 0);
				graphicsHandler.AddGUIImage(LiveCount);

				LiveCount2.setPosition(100, 0);
				graphicsHandler.AddGUIImage(LiveCount2);

				LiveCount3.setPosition(0, 0);
				graphicsHandler.AddGUIImage(LiveCount3);

				score.getStartTime();
			}
		}
		
		if(gameState == 1)
		{
			//Use the 'a' and 'd' keys to move left and right
			if(Input.IsKeyDown('a'))
			{
				player.move(-1, 0, 0);
			}
		
			if(Input.IsKeyDown('d'))
			{
				player.move(1, 0, 0);
			}
			
			if(Input.IsKeyDown('p'))
			{
				GUI Paused = new GUI("Images//Paused.jpg");
				saveGameButton.setPosition(800, 100);
				loadGameButton.setPosition(800, 400);
				graphicsHandler.AddGUIImage(Paused);
				graphicsHandler.AddGUIButton(saveGameButton);
				graphicsHandler.AddGUIButton(loadGameButton);
				graphicsHandler.ShowMouse();
				Input.unlockMouse();
				Input.SetKeyDown('p', false);
				gameState = 2;
			}
			
			for(int i = 0; i < enemyBlocks.size(); i++)
			{
				//Move each cube object 
				graphicsHandler.moveObject(enemyBlocks.get(i).getName(), new Vector3d(0, 0, 0.5));
				
				if(GameObject.getIsColliding(enemyBlocks.get(i), player.getCharacterObject(), 2))
				{
					if(!currentlyIsColliding)
					{
						health.damage();
					
						if(health.isDead())
						{
							gameState = 4;
							graphicsHandler.clearGUI();
							graphicsHandler.AddGUIImage(new GUI("Images//GameLost.jpg"));
						}
						
						if(LiveCount.getIsVisible())
						{
							LiveCount.SetVisible(false);
						} else if(LiveCount2.getIsVisible())
						{
							LiveCount2.SetVisible(false);
						} else if(LiveCount3.getIsVisible())
						{
							LiveCount3.SetVisible(false);
						}
						
						currentlyIsColliding = true;
					}
				} else {
					currentlyIsColliding = false;
				}
			}
			
			if((System.currentTimeMillis() - lastCheckTime) / 1000 > 1)
			{
				for(int  i = 0; i < l.getSpawnRate(onGameLevel); i++)
				{
					BoxObject bObject = new BoxObject("Enemy"+(enemyBlocks.size() + 1));
					bObject.setMaterial("Images//" + l.getBlockColour(onGameLevel) + ".jpg");
					graphicsHandler.AddObject(bObject);
					graphicsHandler.setObjectPosition("Enemy" + (enemyBlocks.size() + 1), new Vector3d((Math.random() * 20) - 10, 2, (Math.random() * 10) - 40));
					enemyBlocks.add(bObject);
				}
				
				lastCheckTime = System.currentTimeMillis();
			}
			
			if(System.currentTimeMillis() - levelStartTime >= (l.getNumBlocks(onGameLevel) * l.getSpawnRate(onGameLevel) * 1000))
			{
				onGameLevel++;
				
				if(onGameLevel == 5)
				{
					gameState = 4;
					graphicsHandler.clearScene();
					graphicsHandler.clearGUI();
					graphicsHandler.AddGUIImage(new GUI("Images//GameWon.jpg"));
				} else {
					gameState = 3;
					graphicsHandler.clearGUI();
					graphicsHandler.AddGUIImage(new GUI("Images//LevelComplete.jpg"));
					graphicsHandler.AddGUIButton(nextButton);
					nextButton.setPosition(500, 500);
					graphicsHandler.ShowMouse();
					Input.unlockMouse();
					score.getEndTime();
					graphicsHandler.addString("Score: " + score.getScore());
				}
			}
		}
		
		if(gameState == 2)
		{
			if(Input.IsKeyDown('p'))
			{
				graphicsHandler.clearGUI();
				Input.lockMouse();
				graphicsHandler.HideMouse();
				Input.SetKeyDown('p', false);
				gameState = 1;
				
				GUI LiveCount = new GUI ("Images//red_heart.png");
				GUI LiveCount2 = new GUI ("Images//red_heart.png");
				GUI LiveCount3 = new GUI ("Images//red_heart.png");

				//Sets heart points
				LiveCount.setPosition(200, 0);
				graphicsHandler.AddGUIImage(LiveCount);

				LiveCount2.setPosition(100, 0);
				graphicsHandler.AddGUIImage(LiveCount2);

				LiveCount3.setPosition(0, 0);
				graphicsHandler.AddGUIImage(LiveCount3);
			}
			
			if(saveGameButton.getIsClicked(Input.GetMousePosition(), Input.IsLeftMouseButtonDown()))
			{
				GUI gameSavedImage = new GUI("Images//GameSaved.jpg");
				gameSavedImage.setPosition(800, 100);
				graphicsHandler.AddGUIImage(gameSavedImage);
				score.getEndTime();
				SaveFileIO.saveFile1(onGameLevel, (int)score.getScore());
			}
			
			if(loadGameButton.getIsClicked(Input.GetMousePosition(), Input.IsLeftMouseButtonDown()))
			{
				ReadSaveFile saveFileReader = new ReadSaveFile(1);
				createLevel levelLoader = new createLevel();
				levelLoader.drawLevel(saveFileReader.getLevel(1), graphicsHandler, enemyBlocks);
				player.addToScene();
				graphicsHandler.compileObjects();
				
				GUI LiveCount = new GUI ("Images//red_heart.png");
				GUI LiveCount2 = new GUI ("Images//red_heart.png");
				GUI LiveCount3 = new GUI ("Images//red_heart.png");

				//Sets heart points
				LiveCount.setPosition(200, 0);
				graphicsHandler.AddGUIImage(LiveCount);

				LiveCount2.setPosition(100, 0);
				graphicsHandler.AddGUIImage(LiveCount2);

				LiveCount3.setPosition(0, 0);
				graphicsHandler.AddGUIImage(LiveCount3);
				
				levelStartTime = System.currentTimeMillis();
				lastCheckTime = System.currentTimeMillis();
				onGameLevel = saveFileReader.getLevel(1);
				gameState = 1;
			}
		}
		
		if(gameState == 3)
		{
			if(nextButton.getIsClicked(Input.GetMousePosition(), Input.IsLeftMouseButtonDown()))
			{
				createLevel levelLoader = new createLevel();
				enemyBlocks.clear();
				levelLoader.drawLevel(onGameLevel, graphicsHandler, enemyBlocks);
				player.addToScene();
				graphicsHandler.compileObjects();
				GUI LiveCount = new GUI ("Images//red_heart.png");
				GUI LiveCount2 = new GUI ("Images//red_heart.png");
				GUI LiveCount3 = new GUI ("Images//red_heart.png");

				//Sets heart points
				if(LiveCount.getIsVisible())
				{
					LiveCount.setPosition(200, 0);
					graphicsHandler.AddGUIImage(LiveCount);
				}
				
				if(LiveCount2.getIsVisible())
				{
					LiveCount2.setPosition(100, 0);
					graphicsHandler.AddGUIImage(LiveCount2);
				}
				
				if(LiveCount3.getIsVisible())
				{
					LiveCount3.setPosition(0, 0);
					graphicsHandler.AddGUIImage(LiveCount3);
				}
				
				levelStartTime = System.currentTimeMillis();
				lastCheckTime = System.currentTimeMillis();
				gameState = 1;
			}
		}
		
		//If the 'q' key is pressed, quit the game
		if(Input.IsKeyDown('q'))
		{
			System.exit(0);
		}
	}
}
