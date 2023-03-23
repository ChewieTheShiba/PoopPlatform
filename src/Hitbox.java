public class Hitbox {

	private int h, k, a, b, startx, starty;
	
	public Hitbox(int a, int b, int h, int k)
	{
		this.a = a;
		this.b = b;
		this.h = h;
    this.k = k;
	}
	
	public double[] intersects(Hitbox h1)
	{
    double d[] = {-1, -1};
		for(int q = (-1 * a)+h; q <= a; q += 0.1)
      {
        for(int w = (-1 * h1.getA()); w < h1.getA(); w += 0.1)
          {
            int ha = h1.getA();
            int hb = h1.getB();
            int hk = h1.getK();
              
            int ab = (a*a)*(b*b);
            int bb = (b*b)*((q-h)*(q-h));
            int y1 = k + Math.sqrt((ab*bb)/(a*a));
            int negy1 = k - Math.sqrt((ab*bb)/(a*a));

            ab = (ha*ha)*(hb*hb);
            bb = (hb*hb)*((w-hk)*(w-hk));
            int y2 = hk + Math.sqrt((ab*bb)/(ha*ha));
            int negy2 = hk - Math.sqrt((ab*bb)/(ha*ha));
            
            if(Math.abs(q-w) < 0.1 && Math.abs(y2-y1) < 0.1 || Math.abs(q-w) < 0.1 && Math.abs(negy2-y1) < 0.1)
            {
              d[0] = q;
              d[1] = y1;
            }
            if(Math.abs(q-w) < 0.1 && Math.abs(y2-negy1) < 0.1 || Math.abs(q-w) < 0.1 && Math.abs(y2-y1) < 0.1)
            {
              d[0] = q;
              d[1] = negy1;
            }
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
}
