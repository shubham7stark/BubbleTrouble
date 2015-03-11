package com.example.bubblegame;

/*
 * initially MainActivity + GIFView + GIFViewThread+ GIFViewElement
 * then if Starts play: GameActivity + SurfacePanel + BubleThread + Bubble + ColorBall are in use
 * FinishGameBoardActivity + FinishGameBoardThread  + finish_board.xml are in use.
 * */

import android.annotation.SuppressLint;
import android.app.ActionBar.LayoutParams;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.graphics.Point;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.Log;
import android.view.Display;
import android.view.Menu;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.TranslateAnimation;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;


@SuppressLint({ "NewApi", "ClickableViewAccessibility"})

public class MainActivity extends Activity implements OnClickListener{

	Display display;
	Point size = new Point();
	GIFView gifView;
	
	//image view for button n utilities
	RelativeLayout layout;
	
	private static ImageButton play,about_us,volume_on_off; 
    
	RelativeLayout.LayoutParams play_lp, about_us_lp,volume_lp;
	int WIDTH, HEIGHT;
	
	//ref for getting volume status
	SharedPreferences preferences;
	boolean is_vol_on = false;
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
        
    
		///shared preferences for checking volume
        preferences = this.getSharedPreferences("BUBBLE_GAME",
                Context.MODE_PRIVATE);
                      is_vol_on = preferences.getBoolean("is_vol_on",true);

	  //add image buttons
       layout = new RelativeLayout(this);
       
      //Image Button implemented 
       play = new ImageButton(this);
       play.setImageResource(R.drawable.play_button);
       play.setId(11);
       play.setBackground(null);
       
         
       about_us = new ImageButton(this);
       about_us.setImageResource(R.drawable.about_us_button);
       about_us.setId(3);
       about_us.setBackground(null);
       

       volume_on_off = new ImageButton(this);
       if(is_vol_on){
       volume_on_off.setImageResource(R.drawable.volume_on_symbol);
        Log.i("","vol is on");
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
       play_lp.addRule(RelativeLayout.ABOVE,about_us.getId());
       play_lp.addRule(RelativeLayout.CENTER_HORIZONTAL,1);
       play_lp.setMargins(0, 50,0,10);
       Log.i("","this is wow!!");
    
       
       about_us_lp =new RelativeLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
       about_us_lp.addRule(RelativeLayout.CENTER_HORIZONTAL,1);	       
       about_us_lp.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
       about_us_lp.setMargins(5, 5, 5, 20);
       
     	moveViewToScreenCenter(play,0,HEIGHT/4,1000);
        moveViewToScreenCenter(about_us,0,HEIGHT/3,700);
   
        
        play.setOnClickListener(this);
        about_us.setOnClickListener(this);
        volume_on_off.setOnClickListener(this);
       
        layout.addView(gifView, size.x, size.y);
        layout.addView(play,play_lp);
        layout.addView(about_us,about_us_lp);
        layout.addView(volume_on_off,volume_lp);
        setContentView(layout);
	 
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
		switch (v.getId()){
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
	}

	public void onPlayButtonClicked(){
		gifView.GIFViewThread.setRunning(false);
		Intent intent = new Intent(this, Instructions.class);
		startActivity(intent);
	}


	private void onAboutUsClicked(){
		gifView.GIFViewThread.setRunning(false);
		Intent intent = new Intent(this, AboutUs.class);
		startActivity(intent);
		
		
	}

	private void onVolumeButtonClicked(){
		SharedPreferences.Editor prefeditor = preferences.edit();
		 if(is_vol_on){
	 	 prefeditor.putBoolean("is_vol_on", false);
	     is_vol_on = false;
		 volume_on_off.setImageResource(R.drawable.volume_off_symbol);
		 mediaPlayer.stop();
		 }
		 else{
		 prefeditor.putBoolean("is_vol_on", true);
		 volume_on_off.setImageResource(R.drawable.volume_on_symbol);
		 is_vol_on = true;
		 mediaPlayer.start();
		 }
		 prefeditor.commit();  

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
	           public void onClick(DialogInterface dialog, int id){
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
	   if (!mediaPlayer.isPlaying() && is_vol_on) {
	        mediaPlayer.start();
	    }
	}

	@Override
	protected void onStop(){
		super.onStop();
		finish();
	}
	
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu){
		return false;
	}

	
}

