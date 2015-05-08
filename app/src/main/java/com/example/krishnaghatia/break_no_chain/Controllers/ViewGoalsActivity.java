package com.example.krishnaghatia.break_no_chain.Controllers;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.content.res.TypedArray;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.AvoidXfermode;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.krishnaghatia.break_no_chain.Models.Goal;
import com.example.krishnaghatia.break_no_chain.R;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;


public class ViewGoalsActivity extends ActionBarActivity {

    private LinearLayout myVerticalLayout;
    private LinearLayout myVerticalLayout2;
    private LinearLayout myHorizontlLayout;
    private LinearLayout myHorizontalLayout2;
    private LinearLayout myHorizontalLayout4;
    private ProgressBar pb;
    private ProgressBar progressBar;
    private Button btn;
    private TextView textview;
    private TextView remaining_label;
    private TextView remaining_days;
    private TextView completed_label;
    private TextView completed_days;
    private String userID;



    private class BackgroundProcess extends AsyncTask<String,Integer,String>{
        private int id;
        private int maxvalue;
        private int incrementby;
        private int progressStatus;
        BackgroundProcess(int id,int maxvalue,int incrementby,int progressStatus){this.id= id;
            this.maxvalue = maxvalue;
            this.incrementby=incrementby;
            this.progressStatus=progressStatus;}
        @Override
        protected String doInBackground(String...strings){
            String progressbar_id = Integer.toString(id+4);
            progressBar = (ProgressBar) findViewById(getResources().getIdentifier(progressbar_id,"id",getPackageName()));
            if(progressStatus<maxvalue){
                progressStatus+=incrementby;
                progressBar.setProgress(progressStatus);

            }
            try{
                Thread.sleep(200);

            }
            catch(InterruptedException e){
                e.printStackTrace();
            }
            publishProgress();
            return null;
        }
        @Override
        protected void onPreExecute(){

        }
        @Override
        protected void onProgressUpdate(Integer...values){super.onProgressUpdate(values);}

        @Override
        protected void onPostExecute(String result){super.onPostExecute(result);}
    }

    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_viewgoals);

        //Define Vertical Layout
        myVerticalLayout = (LinearLayout) findViewById(R.id.MyVerticallayout);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,LinearLayout.LayoutParams.WRAP_CONTENT);
        myVerticalLayout.setOrientation(LinearLayout.VERTICAL);

        //Define Horizontal Layout 3
        myVerticalLayout2 = (LinearLayout) findViewById(R.id.MyHorizontalLayout3);
        LinearLayout.LayoutParams horizontallayout3params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,LinearLayout.LayoutParams.WRAP_CONTENT);
        horizontallayout3params.setMargins(20,45,15,20);

        //Define Horizontal Layout
        myHorizontlLayout = (LinearLayout) findViewById(R.id.MyHorizontalLayout);

        //Define Horizontal Layout 2
        myHorizontalLayout2 = (LinearLayout) findViewById(R.id.MyHorizontalLayout2);

        // Define Horizontal Layout 4
        myHorizontalLayout4=(LinearLayout) findViewById(R.id.MyHorizontalLayout4);
        LinearLayout.LayoutParams horizontallayout4params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,LinearLayout.LayoutParams.WRAP_CONTENT);
        horizontallayout4params.setMargins(20,45,15,20);

        //Get Colors and store in an array
        int[] rainbow = getBaseContext().getResources().getIntArray(R.array.Colors);


        //Get user id
        SharedPreferences preferences = getSharedPreferences("userId",MODE_PRIVATE);
        userID = preferences.getString("userId","");

        // Create Database Instance and get ProgressBars
        final DatabaseHelper databaseHelper = new DatabaseHelper(this);
        final SQLiteDatabase sqLiteOpenHelper = databaseHelper.getReadableDatabase();
        String [] whereArgs = new String[]{userID};
        Cursor cursor = sqLiteOpenHelper.query("GoalTable",null,"user_id=?",whereArgs,null,null,null);
        startManagingCursor(cursor);

        //Loop through Database to add progressbars and buttons
        int i =0;
        while(cursor.moveToNext()){
            final int j =i;
            int Max_value =0;
            int daysbetween=0;
            String Startdate = cursor.getString(cursor.getColumnIndex("start_date"));
            String Enddate = cursor.getString(cursor.getColumnIndex("end_date"));
            final String LastMarkeddate = cursor.getString(cursor.getColumnIndex("last_marked"));
            final int count = cursor.getInt(cursor.getColumnIndex("count"));
            Log.i("StartDateinDatabase",Startdate);
            Log.i("EndDateinDatabase",Enddate);
            Log.i("LastMarkedinDatabase",LastMarkeddate);
            Log.i("countinDatabase",String.valueOf(count));
            DateFormat df = new SimpleDateFormat("dd-MM-yyyy");
            Date start = null;
            Date end;
            try{
                start = df.parse(Startdate);
                end =  df.parse(Enddate);
                Log.i("Date:StartDate", String.valueOf(start));
                Log.i("Date:EndDate",String.valueOf(end));
                long timeOne = start.getTime();
                long timeTwo = end.getTime();
                long oneDay = 1000*60*60*24;
                long delta = (timeTwo - timeOne) / oneDay;
                daysbetween = (int) delta;
            }catch(ParseException e){
                e.printStackTrace();
            }

            Max_value = 500;

            Log.i("Daysbetween",String.valueOf(daysbetween));
            Log.i("Value of i", String.valueOf(i));

            Log.i("Incrementby",String.valueOf((Max_value/daysbetween)));

            // Get the completed Progress
            final int completed_progress = count * (Max_value/daysbetween);
            Log.i("CompletedProgress", String.valueOf(completed_progress));



            //Define ProgressBar
            pb = new ProgressBar(this,null,android.R.attr.progressBarStyleHorizontal);
            pb.setLayoutParams(params);
            pb.getLayoutParams().width = Max_value;
            pb.invalidate();
            pb.setId(i + 4);
            pb.setMax(Max_value);
            pb.setProgress(completed_progress);
            pb.getProgressDrawable().setColorFilter(rainbow[i], PorterDuff.Mode.SRC_IN);
            myVerticalLayout.addView(pb);

            //Define Remaining and Completed Days
            remaining_label = new TextView(this);
            remaining_label.setText("Remaining:" + String.valueOf(daysbetween-count));
            remaining_label.setTextColor(rainbow[i]);
           // remaining_days = new TextView(this);
           // remaining_days.setText(String.valueOf(daysbetween-count));
           // remaining_days.setTextColor(rainbow[i]);
            completed_label = new TextView(this);
            completed_label.setText("Completed:" + String.valueOf(count));
            completed_label.setTextColor(rainbow[i]);
         //   completed_days = new TextView(this);
          //  completed_days.setText(String.valueOf(count));
          //  completed_days.setTextColor(rainbow[i]);
            myVerticalLayout2.addView(remaining_label,horizontallayout3params);
            myHorizontalLayout4.addView(completed_label,horizontallayout4params);
          //  myVerticalLayout2.addView(completed_label);
          //  myVerticalLayout2.addView(completed_days);


            //Define TextView
            LinearLayout.LayoutParams linearlayoutparams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,LinearLayout.LayoutParams.WRAP_CONTENT);
            linearlayoutparams.setMargins(50,50,50,30);
            textview = new TextView(this);
            textview.setText(cursor.getString(cursor.getColumnIndex("name")));
            myHorizontalLayout2.addView(textview,linearlayoutparams);

            //Define Button
            LinearLayout.LayoutParams linearparams = new  LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,LinearLayout.LayoutParams.WRAP_CONTENT);
            linearparams.setMargins(24, 0, 24, 0);
            btn = new Button(this);
            btn.setBackgroundColor(rainbow[i]);
            btn.setText("Mark");
            btn.setId(i);
            myHorizontlLayout.addView(btn,linearparams);
            final int finalMax_value = Max_value;
            final int finalDaysbetween = daysbetween;
            final Date finalStart = start;
            btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    //Current Date
                    String curr = new SimpleDateFormat("dd-MM-yyyy").format(new Date());
                    DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
                    Date currentdate = null;
                    try {
                        currentdate = dateFormat.parse(curr);
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                    Log.i("Currentdate", String.valueOf(currentdate));

                    // Compare lastmarked in Database with current date
                    Date lastindb = null;
                    DateFormat dateFormat1 = new SimpleDateFormat("dd-MM-yyyy");
                    try {
                        lastindb = dateFormat1.parse(LastMarkeddate);
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }

                    Log.i("LastinDB",String.valueOf(lastindb));







                        // Convert current time into string and store it in last_marked
                        String last_marked_tobeinserted = new SimpleDateFormat("dd-MM-yyyy").format(new Date());
                        Log.i("Lastmarked", last_marked_tobeinserted);

                        // increment count and update database
                        int count_days = count;
                        count_days = count_days + 1;
                        SQLiteDatabase db = databaseHelper.getWritableDatabase();
                        ContentValues cv = new ContentValues();
                        cv.put("count", count_days);
                        cv.put("last_marked", last_marked_tobeinserted);
                        String where = "user_id=?";
                        String[] userid_values = new String[]{userID};
                        db.update("GoalTable", cv, where, userid_values);

                        //Set parameters and send into function BackgroundProcess
                        int id = j;
                        final int value = finalMax_value;
                        final int Incrementby = (value / finalDaysbetween);
                        final int progressStatus = completed_progress;
                        MarkProgress(id, value, Incrementby, progressStatus);
                    }



            });

            i++;
            sqLiteOpenHelper.close();

        }


    }

    public void MarkProgress(int id, int maxvalue,int incrementby,int progressStatus){
        new BackgroundProcess(id,maxvalue,incrementby,progressStatus).execute();
    }

    @Override

    public boolean onCreateOptionsMenu(Menu menu){
        //Inflate the menu;this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
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


