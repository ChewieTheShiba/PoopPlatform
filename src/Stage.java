import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.*;
import javax.swing.Timer;

public class Stage
{
	private ArrayList<RectangleHitbox> hitboxes;
	private ImageIcon background;
	public Stage(ImageIcon background, ArrayList<RectangleHitbox> hitboxes)
	{
		this.hitboxes = hitboxes;
		this.background = background; 
	}

	public ArrayList<RectangleHitbox> getHitboxes()
	{
		return hitboxes;
	} 

	public void setHitboxes(ArrayList<RectangleHitbox> hitboxes)
	{
		this.hitboxes = hitboxes;
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
