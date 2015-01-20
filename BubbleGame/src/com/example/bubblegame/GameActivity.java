package com.example.bubblegame;

import android.annotation.SuppressLint;
import android.app.ActionBar.LayoutParams;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.graphics.Point;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Display;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.Surface;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.animation.TranslateAnimation;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

@SuppressLint("NewApi")
public class GameActivity extends ActionBarActivity implements OnClickListener{

	Display display;
	Point size = new Point();
	SurfacePanel surfacePanel;
	
    
    
	
    private static ImageButton play,instructions,replay,home; 
    RelativeLayout layout;
	RelativeLayout.LayoutParams play_lp,instructions_lp, replay_lp, home_lp;
	int WIDTH, HEIGHT;
	
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
		
       
        
	      //add image buttons
	       layout = new RelativeLayout(this);
	       
	      //Image Button implemented 
	       play = new ImageButton(this);
	       play.setImageResource(R.drawable.play_logo_round);
	       play.setId(1);
	       play.setBackground(null);
	       
	       
	       replay = new ImageButton(this);
	       replay.setImageResource(R.drawable.replay_logo);
	       replay.setId(12);
	       replay.setBackground(null);
	       
	       
	       instructions = new ImageButton(this);
	       instructions.setImageResource(R.drawable.instruction_logo_large);
	       instructions.setId(11);
	       instructions.setBackground(null);
	       
	       home = new ImageButton(this);
	       home.setImageResource(R.drawable.home_logo_small);
	       home.setId(13);
           home.setBackground(null);
           
           
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
		    
	       instructions_lp = new RelativeLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
	       instructions_lp.addRule(RelativeLayout.BELOW,play.getId());
	       instructions_lp.addRule(RelativeLayout.LEFT_OF,replay.getId());
	       instructions_lp.setMargins(5, 5, 5, 5);
	       
	       Log.i("","this is wow!!");
		    
	       home_lp =new RelativeLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
	       home_lp.addRule(RelativeLayout.RIGHT_OF,replay.getId());	       
	       home_lp.addRule(RelativeLayout.BELOW,play.getId());
	       home_lp.setMargins(5, 5, 5, 5);
	       
	       Log.i("","this is wow!!");
		       
	        play.setOnClickListener(this);
	        replay.setOnClickListener(this);
	        instructions.setOnClickListener(this);
	        home.setOnClickListener(this);
	        
	       Log.i("","asdfdg");  
	       layout.addView(surfacePanel, size.x, size.y);
	        Log.i("","asdfdg2");  
		     
	        setContentView(layout);
	}

	
	@Override
	public void onBackPressed() {
		surfacePanel.surfaceColorBallThread.setRunning(false);
		boolean retry = true;
	    while (retry) {
	        try {
	            surfacePanel.surfaceColorBallThread.join();
	            retry = false;
	        } catch (InterruptedException e) {
	            // try again shutting down the thread
	        }
	    }

		surfacePanel.is_game_paused = true;
		moveInScreen();
	
		/*
		//surfacePanel.surfaceColorBallThread.setRunning(false);
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder
		.setMessage("Do you really want to exit?")
		.setPositiveButton("NO", new DialogInterface.OnClickListener() {
	            public void onClick(DialogInterface dialog, int id) {
             
	            	surfacePanel.surfaceColorBallThread.setRunning(true);
	            }
	   })
	   .setNegativeButton("YES", new DialogInterface.OnClickListener() {
	           public void onClick(DialogInterface dialog, int id) {
	               surfacePanel.surfaceColorBallThread.setRunning(false);
                   finish();
	           }
	       })
	
	   .show();
		Log.i("i", "qi");
		*/
	}
	
	void moveInScreen(){
		moveViewToScreenCenter(play,0,2*HEIGHT/3,500);
        moveViewToScreenCenter(instructions,-1*WIDTH/2,0,1500);
        moveViewToScreenCenter(replay,0,HEIGHT/3,1500);
        moveViewToScreenCenter(home,-WIDTH/2,0,1500);

	    layout.addView(instructions,instructions_lp);
        layout.addView(replay,replay_lp);
        layout.addView(play,play_lp);
        layout.addView(home,home_lp);

	}

  public void moveOutScreen(){
	  
	    layout.removeView(instructions);
        layout.removeView(replay);
        layout.removeView(play);
        layout.removeView(home);
  }

	private void moveViewToScreenCenter(View view, int x, int y, int duration)
	{
	    TranslateAnimation anim = new TranslateAnimation( x,0,y,0);
	    anim.setDuration(duration);
	    anim.setFillAfter(true);
	    view.startAnimation(anim);
	
	}
	
	@Override
	public void onClick(View v) {
	
		Log.i("","its activity ");
		
		if(surfacePanel.is_game_paused){
		switch (v.getId()){
		case 1:
	    moveOutScreen();
	    surfacePanel.is_game_paused = false;
	    break;
	
		case 11:
	    onInstructionsClicked();
		break;
    	case 12:
		surfacePanel.life_left = 5;
		surfacePanel.score = 0;
		moveOutScreen();
        surfacePanel.is_game_paused = false;
        break;
        
		case 13:
		Intent intent = new Intent(this, MainActivity.class);
			startActivity(intent);
			finish();
    	break;
		}
       }
		
	}
	
	void onInstructionsClicked(){
		Intent intent = new Intent(this, Instructions.class);
		startActivity(intent);
	}
	
	

	@Override
	protected void onResume(){
	   super.onResume();
	    moveOutScreen();
	    surfacePanel.is_game_paused = false;
	}
	
	@Override
	protected void onPause(){
		super.onPause();
	surfacePanel.surfaceColorBallThread.setRunning(false);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings){
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
	   surfacePanel.surfaceColorBallThread.setRunning(false);
	   finish();  
	}
}
