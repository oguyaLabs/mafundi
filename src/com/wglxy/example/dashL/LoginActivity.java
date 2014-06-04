package com.wglxy.example.dashL;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class LoginActivity extends DashboardActivity {

	EditText edit_username;
	EditText edit_password;
	Button btn_cancel;
	Button btn_login;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);
		setTitleFromActivityLabel(R.id.title_text);
		showLogginLogout(findViewById(R.id.btn_login_logout));
		
		initUI();
	}
	
	void initUI(){
		edit_username = (EditText)findViewById(R.id.edit_login_username);
		edit_password = (EditText)findViewById(R.id.edit_login_password);
		btn_cancel = (Button)findViewById(R.id.btn_login_cancel);
		btn_login = (Button)findViewById(R.id.btn_login_login);
		
		btn_login.setOnClickListener(clickListener);
		btn_cancel.setOnClickListener(clickListener);
	}
	
	boolean validateInput(){
		if(TextUtils.isEmpty(edit_username.getText().toString())){
			toast("Please enter your username");
			return false;
		}else if(TextUtils.isEmpty(edit_password.getText().toString())){
			toast("Please enter your password");
			return false;
		}else{
			return true;
		}
	}
	
	View.OnClickListener clickListener = new View.OnClickListener() {
		
		@Override
		public void onClick(View view) {
			// TODO Auto-generated method stub
			switch(view.getId()){
			case R.id.btn_login_login:	//log in user
				if(validateInput()){
					//TODO call api
				}
				break;
				
			case R.id.btn_login_cancel:	//cancel
				LoginActivity.this.onBackPressed();
				break;
				
			default:break;
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
