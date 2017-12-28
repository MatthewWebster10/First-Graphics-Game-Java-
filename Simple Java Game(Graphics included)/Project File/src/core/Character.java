package core;

import javax.vecmath.Vector3d;

import graphics.BoxObject;

public class Character extends GameObject
{
	public BoxObject characterObject = new BoxObject(new Vector3d(0, 10, 0), new Vector3d(0, 0, 0), new Vector3d(1.0, 1.0, 1.0), "Character");
}
