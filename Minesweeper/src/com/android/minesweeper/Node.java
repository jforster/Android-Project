/**
 * 
 */
package com.android.minesweeper;

/**
 * Class for node of minesweeper
 * game board.
 * 
 * @author Ryan Iverson and Jeffrey Forster
 *
 */
public class Node{
	int x;//coord
	int y;//coord
	int id;//id given by index
	boolean hasMine = false;
	int surroundingMines = 0;
	boolean disarmed = false;
	boolean flagged = false;
	
	/**
	 * @param x coord
	 * @param y coord
	 * @param id index
	 */
	Node(int x, int y, int id){
		this.x = x;
		this.y = y;
		this.id = id;
	}
	
}
