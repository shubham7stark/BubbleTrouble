package com.example.bubblegame;

public class GIFViewElement {


	   long x_cordinate; 
	   long y_cordinate;
	  
	   
	   public GIFViewElement(long x, long y){
		   x_cordinate = x; 
		   y_cordinate = y;
	   }
	   
	   public void moveball(int h){
		   y_cordinate += h;
	   }
	   
	}
