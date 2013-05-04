package com.android.minesweeper;

import java.util.List;

import android.os.Bundle;
import android.view.Window;
import android.widget.EditText;

/**
 * This class displays the high scores from all three levels
 * 
 * @author Ryan Iverson and Jeffrey Forster
 *
 */
public class Highscores extends EasyActivity{
	
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		super.onCreate(savedInstanceState);
	    setContentView(R.layout.highscores);
	    
	    // beginner scores
	    EditText beginner = (EditText)findViewById(R.id.beginnerScores);
	    beginner.setText(getHighscores("easy"));
	    EditText beginnerSetup = (EditText)findViewById(R.id.beginnerSetup);
	    beginnerSetup.setText("# Mines      Time");
	    
	    // intermediate scores
	    EditText intermediate = (EditText)findViewById(R.id.intermediateScores);
	    intermediate.setText(getHighscores("med"));
	    EditText intermediateSetup = (EditText)findViewById(R.id.intermediateSetup);
	    intermediateSetup.setText("# Mines      Time");
	    
	    // expert scores
	    EditText expert = (EditText)findViewById(R.id.expertScores);
	    expert.setText(getHighscores("hard"));
	    EditText expertSetup = (EditText)findViewById(R.id.expertSetup);
	    expertSetup.setText("# Mines      Time");

	}
	
	/**
	 * Returns string of high scores
	 *
	 * @param string the database to get the scores from
	 * @return String of high scores
	 */
	public String getHighscores(String databaseToUse){
		String returnString = "";
		List<Score> allScores = database.getAllScores(databaseToUse);
		
		//loop through top 4 entries of table as obtained in the allScores list
		for(int i = allScores.size()-1, j = 3; i>=0 && j>=0; j--, i--){
			returnString += String.valueOf(allScores.get(i).score + "                " + allScores.get(i).time + "\n");
		}
		return returnString;
	}

}
