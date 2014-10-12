package com.example.bubblegame;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import android.content.Context;
import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.RectF;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

public class SurfacePanel extends SurfaceView{

	private Context mContext;
	
	private static int WIDTH;
	private static int HEIGHT;
	private static int WIDTH_GAP;
	
	private static int radius;
	private static int margin_x;
	
	private static final int NO_OF_TIMES = 20;
	private static int NO_OF_TIMES_MOVED = 20;
	
	private static final int MAX_BALLS = 8;
	private static final int NO_OF_BALLS = 4;
	
	private static int velocity = 8;
	private static final int TYPES_OF_BALL = 5;
    private Paint mPaint;
    private Paint mPaints[] = new Paint[7];
    
    private static int score;
    private static int life_left;
    
    private static int no_of_time_execution;
    
	private SurfaceHolder surfaceHolder;
	SurfaceColorBallThread surfaceColorBallThread = null;
	
	List<ColorBall> listColorBalls = new ArrayList<ColorBall>();
	Bubble bubble;
	
	
	public SurfacePanel(Context context, int width, int height){
		super(context);
		
		margin_x = (int)(0.05*width);
		WIDTH = (int)(0.9*width);
		WIDTH_GAP = WIDTH/(MAX_BALLS+1);
		HEIGHT =height;
	    
		radius = WIDTH/(2*MAX_BALLS);
		
		bubble = new Bubble(width/2,height/2,width/8);
       
		/*----Paint-------*/
		for(int i =0;i<7;i++){
		mPaints[i] = new Paint();
	    //mPaint.setAntiAlias(true);
		mPaints[i].setDither(true);
		mPaints[i].setColor(Color.BLUE);
		mPaints[i].setStyle(Paint.Style.FILL);
		mPaints[i].setStrokeJoin(Paint.Join.MITER);
		mPaints[i].setStrokeCap(Paint.Cap.SQUARE);
		mPaints[i].setStrokeWidth(5);
		}
		mPaints[0].setColor(Color.RED);
		mPaints[1].setColor(Color.BLUE);
		mPaints[2].setColor(Color.MAGENTA);
		mPaints[3].setColor(Color.YELLOW);
		mPaints[4].setColor(Color.rgb(128,0,128));
		mPaints[5].setColor(Color.BLACK);
       	
		for(int i =0;i<6;i++)
		mPaints[i].setAlpha(150);
		
			/*------------------*/
	    mPaint = new Paint();
	    mPaint.setStrokeWidth(8);
	    mPaint.setStyle(Style.STROKE);
	    mPaint.setColor(Color.BLUE);
	    mPaint.setAlpha(50);
	      /*----------------------*/
	    mPaints[6].setTextSize(HEIGHT/20);
	    mPaints[6].setColor(Color.WHITE);
	    
	    
	    
	    score = 0;life_left = 5;
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
	
	 }
  /*-------------------on Touch----------------------*/
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		// TODO Auto-generated method stub

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
    	Log.i("motion", "Action move called");
		break;
		}
		
		bubble.moveball((int)event.getX(), (int)event.getY());
    	bubble.increaseRadius(5);
		return true;
	}
	

	
	/*----------------Update and Draw-------------------------*/
	@Override
	public void draw(Canvas canvas){
		super.draw(canvas);
		canvas.drawColor(Color.WHITE);
	
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
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	
	//Draw ScorEBoard
	RectF rectf = new RectF((WIDTH - WIDTH/4),HEIGHT/10,WIDTH+2*margin_x+margin_x,HEIGHT/80);
	canvas.drawRect(rectf,mPaints[5]);
	canvas.drawText(Integer.toString(score)+"/"+Integer.toString(life_left),
			(WIDTH - WIDTH/5),HEIGHT/13, mPaints[6]);
	
	
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
		if(cb.color_code == 0 && life_left>0)life_left--;  
	    listColorBalls.remove(i);	
	    }
		}
		
		//motion of balls
    	for(int i  = 0 ; i < listColorBalls.size(); i++){
	      	listColorBalls.get(i).moveball();	
		}
    	
    	//Adding more balls
    	if(NO_OF_TIMES_MOVED == NO_OF_TIMES){
    		int a[] = new int[3*NO_OF_BALLS];
        	NO_OF_TIMES_MOVED = 0;
    
        	
        	Random random = new Random();
        	
        	for(int j = 0; j < NO_OF_BALLS; j++){
        	int k = random.nextInt(100)%10;
        	while(isInArray(a,k,j)){
        			k = random.nextInt(100)%MAX_BALLS;
        	}
        	a[j] = k;
        	}
        	
    		for(int j = NO_OF_BALLS; j < 3*NO_OF_BALLS; j++){
    		a[j] = random.nextInt(100)%TYPES_OF_BALL;
    		Log.i("random", Integer.toString(a[j]));
    		}
    		
    	    
    		for(int j = 0; j < NO_OF_BALLS; j++){
    		ColorBall colorBall = new ColorBall(a[j]*WIDTH_GAP+margin_x,velocity,
    				                           a[j+NO_OF_BALLS],
    				                          a[j+2*NO_OF_BALLS]
                                     				);
    	      listColorBalls.add(colorBall);
    		}
    	
    		Log.i("tag", "update balls");
    	   
     }//Adding More Ball complete
     	
         	NO_OF_TIMES_MOVED++;
         	
           	Log.i("tag", "update");
	//bubble radius auto-decrease
       	bubble.decreaseRadius(-20);
	
      
       	no_of_time_execution++;
       	if(no_of_time_execution > 200){
       	   no_of_time_execution = 0;
       	/*---fill the blanks-----*/ 
       	}
	
	}
	
/*-------------------------------------------------------------*/
	void onDie() throws InterruptedException{
	if(life_left >0){
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
	    mContext.startActivity(intent);
		//startActivity(intent);
	}
	
	}

}
