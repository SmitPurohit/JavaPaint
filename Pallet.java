//the purpose of this class is to create objects for the pallet, allowing us to iterate through them

public class Pallet
{
   public int x,y,width,height; //can be public as data access is ok honestly
   public Pallet(){}
   
   public Pallet(int x, int y, int width, int height)
   {
      this.x = x;
      this.y = y;
      this.width = width;
      this.height = height;
   }
}