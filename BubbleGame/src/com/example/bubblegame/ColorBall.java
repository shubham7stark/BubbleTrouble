package com.example.bubblegame;


public class ColorBall{

   int x_cordinate; 
   int y_cordinate = 10;
   int velocity_color_ball;
   int radius;
   int color_code;
   int radius_code;
   
   public ColorBall(int x, int v, int c_c,int r_c){
	   x_cordinate = x; 
	   velocity_color_ball = v;
	   color_code = c_c;
       radius_code = r_c;
   }
   
   public void moveball(){
	   y_cordinate += velocity_color_ball;
   
   }
   
}
