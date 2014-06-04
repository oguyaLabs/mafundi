package com.wglxy.example.dashL;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class SignUpActivity extends DashboardActivity {

	EditText text_su_email;
	EditText text_su_username;
	EditText text_su_password1;
	Button btn_su_sign_up;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_signup);
		setTitleFromActivityLabel(R.id.title_text);
		showLogginLogout(findViewById(R.id.btn_login_logout));
		
		initUI();
	}
	
	void initUI(){
		text_su_email = (EditText)findViewById(R.id.text_su_email);
		text_su_username = (EditText)findViewById(R.id.text_su_username);
		text_su_password1 = (EditText)findViewById(R.id.text_su_password1);
		btn_su_sign_up = (Button)findViewById(R.id.btn_su_sign_up);
		
		btn_su_sign_up.setOnClickListener(clickListener);
	}
	
	boolean validateInput(){
		if(TextUtils.isEmpty(text_su_email.getText().toString())){
			toast("Please enter a valid email address");
			return false;
		}else if(TextUtils.isEmpty(text_su_username.getText().toString())){
			toast("Please enter your username");
			return false;
		}else if(TextUtils.isEmpty(text_su_password1.getText().toString())){
			toast("Please enter your password");
			return false;
		}else
			return true;
	}
	
	View.OnClickListener clickListener = new View.OnClickListener() {
		
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			
			if(validateInput()){
				//TODO add api calls
			}
		}
	};

	@Override
	protected void onPause(){
		super.onPause();
	}
	
	@Override
	protected void onResume(){
		super.onResume();
	}
	
	@Override
	protected void onSaveInstanceState(Bundle outState){
		super.onSaveInstanceState(outState);
	}
	
	@Override
	protected void onRestoreInstanceState(Bundle savedInstanceState){
		super.onRestoreInstanceState(savedInstanceState);
		
	}
}