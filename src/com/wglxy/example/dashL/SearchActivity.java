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

import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIUtils;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RatingBar;
import android.widget.TextView;

import com.wglxy.example.dashL.adapters.ViewInflaterBaseAdapter;
import com.wglxy.example.dashL.constants.Constants;
import com.wglxy.example.dashL.model.User;
import com.wglxy.example.dashL.net.NetHandler;

/**
 * This is the Search activity in the dashboard application. It displays some
 * text and provides a way to get back to the home activity.
 * 
 */

public class SearchActivity extends DashboardActivity implements
		OnItemClickListener {

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
	private ProgressBar loading;
	private TextView empty_results;

	private View searchBar;
	private EditText edt_search;
	private Button btn_search;

	private ArrayList<User> data = new ArrayList<User>();;
	private boolean loadingError = false;

	private static final String KEY_USER_OBJ = "company";
	private static final String KEY_SEARCH_QUERY = "query";

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_search);
		setTitleFromActivityLabel(R.id.title_text);
		showLogginLogout(findViewById(R.id.btn_login_logout));

		initUI();
		if (savedInstanceState != null
				&& savedInstanceState.containsKey(KEY_SEARCH_QUERY)
				&& savedInstanceState.containsKey(KEY_USER_OBJ)) {
			searchStr = savedInstanceState.getString(KEY_SEARCH_QUERY);
			data = savedInstanceState.getParcelableArrayList(KEY_USER_OBJ);
		}

		try {
			Bundle args = getIntent().getExtras();
			searchStr = args.getString(Constants.KEY_SEARCH_ARGS);
			Log.e(LOG_TAG, "search query: " + searchStr);
			doSearch(searchStr);
		} catch (Exception ex) {
			Log.e(LOG_TAG, "no search args", ex.getCause());
			searchBar.setVisibility(View.VISIBLE);
			edt_search.requestFocus();
		}

	}

	void initUI() {
		listView = (ListView) findViewById(R.id.searchResultsList);
		listView.setOnItemClickListener(this);
		loading = (ProgressBar) findViewById(R.id.loading);
		empty_results = (TextView) findViewById(R.id.empty_results);
		searchBar = (LinearLayout) findViewById(R.id.search_bar);
		edt_search = (EditText) findViewById(R.id.edt_search);
		btn_search = (Button) findViewById(R.id.btn_search);

		btn_search.setOnClickListener(clickListener);
		edt_search.addTextChangedListener(watcher);
	}

	TextWatcher watcher = new TextWatcher() {
		@Override
		public void onTextChanged(CharSequence s, int start, int before,
				int count) {
		}

		@Override
		public void beforeTextChanged(CharSequence s, int start, int count,
				int after) {
		}

		@Override
		public void afterTextChanged(Editable editable) {
			if (edt_search.getText().length() > 3) {
				btn_search.setEnabled(true);
			} else {
				btn_search.setEnabled(false);
			}
		}
	};

	View.OnClickListener clickListener = new View.OnClickListener() {
		@Override
		public void onClick(View view) {
			switch (view.getId()) {
			case R.id.btn_search:
				String query = edt_search.getText().toString();
				if (query.length() > 3) {
					doSearch(query);
				}
				break;
			default:
				break;
			}
		}
	};

	void setListData() {
		adapter = new SearchListAdapter(new Inflater(), data);
		listView.setAdapter(adapter);
	}

	void doSearch(String queryStr) {
		String[] args = { queryStr };
		try {
			String results = new NetOps().execute(args).get();
			data = parseJSON(results);
			if (data.size() != 0 && !loadingError) {
				setListData();
			} else {
				toast("Sorry, no results found matching your search criteria");
				empty_results.setVisibility(View.VISIBLE);
			}
		} catch (InterruptedException e) {
			loadingError = true;
			toast("something's not right! Please try again later.");
		} catch (ExecutionException e) {
			loadingError = true;
			toast("something's not right! Please try again later.");
		} catch (JSONException e) {
			loadingError = true;
			toast("something's not right! Please try again later.");
		}
	}

	ArrayList<User> parseJSON(String results) throws JSONException {
		ArrayList<User> list = new ArrayList<User>();
		JSONObject jsonResult = new JSONObject(results);
		int status = jsonResult.getInt("status");
		int numResults = jsonResult.getInt("numResults");

		if (status != 200 && numResults <= 0)
			return list;
		else {
			JSONArray arr = jsonResult.getJSONArray("results");
			Log.e(LOG_TAG, "results: " + arr.length());

			for (int i = 0; i < arr.length(); i++) {
				JSONObject jsonObject = arr.getJSONObject(i);
				User user = new User();
				int id = jsonObject.getInt("id");
				String email = jsonObject.getString("email");
				String first_name = jsonObject.getString("first_name");
				String last_name = jsonObject.getString("last_name");
				String business_name = jsonObject.getString("business_name");
				String services = jsonObject.getString("services");
				String address = jsonObject.getString("address");
				int stars = jsonObject.getInt("stars");

				user.setId(id);
				user.setEmail(email);
				user.setFirst_name(first_name);
				user.setLast_name(last_name);
				user.setBusiness_name(business_name);
				user.setAddress(address);
				user.setServices(services);
				user.setStars(stars);

				list.add(user);
			}
			return list;
		}
	}

	@Override
	public void onPause() {
		super.onPause();
	}

	@Override
	public void onResume() {
		super.onResume();
	}

	@Override
	protected void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		storeTemp();
		outState.putString(KEY_SEARCH_QUERY, searchStr);
		outState.putParcelableArrayList(KEY_USER_OBJ, data);
	}

	@Override
	protected void onRestoreInstanceState(Bundle savedInstanceState) {
		super.onRestoreInstanceState(savedInstanceState);
		if (savedInstanceState != null
				&& savedInstanceState.containsKey(KEY_SEARCH_QUERY)
				&& savedInstanceState.containsKey(KEY_USER_OBJ)) {
			searchStr = savedInstanceState.getString(KEY_SEARCH_QUERY);
			data = savedInstanceState.getParcelableArrayList(KEY_USER_OBJ);
		}
	}

	void storeTemp() {
		searchStr = searchBar.getVisibility() == View.VISIBLE ? edt_search
				.getText().toString() : searchStr;
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
				viewHolder.img_biz_pic = (ImageView) rowView
						.findViewById(R.id.img_biz_pic);
				viewHolder.txt_biz_name = (TextView) rowView
						.findViewById(R.id.txt_biz_name);
				viewHolder.txt_biz_loc = (TextView) rowView
						.findViewById(R.id.txt_biz_loc);
				viewHolder.rb_ratings = (RatingBar) rowView
						.findViewById(R.id.rb_ratings);

				rowView.setTag(viewHolder);
			}
			ViewHolder viewHolder = (ViewHolder) rowView.getTag();
			// viewHolder.img_biz_pic
			// TODO add imageloader

			String business_name = TextUtils.isEmpty(data.get(pos)
					.getBusiness_name()) ? data.get(pos).getFirst_name() + " "
					+ data.get(pos).getLast_name() : data.get(pos)
					.getBusiness_name();

			viewHolder.txt_biz_name.setText(business_name);
			viewHolder.txt_biz_loc.setText(data.get(pos).getAddress());
			viewHolder.rb_ratings.setRating(data.get(pos).getStars());
			return rowView;
		}
	}

	// list adapter
	public class SearchListAdapter extends ViewInflaterBaseAdapter<User> {

		public SearchListAdapter(ViewInflater inflater, ArrayList<User> data) {
			super(inflater, data);
			super.setInflater(inflater);
		}
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {

		// TODO get company ID/details
		Bundle args = new Bundle();
		args.putParcelable(Constants.KEY_COMPANY_BUNDLE_ARGS,
				data.get(position));
		Intent companyIntent = new Intent(SearchActivity.this,
				CompanyActivity.class);
		companyIntent.putExtras(args);
		startActivity(companyIntent);
	}

	class NetOps extends AsyncTask<String, Void, String> {
		InputStream inputStream;
		HttpClient httpclient;
		HttpPost httppost;
		List<NameValuePair> nameValuePairs;
		StringBuffer sb;

		Runnable showToast = new Runnable() {
			@Override
			public void run() {
				// TODO Auto-generated method stub
				toast("Unable to connect to the internet");
			}
		};

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			loading.setVisibility(View.VISIBLE);
			empty_results.setVisibility(View.GONE);
			data.clear();
			// if (pDlg != null)
			// pDlg.show();
		}

		@Override
		protected String doInBackground(String... params) {
			String searchStr = params[0];

			String results = null;
			try {
				URI uri = buildURI(searchStr);
				results = new NetHandler().callAPI(uri);
				Log.e(LOG_TAG, "result:" + results);
			} catch (ClientProtocolException e) {
				loadingError = true;
				Log.e(LOG_TAG, "Api error: " + e.getMessage());
				runOnUiThread(showToast);
			} catch (IOException e) {
				loadingError = true;
				Log.e(LOG_TAG, "Api error: " + e.getMessage());
				runOnUiThread(showToast);
			} catch (URISyntaxException e) {
				loadingError = true;
				Log.e(LOG_TAG, "Api error: " + e.getMessage());
				runOnUiThread(showToast);
			}
			return results;
		}

		URI buildURI(String searchStr) throws URISyntaxException {
			ArrayList<NameValuePair> args = new ArrayList<NameValuePair>();
			args.add(new BasicNameValuePair(Constants.API_ENDPOINT_ARG,
					Constants.API_ENDPOINT_SEARCH));
			args.add(new BasicNameValuePair(Constants.API_SEARCH_ARGS_QUERY,
					searchStr));
			return URIUtils.createURI("http", Constants.API_BASE_URL, -1,
					Constants.API_ENDPOINT_URL,
					URLEncodedUtils.format(args, "UTF-8"), null);
		}

		@Override
		protected void onPostExecute(String results) {
			super.onPostExecute(results);
			loading.setVisibility(View.GONE);
			// if (pDlg != null)
			// pDlg.dismiss();
		}

	}
}
