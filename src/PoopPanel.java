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
	private int w, h, py, px, pw, ph, sx, sy, sw, sh, p1YVelocity, p1MoveSpeed, p1LaunchDirection, ogpx, ogpy, p1JumpStart, p1SelectX, p1SelectY, p2SelectX, p2SelectY;
	private double p1Knockback, p1LaunchSpeed, p1EndXTraj, p1KnockbackTheta;
	private ImageIcon startUpAnimation, startUpScreen, c1Image, c2Image;
	private Timer startUpWait, ticker, p1Knockbacker;
	private boolean started, gettingReadyToPlay, readyToPlay, p1KnockingBack, stageSelectReady, characterSelectReady, p1StageSelected, p2StageSelected, p1CharacterSelected, p2CharacterSelected, characterReady;
	private final int GRAVITY, JUMPHEIGHT;
	private Character c1, c2;
	private final Character PoopDefender, Neff, Kuma, Mob;
	private Character[][] characterSelect;
	private Stage[][] stageSelect;
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
		
		py = 100;
		px = 500;
		pw = 50;
		ph = 100;
		
		sy = 1000;
		sx = 100;
		sw = 50;
		sh = 100;
		
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
		
		p1KnockingBack = false;
		
		/*sets up startup animation and wait and then waiting screen
		Animation will play and then once it stops screen will appear
		and wait for the player to press start
		*/
		startUpScreen = new ImageIcon("assets/startUpAnimation.gif");
		//startUpScreen = new ImageIcon(ImageIO.read(getClass().getResource("assets/startUpAnimation.gif")));
		startUpWait = new Timer(5000, new actionListener());
		startUpWait.start();
		
		//sets up timer for 1 tick of the game
		ticker = new Timer(20, new actionListener());
		
		//sets up jumping mechanism
		p1Knockbacker = new Timer(20, new actionListener());
		JUMPHEIGHT = 20;

		//sets up the object for each character you can select
		PoopDefender = new Character(new OvalHitbox(0, 0, 0, 0, 0));
		Neff = new Character(new OvalHitbox(0, 0, 0, 0, 0));
		Kuma = new Character(new OvalHitbox(0, 0, 0, 0, 0));
		Mob = new Character(new OvalHitbox(0, 0, 0, 0, 0));
		
		characterSelect = new Character[2][2];
		stageSelect = new Stage[2][2];
		
		characterSelect[0][0] = PoopDefender;
		characterSelect[0][1] = Neff;
		characterSelect[1][0] = Kuma;
		characterSelect[1][1] = Mob;
		
		//filler stage objects for test runs while we dont have a stage
		stageSelect[0][0] = new Stage(new ImageIcon("t"), hitboxes, hitboxes);
		stageSelect[0][1] = new Stage(new ImageIcon("t"), hitboxes, hitboxes);
		stageSelect[1][0] = new Stage(new ImageIcon("t"), hitboxes, hitboxes);
		stageSelect[1][1] = new Stage(new ImageIcon("t"), hitboxes, hitboxes);
		stageSelected = null;
		
		//sets up character object for each player (later this will be moved out of constructor to a character select page in paintComponent)
		c1 = new Character(new OvalHitbox(px+pw, py+ph, pw, ph, 0));
		c1.setHP(0.0);
		c1.setWeight(75.0);
		c1.setMoveLeft(false);
		c1.setMoveRight(false);
		c1.setJumping(false);
		c1.setDoubleJumping(false);
		
		c2 = new Character(new OvalHitbox(sx+sw, sy+sh, sw, sh, 0));
		c2.setHP(0.0);
		c2.setWeight(75.0);
		c2.setMoveLeft(false);
		c2.setMoveRight(false);
		c2.setJumping(false);
		c2.setDoubleJumping(false);
		
		hitboxes = new ArrayList<Hitbox>();
		hitboxes.add(c1.getHitbox());
		hitboxes.add(c2.getHitbox());
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
			
			ticker.start();
			//all drawings below here:
			g.setColor(Color.black);
			g.drawOval(px, py, pw*2, ph*2);
			g.drawOval(sx, sy, sw*2, sh*2);
			
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
				if(!h.equals(c1.getHitbox()))
				{
					p1Intersection = h.intersects(c1.getHitbox());
					p1OppIntersection = h.getOppositeIntersection();
				}
				
				if(!h.equals(c2.getHitbox()))
				{
					p2Intersection = h.intersects(c2.getHitbox());
					p2OppIntersection = h.getOppositeIntersection();
				}
				
				if(p1Intersection[0] != -1 && h.getDamage() != 0)
				{
					g.setColor(Color.red);
					g.drawOval((int)p1Intersection[0], (int)p1Intersection[1], 20, 20);
					g.drawOval((int)p1OppIntersection[0], (int)p1OppIntersection[1], 20, 20);
					double p1Dist = Math.sqrt(Math.pow(Math.abs(p1Intersection[0]-p1OppIntersection[0]), 2) + Math.pow(Math.abs(p1Intersection[1]-p1OppIntersection[1]), 2));
					p1Knockback = getp1Knockback(h.getDamage(), h.getKB());
					
					p1KnockbackTheta = Math.atan((p1Intersection[1]-p1OppIntersection[1])/(p1Intersection[0]-p1OppIntersection[0]));
					
					//Sets the end of knockback trajectory halfway through the arc
					p1EndXTraj = 2*p1Knockback*Math.sin(p1KnockbackTheta);
					System.out.println(Math.toDegrees(p1KnockbackTheta));
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
				
				else if(p1Intersection[0] != -1)
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
			
			if(p1StageSelected && p2StageSelected)
			{
				Random rand = new Random();
				if(rand.nextInt(2) == 0)
				{
					stageSelected = stageSelect[p1SelectY][p1SelectX];
					System.out.println("Hi");
				}
				else
				{
					stageSelected = stageSelect[p2SelectY][p2SelectX];
					System.out.println("Bye");
				}
				p1StageSelected = false;
				p2StageSelected = false;
				characterSelectReady = true;
				stageSelectReady = false;
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
					
					if(p1YVelocity > JUMPHEIGHT)
						p1YVelocity = JUMPHEIGHT;
				}
				
					
			}
			else
			{
				if(CoordIsTouching(py+ph*2+p1YVelocity).equals("bottom"))
				{
					p1YVelocity = GRAVITY;
					updatePlayer1Position(px, 1080-ph*2);
				}
				else
				{
					if(p1YVelocity > 10)
						p1YVelocity = 10;
					else
						p1YVelocity *= GRAVITY;
					updatePlayer1Position(px, py+p1YVelocity);
				}
			}
			
			//does while moving left or right
			if(c1.getMoveRight())
				updatePlayer1Position(px+p1MoveSpeed, py);
			if(c1.getMoveLeft())
				updatePlayer1Position(px-p1MoveSpeed, py);
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
			// TODO Auto-generated method stub
			
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
					if(!c1.getJumping())
					{
						p1JumpStart = py;
						p1YVelocity = JUMPHEIGHT;
						c1.setJumping(true);
					}
					else if(c1.getJumping() && !c1.getDoubleJumping())
					{
						p1YVelocity = JUMPHEIGHT;
						c1.setDoubleJumping(true);
					}
					break;
				case KeyEvent.VK_S:
					updatePlayer1Position(px, py+10);
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
			else if(!started && e.getKeyCode() == KeyEvent.VK_X)
			{
				if(!stageSelectReady && readyToPlay)
				{
					stageSelectReady = true;
					readyToPlay = false;
					gettingReadyToPlay = false;
				}
				else if(characterSelectReady && characterReady)
				{
					started = true;
					characterSelectReady = false;
					characterReady = false;
					p1CharacterSelected = false;
					p2CharacterSelected = false;
					p1SelectY = 0;
					p1SelectX = 0;
					p2SelectY = 0;
					p2SelectX = 0;
				}
			}
			if(characterSelectReady || stageSelectReady)
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
						case KeyEvent.VK_C:
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
		}
			

		@Override
		public void keyReleased(KeyEvent e)
		{
			switch(e.getKeyCode())
			{
			case KeyEvent.VK_A:
				c1.setMoveLeft(false);
				break;
			case KeyEvent.VK_D:
				c1.setMoveRight(false);
				break;
			case KeyEvent.VK_SEMICOLON:
				c2.setMoveRight(false);
				break;
			case KeyEvent.VK_K:
				c2.setMoveLeft(false);
				break;
			}
		}
		
	}
	
	
}
