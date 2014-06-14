package com.wglxy.example.dashL;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.wglxy.example.dashL.constants.Constants;

public class LoginActivity extends DashboardActivity {

	static final String LOG_TAG = "LoginActivity";
	EditText edit_email;
	EditText edit_phoneNumber;
	Button btn_cancel;
	Button btn_login;
	TextView txt_signUp;
	TextView txt_login_status;
	ProgressDialog pDlg;
	
	boolean loginError = false;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);
		setTitleFromActivityLabel(R.id.title_text);
		showLogginLogout(findViewById(R.id.btn_login_logout));

		initUI();
	}

	void initUI() {
		edit_email = (EditText) findViewById(R.id.edit_login_email);
		edit_phoneNumber = (EditText) findViewById(R.id.edit_login_phone);
		btn_cancel = (Button) findViewById(R.id.btn_login_cancel);
		btn_login = (Button) findViewById(R.id.btn_login_login);
		txt_signUp = (TextView) findViewById(R.id.txt_signUp);
		txt_login_status = (TextView) findViewById(R.id.txt_login_status);

		pDlg = new ProgressDialog(this);
		pDlg.setCancelable(false);
		pDlg.setMessage("Please wait...");
		pDlg.setIndeterminate(true);

		btn_login.setOnClickListener(clickListener);
		btn_cancel.setOnClickListener(clickListener);
		txt_signUp.setOnClickListener(clickListener);
	}

	boolean validateInput() {
		if (TextUtils.isEmpty(edit_email.getText().toString())) {
			toast("Please enter your username");
			return false;
		} else if (TextUtils.isEmpty(edit_phoneNumber.getText().toString())) {
			toast("Please enter your password");
			return false;
		} else {
			return true;
		}
	}

	View.OnClickListener clickListener = new View.OnClickListener() {

		@Override
		public void onClick(View view) {
			// TODO Auto-generated method stub
			switch (view.getId()) {
			case R.id.btn_login_login: // log in user
				if (validateInput()) {
					// TODO call api
				}
				break;

			case R.id.btn_login_cancel: // cancel
				LoginActivity.this.onBackPressed();
				break;

			case R.id.txt_signUp: // signUp
				startActivity(new Intent(LoginActivity.this,
						SignUpActivity.class));
				break;

			default:
				break;
			}

		}
	};

	@Override
	protected void onPause() {
		super.onPause();
	}

	@Override
	protected void onResume() {
		super.onResume();
	}

	@Override
	protected void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
	}

	@Override
	protected void onRestoreInstanceState(Bundle savedInstanceState) {
		super.onRestoreInstanceState(savedInstanceState);

	}

	class NetOps extends AsyncTask<String, Void, String> {
		InputStream inputStream;
		HttpClient httpclient;
		HttpPost httppost;
		List<NameValuePair> nameValuePairs;
		StringBuffer sb;
		

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			if (pDlg != null)
				pDlg.show();
		}

		@Override
		protected String doInBackground(String... params) {
			String email = params[0];
			String phoneNumber = params[1];

			String results;
			try {
				results = callAPI(email, phoneNumber);
			} catch (ClientProtocolException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				loginError = true;
				Log.e(LOG_TAG, "Api error: "+e.getMessage());
			} catch (IOException e) {
				// TODO Auto-generated catch block
				loginError = true;
				e.printStackTrace();
				Log.e(LOG_TAG, "Api error: "+e.getMessage());
			}
			
			// TODO Auto-generated method stub
			return null;
		}

		String callAPI(String email, String phoneNumber) throws ClientProtocolException, IOException {
			String url = URLEncoder.encode(Constants.API_BASE_URL + Constants.API_ENDPOINT_LOGIN);
			InputStream inputStream;
			HttpClient httpClient;
			HttpPost httpPost;
			List<NameValuePair> args = new ArrayList<NameValuePair>();

			httpClient = new DefaultHttpClient();
			httpPost = new HttpPost(url);

			args.add(new BasicNameValuePair(Constants.API_LOGIN_ARGS_EMAIL, email));
			args.add(new BasicNameValuePair(Constants.API_LOGIN_ARGS_PHONE, phoneNumber));

			httpPost.setEntity(new UrlEncodedFormEntity(args));
			HttpResponse response = httpClient.execute(httpPost);
			inputStream = response.getEntity().getContent();

			BufferedReader rd = new BufferedReader(new InputStreamReader(inputStream), 4096);
			String line;
			StringBuilder sb = new StringBuilder();

			while ((line = rd.readLine()) != null) {
				sb.append(line);
			}
			rd.close();
			inputStream.close();

			return sb.toString();
		}

		@Override
		protected void onPostExecute(String results) {
			super.onPostExecute(results);
			if (pDlg != null)
				pDlg.dismiss();
		}

	}
}
