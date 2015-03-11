package com.example.bubblegame;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.Menu;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

public class Instructions extends Activity{
	TextView inst_txt;
	protected boolean _active = true;
	protected int _splashTime = 1500; // time to display the splash screen in ms

	private Thread splashThread;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	   
		requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN);
		
        
		setContentView(R.layout.instructions);
	    inst_txt = (TextView)findViewById(R.id.instructions_text);
	    inst_txt.setText("'Don't catch the red balls'");
	    inst_txt.setTextSize(30);
	    
	    Typeface custom_font = Typeface.createFromAsset(getAssets(), "fonts/Snickles.ttf");
	    inst_txt.setTypeface(custom_font);
        	
	    splashThread = new Thread(){
	        @Override
	        public void run(){
	            try {
	                int waited = 0;
	                while (_active && (waited < _splashTime)) {
	                    sleep(100);
	                    if (_active){
	                        waited += 100;
	                    }
	                }
	            } catch (Exception e){

	            } finally {
	                startActivity(new Intent(Instructions.this,
	                        GameActivity.class));
	                finish();
	            }
	        };
	             };
	    splashThread.start();
	
	}

	@Override
	public void onBackPressed(){
		super.onBackPressed();
    	_active = false;
	}


	@Override
	public boolean onCreateOptionsMenu(Menu menu){
		return false;
	}

	@Override
	protected void onStop() {
		super.onStop();
	  
	}

 }
