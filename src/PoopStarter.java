
//import JFrame
import javax.swing.JFrame;


public class PoopStarter //Main class
{

	public static void main(String[] args)
	{
		//change to match your values for width/height
		//these can be changed
		int w = 2560;
		int h = 1440;
		
		Hitbox h1, h2;
		h1 = new Hitbox(100, 150, 200, 200);
		h2 = new Hitbox(150, 100, 250, 250);
		
		double[] d = h1.intersects(h2);
		System.out.println(d[0] + "\t" + d[1]);
		
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