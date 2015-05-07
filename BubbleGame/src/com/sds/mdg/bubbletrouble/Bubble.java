package com.sds.mdg.bubbletrouble;


public class Bubble {
   int x_cordinate; 
   int y_cordinate = 10;
   double radius;
   int velocity_x;
   int velocity_y;
   int final_x;
   int final_y;

   public Bubble(int x, int y, int r){
	   x_cordinate = x; 
	   y_cordinate = y;
	   final_x = x;
	   final_y = y;
	   radius = r;
   }
   
   public void moveball(int x, int y){
	   final_x = x;
       final_y = y;
   }
   
   public void decreaseRadius(int r){
	   if(radius<75)
	 	   radius += 0.2*r;
	   else if(radius<150)
		   radius += 0.3*r;
	   else if(radius<300)
			radius += 0.35*r;
	   else return;
   }
   
   public void increaseRadius(int r){
	   if(radius<75)
 	   radius += 0.3*r;
	   else if(radius<150)
	   radius += 0.4*r;	   
	   else if(radius<250)
		radius += 0.5*r;   
	   else return;
   }
}
