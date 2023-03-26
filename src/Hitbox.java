import java.util.*;
import java.awt.*;

public abstract class Hitbox {

	protected int h, k, a, b, damage;
	protected double[] latestIntersection = {-1,-1};
	
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
  
}
