package com.android.minesweeper;

import java.util.List;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.Window;
import android.widget.Chronometer;
import android.widget.EditText;
import com.android.minesweeper.R;

/**
 * Inherits methods from EasyActivity.  Handles larger
 * board layout for minesweeper game.
 * 
 * @author Ryan Iverson and Jeffrey Forster
 *
 */
public class HardActivity extends EasyActivity {
	
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		super.onCreate(savedInstanceState);
	    setContentView(R.layout.hard);
	    
	    //Setup Board
	    board = new Board(8, 20);
	    
	    //Mine display setup
	    EditText mineCountDisplay = (EditText)findViewById(R.id.minesLeftDisplay);
	    mineCountDisplay.setText(String.valueOf(board.numberOfMines));
	}
	
	/**
	 * Performs end of game actions after game is won or lost
	 *
	 *
	 * @param winOrLoss tells method if win or loss case
	 * @overide allows for correct game to be started on Play Again
	 */
	public void gameOver(String winOrLoss){
		
		AlertDialog.Builder alert = new AlertDialog.Builder(HardActivity.this);//Create end of game alert
		
		//get time info
		Chronometer timer = (Chronometer)findViewById(R.id.chronometer1);
		String time = (String) timer.getText();
		timer.stop();
		
		//Add game info to database
		database.addScore("hard", correctMinesGotten, time);
		
		//Loser!
		if(winOrLoss == "lost"){
			//set title
			alert.setTitle("Game Over!");
			//set dialog message
			alert.setMessage("\n" + "Top Score\n-------\n" + getHighscores())
				 .setCancelable(true)//disable canceling feature
				 
				 //Play again button
				 .setPositiveButton("Play Again", new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int id) {
//						EasyActivity.this.finish();
						finish();
						startActivity(getIntent());
					}
				})
				
				//New level button
				.setNegativeButton("Choose New Level", new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						HardActivity.this.finish();
					}
				});
			
		//Winner!
		}else{
			//set title
			alert.setTitle("Winner!");
			//set dialog message
			alert.setMessage("Nice job!" + 
							 "\n" + "Top Scores\n" + getHighscores())
				 .setCancelable(false)//disable cancel feature
				 
				 //Play again button
				 .setPositiveButton("Play Again", new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int id) {
						finish();
						startActivity(getIntent());
					}
				})
				
				//Choose level button
				.setNegativeButton("Choose New Level", new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						HardActivity.this.finish();
					}
				});
		}
		
		//Display alert
		AlertDialog alertDialog = alert.create();
		alertDialog.show();
		
	}
	
	/**
	 * Returns string of high scores
	 * Currently used in end of game display
	 *
	 * @return String of high scores
	 */
	public String getHighscores(){
		String returnString = "";
		List<Score> allScores = database.getAllScores("hard");
		
		//loop through top 4 entries of table as obtained in the allScores list
		for(int i = allScores.size()-1, j = 3; i>=0 && j>=0; j--, i--){
			returnString += String.valueOf(allScores.get(i).score + " | " + allScores.get(i).time + "\n");
		}
		return returnString;
	}

	//Menu stuff
	public void onOptionPlayAgain(MenuItem menu){
		finish();
		startActivity(new Intent(this, HardActivity.class));
	}
	
}
