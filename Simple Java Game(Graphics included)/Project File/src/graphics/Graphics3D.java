package graphics;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.GraphicsConfiguration;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Vector;

import javax.imageio.ImageIO;
import javax.media.j3d.BoundingSphere;
import javax.media.j3d.BranchGroup;
import javax.media.j3d.Canvas3D;
import javax.media.j3d.DirectionalLight;
import javax.media.j3d.MultipleParentException;
import javax.media.j3d.RestrictedAccessException;
import javax.media.j3d.Texture2D;
import javax.media.j3d.Transform3D;
import javax.media.j3d.TransformGroup;
import javax.swing.JFrame;
import javax.swing.Timer;
import javax.vecmath.Color3f;
import javax.vecmath.Point3d;
import javax.vecmath.Vector3d;
import javax.vecmath.Vector3f;
import com.sun.j3d.utils.universe.SimpleUniverse;


public class Graphics3D 
{
	static SimpleUniverse universe;
	static BranchGroup objects;
	static Timer refreshTimer = new Timer(60, null);
	static Vector<GUI> GUIImages = new Vector<GUI>();
	static Vector<BoxObject> boxObjects = new Vector<BoxObject>();
	static Vector<String> stringsToRender = new Vector<String>();
	static Vector<TransformGroup> transforms = new Vector<TransformGroup>();
	static Viewport3D viewport;
	static JFrame frame;
	Texture2D backgroundImage = new Texture2D();
	BufferedImage bgImage = new BufferedImage(1280, 720, BufferedImage.TYPE_INT_ARGB);
	
	public Graphics3D()
	{
		

		frame = new JFrame();
		objects = new BranchGroup();
		objects.setCapability(BranchGroup.ALLOW_DETACH);
		objects.setCapability(BranchGroup.ALLOW_CHILDREN_EXTEND);
		viewport = new Viewport3D(SimpleUniverse.getPreferredConfiguration());
	    universe = new SimpleUniverse(viewport);
		universe.getViewingPlatform().setNominalViewingTransform();
		universe.getViewer().getView().setBackClipDistance(100.0);
		universe.getViewer().getView().setFrontClipDistance(0.1);
		frame.add(viewport);
		frame.setVisible(true);
	}
	
	public JFrame getWindow()
	{
		return frame;
	}
	
	public Canvas3D getCanvas()
	{
		return universe.getCanvas();
	}
	
	public SimpleUniverse getUniverse()
	{
		return universe;
	}
	
	public void AddObject(BoxObject toAdd)
	{
		boxObjects.add(toAdd);
		TransformGroup ObjectTransform = new TransformGroup();
		Transform3D objt = new Transform3D();
		objt.setScale(toAdd.getScale());
		ObjectTransform.setTransform(objt);
		ObjectTransform.addChild(toAdd.getShape());
		ObjectTransform.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);
		ObjectTransform.setCapability(TransformGroup.ALLOW_TRANSFORM_READ);
		try
		{
			objects.addChild(ObjectTransform);	
		} catch(RestrictedAccessException e)
		{
			BranchGroup b = new BranchGroup();
			b.addChild(ObjectTransform);
			objects.addChild(b);
		}
		transforms.add(ObjectTransform);
	}
	
	public void compileObjects()
	{
		universe.addBranchGraph(objects);
	}
	
	public void AddGUIImage(GUI toAdd)
	{
		GUIImages.add(toAdd);
	}
	
	public void AddGUIButton(Button toAdd)
	{
		GUIImages.add(toAdd);
	}
	
	public void Repaint()
	{
		universe.getCanvas().getView().repaint();		
	}
	
	public void HideMouse()
	{
		BufferedImage blankImg = new BufferedImage(16, 16, BufferedImage.TYPE_INT_ARGB);
		Cursor blankCursor = Toolkit.getDefaultToolkit().createCustomCursor(blankImg, new Point(0, 0), "blank");
				
		getCanvas().setCursor(blankCursor);
	}
	
	public void ShowMouse()
	{
		getCanvas().setCursor(Cursor.getDefaultCursor());
	}
	
	public void setBackgroundImage(String path)
	{
		try 
		{
			bgImage = ImageIO.read(new File(path));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void clearGUI()
	{
		GUIImages.clear();
		stringsToRender.clear();
	}
	
	public void clearScene()
	{
		clearGUI();
		objects.detach();
		objects = new BranchGroup();
		objects.setCapability(BranchGroup.ALLOW_DETACH);
		objects.setCapability(BranchGroup.ALLOW_CHILDREN_EXTEND);
		boxObjects = new Vector<BoxObject>();
		transforms = new Vector<TransformGroup>();
	}
	
	public void moveObject(String name, Vector3d MoveAmount)
	{
		for(int i = 0; i < boxObjects.size(); i++)
		{
			if(boxObjects.get(i).getName().equals(name))
			{
				if(boxObjects.get(i).getPosition().x + MoveAmount.x < 8 && boxObjects.get(i).getPosition().x + MoveAmount.x > -8)
				{
					boxObjects.get(i).move(MoveAmount.x, MoveAmount.y, MoveAmount.z);
					
					Transform3D transform = new Transform3D();
					transform.setTranslation(MoveAmount);
					Transform3D transformOriginal = new Transform3D();
					transforms.get(i).getTransform(transformOriginal);
					transform.mul(transformOriginal);
					transforms.get(i).setTransform(transform);
				}
			}
		}
		
	}
	
	public void setObjectPosition(String name, Vector3d newPosition)
	{
		for(int i = 0; i < boxObjects.size(); i++)
		{
			if(boxObjects.get(i).getName().equals(name))
			{
				Transform3D transform = new Transform3D();
				transform.setTranslation(newPosition);
				transforms.get(i).setTransform(transform);
				boxObjects.get(i).setPosition(newPosition);
			}
		}
		
	}
	
	public void addDirectionalLight(double x, double y, double z, Color lightColor)
	{
		DirectionalLight lightToAdd = new DirectionalLight();
		lightToAdd.setDirection(new Vector3f((float)x, (float)y, (float)z));
		lightToAdd.setColor(new Color3f(lightColor));
		lightToAdd.setInfluencingBounds(new BoundingSphere(new Point3d(0.0, 0.0, 0.0), 100.0));
		objects.addChild(lightToAdd);
	}
	
	public void addString(String toAdd)
	{
		stringsToRender.add(toAdd);
	}
	
	public class Viewport3D extends Canvas3D   
	{
        public Viewport3D(GraphicsConfiguration arg0) 
        {
			super(arg0);
		}

		private static final long serialVersionUID = 7144426579917281131L;
		
		@Override
		public void preRender()
		{
			this.getGraphics2D().drawImage(bgImage, 0, 0, null);
			this.getGraphics2D().flush(false);
		}
        
        public void postRender()
        {
        	for(int i = 0; i < GUIImages.size(); i++)
    		{
        		if(GUIImages.get(i).getIsVisible())
        		{
        			this.getGraphics2D().drawImage(GUIImages.get(i).getImage(), (int)GUIImages.get(i).getPosition().x, (int)GUIImages.get(i).getPosition().y, null);
        		}
        	}
        	
        	for(int i = 0; i < stringsToRender.size(); i++)
        	{
        		this.getGraphics2D().setColor(Color.BLUE);
        		this.getGraphics2D().setFont(new Font("Arial", Font.PLAIN, 36));
        		this.getGraphics2D().drawString(stringsToRender.get(i), 0, 200);
        	}
        	
			this.getGraphics2D().flush(false);
        }
    };
}
