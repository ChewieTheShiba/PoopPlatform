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
		for(double q = h-(a/2.0); q <= h+(a/2.0); q += 10)
		{
	        for(double w = h1.getH()-(h1.getA()/2.0); w <= h1.getH()+(h1.getA()/2.0); w += 10)
	          {
	            int ha = h1.getA();
	            int hb = h1.getB();
	            int hh = h1.getH();
	            int hk = h1.getK();
	            
	              
	            long ab = (a*a)*(b*b);
	            System.out.println( q + " \t" + w);
	            System.out.println("ab\t" + ab);
	            long bb = (int)((b*b)*((q-h)*(q-h)));
	            System.out.println("bb\t" + bb);
	            double y1 = k + Math.sqrt((ab-bb)/(a*a));
	            System.out.println("y1\t" + y1);
	            double negy1 = k - Math.sqrt((ab-bb)/(a*a));
	            System.out.println("negy1\t" + negy1);
	
	            ab = (ha*ha)*(hb*hb);
	            System.out.println("ab2\t" + ab);
	            bb = (int)((hb*hb)*((w-hh)*(w-hh)));
	            System.out.println("bb2\t" + bb);
	            double y2 = hk + Math.sqrt((ab-bb)/(ha*ha));
	            System.out.println("y2\t" + y2);
	            double negy2 = hk - Math.sqrt((ab-bb)/(ha*ha));
	            System.out.println("negy2\t" + negy2);
	            
	            if(q == 170 && w == 175)
	            {
	            	System.out.println(y1 + "\t" + negy1 + "\t" + y2 + "\t" + negy2);
	            }
	            
	            if(Math.abs(q-w) < 10 && Math.abs(y2-y1) < 20 || Math.abs(q-w) < 10 && Math.abs(negy2-y1) < 20)
	            {
	              d[0] = q;
	              d[1] = y1;
	              latestIntersection = d;
	              System.out.println("fadonbhfabdfbnaodf");
	              return d;
	            }
	            if(Math.abs(q-w) < 10 && Math.abs(y2-negy1) < 20 || Math.abs(q-w) < 10 && Math.abs(negy2-negy1) < 20)
	            {
	              d[0] = q;
	              d[1] = negy1;
	              latestIntersection = d;
	              System.out.println("fadonbhfabdfbnaodf");
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
