import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.image.BufferedImage;
import java.awt.Cursor;
import java.awt.Toolkit;
import java.awt.Point;

public class PaintEngine
{
   public static void main(String args[])
   {
      int fX = 200;
      int fY = 50;
      int fW = 820;
      int fH = 700;
     
      JFrame frame = new JFrame();
      frame.setBounds(fX,fY,fW,fH);
      frame.setTitle("JavaPaint");
      frame.setResizable(true);
      JavaPaint canvas = new JavaPaint(fW,fH); //makes canvas
      frame.add(canvas);
      
      frame.setVisible(true);
      frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      while(true){ //infinite loop
         
         canvas.repaint();
      }
   }
}