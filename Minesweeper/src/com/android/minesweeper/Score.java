/**
 * 
 */
package com.android.minesweeper;

/**
 * Player score data.
 * 
 * @author Ryan Iverson and Jeffrey Forster
 *
 */
public class Score {
	String name;//player name
	int score;//number of mines correctly gotten
	String time;//time taken
	
	/**
	 * Default constructor with no inputs
	 */
	Score(){}
	
	/**
	 * @param name player name
	 * @param score number of mines correctly gotten
	 * @param time time taken
	 */
	Score(String name, int score, String time){
		this.name = name;
		this.score = score;
		this.time = time;
	}

	/**
	 * @return the time
	 */
	public String getTime() {
		return time;
	}

	/**
	 * @param time the time to set
	 */
	public void setTime(String time) {
		this.time = time;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the score
	 */
	public int getScore() {
		return score;
	}

	/**
	 * @param score the score to set
	 */
	public void setScore(int score) {
		this.score = score;
	}
	
	
	
}
