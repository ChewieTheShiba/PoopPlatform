import java.awt.*;
import java.awt.event.*;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.Timer;

public class Notify extends JPanel{

    public void Notify(String Message, String Type, String Character, Graphics notificationGraphics){
       super.paintComponent(notificationGraphics);

       notificationGraphics.drawRoundRect(ALLBITS, ABORT, WIDTH, HEIGHT, WIDTH, HEIGHT);
    }

}
