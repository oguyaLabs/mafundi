package com.wglxy.example.dashL.utils;

import android.app.Activity;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.preference.PreferenceManager;

import com.wglxy.example.dashL.constants.Constants;
import com.wglxy.example.dashL.model.User;

public class PrefUtils {

	private Activity activity;
	
	public PrefUtils(Activity activity) {
		this.activity = activity;
	}

	public boolean isLoggedIn(){
		SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this.activity);
		boolean loggedIn = prefs.getBoolean(Constants.KEY_USER_LOGGED_IN, false);
		return loggedIn;
	}

	public User getLoggedInUser(){
		SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this.activity);
		User user = new User();
		String phone = prefs.getString(Constants.KEY_USER_PHONE, null);
		String email = prefs.getString(Constants.KEY_USER_EMAIL, null);
		String first_name = prefs.getString(Constants.KEY_USER_FIRST_NAME, null);
		String last_name = prefs.getString(Constants.KEY_USER_LAST_NAME, null);
		
		user.setPhone(phone);
		user.setEmail(email);
		user.setFirst_name(first_name);
		user.setLast_name(last_name);
		
		return user;
	}

	public void setLoggedIn(String phone, String email, boolean loggedIn, String first_name, String last_name){
		SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this.activity);
		Editor editor = prefs.edit();
		editor.putString(Constants.KEY_USER_PHONE, phone);
		editor.putString(Constants.KEY_USER_EMAIL, email);
		editor.putBoolean(Constants.KEY_USER_LOGGED_IN, loggedIn);
		editor.putString(Constants.KEY_USER_FIRST_NAME, first_name);
		editor.putString(Constants.KEY_USER_LAST_NAME, last_name);		
		editor.commit();
	}

	public void logOutUser(){
		SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this.activity);
		Editor editor = prefs.edit();
		editor.putString(Constants.KEY_USER_PHONE, "");
		editor.putString(Constants.KEY_USER_EMAIL, "");
		editor.putBoolean(Constants.KEY_USER_LOGGED_IN, false);
		editor.commit();
	}
	
}
