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

	public double[] intersects(Hitbox h1)
	{
		return null;
	}

	public double[] getOppositeIntersection()
	{
		return null;
	}
	
}
