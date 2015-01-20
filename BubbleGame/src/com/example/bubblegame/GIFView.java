package com.example.bubblegame;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.drawable.BitmapDrawable;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

public class GIFView extends SurfaceView{
	
	private static int WIDTH;
	private static int HEIGHT;
	
//	private Context mContext;
	
	private static int NO_OF_TIMES = 0;
    private int WIDTH_logo = 1;
    private int HEIGHT_logo = 1;
	
   
    /*
	protected static long y_pt;
	protected static long x_pt;
	private static long button_radius;
	
	private Paint mPaints[] = new Paint[4];
    
    
    protected boolean playClicked = false;
    protected boolean how_to_playClicked = false;
    protected boolean high_score_Clicked = false;
    protected boolean about_us = false;
    
    protected boolean saturation = false;
    protected boolean active_button = false;
    
    */
    
	private SurfaceHolder surfaceHolder;
	GIFViewThread GIFViewThread = null;
	
//	List<GIFViewElement> button_list = new ArrayList<GIFViewElement>();
	
	
	//int soundIds[] = new int[10];
	//SoundPool soundPool;
	
	
	public GIFView(Context context, int width, int height){
		super(context);
		//mContext = context;
		WIDTH = width;
		HEIGHT =height;
		NO_OF_TIMES = 0;
	
		/*----Paint-------*/
		/* 
		for(int i = 0; i < 4; i++){
			   
		mPaints[i] = new Paint();
		mPaints[i].setDither(true);
		mPaints[i].setStyle(Paint.Style.FILL);
	   }
	    
	    mPaints[0].setColor(Color.parseColor("#2cc185"));
	    mPaints[1].setColor(Color.parseColor("#f6c362"));
	    mPaints[2].setColor(Color.parseColor("#f1915e"));
	    mPaints[3].setColor(Color.parseColor("#e22d2d"));
	    
	    */
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
	
	//    initial();
	    
	    /*-------------------Soundpool goes here-------------------------*/
	   /*
	    soundPool = new SoundPool(10, AudioManager.STREAM_MUSIC, 0); 
	    soundIds[0] = soundPool.load(context, R.raw.new_bubble, 1);
	   */
	    /* 
	    soundID a soundID returned by the load() function
	    leftVolume left volume value (range = 0.0 to 1.0)
	    rightVolume right volume value (range = 0.0 to 1.0)
	    priority stream priority (0 = lowest priority)
	    loop loop mode (0 = no loop, -1 = loop forever)
	    rate playback rate (1.0 = normal playback, range 0.5 to 2.0)
	    */
	}
	
/*	//utility method of onCreateView
	void initial(){
	
    	//Adding Buttons
    	for(int j = 0; j < 3; j++){
			GIFViewElement gif_v = new GIFViewElement(j*WIDTH/3+WIDTH/6,HEIGHT-WIDTH/6);
			button_list.add(gif_v);
		}
    	GIFViewElement gif_v = new GIFViewElement(2*WIDTH/3+WIDTH/6,WIDTH/6);
    	button_list.add(gif_v);
		
	}
*/
	
  /*-------------------on Touch----------------------*/

 @Override
  public boolean onTouchEvent(MotionEvent event)
  {
	  /*
	    soundPool.play(soundIds[0], 1, 1, 1, 1, (float) 2.0);
	    Log.i("","on touch callled");
	  */
	  //after one BUBBLEGAME is shown. Action on clicking buttons
	  // changing x_pt,y_pt,button radius n (active button to click = false)
	  /*
	    if(NO_OF_TIMES == 10 && saturation){
	    playClicked = false;
	    how_to_playClicked = false;
	    high_score_Clicked = false;
	    about_us = false;
	    x_pt = 0; y_pt= 0;button_radius = WIDTH/6;
	    saturation = false;
		active_button = true;
	 
	  }*/
	  return false;
 }
	

	
/*----------------Update and Draw-------------------------*/
@Override
public void draw(Canvas canvas){
		super.draw(canvas);
		
		//canvas.drawColor(Color.WHITE);
		//draw logo of app_screen

		BitmapDrawable logo_image = (BitmapDrawable) getResources().getDrawable(R.drawable.play_button);    
		Bitmap logo = logo_image.getBitmap();
		HEIGHT_logo = (HEIGHT/WIDTH)*WIDTH_logo;
		Bitmap logo_scaled = Bitmap.createScaledBitmap(logo,WIDTH_logo,HEIGHT_logo,true);
		Paint paint_logo = new Paint();    
		paint_logo.setAlpha(255);                             //you can set your transparent value here    
		canvas.drawBitmap(logo_scaled,WIDTH/2 - WIDTH_logo/2, HEIGHT/3 - HEIGHT_logo/2 , paint_logo);
		
/*		  
		//draw after click button
	if(how_to_playClicked){
		canvas.drawCircle(x_pt, y_pt, button_radius, mPaints[0]);
	}
	if(high_score_Clicked){
		canvas.drawCircle(x_pt,y_pt, button_radius, mPaints[1]);
	}
	if(about_us){
		canvas.drawCircle(x_pt, y_pt, button_radius, mPaints[2]);
	}
	if(playClicked){
		canvas.drawCircle(x_pt,y_pt, button_radius, mPaints[3]);
	}
		
	
	//draw after click button and saturation achieved
	//Typeface typeface = Typeface.createFromAsset(getContext().getAssets(),"fonts/TITILLIUMWEB-LIGHT.ttf");
	
	if(how_to_playClicked && saturation){
	     Paint paint = new Paint();
	     paint.setColor(Color.WHITE);
	     paint.setAlpha(80);
	     paint.setTextSize(110);
	  //   paint.setTypeface(typeface);
	     canvas.drawText("HOW", WIDTH/2- 160 ,HEIGHT/2 - 50, paint);
	     canvas.drawText("TO", WIDTH/2-110 , HEIGHT/2 +100 , paint);
	     canvas.drawText("PLAY ?", WIDTH/2 -265
	    		 , HEIGHT/2 + 250, paint);
	    
	     Paint paint1 = new Paint();
	     paint1.setColor(Color.WHITE);
	     paint1.setAlpha(120);
	     paint1.setTextSize(50);
	    // paint1.setTypeface(typeface);
	     canvas.drawText("Instructions",WIDTH/2 - 272, 70, paint1);	
	}
	
	if(high_score_Clicked && saturation){
	    
		 Paint paint1 = new Paint();
	     paint1.setColor(Color.WHITE);
	     paint1.setTextSize(50);
	     //paint1.setTypeface(typeface);
	     canvas.drawText("HIGH SCORE", WIDTH/2 - 230, 80, paint1);
	
	     Paint paint = new Paint();
	     paint.setColor(Color.WHITE);
	     paint.setAlpha(80);
	     paint.setTextSize(80);
	     //paint.setTypeface(typeface);
	     canvas.drawText("WINNERS",WIDTH/2-260,HEIGHT-100, paint);	
	
	     Resources res = getResources();
	     Bitmap bitmap = BitmapFactory.decodeResource(res, R.drawable.high_score_logo_large);
	     canvas.drawBitmap(bitmap, WIDTH/2-165, HEIGHT/2-100, paint1);
	 
		 SharedPreferences preferences = mContext.getSharedPreferences("BUBBLE_GAME",
                 Context.MODE_PRIVATE);
            int first_high_score = preferences.getInt("first_high_score", -1);
            int sec_high_score = preferences.getInt("sec_high_score",-1);
            int third_high_score = preferences.getInt("third_high_score", -1);

           canvas.drawText("1 "+Integer.toString(first_high_score),WIDTH/2-100,HEIGHT/4,paint1);
           canvas.drawText("3 "+Integer.toString(sec_high_score)+"\n" ,WIDTH/2-100,3*HEIGHT/10 , paint1);
           canvas.drawText("3 "+Integer.toString(third_high_score), WIDTH/2-100, 7*HEIGHT/20, paint1);
	  }
*/

}
	
	
	//update
	public void update(long current_time){
		
		if(NO_OF_TIMES < 10){
		if(NO_OF_TIMES < 8){
			WIDTH_logo += WIDTH/8;
		}
		else{
			WIDTH_logo -= WIDTH/16;
		}
			NO_OF_TIMES++;
        }
		
		
	/*
    	if(NO_OF_TIMES == 9){ 
    		active_button = true;
    	}
    	
           	Log.i("tag", "update");
           	
     
     if((playClicked || how_to_playClicked || high_score_Clicked || about_us) && !saturation) 
     {
               y_pt = (4*y_pt+HEIGHT)/6; 	 
               button_radius += WIDTH/8;
               x_pt = (4*x_pt+WIDTH)/6;
     }
    
     if(button_radius > HEIGHT){
    	 saturation = true;
     }
     
	
     if(button_radius > 0.9*HEIGHT && playClicked){
    	 GIFViewThread.setRunning(false);
		 Intent intent = new Intent(mContext, GameActivity.class);
		 mContext.startActivity(intent);
     }
   
   */
		

	}




}
