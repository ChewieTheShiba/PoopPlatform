import java.awt.event.*;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.*;
import java.util.*;
import javax.swing.Timer;

public class PoopDefender extends Character
{
	private ImageIcon specialProjectile, specialLeft, specialRight;
	private ArrayList<RectangleHitbox> projectiles;
	private RectangleHitbox specialHitbox;
	
	public PoopDefender()
	{
		hitbox = new OvalHitbox(60, 121, 60, 121, 00, 10);
		attack1Image = new ImageIcon("assets/Poop Defender/PoopDefenderLeftTilt.png");
		attack2Image = new ImageIcon("assets/Poop Defender/PoopDefenderRightTilt.png");
		attack3Image = new ImageIcon("assets/Poop Defender/PoopDefenderUpTilt.png");
		//uncomment when image is finished
		//attack4Image = new ImageIcon("assets/Poop Defender/PoopDefenderDownTilt.png");
		idleRight = new ImageIcon("assets/Poop Defender/PoopDefenderIdleRight.png");
		idleLeft = new ImageIcon("assets/Poop Defender/PoopDefenderIdleLeft.png");
		specialProjectile = new ImageIcon("assets/Poop Defender/SpecialProjectile.png");
		specialRight = new ImageIcon("assets/Poop Defender/PoopDefenderSpecialRight.png");
		specialLeft = new ImageIcon("assets/Poop Defender/PoopDefenderSpecialLeft.png");
	}

	public void rightTilt()
	{
		//og ab is 60,121
		attack2Hitbox = new RectangleHitbox(hitbox.getH()+hitbox.getA()+1, hitbox.getK()+7, 59, 74, 20, 20);
		currentPlayerImage = attack2Image;
	}

	public void leftTilt()
	{
		// TODO Auto-generated method stub
		
	}

	public void upTilt()
	{
		// TODO Auto-generated method stub
		
	}

	public void downTilt()
	{
		// TODO Auto-generated method stub
		
	}

	public void special()
	{
		// TODO Auto-generated method stub
		
	}

}
