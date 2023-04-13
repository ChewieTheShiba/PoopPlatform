import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.*;
import java.util.*;
import javax.swing.Timer;

public class PoopPanel extends JPanel
{
	//variables for the overall width and height
	private int w, h, px, py, pw, ph, sx, sy, sw, sh, p1YVelocity, p1MoveSpeed, p1LaunchDirection, ogpx, ogpy, p1JumpStart, p1SelectX, p1SelectY, p2SelectX, p2SelectY;
	private double p1Knockback, p1LaunchSpeed, p1EndXTraj, p1KnockbackTheta;
	private ImageIcon startUpAnimation, startUpScreen, c1Image, c2Image, projectile;
	private Timer startUpWait, ticker, p1Knockbacker;
	private boolean p1Falling, started, gettingReadyToPlay, readyToPlay, p1KnockingBack, stageSelectReady, characterSelectReady, p1StageSelected, p2StageSelected, p1CharacterSelected, p2CharacterSelected, characterReady;
	private final int GRAVITY, JUMPHEIGHT;
	private Character c1, c2;
	private final Character PoopDefender, Neff, Kuma, Mob;
	private final Character[][] characterSelect;
	private final Stage[][] stageSelect;
	private Stage stageSelected;
	private ArrayList<Hitbox> hitboxes;
	
	//sets up the initial panel for drawing with proper size
	public PoopPanel(int w, int h) throws IOException
	{
		this.w = w;
		this.h = h;
		this.setPreferredSize(new Dimension(w,h));
		
		//sets up listeners
		this.addKeyListener(new keyListen());
		this.setFocusable(true);
		
		//sets up gravity stuff
		p1YVelocity = 1;
		GRAVITY = 2;
		
		//sets up stuff to start game and test if its ready to start
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
		p1Falling = true;
		
		p1KnockingBack = false;
		
		/*sets up startup animation and wait and then waiting screen
		Animation will play and then once it stops screen will appear
		and wait for the player to press start
		*/
		startUpScreen = new ImageIcon("assets/startUpAnimation.gif");
		//startUpScreen = new ImageIcon(ImageIO.read(getClass().getResource("assets/startUpAnimation.gif")));
		startUpWait = new Timer(100, new actionListener());
		startUpWait.start();
		
		//sets up timer for 1 tick of the game
		ticker = new Timer(20, new actionListener());
		
		//sets up jumping mechanism
		p1Knockbacker = new Timer(20, new actionListener());
		JUMPHEIGHT = 20;

		//sets up the object for each character you can select
		/*PoopDefender = new PoopDefender();
		Neff = new Neff();
		Kuma = new Kuma();
		Mob = new Mob();*/
		
		characterSelect = new Character[2][2];
		stageSelect = new Stage[2][2];
		
		//temporary instansiations 
		PoopDefender = new PoopDefender();
		Neff = new Neff();
		Kuma = new Neff();
		Mob = new Neff();
		
		characterSelect[0][0] = PoopDefender;
		characterSelect[0][1] = Neff;
		characterSelect[1][0] = Kuma;
		characterSelect[1][1] = Mob;
		
		//instantiates characters
		c1 = new Character();
		c2 = new Character();
		projectile = new ImageIcon("assets/Poop Defender/SpecialProjectile.png");
		
		//filler stage objects for test runs while we dont have a stage
		stageSelect[0][0] = new Stage(new ImageIcon("t"), hitboxes, hitboxes);
		stageSelect[0][1] = new Stage(new ImageIcon("t"), hitboxes, hitboxes);
		stageSelect[1][0] = new Stage(new ImageIcon("t"), hitboxes, hitboxes);
		stageSelect[1][1] = new Stage(new ImageIcon("t"), hitboxes, hitboxes);
		stageSelected = null;
		
		hitboxes = new ArrayList<Hitbox>();
		hitboxes.add(new OvalHitbox(-100, -100, 1, 1, 0, 0));
	}
	
	
	//all graphical components go here
	//this.setBackground(Color c) for example will change background color
	public void paintComponent(Graphics g)
	{
		//this line sets up the graphics - always needed
		super.paintComponent(g);
		
		if(started)
		{
			double[] p1Intersection = {-1,-1}, p2Intersection = {-1,-1}, p1OppIntersection = {-1,-1}, p2OppIntersection = {-1,-1};
			px = c1.getHitbox().getH()-c1.getHitbox().getA();
			py = c1.getHitbox().getK()*-1-c1.getHitbox().getB();
			pw = c1.getHitbox().getA();
			ph = c1.getHitbox().getB();
			sx = c2.getHitbox().getH()-c2.getHitbox().getA();
			sy = c2.getHitbox().getK()*-1-c2.getHitbox().getB();
			sw = c2.getHitbox().getA();
			sh = c2.getHitbox().getB();
			
			c1Image = c1.getCurrentPlayerImage();
			c2Image = c2.getCurrentPlayerImage();
			
			ticker.start();
			//all drawings below here:
			g.setColor(Color.black);
			
			c1Image.paintIcon(this, g, px-c1.getXOffPut(), py-c1.getYOffPut());
			c2Image.paintIcon(this, g, sx-c2.getXOffPut(), sy-c2.getYOffPut());
			g.drawOval(px, py, pw*2, ph*2);
			g.drawOval(sx, sy, sw*2, sh*2);
			/*if(((PoopDefender) c1).getSpecialHitbox() != null)
			{
				g.drawRect(((PoopDefender) c1).getSpecialHitbox().getH(), ((PoopDefender) c1).getSpecialHitbox().getK(), ((PoopDefender) c1).getSpecialHitbox().getA(), ((PoopDefender) c1).getSpecialHitbox().getB());
			}*/
			
			for(int v = 0; v < 1920; v+=30)
			{
				g.drawLine(v, 0, v, 10);
				g.drawString("" + v, v, 20);
			}
			
			for(int v = 0; v < 1080; v+=30)
			{
				g.drawLine(0, v, 10, v);
				g.drawString("" + v, 20, v);
			}
			
			
			for(Hitbox h : hitboxes)
			{
				if(h != null && h.getId().equals("Projectile"))
					projectile.paintIcon(this, g, h.getH(), h.getK());
				
				if(h != null && !h.equals(c1.getHitbox()) && !h.getId().equals((c1.getName())))
				{
					p1Intersection = h.intersects(c1.getHitbox());
					p1OppIntersection = h.getOppositeIntersection();
				}
				
				if(h != null && !h.equals(c2.getHitbox()) && !h.getId().equals(c2.getName()))
				{
					p2Intersection = h.intersects(c2.getHitbox());
					p2OppIntersection = h.getOppositeIntersection();
				}
				
				
				if(h != null && p1Intersection[0] != -1 && h.getDamage() != 0)
				{
					System.out.println(p1Intersection[0]);
					System.out.println(p1Intersection[1]);
					g.setColor(Color.red);
					g.drawOval((int)p1Intersection[0], (int)p1Intersection[1], 20, 20);
					g.drawOval((int)p1OppIntersection[0], (int)p1OppIntersection[1], 20, 20);
					double p1Dist = Math.sqrt(Math.pow(Math.abs(p1Intersection[0]-p1OppIntersection[0]), 2) + Math.pow(Math.abs(p1Intersection[1]-p1OppIntersection[1]), 2));
					
					//change to h.getKB());
					p1Knockback = getp1Knockback(h.getDamage(), 600);
					
					p1KnockbackTheta = Math.atan((p1Intersection[1]-p1OppIntersection[1])/(p1Intersection[0]-p1OppIntersection[0]));
					
					//Sets the end of knockback trajectory halfway through the arc
					p1EndXTraj = 2*p1Knockback*Math.sin(p1KnockbackTheta);
					p1EndXTraj /= p1YVelocity;
					p1EndXTraj /= 2;
					p1EndXTraj += p1Intersection[0];
					
					p1LaunchSpeed = p1Knockback * 0.03;
					
					p1LaunchDirection = 1;
					
					
					if(p1OppIntersection[0] - c1.getHitbox().getH() < 0)
					{
						p1EndXTraj = p2Intersection[0] - p1EndXTraj;
						p1LaunchDirection = -1;
						p1KnockbackTheta *= -1;
					}
					if(p1OppIntersection[1] - c1.getHitbox().getK() < 0)
						;
					
					ogpx = px;
					ogpy = py;
					
					p1KnockingBack = true;
					p1Knockbacker.start();
				}
				
				else if(p1Intersection[0] != -1 && h != null)
				{
					if(h.getMoveRight())
						updatePlayer1Position(px+(int)h.getKB(), py);
					if(h.getMoveLeft())
						updatePlayer1Position(px-(int)h.getKB(), py);
					if(h.getMoveUp())
						updatePlayer1Position(px, py-(int)h.getKB());
					if(h.getMoveDown())
						updatePlayer1Position(px, py+(int)h.getKB());
				}
			}
			
		}
		else if(readyToPlay || gettingReadyToPlay)
		{
			startUpScreen.paintIcon(this, g, 0, 0);
		}
		else if(stageSelectReady)
		{
			new ImageIcon("assets/Poop Defender/PoopDefenderIdleRight.png").paintIcon(this, g, 0, 0);
			new ImageIcon("assets/Poop Defender/PoopDefenderIdleRight.png").paintIcon(this, g, w/2, 0);
			new ImageIcon("assets/Poop Defender/PoopDefenderIdleRight.png").paintIcon(this, g, 0, h/2);
			new ImageIcon("assets/Poop Defender/PoopDefenderIdleRight.png").paintIcon(this, g, w/2, h/2);
			
			g.setColor(Color.GREEN);
			g.drawRect(p1SelectX*w/2, p1SelectY*h/2, w/2, h/2);
			g.setFont(new Font("Comic Sans", Font.PLAIN, 15));
			g.setColor(Color.BLACK);
			g.drawString("Player 1", p1SelectX*w/2, p1SelectY*h/2+15);
			
			g.setColor(Color.BLUE);
			g.drawRect(p2SelectX*w/2, p2SelectY*h/2, w/2, h/2);
			g.setColor(Color.BLACK);
			g.drawString("Player 2", p2SelectX*w/2, p2SelectY*h/2+15);
			
			if(p1StageSelected && p2StageSelected)
			{
				Random rand = new Random();
				if(rand.nextInt(2) == 0)
					stageSelected = stageSelect[p1SelectY][p1SelectX];
				else
					stageSelected = stageSelect[p2SelectY][p2SelectX];
				
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
		}
		else if(characterSelectReady)
		{
			new ImageIcon("assets/Poop Defender/PoopDefenderIdleRight.png").paintIcon(this, g, 0, 0);
			g.setFont(new Font("Comic Sans", Font.PLAIN, 40));
			g.setColor(Color.ORANGE);
			g.fillRect(0, h/2-200, w/2, 200);
			g.setColor(Color.BLACK);
			g.drawString("Poop Defender", w/4-135, 2*h/5);
			new ImageIcon("assets/Poop Defender/PoopDefenderIdleRight.png").paintIcon(this, g, w/2, 0);
			new ImageIcon("assets/Poop Defender/PoopDefenderIdleRight.png").paintIcon(this, g, 0, h/2);
			new ImageIcon("assets/Poop Defender/PoopDefenderIdleRight.png").paintIcon(this, g, w/2, h/2);
			
			g.setColor(Color.GREEN);
			g.drawRect(p1SelectX*w/2, p1SelectY*h/2, w/2, h/2);
			g.setFont(new Font("Comic Sans", Font.PLAIN, 15));
			g.setColor(Color.BLACK);
			g.drawString("Player 1", p1SelectX*w/2, p1SelectY*h/2+15);
			
			g.setColor(Color.BLUE);
			g.drawRect(p2SelectX*w/2, p2SelectY*h/2, w/2, h/2);
			g.setColor(Color.BLACK);
			g.drawString("Player 2", p2SelectX*w/2, p2SelectY*h/2+15);
			
			if(p1CharacterSelected && p2CharacterSelected)
			{
					if(characterSelect[p1SelectY][p1SelectX].getClass().equals(PoopDefender.class))
						c1 = new PoopDefender();
					if(characterSelect[p1SelectY][p1SelectX].getClass().equals(Neff.class))
						c1 = new Neff();
					if(characterSelect[p1SelectY][p1SelectX].getClass().equals(PoopDefender.class))
						c1 = new PoopDefender();
					if(characterSelect[p1SelectY][p1SelectX].getClass().equals(PoopDefender.class))
						c1 = new PoopDefender();
					c1.setName("Player 1");
					c1.setMoveLeft(false);
					c1.setMoveRight(false);
					c1.setJumping(false);
					c1.setDoubleJumping(false);
					c1.setH(500);
					c1.setK(-500);
					hitboxes.add(c1.attack1Hitbox);
					hitboxes.add(c1.attack2Hitbox);
					hitboxes.add(c1.attack3Hitbox);
					hitboxes.add(c1.attack4Hitbox);
					if(characterSelect[p2SelectY][p2SelectX].getClass().equals(PoopDefender.class))
						c2 = new PoopDefender();
					if(characterSelect[p2SelectY][p2SelectX].getClass().equals(Neff.class))
						c2 = new Neff();
					if(characterSelect[p2SelectY][p2SelectX].getClass().equals(PoopDefender.class))
						c2 = new PoopDefender();
					if(characterSelect[p2SelectY][p2SelectX].getClass().equals(PoopDefender.class))
						c2 = new PoopDefender();
					c2.setName("Player 2");
					c2.setMoveLeft(false);
					c2.setMoveRight(false);
					c2.setJumping(false);
					c2.setDoubleJumping(false);
					c2.setH(300);
					c2.setK(-1100);
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
	
	//happens every time a tick occurs
	public void updateP1()
	{
			//jumping is if else is falling
			if(c1.getJumping() || c1.getDoubleJumping())
			{
				if (CoordIsTouching((py+ph*2-p1YVelocity)).equals("bottom"))
				{
					c1.setJumping(false);
					c1.setDoubleJumping(false);
					py = p1JumpStart;
					p1YVelocity = JUMPHEIGHT;
				}
				else
				{
					updatePlayer1Position(px, py-p1YVelocity);
					p1YVelocity -= GRAVITY;
					
					if(p1YVelocity < 0)
					{
						c1.setMoveDown(true);
						c1.setMoveUp(false);
					}
					
					if(p1YVelocity > JUMPHEIGHT)
						p1YVelocity = JUMPHEIGHT;
				}
				
					
			}
			else if(!p1KnockingBack)
			{
				p1Falling = true;
				if(CoordIsTouching(py+ph*2+p1YVelocity).equals("bottom"))
				{
					p1YVelocity = GRAVITY;
					c1.setMoveDown(false);
					updatePlayer1Position(px, 1080-ph*2);
					p1Falling = false;
				}
				else
				{
					if(p1YVelocity > 10)
						p1YVelocity = 10;
					else
						p1YVelocity *= GRAVITY;
					updatePlayer1Position(px, py+p1YVelocity);
					c1.setMoveDown(true);
				}
			}
			
			//does while moving left or right
			if(c1.getMoveRight() && !c1.getTryTilt())
			{
				updatePlayer1Position(px+p1MoveSpeed, py);
			}
				//updatePlayer1Position(px+p1MoveSpeed, py);
			if(c1.getMoveLeft() && !c1.getTryTilt())
				updatePlayer1Position(px-p1MoveSpeed, py);
			
			if(c1.getTryTilt() && !p1KnockingBack)
			{
				if(c1.getMoveRight())
				{
					c1.setMoveRight(false);
					c1.setTryTilt(false);
					c1.rightTilt();
					c1Image = c1.getCurrentPlayerImage();
				}
				if(c1.getMoveLeft())
				{
					c1.setMoveLeft(false);
					c1.setTryTilt(false);
					c1.leftTilt();
					c1Image = c1.getCurrentPlayerImage();
				}
				if(c1.getMoveUp()) 
				{
					c1.setMoveLeft(false);
					c1.setTryTilt(false);
					c1.upTilt();
					c1Image = c1.getCurrentPlayerImage();
				}
			}
			if(c1.getTrySpecial())
			{
				c1.special();
				hitboxes.add(c1.getSpecialProjectiles().get(c1.getSpecialProjectiles().size()-1));
				c1.setTrySpecial(false);
				c1Image = c1.getCurrentPlayerImage();
			}
	}
	
	//checks if p1 is touching ceiling or platform or wall
	public String CoordIsTouching(int x) 
	{
		if(x >= 1080)
			return "bottom";
		if(x <= 0)
			return "top";
		
		return "";
	}
	
	
	
	
	public void updatePlayer1Position(int x, int y)
	{
		px = x;
		py = y;
		c1.setH(x+pw );
		c1.setK((y+ph)*-1);
	}
	
	public double getp1Knockback(double attackPower, double baseKnockBack)
	{
		/*This formula is used off a wiki page for the smash bros formula for calculating distance based
		based on weight, percentage, attack power, etc. since I figured instead of having a convoluted
		bad formula thats weird it's better to use one that works well*/
		
		//ALL RECOMENDATIONS ARE VERY WORK IN PROGRESS IM MAKING A LARGE
		//ESTIMATION FOR THE TIME BEING
		
		//recomended weights
		//light is 50ish
		//medium is 100ish
		//heavy is 150-200ish
		
		//recomended knockback
		//light is 100ish
		//medium is 250-300ish
		//killers are 400ish
		
		//when deciding how far to knock them in x and y take the distance from intersection to p1OppIntersection
		//then do x/dist and y/dist
		//then multiply those ratios by knockback
		//then add that knockbackx and knockbacky to the players x and y
		
		
		double percentage = c1.getHP();
		double weight = c1.getWeight();
		
		double knockBack = (percentage/10) + (percentage*attackPower/20);
		System.out.println("1\t" + knockBack);
		knockBack *= 200/(weight+100);
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
		if(c2.getMoveRight())
		{
			updatePlayer2Position(sx+10, sy);
		}
		if(c2.getMoveLeft())
		{
			updatePlayer2Position(sx-10, sy);
		}
	}
	
	public void updatePlayer2Position(int x, int y)
	{
		sx = x;
		sy = y;
		c2.setH(x+sw);
		c2.setK((y+sh)*-1);
	}
	
	private class actionListener implements ActionListener
	{

		public void actionPerformed(ActionEvent e)
		{
			Object source = e.getSource();
			
			//changes startup image to the waiting screen
			if(source.equals(startUpWait))
			{
				startUpWait.stop();
				//try {
					startUpScreen = new ImageIcon("assets/startUpScreen.png");
					//startUpScreen = new ImageIcon(ImageIO.read(getClass().getResource("assets/startUpAnimation.gif")));
				//} catch (IOException e1) {
					// TODO Auto-generated catch block
					//e1.printStackTrace();
				//}
				readyToPlay = true;
			}
			
			if(source.equals(ticker))
			{
				updateP1();
				updateP2();
				
				for(int x = 0; x < hitboxes.size(); x++)
				{
					Hitbox h = hitboxes.get(x);
					if(h != null && (h.getH() < -150 || h.getH() > 2000 || h.getK() > 1200 || h.getK() < -100) && !h.equals(c1.getHitbox()) && !h.equals(c2.getHitbox()))
						hitboxes.remove(h);
				}
				
				repaint();
			}
			
			if(source.equals(p1Knockbacker))
			{
				
				double tempXDist = p1LaunchSpeed*p1LaunchDirection;
				double tan = Math.tan(p1KnockbackTheta);
				double cos = Math.cos(p1KnockbackTheta);
				
				//limiter so they dont get juggled into outer space
				if (tan > 4)
				{
					tan = 4;
				}
				if (tan < -4)
				{
					tan = -4;
				}
				if(cos > -0.1 && cos < 0.1 )
				{
					cos = 0.1;
				}
		
				double tempYDist = Math.abs((px+tempXDist)-ogpx)*tan;
				tempYDist -= (p1YVelocity*Math.pow(Math.abs((px+tempXDist)-ogpx), 2))/(2*p1Knockback*p1Knockback*Math.pow(cos,2));

				updatePlayer1Position((int)(px+tempXDist),(int) (ogpy+tempYDist));
				
				p1LaunchSpeed -= 1;
				
				if(p1LaunchSpeed <= 0)
				{
					p1KnockingBack = false;
					p1Knockbacker.stop();
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
			if(started)
			{
				switch(e.getKeyCode())
				{
				case KeyEvent.VK_A:
					c1.setMoveLeft(true);
					if(p1KnockingBack)
						p1MoveSpeed = 0;
					else
						p1MoveSpeed = 10;
					break;
				case KeyEvent.VK_D:
					c1.setMoveRight(true);
					if(p1KnockingBack)
						p1MoveSpeed = 0;
					else
						p1MoveSpeed = 10;
					break;
				case KeyEvent.VK_W:
					c1.setMoveUp(true);
					if(!c1.getJumping() && !c1.getTryTilt())
					{
						p1JumpStart = py;
						p1YVelocity = JUMPHEIGHT;
						c1.setJumping(true);
					}
					else if(c1.getJumping() && !c1.getDoubleJumping() && !c1.getTryTilt())
					{
						p1YVelocity = JUMPHEIGHT;
						c1.setDoubleJumping(true);
					}
					break;
				case KeyEvent.VK_X:
					c1.setTryTilt(true);
					break;
				case KeyEvent.VK_Z:
					c1.setTrySpecial(true);
					break;
				
				case KeyEvent.VK_SEMICOLON:
					c2.setMoveRight(true);
					break;
				case KeyEvent.VK_K:
					c2.setMoveLeft(true);
					break;
				case KeyEvent.VK_O:
					updatePlayer2Position(sx, sy-10);
					break;
				
				}
			}
			repaint();
		}
			

		@Override
		public void keyReleased(KeyEvent e)
		{
			if(started)
			{
				switch(e.getKeyCode())
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
				case KeyEvent.VK_SEMICOLON:
					c2.setMoveRight(false);
					break;
				case KeyEvent.VK_K:
					c2.setMoveLeft(false);
					break;
				case KeyEvent.VK_X:
					c1.setTryTilt(false);
					break;
				case KeyEvent.VK_Z:
					c1.setTrySpecial(false);
					break;
				}
			}
			if(stageSelectReady)
			{
				if(!p1StageSelected)
				{
					switch(e.getKeyCode())
					{
						case KeyEvent.VK_D:
							if(p1SelectX == 1);
							else p1SelectX++;
							break;
						case KeyEvent.VK_A:
							if(p1SelectX == 0);
							else p1SelectX--;
							break;
						case KeyEvent.VK_W:
							if(p1SelectY == 0);
							else p1SelectY--;
							break;
						case KeyEvent.VK_S:
							if(p1SelectY == 1);
							else p1SelectY++;
							break;
						case KeyEvent.VK_X:
							p1StageSelected = true;
							break;
					}
				}
				if(!p2StageSelected)
				{
					switch(e.getKeyCode())
					{
						case KeyEvent.VK_SEMICOLON:
							if(p2SelectX == 1);
							else p2SelectX++;
							break;
						case KeyEvent.VK_K:
							if(p2SelectX == 0);
							else p2SelectX--;
							break;
						case KeyEvent.VK_O:
							if(p2SelectY == 0);
							else p2SelectY--;
							break;
						case KeyEvent.VK_L:
							if(p2SelectY == 1);
							else p2SelectY++;
							break;
						case KeyEvent.VK_PERIOD:
							p2StageSelected = true;
							break;
					}
				}
			}
				if(characterSelectReady)
				{
					if(!p1CharacterSelected)
					{
						switch(e.getKeyCode())
						{
							case KeyEvent.VK_D:
								if(p1SelectX == 1);
								else p1SelectX++;
								break;
							case KeyEvent.VK_A:
								if(p1SelectX == 0);
								else p1SelectX--;
								break;
							case KeyEvent.VK_W:
								if(p1SelectY == 0);
								else p1SelectY--;
								break;
							case KeyEvent.VK_S:
								if(p1SelectY == 1);
								else p1SelectY++;
								break;
							case KeyEvent.VK_X:
								p1CharacterSelected = true;
								break;
						}
					}
					if(!p2CharacterSelected)
					{
						switch(e.getKeyCode())
						{
							case KeyEvent.VK_SEMICOLON:
								if(p2SelectX == 1);
								else p2SelectX++;
								break;
							case KeyEvent.VK_K:
								if(p2SelectX == 0);
								else p2SelectX--;
								break;
							case KeyEvent.VK_O:
								if(p2SelectY == 0);
								else p2SelectY--;
								break;
							case KeyEvent.VK_L:
								if(p2SelectY == 1);
								else p2SelectY++;
								break;
							case KeyEvent.VK_PERIOD:
								p2CharacterSelected = true;
								break;
						}
					}
			}
			
			
			//MAKE SURE THIS IS ON BOTTOM IT CAUSES PROBLEMS IF NOT
			if(!started && e.getKeyCode() == KeyEvent.VK_X)
			{
				if(!stageSelectReady && readyToPlay)
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

