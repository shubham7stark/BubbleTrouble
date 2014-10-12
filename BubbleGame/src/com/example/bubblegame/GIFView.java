package com.example.bubblegame;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.BitmapDrawable;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

public class GIFView extends SurfaceView{
	
	private static int WIDTH;
	private static int HEIGHT;
	private static int WIDTH_GAP;
	
	private Context mContext;
	
	private static final int ball_radius = 40;
	
	private static final int margin_x = 15;
	
	private static int NO_OF_TIMES = 0;
	
	private static final int MAX_BALLS = 8;
	
	private static long y_pt;
	private static long x_pt;
	private static long button_radius;
	
	private Paint mPaints[] = new Paint[4];
    
    private boolean active_button = false;
    
    private boolean playClicked = false;
    private boolean how_to_playClicked = false;
    private boolean high_score_Clicked = false;
    private boolean about_us = false;
    private boolean saturation = false;
    
	private SurfaceHolder surfaceHolder;
	GIFViewThread GIFViewThread = null;
	
	List<GIFViewElement> element_list = new ArrayList<GIFViewElement>();
	List<GIFViewElement> button_list = new ArrayList<GIFViewElement>();
	
	
	
	public GIFView(Context context, int width, int height){
		super(context);
		
		WIDTH = width;
		WIDTH_GAP = (width - 2*margin_x)/(MAX_BALLS-1);
		HEIGHT =height;
	
		y_pt = HEIGHT - WIDTH/3;
		button_radius = WIDTH/8; 
		NO_OF_TIMES = 0;
       
		/*----Paint-------*/
		 for(int i = 0; i < 4; i++){
			   
		mPaints[i] = new Paint();
		mPaints[i].setAntiAlias(true);
		mPaints[i].setDither(true);
		mPaints[i].setStyle(Paint.Style.FILL);
		mPaints[i].setStrokeJoin(Paint.Join.MITER);
		mPaints[i].setStrokeCap(Paint.Cap.SQUARE);
		mPaints[i].setStrokeWidth(5);
		mPaints[i].setAlpha(30);
	    }
	    
	    mPaints[0].setColor(Color.BLUE);
	    mPaints[1].setColor(Color.RED);
	    mPaints[2].setColor(Color.GREEN);
	    mPaints[3].setColor(Color.MAGENTA);
	    
	    
	    /*------------------*/
	    
	    
		GIFViewThread = new GIFViewThread(this);
		
		surfaceHolder = this.getHolder();
	    surfaceHolder.addCallback(new SurfaceHolder.Callback() {
			
			@Override
			public void surfaceDestroyed(SurfaceHolder arg0) {
				GIFViewThread.setRunning(false);
			    boolean retry = true;
			    while (retry) {
			        try {
			            GIFViewThread.join();
			            retry = false;
			        } catch (InterruptedException e) {
			            // try again shutting down the thread
			        }
			    }

			}
			
			@Override
			public void surfaceCreated(SurfaceHolder arg0) {
				// TODO Auto-generated method stub
				GIFViewThread.setRunning(true);
				GIFViewThread.start();
			}
			
			@Override
			public void surfaceChanged(SurfaceHolder arg0, int arg1, int arg2, int arg3) {
				// TODO Auto-generated method stub
				
			}
	    
	    });
	
	    initial();
	 }
	
	void initial(){
		//Adding balls BUBBLEGAME
		for(int j = 0; j < MAX_BALLS; j++){
			GIFViewElement gif_v = new GIFViewElement(j*WIDTH_GAP+margin_x,(j%2 == 0)?0:HEIGHT);
			element_list.add(gif_v);
		}
	
    	//Adding Buttons
    	for(int j = 0; j < 3; j++){
			GIFViewElement gif_v = new GIFViewElement(j*WIDTH/3+WIDTH/6,HEIGHT-WIDTH/6);
			button_list.add(gif_v);
		}
    	GIFViewElement gif_v = new GIFViewElement(2*WIDTH/3+WIDTH/6,WIDTH/6);
    	button_list.add(gif_v);
		
	}
	
  /*-------------------on Touch----------------------*/
  @Override
  public boolean onTouchEvent(MotionEvent event) {
		
	  //after one BUBBLEGAME is shown. Action on clicking buttons
	  // changing x_pt,y_pt,button radius n (active button to click = false)
	  
	if(active_button){
	  if(event.getY() > HEIGHT-WIDTH/3){
		 if(event.getX() < WIDTH/3)
			{how_to_playClicked = true;
			 x_pt = WIDTH/6;
			}
		 if(event.getX()<2*WIDTH/3 && event.getX()>WIDTH/3){
			high_score_Clicked = true;
			x_pt = WIDTH/3 + WIDTH/6;
		    }
		 if(event.getX()>2*WIDTH/3){
			playClicked = true;
		    x_pt = 2*WIDTH/3 + WIDTH/6;
		 } 
		 y_pt = HEIGHT-WIDTH/6;  
		 button_radius = WIDTH/8;
		 active_button = false;
	  }

	  if(event.getY() < WIDTH/3 && event.getX()>2*WIDTH/3){
		  about_us = true;
		  x_pt = 2*WIDTH/3 + WIDTH/6;
		  y_pt = WIDTH/6;  
		  button_radius = WIDTH/8;
		  active_button = false;
	  }
	  
	}
	
	else{
		if(NO_OF_TIMES == 25 && saturation){
	    
		playClicked = false;
	    how_to_playClicked = false;
	    high_score_Clicked = false;
	    about_us = false;
	    
	    x_pt = 0; y_pt= 0;button_radius = WIDTH/8;
	
	    saturation = false;
		active_button = true;
	
	}
		
	}
	
	
		return true;
 
 }
	

	
/*----------------Update and Draw-------------------------*/
@Override
public void draw(Canvas canvas){
		super.draw(canvas);
		canvas.drawColor(Color.LTGRAY);
		/*BitmapDrawable bd = (BitmapDrawable) getResources().getDrawable(R.drawable.loading);    
		Bitmap bm = bd.getBitmap();    
		Paint paint = new Paint();    
		paint.setAlpha(60);                             //you can set your transparent value here    
		canvas.drawBitmap(bm, 0, 0, paint);
		*/
	for(int i =0; i < element_list.size(); i++){
		GIFViewElement gif_v = element_list.get(i);
		canvas.drawCircle(gif_v.x_cordinate, gif_v.y_cordinate, ball_radius, mPaints[i%4]);
	}
	
	//Draw Buttons
	if(active_button){
		for(int i = 0; i <4;i++){
			GIFViewElement gif_v = button_list.get(i);
			canvas.drawCircle(gif_v.x_cordinate, gif_v.y_cordinate, WIDTH/8, mPaints[i]);
		}
   }

	
	//draw after click button
	if(playClicked){
	canvas.drawCircle(x_pt,y_pt, button_radius, mPaints[2]);
	}
	
	if(how_to_playClicked){
		canvas.drawCircle(x_pt, y_pt, button_radius, mPaints[0]);
	}
	if(high_score_Clicked){
		canvas.drawCircle(x_pt,y_pt, button_radius, mPaints[1]);
	}
	if(about_us){
		canvas.drawCircle(x_pt, y_pt, button_radius, mPaints[3]);
	}
		
		
/*
	if(active_button){
	     canvas.drawBitmap(bitmap, l, t, paint);
	     canvas.drawBitmap(bitmap, l, t, paint);
	     canvas.drawBitmap(bitmap, l, t, paint);
	}
	
	if(playClicked)
		canvas.drawBitmap(bitmap, l, t, paint);
    if(how_to_playClicked)
    	canvas.drawBitmap(bitmap, l, t, paint);
    if(high_score_Clicked)
   	canvas.drawBitmap(bitmap, l, t, paint);
  
  */
	
 }
	
	
	/////////////////////////////////update
	
	public void update(long current_time){
		
		if(NO_OF_TIMES < 25){
		//motion of balls
    	for(int i  = 0 ; i < element_list.size(); i++){
    		element_list.get(i).moveball((i%2==0)?HEIGHT/75:-2*HEIGHT/75);	
		}
     	NO_OF_TIMES++;
        }
		
    	if(NO_OF_TIMES == 24){ 
    		active_button = true;
    	}
    	
           	Log.i("tag", "update");
     
     if((playClicked || how_to_playClicked || high_score_Clicked||about_us) && !saturation) 
     {
               y_pt = y_pt/2 + HEIGHT/4; 	 
               button_radius += WIDTH/10;
               x_pt = x_pt/2 + WIDTH/4;
     }
    
     /*
      * if(button_radius > 1.4*HEIGHT && play_clicked){
    	 saturation = true;
     }
	 */
     
     if(button_radius > 1.5*HEIGHT){
    	 saturation = true;
     }
	
     if(button_radius > 1.3*HEIGHT && playClicked){
    	 GIFViewThread.setRunning(false);
		 mContext = getContext();
    	 Intent intent = new Intent(mContext, GameActivity.class);
    	    mContext.startActivity(intent);
     }
	
     
   }
		
}
