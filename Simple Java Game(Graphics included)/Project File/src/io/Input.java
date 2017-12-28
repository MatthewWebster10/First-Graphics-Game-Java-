package io;
import java.awt.AWTException;
import java.awt.Point;
import java.awt.Robot;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

public class Input 
{
	private static boolean[] keysDown = new boolean[256];
	private static boolean leftMouseButtonPressed, rightMouseButtonPressed, middleMouseButtonPressed;
	private static Point mousePos = new Point(0, 0);
	private static Point mouseDeltaPos = new Point(0, 0);
	private static Point mousePosOld = new Point(0, 0);
	private static Robot robot;
	private static boolean mouseIsLocked = false;
	
	public static void Register()
	{
		try 
		{
			robot = new Robot();
		} catch (AWTException e) 
		{
			e.printStackTrace();
		}
		
		robot.mouseMove(400, 400);
	}
	
	public static class keyHandler implements KeyListener
	{
		@Override
		public void keyPressed(KeyEvent e) 
		{	
			if((int)e.getKeyChar() <= 256)
			{
				keysDown[(int)e.getKeyChar()] = true;
		
			}
		}

		@Override
		public void keyReleased(KeyEvent e) 
		{
			if((int)e.getKeyChar() <= 256)
			{
				keysDown[(int)e.getKeyChar()] = false;
			}
		}

		@Override
		public void keyTyped(KeyEvent e) 
		{	
			
		}		
	}
	
	public static class mouseHandler implements MouseListener
	{
		@Override
		public void mouseClicked(MouseEvent arg0) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void mouseEntered(MouseEvent arg0) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void mouseExited(MouseEvent arg0) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void mousePressed(MouseEvent arg0) 
		{
			switch(arg0.getButton())
			{
			case 1:
				leftMouseButtonPressed = true;
				break;
			case 2:
				rightMouseButtonPressed = true;
				break;
			case 3:
				middleMouseButtonPressed = true;
				break;
			default:
				break;
			}	
		}

		@Override
		public void mouseReleased(MouseEvent arg0) 
		{
			switch(arg0.getButton())
			{
			case 1:
				leftMouseButtonPressed = false;
				break;
			case 2:
				rightMouseButtonPressed = false;
				break;
			case 3:
				middleMouseButtonPressed = false;
				break;
			default:
				break;
			}		
		}
	}
	
	public static class mouseMovementHandler implements MouseMotionListener
	{
		@Override
		public void mouseDragged(MouseEvent arg0) 
		{
			
		}

		@Override
		public void mouseMoved(MouseEvent arg0) 
		{
			mouseDeltaPos = new Point((int)(arg0.getXOnScreen() - mousePosOld.getX()),(int)(arg0.getYOnScreen() - mousePosOld.getY()));
			mousePosOld = mousePos;
			mousePos = new Point(arg0.getXOnScreen(), arg0.getYOnScreen());
		}
	}
	
	public static boolean IsKeyDown(char keyToCheck)
	{
		if(keysDown[(int)keyToCheck] == true)
		{
			return true;
		}
		
		return false;
	}
	
	public static void SetKeyDown(char key, boolean toSet)
	{
		keysDown[(int)key] = toSet;
	}
	
	public static void Update()
	{
		mouseDeltaPos = new Point(0, 0);
		if(mouseIsLocked)
		{
			robot.mouseMove(400, 400);
		}
	}
	
	public static void lockMouse()
	{
		mouseIsLocked = true;
		robot.mouseMove(400, 400);
	}
	
	public static void unlockMouse()
	{
		mouseIsLocked = false;
	}
	
	public static boolean IsLeftMouseButtonDown()
	{
		return leftMouseButtonPressed;
	}
	
	public static boolean IsRightMouseButtonDown()
	{
		return rightMouseButtonPressed;
	}
	
	public static boolean IsMiddleMouseButtonDown()
	{
		return middleMouseButtonPressed;
	}
	
	public static Point GetMousePosition()
	{
		return mousePos;
	}
	
	public static Point GetMouseDeltaPosition()
	{
		return mouseDeltaPos;
	}
}
