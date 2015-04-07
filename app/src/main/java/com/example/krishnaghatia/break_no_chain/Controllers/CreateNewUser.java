package com.example.krishnaghatia.break_no_chain.Controllers;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.krishnaghatia.break_no_chain.Models.User;
import com.example.krishnaghatia.break_no_chain.R;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;


public class CreateNewUser extends ActionBarActivity {

    String fileName = "userId";
    private final String FILENAME = "Hello_file";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_new_user);
        try{
            createNewUser();
        }
        catch (IOException e){
            System.out.println("IO Exception here");
        }

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_create_new_user, menu);
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

    public void createNewUser() throws IOException {
        Button button=(Button) findViewById(R.id.buttonCreateNewUser);
        button.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                EditText editTextName = (EditText) findViewById(R.id.editTextNewUserName);
                EditText editTextEmail = (EditText) findViewById(R.id.editTextNewUserEmail);
                EditText editTextPhoneNumber = (EditText) findViewById(R.id.editTextNewUserPhoneNumber);
                EditText editTextPassword = (EditText) findViewById(R.id.editTextNewUserPassword);
                Log.v("***************", "!!!!!!!!!!!!!!*****************");
                System.out.println("!!!!!!!!!!!!!!!!!!!!!!!@@@@@@@@@@@@@@@@@@@@@@@@@@@@****************");

                SharedPreferences sharedPreferences = getSharedPreferences("user_ID", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                String text = editTextEmail.getText().toString();
                editor.putString("user_Id", editTextEmail.getText().toString());
                //editor.putString("user_Id", "");
                editor.commit();
                System.out.println(editTextEmail.getText().toString()+"!!!!!!!!!!!!!!!!!!!%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%");
                String user_Id = sharedPreferences.getString("user_Id", "");
                System.out.println(user_Id+"!++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
                editor.putString("Phone Number", editTextPhoneNumber.getText().toString());
                editor.putString("Password", editTextPassword.getText().toString());
                editor.putString("Name", editTextName.getText().toString());
               // editor.commit();
                User user = new User(editTextName.getText().toString(), editTextEmail.getText().toString(), editTextPhoneNumber.getText().toString());

                /*FileOutputStream fos = openFileOutput(FILENAME, Context.MODE_APPEND);
                fos.write(editTextName.toString().getBytes());
                fos.write(editTextEmail.toString().getBytes());
                fos.write(editTextPassword.toString().getBytes());
                fos.write(editTextPhoneNumber.toString().getBytes());
                fos.close();*/

                Intent intent = new Intent(CreateNewUser.this, MainActivity.class);
                startActivity(intent);
            }

        });

    }
}