import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.Timer;

public class PoopPanel extends JPanel
{
	//variables for the overall width and height
	private int w, h;
	private ImageIcon startUpAnimation, startUpScreen;
	private JLabel startUpImage;
	private Timer startUpWait;
	
	//sets up the initial panel for drawing with proper size
	public PoopPanel(int w, int h)
	{
		this.w = w;
		this.h = h;
		this.setPreferredSize(new Dimension(w,h));
		
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
	
	
}