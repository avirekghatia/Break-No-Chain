package com.example.krishnaghatia.break_no_chain.Models;

import android.os.Parcel;
import android.os.Parcelable;

import java.lang.reflect.Array;
import java.util.ArrayList;

/**
 * Created by Krishna Ghatia on 4/1/2015.
 */



public class User implements Parcelable {

    private String name;
    private String emailId;
    private String phoneNumber;
    private ArrayList<User> friendList;
    private ArrayList<Goal> goalsList;

    public static final Parcelable.Creator<User> CREATOR = new Parcelable.Creator<User>() {
        public User createFromParcel(Parcel p) {
            return new User(p);
        }

        public User[] newArray(int size) {
            return new User[size];
        }
    };

    public User(Parcel g) {
        name = g.readString();
        emailId = g.readString();
        phoneNumber = g.readString();
        friendList = g.readTypedList(friendList, User.CREATOR);
        goalsList = g.readTypedList(goalsList, Goal.CREATOR);
    }

    public User(String name, String emailId, String phoneNumber, ArrayList friendList, ArrayList goalsList){
        this.emailId = emailId;
        this.phoneNumber = phoneNumber;
        this.name = name;
        this.friendList = friendList;
        this.goalsList = goalsList;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {

        dest.writeString(emailId);
        dest.writeString(phoneNumber);
        dest.writeString(name);
        dest.writeTypedList(friendList);
        dest.writeTypedList(goalsList);

    }

    @Override
    public int describeContents() {
        // Do not implement!
        return 0;
    }

}
