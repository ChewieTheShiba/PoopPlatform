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
    int theta = 0, thetaopp = 0;
		
		if(h1R)
		{
			theta = a/2;
			thetaopp = 180;
		}
		else if(h1L)
		{
			theta = 0;
			thetaopp = 90;
		}

    Rectangle rect = new Rectangle(h, k, a, b);
		Rectangle rect1 = new Rectangle(h1.getH()-h1.getA(), h1.getK()*-1-h1.getB(), h1.getA()*2, h1.getB()*2);
    
    double d[] = {-1, -1};

    if(rect.intersects(rect1))
    {
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
      for(int m = 1; m > -1; m-=2)
				{
					for(double q = 1; q <= thetaopp*m; q+=0.5*m)
					{
						double f = 0;
						int ta = a;
						int tb = b;
						double r1 = 0;
						double top = 0, bottom = 0;
						
			            if(ta < tb)
			            {
			            	int temp = ta;
			            	ta = tb;
			            	tb = temp;
			            	
			            	f = Math.sqrt((ta*ta)-(tb*tb));
				            
				            double e = f/ta;
				            
				            
				            top = (ta*ta)*(1-(e*e));
				            bottom = 1-((e*e)*(Math.sin(q)*Math.sin(q)));
				            
			            }
			            else
			            {
			            	f = Math.sqrt((ta*ta)-(tb*tb));
				            
				            double e = f/ta;
				            
				            
				            top = (ta*ta)*(1-(e*e));
				            bottom = 1-((e*e)*(Math.cos(q)*Math.cos(q)));
				            
			            }
			            
			            r1 = Math.sqrt(top/bottom);
			            
			            double x1 = r1*Math.cos(q);
			            double y1 = r1*Math.sin(q);
			            x1 += h;
			            y1 += k;
			    for(int y = k; y < b+k; y++)
          {
            for(int x = h+theta; x < h+(a/2)+theta; x++)
              {
                if(Math.abs(x1-x) < 5 && Math.abs(y1-y) < 5)
                {
                  d[0] = x1;
                  d[1] = y1;
                  latestIntersection = d;
                  return d;
                }
              }
          }
        }
      }
    }
	}
  latestIntersection = d;
  return d;
  }

	public double[] getOppositeIntersection()
	{
		return null;
	}
	
}
