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
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.wglxy.example.dashL.adapters.ViewInflaterBaseAdapter;
import com.wglxy.example.dashL.adapters.ViewInflaterBaseAdapter.ViewInflater;


public class F1Activity  extends DashboardActivity implements OnItemClickListener{
	
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
    
    ListView listView;
    ListAdapter adapter;

	public void onCreate(Bundle savedInstanceState) {
		
		
		 super.onCreate(savedInstanceState);
	      setContentView(R.layout.electricians);
	   myListView= (ListView)this.findViewById(android.R.id.list);
	      //Spinner spin=(Spinner) findViewById(R.id.spinner);
		
		listView = (ListView)findViewById(R.id.featureResultsList);
		listView.setOnItemClickListener(this);
		
		//dummy data 
		ArrayList<String> data = new ArrayList<String>();
		for(int i=0; i<10; i++) data.add("");
		
		adapter = new ListAdapter(new Inflater(), data);
		listView.setAdapter(adapter);
	}
	
   @Override
   public void onPause(){
       super.onPause();
   }

   @Override
   public void onResume(){
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

	
	
	// list adapter
	public class ListAdapter extends ViewInflaterBaseAdapter<String> {

		public ListAdapter(ViewInflater inflater, ArrayList<String> data) {
			super(inflater, data);
			super.setInflater(inflater);
		}
	}
	
	// inflater
	public class Inflater implements ViewInflaterBaseAdapter.ViewInflater {

		private class ViewHolder {
			ImageView img_biz_pic;
			TextView txt_biz_name;
			TextView txt_biz_loc;
			RatingBar rb_ratings;

		}

		@Override
		public View inflate(ViewInflaterBaseAdapter adapter, int pos,
				View ConvertView, ViewGroup parent) {
			LayoutInflater inflater = LayoutInflater.from(parent.getContext());
			View rowView = ConvertView;

			if (rowView == null) {
				rowView = inflater.inflate(R.layout.list_item_search, parent,
						false);

				ViewHolder viewHolder = new ViewHolder();
				viewHolder.img_biz_pic = (ImageView)rowView.findViewById(R.id.img_biz_pic);
				viewHolder.txt_biz_name = (TextView)rowView.findViewById(R.id.txt_biz_name);
				viewHolder.txt_biz_loc = (TextView)rowView.findViewById(R.id.txt_biz_loc);
				viewHolder.rb_ratings = (RatingBar)rowView.findViewById(R.id.rb_ratings);
				
				rowView.setTag(viewHolder);
			}

			ViewHolder viewHolder = (ViewHolder) rowView.getTag();

			// TODO set data
			viewHolder.img_biz_pic.setImageDrawable(parent.getContext().getResources().getDrawable(R.drawable.electronics));
			viewHolder.txt_biz_name.setText("HomeTide Electronics");
			viewHolder.txt_biz_loc.setText("ABC Place, Westlands");
//			viewHolder.rb_ratings.setRating();

			return rowView;
		}

	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		// TODO Auto-generated method stub
		
	}
	

	
	private class NetOps{
		
		void tmp_func(){
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
//			   			for(int i=0; i < num.length; i++)
//			   			{
//			   				items.add(num[i]);
//			   				
			   				int  layoutID= R.layout.listdesigner;
			   				aa= new ArrayAdapter<String>(F1Activity.this, layoutID, Splittedclinics);
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

	}

