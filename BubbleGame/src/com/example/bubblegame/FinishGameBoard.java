package com.example.bubblegame;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.graphics.Point;
import android.graphics.Typeface;
import android.os.Bundle;
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
	private ImageButton high_score;
	private ImageButton home;
	
    private Intent intent;
    
    int your_score;
	
    int first_high_score, sec_high_score, third_high_score;
    
    
    
    
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
	    high_score = (ImageButton)findViewById(R.id.button_high_score);
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
	 high_score.setOnClickListener(this);
	 home.setOnClickListener(this);
	 
	 //score editing
	 Intent intent = getIntent();
	 your_score = intent.getIntExtra("ur_score", 0);
	 myscore.setText(Integer.toString(your_score));
	 
	 
	 
	 SharedPreferences preferences = mContext.getSharedPreferences("BUBBLE_GAME",
			                                                          Context.MODE_PRIVATE);
	 first_high_score = preferences.getInt("first_high_score", 0);
	 sec_high_score = preferences.getInt("sec_high_score",0);
     third_high_score = preferences.getInt("third_high_score", 0);
     
     if(your_score > sec_high_score){
    	 if(your_score > first_high_score){
             third_high_score = sec_high_score;
             sec_high_score = first_high_score;
             first_high_score = your_score;
    	  
       }else{
    		 third_high_score = sec_high_score;
    		 sec_high_score = your_score;
    	 }
     }
     else{
    	 if(your_score > third_high_score){
    		 third_high_score = your_score;
    	 }
     }
     
     bestscore.setText("BEST "+Integer.toString(first_high_score));
    
     
	 SharedPreferences.Editor prefeditor = preferences.edit();
     prefeditor.putInt("first_high_score", first_high_score); 
     prefeditor.putInt("sec_high_score", sec_high_score);
     prefeditor.putInt("third_high_score", third_high_score);
     prefeditor.commit();  
	

	}


	@Override
	public void onClick(View v) {
		
		switch(v.getId()){
		
		case R.id.button_replay:
			intent = new Intent(this, GameActivity.class);
			startActivity(intent);
			finish();
			break;
		case R.id.button_instructions:
			onInstructionsClicked();
			break;
		case R.id.button_high_score:
			onHighScoreClicked();
           break;
		case R.id.button_home:
			intent = new Intent(this, MainActivity.class);
			startActivity(intent);
			finish();
			break;
		}
	}

	public void onInstructionsClicked(){
	/*	final Dialog dialog = new Dialog(this);
					dialog.setContentView(R.layout.instructions);
					Log.i("1","1");
					TextView instruction_heading = (TextView)dialog.findViewById(R.id.instruction_heading);
					instruction_heading.setText("INSTRUCTIONS");
					
					ImageButton close = (ImageButton) dialog.findViewById(R.id.close_symbol);
					TextView instruction_text = (TextView)dialog.findViewById(R.id.instructions_text);
					Log.i("1","1");
					instruction_text.setText("he ha ha");
					close.setImageResource(R.drawable.ic_launcher);
					Log.i("1","1");
					
					close.setOnClickListener(new OnClickListener() {
						@Override
						public void onClick(View v) {
							Log.i("1","1");
							
							dialog.dismiss();
						}
					});
					Log.i("1","1");
					
		 			dialog.show();	
		*/
		intent = new Intent(this, Instructions.class);
		startActivity(intent);
    }

	
	public void onHighScoreClicked(){
		intent = new Intent(this, HighScore.class);
		startActivity(intent);
    	/*
		Log.i("i", "qi");
		AlertDialog.Builder builder = new AlertDialog.Builder(FinishGameBoard.this);
		builder
		//.setTitle("HIGH SCORE")
		.setMessage("1)"+Integer.toString(first_high_score)+"\n"+
		                   "2)"+Integer.toString(sec_high_score)+"\n"
		                  +"3)"+Integer.toString(third_high_score)+"\n")
		//.setIcon(R.drawable.high_score_logo_small)
			
		.setPositiveButton("OK", new DialogInterface.OnClickListener() {
	           public void onClick(DialogInterface dialog, int id) {
	               // User clicked OK button
	           }
	       })
	   .setNegativeButton("Cancel", new DialogInterface.OnClickListener(){
	           public void onClick(DialogInterface dialog, int id) {
	               // User cancelled the dialog
	           }
	       })
	   .show();
		Log.i("i", "qi");
		*/
	}
	
	@Override
	public void onBackPressed() {
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
		    finish();
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
	
}
