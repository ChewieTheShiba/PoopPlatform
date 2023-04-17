import java.awt.event.*;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.*;
import java.util.*;
import javax.swing.Timer;

public class Neff extends Character
{
	
	private ImageIcon specialProjectile, specialLeft, specialRight, attack3Image1, attack3Image2, attack4Image1, attack4Image2;
	private RectangleHitbox specialHitbox;

	public Neff()
	{
		hitbox = new OvalHitbox(8, 19, 8, 19, 0, 10);
		tiltTime = new Timer(500, new actionListener());
		stopMoving = false;
		Weight = 50;
		name = "";
		stopChecker = new Timer(20, new actionListener());
		specialProjectiles = new ArrayList<Hitbox>();
		currentAttack = "";
		xOffPut = 10;
		yOffPut = 2;
		tryTilt = false;
		trySpecial = false;
		tiltAttacking = false;
		attack1Hitbox = new RectangleHitbox(-100, -100, 11, 2, 10.5, 300);
		attack2Hitbox = new RectangleHitbox(-100, -100, 11, 2, 10.5, 300);
		attack3Hitbox = new RectangleHitbox(-100, -100, 63, 207, 5, 350);
		attack4Hitbox = new RectangleHitbox(-100, -100, 63, 246, 7, 500);
		specialHitbox = new RectangleHitbox(-100, -100, 20, 12, 3.5, 300);
		
		attack1Hitbox.setMoveLeft(true);
		attack1Hitbox.setMoveUp(true);
		attack1Hitbox.setMoveDown(true);
		attack1Hitbox.setMoveRight(true);
		
		attack2Hitbox.setMoveRight(true);
		attack2Hitbox.setMoveUp(true);
		attack2Hitbox.setMoveDown(true);
		attack2Hitbox.setMoveLeft(true);
		
		attack3Hitbox.setMoveLeft(true);
		attack3Hitbox.setMoveUp(true);
		attack3Hitbox.setMoveRight(true);
		attack3Hitbox.setMoveDown(true);

		//makes the move only possible via spike
		attack4Hitbox.setMoveDown(true);
		
		attack1Image = new ImageIcon("assets/Neff/NeffLeftTilt.png");
		attack2Image = new ImageIcon("assets/Neff/NeffRightTilt.png");
		attack3Image1 = new ImageIcon("assets/Poop Defender/PoopDefenderUpTiltFaceRight.png");
		attack3Image2  = new ImageIcon("assets/Poop Defender/PoopDefenderUpTiltFaceLeft.png");
		attack4Image1 = new ImageIcon("assets/Poop Defender/PoopDefenderDownTiltRight.png");
		attack4Image2 = new ImageIcon("assets/Poop Defender/PoopDefenderDownTiltLeft.png");
		idleRight = new ImageIcon("assets/Neff/NeffIdleLeft.png");
		idleLeft = new ImageIcon("assets/Neff/NeffIdleRight.png");
		specialProjectile = new ImageIcon("assets/Poop Defender/SpecialProjectile.png");
		specialRight = new ImageIcon("assets/Poop Defender/PoopDefenderSpecialRight.png");
		specialLeft = new ImageIcon("assets/Poop Defender/PoopDefenderSpecialLeft.png");
		currentPlayerImage = idleRight;
	}
	
	public void rightTilt()
	{
		//og ab is 60,121
		attack2Hitbox.setId(name);
		currentAttack = "rightTilt";
		attack2Hitbox.setH(hitbox.getH()+hitbox.getA()-2);
		attack2Hitbox.setK((hitbox.getK()+7)*-1);
		currentPlayerImage = attack2Image;
		tiltAttacking = true;
		tiltTime.start();
		stopChecker.start();
	}

	public void leftTilt()
	{
		attack1Hitbox.setId(name);
		currentAttack = "leftTilt";
		attack1Hitbox.setH(hitbox.getH()-hitbox.getA()-attack1Hitbox.getA()+2);
		attack1Hitbox.setK((hitbox.getK()+7)*-1);
		currentPlayerImage = attack1Image;
		tiltAttacking = true;
		tiltTime.start();
		stopChecker.start();
	}

	public void upTilt()
	{
		attack3Hitbox.setId(name);
		xOffPut = 76;
		yOffPut = 158;
		currentAttack = "upTilt";
		
		if(facingRight)
		{
			attack3Hitbox.setH(hitbox.getH()+hitbox.getA()-22);
			attack3Hitbox.setK((hitbox.getK()+attack3Hitbox.getB()-22)*-1);
			currentPlayerImage = attack3Image1;
			tiltAttacking = true;
			tiltTime.start();
		}
		else
		{
			attack3Hitbox.setH(hitbox.getH()-hitbox.getA()-40);
			attack3Hitbox.setK((hitbox.getK()+attack3Hitbox.getB()-22)*-1);
			currentPlayerImage = attack3Image2;
			tiltAttacking = true;
			tiltTime.start();
		}
		
	}

	public void downTilt()
	{
		attack4Hitbox.setId(name);
		xOffPut = 76;
		yOffPut = 0;
		currentAttack = "downTilt";
		
		if(facingRight)
		{
			attack4Hitbox.setH(hitbox.getH()+26);
			attack4Hitbox.setK((hitbox.getK()-2)*-1);
			currentPlayerImage = attack4Image1;
			tiltAttacking = true;
			tiltTime.start();
		}
		else
		{
			attack4Hitbox.setH(hitbox.getH()-attack4Hitbox.getA()-26);
			attack4Hitbox.setK((hitbox.getK()-2)*-1);
			currentPlayerImage = attack4Image2;
			tiltAttacking = true;
			tiltTime.start();
		}
		
	}

	public void special()
	{
		if(facingRight)
		{
			specialHitbox = new RectangleHitbox(-100, -100, 20, 12, 3.5, 300);
			specialHitbox.setId("Projectile");
			specialHitbox.setH(hitbox.getH()+hitbox.getA()+36);
			specialHitbox.setK((hitbox.getK()+3)*-1);
			specialHitbox.setMoveRight(true);
			specialProjectiles.add(specialHitbox);
			currentPlayerImage = specialRight;
			stopChecker.start();
		}
		else
		{
			specialHitbox = new RectangleHitbox(-100, -100, 20, 12, 3.5, 300);
			specialHitbox.setId("Projectile");
			specialHitbox.setH(hitbox.getH()-hitbox.getA()-52);
			specialHitbox.setK((hitbox.getK()+3)*-1);
			specialHitbox.setMoveLeft(true);
			specialProjectiles.add(specialHitbox);
			currentPlayerImage = specialLeft;
			stopChecker.start();

		}
	}
	
	public class actionListener implements ActionListener
	{

		public void actionPerformed(ActionEvent e)
		{
			Object source = e.getSource();
			
			if(source.equals(tiltTime))
			{
				tiltTime.stop();
				tiltAttacking = false;
				attack1Hitbox.setH(-100);
				attack1Hitbox.setK(-100);
				attack2Hitbox.setH(-100);
				attack2Hitbox.setK(-100);
				attack3Hitbox.setH(-100);
				attack3Hitbox.setK(-100);
				attack4Hitbox.setH(-100);
				attack4Hitbox.setK(-100);
				specialHitbox.setH(-100);
				specialHitbox.setK(-100);
				stopChecker.stop();
				
				xOffPut = 10;
				yOffPut = 2;
				
				if(facingRight)
					currentPlayerImage = idleRight;
				else
					currentPlayerImage = idleLeft;
			}
			
			if(source.equals(stopChecker))
			{
				if(tiltAttacking && (getMoveRight() || getMoveLeft() || getFalling())) {
					tiltTime.stop();
					tiltAttacking = false;
					attack1Hitbox.setH(-100);
					attack1Hitbox.setK(-100);
					attack2Hitbox.setH(-100);
					attack2Hitbox.setK(-100);
					attack4Hitbox.setH(-100);
					attack4Hitbox.setK(-100);
					specialHitbox.setH(-100);
					specialHitbox.setK(-100);
					stopChecker.stop();
					
					xOffPut = 10;
					yOffPut = 2;
					
					if(facingRight)
						currentPlayerImage = idleRight;
					else
						currentPlayerImage = idleLeft;
				}
				for(Hitbox h : specialProjectiles)
				{
					if(h.getMoveRight())
						h.setH(h.getH() + 20);
					if(h.getMoveLeft())
						h.setH(h.getH() - 20);
				}
			}
			
		}
		
	}

	
}
