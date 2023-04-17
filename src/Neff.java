import java.awt.event.*;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.*;
import java.util.*;
import javax.swing.Timer;

public class Neff extends Character
{
	
	private ImageIcon specialProjectileLeft, specialProjectileRight, specialLeft, specialRight;
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
		attack3Hitbox = new RectangleHitbox(-100, -100, 20, 4, 5, 200);
		attack4Hitbox = new RectangleHitbox(-100, -100, 20, 4, 7, 200);
		specialHitbox = new RectangleHitbox(-100, -100, 64, 32, 3.5, 300);
		
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
		attack3Image = new ImageIcon("assets/Neff/NeffUpTilt.png");
		attack4Image = new ImageIcon("assets/Neff/NeffDownTilt.png");
		idleRight = new ImageIcon("assets/Neff/NeffIdleLeft.png");
		idleLeft = new ImageIcon("assets/Neff/NeffIdleRight.png");
		specialProjectileRight = new ImageIcon("assets/Neff/HighlanderRight.png");
		specialProjectileLeft = new ImageIcon("assets/Neff/HighlanderLeft.png");
		specialRight = new ImageIcon("assets/Neff/NeffIdleRight.png");
		specialLeft = new ImageIcon("assets/Neff/NeffIdleLeft.png");
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
		currentAttack = "upTilt";
		
		attack4Hitbox.setH(hitbox.getH()+5);
		attack4Hitbox.setK((hitbox.getK()-9)*-1);
			currentPlayerImage = attack3Image;
			tiltAttacking = true;
			tiltTime.start();
		
	}

	public void downTilt()
	{
		attack4Hitbox.setId(name);
		currentAttack = "downTilt";
			
		attack3Hitbox.setH(hitbox.getH()+hitbox.getA()-12);
		attack3Hitbox.setK((hitbox.getK()+attack3Hitbox.getB()-9)*-1);
			currentPlayerImage = attack4Image;
			tiltAttacking = true;
			tiltTime.start();
		
	}

	public void special()
	{
		if(facingRight)
		{
			specialHitbox = new RectangleHitbox(-100, -100, 64, 32, 3.5, 300);
			specialHitbox.setId("HighProjectileRight");
			specialHitbox.setH(hitbox.getH()+hitbox.getA()+16);
			specialHitbox.setK((hitbox.getK()+30)*-1);
			specialHitbox.setMoveRight(true);
			specialProjectiles.add(specialHitbox);
			currentPlayerImage = specialRight;
			stopChecker.start();
		}
		else
		{
			specialHitbox = new RectangleHitbox(-100, -100, 64, 32, 3.5, 300);
			specialHitbox.setId("HighProjectileLeft");
			specialHitbox.setH(hitbox.getH()-hitbox.getA()-60);
			specialHitbox.setK((hitbox.getK()+30)*-1);
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
