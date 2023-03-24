public class Hitbox {

	private int h, k, a, b, startx, starty;
	private double[] latestIntersection = {-1,-1};
	
	public Hitbox(int h, int k, int a, int b)
	{
		this.a = a;
		this.b = b;
		this.h = h;
		this.k = k*-1;
	}
	
	public double[] intersects(Hitbox h1)
	{
		double d[] = {-1, -1};
		for(double q = 1; q <= 360; q+=.5)
		{
			double f = 0;
            if(a > b)
            	f = Math.sqrt((a*a)-(b*b));
            else
            	f = Math.sqrt((b*b)-(a*a));
            
            double e = f/a;
            
            double top = (a*a)*(1-(e*e));
            double bottom = 1-((e*e)*(Math.cos(q)*Math.cos(q)));
            
            double r1 = Math.sqrt(top/bottom);
            
            double x1 = r1*Math.cos(q);
            double y1 = r1*Math.sin(q);
            x1 += h;
            y1 += k;
            
	        for(double w = 0; w <= 360; w+=.5)
	          {
	        	double a2 = h1.getA();
	        	double b2 = h1.getB();
	        	
	        	double f2 = 0;
	        	
	        	if(a2 > b2)
	            	f2 = Math.sqrt((a2*a2)-(b2*b2));
	            else
	            	f2 = Math.sqrt((b2*b2)-(a2*a2));
	        	
	        	double e2 = f2/a2;
	        	
	            double top2 = (a2*a2)*(1-(e2*e2));
	            double bottom2 = 1-((e2*e2)*(Math.cos(w)*Math.cos(w)));
	            
	            double r2 = Math.sqrt(top2/bottom2);
	            
	            double x2 = r2*Math.cos(w);
	            double y2 = r2*Math.sin(w);
	            x2 += h1.getH();
	            y2 += h1.getK();
	           
	            
	            if(Math.abs(x2-x1) < 1 && Math.abs(y2-y1) < 1)
	            {
	            	d[0] = x1;
	            	d[1] = y2;
	            	System.out.println(r1 + "\t" + r2);
	            	System.out.println("a" + "\t"+ a2);
	            	System.out.println("b" + "\t"+ b2);
	            	System.out.println("e" + "\t"+ e2);
	            	System.out.println("f" + "\t"+ f2);
	            	System.out.println(q+ "\t" + w);
	            	System.out.println("xxxxx\t" + x1 + "\nyyyy\t" + y1);
	            	System.out.println("xxxxx2\t" + x2 + "\nyyyy2\t" + y2);
	            	latestIntersection = d;
	            	return d;
	            }
	            
	            
	          }
		}
		latestIntersection = d;
        return d;
	}
	
	
	public double[] getOppositeInteresection()
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
