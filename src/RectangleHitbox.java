import java.awt.*;

public class RectangleHitbox extends Hitbox
{
	
	public RectangleHitbox(int h, int k, int a, int b, int damage)
	{
		this.a = a;
		this.b = b;
		this.h = h;
		this.damage = damage;
		this.k = k;
	}

	public double[] intersects(Hitbox h1, boolean h1R, boolean h1L, boolean jumping)
	{
    double d[] = {-1, -1};
		if(this.getClass().equals(h1.getClass()))
    {
      Rectangle r = new Rectangle(h1.getH(), h1.getK(), h1.getA(), h1.getB());
      for(int y = k; y < b+k; y++)
        {
          for(int x = h; x < h+a; x++)
            {
              if(r.contains(x, y))
              {
                d[0] = x;
                d[1] = y;
                latestIntersection = d;
                return d;
              }
            }
        }
    }
    else
    {
      //change me
      return null;
    }
    latestIntersection = d;
    return d;
	}

	public double[] getOppositeIntersection()
	{
		return null;
	}
	
}
