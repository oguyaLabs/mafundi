package com.wglxy.example.dashL;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;


import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;



import android.app.Activity;
import android.content.Intent;

import android.os.Bundle;
import android.os.StrictMode;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import android.widget.Toast;


public class F1Activity  extends DashboardActivity{
	
	InputStream inputStream;
	HttpClient httpclient;
	HttpPost httppost;
    List<NameValuePair> nameValuePairs;
    StringBuffer sb;
    String line, result, number;
    String health =null;
    byte[] data;
    ArrayAdapter<?> aa;
    ListView myListView;

	public void onCreate(Bundle savedInstanceState) {
		
		
		 super.onCreate(savedInstanceState);
	      setContentView(R.layout.electricians);
	   myListView= (ListView)this.findViewById(android.R.id.list);
	      //Spinner spin=(Spinner) findViewById(R.id.spinner);
	      StrictMode.ThreadPolicy policy = new
	    		  StrictMode.ThreadPolicy.Builder()
	    		  .permitAll().build();
	    		  StrictMode.setThreadPolicy(policy);
		
		try {
	           httpclient = new DefaultHttpClient();
	           
	                httppost = new HttpPost("http://10.0.2.2/mafundidatabase/electrician.php");
	                // Add your data
		             nameValuePairs = new ArrayList<NameValuePair>(4);
		             nameValuePairs.add(new BasicNameValuePair("selectedname", health));
		        	                   
		             httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));  
		             
		              // Execute HTTP Post Request
		             HttpResponse response = httpclient.execute(httppost);
		              
		              inputStream = response.getEntity().getContent();
		              
		              BufferedReader rd = new BufferedReader(new InputStreamReader(inputStream), 4096);
		              String line;
		              StringBuilder sb =  new StringBuilder();
		              
		              while ((line = rd.readLine()) != null) {
		              		sb.append(line);
		              }
		              rd.close();
		              result = sb.toString();
		              
		              inputStream.close();
		              
		              final String [] Splittedclinics = result.split("#");
//		   			for(int i=0; i < num.length; i++)
//		   			{
//		   				items.add(num[i]);
//		   				
		   				int  layoutID= R.layout.listdesigner;
		   				aa= new ArrayAdapter<String>(this, layoutID, Splittedclinics);
		   				myListView.setAdapter(aa);
		   				myListView.setOnItemClickListener(new OnItemClickListener() {
			   		
						

							public void onItemClick(AdapterView<?> arg0, View arg1,
									int pos, long arg3) {
								
								
								String keyword = Splittedclinics[pos];
	                            //String email="yess";
								Intent intent = new Intent(F1Activity.this, ElectricianCall.class);
								Bundle bundle = new Bundle();
								bundle.putString("selectedname", keyword);	//keyword of the value of the name selected	
								//bundle.putString("a", email); //to move with email
								intent.putExtras(bundle);
								startActivity(intent);
								
								
								
								
							}});
		   	
		   			
		       }
		              catch (Exception e)
		              {
		                  Toast.makeText(getApplicationContext(), "Error inside set:"+e.toString(), Toast.LENGTH_LONG).show();
		              }

		}


	}

