package com.sds.mdg.bubbletrouble;

import android.annotation.SuppressLint;
import android.app.ActionBar.LayoutParams;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Point;
import android.os.Bundle;
import android.util.Log;
import android.view.Display;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.TranslateAnimation;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;

@SuppressLint("NewApi")
public class GameActivity extends Activity implements OnClickListener{

	Display display;
	Point size = new Point();
	SurfacePanel surfacePanel;
	
    
    private static ImageButton play,replay;
    
    RelativeLayout layout;
	RelativeLayout.LayoutParams play_lp,replay_lp, layout_params;
	
	
	int WIDTH, HEIGHT;
	int on_back_pressed = 0;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState){
       Log.i("","hi 2");
		super.onCreate(savedInstanceState);
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
		
		display = getWindowManager().getDefaultDisplay();
		display.getSize(size);
	    
		requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN);
        
       
        surfacePanel = new SurfacePanel(this,size.x,size.y);
		
        WIDTH = size.x;
  		HEIGHT = size.y;
      
    		
	      //add image buttons
	       layout = new RelativeLayout(this);
	       
	      //Image Button implemented 
	       play = new ImageButton(this);
	       play.setImageResource(R.drawable.play_logo_round);
	       play.setId(11);
	       play.setBackground(null);
	       
	       
	       replay = new ImageButton(this);
	       replay.setImageResource(R.drawable.replay_logo);
	       replay.setId(12);
	       replay.setBackground(null);
	       
	       
	       
           
           //layout params
	       play_lp = new RelativeLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
	       play_lp.addRule(RelativeLayout.CENTER_VERTICAL,1);
	       play_lp.addRule(RelativeLayout.CENTER_HORIZONTAL,1);
	       play_lp.setMargins(0, 0, 0, 200);
	       Log.i("","this is wow!!");
	   
	       replay_lp = new RelativeLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
		   replay_lp.addRule(RelativeLayout.CENTER_HORIZONTAL,1);
	       replay_lp.addRule(RelativeLayout.BELOW,play.getId());
	       replay_lp.setMargins(10, 5, 10, 5);
	     
	       Log.i("","this is wow!!");
		   
	       layout_params = new RelativeLayout.LayoutParams(WIDTH,HEIGHT);
	       layout.setLayoutParams(layout_params);
	       
	       
	       play.setOnClickListener(this);
	       replay.setOnClickListener(this);
	       Log.i("","asdfdg");  
	       
	       layout.addView(surfacePanel, size.x, size.y);
	       setContentView(layout);
	}

	
	@Override
	public void onClick(View v) {
	
		if(surfacePanel.is_game_paused){
		
		switch (v.getId()){
		case 11:
	    moveOutScreen();
	    surfacePanel.is_game_paused = false;
	    on_back_pressed = 0;
	    break;
	
		case 12:
		surfacePanel.life_left = 5;
		surfacePanel.score = 0;
		moveOutScreen();
		on_back_pressed = 0;
	    surfacePanel.is_game_paused = false;
        break;
        }
		
       }

	}	
	
	@Override
	public void onBackPressed(){
        		on_back_pressed++;
        		
	if(on_back_pressed == 1){
     	surfacePanel.is_game_paused = true;
		moveInScreen();
	}else if (on_back_pressed == 2){
		Intent intent = new Intent(this,MainActivity.class);
		startActivity(intent);
		finish();
	 }
	}
	
	void moveInScreen(){
		moveViewToScreenCenter(play,0,2*HEIGHT/3,500);
        moveViewToScreenCenter(replay,0,HEIGHT/3,1500);
       
        layout.addView(replay,replay_lp);
        layout.addView(play,play_lp);
     }
	

/*	void settingBackgroundImage(){
		 Bitmap bitmap;
	        
		    surfacePanel.setDrawingCacheEnabled(true); 
	        bitmap = Bitmap.createBitmap(surfacePanel.getDrawingCache());
	        surfacePanel.setDrawingCacheEnabled(false);
	        Log.i("","step1");
	        
	        Bitmap scaled_bitmap = Bitmap.createScaledBitmap(bitmap, (int)WIDTH/3,(int)HEIGHT/3 ,false);
	        bitmap = Bitmap.createScaledBitmap(scaled_bitmap, (int)WIDTH,(int)HEIGHT,false);
	        
	        Log.i("","step1");
		    
	        //Drawable d = new BitmapDrawable(getResources(),bitmap);
	        background_blur_image.setImageBitmap(bitmap);
	        layout.addView(background_blur_image, play_lp);

	}

*/	
	
   public void moveOutScreen(){
	 //   layout.removeView(instructions);
        layout.removeView(replay);
        layout.removeView(play);
        //   layout.removeView(home);
  }

	private void moveViewToScreenCenter(View view, int x, int y, int duration)
	{
	    TranslateAnimation anim = new TranslateAnimation( x,0,y,0);
	    anim.setDuration(duration);
	    anim.setFillAfter(true);
	    view.startAnimation(anim);
	}
	
		
	@Override
	protected void onResume(){
	   super.onResume();
	    surfacePanel.is_game_paused = false;
	}
	
	
	@Override
	protected void onPause(){
		super.onPause();
	surfacePanel.surfaceColorBallThread.setRunning(false);
	}
	

	@Override
	public boolean onCreateOptionsMenu(Menu menu){
		return false;
	}

	@Override
	protected void onStop(){
		super.onStop();
	   surfacePanel.surfaceColorBallThread.setRunning(false);
	   finish();  
	}
	
	
}


