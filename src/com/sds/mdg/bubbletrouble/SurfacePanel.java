package com.sds.mdg.bubbletrouble;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.RectF;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.SoundPool;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

public class SurfacePanel extends SurfaceView {

	private Context mContext;
	
	private static int WIDTH;
	private static int HEIGHT;
	private static int WIDTH_GAP;
	
	private static int radius;
	private static int margin_x;
	
	private static int NO_OF_TIMES = 40;
	private static int NO_OF_TIMES_MOVED = 40;
	
	private static final int MAX_BALLS = 8;
	private static final int NO_OF_BALLS = 4;
	
	
	private static int velocity;
	private static final int TYPES_OF_BALL = 5;
    
	private Paint mPaint;
    private Paint mPaints[] = new Paint[8];
    
    protected static int score;
    protected static int life_left;
    private static int no_of_time_execution;
    
	private SurfaceHolder surfaceHolder;
	SurfaceColorBallThread surfaceColorBallThread = null;
	
	List<ColorBall> listColorBalls = new ArrayList<ColorBall>();
	Bubble bubble;

	protected boolean is_game_paused = false;
	protected boolean is_game_started = false;
	
	SoundPool soundPool;
	
	int soundIds[] = new int[10];
	
	//ref for getting volume status
		SharedPreferences preferences;
		boolean is_vol_on = false;

		
	public SurfacePanel(Context context, int width, int height){
		super(context);
	
		margin_x = (int)(0.05*width);
		WIDTH = (int)(0.9*width);
		WIDTH_GAP = WIDTH/(MAX_BALLS+1);
		HEIGHT =height;
	    
		radius = WIDTH/(2*MAX_BALLS);
		
		velocity = WIDTH/100;
		
		bubble = new Bubble(width/2,2*height/3,width/8);
       
		/*----Paint-------*/
		for(int i =0;i<8;i++){
		mPaints[i] = new Paint();
	    //mPaint.setAntiAlias(true);
		mPaints[i].setDither(true);
		mPaints[i].setColor(Color.BLUE);
		mPaints[i].setStyle(Paint.Style.FILL);
		mPaints[i].setStrokeJoin(Paint.Join.MITER);
		mPaints[i].setStrokeCap(Paint.Cap.SQUARE);
		mPaints[i].setStrokeWidth(5);
		}
		
		mPaints[0].setColor(Color.parseColor("#e54242"));
		mPaints[1].setColor(Color.parseColor("#39aaab"));
		mPaints[2].setColor(Color.parseColor("#3ad165"));
		mPaints[3].setColor(Color.parseColor("#f3d34a"));
		mPaints[4].setColor(Color.parseColor("#e58630"));
		mPaints[5].setColor(Color.BLACK);
       	for(int i =0;i<6;i++)
		mPaints[i].setAlpha(150);
		
			/*------------------*/
	    mPaint = new Paint();
	    mPaint.setStrokeWidth(8);
	    mPaint.setStyle(Style.STROKE);
	    mPaint.setColor(Color.WHITE);
	    mPaint.setStyle(Paint.Style.FILL);
		mPaint.setAlpha(50);
	      /*----------------------*/
	    
	    mPaints[6].setTextSize(HEIGHT/20);
	    mPaints[6].setColor(Color.WHITE);
	    mPaints[7].setTextSize(HEIGHT/20);
	    mPaints[7].setColor(Color.parseColor("#43d2fc"));
	    
	    
	    
	    score = 0;
	    life_left = 5;
	    no_of_time_execution = 0;
	    
		surfaceColorBallThread = new SurfaceColorBallThread(this);
		
		surfaceHolder = this.getHolder();
	    surfaceHolder.addCallback(new SurfaceHolder.Callback() {
			
			@Override
			public void surfaceDestroyed(SurfaceHolder arg0) {
				surfaceColorBallThread.setRunning(false);
			    boolean retry = true;
			    while (retry) {
			        try {
			            surfaceColorBallThread.join();
			            retry = false;
			        } catch (InterruptedException e) {
			            // try again shutting down the thread
			        }
			    }
			}
			
			@Override
			public void surfaceCreated(SurfaceHolder arg0) {
				// TODO Auto-generated method stub
				surfaceColorBallThread.setRunning(true);
				surfaceColorBallThread.start();
 		}
			
			@Override
			public void surfaceChanged(SurfaceHolder arg0, int arg1, int arg2, int arg3) {
				// TODO Auto-generated method stub
				
			}
	    
	    });
	    mContext = context;
		///shared preferences for checking volume
        preferences = mContext.getSharedPreferences("BUBBLE_GAME",
                Context.MODE_PRIVATE);
                      is_vol_on = preferences.getBoolean("is_vol_on",true);


	    
	    /*-------------------Soundpool goes here-------------------------*/
	    soundPool = new SoundPool(10, AudioManager.STREAM_MUSIC, 0); 
	    soundIds[0] = soundPool.load(context, R.raw.new_bubble, 1);
	
	    /* 
	    soundID a soundID returned by the load() function
	    leftVolume left volume value (range = 0.0 to 1.0)
	    rightVolume right volume value (range = 0.0 to 1.0)
	    priority stream priority (0 = lowest priority)
	    loop loop mode (0 = no loop, -1 = loop forever)
	    rate playback rate (1.0 = normal playback, range 0.5 to 2.0)
	    */
	    MediaPlayer btnSound = MediaPlayer.create(context, R.raw.gif_bk);
        if(is_vol_on)    
	    btnSound.start();
	      
	}
	
	
	
  /*-------------------on Touch----------------------*/
	@SuppressLint("ClickableViewAccessibility")
	@Override
	public boolean onTouchEvent(MotionEvent event) {
        is_game_started = true;
		Log.i("fjhbk","its view bing ");
		if(!is_game_paused){
			/*
			if(!surfaceColorBallThread.running){
				surfaceColorBallThread.setRunning(true);
			    retrieveThread = new RetrieveThread(mContext);
				(retrieveThread).execute();
			}
			*/
			boolean is_bubble_touched = false;
			switch(event.getAction()){
			case MotionEvent.ACTION_DOWN:
				if(((event.getX()-bubble.x_cordinate <= 30 )&&(event.getRawX()-bubble.x_cordinate >= -30 ))
						&& (event.getRawY() - bubble.y_cordinate <= 30)&&(event.getRawY() - bubble.y_cordinate >= -30))
				{
				is_bubble_touched = true;
				Log.i("motion", "yes");
				}
				Log.i("motion", "Action down called");
			break;
			
			case MotionEvent.ACTION_UP:
			   if(is_bubble_touched)
				   bubble.moveball((int)event.getX(), (int)event.getY());			
			   Log.i("motion", "Action up called");
			break;
			
			case MotionEvent.ACTION_MOVE:
				bubble.moveball((int)event.getX(), (int)event.getY());			
			break;
			}
			
			bubble.moveball((int)event.getX(), (int)event.getY());
	    	bubble.increaseRadius(WIDTH/50);

		
		}
		
		return true;
	}
	

	
	/*----------------Update and Draw-------------------------*/
	@Override
	public void draw(Canvas canvas){
		super.draw(canvas);
		//canvas.drawColor(Color.WHITE);
	   
	//draw balls	
	for(int i =0; i < listColorBalls.size(); i++){
		ColorBall colorBall = listColorBalls.get(i);
	
		if(colorBall.y_cordinate > HEIGHT){
		listColorBalls.remove(i);
		}
		else{
		canvas.drawCircle(colorBall.x_cordinate,
				              colorBall.y_cordinate,
				              radius+(radius*colorBall.radius_code/TYPES_OF_BALL),
				               mPaints[colorBall.color_code]);
		}	
	}
	
	//bubble draw
	if(bubble.radius > 0){
	    int to_move_x = bubble.x_cordinate/2 + bubble.final_x/2;
	    int to_move_y =  bubble.y_cordinate/2 + bubble.final_y/2;
	    
	    bubble.x_cordinate = to_move_x;
		bubble.y_cordinate = to_move_y;
	  
		canvas.drawCircle(to_move_x,to_move_y, (int)bubble.radius, mPaint);
	
		
	  Log.i("motion", Integer.toString(to_move_x)+ "  "+ Integer.toString(to_move_y));
	
	}
	else{
		try {
			this.onDie();
			 is_game_started = false;
		} catch (InterruptedException e){
			e.printStackTrace();
		}
	}
	
   /*	
	//draw coins 
	BitmapDrawable bitmapDrawable_logo = (BitmapDrawable) getResources().getDrawable(R.drawable.coin);    
	Bitmap logo = bitmapDrawable_logo.getBitmap();    
	Bitmap logo_scaled = Bitmap.createScaledBitmap(logo, WIDTH/8 ,WIDTH/8, true);
	Paint paint_coins = new Paint();    
	paint_coins.setAlpha(200);                             //you can set your transparent value here    

	for(int i =0; i < listCoins.size(); i++){
		ColorBall colorBall = listColorBalls.get(i);
		if(colorBall.y_cordinate > HEIGHT){
		listCoins.remove(i);
		}
		else{
		canvas.drawBitmap(logo_scaled,colorBall.x_cordinate,colorBall.y_cordinate, paint_coins);
		}	
	}
    */
	
	
	
   if(is_game_paused){	
	RectF rectf = new RectF(0,0,WIDTH+2*margin_x+margin_x,HEIGHT);
	canvas.drawRect(rectf,mPaints[5]);
   }
   
 //Draw ScorEBoard
	canvas.drawText(Integer.toString(life_left),
			margin_x,HEIGHT/13, mPaints[6]);
	
	canvas.drawText(Integer.toString(score),
			margin_x,24*HEIGHT/25, mPaints[7]);
   
	}
	
	//OnUpdateUtility
	boolean isInArray(int arr[], int key, int n){
		for(int i =0; i < n;i++)
			if(arr[i] == key)return true;
		return false;
	}
	
/*-----------------------------------------------------update----------*/
	
	public void update(long current_time){
		//removing n increasing score n lifes once bubble encircle balls
		if(!is_game_paused && is_game_started){
	
		for(int i  = 0 ; i < listColorBalls.size(); i++){
		ColorBall cballs = listColorBalls.get(i);
		int dist_bw_ball_n_bubble_x = ((cballs.x_cordinate - bubble.x_cordinate)>0)?
				(cballs.x_cordinate - bubble.x_cordinate):(bubble.x_cordinate - cballs.x_cordinate);
		int dist_bw_ball_n_bubble_y = ((cballs.y_cordinate - bubble.y_cordinate)>0)?
				(cballs.y_cordinate - bubble.y_cordinate):(bubble.y_cordinate - cballs.y_cordinate);
		if(dist_bw_ball_n_bubble_x <(bubble.radius - radius) && dist_bw_ball_n_bubble_y < (bubble.radius - radius))
		{
		ColorBall cb = listColorBalls.get(i);
		score += cb.color_code*cb.radius_code;
		
		if(is_vol_on)
	    soundPool.play(soundIds[0], 1, 1, 1, 3, (float) 2.0); //sound on bubble capturing
			
			try {
				if(cb.color_code == 0 && life_left > 0)
				{if(life_left>1){
					life_left--;
				}else{
					onDie();
				}
				}
			} catch (InterruptedException e) {
				Log.i("",toString());
			}
			
		listColorBalls.remove(i);	
	    }
		}
		
	/*	//motion of coins , removing of coins , inc no of coins
		for(int i  = 0 ; i < listCoins.size(); i++){
			ColorBall cballs = listCoins.get(i);
			int dist_bw_ball_n_bubble_x = ((cballs.x_cordinate - bubble.x_cordinate)>0)?
					(cballs.x_cordinate - bubble.x_cordinate):(bubble.x_cordinate - cballs.x_cordinate);
			int dist_bw_ball_n_bubble_y = ((cballs.y_cordinate - bubble.y_cordinate)>0)?
					(cballs.y_cordinate - bubble.y_cordinate):(bubble.y_cordinate - cballs.y_cordinate);
			if(dist_bw_ball_n_bubble_x <(bubble.radius - radius) && dist_bw_ball_n_bubble_y < (bubble.radius - radius))
			{
			ColorBall cb = listColorBalls.get(i);
			no_of_coins += coin_rate*cb.radius_code;
			}
			}
		*/	
		
		//motion of balls
    	for(int i  = 0 ; i < listColorBalls.size(); i++){
	      	listColorBalls.get(i).moveball();	
		}
    	
    //Adding more balls
    	Random random = new Random();
        
    	if(NO_OF_TIMES_MOVED == NO_OF_TIMES){
    		int a[] = new int[4*NO_OF_BALLS];
        	NO_OF_TIMES_MOVED = 0;
    
        	for(int j = 0; j < NO_OF_BALLS; j++){
        	int k = random.nextInt(100)%10;
        	while(isInArray(a,k,j)){
        			k = random.nextInt(100)%MAX_BALLS;
        	}
        	a[j] = k;
        	}
        	
    		for(int j = NO_OF_BALLS; j < 4*NO_OF_BALLS; j++){
    		a[j] = random.nextInt(100)%TYPES_OF_BALL;
    		Log.i("random", Integer.toString(a[j]));
    		}
    		
    	    
    		for(int j = 0; j < NO_OF_BALLS; j++){
    		ColorBall colorBall = new ColorBall(a[j]*WIDTH_GAP+margin_x,
    				                          (a[j+3*NO_OF_BALLS]+1)*velocity/2,
    				                           a[j+NO_OF_BALLS],
    				                          a[j+2*NO_OF_BALLS]
                                     				);
    	      listColorBalls.add(colorBall);
    		}
    	
    		Log.i("tag", "update balls");
    	   
     }
    	
    //Adding More balls complete
         	NO_OF_TIMES_MOVED++;
           	Log.i("tag", "update");

    //bubble radius auto-decrease
       	bubble.decreaseRadius(-1*WIDTH/25);
	   	no_of_time_execution++;
       	if(no_of_time_execution > 100){
       	   no_of_time_execution = 0;
       	   if(velocity<WIDTH/50)
       	      velocity++;   
       	   if(NO_OF_TIMES > 20){
       	      NO_OF_TIMES -=2;
       	      NO_OF_TIMES_MOVED = 0;
       	      }	
       }
       	
	}
	}
	
/*-------------------------------------------------------------*/
	void onDie() throws InterruptedException{
	if(life_left >1){
		surfaceColorBallThread.sleep(500);
		life_left--;
		bubble.radius = WIDTH/6;
		bubble.x_cordinate = WIDTH/2;
		bubble.y_cordinate = HEIGHT/2;
	}
	else{
		surfaceColorBallThread.setRunning(false);
	    mContext = getContext();		
	    Intent intent = new Intent(mContext, FinishGameBoard.class);
		intent.putExtra("ur_score",score);		    
		mContext.startActivity(intent);
		((Activity)mContext).finish();
		//startActivity(intent);
	}
}
	
}
