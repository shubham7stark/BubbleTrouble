package com.sds.mdg.bubbletrouble;

import android.app.Activity;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.Menu;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

public class InstructionsDetailed extends Activity{
	TextView inst_txt;
	String rules = "How to play " +"\n"+
			 "-Tap screen to move the bubble with your finger."+"\n"+
	         "-Try to capture color balls (except Red) in bubble."+"\n"+
             "-Capturing Red ball will cost a life."+"\n"+
             "-On touch bubble radius increases, reduces otherwise."+"\n"+
             "-Score increases on the basis of color and size of captured balls.";

	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	   
		requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN);
		
        
		setContentView(R.layout.instructions);
	    inst_txt = (TextView)findViewById(R.id.instructions_text);
	    inst_txt.setText(rules);
	    inst_txt.setTextSize(30);
	    inst_txt.setTextColor(Color.parseColor("#43d2fc"));
	    
	    Typeface custom_font = Typeface.createFromAsset(getAssets(), "fonts/RobotoCondensed-Light.ttf");
	    inst_txt.setTypeface(custom_font);
        	
	
	}

	
	@Override
	public void onBackPressed(){
		super.onBackPressed();
	}


	@Override
	public boolean onCreateOptionsMenu(Menu menu){
		return false;
	}

	@Override
	protected void onStop(){
		super.onStop();
	  
	}

 }
