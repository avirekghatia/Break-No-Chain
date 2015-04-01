package com.example.krishnaghatia.break_no_chain.Models;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Krishna Ghatia on 4/1/2015.
 */
public class Goal implements Parcelable{

    private int goalId;
    private String shareBoolean;
    private String name;
    private String startDate;
    private String endDate;
    private String notificatonTime;

    public static final Parcelable.Creator<Goal> CREATOR = new Parcelable.Creator<Goal>(){
        public Goal createFromParcel(Parcel g) {
            return new Goal(g);
        }
        public Goal[] newArray(int size) {
                return new Goal[size];
        }

    };

    public Goal(Parcel g) {
        name = g.readString();
        shareBoolean = g.readString();
        startDate = g.readString();
        endDate = g.readString();
        goalId = g.readInt();
        notificatonTime = g.readString();
    }

    public Goal(int goalId, String shareBoolean, String name, String startDate, String endDate, String notificatonTime){
        this.goalId = goalId;
        this.shareBoolean = shareBoolean;
        this.name = name;
        this.startDate = startDate;
        this.endDate = endDate;
        this.notificatonTime = notificatonTime;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {

        dest.writeInt(goalId);
        dest.writeString(shareBoolean);
        dest.writeString(name);
        dest.writeString(startDate);
        dest.writeString(endDate);
        dest.writeString(notificatonTime);

    }
    public int getGoalId(){
        return goalId;
    }

    public String getShareBoolean() {
        return shareBoolean;
    }

    public String getName() {
        return name;
    }

    public String getStartDate() {
        return startDate;
    }

    public String getEndDate(){
        return endDate;
    }

    public String getNotificatonTime(){
        return notificatonTime;
    }

    public void setShareBoolean(String shareBoolean) {
        this.shareBoolean = shareBoolean;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public void setNotificatonTime(String notificatonTime) {
        this.notificatonTime = notificatonTime;
    }


    @Override
    public int describeContents() {
        // Do not implement!
        return 0;
    }

}
