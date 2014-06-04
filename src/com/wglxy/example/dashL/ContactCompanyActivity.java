package com.wglxy.example.dashL;

import com.wglxy.example.dashL.constants.Constants;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class ContactCompanyActivity extends DashboardActivity {
	
	private static final String LOG_TAG = "ContactCompanyActivity";
	EditText edit_subject;
	EditText edit_message;
	Button btn_submit;
	
	String subject;
	String message;
	
	private static final String KEY_SUBJECT = "subject";
	private static final String KEY_MESSAGE = "message";
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_contact_company);
		setTitleFromActivityLabel(R.id.title_text);
		showLogginLogout(findViewById(R.id.btn_login_logout));
		
		initUI();
	}
	
	void initUI(){
		edit_subject = (EditText)findViewById(R.id.edit_subject);
		edit_message = (EditText)findViewById(R.id.edit_message);
		btn_submit = (Button)findViewById(R.id.btn_submit);
		
		btn_submit.setOnClickListener(clickListener);
	}
	
	View.OnClickListener clickListener = new View.OnClickListener() {
		
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			
			//check if logged
			if(!isLoggedIn()){
				Toast.makeText(ContactCompanyActivity.this, "Please login first before you contact a company.", Toast.LENGTH_LONG).show();
				return;
			}
			
			if(validateInput()){
				//TODO send sms/send to server
				
			}
		}
	};
	
	boolean validateInput(){
		subject = edit_subject.getText().toString();
		message = edit_message.getText().toString();
		
		if(TextUtils.isEmpty(subject)){
			toast("Please enter the subject of your message");
			Log.e(LOG_TAG, "enter subject");
			return false;
		}else if(TextUtils.isEmpty(message)){
			toast("Please enter the message");
			Log.e(LOG_TAG, "enter message");
			return false;
		}else
			return true;
	}
	
	void storeTemp(){
		subject = edit_subject.getText().toString();
		message = edit_message.getText().toString();
	}
	

	
	@Override
	protected void onPause(){
		super.onPause();
		storeTemp();
	}
	
	@Override
	protected void onResume(){
		super.onResume();
	}
	
	@Override
	protected void onSaveInstanceState(Bundle outState){
		super.onSaveInstanceState(outState);
		
		storeTemp();
		outState.putString(KEY_MESSAGE, message);
		outState.putString(KEY_SUBJECT, subject);
	}
	
	@Override
	protected void onRestoreInstanceState(Bundle savedInstanceState){
		super.onRestoreInstanceState(savedInstanceState);
		
		message = savedInstanceState.getString(KEY_MESSAGE);
		subject = savedInstanceState.getString(KEY_SUBJECT);
	}	
}
