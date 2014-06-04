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



import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import android.view.View;
import android.view.View.OnClickListener;

import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;



public class ElectricianCall extends DashboardActivity{
	InputStream inputStream;
	HttpClient httpclient;
	HttpPost httppost;
	String [] spliteddata;
    List<NameValuePair> nameValuePairs;
    StringBuffer sb;
    public String line, result, number,phone;
    byte[] data;
    public TextView description;
    Button sendsms,sendemail;
  
    String health =null;

	@Override
	protected void onCreate(Bundle savedInstanceState){
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.electrician_call);
	      	
		        Button btButton=(Button)findViewById(R.id.btncall);
		        Button sendsms=(Button)findViewById(R.id.SendSMS);
		  
		        
					description=(TextView)findViewById(R.id.description);
					sendsms.setOnClickListener(new OnClickListener() {
						   public void onClick(View arg0) {
								Intent i = new Intent(getApplicationContext(),SendSMSActivity.class);
					          startActivity(i);
					       }
						 });
				     Bundle bundle = getIntent().getExtras();
					final String setClinic = bundle.getString("selectedname");
					
					//final String setClini = bundle.getString("a");
					//Toast.makeText(Description.this, "Your Choose"+" "+setClini, Toast.LENGTH_LONG).show();
					
			//send to remote server fetch disease details list
		    try {
		        httpclient = new DefaultHttpClient();
		        httppost = new HttpPost("http://10.0.2.2/mafundidatabase/electrician_details");
		         // Add your data
		          nameValuePairs = new ArrayList<NameValuePair>(4);
		          nameValuePairs.add(new BasicNameValuePair("info",setClinic));
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
		           spliteddata = result.split("#");
				   description.append("id: "+spliteddata[0]+"\n\nphone: "+spliteddata[1]+"\n\nviews: "+spliteddata[2]+"\n\n"+"about:"+spliteddata[3]+"location:"+spliteddata[4]);
		        	   Toast.makeText(getApplicationContext(), spliteddata[0], Toast.LENGTH_LONG).show();
		        	   btButton.setText("Call"+spliteddata[0]+ "Now");
		    }
		           catch (Exception e)
		           {
		               Toast.makeText(getApplicationContext(), "Error inside set:"+e.toString(), Toast.LENGTH_LONG).show();
		           }
		           
			}
	
			public void  clicktocall(View v)
			{
				Intent intent;
				
				intent = new Intent(Intent.ACTION_CALL,Uri.parse("tel:"+spliteddata[1]));
				startActivity(intent);
			
			
		

		}
}


	
	