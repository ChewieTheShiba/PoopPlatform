
//import JFrame 
//Poopstarter
import java.io.IOException;

import javax.swing.JFrame;


public class PoopStarter //Main class
{

	public static void main(String[] args) throws IOException
	{
		//change to match your values for width/height
		//these can be changed
		int w = 1920;
		int h = 1080;
		//sets up a JFrame object with title "Template"
		JFrame frame = new JFrame("Poop Platform");
		//make sure the jframe closes when you hit the 'x'
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		//adds the drawing panel to the frame
		frame.getContentPane().add(new PoopPanel(w,h));
		//resizes the frame to fit the pane
		//makes it visible
		frame.setUndecorated(true);
		frame.pack();
		frame.setVisible(true);
	

	}

}
