package main;
import java.awt.Robot;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;

import javax.swing.Timer;
import core.Player;
import graphics.Graphics3D;
import io.Input;

public class Main 
{
	static Graphics3D graphicsHandler;
	static Player player;
	static Robot robot;
	
	public static void main(String[] args) 
	{	
		graphicsHandler = new Graphics3D();
		graphicsHandler.getWindow().setSize(1280, 720);
		graphicsHandler.getWindow().setTitle("Game Title");
		graphicsHandler.getWindow().setResizable(false);
		
		graphicsHandler.getCanvas().addKeyListener(new Input.keyHandler());
		graphicsHandler.getCanvas().addMouseListener(new Input.mouseHandler());
		graphicsHandler.getCanvas().addMouseMotionListener(new Input.mouseMovementHandler());
		
		graphicsHandler.getWindow().setLocation(50, 50);
		graphicsHandler.getWindow().addWindowListener(new WindowAdapter() {@Override public void windowClosing(WindowEvent e) { super.windowClosing(e); e.getWindow().dispose(); System.exit(0);}});

		player = new Player(graphicsHandler);
		Input.Register();
		
		GameCode.onGameStart(graphicsHandler, player);
		new Timer(16, new ActionListener(){@Override public void actionPerformed(ActionEvent arg0) {try {
			GameCode.mainGameLoop(graphicsHandler, player);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}}}).start();
	}
}
