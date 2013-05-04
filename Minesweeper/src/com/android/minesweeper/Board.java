package com.android.minesweeper;

import java.util.ArrayList;

/**
 * Class allowing creation and maintaining
 * of minesweeper game board
 * 
 * @author Ryan Iverson and Jeffrey Forster
 *
 */
public class Board {

	protected int length;
	ArrayList<Node> nodes = new ArrayList<Node>();
	int numberOfMines;
	
	/**
	 * Constructor taking in Board length
	 * 
	 * @param length length to be used for Board
	 */
	Board(int length){
		this.length = length;
		this.numberOfMines = length*length/5;
		createBoard();
		generateBoardValues();
	}
	
	/**
	 * Sets Board length and number of mines
	 * 
	 * @param length length of Board sides
	 * @param numberOfMines number of mines to be put in Board
	 */
	Board(int length, int numberOfMines){
		this.length = length;
		this.numberOfMines = numberOfMines;
		createBoard();
		generateBoardValues();
	}
	
	/**
	 * Add nodes(buttons) to arraylist
	 */
	public void createBoard(){
		for(int k = 0;k<(length*length)-1;){
			for(int i = 0; i< length; i++){
				for(int j = 0; j<length; j++,k++){
					nodes.add(new Node(i, j, k));
				}
			}
		}
	}
	
	/**
	 * Wrapper class for generating mines and numbers
	 * on Board
	 */
	public void generateBoardValues(){
		generateMines();
		assignValues();
	}
	
	/**
	 * Randomly put mines in Board nodes
	 */
	public void generateMines(){
		//put mines at random places
		for (int i = 0; i < numberOfMines; i++) {
			Node node = nodes.get((int)(Math.random()*((length*length)-1)));
			
			//if square already has mine
			if(node.hasMine){
				i--;
				continue;
			}
			node.hasMine = true;			
		}
	}
	
	/**
	 * Get node using coordinates
	 *
	 * @param i x coord
	 * @param j y coord
	 * @return node with given coords
	 */
	public Node getNode(int i, int j){
		for (Node node : nodes) {
			if(node.x == i && node.y == j)return node;
		}
		return null;
	}
	
	/**
	 * Assign number values to graphs depending on placement
	 * of mines
	 */
	public void assignValues(){
		
		//assign surroundMines values
		for (Node node : nodes) {
			if(node.hasMine){
				node.surroundingMines = 9;
				continue;
			}
			
			//check surrounding nodes
			if(isValidCoord(node.x+1, node.y) && getNode(node.x+1, node.y).hasMine) {
				node.surroundingMines++;			
			}
			if(isValidCoord(node.x+1, (node.y-1)) && getNode(node.x+1, node.y-1).hasMine) {
				node.surroundingMines++;
			}
			if(isValidCoord(node.x, (node.y-1)) && getNode(node.x, node.y-1).hasMine) {
				node.surroundingMines++;
			}
			if(isValidCoord(node.x-1, node.y-1) && getNode(node.x-1, node.y-1).hasMine) {
				node.surroundingMines++;
			}
			if(isValidCoord(node.x-1, node.y) && getNode(node.x-1, node.y).hasMine) {
				node.surroundingMines++;
			}
			if(isValidCoord(node.x-1, node.y+1) && getNode(node.x-1, node.y+1).hasMine) {
				node.surroundingMines++;
			}
			if(isValidCoord(node.x, node.y+1) && getNode(node.x, node.y+1).hasMine) {
				node.surroundingMines++;
			}
			if(isValidCoord(node.x+1, node.y+1) && getNode(node.x+1, node.y+1).hasMine) {
				node.surroundingMines++;
			}
		}
		
	}
	
	/**
	 * Check if coord is on Board
	 *
	 * @param x coord
	 * @param y coord
	 * @return boolean if on Board or not
	 */
	public boolean isValidCoord(int x, int y){
		//x check
		if(x<0 || x>=length) {
			return false;
		}
		//y check
		if(y< 0 || y>= length) {
			return false;
		}
		return true;
	}

}
