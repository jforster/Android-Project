package com.android.minesweeper;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
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
public class Settings extends Activity {

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.settings);
	    // TODO Auto-generated method stub
	}
	
	/**
	 * Clear all scores.  Deletes all rows from
	 * table in database.
	 */
	public void clearHighscores(View v){
		Database database = new Database(this);
		database.deleteAll("easy");
		database.deleteAll("med");
		database.deleteAll("hard");
		
		//Display verification alert on after completed
		AlertDialog.Builder alert = new AlertDialog.Builder(Settings.this);
		alert.setTitle("Success");
		alert.setMessage("Clear completed!")
			 .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
				
				@Override
				public void onClick(DialogInterface dialog, int which) {
					dialog.cancel();
				}
			});
		
		//Display alert
		AlertDialog alertDialog = alert.create();
		alertDialog.show();
	}
	
	//Menu stuff
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		startActivity(new Intent(this, MainActivity.class));
		return true;
	}
	
}
