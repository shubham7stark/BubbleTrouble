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
	
   
	private SurfaceHolder surfaceHolder;
	GIFViewThread GIFViewThread = null;
	
	public GIFView(Context context, int width, int height){
		super(context);
		//mContext = context;
		WIDTH = width;
		HEIGHT =height;
		NO_OF_TIMES = 0;
	
		
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
	
     }
	
	
	
/*----------------Update and Draw-------------------------*/
@Override
public void draw(Canvas canvas){
		super.draw(canvas);
	
		BitmapDrawable logo_image = (BitmapDrawable) getResources().getDrawable(R.drawable.currentlogo);    
		Bitmap logo = logo_image.getBitmap();
		HEIGHT_logo = (HEIGHT/WIDTH)*WIDTH_logo;
		Bitmap logo_scaled = Bitmap.createScaledBitmap(logo,WIDTH_logo,HEIGHT_logo,true);
		Paint paint_logo = new Paint();    
		paint_logo.setAlpha(255);                             //you can set your transparent value here    
		canvas.drawBitmap(logo_scaled,WIDTH/2 - WIDTH_logo/2, HEIGHT/3 - HEIGHT_logo/2 , paint_logo);
		
/*		  
	//Typeface typeface = Typeface.createFromAsset(getContext().getAssets(),"fonts/TITILLIUMWEB-LIGHT.ttf");
	
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
	}

}
