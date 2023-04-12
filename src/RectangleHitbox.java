import java.awt.*;

public class RectangleHitbox extends Hitbox
{
	
	public RectangleHitbox(int h, int k, int a, int b, double damage, double KB)
	{
		this.a = a;
		this.b = b;
		this.h = h;
		this.damage = damage;
		this.KB = KB;
		this.id = "" + nothingsTheSame;
		this.k = k;
		nothingsTheSame++;
	}

	public double[] intersects(Hitbox h1)
	{
		Hitbox checker = h1;
		
		int theta = 0, thetaopp = 0, adder = 0, adderopp = 0;
		
		if(moveRight)
		{
			theta = 90;
			adderopp = 90;
			thetaopp = 180;
		}
		else if(moveLeft)
		{
			theta = 180;
			adder = 90;
			thetaopp = 90;
		}
		else if(moveUp)
		{
			theta = 180;
			thetaopp = 360;
			adderopp = 180;
		}
		else if(moveDown)
		{
			adder = 180;
			theta = 360;
			thetaopp = 180;
		}

    Rectangle rect = new Rectangle(h, k, a, b);
    Rectangle rect1 = null;
    
    if(checker.getClass().equals(new RectangleHitbox(1, 1, 1, 1, 0, 0).getClass()))
    	rect1 = new Rectangle(h1.getH(), h1.getK(), h1.getA(), h1.getB());
    else
    	rect1 = new Rectangle(h1.getH()-h1.getA(), h1.getK()*-1-h1.getB(), h1.getA()*2, h1.getB()*2);
    
    double d[] = {-1, -1};

    if(rect.intersects(rect1))
    {
      if(this.getClass().equals(h1.getClass()))
      {
        Rectangle r = new Rectangle(h1.getH(), h1.getK(), h1.getA(), h1.getB());
        for(int y = k; y < k+b; y++)
          {
            for(int x = h+theta; x < h+a/2+theta; x++)
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
		  for(int m = 1; m > -1; m--)
			{
			  	double q = adder;
				double j = theta;
				
				if(m == 0 && (moveRight || moveLeft))
				{
					q = -1*theta;
					j = adder*-1;
				}
				
			  
				for(; q <= j; q+=0.5)
				{
					double f = 0;
					int ta = h1.getA();
					int tb = h1.getB();
					double top = 0, bottom = 0;
					
		            if(ta < tb)
		            {
		            	int temp = ta;
		            	ta = tb;
		            	tb = temp;
		            	
		            	f = Math.sqrt((ta*ta)-(tb*tb));
			            
			            double e = f/ta;
			            
			            
			            top = (ta*ta)*(1-(e*e));
			            bottom = 1-((e*e)*(Math.sin(Math.toRadians(q))*Math.sin(Math.toRadians(q))));
			            
			            
		            }
		            else
		            {
		            	f = Math.sqrt((ta*ta)-(tb*tb));
			            
			            double e = f/ta;
			            
			            
			            top = (ta*ta)*(1-(e*e));
			            bottom = 1-((e*e)*(Math.cos(Math.toRadians(q))*Math.cos(Math.toRadians(q))));
			            
		            }

		            
		            double r1 = Math.sqrt(top/bottom);
		            
		            f = Math.sqrt((ta*ta)-(tb*tb));
		            
		            double x1 = r1*Math.cos(Math.toRadians(q));
		            double y1 = r1*Math.sin(Math.toRadians(q));
		            x1 += h1.getH();
		            y1 += h1.getK();
		            
		            y1 *= -1;
		           
		            
		            Rectangle r = new Rectangle(h, k, a, b);
		            if(r.contains((int)x1, (int)y1))
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
  latestIntersection = d;
  return d;
  }

	public double[] getOppositeIntersection()
	{
		return null;
	}
	
}
