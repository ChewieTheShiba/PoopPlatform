import java.awt.*;

public class OvalHitbox extends Hitbox
{
	
	public OvalHitbox(int h, int k, int a, int b, int damage)
	{
		this.a = a;
		this.b = b;
		this.h = h;
		this.k = k*-1;
		this.damage = damage;
	}
	
	public double[] intersects(Hitbox h1, boolean h1R, boolean h1L)
	{
		Hitbox checker = h1;
		double d[] = {-1, -1};
		
		int theta = 0;
		
		if(h1R)
			theta = 90;
		else if(h1L)
			theta = 180;
			
		
		Rectangle rect = new Rectangle(h-a, k*-1-b, a*2, b*2);
		Rectangle rect1 = new Rectangle(h1.getH()-h1.getA(), h1.getK()*-1-h1.getB(), h1.getA()*2, h1.getB()*2);
		
		
		if(rect.intersects(rect1))
		{
			if(checker.getClass().equals(new RectangleHitbox(1, 1, 1, 1, 0).getClass()))
			{
				for(int m = 1; m > -1; m-=2)
				{
					for(double q = 1; q <= theta*m; q+=1)
					{
						double f = 0;
						int ta = a;
						int tb = b;
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
			            
			            double r1 = Math.sqrt(top/bottom);
			            
			            f = Math.sqrt((ta*ta)-(tb*tb));
			            
			            double x1 = r1*Math.cos(q);
			            double y1 = r1*Math.sin(q);
			            x1 += h;
			            y1 += k;
			            
			            y1 *= -1;
			            
			            System.out.println((int)x1 + "\t" + (int)y1);
			            
			            Rectangle r = new Rectangle(h1.getH(), h1.getK(), h1.getA(), h1.getB());
			            if(r.contains((int)x1, (int)y1))
			            {
			            	d[0] = x1;
			            	d[1] = y1*-1;
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
					for(double q = 1; q <= theta*m; q+=0.5)
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
			            
			            
			            
				        for(double w = 0; w <= 360; w+=0.5)
				          {
				        	int a2 = h1.getA();
				        	int b2 = h1.getB();
				        	double r2 = 0;
				        	double f2 = 0;
				        	double top2 = 0, bottom2 = 0;
				        	
				        	if (a2 < b2)
				        	{
				        		int temp = a2;
				        		a2 = b2;
				        		b2 = temp;
				        		
					        	f2 = Math.sqrt((a2*a2)-(b2*b2));
					        	
					        	double e2 = f2/a2;
					        	
					            top2 = (a2*a2)*(1-(e2*e2));
					            bottom2 = 1-((e2*e2)*(Math.sin(w)*Math.sin(w)));
					            
				        	}
				        	else
				        	{
					        	f2 = Math.sqrt((a2*a2)-(b2*b2));
					        	
					        	double e2 = f2/a2;
					        	
					            top2 = (a2*a2)*(1-(e2*e2));
					            bottom2 = 1-((e2*e2)*(Math.cos(w)*Math.cos(w)));
					          
				        	}
				            
				        	r2 = Math.sqrt(top2/bottom2);
				            double x2 = r2*Math.cos(w);
				            double y2 = r2*Math.sin(w);
				            x2 += h1.getH();
				            y2 += h1.getK();	
				            
				            if(Math.abs(x2-x1) < 1 && Math.abs(y2-y1) < 1)
				            {
				            	d[0] = x1;
				            	d[1] = y2;
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
		else
			return d;
		
		
	}
	
	
	public double[] getOppositeIntersection()
	{
		double d[] = {-1,-1};
		if(latestIntersection != d)
		{
			if(latestIntersection[0]-h < 0)
			{
				d[0] = latestIntersection[0]-h + (((latestIntersection[0]-h)*2)*-1);
				System.out.println(d[0]);
				d[0] += h;
			}
			else if(latestIntersection[0]-h > 0)
			{
				d[0] = latestIntersection[0]-h - ((latestIntersection[0]-h)*2);
				System.out.println(d[0]);
				d[0] += h;
			}
			if(latestIntersection[1]-k < 0)
			{
				d[1] = latestIntersection[1]-k + (((latestIntersection[1]-k)*2)*-1);
				System.out.println(d[1]);
				d[1] += k;
				return d;
			}
			else if(latestIntersection[1]-k > 0)
			{
				d[1] = latestIntersection[1]-k - ((latestIntersection[1]-k)*2);
				System.out.println(d[1]);
				d[1] += k;
				return d;
			}
		}
		
		return d;
	}
	
	
}
