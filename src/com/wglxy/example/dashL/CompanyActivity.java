package com.wglxy.example.dashL;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class CompanyActivity extends  DashboardActivity {

	private static final String LOG_TAG = "CompanyActivity";
	private ImageView img_profpic;
	private TextView txt_company_name;
	private RatingBar rb_ratings;
	private ListView servicesList;
	private Button btn_contact;
	private Button btn_reviews;
	
	private ListView reviewsList;
	private View section_content;
	private View section_reviews;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_company);
		setTitleFromActivityLabel(R.id.title_text);
		showLogginLogout(findViewById(R.id.btn_login_logout));
		
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
		
		reviewsList = (ListView)findViewById(R.id.reviewsList);
		section_content = (RelativeLayout)findViewById(R.id.section_content);
		section_reviews = (RelativeLayout)findViewById(R.id.section_reviews);
	}
	
	View.OnClickListener clickListener = new View.OnClickListener() {
		
		@Override
		public void onClick(View view) {
			// TODO Auto-generated method stub
			switch(view.getId()){
			case R.id.btn_contact:	//show contact
				//TODO add company ID args
				Bundle args = new Bundle();
				Intent contactIntent = new Intent(CompanyActivity.this, ContactCompanyActivity.class);
				startActivity(contactIntent);
				break;
				
			case R.id.btn_reviews:	//show reviews
				toggleReviews(true);
				break;
			}
		}
	};
	
	void toggleReviews(boolean show){
		if(show){
			section_reviews.setVisibility(View.VISIBLE);
			section_content.setVisibility(View.GONE);
		}else{
			section_reviews.setVisibility(View.GONE);
			section_content.setVisibility(View.VISIBLE);
		}
	}
	
	void setData(){
		//dummy data
		String[] data = new String[10];
		for(int i=0;i<10;i++) data[i] = "After Party Cleaning"; 
		servicesList.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, data));
		reviewsList.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, data));
		
	}
	
	@Override
	public void onBackPressed(){
		
		if(section_reviews.getVisibility() == View.VISIBLE){
			toggleReviews(false);
		}else super.onBackPressed();
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
