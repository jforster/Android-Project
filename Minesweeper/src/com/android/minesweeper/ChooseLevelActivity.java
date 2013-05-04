package com.android.minesweeper;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.Window;
import com.android.minesweeper.R;

/**
 * 
 * @author Ryan Iverson and Jeffrey Forster
 *
 */
public class ChooseLevelActivity extends Activity {

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
	    setContentView(R.layout.choose_level);
	}
	
	//Easy Level
	public void easy(View v){
		startActivity(new Intent(this, EasyActivity.class));
	}
	
	//Medium Level
	public void med(View v){
		startActivity(new Intent(this, MedActivity.class));
	}
	
	//Hard Level
	public void hard(View v){
		startActivity(new Intent(this, HardActivity.class));
	}

	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		startActivity(new Intent(this, MainActivity.class));
		return true;
	}
}
