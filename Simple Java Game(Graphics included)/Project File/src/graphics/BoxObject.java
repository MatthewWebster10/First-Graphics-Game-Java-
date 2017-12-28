package graphics;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.media.j3d.Appearance;
import javax.media.j3d.Material;
import javax.media.j3d.Texture;
import javax.media.j3d.Texture2D;
import javax.media.j3d.TextureAttributes;
import javax.vecmath.Color3f;
import javax.vecmath.Vector3d;

import com.sun.j3d.utils.geometry.Box;
import com.sun.j3d.utils.geometry.Primitive;
import com.sun.j3d.utils.image.TextureLoader;

import core.GameObject;

public class BoxObject extends GameObject 
{
	private Box shape = new Box();
	private Appearance material = new Appearance();
	private Vector3d minimum = new Vector3d();
	private Vector3d maximum = new Vector3d();
	private String name = "ERROR";
	
	public BoxObject(String name)
	{
		shape = new Box(1.0f, 1.0f, 1.0f, Primitive.GENERATE_NORMALS + Primitive.GENERATE_TEXTURE_COORDS, material);
		setName(name);
	}
	
	public BoxObject(Vector3d position, Vector3d rotation, Vector3d scaleIn, String name)
	{
		shape = new Box(1.0f, 1.0f, 1.0f, Primitive.GENERATE_NORMALS + Primitive.GENERATE_TEXTURE_COORDS, material);
		setScale(scaleIn.x, scaleIn.y, scaleIn.z);
		setName(name);
	}
	
	public BoxObject(Vector3d min, Vector3d max, String name)
	{
		shape = new Box(1.0f, 1.0f, 1.0f, Primitive.GENERATE_NORMALS + Primitive.GENERATE_TEXTURE_COORDS, material);
		setName(name);
	}
	
	public void setMaterial(String path)
	{
		Texture2D texture = new Texture2D();
		TextureAttributes texAtt = new TextureAttributes();
		texAtt.setTextureMode(TextureAttributes.MODULATE);
		Material mat = new Material();
		mat.setAmbientColor(new Color3f(0.0f, 0.0f, 0.0f));
		mat.setDiffuseColor(new Color3f(0.7f,0.7f,0.7f));
		mat.setSpecularColor(new Color3f(0.9f,0.9f,0.9f));
		
		try 
		{
			texture = (Texture2D)new TextureLoader(ImageIO.read(new File(path))).getTexture();
		} catch (IOException e) 
		{
			e.printStackTrace();
		}
		
		texture.setBoundaryModeS(Texture.WRAP);
		texture.setBoundaryModeT(Texture.WRAP);

		material.setTexture(texture);
		material.setMaterial(mat);
		shape.setAppearance(material);
	}
	
	public String getName()
	{
		return name;
	}
	
	public void setName(String toSet)
	{
		name = toSet;
	}
	
	public Vector3d getMinimum()
	{
		return minimum;
	}
	
	public Vector3d getMaximum()
	{
		return maximum;
	}
	
	public Box getShape()
	{
		return shape;
	}
}
