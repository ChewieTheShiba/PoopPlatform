import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.*;
import java.util.*;
import javax.swing.Timer;

public class PoopPanel extends JPanel
{
	// variables for the overall width and height
	private int bruh, w, h, px, py, pw, ph, sx, sy, sw, sh, p1YVelocity, p2YVelocity, p1MoveSpeed, p2MoveSpeed,
			p1LaunchDirection, p2LaunchDirection, ogpx, ogpy, ogsx, ogsy, p1JumpStart, p2JumpStart, p1SelectX,
			p1SelectY, p2SelectX, p2SelectY, p1Thingy, p2Thingy, c1Lives, c2Lives;
	private double p1Knockback, p2Knockback, p1LaunchSpeed, p2LaunchSpeed, p1EndXTraj, p2EndXTraj, p1KnockbackTheta,
			p2KnockbackTheta;
	private ImageIcon startUpAnimation, startUpScreen, c1Image, c2Image, projectile;
	private Timer startUpWait, ticker, p1Knockbacker, p2Knockbacker;
	private boolean started, gettingReadyToPlay, readyToPlay, p1KnockingBack, p2KnockingBack, stageSelectReady,
			characterSelectReady, p1StageSelected, p2StageSelected, p1CharacterSelected, p2CharacterSelected,
			characterReady, p1Stopper, p2Stopper;
	private final int GRAVITY, JUMPHEIGHT;
	private Character c1, c2;
	private final Character PoopDefender, Neff, Kuma, Mob;
	private final Character[][] characterSelect;
	private final Stage[]stageSelect;
	private Stage stageSelected, Battlefield, Dreamland;
	private ArrayList<Hitbox> hitboxes;
	private ArrayList<RectangleHitbox> stageHitboxes;
	private ArrayList<Hitbox> toDelete;

	// sets up the initial panel for drawing with proper size
	public PoopPanel(int w, int h) throws IOException
	{
		this.w = w;
		this.h = h;
		this.setPreferredSize(new Dimension(w, h));

		// sets up listeners
		this.addKeyListener(new keyListen());
		this.setFocusable(true);

		// sets up gravity stuff
		p1YVelocity = 1;
		p2YVelocity = 1;
		GRAVITY = 2;

		// sets up stuff to start game and test if its ready to start
		started = false;
		gettingReadyToPlay = true;
		readyToPlay = false;
		characterSelectReady = false;
		stageSelectReady = false;
		p1StageSelected = false;
		p1CharacterSelected = false;
		p2StageSelected = false;
		p2CharacterSelected = false;
		characterReady = false;

		p1KnockingBack = false;

		/*
		 * sets up startup animation and wait and then waiting screen Animation will
		 * play and then once it stops screen will appear and wait for the player to
		 * press start
		 */
		startUpScreen = new ImageIcon("assets/startUpAnimation.gif");
		// startUpScreen = new
		// ImageIcon(ImageIO.read(getClass().getResource("assets/startUpAnimation.gif")));
		startUpWait = new Timer(100, new actionListener());
		startUpWait.start();

		// sets up timer for 1 tick of the game
		ticker = new Timer(20, new actionListener());

		// sets up jumping mechanism
		p1Knockbacker = new Timer(20, new actionListener());
		p2Knockbacker = new Timer(20, new actionListener());
		JUMPHEIGHT = 20;

		// sets up the object for each character you can select
		/*
		 * PoopDefender = new PoopDefender(); Neff = new Neff(); Kuma = new Kuma(); Mob
		 * = new Mob();
		 */

		characterSelect = new Character[2][2];
		stageSelect = new Stage[2];

		// temporary instansiations
		PoopDefender = new PoopDefender();
		Neff = new Neff();
		Kuma = new Neff();
		Mob = new Neff();

		characterSelect[0][0] = PoopDefender;
		characterSelect[0][1] = Neff;
		characterSelect[1][0] = Kuma;
		characterSelect[1][1] = Mob;

		// instantiates characters
		c1 = new Character();
		c2 = new Character();
		projectile = new ImageIcon("assets/Poop Defender/SpecialProjectile.png");

		//Sets up hitboxes for battlefields
		ArrayList<RectangleHitbox> Battlefields = new ArrayList<RectangleHitbox>();
		Battlefields.add(new RectangleHitbox(366, 524, 1194, 70, 0, 0));
		Battlefields.add(new RectangleHitbox(506, 328, 286, 32, 0, 0));
		Battlefields.add(new RectangleHitbox(824, 155, 286, 32, 0, 0));
		Battlefields.add(new RectangleHitbox(1133, 328, 286, 32, 0, 0));
		
		ArrayList<RectangleHitbox> Dreamlands = new ArrayList<RectangleHitbox>();
		Dreamlands.add(new RectangleHitbox(463, 817, 998, 263, 0, 0));
		Dreamlands.add(new RectangleHitbox(572, 596, 200, 32, 0, 0));
		Dreamlands.add(new RectangleHitbox(839, 449, 200, 32, 0, 0));
		Dreamlands.add(new RectangleHitbox(1157, 597, 200, 32, 0, 0));
		
		Battlefield = new Stage(new ImageIcon("assets/Battlefield.png"), Battlefields);
		
		Dreamland = new Stage(new ImageIcon("assets/Dreamland.png"), Dreamlands);
		
		stageHitboxes = new ArrayList<RectangleHitbox>();
		
		stageSelect[0] = Battlefield;
		stageSelect[1] = Dreamland;
		
		toDelete = new ArrayList<Hitbox>();
	}

	// all graphical components go here
	// this.setBackground(Color c) for example will change background color
	public void paintComponent(Graphics g)
	{
		// this line sets up the graphics - always needed
		super.paintComponent(g);

		if (started)
		{	
			stageSelected.getBackground().paintIcon(this, g, 0, 0);
			
			double[] p1Intersection =
			{ -1, -1 }, p2Intersection =
			{ -1, -1 }, p1OppIntersection =
			{ -1, -1 }, p2OppIntersection =
			{ -1, -1 };
			px = c1.getHitbox().getH() - c1.getHitbox().getA();
			py = c1.getHitbox().getK() * -1 - c1.getHitbox().getB();
			pw = c1.getHitbox().getA();
			ph = c1.getHitbox().getB();
			sx = c2.getHitbox().getH() - c2.getHitbox().getA();
			sy = c2.getHitbox().getK() * -1 - c2.getHitbox().getB();
			sw = c2.getHitbox().getA();
			sh = c2.getHitbox().getB();

			c1Image = c1.getCurrentPlayerImage();
			c2Image = c2.getCurrentPlayerImage();
			
			c1Image.paintIcon(this, g, px - c1.getXOffPut(), py - c1.getYOffPut());
			c2Image.paintIcon(this, g, sx - c2.getXOffPut(), sy - c2.getYOffPut());

			ticker.start();
			// all drawings below here:
			g.setColor(Color.black);
			g.drawOval(px, py, pw * 2, ph * 2);
			g.drawOval(sx, sy, sw * 2, sh * 2);
			g.setFont(new Font("Comic Sans", Font.PLAIN, 30));
			g.drawString("Player 1 Lives: " + c1Lives, 200, 1050);
			g.drawString("Player 2 Lives: " + c2Lives, 1520, 1050);
			

			if (c1.getAttack4Hitbox() != null)
			{
				Hitbox l = c1.getAttack4Hitbox();
				g.drawRect(l.getH(), l.getK(), l.getA(), l.getB());
			}

			for (int i = 0; i < hitboxes.size(); i++)
			{
				
				Hitbox h = hitboxes.get(i);

				if (h != null && h.getId().equals("Projectile"))
					projectile.paintIcon(this, g, h.getH(), h.getK());

				if (h != null && !h.equals(c1.getHitbox()) && !h.getId().equals((c1.getName())))
				{
					p1Intersection = h.intersects(c1.getHitbox());
					p1OppIntersection = h.getOppositeIntersection();
				}

				if (h != null && !h.equals(c2.getHitbox()) && !h.getId().equals(c2.getName()))
				{
					p2Intersection = h.intersects(c2.getHitbox());
					p2OppIntersection = h.getOppositeIntersection();
				}

				if (p1Intersection[0] != -1 && h.getDamage() != 0 && !h.getId().equals("Player 1"))
				{
					if(h.getId().equals("Projectile"))
						toDelete.add(h);
					
					System.out.println(h.getId());
					
					g.setColor(Color.red);
					g.drawOval((int) p1Intersection[0], (int) p1Intersection[1], 20, 20);
					g.drawOval((int) p1OppIntersection[0], (int) p1OppIntersection[1], 20, 20);
					
					c1.setHP(c1.getHP()+h.getDamage());
					
					System.out.println("damage " + h.getKB());

					p1Knockback = getp1Knockback(h.getDamage(), h.getKB());

					p1KnockbackTheta = Math.atan(
							(p1Intersection[1] - p1OppIntersection[1]) / (p1Intersection[0] - p1OppIntersection[0]));

					// Sets the end of knockback trajectory halfway through the arc
					p1EndXTraj = 2 * p1Knockback * Math.sin(p1KnockbackTheta);
					p1EndXTraj /= p1YVelocity;
					p1EndXTraj /= 2;

					p1LaunchSpeed = p1Knockback * 0.03;

					p1LaunchDirection = 1;

					if (p1OppIntersection[0] - c1.getHitbox().getH() < 0)
					{
						p1EndXTraj = p1Intersection[0] - p1EndXTraj;
						p1LaunchDirection = -1;
						p1KnockbackTheta *= -1;
					} else
						p1EndXTraj = p1Intersection[0] - p1EndXTraj;
					if (p1OppIntersection[1] - c1.getHitbox().getK() < 0)
						;

					ogpx = px;
					ogpy = py;

					p1KnockingBack = true;
					p1Knockbacker.start();
				}

				else if (p1Intersection[0] != -1)
				{
					double xkb = h.getKB();
					double ykb = h.getKB();

					if (xkb >= pw)
						xkb = pw - 1;
					if (ykb >= ph)
						ykb = ph - 1;

					if (h.getMoveRight())
						updatePlayer1Position(px + (int) xkb, py);
					if (h.getMoveLeft())
						updatePlayer1Position(px - (int) xkb, py);
					if (h.getMoveUp() || h.getMoveDown())
					{
						if (c1.getHitbox().getH() > h.getH())
							updatePlayer1Position(px + (int) xkb, py);
						else
							updatePlayer1Position(px - (int) xkb, py);
					}
				}

				if (h != null && p2Intersection[0] != -1 && h.getDamage() != 0 &&  !h.getId().equals("Player 2"))
				{
					if(h.getId().equals("Projectile"))
						toDelete.add(h);
					
					g.setColor(Color.red);
					g.drawOval((int) p2Intersection[0], (int) p2Intersection[1], 20, 20);
					g.drawOval((int) p2OppIntersection[0], (int) p2OppIntersection[1], 20, 20);

					c2.setHP(c2.getHP()+h.getDamage());

					p2Knockback = getp2Knockback(h.getDamage(), h.getKB());

					p2KnockbackTheta = Math.atan(
							(p2Intersection[1] - p2OppIntersection[1]) / (p2Intersection[0] - p2OppIntersection[0]));

					// Sets the end of knockback trajectory halfway through the arc
					p2EndXTraj = 2 * p2Knockback * Math.sin(p2KnockbackTheta);
					p2EndXTraj /= p2YVelocity;
					p2EndXTraj /= 2;

					p2LaunchSpeed = p2Knockback * 0.03;

					p2LaunchDirection = 1;

					if (p2OppIntersection[0] - c2.getHitbox().getH() < 0)
					{
						p2EndXTraj = p2Intersection[0] - p2EndXTraj;
						p2LaunchDirection = -1;
						p2KnockbackTheta *= -1;
					} else
						p2EndXTraj = p2Intersection[0] + p2EndXTraj;
					if (p2OppIntersection[1] - c2.getHitbox().getK() < 0)
						;

					ogsx = sx;
					ogsy = sy;

					p2KnockingBack = true;
					p2Knockbacker.start();
				}

				else if (h != null && p2Intersection[0] != -1)
				{
					double xkb = h.getKB();
					
					System.out.println(xkb);

					if (xkb >= sw)
						xkb = sw - 1;

					if (h.getMoveRight())
						updatePlayer2Position(sx + (int) xkb, sy);
					if (h.getMoveLeft())
						updatePlayer2Position(sx - (int) xkb, sy);
					if (h.getMoveUp() || h.getMoveDown())
					{
						if (c2.getHitbox().getH() > h.getH())
							updatePlayer2Position(sx + (int) xkb, sy);
						else
							updatePlayer2Position(sx - (int) xkb, sy);
					}
				}

				if(stageHitboxes != null)
				{
					for(int z = 0; z < toDelete.size(); z++)
					{
						hitboxes.remove(toDelete.get(z));
					}
					
				}
			}
		} else if (readyToPlay || gettingReadyToPlay)
		{
			startUpScreen.paintIcon(this, g, 0, 0);
		} else if (stageSelectReady)
		{
			hitboxes = new ArrayList<Hitbox>();
			hitboxes.add(new OvalHitbox(-100, -100, 1, 1, 0, 0));
			
			new ImageIcon("assets/BattlefieldSelect.png").paintIcon(this, g, 0, 0);
			new ImageIcon("assets/DreamlandSelect.png").paintIcon(this, g, w / 2, 0);

			g.setColor(Color.GREEN);
			g.drawRect(p1SelectX * w / 2, p1SelectY * h / 2, w / 2, h);
			g.setFont(new Font("Comic Sans", Font.PLAIN, 15));
			g.setColor(Color.WHITE);
			g.drawString("Player 1", p1SelectX * w / 2, p1SelectY * h + 15);

			g.setColor(Color.BLUE);
			g.drawRect(p2SelectX * w / 2, p2SelectY * h / 2, w / 2, h);
			g.setColor(Color.WHITE);
			g.drawString("Player 2", p2SelectX * w / 2, p2SelectY * h + 15);

			if (p1StageSelected && p2StageSelected)
			{
				 Random rand = new Random(); 
				 if(rand.nextInt(2) == 0) stageSelected = stageSelect[p1SelectX]; 
				 else stageSelected = stageSelect[p2SelectX];
				 
				stageHitboxes = stageSelected.getHitboxes();

				p1StageSelected = false;
				p2StageSelected = false;
				characterSelectReady = true;
				stageSelectReady = false;
				p1SelectX = 0;
				p1SelectY = 0;
				p2SelectX = 0;
				p2SelectY = 0;
				repaint();
			}
		} else if (characterSelectReady)
		{
			new ImageIcon("assets/Poop Defender/PoopDefenderIdleRight.png").paintIcon(this, g, 0, 0);
			g.setFont(new Font("Comic Sans", Font.PLAIN, 40));
			g.setColor(Color.ORANGE);
			g.fillRect(0, h / 2 - 200, w / 2, 200);
			g.setColor(Color.BLACK);
			g.drawString("Poop Defender", w / 4 - 135, 2 * h / 5);
			new ImageIcon("assets/Poop Defender/PoopDefenderIdleRight.png").paintIcon(this, g, w / 2, 0);
			new ImageIcon("assets/Poop Defender/PoopDefenderIdleRight.png").paintIcon(this, g, 0, h / 2);
			new ImageIcon("assets/Poop Defender/PoopDefenderIdleRight.png").paintIcon(this, g, w / 2, h / 2);

			g.setColor(Color.GREEN);
			g.drawRect(p1SelectX * w / 2, p1SelectY * h / 2, w / 2, h / 2);
			g.setFont(new Font("Comic Sans", Font.PLAIN, 15));
			g.setColor(Color.BLACK);
			g.drawString("Player 1", p1SelectX * w / 2, p1SelectY * h / 2 + 15);

			g.setColor(Color.BLUE);
			g.drawRect(p2SelectX * w / 2, p2SelectY * h / 2, w / 2, h / 2);
			g.setColor(Color.BLACK);
			g.drawString("Player 2", p2SelectX * w / 2, p2SelectY * h / 2 + 15);
			
			c1Lives = 3;
			c2Lives = 3;

			if (p1CharacterSelected && p2CharacterSelected)
			{
				if (characterSelect[p1SelectY][p1SelectX].getClass().equals(PoopDefender.class))
					c1 = new PoopDefender();
				if (characterSelect[p1SelectY][p1SelectX].getClass().equals(Neff.class))
					c1 = new Neff();
				if (characterSelect[p1SelectY][p1SelectX].getClass().equals(PoopDefender.class))
					c1 = new PoopDefender();
				if (characterSelect[p1SelectY][p1SelectX].getClass().equals(PoopDefender.class))
					c1 = new PoopDefender();
				c1.setName("Player 1");
				c1.setMoveLeft(false);
				c1.setMoveRight(false);
				c1.setJumping(false);
				c1.setDoubleJumping(false);
				c1.setH(700);
				c1.setK(-200);
				hitboxes.add(c1.attack1Hitbox);
				hitboxes.add(c1.attack2Hitbox);
				hitboxes.add(c1.attack3Hitbox);
				hitboxes.add(c1.attack4Hitbox);
				if (characterSelect[p2SelectY][p2SelectX].getClass().equals(PoopDefender.class))
					c2 = new PoopDefender();
				if (characterSelect[p2SelectY][p2SelectX].getClass().equals(Neff.class))
					c2 = new Neff();
				if (characterSelect[p2SelectY][p2SelectX].getClass().equals(PoopDefender.class))
					c2 = new PoopDefender();
				if (characterSelect[p2SelectY][p2SelectX].getClass().equals(PoopDefender.class))
					c2 = new PoopDefender();
				c2.setName("Player 2");
				c2.setMoveLeft(false);
				c2.setMoveRight(false);
				c2.setJumping(false);
				c2.setDoubleJumping(false);
				c2.setH(1300);
				c2.setK(-200);
				hitboxes.add(c2.attack1Hitbox);
				hitboxes.add(c2.attack2Hitbox);
				hitboxes.add(c2.attack3Hitbox);
				hitboxes.add(c2.attack4Hitbox);
				hitboxes.add(c1.getHitbox());
				hitboxes.add(c2.getHitbox());

				p1CharacterSelected = false;
				p2CharacterSelected = false;
				started = true;
				characterSelectReady = false;
				repaint();
			}
		}
	}

	// happens every time a tick occurs
	public void updateP1()
	{
		// jumping is if else is falling
		if (c1.getJumping() || c1.getDoubleJumping())
		{
			int thingy = -1;
			int ogy = 0;

			if (c1.getFalling())
			{
				ogy = (c1.getHitbox().getK() * -1) + c1.getHitbox().getB();
				thingy = p1YCoordIsTouching(ogy, Math.abs(p1YVelocity));
			}

			if (thingy != -1)
			{
				p1YVelocity = GRAVITY;
				c1.setFalling(false);
				c1.setJumping(false);
				c1.setdoubleJumping(false);
				updatePlayer1Position(px, thingy - ph * 2);
			} else
			{
				updatePlayer1Position(px, py - p1YVelocity);
				p1YVelocity -= GRAVITY;

				if (p1YVelocity < 0)
				{
					c1.setFalling(true);
					c1.setMoveUp(false);
				}

				if (p1YVelocity > JUMPHEIGHT)
					p1YVelocity = JUMPHEIGHT;
			}

		} else if (!p1KnockingBack)
		{
			if (p1YVelocity > 10)
				p1YVelocity = 10;
			else
				p1YVelocity *= GRAVITY;

			c1.setFalling(true);

			int ogy = (c1.getHitbox().getK() * -1) + c1.getHitbox().getB();
			int thingy = p1YCoordIsTouching(ogy, p1YVelocity);
			if (thingy != -1)
			{
				p1YVelocity = GRAVITY;
				c1.setFalling(false);
				updatePlayer1Position(px, thingy - ph * 2);
			} else
				updatePlayer1Position(px, py + p1YVelocity);
		}

		// does while moving left or right
		if (c1.getMoveRight() && !c1.getTryTilt())
		{
			updatePlayer1Position(px + p1MoveSpeed, py);
		}
		// updatePlayer1Position(px+p1MoveSpeed, py);
		if (c1.getMoveLeft() && !c1.getTryTilt())
			updatePlayer1Position(px - p1MoveSpeed, py);

		if (c1.getTryTilt() && !p1KnockingBack)
		{
			if (c1.getMoveRight())
			{
				c1.setMoveRight(false);
				c1.setTryTilt(false);
				c1.rightTilt();
				c1Image = c1.getCurrentPlayerImage();
			}
			if (c1.getMoveLeft())
			{
				c1.setMoveLeft(false);
				c1.setTryTilt(false);
				c1.leftTilt();
				c1Image = c1.getCurrentPlayerImage();
			}
			if (c1.getMoveUp())
			{
				c1.setTryTilt(false);
				c1.upTilt();
				c1Image = c1.getCurrentPlayerImage();
			}
			if (c1.getMoveDown())
			{
				c1.setTryTilt(false);
				c1.downTilt();
				c1Image = c1.getCurrentPlayerImage();
			}
		}
		if (c1.getTrySpecial())
		{
			c1.special();
			hitboxes.add(c1.getSpecialProjectiles().get(c1.getSpecialProjectiles().size() - 1));
			c1.setTrySpecial(false);
			c1Image = c1.getCurrentPlayerImage();
		}
		
		if(c1.getHitbox().getK()*-1-c1.getHitbox().getB() >= 1080 || c1.getHitbox().getK()*-1+c1.getHitbox().getB() <= 0 || c1.getHitbox().getH()+c1.getHitbox().getA() <= 0|| c1.getHitbox().getH()-c1.getHitbox().getA() >= 1920)
		{
			c1Lives--;
			
			if (c1Lives == 0)
			{
				c1Lives--;
				started = false;
				readyToPlay = true;
				startUpWait.start();
			}
			else
			{
				c1.setMoveLeft(false);
				c1.setMoveRight(false);
				c1.setJumping(false);
				c1.setDoubleJumping(false);
				c1.setH(700);
				c1.setK(-200);
				c1.setHP(0);
				c1.setStopMoving(false);
			}
			
		}
	}

	// checks if p1 is touching ceiling or platform or wall
	public int p1YCoordIsTouching(int x, int increase)
	{
		if (x-ph*2 >= 1080)
			return 1080+ph*4;
		if (x <= 0)
			return 0;

		OvalHitbox t = c1.getHitbox();
		OvalHitbox temp = new OvalHitbox(t.getH(), t.getK() * -1 + increase * 2, t.getA(), t.getB(),
				t.getDamage(), t.getKB());

		temp.setMoveDown(t.getMoveDown());
		temp.setMoveUp(t.getMoveUp());
		temp.setMoveRight(t.getMoveRight());
		temp.setMoveLeft(t.getMoveLeft());

		for (RectangleHitbox h : stageHitboxes)
		{
			double[] d = temp.intersects(h);

			if (d[0] != -1)
			{
				if (c1.getFalling() && t.getK() * -1 + t.getB() <= h.getK())
				{
					c1.setFalling(false);
					p1KnockingBack = false;
					c1.setJumping(false);
					c1.setDoubleJumping(false);
					return h.getK();
				} else if (t.getK() * -1 + t.getB() >= h.getK())
				{
					if (c1.getMoveLeft() && t.getK() * -1 <= h.getK())
					{
						c1.setFalling(false);
						p1KnockingBack = false;
						c1.setJumping(false);
						c1.setDoubleJumping(false);
						return h.getK();
					} else if (c1.getMoveLeft() && !c1.getJumping())
					{
						updatePlayer1Position(px + pw, py);
					}
					if (c1.getMoveRight() && t.getK() * -1 <= h.getK())
					{
						c1.setFalling(false);
						p1KnockingBack = false;
						c1.setJumping(false);
						c1.setDoubleJumping(false);
						return h.getK();
					} else if (c1.getMoveRight() && !c1.getJumping())
					{
						updatePlayer1Position(px - pw, py);
					}
					if (c1.getJumping())
					{
						c1.setMoveUp(false);
						updatePlayer1Position(px, py + ph);
					}
					p1KnockingBack = false;
					p1YVelocity = GRAVITY;
					c1.setFalling(true);
					return t.getK() * -1 + t.getB();
				}
			}
		}

		return -1;
	}

	public int p2YCoordIsTouching(int x, int increase)
	{
		if (x-sh*2 >= 1080)
			return 1080+sh*4;
		if (x <= 0)
			return 0;

		OvalHitbox t = c2.getHitbox();
		OvalHitbox temp = new OvalHitbox(t.getH(), t.getK() * -1 + increase * 2, t.getA(), t.getB(),
				t.getDamage(), t.getKB());

		temp.setMoveDown(t.getMoveDown());
		temp.setMoveUp(t.getMoveUp());
		temp.setMoveRight(t.getMoveRight());
		temp.setMoveLeft(t.getMoveLeft());

		for (RectangleHitbox h : stageHitboxes)
		{
			double[] d = temp.intersects(h);

			if (d[0] != -1)
			{
				if (c2.getFalling() && t.getK() * -1 + t.getB() <= h.getK())
				{
					c2.setFalling(false);
					p2KnockingBack = false;
					c2.setJumping(false);
					c2.setDoubleJumping(false);
					return h.getK();
				} else if (t.getK() * -1 + t.getB() >= h.getK())
				{
					if (c2.getMoveLeft() && t.getK() * -1 <= h.getK())
					{
						c2.setFalling(false);
						p2KnockingBack = false;
						c2.setJumping(false);
						c2.setDoubleJumping(false);
						return h.getK();
					} else if (c2.getMoveLeft() && !c2.getJumping())
					{
						updatePlayer1Position(sx + sw, sy);
					}
					if (c2.getMoveRight() && t.getK() * -1 <= h.getK())
					{
						c2.setFalling(false);
						p2KnockingBack = false;
						c2.setJumping(false);
						c2.setDoubleJumping(false);
						return h.getK();
					} else if (c2.getMoveRight() && !c2.getJumping())
					{
						updatePlayer1Position(sx - sw, sy);
					}
					if (c2.getJumping())
					{
						c2.setMoveUp(false);
						updatePlayer1Position(sx, sy + sh);
					}
					p2KnockingBack = false;
					p2YVelocity = GRAVITY;
					c2.setFalling(true);
					return t.getK() * -1 + t.getB();
				}
			}
		}

		return -1;
	}

	public void updatePlayer1Position(int x, int y)
	{
		px = x;
		py = y;
		c1.setH(x + pw);
		c1.setK((y + ph) * -1);
	}

	public double getp1Knockback(double attackPower, double baseKnockBack)
	{
		/*
		 * This formula is used off a wiki page for the smash bros formula for
		 * calculating distance based based on weight, percentage, attack power, etc.
		 * since I figured instead of having a convoluted bad formula thats weird it's
		 * better to use one that works well
		 */

		// ALL RECOMENDATIONS ARE VERY WORK IN PROGRESS IM MAKING A LARGE
		// ESTIMATION FOR THE TIME BEING

		// recomended weights
		// light is 50ish
		// medium is 100ish
		// heavy is 150-200ish

		// recomended knockback
		// light is 100ish
		// medium is 250-300ish
		// killers are 400ish

		// when deciding how far to knock them in x and y take the distance from
		// intersection to p1OppIntersection
		// then do x/dist and y/dist
		// then multiply those ratios by knockback
		// then add that knockbackx and knockbacky to the players x and y

		double percentage = Math.pow(c1.getHP(), 1.35);;
		double weight = c1.getWeight();

		double knockBack = (percentage / 10) + (percentage * attackPower / 20);
		System.out.println("1\t" + knockBack);
		knockBack *= 200 / (weight + 100);
		System.out.println("2\t" + knockBack);
		knockBack *= 1.4;
		System.out.println("3\t" + knockBack);
		knockBack += 18;
		System.out.println("4\t" + knockBack);
		knockBack += baseKnockBack;
		System.out.println("5\t" + knockBack);

		System.out.println("Knockback" + knockBack);

		return knockBack;

	}
	
	public double getp2Knockback(double attackPower, double baseKnockBack)
	{
		double percentage = Math.pow(c2.getHP(), 1.35);
		double weight = c2.getWeight();

		double knockBack = (percentage / 10) + (percentage * attackPower / 20);
		System.out.println("1\t" + knockBack);
		knockBack *= 200 / (weight + 100);
		System.out.println("2\t" + knockBack);
		knockBack *= 1.4;
		System.out.println("3\t" + knockBack);
		knockBack += 18;
		System.out.println("4\t" + knockBack);
		knockBack += baseKnockBack;
		System.out.println("5\t" + knockBack);

		System.out.println("Knockback" + knockBack);

		return knockBack;

	}

	public void updateP2()
	{
		if (c2.getJumping() || c2.getDoubleJumping())
		{
			int thingy = -1;
			int ogy = 0;

			if (c2.getFalling())
			{
				ogy = (c2.getHitbox().getK() * -1) + c2.getHitbox().getB();
				thingy = p2YCoordIsTouching(ogy, p1YVelocity);
			}

			if (thingy != -1)
			{
				p2YVelocity = GRAVITY;
				c2.setFalling(false);
				c2.setJumping(false);
				c2.setdoubleJumping(false);
				updatePlayer2Position(sx, thingy - sh * 2);
			} else
			{
				updatePlayer2Position(sx, sy - p2YVelocity);
				p2YVelocity -= GRAVITY;

				if (p2YVelocity < 0)
				{
					c2.setFalling(true);
					c2.setMoveUp(false);
				}

				if (p2YVelocity > JUMPHEIGHT)
					p2YVelocity = JUMPHEIGHT;
			}

		} else if (!p2KnockingBack)
		{
			if (p2YVelocity > 10)
				p2YVelocity = 10;
			else
				p2YVelocity *= GRAVITY;

			c2.setFalling(true);

			int ogy = (c2.getHitbox().getK() * -1) + c2.getHitbox().getB();
			int thingy = p2YCoordIsTouching(ogy, p2YVelocity);
			if (thingy != -1)
			{
				p2YVelocity = GRAVITY;
				c2.setFalling(false);
				updatePlayer2Position(sx, thingy - sh * 2);
			} else
				updatePlayer2Position(sx, sy + p2YVelocity);
		}

		// does while moving left or right
		if (c2.getMoveRight() && !c2.getTryTilt())
		{
			updatePlayer2Position(sx + p2MoveSpeed, sy);
		}
		// updatePlayer1Position(px+p1MoveSpeed, py);
		if (c2.getMoveLeft() && !c2.getTryTilt())
			updatePlayer2Position(sx - p2MoveSpeed, sy);

		if (c2.getTryTilt() && !p2KnockingBack)
		{
			if (c2.getMoveRight())
			{
				c2.setMoveRight(false);
				c2.setTryTilt(false);
				c2.rightTilt();
				c2Image = c2.getCurrentPlayerImage();
			}
			if (c2.getMoveLeft())
			{
				c2.setMoveLeft(false);
				c2.setTryTilt(false);
				c2.leftTilt();
				c2Image = c2.getCurrentPlayerImage();
			}
			if (c2.getMoveUp())
			{
				c2.setTryTilt(false);
				c2.upTilt();
				c2Image = c2.getCurrentPlayerImage();
			}
			if (c2.getMoveDown())
			{
				c2.setTryTilt(false);
				c2.downTilt();
				c2Image = c2.getCurrentPlayerImage();
			}
		}
		if (c2.getTrySpecial())
		{
			c2.special();
			hitboxes.add(c2.getSpecialProjectiles().get(c2.getSpecialProjectiles().size() - 1));
			c2.setTrySpecial(false);
			c2Image = c2.getCurrentPlayerImage();
		}
		
		if(c2.getHitbox().getK()*-1-c2.getHitbox().getB() >= 1080 || c2.getHitbox().getK()*-1+c2.getHitbox().getB() <= 0 || c2.getHitbox().getH()+c2.getHitbox().getA() <= 0|| c2.getHitbox().getH()-c2.getHitbox().getA() >= 1920)
		{
				c2Lives--;
			
				if (c2Lives == 0)
				{
					c2Lives--;
					started = false;
					readyToPlay = true;
					startUpWait.start();
				}
				else
				{
					c2.setMoveLeft(false);
					c2.setMoveRight(false);
					c2.setJumping(false);
					c2.setDoubleJumping(false);
					c2.setH(1300);
					c2.setK(-200);
					c2.setHP(0);
					c2.setStopMoving(false);
				}
		}
	}

	public void updatePlayer2Position(int x, int y)
	{
		sx = x;
		sy = y;
		c2.setH(x + sw);
		c2.setK((y + sh) * -1);
	}

	private class actionListener implements ActionListener
	{

		public void actionPerformed(ActionEvent e)
		{
			Object source = e.getSource();

			// changes startup image to the waiting screen
			if (source.equals(startUpWait))
			{
				startUpWait.stop();
				// try {
				startUpScreen = new ImageIcon("assets/startUpScreen.png");
				// startUpScreen = new
				// ImageIcon(ImageIO.read(getClass().getResource("assets/startUpAnimation.gif")));
				// } catch (IOException e1) {
				// TODO Auto-generated catch block
				// e1.printStackTrace();
				// }
				readyToPlay = true;
			}

			if (source.equals(ticker))
			{
				updateP1();
				updateP2();

				repaint();
			}

			if (source.equals(p1Knockbacker))
			{

				double tempXDist = p1LaunchSpeed * p1LaunchDirection;
				double tan = Math.tan(p1KnockbackTheta);
				double cos = Math.cos(p1KnockbackTheta);
				c1.setFalling(true);

				// limiter so they dont get juggled into outer space
				if (tan > 4)
				{
					tan = 4;
				}
				if (tan < -4)
				{
					tan = -4;
				}
				if (cos > -0.1 && cos < 0.1)
				{
					cos = 0.1;
				}

				double tempYDist = Math.abs((px + tempXDist) - ogpx) * tan;
				tempYDist -= (p1YVelocity * Math.pow(Math.abs((px + tempXDist) - ogpx), 2))
						/ (2 * p1Knockback * p1Knockback * Math.pow(cos, 2));

				int thingy = p1YCoordIsTouching(ogpy, (int) tempYDist);
				if(thingy != -1 && !p1Stopper)
				{
					p1Stopper = true;
					p1Thingy = thingy;
				}
				if (thingy != -1 || p1Stopper)
				{
					p1Stopper = true;
					
					updatePlayer1Position((int) (px + tempXDist), p1Thingy - ph * 2);

					p1LaunchSpeed -= 1;

					if (p1LaunchSpeed <= 0)
					{
						p1KnockingBack = false;
						p1Knockbacker.stop();
						c1.setFalling(true);
					}
				} 
				else
				{
					updatePlayer1Position((int) (px + tempXDist), (int) (ogpy + tempYDist));

					p1LaunchSpeed -= 1;

					if (p1LaunchSpeed <= 0)
					{
						p1Stopper = false;
						p1Thingy = -1;
						p1KnockingBack = false;
						p1Knockbacker.stop();
						c1.setFalling(true);
					}
				}
			}

			if (source.equals(p2Knockbacker))
			{
				double tempXDist = p2LaunchSpeed * p2LaunchDirection;
				double tan = Math.tan(p2KnockbackTheta);
				double cos = Math.cos(p2KnockbackTheta);
				c2.setFalling(true);

				// limiter so they dont get juggled into outer space
				if (tan > 4)
				{
					tan = 4;
				}
				if (tan < -4)
				{
					tan = -4;
				}
				if (cos > -0.1 && cos < 0.1)
				{
					cos = 0.1;
				}

				double tempYDist = Math.abs((sx + tempXDist) - ogsx) * tan;
				tempYDist -= (p2YVelocity * Math.pow(Math.abs((sx + tempXDist) - ogsx), 2))
						/ (2 * p2Knockback * p2Knockback * Math.pow(cos, 2));

				int thingy = p2YCoordIsTouching(ogsy, (int) tempYDist);
				if(thingy != -1 && !p2Stopper)
				{
					p2Stopper = true;
					p2Thingy = thingy;
				}
				if (thingy != -1 || p2Stopper)
				{
					p2Stopper = true;
					
					updatePlayer2Position((int) (sx + tempXDist), p2Thingy - sh * 2);

					p2LaunchSpeed -= 1;

					if (p2LaunchSpeed <= 0)
					{
						p2KnockingBack = false;
						p2Knockbacker.stop();
						c2.setFalling(true);
					}
				} 
				else
				{
					updatePlayer2Position((int) (sx + tempXDist), (int) (ogsy + tempYDist));

					p2LaunchSpeed -= 1;

					if (p2LaunchSpeed <= 0)
					{
						p2Stopper = false;
						p2Thingy = -1;
						p2KnockingBack = false;
						p2Knockbacker.stop();
						c2.setFalling(true);
					}
				}
			}

		}
	}

	private class keyListen implements KeyListener
	{

		@Override
		public void keyTyped(KeyEvent e)
		{

		}

		@Override
		public void keyPressed(KeyEvent e)
		{
			if (started)
			{
				switch (e.getKeyCode())
				{
				case KeyEvent.VK_A:
					c1.setMoveLeft(true);
					if (p1KnockingBack || c1.getStopMoving())
						p1MoveSpeed = 0;
					else
						p1MoveSpeed = 10;
					break;
				case KeyEvent.VK_D:
					c1.setMoveRight(true);
					if (p1KnockingBack || c1.getStopMoving())
						p1MoveSpeed = 0;
					else
						p1MoveSpeed = 10;
					break;
				case KeyEvent.VK_W:
					c1.setMoveUp(true);
					if (!c1.getJumping() && !c1.getTryTilt())
					{
						p1JumpStart = py;
						p1YVelocity = JUMPHEIGHT;
						c1.setJumping(true);
					} else if (c1.getJumping() && !c1.getDoubleJumping() && !c1.getTryTilt())
					{
						p1YVelocity = JUMPHEIGHT;
						c1.setDoubleJumping(true);
					}
					break;
				case KeyEvent.VK_S:
					c1.setMoveDown(true);
					break;
				case KeyEvent.VK_X:
					c1.setTryTilt(true);
					break;
				case KeyEvent.VK_Z:
					c1.setTrySpecial(true);
					break;

				case KeyEvent.VK_SEMICOLON:
					c2.setMoveRight(true);
					if (p2KnockingBack || c2.getStopMoving())
						p2MoveSpeed = 0;
					else
						p2MoveSpeed = 10;
					break;
				case KeyEvent.VK_K:
					c2.setMoveLeft(true);
					if (p2KnockingBack || c2.getStopMoving())
						p2MoveSpeed = 0;
					else
						p2MoveSpeed = 10;
					break;
				case KeyEvent.VK_O:
					c2.setMoveUp(true);
					if (!c2.getJumping() && !c2.getTryTilt())
					{
						p2JumpStart = sy;
						p2YVelocity = JUMPHEIGHT;
						c2.setJumping(true);
					} else if (c2.getJumping() && !c2.getDoubleJumping() && !c2.getTryTilt())
					{
						p2YVelocity = JUMPHEIGHT;
						c2.setDoubleJumping(true);
					}
					break;
				case KeyEvent.VK_L:
					c2.setMoveDown(true);
					break;
				case KeyEvent.VK_PERIOD:
					c2.setTryTilt(true);
					break;
				case KeyEvent.VK_COMMA:
					c2.setTrySpecial(true);
					break;

				}
			}
			repaint();
		}

		@Override
		public void keyReleased(KeyEvent e)
		{
			if (started)
			{
				switch (e.getKeyCode())
				{
				case KeyEvent.VK_A:
					c1.setMoveLeft(false);
					break;
				case KeyEvent.VK_D:
					c1.setMoveRight(false);
					break;
				case KeyEvent.VK_W:
					c1.setMoveUp(false);
					break;
				case KeyEvent.VK_S:
					c1.setMoveDown(false);
					break;
				case KeyEvent.VK_SEMICOLON:
					c2.setMoveRight(false);
					break;
				case KeyEvent.VK_K:
					c2.setMoveLeft(false);
					break;
				case KeyEvent.VK_O:
					c2.setMoveUp(false);
					break;
				case KeyEvent.VK_L:
					c2.setMoveDown(false);
					break;
				case KeyEvent.VK_X:
					c1.setTryTilt(false);
					break;
				case KeyEvent.VK_Z:
					c1.setTrySpecial(false);
					break;
				}
			}
			if (stageSelectReady)
			{
				if (!p1StageSelected)
				{
					switch (e.getKeyCode())
					{
					case KeyEvent.VK_D:
						if (p1SelectX == 1)
							;
						else
							p1SelectX++;
						break;
					case KeyEvent.VK_A:
						if (p1SelectX == 0)
							;
						else
							p1SelectX--;
						break;
					case KeyEvent.VK_X:
						p1StageSelected = true;
						break;
					}
				}
				if (!p2StageSelected)
				{
					switch (e.getKeyCode())
					{
					case KeyEvent.VK_SEMICOLON:
						if (p2SelectX == 1)
							;
						else
							p2SelectX++;
						break;
					case KeyEvent.VK_K:
						if (p2SelectX == 0)
							;
						else
							p2SelectX--;
						break;
					case KeyEvent.VK_PERIOD:
						p2StageSelected = true;
						break;
					}
				}
			}
			if (characterSelectReady)
			{
				if (!p1CharacterSelected)
				{
					switch (e.getKeyCode())
					{
					case KeyEvent.VK_D:
						if (p1SelectX == 1)
							;
						else
							p1SelectX++;
						break;
					case KeyEvent.VK_A:
						if (p1SelectX == 0)
							;
						else
							p1SelectX--;
						break;
					case KeyEvent.VK_W:
						if (p1SelectY == 0)
							;
						else
							p1SelectY--;
						break;
					case KeyEvent.VK_S:
						if (p1SelectY == 1)
							;
						else
							p1SelectY++;
						break;
					case KeyEvent.VK_X:
						p1CharacterSelected = true;
						break;
					}
				}
				if (!p2CharacterSelected)
				{
					switch (e.getKeyCode())
					{
					case KeyEvent.VK_SEMICOLON:
						if (p2SelectX == 1)
							;
						else
							p2SelectX++;
						break;
					case KeyEvent.VK_K:
						if (p2SelectX == 0)
							;
						else
							p2SelectX--;
						break;
					case KeyEvent.VK_O:
						if (p2SelectY == 0)
							;
						else
							p2SelectY--;
						break;
					case KeyEvent.VK_L:
						if (p2SelectY == 1)
							;
						else
							p2SelectY++;
						break;
					case KeyEvent.VK_PERIOD:
						p2CharacterSelected = true;
						break;
					}
				}
			}

			// MAKE SURE THIS IS ON BOTTOM IT CAUSES PROBLEMS IF NOT
			if (!started && e.getKeyCode() == KeyEvent.VK_X)
			{
				if (!stageSelectReady && readyToPlay)
				{
					stageSelectReady = true;
					readyToPlay = false;
					gettingReadyToPlay = false;
				}
			}
			repaint();
		}

	}

}
