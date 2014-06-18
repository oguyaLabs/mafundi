package com.wglxy.example.dashL.model;

import android.os.Parcel;
import android.os.Parcelable;

public class Review implements Parcelable{

	private String from;
	private String to;
	private String subject;
	private int userID;
	
	public Review() {}
	
	public Review(Parcel inParcel){
		from = inParcel.readString();
		to = inParcel.readString();
		subject = inParcel.readString();
		userID = inParcel.readInt();
	}
	
	@Override
	public int describeContents(){
		return 0;
	}
	
	@Override
	public void writeToParcel(Parcel outParcel, int flags){
		outParcel.writeString(from);
		outParcel.writeString(to);
		outParcel.writeString(subject);
		outParcel.writeInt(userID);
	}

	public String getFrom() {
		return from;
	}

	public void setFrom(String from) {
		this.from = from;
	}

	public String getTo() {
		return to;
	}

	public void setTo(String to) {
		this.to = to;
	}

	public String getSubject() {
		return subject;
	}
	
	public void setUserID(int userID){
		this.userID = userID;
	}
	
	public int getUserID(){
		return this.userID;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}
	
	public static final Parcelable.Creator<Review> CREATOR = new Parcelable.Creator<Review>() {
        public Review createFromParcel(Parcel in) {
            return new Review(in);
        }
 
        public Review[] newArray(int size) {
            return new Review[size];
        }
    };
}
