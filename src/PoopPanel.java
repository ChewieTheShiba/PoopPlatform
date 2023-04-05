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
	private int w, h, py, px, pw, ph, sx, sy, sw, sh, p1YVelocity, p1MoveSpeed, p1LaunchDirection, ogpx, ogpy;
	private double p1XDist, p1YDist, p1Knockback, p1LaunchSpeed, p1EndXTraj, p1YTraj, p1KnockbackTheta;
	private Hitbox h1, h2;
	private ImageIcon startUpAnimation, startUpScreen;
	private Timer startUpWait, ticker, p1Knockbacker;
	private boolean started, readyToPlay, p1KnockingBack;
	private final int GRAVITY, JUMPHEIGHT;
	private Character c1, c2;
	
	//sets up the initial panel for drawing with proper size
	public PoopPanel(int w, int h) throws IOException
	{
		this.w = w;
		this.h = h;
		this.setPreferredSize(new Dimension(w,h));
		
		//sets up listeners
		this.addMouseMotionListener(new mouseListen());
		this.addMouseListener(new mouseListen());
		this.addKeyListener(new keyListen());
		this.setFocusable(true);
		
		py = 100;
		px = 500;
		pw = 100;
		ph = 200;
		
		sy = 1000;
		sx = 100;
		sw = 100;
		sh = 200;
		
		//sets up gravity stuff
		p1YVelocity = 1;
		GRAVITY = 2;
		
		//sets up stuff to start game and test if its ready to start
		started = true;
		readyToPlay = false;
		
		h1 = new OvalHitbox(px+pw, py+ph, pw, ph, 0);
		h2 = new OvalHitbox(sx+sw, sy+sh, sw, sh, 0);
		
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
		
		c1 = new Character();
		c1.setMoveLeft(false);
		c1.setMoveRight(false);
		c1.setJumping(false);
		c1.setDoubleJumping(false);
		
		c2 = new Character();
		c2.setMoveLeft(false);
		c2.setMoveRight(false);
		c2.setJumping(false);
		c2.setDoubleJumping(false);
	}
	
	
	//all graphical components go here
	//this.setBackground(Color c) for example will change background color
	public void paintComponent(Graphics g)
	{
		//this line sets up the graphics - always needed
		super.paintComponent(g);
		
		if(started)
		{
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
			
			double p1Intersection[] = h1.intersects(h2, c1.getMoveRight(), c1.getMoveLeft(), c1.getJumping());
			double p1OppIntersection[] = h1.getOppositeIntersection();
			
			double p2Intersection[] = h2.intersects(h1, c2.getMoveRight(), c2.getMoveLeft(), c2.getJumping());
			double p2OppIntersection[] = h2.getOppositeIntersection();
			
			if(p2Intersection[0] != -1)
			{
				g.setColor(Color.red);
				g.drawOval((int)p2Intersection[0], (int)p2Intersection[1], 20, 20);
				g.drawOval((int)p2OppIntersection[0], (int)p2OppIntersection[1], 20, 20);
				double p1Dist = Math.sqrt(Math.pow(Math.abs(p2Intersection[0]-p2OppIntersection[0]), 2) + Math.pow(Math.abs(p2Intersection[1]-p2OppIntersection[1]), 2));
				p1Knockback = getp1Knockback(10, 400);
				
				p1XDist = Math.abs(p2Intersection[0]-p2OppIntersection[0])/p1Dist;
				p1YDist = Math.abs(p2Intersection[1]-p2OppIntersection[1])/p1Dist;
				
				p1KnockbackTheta = Math.atan((p2Intersection[1]-p2OppIntersection[1])/(p2Intersection[0]-p2OppIntersection[0]));
				
				
				//Sets the end of knockback trajectory halfway through the arc
				p1EndXTraj = Math.pow(p1Knockback, 2)*Math.sin(p1KnockbackTheta*2);
				p1EndXTraj /= p1YVelocity;
				p1EndXTraj /= 2;
				p1EndXTraj += p2Intersection[0];
				
				p1LaunchSpeed = p1Knockback * 0.03;
				
				p1LaunchDirection = 1;
				
				
				if(p2OppIntersection[0] - h1.getH() < 0)
				{
					p1EndXTraj = p2Intersection[0] - p1EndXTraj;
					p1LaunchDirection = -1;
					p1KnockbackTheta *= -1;
				}
				if(p2OppIntersection[1] - h1.getK() < 0)
					;
				
				ogpx = px;
				ogpy = py;
				
				p1KnockingBack = true;
				System.out.println("TTTTT\t" + p1KnockbackTheta);
				p1Knockbacker.start();
			}
		}
		else
		{
			startUpScreen.paintIcon(this,  g, 0, 0);
		}
	}
	
	//happens every time a tick occurs
	public void updateP1()
	{
			//jumping is if else is falling
			if(c1.getJumping() || c1.getDoubleJumping())
			{
				updatePlayer1Position(px, py-p1YVelocity);
				p1YVelocity -= GRAVITY;
				
				
				if (p1YVelocity < -1*JUMPHEIGHT)
				{
					c1.setJumping(false);
					c1.setDoubleJumping(false);
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
		h1.setH(x+pw );
		h1.setK((y+ph)*-1);
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
		
		
		double percentage = 120;
		//double percentage = c1.getHP();
		double weight = 100;
		//double weight = c1.getWeight();
		
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
		h2.setH(x+sw);
		h2.setK((y+sh)*-1);
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
					startUpScreen = new ImageIcon("assets/startUpAnimation.gif");
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
				double tempYDist = Math.abs(px-ogpx)*Math.tan(p1KnockbackTheta);
				tempYDist -= (p1YVelocity*Math.pow(Math.abs(px-ogpx), 2))/(2*p1Knockback*p1Knockback*Math.pow(Math.cos(p1KnockbackTheta),2));
				
				System.out.println("x\t" + tempXDist);
				System.out.println("T\t" + p1KnockbackTheta);
				System.out.println(tempYDist);
				
				updatePlayer1Position((int)(px+tempXDist),(int) (ogpy+tempYDist));
				
				p1LaunchSpeed -= 1;
				
				if(Math.abs(tempXDist) <= 1)
				{
					p1KnockingBack = false;
					p1Knockbacker.stop();
				}
			}
			
		}
	}
	
	
	
	private class mouseListen implements MouseListener, MouseMotionListener
	{

		@Override
		public void mouseDragged(MouseEvent e)
		{
			// TODO Auto-generated method stub
			
		}

		@Override
		public void mouseMoved(MouseEvent e)
		{
			// TODO Auto-generated method stub
			
		}

		@Override
		public void mouseClicked(MouseEvent e)
		{
			
		}

		@Override
		public void mousePressed(MouseEvent e)
		{
			if(!started && readyToPlay)
				started = true;
			
		}

		@Override
		public void mouseReleased(MouseEvent e)
		{
			// TODO Auto-generated method stub
			
		}

		@Override
		public void mouseEntered(MouseEvent e)
		{
			// TODO Auto-generated method stub
			
		}

		@Override
		public void mouseExited(MouseEvent e)
		{
			// TODO Auto-generated method stub
			
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
