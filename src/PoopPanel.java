import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.Timer;

public class PoopPanel extends JPanel
{
	//variables for the overall width and height
	private int w, h, py, px, pw, ph, sx, sy, sw, sh;
	private Hitbox h1, h2;
	private ImageIcon startUpAnimation, startUpScreen;
	private JLabel startUpImage;
	private Timer startUpWait;
	
	//sets up the initial panel for drawing with proper size
	public PoopPanel(int w, int h)
	{
		this.w = w;
		this.h = h;
		this.setPreferredSize(new Dimension(w,h));
		
		this.addKeyListener(new keyListen());
		this.setFocusable(true);
		
		py = 100;
		px = 300;
		pw = 200;
		ph = 100;
		
		sy = 100;
		sx = 250;
		sw = 200;
		sh = 100;
		
		
		
		h1 = new Hitbox(px+(pw/2), py+(ph/2), pw, ph);
		h2 = new Hitbox(sx+(sw/2), sy+(sh/2), sw, sh);
		
		/*sets up startup animation and wait and then waiting screen
		Animation will play and then once it stops screen will appear
		and wait for the player to press start
		*/
		startUpAnimation = new ImageIcon("assets/startUpAnimation.gif");
		startUpScreen = new ImageIcon("assets/startUpScreen.png");
		startUpImage = new JLabel(startUpAnimation);
		startUpWait = new Timer(5000, new actionListen());
		startUpWait.start();
		this.add(startUpImage);
	}
	
	
	//all graphical components go here
	//this.setBackground(Color c) for example will change background color
	public void paintComponent(Graphics g)
	{
		//this line sets up the graphics - always needed
		super.paintComponent(g);
		
		//all drawings below here:
		g.drawOval(px, py, pw, ph);
		g.drawOval(sx, sy, sw, sh);
		System.out.println(px);
		System.out.println(py);
		System.out.println("y\t" + h1.getH());
		System.out.println("y\t" + h1.getK());
		
		for(int v = 0; v < 1920; v+=30)
		{
			g.drawLine(v, 0, v, 10);
			g.drawString("" + v, v, 20);
		}
		
		if(h1.intersects(h2)[0] != -1)
		{
			System.out.println("fodsuhiufisbdf");
		}
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
				startUpImage.setIcon(startUpScreen);
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
			switch(e.getKeyCode())
			{
			case KeyEvent.VK_LEFT:
				px -= 10;
				h1.setH(h1.getH()-10);
				break;
			case KeyEvent.VK_RIGHT:
				px += 10;
				h1.setH(h1.getH()+10);
				break;
			case KeyEvent.VK_UP:
				py -= 10;
				h1.setK(h1.getK()+10);
				break;
			case KeyEvent.VK_DOWN:
				py += 10;
				h1.setK(h1.getK()-10);
				break;
			}
			repaint();
			
		}

		@Override
		public void keyReleased(KeyEvent e)
		{
			// TODO Auto-generated method stub
			
		}
		
	}
	
	
}