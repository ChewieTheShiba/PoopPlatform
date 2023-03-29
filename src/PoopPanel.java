import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.*;
import javax.swing.Timer;

public class PoopPanel extends JPanel
{
	//variables for the overall width and height
	private int w, h, py, px, pw, ph, sx, sy, sw, sh, p1YVelocity;
	private Hitbox h1, h2;
	private ImageIcon startUpAnimation, startUpScreen;
	private Timer startUpWait, ticker, jumper;
	private boolean started, readyToPlay, p1Jumping, p1DoubleJumping;
	private boolean p1R, p1L;
	private final int GRAVITY, JUMPHEIGHT;
	
	//sets up the initial panel for drawing with proper size
	public PoopPanel(int w, int h)
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
		px = 300;
		pw = 100;
		ph = 200;
		
		sy = 800;
		sx = 250;
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
		
		/*sets up startup animation and wait and then waiting screen
		Animation will play and then once it stops screen will appear
		and wait for the player to press start
		*/
		startUpScreen = new ImageIcon("assets/startUpAnimation.gif");
		startUpWait = new Timer(5000, new actionListen());
		startUpWait.start();
		
		//sets up timer for 1 tick of the game
		ticker = new Timer(1, new actionListen());
		
		//sets up jumping mechanism
		jumper = new Timer(500, new actionListen());
		p1Jumping = false;
		p1DoubleJumping = false;
		JUMPHEIGHT = 20;
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
			
			double intersection[] = h1.intersects(h2);
			
			if(intersection[0] != -1)
			{
				g.setColor(Color.red);
				g.drawOval((int)intersection[0], -1*(int)intersection[1], 20, 20);
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
			if(p1Jumping || p1DoubleJumping)
			{
				updatePlayer1Position(px, py-p1YVelocity);
				p1YVelocity -= GRAVITY;
				System.out.println(h1.getK() + "\t" + py);
				
				if (p1YVelocity < -1*JUMPHEIGHT)
				{
					p1Jumping = false;
					p1DoubleJumping = false;
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
					if(p1YVelocity > 50)
						p1YVelocity = 50;
					else
						p1YVelocity *= GRAVITY;
					updatePlayer1Position(px, py+p1YVelocity);
				}
			}
			
			//does while moving left or right
			if(p1R)
			{
				updatePlayer1Position(px+10, py);
				//System.out.println("hi");
			}
			if(p1L)
				updatePlayer1Position(px-10, py);
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
	
	public void p1Jump()
	{
		
	}
	
	private class actionListen implements ActionListener
	{

		public void actionPerformed(ActionEvent e)
		{
			Object source = e.getSource();
			
			//changes startup image to the waiting screen
			if(source.equals(startUpWait))
			{
				startUpWait.stop();
				startUpScreen = new ImageIcon("assets/startUpScreen.png");
				readyToPlay = true;
			}
			
			if(source.equals(ticker))
			{
				updateP1();
				//updateP2();
				
				repaint();
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
					p1L = true;
					break;
				case KeyEvent.VK_D:
					p1R = true;
					break;
				case KeyEvent.VK_W:
					if(!p1Jumping)
					{
						p1YVelocity = JUMPHEIGHT;
						p1Jumping = true;
					}
					else if(p1Jumping && !p1DoubleJumping)
					{
						p1YVelocity = JUMPHEIGHT;
						p1DoubleJumping = true;
					}
					break;
				case KeyEvent.VK_S:
					updatePlayer1Position(px, py+10);
					break;
				}
			}
			

		@Override
		public void keyReleased(KeyEvent e)
		{
			switch(e.getKeyCode())
			{
			case KeyEvent.VK_A:
				p1L = false;
				break;
			case KeyEvent.VK_D:
				p1R = false;
				break;
			}
		}
		
	}
	
	
}
