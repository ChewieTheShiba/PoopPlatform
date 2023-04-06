import java.util.*;
import java.awt.*;

public abstract class Hitbox {

	protected int h, k, a, b, damage;
	protected double KB;
	protected double[] latestIntersection = {-1,-1};
	protected Hitbox lastTouched;
	protected boolean moveRight, moveLeft, moveUp, moveDown;
	
	public abstract double[] intersects(Hitbox h1);
	public abstract double[] getOppositeIntersection();
	
	public int getA()
    {
      return a;
    }
    public int getB()
    {
      return b;
    }
  
    public int getH()
    {
      return this.h;
    }

    public int getK()
    {
      return this.k;
    }

	public void setH(int h)
	{
	  this.h = h;
	}
	
	public void setK(int k)
	{
		this.k = k;
	}
	
	public void setA(int a)
	{
		this.a = a;
	}
	
	public void setB(int b)
	{
		this.b = b;
	}
	public int getDamage()
	{
		return damage;
	}
	public void setDamage(int damage)
	{
		this.damage = damage;
	}
	public double[] getLatestIntersection()
	{
		return latestIntersection;
	}
	public void setLatestIntersection(double[] latestIntersection)
	{
		this.latestIntersection = latestIntersection;
	}
	public Hitbox getLastTouched()
	{
		return lastTouched;
	}
	public void setLastTouched(Hitbox lastTouched)
	{
		this.lastTouched = lastTouched;
	}
	public boolean getMoveRight()
	{
		return moveRight;
	}
	public void setMoveRight(boolean moveRight)
	{
		this.moveRight = moveRight;
	}
	public boolean getMoveLeft()
	{
		return moveLeft;
	}
	public void setMoveLeft(boolean moveLeft)
	{
		this.moveLeft = moveLeft;
	}
	public boolean getMoveUp()
	{
		return moveUp;
	}
	public void setMoveUp(boolean moveUp)
	{
		this.moveUp = moveUp;
	}
	public boolean getMoveDown()
	{
		return moveDown;
	}
	public void setMoveDown(boolean moveDown)
	{
		this.moveDown = moveDown;
	}
	public double getKB()
	{
		return KB;
	}
	public void setKB(double kB)
	{
		KB = kB;
	}
	
  
}
