/**
 * 
 */
package com.android.minesweeper;

import java.util.ArrayList;
import java.util.List;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Handles database actions such as adding
 * and remove data from table
 * 
 * @author Ryan Iverson and Jeffrey Forster
 *
 */
public class Database extends SQLiteOpenHelper{

	private static final String DATABASE_NAME = "scoreManager";
	private static final int DATABASE_VERSION = 1;
	private static final String LEVEL = "level";
	private static final String SCORE = "score";
	private static final String TIME = "time";
	private static final String TABLE_EASY = "table_easy";
	private static final String TABLE_MED = "table_med";
	private static final String TABLE_HARD = "table_hard";
	
	private static final String EASY_TABLE_CREATE = "CREATE TABLE " + TABLE_EASY + " ("
            + LEVEL + " TEXT,"
            + SCORE + " INT," 
            + TIME + " TEXT" + ");";

	private static final String MED_TABLE_CREATE = "CREATE TABLE " + TABLE_MED + " ("
            + LEVEL + " TEXT,"
            + SCORE + " INT," 
            + TIME + " TEXT" + ");";
	
	private static final String HARD_TABLE_CREATE = "CREATE TABLE " + TABLE_HARD + " ("
            + LEVEL + " TEXT,"
            + SCORE + " INT," 
            + TIME + " TEXT" + ");";
	
	/**
	 * @param context
	 * @param name
	 * @param factory
	 * @param version
	 */
	public Database(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	/* (non-Javadoc)
	 * @see android.database.sqlite.SQLiteOpenHelper#onCreate(android.database.sqlite.SQLiteDatabase)
	 */
	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL(EASY_TABLE_CREATE);
		db.execSQL(MED_TABLE_CREATE);
		db.execSQL(HARD_TABLE_CREATE);
	}

	/* (non-Javadoc)
	 * @see android.database.sqlite.SQLiteOpenHelper#onUpgrade(android.database.sqlite.SQLiteDatabase, int, int)
	 */
	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		db.execSQL("DROP TABLE IF EXISTS" + TABLE_EASY);
		db.execSQL("DROP TABLE IF EXISTS" + TABLE_MED);
		db.execSQL("DROP TABLE IF EXISTS" + TABLE_HARD);
		
		onCreate(db);
	}
	
	/**
	 * Add a score to the table
	 *
	 * @param score number of mines correctly gotten
	 * @param time time taken
	 */
	void addScore(String level, int score, String time){
		SQLiteDatabase db = this.getWritableDatabase();

		ContentValues values = new ContentValues();

		values.put(LEVEL, level);
		values.put(SCORE, score);
		values.put(TIME, time);
		
		//Insert Row
		db.insert(getTableName(level), null, values);
		
		db.close();
	}
	
	/**
	 * Return all scores in table
	 *
	 * @return list of scores in table
	 */
	public List<Score> getAllScores(String level){
		List<Score> scoreList = new ArrayList<Score>();
		
		//Select all query and sort by score
		String selectQuery;
		
		selectQuery = "SELECT * FROM " + getTableName(level) + " ORDER BY " + SCORE + " , " + TIME + " DESC";
		
		SQLiteDatabase db = this.getWritableDatabase();
		Cursor cursor = db.rawQuery(selectQuery, null);
		
		//loop through all rows and add to list
		if(cursor.moveToFirst()){
			do{
				Score score = new Score();
				score.setName(cursor.getString(0));
				score.setScore(cursor.getInt(1));
				score.setTime(cursor.getString(2));
				scoreList.add(score);
			}while(cursor.moveToNext());
		}
		cursor.close();
		return scoreList;
	}

	/**
	 * Return number of scores in table
	 *
	 * @return number of rows in table
	 */
	public int getScoreCount(String level){
		String countQuery = "SELECT * FROM " + getTableName(level);
		
		SQLiteDatabase db = this.getReadableDatabase();
		Cursor cursor = db.rawQuery(countQuery, null);
		return cursor.getCount();
	}

	/**
	 * Delete a single user from the table
	 * 
	 */
	public void deleteLowest(String level){
		SQLiteDatabase db = this.getWritableDatabase();
		System.out.println("DELETING!!!");
		String deleteString = "DELETE FROM " + getTableName(level) + " WHERE " + SCORE + " IN (SELECT " + SCORE + " FROM " + getTableName(level) + " ORDER BY " + SCORE + " ASC LIMIT 1)";
		db.execSQL(deleteString);
		db.close();
	}
	
	/**
	 * Delete all rows from table
	 */
	public void deleteAll(String level){
		SQLiteDatabase db = this.getWritableDatabase();
		String stringExec = "DELETE FROM " + getTableName(level);
		db.execSQL(stringExec);
		db.close();
	}
	
	/**
	 * Return table name based on simple string:
	 * easy, med, hard
	 *
	 * @param level string indicating level
	 * @return official table title, null if invalid
	 */
	public String getTableName(String level){
		if(level == "easy")return TABLE_EASY;
		if(level == "med")return TABLE_MED;
		if(level == "hard")return TABLE_HARD;
		
		System.out.println("ERROR: Invalid level string!");
		return null;
	}

}
