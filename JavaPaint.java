import javax.swing.JPanel;
import javax.swing.JFrame;
import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.event.*;
import java.applet.Applet;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
//TODO: Make saving rectangle more precise, Choose file name/destination?
//11/1/19: Going to take a break from this and work on other projects
//:Currently as a draw (d) and polygon (p) mode, and can save to a file with a defualt name(s); (_letter_) denotes a key that does the preceding action
public class JavaPaint extends JPanel implements KeyListener, ActionListener, FocusListener, MouseListener, MouseMotionListener
{
   //keyboard press variables
   boolean enterPressed = false;
   //for Polygon drawing
   int pressCount = 0;
   Polygon poly = new Polygon();

   //boolean for deciding the drawing "mode"
   boolean p, d = false; //polygon and regular draw
   
   //boolean for saving
   private boolean save = false;
   boolean cred = false;
   //Array of rects in pallet
   Pallet pallets[] = new Pallet[12];
   
   //frame dimensions
   private int fW, fH;

   //coordinates of the mouse
   private int xMouse,yMouse;
   
   
   //drawing variables
   private Color color = Color.BLACK;
   private int size;
   Color background = new Color(255,255,255);
   
   
   //draw/clear
   boolean mousePressed = false;
   boolean mouseClicked = false;
   boolean clear = true;
   boolean backChange = false;
   
   public JavaPaint(int fW,int fH)
   {
      p = false;
      d =!p;
      color = new Color(0,0,0);
      this.fW = fW;
      this.fH = fH;
      size = 5;
      addKeyListener(this);
      setFocusable(true);
      setFocusTraversalKeysEnabled(false);
      addMouseListener(this);
      addMouseMotionListener(this);
      
      
      
   }
   
   public void paint(Graphics g)
   {    
      canvas(g);
      if(save)
         save(g);
      //if in draw mode
      if(d){
         if(mousePressed)
         {
         
         
            draw(g); //calls the draw class which ... draws
         }
      }
     //if in polygon mode
      if(p){
         
         if(mouseClicked){
            enterPressed = false;
            pressCount++;
            drawPoly(g);
            mouseClicked = false;
            
            
            
         }
         if(enterPressed){
               g.setColor(color);
               g.drawPolygon(poly);
               pressCount = 0;
               mouseClicked = false;
               enterPressed = false;
               poly = null;
               poly = new Polygon();
            
            }
      }
            
      if(clear)
      {
         Color oldColor = getColor();
         canvas(g);  //sets the canvas (background + pallet)
         addBackground(g); //clears the screen (sets the background color to the background color... for changing background color)
         clear = false;
         color = oldColor;
         poly = null;
         poly = new Polygon();
         pressCount = 0;
      }
      if(cred)
      {
         
         g.drawString("Created by Smit Purohit", 30,30); 
         
      }
      
   }
   
   public void addBackground(Graphics g) //sets the background to Color background attribute
   {
      
      g.setColor(background);
      g.fillRect(0,0,fW,fH-100);
   }
   
   public void draw(Graphics g) //runs whenever the mouse is pressed, so no need to check for mousePress
   {
      
      
      if(yMouse>600-size) //if in the "pallet"
      {
         if(backChange) //if the shift key is pressed, meaning the background would change colors
         {
            for(int k =0;k<pallets.length-3;k++) //for loop for the colors
            {
            
               if(xMouse >= pallets[k].x && xMouse <= pallets[k].x+67) //is the mouseX in the pallet - color
               {
                  changeBackground(k, g);
               
               
               }
            }
         }
         else
            for(int k =0;k<pallets.length-3;k++) //for loop for the colors
            {
            
               if(xMouse >= pallets[k].x && xMouse <= pallets[k].x+67) //is the mouseX in the pallet - color
               {
                  changeColor(k, g);
                  
               
               }
            }
         for(int k = pallets.length-3;k < pallets.length; k++)
         {
            if(xMouse > pallets[k].x && xMouse < pallets[k].x+67) //is the mouseX in the pallet - size
               changeSize(k-8);
         }
      }
      else
      {
         g.setColor(color);
         //g.drawLine(xMouse,yMouse,xMouse,yMouse);
         g.fillOval(xMouse, yMouse, size, size);
      }
   }
   //This draw method will draw a polygon
   public void drawPoly(Graphics g) //runs whenever the mouse is pressed, so no need to check for mousePress
   {
      
      
      if(yMouse>600-size) //if in the "pallet"
      {
         if(backChange) //if the shift key is pressed, meaning the background would change colors
         {
            for(int k =0;k<pallets.length-3;k++) //for loop for the colors
            {
            
               if(xMouse >= pallets[k].x && xMouse <= pallets[k].x+67) //is the mouseX in the pallet - color
               {
                  changeBackground(k, g);
               
               
               }
            }
         }
         else
            for(int k =0;k<pallets.length-3;k++) //for loop for the colors
            {
            
               if(xMouse >= pallets[k].x && xMouse <= pallets[k].x+67) //is the mouseX in the pallet - color
               {
                  changeColor(k, g);
              
               
               }
            }
         
      }
      else
      {
         
         if(!enterPressed){
            g.setColor(color);
            System.out.println(color);
            poly.addPoint(xMouse,yMouse);
            g.fillOval(xMouse, yMouse, 2, 2);
            System.out.println(pressCount);
         }
         
           
      }
   }
   
       
       
            
   
  
   //sets the canvas (background + pallet)
   private void canvas(Graphics g)
   {
      int width = 67; //the x of the top left corner and width of rectangle
      Color oldColor = color;
      for(int k = 0;k<pallets.length;k++)
      {
         pallets[k] = new Pallet(width*k,600,width, fH);
         
      }
      
      
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
      g.fillOval(9*width+33,630,5,5);
      g.fillOval(10*width+30,625,10,10);
      g.fillOval(11*width+25,620,20,20);
      color = oldColor;
      
   }
   //returns the color to set the cursor color
   public Color getColor()
   {
      return color;
   }
   
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
   
   //@param are the same as above, except the default is white
   public void changeBackground(int num, Graphics g)
   {
      switch(num)
      {
         case 0: background = Color.RED; 
            break;
         case 1:  background = Color.ORANGE; 
            break;
         case 2:  background = Color.YELLOW; 
            break;
         case 3:  background = Color.GREEN; 
            break;
         case 4:  background = Color.BLUE; 
            break;
         case 5:  background = Color.MAGENTA; 
            break;
         case 6:  background = Color.GRAY; 
            break;
         case 7:  background = Color.BLACK; 
            break;
         case 8:  background = Color.WHITE; 
            break;
         default:  background = Color.WHITE; 
            break;
      }
      clear = true;
   }
   
   /*
    *@param num corresponds to the size:
    *1: small (5x5)
    *2: medium (10x10)
    *3: large (20x20)
    *default is small
    */
   public void changeSize(int num)
   {
      switch(num)
      {
         case 1: size = 5; 
            break;
         case 2: size = 10; 
            break;
         case 3: size = 20; 
            break;
         default: size = 5; 
            break;
      }
   }
   
   public void save(Graphics d)
   {
      try{
      BufferedImage image = new Robot().createScreenCapture(new Rectangle(220,80,fW-30,600));
      ImageIO.write(image, "png", new File("screen.png"));}
      catch(Exception e){}
   }
      
   
   
   @Override
   public void actionPerformed(ActionEvent e){ repaint();}
   
   
   @Override
   public void keyTyped(KeyEvent e)
   {
      int keyCode = e.getKeyCode();
      
      
         
   }
   
   @Override
   public void keyPressed(KeyEvent e) //49 is num 1 ... -:45 =:61 SHiFT: 16, CTRL: 17
   {
      int keyCode = e.getKeyCode();
      if(keyCode == KeyEvent.VK_D){
         enterPressed = true;
         d = true;
         p= false;
         }
      if(keyCode == KeyEvent.VK_P)
      {
         p = true;
         d = false;
      }   
      if(keyCode == KeyEvent.VK_ENTER)
         enterPressed = true;
      if(keyCode == KeyEvent.VK_C)
         cred = true;
      if(keyCode == KeyEvent.VK_S)
         save = true;
      if(keyCode == 32) //spaceBar
      {
         clear = true;
      }
      for(int k = 49; k <= 57; k++)
      {
         if(keyCode == k)
         {
            switch(k-49)
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
            
         } 
      }
      if(keyCode == 45)
         if(size>1)
            size--;
      if(keyCode == 61)
         if(size < 30)
            size++;
      if(keyCode == 16)
         backChange = true;
      
       
   }
   
   @Override
   public void keyReleased(KeyEvent e)
   {
      int keyCode = e.getKeyCode();
      if(keyCode == 16)
         backChange = false;
      if(keyCode == KeyEvent.VK_S)
         save = false;
      if(keyCode == KeyEvent.VK_C)
         cred = false;
      
      
   }
   
   @Override
   public void focusGained(FocusEvent e){}
   
   @Override
   public void focusLost(FocusEvent e){}
   
   @Override
   public void mouseClicked(MouseEvent e) {
      
      xMouse = e.getX();
      yMouse = e.getY();
      mouseClicked = true;
      //System.out.println(xMouse);
      //System.out.println(yMouse);
      mouseMoved(e);
   
   }

   @Override
   public void mouseExited(MouseEvent e) {
   
   }

   @Override
   public void mouseEntered(MouseEvent e) {
   
   }

   @Override
   public void mousePressed(MouseEvent e) {
   
      xMouse = e.getX();
      yMouse = e.getY();
      mousePressed = true;
      //System.out.println(xMouse);
      //System.out.println(yMouse);
      mouseMoved(e);
   
   }

   @Override
   public void mouseReleased(MouseEvent e) {
      
      xMouse = e.getX();
      yMouse = e.getY();
      mousePressed = false;
      //System.out.println(xMouse);
      //System.out.println(yMouse);
   
      
   }
   
   @Override
   public void mouseMoved(MouseEvent e)
   {
      xMouse = e.getX();
      yMouse = e.getY();
     
   }
   @Override
   public void mouseDragged(MouseEvent e) 
   {
      xMouse = e.getX();
      yMouse = e.getY();
   }
   
   
   
}
