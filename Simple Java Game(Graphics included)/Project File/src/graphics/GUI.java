package graphics;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.vecmath.Vector2d;

public class GUI 
{
	private boolean isVisible = true;
	private BufferedImage image;
	private Vector2d position = new Vector2d(0, 0);
	
	public GUI(String path)
	{
		loadImage(path);
	}
	
	public void loadImage(String path)
	{
		try
		{
			image = ImageIO.read(new File(path));
		} catch(IOException e) {
			System.out.println("Could not load GUI image: " + path);
			e.printStackTrace();
		}
	}
	
	public void SetVisible(boolean toSet)
	{
		isVisible = toSet;
	}
	
	public void setPosition(int x, int y)
	{
		position = new Vector2d(x, y);
	}
	
	public Vector2d getPosition()
	{
		return position;
	}
	
	public boolean getIsVisible()
	{
		return isVisible;
	}
	
	public BufferedImage getImage()
	{
		return image;
	}
}
