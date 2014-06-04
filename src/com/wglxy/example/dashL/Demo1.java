package com.wglxy.example.dashL;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;


public class Demo1  extends DashboardActivity{
	
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.demo1);
         
       //handle next button
        ImageButton next_level = (ImageButton) findViewById(R.id.imageButton1);        
        //Listening to button event
        next_level.setOnClickListener(new View.OnClickListener() { 
            public void onClick(View arg0) {
                //Starting a new Intent
            	Intent play = new Intent(getApplicationContext(), Demo2.class);              	
             	startActivity(play);  
            }
        });
	}
}
