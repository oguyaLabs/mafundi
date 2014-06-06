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
import java.util.Random;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.wglxy.example.dashL.adapters.ViewInflaterBaseAdapter;

/**
 * This is the activity for feature 2 in the dashboard application. It displays
 * some text and provides a way to get back to the home activity.
 * 
 */

public class F2Activity extends DashboardActivity implements OnItemClickListener {

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

	ListAdapter adapter;
    private ListView listView;
	
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_f2);
		setTitleFromActivityLabel(R.id.title_text);
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
			viewHolder.img_biz_pic.setImageDrawable(parent.getContext().getResources().getDrawable(R.drawable.car_repair));
			viewHolder.txt_biz_name.setText("Quick Fix Garage");
			viewHolder.txt_biz_loc.setText("Ngara");
//			viewHolder.rb_ratings.setRating();

			return rowView;
		}

	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		// TODO Auto-generated method stub
		
	}
	


} // end class
