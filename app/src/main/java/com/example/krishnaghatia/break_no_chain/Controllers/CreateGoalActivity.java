package com.example.krishnaghatia.break_no_chain.Controllers;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.example.krishnaghatia.break_no_chain.R;
import com.facebook.AccessToken;
import com.facebook.GraphRequest;
import com.facebook.GraphRequestBatch;
import com.facebook.GraphResponse;
import com.parse.ParseObject;
import com.parse.SaveCallback;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;


public class CreateGoalActivity extends ActionBarActivity implements
        View.OnClickListener {
    String goal_name;
    int goal_id;
    String from_date;
    String to_date;
    String notification_time;
    Boolean isShareChecked;
    Button btnCalendar_frm, btnCalendar_to, btnTimePicker;
    EditText txtDate_frm, txtDate_to, txtTime;
    EditText edit;
    String object_id;           //TODO - I have added this - Avirek 5/8/15

    // Variable for storing current date and time
    private int mYear, mMonth, mDay, mHour, mMinute;


    /*SharedPreferences prefs = this.getSharedPreferences(
            "uid", Context.MODE_PRIVATE);

    String getuser = "uid";
    String u = prefs.getString(getuser,null);*/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_goal);
        btnCalendar_frm = (Button) findViewById(R.id.btn_frm_date);
        btnCalendar_to =   (Button) findViewById(R.id.btn_to_date);
        btnTimePicker = (Button) findViewById(R.id.noti_time);

        txtDate_frm = (EditText) findViewById(R.id.frm_date);
        txtDate_to = (EditText) findViewById(R.id.to_date);
        txtTime = (EditText) findViewById(R.id.Noti_time);

        btnCalendar_frm.setOnClickListener((View.OnClickListener) this);
        btnCalendar_to.setOnClickListener((View.OnClickListener) this);
        btnTimePicker.setOnClickListener((View.OnClickListener) this);

    }
    // public void save_goal (String user_id,String name, String start_date, String end_date,String notificationTime, boolean shareBoolean  ){
    public int save_goal (String name, String start_date, String end_date,String notificationTime, boolean shareBoolean  ){

        DatabaseHelper dbHelper = new DatabaseHelper(this);     //TODO - moved it up - Avirek 5/8/15

        //TODO - Added following code as it goes - Avirek 5/8/15
        final ParseObject goal = new ParseObject("Goal");
        goal.put("name", name);
        goal.put("start_date", start_date);
        goal.put("end_date", end_date);
        goal.put("notification_time", notificationTime);
        goal.put("shareBoolean", shareBoolean);
        //TODO - Check internet connection here
        goal.saveInBackground(new SaveCallback() {
            @Override
            public void done(com.parse.ParseException e) {
                if(e==null){
                    object_id = goal.getObjectId();
                    System.out.println("The Object IS returned is "+object_id);
                }
                else {
                    e.printStackTrace();
                }
            }
        });


        //object_id = goal.getObjectId();
        dbHelper.parse_create_goal(object_id, name);
        System.out.println("The Object IS returned is "+object_id);
        //TODO - until here changed - Avirek 5/8/15


        String share = String.valueOf(shareBoolean);
        SharedPreferences prefs = getSharedPreferences("userId",MODE_PRIVATE);
        String string = prefs.getString("user_Id","");
        int goal_id = dbHelper.create_goal(string, name, start_date, end_date, notificationTime, share);
        System.out.println("Hey I am over here!! Come and get me now");
        //startNotificationService();
        //dbHelper.create_goal(goal_id, user_id, name, start_date, end_date, notificationTime, share);
        //  dbHelper.create_goal(user_id, name, start_date, end_date, notificationTime, share);
        return goal_id;
    }
    public void back(View v){
        /*Intent back = new Intent(getBaseContext(), MainActivity.class);
        startActivity(back);*/
        setResult(Activity.RESULT_CANCELED, getIntent());
        finish();
    }
    public void create_goal (View v) throws ParseException {

        ToggleButton toggle = (ToggleButton) findViewById(R.id.toggleButton);
        boolean result = toggle.isChecked();
        isShareChecked = result;
        if(isShareChecked == true){
            shareWithFriends();
        }
        edit = (EditText) findViewById(R.id.goal_name_val);
        goal_name = edit.getText().toString();
        edit = (EditText) findViewById(R.id.frm_date);
        from_date = edit.getText().toString();
        edit = (EditText) findViewById(R.id.to_date);
        to_date =  edit.getText().toString();
        edit = (EditText) findViewById(R.id.Noti_time);
        notification_time = edit.getText().toString();

        /*AlarmManager alarmManager = (AlarmManager)getSystemService(ALARM_SERVICE);
        pendingIntent = PendingIntent.getService(ThisApp.this, 0, myIntent, 0);
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, 12);
        calendar.set(Calendar.MINUTE, 00);
        calendar.set(Calendar.SECOND, 00);
        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), 24*60*60*1000 , pendingIntent);  //set repeating every 24 hours*/

        //save_goal("Prachib",goal_name,from_date,to_date,notification_time,Share_boolean);
        String simpleDate = mMonth +" " +mDay+ " "+mYear+" "+mHour+":"+mMinute+ ":00";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MM dd yyyy HH:mm:ss");
        Date date = simpleDateFormat.parse(simpleDate);
        System.out.println("The date is "+date);
        int goal_id =  save_goal(goal_name,from_date,to_date,notification_time, isShareChecked);
        Toast toast = Toast.makeText(getApplicationContext(),"Goal  "+goal_name+"  is created successfully",Toast.LENGTH_LONG);
        toast.show();
        Intent intent = new Intent(getBaseContext(),MainActivity.class);
        intent.putExtra("goalId",goal_id);
        startActivity(intent);
    }



    // Start of change
    // Widget GUI
    // @Override
    public void onClick(View v) {
        String simpleDate = "";

        if (v == btnCalendar_frm) {

            // Process to get Current Date
            final Calendar c = Calendar.getInstance();
            mYear = c.get(Calendar.YEAR);
            mMonth = c.get(Calendar.MONTH);
            mDay = c.get(Calendar.DAY_OF_MONTH);

            // Launch Date Picker Dialog
            DatePickerDialog dpd = new DatePickerDialog(this,
                    new DatePickerDialog.OnDateSetListener() {

                        @Override
                        public void onDateSet(DatePicker view, int year,
                                              int monthOfYear, int dayOfMonth) {
                            // Display Selected date in textbox
                            txtDate_frm.setText(dayOfMonth + "-"
                                    + (monthOfYear + 1) + "-" + year);
                            String simpleDate = dayOfMonth + " "
                                    + (monthOfYear + 1) + " " + year;

                        }
                    }, mYear, mMonth, mDay);
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MM dd yyyy");
            try {
                Date date = simpleDateFormat.parse(simpleDate);
                Toast.makeText(this, "You selected "+date+".", Toast.LENGTH_SHORT).show();
                System.out.println(date);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            dpd.show();

        }
        if (v == btnCalendar_to ) {

            // Process to get Current Date
            final Calendar c = Calendar.getInstance();
            mYear = c.get(Calendar.YEAR);
            mMonth = c.get(Calendar.MONTH);
            mDay = c.get(Calendar.DAY_OF_MONTH);

            // Launch Date Picker Dialog
            DatePickerDialog dpd = new DatePickerDialog(this,
                    new DatePickerDialog.OnDateSetListener() {

                        @Override
                        public void onDateSet(DatePicker view, int year,
                                              int monthOfYear, int dayOfMonth) {
                            // Display Selected date in textbox
                            txtDate_to.setText(dayOfMonth + "-"
                                    + (monthOfYear + 1) + "-" + year);

                        }
                    }, mYear, mMonth, mDay);
            dpd.show();
        }

        if (v == btnTimePicker) {

            // Process to get Current Time
            final Calendar c = Calendar.getInstance();
            mHour = c.get(Calendar.HOUR_OF_DAY);
            mMinute = c.get(Calendar.MINUTE);

            // Launch Time Picker Dialog
            TimePickerDialog tpd = new TimePickerDialog(v.getContext(),
                    new TimePickerDialog.OnTimeSetListener() {

                        @Override
                        public void onTimeSet(TimePicker view, int hourOfDay,
                                              int minute) {
                            // Display Selected time in textbox
                            txtTime.setText(hourOfDay + ":" + minute);
                        }
                    }, mHour, mMinute, false);
            tpd.show();
        }
    }
    // End of Change




    private void startNotificationService() {
        Intent startNotificationServiceIntent = new Intent(this, NotifyService.class);
        startNotificationServiceIntent.putExtra("GOAL_NAME", goal_name);
        startService(startNotificationServiceIntent);
    }

    private void shareWithFriends() {
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
                                System.out.println("The name of the user is " + name);
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