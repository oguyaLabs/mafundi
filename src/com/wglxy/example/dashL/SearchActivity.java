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

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

import com.wglxy.example.dashL.adapters.ViewInflaterBaseAdapter;
import com.wglxy.example.dashL.constants.Constants;

/**
 * This is the Search activity in the dashboard application. It displays some
 * text and provides a way to get back to the home activity.
 * 
 */

public class SearchActivity extends DashboardActivity implements OnItemClickListener {

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
	
	private static final String LOG_TAG = "SearchActivity";
	
	SearchListAdapter adapter;
    private ListView listView;
    private String searchStr;

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_search);
		setTitleFromActivityLabel(R.id.title_text);
		

		try{
			Bundle args = getIntent().getExtras();
			searchStr = args.getString(Constants.KEY_SEARCH_ARGS);
		}catch(Exception ex){
			Log.e(LOG_TAG, "no search args: "+ex.getMessage());
		}
		
		
		listView = (ListView)findViewById(R.id.searchResultsList);
		listView.setOnItemClickListener(this);
		
		//dummy data 
		ArrayList<String> data = new ArrayList<String>();
		for(int i=0; i<10; i++) data.add("");
		
		adapter = new SearchListAdapter(new Inflater(), data);
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


	// inflater
	public class Inflater implements ViewInflaterBaseAdapter.ViewInflater {

		private class ViewHolder {

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
				// viewHolder.section_media =
				// (LinearLayout)rowView.findViewById(R.id.section_media);
				// viewHolder.img_profpic = (ImageView)
				// rowView.findViewById(R.id.img_profpic);

				rowView.setTag(viewHolder);
			}

			ViewHolder viewHolder = (ViewHolder) rowView.getTag();

			// TODO set data
			// viewHolder.img_profpic;
			// viewHolder.img_mediaPic;
			// viewHolder.img_MarkText;
			// viewHolder.txt_sender_message;
			// viewHolder.txt_sender_name;
			// viewHolder.txt_sender_phone;
			// viewHolder.txt_sender_time;
			// viewHolder.txt_comment_count;
			// viewHolder.txt_message_type;

			// dummy stuff TODO remove this

			// TODO format dates, set Fonts

			return rowView;
		}

		int getRandomNo() {
			return new Random(200719293).nextInt();
		}
	}

	// list adapter
	public class SearchListAdapter extends ViewInflaterBaseAdapter<String> {

		public SearchListAdapter(ViewInflater inflater, ArrayList<String> data) {
			super(inflater, data);
			super.setInflater(inflater);
		}
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		
		//TODO get company ID/details
		Bundle args = new Bundle();
		Intent companyIntent = new Intent(SearchActivity.this, CompanyActivity.class);
		companyIntent.putExtras(args);
		startActivity(companyIntent);
	}
}
