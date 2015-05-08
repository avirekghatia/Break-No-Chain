package com.example.krishnaghatia.break_no_chain.Controllers;

import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.v4.app.NotificationCompat;
import android.widget.Toast;

import com.example.krishnaghatia.break_no_chain.R;

import java.util.Calendar;
import java.util.logging.Handler;

public class NotifyService extends Service {

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        System.out.println("Notify service has been started------------------------------------------------------------------------------------");
        //Toast.makeText(this, "You will be notified at the time selected", Toast.LENGTH_SHORT);
        Intent resultIntent = new Intent(this, ViewGoalsActivity.class);
        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this);
        notificationBuilder.setAutoCancel(true);
        notificationBuilder.setContentTitle("Break-No-Chain");
        notificationBuilder.setContentText("Your activity start time");
        notificationBuilder.setSmallIcon(R.drawable.com_facebook_button_icon);
        AlarmManager alarmManager =(AlarmManager)getSystemService(ALARM_SERVICE);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, resultIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.HOUR_OF_DAY, 15);                  //Time
        cal.set(Calendar.MINUTE, 43);
        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, cal.getTimeInMillis(), 24*60*60*1000/*24*60*60*1000*/, pendingIntent);
        notificationBuilder.setContentIntent(pendingIntent);
        Notification notification = notificationBuilder.build();
        NotificationManager notificationManager = (NotificationManager) this.getSystemService(NOTIFICATION_SERVICE);
        //notificationManager.notify(0, notification);
        return START_STICKY;
    }
}
