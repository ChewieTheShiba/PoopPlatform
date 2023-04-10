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
		this.lastTouched = null;
	}
	
	public double[] intersects(Hitbox h1)
	{
		Hitbox checker = h1;
		lastTouched = h1;
		double d[] = {-1, -1};
		
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
		
		Rectangle rect = new Rectangle(h-a, k*-1-b, a*2, b*2);
		Rectangle rect1 = null;
		
		if(checker.getClass().equals(new RectangleHitbox(1, 1, 1, 1, 0).getClass()))
	    	rect1 = new Rectangle(h1.getH(), h1.getK(), h1.getA(), h1.getB());
	    else
	    	rect1 = new Rectangle(h1.getH()-h1.getA(), h1.getK()*-1-h1.getB(), h1.getA()*2, h1.getB()*2);
		
		if(rect.intersects(rect1))
		{
			if(checker.getClass().equals(new RectangleHitbox(1, 1, 1, 1, 0).getClass()))
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
			            x1 += h;
			            y1 += k;
			            
			            y1 *= -1;
			            
			            Rectangle r = new Rectangle(h1.getH(), h1.getK(), h1.getA(), h1.getB());
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
			
			else
			{
				for(int m = 1; m > -1; m--)
				{
					double q = adder;
					double j = theta;
					
					if(m == 0 && (moveLeft || moveRight))
					{
						q = -1*theta;
						j = adder*-1;
					}
					
					for(; q <= j; q+=0.5)
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
				            bottom = 1-((e*e)*(Math.sin(Math.toRadians(q))*Math.sin(Math.toRadians(q))));
			            }
			            else
			            {
			            	f = Math.sqrt((ta*ta)-(tb*tb));
				            
				            double e = f/ta;
				            
				            
				            top = (ta*ta)*(1-(e*e));
				            bottom = 1-((e*e)*(Math.cos(Math.toRadians(q))*Math.cos(Math.toRadians(q))));
				            
			            }
			            
			            r1 = Math.sqrt(top/bottom);
			            
			            double x1 = r1*Math.cos(Math.toRadians(q));
			            double y1 = r1*Math.sin(Math.toRadians(q));
			            x1 += h;
			            y1 += k;
			            
			            
			            for(int l = 1; l > -1; l--) 
			            {
			            	double w = adderopp;
							double p = thetaopp;
							
							if(l == 0 && (moveRight || moveLeft))
							{
								w = -1*thetaopp;
								p = adderopp*-1;
							}
			            	
			            	for(; w <= p; w+=0.5)
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
						            bottom2 = 1-((e2*e2)*(Math.sin(Math.toRadians(w))*Math.sin(Math.toRadians(w))));
						            
					        	}
					        	else
					        	{
						        	f2 = Math.sqrt((a2*a2)-(b2*b2));
						        	
						        	double e2 = f2/a2;
						        	
						            top2 = (a2*a2)*(1-(e2*e2));
						            bottom2 = 1-((e2*e2)*(Math.cos(Math.toRadians(w))*Math.cos(Math.toRadians(w))));
						          
					        	}
					            
					        	r2 = Math.sqrt(top2/bottom2);
					            double x2 = r2*Math.cos(Math.toRadians(w));
					            double y2 = r2*Math.sin(Math.toRadians(w));
					         
					            x2 += h1.getH();
					            y2 += h1.getK();	
					            
					            if(Math.abs(x2-x1) < 1 && Math.abs(y2-y1) < 1)
					            {
					            	d[0] = x1;
					            	d[1] = y1*-1;
					            	latestIntersection = d;
					            	return d;
					            }
					          }
			            }
					}
				}
			}
		}
		lastTouched = null;
		latestIntersection = d;
		return d;
		
		
	}
	
	
	public double[] getOppositeIntersection()
	{
		double d[] = {-1,-1};
		Hitbox Checker = lastTouched;
		
		double yThing = 0, xThing = 0;
		
		if(Checker != null)
		{
			yThing = latestIntersection[1] - lastTouched.getK()*-1;
			xThing = latestIntersection[0] - lastTouched.getH();
		}
		
		if(latestIntersection[0] != -1 && Checker.getClass().equals(new OvalHitbox(1,1,1,1,1).getClass()))
		{
			if(xThing < 0)
			{
				d[0] = xThing - xThing*2;
				d[0] += lastTouched.getH();
			}
			else if(xThing > 0)
			{
				d[0] = xThing - xThing*2;
				d[0] += lastTouched.getH();
			}
			if(yThing < 0)
			{
				d[1] = yThing - yThing*2;
				d[1] += lastTouched.getK()*-1;
				return d;
			}
			else if(yThing > 0)
			{
				d[1] = yThing - yThing*2;
				d[1] += lastTouched.getK()*-1;
				return d;
			}
		}
		
		return d;
	}
	
	
}
