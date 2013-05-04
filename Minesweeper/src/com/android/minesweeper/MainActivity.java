package com.android.minesweeper;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.view.Window;
import com.android.minesweeper.R;

/**
 * 
 * @author Ryan Iverson and Jeffrey Forster
 *
 */
public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_main);
	}
	
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		startActivity(new Intent(this, Settings.class));
		return true;
	}
	
	
	//Play Game
	public void playGame(View v){
		startActivity(new Intent(this, ChooseLevelActivity.class));
	}
	
	//Setting
	public void handleButtonSettings(View v){
		startActivity(new Intent(this, Settings.class));
	}
	
	public void handleButtonHighscores(View v){
		startActivity(new Intent(this, Highscores.class));
	}
	
}
