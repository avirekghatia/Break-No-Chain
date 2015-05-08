package com.example.krishnaghatia.break_no_chain.Controllers;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInstaller;
import android.net.Uri;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.example.krishnaghatia.break_no_chain.R;
import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphRequestBatch;
import com.facebook.GraphResponse;
import com.facebook.Profile;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.parse.LogInCallback;
import com.parse.ParseException;
import com.parse.ParseFacebookUtils;
import com.parse.ParseObject;
import com.parse.ParseUser;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

public class UserLoginActivity extends ActionBarActivity {

    CallbackManager callbackManager;
    private final String FILENAME = "user_ID";
    private final int CODE_FACEBOOK_ACTIVITY = 0;
    private final int CODE_CREATE_USER = 1;
    private final String string = "";

    private FacebookCallback<LoginResult> mCallback = new FacebookCallback<LoginResult>(){

        @Override
        public void onSuccess(LoginResult loginResult)
        {
            //AccessToken accessToken = loginResult.getAccessToken();
            Profile profile = Profile.getCurrentProfile();
            String profileId = profile.getId();
            System.out.println(profileId);
            String profileName = profile.getName();
            System.out.println("!!!!!User Profile name "+ profile.getName());
            Uri linkURI = profile.getLinkUri();
            SharedPreferences sharedPreferences = getSharedPreferences(FILENAME, MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString("user_Id", profileId);
            editor.putString("Name", profileName);
            //editor.putBoolean("isLoggedIn", true);
            editor.commit();
            System.out.println("I have the user logged in now, going to retrieve the friendlist");
            AccessToken access_token = AccessToken.getCurrentAccessToken();
            GraphRequestBatch batch = new GraphRequestBatch(
                    GraphRequest.newMeRequest(
                            access_token,
                            new GraphRequest.GraphJSONObjectCallback() {
                                @Override
                                public void onCompleted(
                                        JSONObject jsonObject,
                                        GraphResponse response) {
                                    String name = jsonObject.optString("first_name");   // Application code for user
                                    System.out.println("The name of the user is "+name);
                                }
                            }),
                    GraphRequest.newMyFriendsRequest(
                            access_token,
                            new GraphRequest.GraphJSONArrayCallback() {
                                @Override
                                public void onCompleted(
                                        JSONArray jsonArray,
                                        GraphResponse response) {
                                    System.out.println("Number of friends returned "+jsonArray.length());
                                    if (jsonArray.length()!=0)
                                    for(int i=0; i<jsonArray.length();i++){
                                        try {
                                            JSONObject jsonObject = (JSONObject) jsonArray.get(i);
                                            String nameFriend = (String) jsonObject.getString("name");
                                            System.out.println("The friend's name is !!++++++++!!!!!!!!!!!!+++++++++++++!!!!!!!!!!!!!++++++++++!!!"+nameFriend  );
                                            String friendId = (String) jsonObject.getString("id");
                                            DatabaseHelper databaseHelper = new DatabaseHelper(getApplicationContext());
                                            //friends in database
                                        } catch (JSONException e) {
                                            System.out.println(e);
                                        }
                                    }
                                    System.out.println("Application code for users friends");   // Application code for users friends
                                }
                            })
            );
            batch.addCallback(new GraphRequestBatch.Callback() {
                @Override
                public void onBatchCompleted(GraphRequestBatch graphRequests) {
                    System.out.println("Batch Completed");// Application code for when the batch finishes
                }
            });
            batch.executeAsync();
            System.out.println("Execute async ki jagah");
            /*GraphRequest graphRequest = GraphRequest.newMyFriendsRequest((AccessToken.getCurrentAccessToken(), new GraphRequest.GraphJSONArrayCallback() {
                @Override
                public void onCompleted(
                        JSONArray jsonArray,
                        GraphResponse response) {
                    // Application code for users friends
                }
            })));
            batch.addCallback(new GraphRequestBatch.Callback() {
                @Override
                public void onBatchCompleted(GraphRequestBatch graphRequests) {
                    // Application code for when the batch finishes
                }
            });
            batch.executeAsync();

            /*Intent intent = new Intent(UserLoginActivity.this, MainActivity.class);
            startActivity(intent);*/

            //Log.v("The profile ID is", profile.getId());
            Log.v("in logged in", "Hey there it is working");

            /*if(profile!=null) {
                userName.setText("Welcome" + profile.getName());
            }*/
        }

        @Override
        public void onCancel()
        {
            System.out.println("In Cancel code");
            Context context=getApplicationContext();
            String text="Could not Log in";
            int duration= Toast.LENGTH_SHORT;
            android.widget.Toast.makeText(context, text, duration).show();
        }

        @Override
        public void onError(FacebookException e) {
            System.out.println("Error occurred. Please try again");
            Context context=getApplicationContext();
            String text="Error occurred. Please try again";
            int duration= Toast.LENGTH_SHORT;
            android.widget.Toast.makeText(context, text, duration).show();
        }

    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {


        Log.v("in User Login activity", "User Login Activity created");
        FacebookSdk.sdkInitialize(this.getApplicationContext());
        //AccessToken.getCurrentAccessToken();

        //callbackManager = CallbackManager.Factory.create();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_login);
        LoginButton loginButton = (LoginButton) findViewById(R.id.login_button);
        loginButton.setReadPermissions("user_friends" );         //TODO check if it is going to be LoginButton or loginButton here
        String permit = "user_friends,user_likes,email";
        List<String> permissions = Arrays.asList(permit.split(","));

        ParseFacebookUtils.logInWithReadPermissionsInBackground(this, permissions, new LogInCallback() {
            @Override
            public void done(ParseUser user, ParseException err) {
                if (user == null) {
                    Log.d("MyApp", "Uh oh. The user cancelled the Facebook login.");
                } else if (user.isNew()) {
                    Log.d("MyApp", "User signed up and logged in through Facebook!");
                } else {
                    Log.d("MyApp", "User logged in through Facebook!");
                }
            }
        });
        //loginButton.registerCallback(callbackManager, mCallback);

    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_user_login, menu);
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


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == CODE_CREATE_USER){
            if(resultCode == RESULT_OK){
                System.out.println("Code create user returned");
            }
            Toast.makeText(this, "An error occurred, please try again.", Toast.LENGTH_SHORT).show();
        }
        else{
            if(resultCode == RESULT_OK){
                //callbackManager.onActivityResult(requestCode, resultCode, data);
                ParseFacebookUtils.onActivityResult(requestCode, resultCode, data);
                System.out.println("The user has now logged in and i am in onactivityresult()");
                finish();
            }
            else{
                Toast.makeText(this, "An error occurred, please try again.", Toast.LENGTH_SHORT).show();
            }
        }
    }

    public void createUserProfile(View view) throws FileNotFoundException {

        //FileOutputStream fos = openFileOutput(FILENAME, MODE_APPEND);           //Don't want to replace the file
        //FileOutputStream fos1 = openFileOutput(FILENAME, MODE_PRIVATE);
        /*try{
            fos1.write(string.getBytes());
            fos1.close();
        }
        catch (IOException e){
            System.out.println("IO Exception");
        }*/


        Intent intent = new Intent(UserLoginActivity.this, CreateNewUser.class);
        startActivity(intent);
        //startActivityForResult(intent, CODE_CREATE_USER);                  //start new activity to create new user
        //TODO write in the details you want in the file

    }

    /*@Override
    public void onBackPressed(){
        SharedPreferences prefs = getSharedPreferences("user_ID", Context.MODE_PRIVATE);
        String userId = prefs.getString("user_Id", "");
        if(userId.equals("")){
            Toast toast = Toast.makeText(getApplicationContext(),"Please log in",Toast.LENGTH_SHORT);
            toast.show();
        }
        else{

        }
    }*/
}