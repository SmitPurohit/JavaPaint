import javax.swing.JPanel;
import java.awt.Graphics;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyListener;
import java.awt.event.KeyEvent;
import java.awt.event.FocusListener;
import java.awt.event.FocusEvent;
import javax.swing.JFrame;
import java.awt.Font;

public class JavaPaint extends JPanel implements KeyListener, ActionListener, FocusListener
{
   //Array of rects in pallet
   Pallet pallets[] = new Pallet[12];
   //frame dimensions
   private int fW, fH;

   //coordinates of the mouse
   private int xMouse,yMouse;
   
   //drawing variables
   Color color;
   int size;
   Color background = new Color(255,255,255);
   
   //draw/clear
   boolean mousePressed = false;
   boolean clear = true;
   
   public JavaPaint(int fW,int fH)
   {
      color = new Color(0,0,0);
      this.fW = fW;
      this.fH = fH;
      size = 1;
      addKeyListener(this);
      setFocusable(true);
      setFocusTraversalKeysEnabled(false);
   }
   
   public void paint(Graphics g)
   {
      
      canvas(g); //sets the canvas (background + pallet)
      
      if(mousePressed)
         draw(); //calls the draw class which ... draws
      if(clear)
      {
         addBackground(g); //clears the screen
         clear = false;
      }
   }
   public void addBackground(Graphics g)
   {
      g.setColor(background);
      g.fillRect(0,0,fW,fH-100);
   }
   public void draw(){}
   public void clear(){}
  
   //sets the canvas (background + pallet)
   private void canvas(Graphics g)
   {
      int width = 67; //the x of the top left corner and width of rectangle
      
      for(int k = 0;k<pallets.length;k++)
      {
         pallets[k] = new Pallet(width*k,600,width, fH);
         
      }
      int count = 0; //a sort of manual counter
      
      //the bottom part of the canvas - colors
      for(int k = 0; k < 12;k++)
      {
         changeColor(k, g); //utilizes the changeColor class with the corresponding numbers
         if(k>=9) //everything but the last three boxes(the last three are the size ones)
            g.drawRect(pallets[k].x,pallets[k].y,pallets[k].width,pallets[k].height);
         else
            g.fillRect(pallets[k].x,pallets[k].y,pallets[k].width,pallets[k].height);
      }
      changeColor(123, g);         
      g.drawRect(0,600,fW,fH);
      
      
      //sizes of the pen
      g.setColor(color);
      g.fillRect(9*width+33,630,5,5);
      g.fillRect(10*width+30,625,10,10);
      g.fillRect(11*width+25,620,20,20);
      
   }
   
   public Color getColor(){
      return color;}//returns the color to set the cursor color
   
   /*
   *@param an integer corresponding to a color:
      0 - red
      1 - orange
      2 - yellow
      3 - green
      4 - blue
      5 - magneta
      6 - gray/grey idk the spelling lol
      7 - black
      8 - white
      default - black
   */
   public void changeColor(int num, Graphics g)
   {
      switch(num)
      {
         case 0: color = Color.RED; 
            break;
         case 1: color = Color.ORANGE; 
            break;
         case 2: color = Color.YELLOW; 
            break;
         case 3: color = Color.GREEN; 
            break;
         case 4: color = Color.BLUE; 
            break;
         case 5: color = Color.MAGENTA; 
            break;
         case 6: color = Color.GRAY; 
            break;
         case 7: color = Color.BLACK; 
            break;
         case 8: color = Color.WHITE; 
            break;
         default: color = Color.BLACK; 
            break;
      }
      g.setColor(color);
   }
   @Override
   public void actionPerformed(ActionEvent e){ repaint();}
   
   @Override
   public void keyTyped(KeyEvent e){}
   
   @Override
   public void keyPressed(KeyEvent e)
   {
      int keyCode = e.getKeyCode();
      
   }
   
   @Override
   public void keyReleased(KeyEvent e)
   {
      int keyCode = e.getKeyCode();
      
   }
   
   @Override
   public void focusGained(FocusEvent e){}
   
   @Override
   public void focusLost(FocusEvent e){}
   
   
}