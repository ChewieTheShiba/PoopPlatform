import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.*;
import javax.swing.Timer;

public class Stage
{
	private ArrayList<Hitbox> hitboxes;
	private ImageIcon background;
	
	public Stage(ImageIcon background, ArrayList<Hitbox> hitboxes)
	{
		this.hitboxes = hitboxes;
		this.background = background;
	}

	public ArrayList<Hitbox> getHitboxes()
	{
		return hitboxes;
	}

	public void setHitboxes(ArrayList<Hitbox> hitboxes)
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
