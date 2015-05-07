package com.sds.mdg.bubbletrouble;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.graphics.Point;
import android.os.Bundle;
import android.util.Log;
import android.view.Display;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.TextView;

public class FinishGameBoard extends Activity implements OnClickListener{

	Display display;
	Point point = new Point();
	
	private static Context mContext;
	
	private TextView myscore;
	private TextView bestscore;
	private ImageButton replay;
	private ImageButton instructions;
	private ImageButton about_us;
	private ImageButton home;
	private Intent intent;
    
    private int your_score;
	
    int first_high_score;
    
    
    
    
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.finish_board);
		 
		
		 mContext = getBaseContext();
			 
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
		 display = getWindowManager().getDefaultDisplay();
	     display.getSize(point);
			 
	     getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
	     
	     //WindowManager.LayoutParams.FLAG_FULLSCREEN);
	          	
	     
	    replay = (ImageButton)findViewById(R.id.button_replay);
	    instructions = (ImageButton)findViewById(R.id.button_instructions);
	    about_us = (ImageButton)findViewById(R.id.about_us_logo);
	    home = (ImageButton)findViewById(R.id.button_home);
	    
	    
	    myscore = (TextView)findViewById(R.id.txt_myscore);
	 	bestscore = (TextView)findViewById(R.id.txt_bestscore);
	 	
	 	/*
	    Typeface tf = Typeface.createFromAsset(getBaseContext().getAssets(),
                "fonts/BEBASNEUE REGULAR.ttf");
	    
	    myscore.setTypeface(tf);
	    bestscore.setTypeface(tf);
	   */
	    
	    
	 replay.setOnClickListener(this);
	 instructions.setOnClickListener(this);
	 about_us.setOnClickListener(this);
	 home.setOnClickListener(this);
	 
	 //score editing
	 Intent intent = getIntent();
	 your_score = intent.getIntExtra("ur_score", 0);
	 myscore.setText(Integer.toString(your_score));
	 
	 
	 SharedPreferences preferences = mContext.getSharedPreferences("BUBBLE_GAME",Context.MODE_PRIVATE);
	 try{
	 first_high_score = preferences.getInt("first_high_score",0);
	 }catch(Exception e){
		 Log.i("",e.toString());
	 }

	 first_high_score = Math.max(your_score, first_high_score);
		 
     bestscore.setText("BEST "+Integer.toString(first_high_score));
     
     SharedPreferences.Editor prefeditor = preferences.edit();
     prefeditor.putInt("first_high_score", first_high_score); 
     prefeditor.commit();  
	
	}


	@Override
	public void onClick(View v){
		
		switch(v.getId()){
		
		case R.id.button_replay:
			intent = new Intent(this, GameActivity.class);
			startActivity(intent);
			finish();
			break;
		case R.id.button_instructions:
			onInstructionsClicked();
			break;
		case R.id.about_us_logo:
			onAboutUsClicked();
           break;
		case R.id.button_home:
		onHomeClicked();
		break;
		}
	}

	public void onHomeClicked(){
		intent = new Intent(this, MainActivity.class);
		startActivity(intent);
        finish();
	}

	public void onInstructionsClicked(){
		intent = new Intent(this, InstructionsDetailed.class);
		startActivity(intent);
    }

	
	public void onAboutUsClicked(){
		
		intent = new Intent(this, AboutUs.class);
		startActivity(intent);
    	Log.i("i", "qi");
	}
	
	@Override
	public void onBackPressed(){
		intent = new Intent(this, MainActivity.class);
		startActivity(intent);
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
		 try{
		  finish();
		 }
		catch(Exception e){
			 Log.i("",e.toString());
		 }
		 return true;
		
		}
		
		return super.onOptionsItemSelected(item);
	}
	
}
