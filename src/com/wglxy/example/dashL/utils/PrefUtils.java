package com.wglxy.example.dashL.utils;

import com.wglxy.example.dashL.constants.Constants;

import android.app.Activity;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.preference.PreferenceManager;

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

	public String getLoggedInUser(){
		SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this.activity);
		String username = prefs.getString(Constants.KEY_USER_PHONE, "John Doe");
		return username;
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
