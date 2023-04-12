import java.awt.event.*;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.*;
import java.util.*;
import javax.swing.Timer;

public class PoopDefender extends Character
{
	private ImageIcon specialProjectile, specialLeft, specialRight;
	private RectangleHitbox specialHitbox;
	
	public PoopDefender()
	{
		tiltTime = new Timer(1000, new actionListener());
		xOffPut = 76;
		tryTilt = false;
		trySpecial = false;
		attacking = false;
		hitbox = new OvalHitbox(60, 121, 60, 121, 00, 10);
		attack2Hitbox = new RectangleHitbox(-100, -100, 59, 74, 10.5, 300);
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
		currentPlayerImage = idleRight;
	}

	public void rightTilt()
	{
		//og ab is 60,121
		attack2Hitbox.setH(hitbox.getH()+hitbox.getA()+1);
		attack2Hitbox.setK((hitbox.getK()+7)*-1);
		currentPlayerImage = attack2Image;
		attacking = true;
		tiltTime.start();
	}

	public void leftTilt()
	{
		attack1Hitbox.setH(hitbox.getH()-hitbox.getA()-1);
		attack2Hitbox.setK((hitbox.getK()+7)*-1);
		currentPlayerImage = attack1Image;
		attacking = true;
		tiltTime.start();
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
	
	public class actionListener implements ActionListener
	{

		public void actionPerformed(ActionEvent e)
		{
			Object source = e.getSource();
			
			if(source.equals(tiltTime))
			{
				tiltTime.stop();
				attacking = false;
				attack1Hitbox.setH(-100);
				attack1Hitbox.setK(-100);
				attack2Hitbox.setH(-100);
				attack2Hitbox.setK(-100);
				attack3Hitbox.setH(-100);
				attack3Hitbox.setK(-100);
				attack4Hitbox.setH(-100);
				attack4Hitbox.setK(-100);
				
				if(facingRight)
					currentImage = idleRight;
				else
					currentImage = idleLeft;
			}
			
		}
		
	}

}
