package com.wglxy.example.dashL;

import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIUtils;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.wglxy.example.dashL.constants.Constants;
import com.wglxy.example.dashL.model.User;
import com.wglxy.example.dashL.net.NetHandler;
import com.wglxy.example.dashL.utils.PrefUtils;

public class ContactCompanyActivity extends DashboardActivity {
	
	private static final String LOG_TAG = "ContactCompanyActivity";
	EditText edit_subject;
	EditText edit_message;
	Button btn_submit;
	
	String subject;
	String message;
	
	private User company;
	private int companyID;

	private ProgressDialog pDlg;
	private boolean loadingError;
	private TextView txt_status;
	private View section_content;
	
	private static final String KEY_SUBJECT = "subject";
	private static final String KEY_MESSAGE = "message";
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_contact_company);
		setTitleFromActivityLabel(R.id.title_text);
		showLogginLogout(findViewById(R.id.btn_login_logout));
		
		getArgs();
		
		initUI();
	}
	
	void getArgs(){
		try{
			Bundle args = getIntent().getExtras();
			companyID = args.getInt(Constants.KEY_COMPANY_ARGS);
			company = args.getParcelable(Constants.KEY_COMPANY_BUNDLE_ARGS);
		}catch(Exception ex){
			Log.e(LOG_TAG, "companyID is null", ex.getCause());
			super.onBackPressed();
		}
	}
	
	void initUI(){
		edit_subject = (EditText)findViewById(R.id.edit_subject);
		edit_message = (EditText)findViewById(R.id.edit_message);
		btn_submit = (Button)findViewById(R.id.btn_submit);
		txt_status = (TextView)findViewById(R.id.txt_status);
		section_content = (RelativeLayout)findViewById(R.id.section_content);
		
		btn_submit.setOnClickListener(clickListener);
		
		pDlg = new ProgressDialog(ContactCompanyActivity.this);
		pDlg.setMessage("Please wait...");
		pDlg.setIndeterminate(true);
		pDlg.setCancelable(false);
	}
	
	View.OnClickListener clickListener = new View.OnClickListener() {
		
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			
			//check if logged
			if(!isLoggedIn()){
				toast("Please login first before you contact a company.");
				return;
			}
			
			if(validateInput()){
				User user = ContactCompanyActivity.this.getLoggedInUser();
				String from = user.getEmail();
				String to = company.getEmail();
				String phone = user.getPhone();
				String subject = edit_subject.getText().toString();
				String body = edit_message.getText().toString();
				String[] args = {from, to, phone, subject, body};
				new NetOps().execute(args);
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
			if (pDlg != null)
				pDlg.show();
		}

		@Override
		protected String doInBackground(String... params) {
			String from = params[0];
			String to = params[1];
			String phone = params[2];
			String subject = params[3];
			String body = params[4];

			String results = null;
			try {
				URI uri = buildURI(from, to, phone, subject, body);
				results = new NetHandler().callAPI(uri);
				Log.e(LOG_TAG, "result:"+results);
			} catch (ClientProtocolException e) {
				loadingError = true;
				Log.e(LOG_TAG, "Api error: "+e.getMessage());
				runOnUiThread(showToast);
			} catch (IOException e) {
				loadingError = true;
				Log.e(LOG_TAG, "Api error: "+e.getMessage());
				runOnUiThread(showToast);
			} catch (URISyntaxException e) {
				loadingError = true;
				Log.e(LOG_TAG, "Api error: "+e.getMessage());
				runOnUiThread(showToast);
			}
			return results;
		}
		
		URI buildURI(String from, String to, String phone, String subject, String body) throws URISyntaxException{
			ArrayList<NameValuePair> args = new ArrayList<NameValuePair>();
			args.add(new BasicNameValuePair(Constants.API_ENDPOINT_ARG, Constants.API_ENDPOINT_CONTACT));
			args.add(new BasicNameValuePair(Constants.API_CONTACT_ARGS_TO, to));
			args.add(new BasicNameValuePair(Constants.API_CONTACT_ARGS_FROM, from));
			args.add(new BasicNameValuePair(Constants.API_CONTACT_ARGS_PHONE, phone));
			args.add(new BasicNameValuePair(Constants.API_CONTACT_ARGS_SUBJECT, subject));
			args.add(new BasicNameValuePair(Constants.API_CONTACT_ARGS_BODY, body));
			return URIUtils.createURI("http", Constants.API_BASE_URL, -1, Constants.API_ENDPOINT_URL, URLEncodedUtils.format(args, "UTF-8"), null);
		}
		
		boolean parseJSON(String response){
			try {
				JSONObject jsonObject = new JSONObject(response);
				int status = jsonObject.getInt("status");
				return (status == 200) ? true : false;
			} catch (JSONException e) {
				loadingError = true;
				Log.e(LOG_TAG, "json parse error! response: "+response+" error:"+e.getCause());
				return false;
			}
		}

		@Override
		protected void onPostExecute(String results) {
			super.onPostExecute(results);
			if (pDlg != null)
				pDlg.dismiss();
			
			if(!loadingError & parseJSON(results)){
				txt_status.setVisibility(View.VISIBLE);
				section_content.setVisibility(View.GONE);
			}else{
				toast("Sorry we're unable to send your message. Please try again later");
			}
		}
	}	
}
