package com.example.bubblegame;
import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.widget.ImageView;

public class AboutUs extends Activity implements OnClickListener{

	
	@Override
	protected void onCreate(Bundle savedInstanceState){
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
        WindowManager.LayoutParams.FLAG_FULLSCREEN);
		Log.i("","1");
		setContentView(R.layout.about_us);
		
		ImageView fb = (ImageView)findViewById(R.id.fb);
		ImageView git = (ImageView)findViewById(R.id.git);
		ImageView play = (ImageView)findViewById(R.id.play);
		fb.setOnClickListener(this);
		git.setOnClickListener(this);
		play.setOnClickListener(this);
	}
	

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		
		switch(v.getId()){
		case R.id.fb :
			Intent fbIntent =
	          new Intent("android.intent.action.VIEW",
	            Uri.parse("https://www.facebook.com/mdgiitr"));
	          startActivity(fbIntent);
			break;
		case R.id.git :
			Intent gitIntent =
	          new Intent("android.intent.action.VIEW",
	            Uri.parse("https://github.com/sdsmdg"));
	          startActivity(gitIntent);			
			break;
		case R.id.play :
			Intent playIntent =
	          new Intent("android.intent.action.VIEW",
	            Uri.parse("https://play.google.com/store/apps/developer?id=SDSLabs"));
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
		Intent intent = new Intent(this,MainActivity.class);
		startActivity(intent);
		Log.i("i", "qi");
		finish();
	}
	

	
}
