package com.example.bubblegame;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;

public class HighScore extends Activity{

	TextView high_score_heading, high_score_text;
	   int first_high_score, sec_high_score, third_high_score;
	   
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN);
        
        setContentView(R.layout.highscore);
   	 
    	
	high_score_heading = (TextView)findViewById(R.id.high_score_heading);
	high_score_text = (TextView)findViewById(R.id.high_score_text);
	
     high_score_heading.setText("HIGH SCORE");
     high_score_heading.setTextSize(30);

 	Typeface typeface = Typeface.createFromAsset(this.getAssets(),"fonts/Snickles.ttf");
 	high_score_heading.setTypeface(typeface);
 	high_score_heading.setTextColor(Color.parseColor("#39aaab"));
    
 	SharedPreferences preferences = this.getSharedPreferences("BUBBLE_GAME",
            Context.MODE_PRIVATE);
     first_high_score = preferences.getInt("first_high_score", 0);
     sec_high_score = preferences.getInt("sec_high_score",0);
     third_high_score = preferences.getInt("third_high_score", 0);

 	high_score_text.setText("1)"+Integer.toString(first_high_score)+"\n"+
		                   "2)"+Integer.toString(sec_high_score)+"\n"
		                  +"3)"+Integer.toString(third_high_score)+"\n");
 	
	}
	
@Override
public void onBackPressed() {
	super.onBackPressed();
    finish();
}

}
