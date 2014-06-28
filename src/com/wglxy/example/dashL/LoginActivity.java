package com.wglxy.example.dashL;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIUtils;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

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
import com.wglxy.example.dashL.model.User;
import com.wglxy.example.dashL.net.NetHandler;

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
					String email = edit_email.getText().toString();
					String phoneNo = edit_phoneNumber.getText().toString();
					try {
						if (loginUser(email, phoneNo)) {
							toast("login successful");
							txt_login_status.setVisibility(View.GONE);
							startActivity(new Intent(LoginActivity.this,
									HomeActivity.class));
							finish();
						} else {
							toast("invalid login");
							txt_login_status.setVisibility(View.VISIBLE);
						}
					} catch (JSONException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
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

	boolean loginUser(String email, String phoneNo) throws JSONException {
		String json = startLogin(email, phoneNo);
		User user;

		user = processResults(json);
		if (user != null) {
			// login successfull
			user = processResults(json);
			super.setLoggedIn(user.getPhone(), user.getEmail(), true,
					user.getFirst_name(), user.getLast_name(), String.valueOf(user.getId()));
			return true;
		} else {
			txt_login_status.setVisibility(View.VISIBLE);
			return false;
		}
	}

	String startLogin(String email, String phoneNo) {
		String[] args = new String[] { email, phoneNo };
		String jsonResult = null;
		try {
			jsonResult = new NetOps().execute(args).get();
			Log.e(LOG_TAG, "res:" + jsonResult);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ExecutionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return jsonResult;
	}

	User processResults(String results) throws JSONException {
		if (results == null)
			return null;
		User user = new User();

		JSONObject jsonObject = new JSONObject(results);
		String status = jsonObject.getString("status");
		String statusMessage = jsonObject.getString("statusMessage");

		if (Integer.parseInt(status) != 200) {
			return null;
		} else {
			JSONObject jsonUser = jsonObject.getJSONObject("userDetails");
			String id = jsonUser.getString("id");
			String email = jsonUser.getString("email");
			String phone = jsonUser.getString("phone");
			String first_name = jsonUser.getString("first_name");
			String last_name = jsonUser.getString("last_name");

			user.setId(Integer.parseInt(id));
			user.setEmail(email);
			user.setPhone(phone);
			user.setFirst_name(first_name);
			user.setLast_name(last_name);
			return user;
		}
	}

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

		Runnable showToast = new Runnable() {
			@Override
			public void run() {
				// TODO Auto-generated method stub
				toast("Unable to connect to the internet");
			}
		};

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			txt_login_status.setVisibility(View.VISIBLE);
			if (pDlg != null)
				pDlg.show();
		}

		@Override
		protected String doInBackground(String... params) {
			String email = params[0];
			String phoneNumber = params[1];

			String results = null;
			try {
				URI uri = buildURI(email, phoneNumber);
				results = new NetHandler().callAPI(uri);
				Log.e(LOG_TAG, "result:" + results);

			} catch (ClientProtocolException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				loginError = true;
				Log.e(LOG_TAG, "Api error: " + e.getMessage());
				runOnUiThread(showToast);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				loginError = true;
				e.printStackTrace();
				Log.e(LOG_TAG, "Api error: " + e.getMessage());
				runOnUiThread(showToast);
			} catch (URISyntaxException e) {
				// TODO Auto-generated catch block
				loginError = true;
				e.printStackTrace();
				Log.e(LOG_TAG, "Api error: " + e.getMessage());
				runOnUiThread(showToast);
			}
			// TODO Auto-generated method stub
			return results;
		}

		URI buildURI(String email, String phoneNumber)
				throws URISyntaxException {
			ArrayList<NameValuePair> args = new ArrayList<NameValuePair>();
			args.add(new BasicNameValuePair(Constants.API_ENDPOINT_ARG,
					Constants.API_ENDPOINT_LOGIN));
			args.add(new BasicNameValuePair(Constants.API_LOGIN_ARGS_EMAIL,
					email));
			args.add(new BasicNameValuePair(Constants.API_LOGIN_ARGS_PHONE,
					phoneNumber));
			return URIUtils.createURI("http", Constants.API_BASE_URL, -1,
					Constants.API_ENDPOINT_URL,
					URLEncodedUtils.format(args, "UTF-8"), null);
		}

		@Override
		protected void onPostExecute(String results) {
			super.onPostExecute(results);
			if (pDlg != null)
				pDlg.dismiss();
		}

	}
}
