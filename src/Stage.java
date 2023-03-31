import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.*;
import javax.swing.Timer;

public class Stage
{
	private ArrayList<Hitbox> hitboxes;
	private ImageIcon background;
	private ArrayList<Hitbox> pass;
	public Stage(ImageIcon background, ArrayList<Hitbox> hitboxes, ArrayList<Hitbox> pass)
	{
		this.hitboxes = hitboxes;
		this.background = background; 
		this.pass = pass;
	}

	public ArrayList<Hitbox> getHitboxes()
	{
		return hitboxes;
	} 
	
	public ArrayList<Hitbox> getPassHB()
	{
	
		return pass; 
	}

	public void setHitboxes(ArrayList<Hitbox> hitboxes)
	{
		this.hitboxes = hitboxes;
	} 
	
	public void setPassHB(ArrayList<Hitbox> pass)
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
