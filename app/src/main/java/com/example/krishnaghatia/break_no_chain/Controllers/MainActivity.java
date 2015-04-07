package com.example.krishnaghatia.break_no_chain.Controllers;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.example.krishnaghatia.break_no_chain.R;
import com.facebook.AccessToken;
import com.facebook.FacebookSdk;


public class MainActivity extends ActionBarActivity {


    public int goal_id = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FacebookSdk.sdkInitialize(this.getApplicationContext());
        AccessToken.getCurrentAccessToken();
        //TODO write the code to check if there exists a user already in the data. Check with Fb and the user Login.
        //TODO If no user exists, start the activity i.e. UserLoginActivity

        SharedPreferences prefs = getSharedPreferences("user_ID", Context.MODE_PRIVATE);
        String userId = prefs.getString("user_Id", "");
        System.out.println(userId+"userId!!!!!!!!!!!!!!!!!!!!!!!!!!!!*************************!!!!!!!!!!!!!!!!!");
        SharedPreferences.Editor editor = prefs.edit();

        editor.putString("user_Id", "");
        //editor.commit();
        String userId1 = prefs.getString("user_Id", "");
        System.out.println(userId1+"userId111   !!!!!!!!!!!!!!!!!!!!!!!!!!!!*************************!!!!!!!!!!!!!!!!!");
        if(userId.equals("")){
            Intent intent = new Intent(MainActivity.this, UserLoginActivity.class);
            startActivity(intent);
        }
        goal_id = getIntent().getIntExtra("goalId",0);

    }

    public void startCreateGoalActivity(View v){
        Intent intent =  new Intent(this, CreateGoalActivity.class);
        startActivityForResult(intent, 1);
    }
    public void viewGoal (View v){
        Intent intent = new Intent(this, ViewGoalsActivity.class);
        intent.putExtra("goalId",goal_id);
        startActivityForResult(intent, 2);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
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

/*package com.example.krishnaghatia.break_no_chain.Controllers;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.example.krishnaghatia.break_no_chain.R;
import com.facebook.AccessToken;
import com.facebook.FacebookSdk;


public class MainActivity extends ActionBarActivity {

    String fileName = "userId";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        FacebookSdk.sdkInitialize(this.getApplicationContext());
        AccessToken.getCurrentAccessToken();
        //TODO write the code to check if there exists a user already in the data. Check with Fb and the user Login.
        //TODO If no user exists, start the activity i.e. UserLoginActivity

        SharedPreferences prefs = getSharedPreferences("user_ID", Context.MODE_PRIVATE);
        String userId = prefs.getString("user_Id", "");
        System.out.println(userId+"userId!!!!!!!!!!!!!!!!!!!!!!!!!!!!*************************!!!!!!!!!!!!!!!!!");
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString("user_Id", "");
        editor.commit();
        String userId1 = prefs.getString("user_Id", "");
        System.out.println(userId1+"userId111   !!!!!!!!!!!!!!!!!!!!!!!!!!!!*************************!!!!!!!!!!!!!!!!!");
        if(userId.equals("")){
            Intent intent = new Intent(MainActivity.this, UserLoginActivity.class);
            startActivity(intent);
        }
        //editor.putString("userId", "Prachib");
        //editor.commit(); // This line is IMPORTANT. If you miss this one its not gonna work!
    }

    public void startCreateGoalActivity(View v){
        Intent intent =  new Intent(this, CreateGoalActivity.class);
        startActivityForResult(intent, 1);
    }
    public void viewGoal (View v){
        Intent intent = new Intent(this, ViewGoalsActivity.class);
        startActivityForResult(intent, 2);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
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
}*/