package com.wglxy.example.dashL.model;

import android.os.Parcel;
import android.os.Parcelable;

public class User implements Parcelable{
	
	private int id;
	private String email;
	private String permissions;
	private String first_name;
	private String last_name;
	private String business_name;
	private String services;
	private String phone;
	private String about;
	private String address;
	private int stars;

	public User(){}
	
	public User(Parcel inParcel){
		id = inParcel.readInt();
		email = inParcel.readString();
		permissions = inParcel.readString();
		first_name = inParcel.readString();
		last_name = inParcel.readString();
		business_name = inParcel.readString();
		services = inParcel.readString();
		phone = inParcel.readString();
		about = inParcel.readString();
		address = inParcel.readString();
		stars = inParcel.readInt();
	}

	@Override
	public int describeContents(){
		return 0;
	}
	
	@Override
    public void writeToParcel(Parcel outParcel, int flags) {
		outParcel.writeInt(id);
        outParcel.writeString(email);
        outParcel.writeString(permissions);
        outParcel.writeString(first_name);
        outParcel.writeString(last_name);
        outParcel.writeString(business_name);
        outParcel.writeString(services);
        outParcel.writeString(phone);
        outParcel.writeString(about);
        outParcel.writeString(address);
        outParcel.writeInt(stars);
    }
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPermissions() {
		return permissions;
	}

	public void setPermissions(String permissions) {
		this.permissions = permissions;
	}

	public String getFirst_name() {
		return first_name;
	}

	public void setFirst_name(String first_name) {
		this.first_name = first_name;
	}

	public String getLast_name() {
		return last_name;
	}

	public void setLast_name(String last_name) {
		this.last_name = last_name;
	}

	public String getBusiness_name() {
		return business_name;
	}

	public void setBusiness_name(String business_name) {
		this.business_name = business_name;
	}

	public String getServices() {
		return services;
	}

	public void setServices(String services) {
		this.services = services;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getAbout() {
		return about;
	}

	public void setAbout(String about) {
		this.about = about;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public int getStars() {
		return stars;
	}

	public void setStars(int stars) {
		this.stars = stars;
	}

	public static final Parcelable.Creator<User> CREATOR = new Parcelable.Creator<User>() {
        public User createFromParcel(Parcel in) {
            return new User(in);
        }
        public User[] newArray(int size) {
            return new User[size];
        }
    };
}
