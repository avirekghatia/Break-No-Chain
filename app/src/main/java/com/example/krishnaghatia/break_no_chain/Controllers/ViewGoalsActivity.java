package com.example.krishnaghatia.break_no_chain.Controllers;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.example.krishnaghatia.break_no_chain.Models.Goal;
import com.example.krishnaghatia.break_no_chain.R;


public class ViewGoalsActivity extends ActionBarActivity {
    TextView goalId, name, startDate,endDate, notifyTime, share;
    String s = null;
    int goal_id;

    //SharedPreferences prefs = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_goals);
        SharedPreferences prefs = getSharedPreferences("userId",MODE_PRIVATE);
        String string = prefs.getString("userId","");
        goal_id = getIntent().getIntExtra("goalId", 0);
        //view_goal(18,"Prachib");
        view_goal(goal_id,string);
    }


    public void view_goal(int goal_id, String user_id){
        DatabaseHelper dbhelper = new DatabaseHelper(this);
        Goal goal =  dbhelper.display_goal(goal_id, user_id);

        goalId = (TextView) findViewById(R.id.goal_id);
        goalId.setText("GoalId:" + goal.getGoalId());

        name = (TextView) findViewById(R.id.goal_name);
        name.setText("Goal Name:  " + goal.getName());
        startDate = (TextView) findViewById(R.id.start_date);
        startDate.setText("Start Date:  " + goal.getStartDate());
        endDate = (TextView) findViewById(R.id.end_date);
        endDate.setText("End Date:  " + goal.getEndDate());
        notifyTime = (TextView) findViewById(R.id.notify_time);
        notifyTime.setText("Notification Time:  " + goal.getNotificatonTime());
        share = (TextView) findViewById(R.id.share_frds);
        share.setText("shared :  " + goal.getShareBoolean());

    }
    public void back (View v){
        setResult(Activity.RESULT_CANCELED, getIntent());
        finish();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_view_goals, menu);
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