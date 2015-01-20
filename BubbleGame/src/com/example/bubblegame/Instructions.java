package com.example.bubblegame;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.TextView;

public class Instructions extends Activity{
	TextView heading_txt;
	TextView inst_txt;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
	   
		requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN);
		
		setContentView(R.layout.instructions);
	    
        heading_txt = (TextView)findViewById(R.id.instruction_heading);
	    inst_txt = (TextView)findViewById(R.id.instructions_text);
	
	    heading_txt.setText("INSTRUCTIONS");
	    heading_txt.setTextSize(30);
	    heading_txt.setTextColor(Color.parseColor("#39aaab"));
	    
	}

	@Override
	public void onBackPressed() {
		super.onBackPressed();
	   finish();
	}

 }
