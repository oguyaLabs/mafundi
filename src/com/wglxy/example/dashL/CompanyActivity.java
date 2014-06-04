package com.wglxy.example.dashL;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.TextView;

public class CompanyActivity extends  DashboardActivity {

	private static final String LOG_TAG = "CompanyActivity";
	private ImageView img_profpic;
	private TextView txt_company_name;
	private RatingBar rb_ratings;
	private ListView servicesList;
	private Button btn_contact;
	private Button btn_reviews;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_company);
		setTitleFromActivityLabel(R.id.title_text);
		//get args data
		getArgs();
		
		initViews();
		
		setData();
	}
	
	void getArgs(){
		Bundle args = getIntent().getExtras();
	}
	
	void initViews(){
		img_profpic = (ImageView)findViewById(R.id.img_profpic);
		txt_company_name = (TextView)findViewById(R.id.txt_company_name);
		rb_ratings = (RatingBar)findViewById(R.id.rb_ratings);
		servicesList = (ListView)findViewById(R.id.servicesList);
		btn_contact = (Button)findViewById(R.id.btn_contact);
		btn_reviews = (Button)findViewById(R.id.btn_reviews);
		
		btn_contact.setOnClickListener(clickListener);
		btn_reviews.setOnClickListener(clickListener);
	}
	
	View.OnClickListener clickListener = new View.OnClickListener() {
		
		@Override
		public void onClick(View view) {
			// TODO Auto-generated method stub
			switch(view.getId()){
			case R.id.btn_contact:	//show contact
				break;
				
			case R.id.btn_reviews:	//show reviews
				break;
			}
		}
	};
	
	
	void setData(){
		//dummy data
		String[] data = new String[10];
		for(int i=0;i<10;i++) data[i] = "After Party Cleaning"; 
		servicesList.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, data));
	}
	
	
	
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
