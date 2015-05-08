package com.example.krishnaghatia.break_no_chain.Controllers;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.example.krishnaghatia.break_no_chain.R;
import com.facebook.AccessToken;
import com.facebook.FacebookSdk;
import com.facebook.login.LoginManager;
import com.parse.Parse;
import com.parse.ParseFacebookUtils;
import com.parse.ParseObject;
import com.parse.ParseUser;
import com.parse.SignUpCallback;

import java.text.ParseException;
//project ID Google App Engine proven-cosmos-92520.

public class MainActivity extends ActionBarActivity {


    public int goal_id = 0;
    public static String FILENAME = "user_ID";
    public static Boolean isExecuted = false;
    public final int callbackRequestCodeOffset = 100;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Parse.initialize(this, "Ln8n71QbTF3Ftw0mf3QaAmN1Pk6ffONpOW8lNi47", "ZBhgrS9RpudrsZSWciAj6aM9ImwWHR57Pgb9uwWo");
        ParseFacebookUtils.initialize(this, callbackRequestCodeOffset);

// other fields can be set just like with ParseObject

        FacebookSdk.sdkInitialize(this.getApplicationContext());
        //LoginManager.getInstance().logOut();            //logout the user
        //TODO write the code to check if there exists a user already in the data. Check with Fb and the user Login.
        //TODO If no user exists, start the activity i.e. UserLoginActivity
        //SharedPreferences prefs = getSharedPreferences(FILENAME, Context.MODE_PRIVATE);
        //SharedPreferences.Editor editor = prefs.edit();
        //editor.putString("user_Id", "");
        //editor.commit();
        ParseUser currentUser = ParseUser.getCurrentUser();
        if (currentUser != null) {

            //currentUser = ParseUser.getCurrentUser();// do stuff with the user
        } else {
            Intent intent = new Intent(MainActivity.this, UserLoginActivity.class);
            startActivity(intent);// show the signup or login screen
        }
        /*String userId = prefs.getString("user_Id", "");
        System.out.println(userId+"userId!!!!!!!!!!!!!!!!!!!!!!!!!!!!*************************!!!!!!!!!!!!!!!!!");
        String userId1 = prefs.getString("user_Id", "");
        System.out.println(userId1+"userId111   !!!!!!!!!!!!!!!!!!!!!!!!!!!!*************************!!!!!!!!!!!!!!!!!");
        if(userId.equals("")){
            Intent intent = new Intent(MainActivity.this, UserLoginActivity.class);
            startActivity(intent);
        }*/
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