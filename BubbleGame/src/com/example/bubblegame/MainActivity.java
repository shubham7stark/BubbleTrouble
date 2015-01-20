package com.example.bubblegame;

/*
 * initially MainActivity + GIFView + GIFViewThread+ GIFViewElement
 * then if Starts play: GameActivity + SurfacePanel + BubleThread + Bubble + ColorBall are in use
 * FinishGameBoardActivity + FinishGameBoardThread  + finish_board.xml are in use.
 * */

import android.annotation.SuppressLint;
import android.app.ActionBar.LayoutParams;
import android.app.AlertDialog;
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
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.TranslateAnimation;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;


@SuppressLint({ "NewApi", "ClickableViewAccessibility" })
public class MainActivity extends ActionBarActivity implements OnTouchListener{

	Display display;
	Point size = new Point();
	GIFView gifView;
	
	//image view for button n utilities
	RelativeLayout layout;
	
	private static ImageView instructions,high_score,play,about_us,volume_on_off; 
	
	RelativeLayout.LayoutParams play_lp,instructions_lp, high_score_lp, about_us_lp,volume_lp;
	int WIDTH, HEIGHT;
	
	//ref for getting volume status
	SharedPreferences preferences;
	boolean is_vol_on;
	
	//music references
	 MediaPlayer mediaPlayer;
	   
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
		
		display = getWindowManager().getDefaultDisplay();
		display.getSize(size);
		Log.i("qw",Integer.toString(size.x));
	    Log.i("qw",Integer.toString(size.y));

		requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
        
        WindowManager.LayoutParams.FLAG_FULLSCREEN);
		Log.i("","1");

        gifView = new GIFView(this,size.x,size.y);
		
		Log.i("","1");

        WIDTH = size.x;
		HEIGHT = size.y;
        
       //setContentView(gifView);
	
		 ///shared preferences for checking volume
        preferences = this.getSharedPreferences("BUBBLE_GAME",
                Context.MODE_PRIVATE);
                      is_vol_on = preferences.getBoolean("is_vol_on",true);

		
		
      //add image buttons
       layout = new RelativeLayout(this);
       
      //Image Button implemented 
       play = new ImageView(this);
       play.setImageResource(R.drawable.play_button);
       play.setId(11);
       
       
       high_score = new ImageView(this);
       high_score.setImageResource(R.drawable.high_score_logo_large);
       high_score.setId(2);
       
       instructions = new ImageView(this);
       instructions.setImageResource(R.drawable.instruction_logo_large);
       instructions.setId(1);
     
       Log.i("","this is wow!!");
     
       about_us = new ImageView(this);
       about_us.setImageResource(R.drawable.about_us_large);
       about_us.setId(3);

       volume_on_off = new ImageButton(this);
       if(is_vol_on){
    	volume_on_off.setImageResource(R.drawable.volume_on_symbol);
       }else{
    	volume_on_off.setImageResource(R.drawable.volume_off_symbol);   
       }
       volume_on_off.setId(21);
       volume_on_off.setBackground(null);
      
       //layout params created
       volume_lp = new RelativeLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
       volume_lp.addRule(RelativeLayout.ALIGN_PARENT_RIGHT,1);
       volume_lp.addRule(RelativeLayout.ALIGN_PARENT_TOP,1);
       volume_lp.setMargins(0, 10, 10, 0);
       Log.i("","this is wow!!");
   
       
       //layout params created
       play_lp = new RelativeLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
       play_lp.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
       play_lp.addRule(RelativeLayout.CENTER_HORIZONTAL,1);
       play_lp.setMargins(0, 50, 0, 100);
       Log.i("","this is wow!!");
    
       high_score_lp = new RelativeLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
       high_score_lp.addRule(RelativeLayout.CENTER_HORIZONTAL,1);
       high_score_lp.addRule(RelativeLayout.ABOVE,play.getId());
       high_score_lp.setMargins(10, 5, 10, 5);
       
       instructions_lp = new RelativeLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
       instructions_lp.addRule(RelativeLayout.ABOVE,play.getId());
       instructions_lp.addRule(RelativeLayout.LEFT_OF,high_score.getId());
       instructions_lp.setMargins(5, 5, 5, 5);
       
       about_us_lp =new RelativeLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
       about_us_lp.addRule(RelativeLayout.RIGHT_OF,high_score.getId());	       
       about_us_lp.addRule(RelativeLayout.ABOVE,play.getId());
       about_us_lp.setMargins(5, 5, 5, 5);
       
       moveInScreen();
       
        play.setOnTouchListener(this);
        high_score.setOnTouchListener(this);
        instructions.setOnTouchListener(this);
        about_us.setOnTouchListener(this);
        
       
        layout.addView(gifView, size.x, size.y);
        layout.addView(instructions,instructions_lp);
        layout.addView(high_score,high_score_lp);
        layout.addView(play,play_lp);
        layout.addView(about_us,about_us_lp);
        layout.addView(volume_on_off,volume_lp);
        setContentView(layout);
	
	 }
	
	void moveInScreen(){
		moveViewToScreenCenter(play,0,HEIGHT/4,1200);
        moveViewToScreenCenter(about_us,WIDTH/2,0,1000);
        moveViewToScreenCenter(high_score,0,HEIGHT/3,1000);
        moveViewToScreenCenter(instructions,-1*WIDTH/2,0,1000);
    }
	
	
	private void moveViewToScreenCenter(View view, int x, int y, int duration)
	{
	    TranslateAnimation anim = new TranslateAnimation( x,0,y,0);
	    anim.setDuration(duration);
	    anim.setFillAfter(true);
	    view.startAnimation(anim);
	}
	
	
	@Override
	public boolean onTouch(View v, MotionEvent arg1){
     	switch (v.getId()) {
		case 1:
		onInstructionsClicked();
			break;
		case 2:
		onHighScoreClicked();
			break;
        case 3:
		onAboutUsClicked();
        	break;
        case 11:
          onPlayButtonClicked();
			break;
		case 21:
			onVolumeButtonClicked();
			break;
		}
		
        return false;
	}


	private void onVolumeButtonClicked() {
		Log.i("","i m called he ha ha");
		
		SharedPreferences.Editor prefeditor = preferences.edit();
		 if(is_vol_on){
	     prefeditor.putBoolean("first_high_score", false);
	     is_vol_on = false;
		 volume_on_off.setImageResource(R.drawable.volume_off_symbol);
		 mediaPlayer.stop();
		 }else{
		 prefeditor.putBoolean("first_high_score", true);
		 
		 volume_on_off.setImageResource(R.drawable.volume_on_symbol);
		 is_vol_on = true;
		 mediaPlayer.start();
		 }
		 prefeditor.commit();  

	}

	public void onPlayButtonClicked(){
		gifView.GIFViewThread.setRunning(false);
		Intent intent = new Intent(this, GameActivity.class);
		startActivity(intent);
	}

	public void onAboutUsClicked(){
		
	}
	
	public void onInstructionsClicked(){
			Intent intent = new Intent(this, Instructions.class);
			startActivity(intent);
	}

	public void onHighScoreClicked(){
			Intent intent = new Intent(this, HighScore.class);
			startActivity(intent);
	}
	
	
	
	@Override
	public void onBackPressed() {
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder
		.setMessage("Do you really want to exit?")
		.setPositiveButton("NO", new DialogInterface.OnClickListener() {
	           public void onClick(DialogInterface dialog, int id) {
               }
	       })
	   .setNegativeButton("YES", new DialogInterface.OnClickListener() {
	           public void onClick(DialogInterface dialog, int id) {
	               gifView.GIFViewThread.setRunning(false);
                   finish();
	           }
	       })
	   .show();
		Log.i("i", "qi");
	}
	
	
	@Override
	protected void onPause(){
		super.onPause();
	gifView.GIFViewThread.setRunning(false);
	if (mediaPlayer != null && mediaPlayer.isPlaying()) {
        mediaPlayer.stop();
        mediaPlayer.release();
        mediaPlayer = null;
    }
  }
	
	@Override
	protected void onResume(){
		super.onResume();
	   gifView.GIFViewThread.setRunning(true);
	   if(mediaPlayer == null)		  
		   mediaPlayer = MediaPlayer.create(this,R.raw.gif_bk);
	   if (!mediaPlayer.isPlaying()) {
	        mediaPlayer.start();
	        mediaPlayer.setLooping(true);
	    }
	}

	@Override
	protected void onDestroy(){
		super.onDestroy();
	   finish();
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
		    finish();
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
	

	
}

