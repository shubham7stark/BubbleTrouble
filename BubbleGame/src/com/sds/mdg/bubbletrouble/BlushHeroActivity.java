package com.sds.mdg.bubbletrouble;

/*
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
import android.graphics.Color;
import android.graphics.Point;
import android.graphics.Typeface;
import android.media.MediaPlayer;
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
import android.widget.TextView;


@SuppressLint({ "NewApi", "ClickableViewAccessibility"})

public class BlushHeroActivity extends Activity implements OnClickListener{

	 
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
		
		display = getWindowManager().getDefaultDisplay();
		display.getSize(size);
		Log.i("qw",Integer.toString(size.x));
	    Log.i("qw",Integer.toString(size.y));

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
	               finish();
	           }
	       })
	   .show();
	}
	
	
	@Override
	protected void onPause(){
		super.onPause();
	if (mediaPlayer != null && mediaPlayer.isPlaying()) {
        mediaPlayer.stop();
        mediaPlayer.release();
    	mediaPlayer = null;
    }
  }
	
	@Override
	protected void onResume(){
		super.onResume();
	   if(mediaPlayer == null)		  
		   mediaPlayer = MediaPlayer.create(this,R.raw.gif_bk);
	   if (!mediaPlayer.isPlaying() && is_vol_on) {
	        mediaPlayer.start();
	    }
	}

	@Override
	protected void onStop(){
		super.onStop();
		//finish();
	}
	
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu){
		return false;
	}

	
}
