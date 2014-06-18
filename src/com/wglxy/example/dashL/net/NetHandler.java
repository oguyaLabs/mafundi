package com.wglxy.example.dashL.net;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;


import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.util.Log;

public class NetHandler extends AsyncTask<String, Void, String> {
	static final String LOG_TAG = "NetHandler";
	
	private Activity activity;
	private ProgressDialog pDlg;

	public NetHandler() {
		// TODO Auto-generated constructor stub
	}

	public String callAPI(URI uri) throws ClientProtocolException, IOException{
		InputStream inputStream;
		HttpClient httpClient;
		HttpPost httpPost = new HttpPost(uri);
		httpClient = new DefaultHttpClient();
		Log.e(LOG_TAG, "API URL: "+httpPost.getURI());
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
	protected String doInBackground(String... params) {
		// TODO Auto-generated method stub
		return null;
	}

}
