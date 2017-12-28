package graphics;

import java.awt.Point;

public class Button extends GUI 
{
	public Button(String path) 
	{
		super(path);
	}
	
	public boolean getIsClicked(Point mousePosition, boolean mouseIsClicked)
	{
		if(mousePosition.getX() > getPosition().getX() && mousePosition.getX() < (getPosition().getX() + getImage().getWidth()))
		{
			if(mousePosition.getY() > getPosition().getY() && mousePosition.getY() < (getPosition().getY() + getImage().getHeight()))
			{
				if(mouseIsClicked)
				{
					return true;
				}
			}
		}
		
		return false;
	}
}
