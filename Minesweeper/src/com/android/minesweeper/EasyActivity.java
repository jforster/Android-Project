package com.android.minesweeper;


import java.util.List;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.SystemClock;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.EditText;
import android.view.Menu;
import com.android.minesweeper.R;

/**
 * Handles game of mine Sweeper and allows
 * other game levels to inherit all methods except
 * for gameOver method which needs to be slightly
 * modified for the other two levels
 * 
 * @author Ryan Iverson and Jeffrey Forster
 *
 */
public class EasyActivity extends Activity{

	int correctMinesGotten = 0;
	Board board;
	boolean flag = false;//if flags are on or off
	boolean timerStarted = false;
	Database database = new Database(this);
	
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
	    setContentView(R.layout.easy);
	    
	    board = new Board(5);//initialize Board
	    
	    //Mine display setup
	    EditText mineCountDisplay = (EditText)findViewById(R.id.minesLeftDisplay);
	    mineCountDisplay.setText(String.valueOf(getNumberOfMinesLeft()));
	     
	}

	/**
	 * Set or unset flag boolean based on
	 * current radio button setting
	 */
	public void handleRadioButton(View v){
		switch(v.getId()){
		case R.id.disarm:
			flag = false;
			break;
		case R.id.flag:
			flag = true;
			break;
		}
	}
	
	/**
	 * Handle any given node button
	 */
	public void handleButtonNode(View v){		
		
		int id = v.getId();
		EditText minesLeftDisplay = (EditText)findViewById(R.id.minesLeftDisplay);
		int resID;
		Button button;
		
		//Setup timer
		if(!timerStarted) {
			timerStarted = true;
		    Chronometer timer = (Chronometer)findViewById(R.id.chronometer1);
		    timer.setBase(SystemClock.elapsedRealtime());
		    timer.start();  
		}
		
		//Loop through all buttons in order to get array position index
		for(int i = 0; i<board.length*board.length; i++){
			resID = getResources().getIdentifier(String.valueOf("button" + i), "id", "com.android.minesweeper");//get id of given array index element
			if(id == resID){//found id and index
				
				button = (Button)findViewById(resID);//set button based on id
				
				//IF FLAG CHECKBOX CHECKED
				//and node not already disarmed
				if(flag && !(board.nodes.get(i).disarmed) ) {
					//check if already flagged
					if(board.nodes.get(i).flagged) {//is flagged
						//unflag
						board.nodes.get(i).flagged = false;
						button.setText("");//clear button text
						button.setTextColor(Color.BLACK);
					} else {//not flagged
						//flag it
						board.nodes.get(i).flagged = true;
						button.setText(">");
						button.setTextColor(Color.rgb(176, 23, 21)); // red
					}
					
					minesLeftDisplay.setText(String.valueOf(getNumberOfMinesLeft()));//set number of mines edittext
					return;
				}

				//do nothing for flagged box
				if(board.nodes.get(i).flagged) {
					return;
				}
				
				//check for mine
				if(board.nodes.get(i).hasMine){
					button.setText("x");
					revealAll();
					gameOver("lost");
				
				//not a mine
				}else{
					revealNode(board, board.nodes.get(i));
				}
			}
		}
		
	}
	
	/**
	 * Reveal all Board values
	 */
	public void revealAll(){
		
		//Loop through all buttons
		for(int i = 0; i<board.length*board.length;i++){
			int resID = getResources().getIdentifier(String.valueOf("button" + i), "id", "com.android.minesweeper");
			Button button = (Button)findViewById(resID);
			if(board.nodes.get(i).hasMine){//if button is mine
				button.setText("X");
				button.setTextColor(Color.rgb(176, 23, 21));
			}else{//non-mine button
				int num = board.nodes.get(i).surroundingMines;
				if(num == 0) {
					button.setVisibility(View.INVISIBLE);
				} else {
					button.setText(String.valueOf(num));
				}
			}
		}
	}

	/**
	 * Performs end of game actions after game is won or lost
	 *
	 * @param winOrLoss tells method if win or loss case
	 */
	public void gameOver(String winOrLoss){
		
		AlertDialog.Builder alert = new AlertDialog.Builder(EasyActivity.this);//Create end of game alert
		
		//get time info
		Chronometer timer = (Chronometer)findViewById(R.id.chronometer1);
		String time = (String) timer.getText();
		timer.stop();
		
		//Add game info to database if score is higher than zero
		if(correctMinesGotten > 0)database.addScore("easy", correctMinesGotten, time);
		
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
						finish();
						startActivity(getIntent());
					}
				})
				
				//New level button
				.setNegativeButton("Choose New Level", new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						EasyActivity.this.finish();
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
						EasyActivity.this.finish();
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
		List<Score> allScores = database.getAllScores("easy");
		
		//loop through top 4 entries of table as obtained in the allScores list
		for(int i = allScores.size()-1, j = 3; i>=0 && j>=0; j--, i--){
			returnString += String.valueOf(allScores.get(i).score + " | " + allScores.get(i).time + "\n");
		}
		return returnString;
	}
	
	
	/**
	 * Reveal nodes surrounding given node
	 *
	 * @param b Board on which nodes exist
	 * @param node node for which all nodes around it will be checked
	 */
	public  void revealSurrouding(Board b,Node node){
		//perform for each side
		if(b.isValidCoord(node.x+1, (node.y-1))) {
			revealNode(b,b.getNode(node.x+1, (node.y-1)));
		}
		if(b.isValidCoord(node.x, (node.y-1))) {
			revealNode(b, b.getNode(node.x, (node.y-1)));
		}
		if(b.isValidCoord(node.x+1, (node.y))) {
			revealNode(b, b.getNode(node.x+1, (node.y)));
		}
		if(b.isValidCoord(node.x-1, node.y-1)) {
			revealNode(b, b.getNode(node.x-1, (node.y-1)));
		}
		if(b.isValidCoord(node.x-1, node.y)) {
			revealNode(b, b.getNode(node.x-1, (node.y)));
		}
		if(b.isValidCoord(node.x-1, node.y+1)) {
			revealNode(b, b.getNode(node.x-1, (node.y+1)));
		}
		if(b.isValidCoord(node.x, node.y+1)) {
			revealNode(b, b.getNode(node.x, (node.y+1)));
		}
		if(b.isValidCoord(node.x+1, node.y+1)) {
			revealNode(b, b.getNode(node.x+1, node.y+1));
		}
	}
	
	/**
	 * Reveal a node.  Mark node as visited.  If node is zero, reveals
	 * surrounding nodes.
	 *
	 * @param b
	 * @param node
	 */
	public void revealNode(Board b, Node node){
		if(node.disarmed) {
			return;
		}
		node.disarmed = true;
				
		int resID = getResources().getIdentifier(String.valueOf("button" + node.id), "id", "com.android.minesweeper");//get id using button id		
		Button button = (Button)findViewById(resID);
		button.setText(String.valueOf(node.surroundingMines));//set button num value
		
		//if this mine has zero mines surrounding it
		if(node.surroundingMines == 0) {
			button.setVisibility(View.INVISIBLE);
			revealSurrouding(b, node);
		}
	}
	
	/**
	 * Obtain number of unset mines left for display.  Also
	 * sets number of correct mines.  Checks for winning game if
	 * all mines have flags on them or if all mines non mine squares
	 * are found.
	 *
	 * @return number of mines left unflagged regardless of if correct or not
	 */
	public int getNumberOfMinesLeft(){
		int minesLeft = board.numberOfMines;
		correctMinesGotten = 0;
		int disarmedCount = 0;
		int numberOfNonMines = ((board.length * board.length)) - board.numberOfMines;
		
		for (Node node : board.nodes) {
			if(node.disarmed) {
				disarmedCount++;
			}
			if(node.flagged && node.hasMine) {
				correctMinesGotten++;
			}
			if(node.flagged) {
				minesLeft--;
			}
		}
		
		if(correctMinesGotten == board.numberOfMines && minesLeft == 0) {
			gameOver("won");//all mines flagged correctly
		}
		if( disarmedCount == numberOfNonMines ) {
			gameOver("lost");//all disarms are correct and no more exist
		}
		
		return minesLeft;
	}
	
	//Menu Stuff
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.game_levels, menu);
		return true;
	}
	
	public void onOptionSettings(MenuItem menu){
		finish();
		startActivity(new Intent(this, Settings.class));
	}
	
	public void onOptionMainMenu(MenuItem menu){
		finish();
		startActivity(new Intent(this, MainActivity.class));
	}

	public void onOptionChooseLevel(MenuItem menu){
		finish();
		startActivity(new Intent(this, MainActivity.class));
	}
	
	public void onOptionPlayAgain(MenuItem menu){
		finish();
		startActivity(new Intent(this, EasyActivity.class));
	}
	
}
