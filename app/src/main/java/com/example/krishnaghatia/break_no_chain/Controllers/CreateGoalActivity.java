package com.example.krishnaghatia.break_no_chain.Controllers;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.example.krishnaghatia.break_no_chain.R;


public class CreateGoalActivity extends ActionBarActivity {
    String goal_name;
    int goal_id;
    String from_date;
    String to_date;
    String notification_time;
    Boolean Share_boolean;

    EditText edit;

    /*SharedPreferences prefs = this.getSharedPreferences(
            "uid", Context.MODE_PRIVATE);

    String getuser = "uid";
    String u = prefs.getString(getuser,null);*/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_goal);
    }
    // public void save_goal (String user_id,String name, String start_date, String end_date,String notificationTime, boolean shareBoolean  ){
    public int save_goal (String name, String start_date, String end_date,String notificationTime, boolean shareBoolean  ){
        DatabaseHelper dbHelper = new DatabaseHelper(this);
        String share = String.valueOf(shareBoolean);
        SharedPreferences prefs = getSharedPreferences("userId",MODE_PRIVATE);
        String string = prefs.getString("userId","");
        //dbHelper.create_goal(goal_id, user_id, name, start_date, end_date, notificationTime, share);
        //  dbHelper.create_goal(user_id, name, start_date, end_date, notificationTime, share);
        int goal_id = dbHelper.create_goal(string, name, start_date, end_date, notificationTime, share);
        return goal_id;
    }
    public void back(View v){
        /*Intent back = new Intent(getBaseContext(), MainActivity.class);
        startActivity(back);*/
        setResult(Activity.RESULT_CANCELED, getIntent());
        finish();
    }
    public void create_goal (View v){

        ToggleButton toggle = (ToggleButton) findViewById(R.id.toggleButton);
        boolean result = toggle.isChecked();
        Share_boolean = result;
        edit = (EditText) findViewById(R.id.goal_name_val);
        goal_name = edit.getText().toString();
        edit = (EditText) findViewById(R.id.frm_date);
        from_date = edit.getText().toString();
        edit = (EditText) findViewById(R.id.to_date);
        to_date =  edit.getText().toString();
        edit = (EditText) findViewById(R.id.Noti_time);
        notification_time = edit.getText().toString();

        //save_goal("Prachib",goal_name,from_date,to_date,notification_time,Share_boolean);
        int goal_id =  save_goal(goal_name,from_date,to_date,notification_time,Share_boolean);
        Toast toast = Toast.makeText(getApplicationContext(),"Goal  "+goal_name+"  is created successfully",Toast.LENGTH_LONG);
        toast.show();
        Intent intent = new Intent(getBaseContext(),MainActivity.class);
        intent.putExtra("goalId",goal_id);
        startActivity(intent);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_create_goal, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}