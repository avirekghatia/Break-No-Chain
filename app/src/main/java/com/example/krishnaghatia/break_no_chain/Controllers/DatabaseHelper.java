package com.example.krishnaghatia.break_no_chain.Controllers;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.krishnaghatia.break_no_chain.Models.Goal;

/**
 * Created by Prachi on 02-04-2015.
 */
public class DatabaseHelper extends SQLiteOpenHelper {

    // TODO - Added these data tables method to create goal on parse - Avirek - 5/8/15
    private static final String PARSE_CREATE_GOAL_TABLE = "Parse_Create_Goal";
    private static final String COLUMN_PARSE_CREATE_GOAL_ID = "Parse_Create_Goal_id";
    private static final String COLUMN_PARSE_CREATE_GOAL_NAME = "Parse_Create_Goal_name";
    // TODO - Until here - Avirek - 5/8/15

    public static String name = "GoalDatabase";
    public static int version = 3;
    private static final String TABLE_GOAL = "GoalTable";

    public DatabaseHelper(Context context) {
        super(context, name, null, version);
        // TODO Auto-generated constructor stub
    }



    public void onCreate(SQLiteDatabase db) {
        db.execSQL("Create table GoalTable(goal_id integer, user_id text, name text, start_date text, end_date text, notificationTime text, shareBoolean text,count integer, last_marked text, PRIMARY KEY(goal_id, user_id) )");
        // TODO - Added this to create table Goal(id retrieved from parse and name of the goal) to create goal on parse - Avirek - 5/8/15
        db.execSQL("create table " + PARSE_CREATE_GOAL_TABLE + "("
                + COLUMN_PARSE_CREATE_GOAL_ID + " text primary key, "
                + COLUMN_PARSE_CREATE_GOAL_NAME + " text)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int
            newVersion) {

        // Drop older table if exists
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_GOAL);
        // create tables again
        onCreate(db);
    }

    //public void create_goal(int goal_id, String user_id, String name, String start_date, String end_date,String notificationTime, String shareBoolean ){
    public int create_goal(String user_id, String name, String start_date, String end_date,String notificationTime, String shareBoolean ){
        SQLiteDatabase dataBase = this.getWritableDatabase();
        SQLiteDatabase db = this.getReadableDatabase();
        int goal_id = 0;
        //int go = dataBase.execSQL("select MAX(goal_id) from GoalTable");
        //Goal g = new Goal();
        // int goal_id = g.getGoalId();
        //g.setGoalID(goal_id + 1);
        Cursor abc = db.rawQuery("Select MAX(goal_id) from GoalTable where user_id =?", new String []{user_id});
        abc.moveToFirst();
        if (abc.getCount()!= 0)
        {
            goal_id = abc.getInt(0);
            //goal_id=abc.getCount();
            goal_id= goal_id + 1;
        }
        dataBase.execSQL("Insert into GoalTable values('" + goal_id + "','" +user_id + "','" +name+ "','" +start_date+ "','"
                // dataBase.execSQL("Insert into GoalTable ((SELECT MAX(goal_id)+1 FROM GoalTable),user_id, name, start_date, end_date, notificationTime, shareBoolean) values('"+user_id + "','" +name+ "','" +start_date+ "','"
                +end_date+ "','" +notificationTime+ "','" +shareBoolean+ "', '0', '')");

        return goal_id;
    }

    // TODO - Added this method to create goal on parse - Avirek - 5/8/15
    public String parse_create_goal(String goal_id, String name){
        SQLiteDatabase dataBase = this.getWritableDatabase();
        SQLiteDatabase db = this.getWritableDatabase();
        System.out.println("Inside insert Trip functions");
        ContentValues cv = new ContentValues();
        cv.put(COLUMN_PARSE_CREATE_GOAL_ID, goal_id);
        cv.put(COLUMN_PARSE_CREATE_GOAL_NAME, name);
        // return id of new trip
        db.insert(PARSE_CREATE_GOAL_TABLE, null, cv);
        System.out.println("Inserted Successfully");
        return goal_id;
    }



    public Goal display_goal(int goal_id, String user_id){
        Goal goal = null;
        int goalId;
        String userId;
        String name;
        String startDate;
        String endDate;
        String notifyTime;
        String ShareFrds;

        SQLiteDatabase database = this.getReadableDatabase();
        String goalID = String.valueOf(goal_id);
        Cursor abc = database.rawQuery("Select goal_id,user_id, name,start_date, end_date,notificationTime, shareBoolean from goalTable where user_id=? and goal_id=?", new String []{user_id, goalID});
        if (abc.getCount()!= 0)
        {
            abc.moveToFirst();
            goalId = abc.getInt(0);
            userId = abc.getString(1);
            name = abc.getString(2);
            startDate  =abc.getString(3);
            endDate = abc.getString(4);
            notifyTime = abc.getString(5);
            ShareFrds = abc.getString(6);
            goal = new Goal(goalId,ShareFrds, name,startDate,endDate,notifyTime);
            // goal = new Goal(name,startDate,endDate,notifyTime,ShareFrds);

        }
        return goal;
    }

}