/*
 * Copyright (C) 2011 Wglxy.com
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.wglxy.example.dashL;


import java.util.ArrayList;

import com.wglxy.example.dashL.adapters.CompanyListAdapter;
import com.wglxy.example.dashL.adapters.CompanyListAdapter.Inflater;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.AdapterView.OnItemClickListener;

/**
 * This is the activity for feature 6 in the dashboard application.
 * It displays some text and provides a way to get back to the home activity.
 *
 */

public class F6Activity extends DashboardActivity implements OnItemClickListener {

	/**
	 * onCreate
	 * 
	 * Called when the activity is first created. This is where you should do
	 * all of your normal static set up: create views, bind data to lists, etc.
	 * This method also provides you with a Bundle containing the activity's
	 * previously frozen state, if there was one.
	 * 
	 * Always followed by onStart().
	 * 
	 * @param savedInstanceState
	 *            Bundle
	 */
	
	private CompanyListAdapter adapter;
	private ListView listView;
	

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_f4);
		setTitleFromActivityLabel(R.id.title_text);
		
		listView = (ListView)findViewById(R.id.featureResultsList);
		listView.setOnItemClickListener(this);
		
		//dummy data 
		ArrayList<String> data = new ArrayList<String>();
		for(int i=0; i<10; i++) data.add("");
		
		CompanyListAdapter.Inflater inflater = new Inflater(6);
		
		adapter = new CompanyListAdapter(inflater, data);
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

@Override
public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
	// TODO Auto-generated method stub
	
}

} // end class
