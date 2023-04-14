import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.*;
import javax.swing.Timer;

public class Stage
{
	private ArrayList<RectangleHitbox> hitboxes;
	private ImageIcon background;
	private ArrayList<RectangleHitbox> pass;
	public Stage(ImageIcon background, ArrayList<RectangleHitbox> hitboxes, ArrayList<RectangleHitbox> pass)
	{
		this.hitboxes = hitboxes;
		this.background = background; 
		this.pass = pass;
	}

	public ArrayList<RectangleHitbox> getHitboxes()
	{
		return hitboxes;
	} 
	
	public ArrayList<RectangleHitbox> getPassHB()
	{
	
		return pass; 
	}

	public void setHitboxes(ArrayList<RectangleHitbox> hitboxes)
	{
		this.hitboxes = hitboxes;
	} 
	
	public void setPassHB(ArrayList<RectangleHitbox> pass)
	{
		this.pass = pass;
	
	}

	public ImageIcon getBackground()
	{
		return background;
	}

	public void setBackground(ImageIcon background)
	{
		this.background = background;
	}
	
	
	
}
