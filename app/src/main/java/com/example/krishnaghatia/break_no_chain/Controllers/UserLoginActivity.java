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
import com.facebook.Profile;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;

public class UserLoginActivity extends ActionBarActivity {

    CallbackManager callbackManager;
    private final String FILENAME = "Hello_file";
    String fileName = "user_ID";
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
            String profileName = profile.getName();
            System.out.println("!!!!!User Profile name "+ profile.getName());
            Uri linkURI = profile.getLinkUri();
            SharedPreferences sharedPreferences = getSharedPreferences(fileName, MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString("user_Id", profileId);
            editor.putString("Phone Number", "");
            editor.putString("Password", "");
            editor.putString("Name", profileName);
            editor.commit();
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
        AccessToken.getCurrentAccessToken();

        callbackManager = CallbackManager.Factory.create();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_login);
        LoginButton loginButton = (LoginButton) findViewById(R.id.login_button);
        loginButton.setReadPermissions("user_friends");         //TODO check if it is going to be LoginButton or loginButton here
        loginButton.registerCallback(callbackManager, mCallback);
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
        if(requestCode == CODE_CREATE_USER){

        }
        else{
            super.onActivityResult(requestCode, resultCode, data);
            callbackManager.onActivityResult(requestCode, resultCode, data);
            Intent intent = new Intent(UserLoginActivity.this, MainActivity.class);
            startActivity(intent);
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
        startActivity(intent);                  //start new activity to create new user
        //TODO write in the details you want in the file

    }
}