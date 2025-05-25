package com.sds.mdg.bubbletrouble;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

public class AboutUs extends Activity implements OnClickListener{

	ImageView fb, git,play;
	TextView inst_txt;
	@Override
	protected void onCreate(Bundle savedInstanceState){
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
        WindowManager.LayoutParams.FLAG_FULLSCREEN);
		Log.i("","1");
		setContentView(R.layout.about_us);
		
		fb = (ImageView)findViewById(R.id.fb);
		git = (ImageView)findViewById(R.id.git);
		play = (ImageView)findViewById(R.id.play);
		fb.setOnClickListener(this);
		git.setOnClickListener(this);
		play.setOnClickListener(this);
		
		inst_txt = (TextView)findViewById(R.id.about_us_detail);
		 Typeface custom_font = Typeface.createFromAsset(getAssets(), "fonts/RobotoCondensed-Light.ttf");
		    inst_txt.setTypeface(custom_font);
	       
	}
	

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		
		switch(v.getId()){
		case R.id.fb :
			Intent fbIntent =
	          new Intent("android.intent.action.VIEW",
	            Uri.parse("http://goo.gl/6Cznj6"));
	          startActivity(fbIntent);
			break;
		case R.id.git :
			Intent gitIntent =
	          new Intent("android.intent.action.VIEW",
	            Uri.parse("http://goo.gl/smpcVZ"));
	          startActivity(gitIntent);			
			break;
		case R.id.play :
			Intent playIntent =
	          new Intent("android.intent.action.VIEW",
	            Uri.parse("http://goo.gl/de2xPm"));
	          startActivity(playIntent);	
			break;
		}
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu){
		return false;
	}

	@Override
	public void onBackPressed() {
		/*
		Intent intent = new Intent(this,MainActivity.class);
		startActivity(intent);
		Log.i("i", "qi");
		*/
		finish();
	}
	

	
}
