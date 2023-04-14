import java.util.*;
import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.*;
import java.util.*;
import javax.swing.Timer;


public class Character {
    protected OvalHitbox hitbox;
    protected String name, description, attack1name, attack2name, attack3name, attack4name, specialattackname, currentAttack;
    protected double HP, Weight, attack1power, attack1knockback, attack2power, attack2knockback, attack3power, attack3knockback, attack4power, attack4knockback, specialattackpower, specialknockback;
    protected boolean jumping, doubleJumping, facingRight, tiltAttacking, specialAttacking, tryTilt, trySpecial, falling, moveDown;
    protected ImageIcon attack1Image, attack2Image, attack3Image, attack4Image, idleRight, idleLeft, currentPlayerImage;
    protected RectangleHitbox attack1Hitbox, attack2Hitbox, attack3Hitbox, attack4Hitbox;
    protected Timer tiltTime, specialTime, stopChecker;
    protected int xOffPut, yOffPut;
    protected ArrayList<Hitbox> specialProjectiles;
    
    //For Charlie
    //make a String variable called currentAttack with setters and getters
    
    //Also leave this here for reference but
    /*
    attack 1 = left tilt
    attack 2 = right tilt
    attack 3 = up tilt
    attack 4 = down tilt
    special = special
    */
    
public boolean equals(Object c)
{
	if(c.getClass().equals(Character.class) & ((Character) c).getName().equals(getName()) && hitbox.equals(((Character) c).getHitbox()))
		return true;
	else return false;
}
    
public boolean getTryTilt()
{
	return tryTilt;
}

public void setTryTilt(boolean tryTilt)
{
	this.tryTilt = tryTilt;
}

public boolean getTrySpecial()
{
	return trySpecial;
}

public void setTrySpecial(boolean trySpecial)
{
	this.trySpecial = trySpecial;
}



public int getXOffPut()
{
	return xOffPut;
}

public void setxOffPut(int xOffPut)
{
	this.xOffPut = xOffPut;
}

public int getYOffPut()
{
	return yOffPut;
}

public void setYOffPut(int yOffPut)
{
	this.yOffPut = yOffPut;
}

public void setH(double wantedH){
        
        int IntH = (int) wantedH;
        
        hitbox.setH(IntH);
    }

    public void setK(double wantedK){
        int IntK = (int) wantedK;

        hitbox.setK(IntK);
    }
    
    public OvalHitbox getDupeHitbox()
    {
    	return new OvalHitbox(hitbox.getH(), hitbox.getK()*-1, hitbox.getA(), hitbox.getB(), hitbox.getDamage(), hitbox.getKB());
    }
    
    public void setMoveRight(boolean moveRight){
        hitbox.setMoveRight(moveRight);
        if(moveRight)
        {
        	currentPlayerImage = idleRight;
        	facingRight = true;
        }
        	
    }

    public boolean getMoveRight(){
    	
    	return hitbox.getMoveRight();
    }
    
    public void setMoveLeft(boolean moveLeft){
        hitbox.setMoveLeft(moveLeft);
        if(moveLeft)
        {
        	currentPlayerImage = idleLeft;
        	facingRight = false;
        }
    }

    public boolean getMoveLeft(){
    	
    	return hitbox.getMoveLeft();
    }
    
    public void setMoveUp(boolean moveUp){
        hitbox.setMoveUp(moveUp);
    }

    public boolean getMoveUp(){
    	
    	return hitbox.getMoveUp();
    }
    
    public void setMoveDown(boolean moveDown){
        this.moveDown = moveDown;
    }

    public boolean getMoveDown(){
    	
    	return moveDown;
    }

    
    public double getWeight() {
        return Weight;
    }


    public void setWeight(double weight) {
        Weight = weight;
    }


    public double getAttack1Knockback() {
        return attack1knockback;
    }


    public void setAttack1Knockback(double attack1knockback) {
        this.attack1knockback = attack1knockback;
    }


    public double getAttack2Knockback() {
        return attack2knockback;
    }


    public void setAttack2Knockback(double attack2knockback) {
        this.attack2knockback = attack2knockback;
    }


    public double getAttack3Knockback() {
        return attack3knockback;
    }


    public void setAttack3Knockback(double attack3knockback) {
        this.attack3knockback = attack3knockback;
    }


    public double getAttack4Knockback() {
        return attack4knockback;
    }


    public void setAttack4Knockback(double attack4knockback) {
        this.attack4knockback = attack4knockback;
    }


    public double getSpecialKnockback() {
        return specialknockback;
    }


    public void setSpecialKnockback(double specialknockback) {
        this.specialknockback = specialknockback;
    }

    public Boolean getJumping() {
        return jumping;
    }

    public void setJumping(boolean jumping) {
        this.jumping = jumping;
    }

    public Boolean getdoubleJumping() {
        return doubleJumping;
    }

    public void setdoubleJumping(boolean doubleJumping) {
        this.doubleJumping = doubleJumping;
    }

    public OvalHitbox getHitbox() {
        return hitbox;
    }

    public String getAttack4Name() {
        return attack4name;
    }

    public void setAttack4name(String attack4name) {
        this.attack4name = attack4name;
    }

    public String getSpecialAttackName() {
        return specialattackname;
    }

    public void setSpecialAttackname(String specialattackname) {
        this.specialattackname = specialattackname;
    }

    public double getAttack4Power() {
        return attack4power;
    }

    public void setAttack4Power(double attack4power) {
        this.attack4power = attack4power;
    }

    public void setHitbox(OvalHitbox hitbox) {
        this.hitbox = hitbox;
    }
 
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
        
        if(attack1Hitbox != null)
        	attack1Hitbox.setId(getName());
        if(attack2Hitbox != null)
        	attack2Hitbox.setId(getName());
        if(attack3Hitbox != null)
        	attack3Hitbox.setId(getName());
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getAttack1Name() {
        return attack1name;
    }

    public void setAttack1Name(String attack1name) {
        this.attack1name = attack1name;
    }

    public String getAttack2Name() {
        return attack2name;
    }

    public void setAttack2Name(String attack2name) {
        this.attack2name = attack2name;
    }

    public String getAttack3Name() {
        return attack3name;
    }

    public void setAttack3Name(String attack3name) {
        this.attack3name = attack3name;
    }

    public double getHP() {
        return HP;
    }

    public void setHP(double hP) {
        HP = hP;
    }

    public double getAttack1Power() {
        return attack1power;
    }

    public void setAttack1Power(double attack1power) {
        this.attack1power = attack1power;
    }

    public double getAttack2Power() {
        return attack2power;
    }

    public void setAttack2Power(double attack2power) {
        this.attack2power = attack2power;
    }

    public double getAttack3Power() {
        return attack3power;
    }

    public void setAttack3Power(double attack3power) {
        this.attack3power = attack3power;
    }
 
    public double getSpecialAttackPower() {
        return specialattackpower;
    }

    public void setSpecialAttackPower(double specialattackpower) {
        this.specialattackpower = specialattackpower;
    }

	public boolean getDoubleJumping()
	{
		return doubleJumping;
	}

	public void setDoubleJumping(boolean doubleJumping)
	{
		this.doubleJumping = doubleJumping;
	}

	public boolean getFacingRight()
	{
		return facingRight;
	}

	public void setFacingRight(boolean facingRight)
	{
		this.facingRight = facingRight;
	}
	
	public ImageIcon getAttack1Image()
	{
		return attack1Image;
	}

	public void setAttack1Image(ImageIcon attack1Image)
	{
		this.attack1Image = attack1Image;
	}

	public ImageIcon getAttack2Image()
	{
		return attack2Image;
	}

	public void setAttack2Image(ImageIcon attack2Image)
	{
		this.attack2Image = attack2Image;
	}

	public ImageIcon getAttack3Image()
	{
		return attack3Image;
	}

	public void setAttack3Image(ImageIcon attack3Image)
	{
		this.attack3Image = attack3Image;
	}

	public ImageIcon getAttack4Image()
	{
		return attack4Image;
	}

	public void setAttack4Image(ImageIcon attack4Image)
	{
		this.attack4Image = attack4Image;
	}

	public ImageIcon getIdleRight()
	{
		return idleRight;
	}

	public void setIdleRight(ImageIcon idleRight)
	{
		this.idleRight = idleRight;
	}

	public ImageIcon getIdleLeft()
	{
		return idleLeft;
	}

	public void setIdleLeft(ImageIcon idleLeft)
	{
		this.idleLeft = idleLeft;
	}

	public RectangleHitbox getAttack1Hitbox()
	{
		return attack1Hitbox;
	}

	public void setAttack1Hitbox(RectangleHitbox attack1Hitbox)
	{
		this.attack1Hitbox = attack1Hitbox;
	}

	public RectangleHitbox getAttack2Hitbox()
	{
		return attack2Hitbox;
	}

	public void setAttack2Hitbox(RectangleHitbox attack2Hitbox)
	{
		this.attack2Hitbox = attack2Hitbox;
	}

	public RectangleHitbox getAttack3Hitbox()
	{
		return attack3Hitbox;
	}

	public void setAttack3Hitbox(RectangleHitbox attack3Hitbox)
	{
		this.attack3Hitbox = attack3Hitbox;
	}

	public RectangleHitbox getAttack4Hitbox()
	{
		return attack4Hitbox;
	}

	public void setAttack4Hitbox(RectangleHitbox attack4Hitbox)
	{
		this.attack4Hitbox = attack4Hitbox;
	}
	

	public ImageIcon getCurrentPlayerImage()
	{
		return currentPlayerImage;
	}

	public void setCurrentPlayerImage(ImageIcon currentPlayerImage)
	{
		this.currentPlayerImage = currentPlayerImage;
	}
	

	public ArrayList<Hitbox> getSpecialProjectiles()
	{
		return specialProjectiles;
	}

	public void setSpecialProjectiles(ArrayList<Hitbox> specialProjectiles)
	{
		this.specialProjectiles = specialProjectiles;
	}

	public boolean getFalling()
	{
		return hitbox.getMoveDown();
	}

	public void setFalling(boolean falling)
	{
		hitbox.setMoveDown(falling);;
	}

	public void rightTilt() {System.out.println("you shouldnt be seeing this");};
	public void leftTilt() {};
	public void upTilt() {};
	public void downTilt() {};
	public void special() {};    
}
