package com.wglxy.example.dashL;

import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
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

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.wglxy.example.dashL.constants.Constants;
import com.wglxy.example.dashL.model.Review;
import com.wglxy.example.dashL.model.User;
import com.wglxy.example.dashL.net.ImageDownloader;
import com.wglxy.example.dashL.net.NetHandler;

public class CompanyActivity extends DashboardActivity {

	private static final String LOG_TAG = "CompanyActivity";
	private ImageView img_profpic;
	private TextView txt_company_name;
	private TextView txt_company_location;
	private TextView txt_no_reviews;
	private RatingBar rb_ratings;
	private ListView servicesList;
	private Button btn_contact;
	private Button btn_reviews;

	private ListView reviewsList;
	private View section_content;
	private View section_reviews;
	private ProgressDialog pDlg;

	private int companyID;
	private boolean loadingError = false;

	private User company = new User();
	private ArrayList<Review> reviews = new ArrayList<Review>();

	private static final String KEY_USER_OBJ = "company";
	private static final String KEY_USER_ID = "userID";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_company);
		setTitleFromActivityLabel(R.id.title_text);
		showLogginLogout(findViewById(R.id.btn_login_logout));

		company = (savedInstanceState != null) ? (User) savedInstanceState
				.getParcelable(KEY_USER_OBJ) : new User();
		companyID = (savedInstanceState != null) ? savedInstanceState
				.getInt(KEY_USER_ID) : 0;

		// get args data
		getArgs();

		initViews();

		setData();
	}

	void getArgs() {
		try {
			Bundle args = getIntent().getExtras();
			companyID = args.getInt(Constants.KEY_COMPANY_ARGS);
			company = args.getParcelable(Constants.KEY_COMPANY_BUNDLE_ARGS);
		} catch (Exception ex) {
			Log.e(LOG_TAG, "companyID is null", ex.getCause());
			super.onBackPressed();
		}
	}

	void initViews() {
		img_profpic = (ImageView) findViewById(R.id.img_profpic);
		txt_company_name = (TextView) findViewById(R.id.txt_company_name);
		txt_company_location = (TextView) findViewById(R.id.txt_company_location);
		txt_no_reviews = (TextView) findViewById(R.id.txt_no_reviews);
		rb_ratings = (RatingBar) findViewById(R.id.rb_ratings);
		servicesList = (ListView) findViewById(R.id.servicesList);
		btn_contact = (Button) findViewById(R.id.btn_contact);
		btn_reviews = (Button) findViewById(R.id.btn_reviews);

		btn_contact.setOnClickListener(clickListener);
		btn_reviews.setOnClickListener(clickListener);

		reviewsList = (ListView) findViewById(R.id.reviewsList);
		section_content = (RelativeLayout) findViewById(R.id.section_content);
		section_reviews = (RelativeLayout) findViewById(R.id.section_reviews);

		pDlg = new ProgressDialog(CompanyActivity.this);
		pDlg.setMessage("Please wait...");
		pDlg.setIndeterminate(true);
		pDlg.setCancelable(false);
	}

	View.OnClickListener clickListener = new View.OnClickListener() {
		@Override
		public void onClick(View view) {
			switch (view.getId()) {
			case R.id.btn_contact: // show contact
				Bundle args = new Bundle();
				args.putInt(Constants.KEY_COMPANY_ARGS, companyID);
				args.putParcelable(Constants.KEY_COMPANY_BUNDLE_ARGS, company);
				Intent contactIntent = new Intent(CompanyActivity.this,
						ContactCompanyActivity.class);
				contactIntent.putExtras(args);
				startActivity(contactIntent);
				break;
			case R.id.btn_reviews: // show reviews
				toggleReviews(true);
				if (reviews.size() > 0)
					return;
				try {
					loadReviews();
					if (reviews.size() <= 0 && !loadingError) {
						reviewsList.setVisibility(View.GONE);
						txt_no_reviews.setVisibility(View.VISIBLE);
						loadingError = false;
					} else if (reviews.size() > 0 && !loadingError) {
						reviewsList.setAdapter(new ArrayAdapter<String>(
								CompanyActivity.this,
								android.R.layout.simple_list_item_1,
								getSubject(reviews)));
						loadingError = false;
					} else {
						toast("Something's not right! Please try again later.");
					}
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					toast("Something's not right! Please try again later.");
				} catch (ExecutionException e) {
					// TODO Auto-generated catch block
					toast("Something's not right! Please try again later.");
				}
				break;
			}
		}
	};

	void toggleReviews(boolean show) {
		if (show) {
			section_reviews.setVisibility(View.VISIBLE);
			section_content.setVisibility(View.GONE);
		} else {
			section_reviews.setVisibility(View.GONE);
			section_content.setVisibility(View.VISIBLE);
		}
	}

	void loadReviews() throws InterruptedException, ExecutionException {
		String results = new NetOps().execute(
				new String[] { String.valueOf(company.getId()) }).get();
		try {
			reviews = parseJSON(results);
		} catch (JSONException e) {
			e.printStackTrace();
			loadingError = true;
			toast("Something's not right! Please try again later.");
		}
	}

	ArrayList<Review> parseJSON(String jsonResults) throws JSONException {
		ArrayList<Review> reviews = new ArrayList<Review>();
		JSONObject jsonObject = new JSONObject(jsonResults);
		int status = jsonObject.getInt("status");
		int numReviews = jsonObject.getInt("numReviews");

		if (status != 200 || numReviews < 0)
			return reviews;

		JSONArray arr = jsonObject.getJSONArray("reviews");
		for (int i = 0; i < arr.length(); i++) {
			JSONObject result = arr.getJSONObject(i);
			Review review = new Review();
			String from = result.getString("from");
			String to = result.getString("to");
			String subject = result.getString("subject");
			int userID = result.getInt("user_id");
			review.setFrom(from);
			review.setTo(to);
			review.setSubject(subject);
			review.setUserID(userID);

			reviews.add(review);
		}
		return reviews;
	}

	String[] getSubject(ArrayList<Review> reviews) {
		String[] subjects = new String[reviews.size()];
		for (int i = 0; i < reviews.size(); i++)
			subjects[i] = reviews.get(i).getSubject();
		return subjects;
	}

	void setData() {
		String url = company.getImage();
		Bitmap def_logo = BitmapFactory.decodeResource(CompanyActivity.this.getResources(), R.drawable.car_repair);
		Bitmap logo = null;
		try {
			logo = new ImageDownloader(img_profpic, url).execute().get();
		} catch (InterruptedException e) {
			Log.e(LOG_TAG, "imageDownload error:"+e);
		} catch (ExecutionException e) {
			Log.e(LOG_TAG, "imageDownload error:"+e);
		}
		
		img_profpic.setImageBitmap(logo != null ?  logo : def_logo );
		String business_name = TextUtils.isEmpty(company.getBusiness_name()) ? company
				.getFirst_name() + " " + company.getLast_name()
				: company.getBusiness_name();
		txt_company_name.setText(business_name);
		txt_company_location.setText(company.getAddress());
		rb_ratings.setRating(company.getStars());

		servicesList.setAdapter(new ArrayAdapter<String>(this,
				android.R.layout.simple_list_item_1, company.getServices()
						.split(",")));
	}

	String[] getServices(String arrString) {
		return TextUtils.isEmpty(arrString) ? arrString.split(",")
				: new String[0];
	}

	@Override
	public void onBackPressed() {
		if (section_reviews.getVisibility() == View.VISIBLE) {
			toggleReviews(false);
		} else
			super.onBackPressed();
	}

	@Override
	protected void onPause() {
		super.onPause();
	}

	@Override
	protected void onResume() {
		super.onResume();
	}

	@Override
	protected void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		outState.putParcelable(KEY_USER_OBJ, company);
		outState.putInt(KEY_USER_ID, companyID);
	}

	@Override
	protected void onRestoreInstanceState(Bundle savedInstanceState) {
		super.onRestoreInstanceState(savedInstanceState);
		company = (savedInstanceState != null) ? (User) savedInstanceState
				.getParcelable(KEY_USER_OBJ) : new User();
		companyID = (savedInstanceState != null) ? savedInstanceState
				.getInt(KEY_USER_ID) : 0;
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
			if (pDlg != null)
				pDlg.show();
		}

		@Override
		protected String doInBackground(String... params) {
			String companyID = params[0];

			String results = null;
			try {
				URI uri = buildURI(companyID);
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

		URI buildURI(String companyID) throws URISyntaxException {
			ArrayList<NameValuePair> args = new ArrayList<NameValuePair>();
			args.add(new BasicNameValuePair(Constants.API_ENDPOINT_ARG,
					Constants.API_ENDPOINT_REVIEWS));
			args.add(new BasicNameValuePair(
					Constants.API_REVIEWS_ARGS_COMPANYID, companyID));
			return URIUtils.createURI("http", Constants.API_BASE_URL, -1,
					Constants.API_ENDPOINT_URL,
					URLEncodedUtils.format(args, "UTF-8"), null);
		}

		@Override
		protected void onPostExecute(String results) {
			super.onPostExecute(results);
			if (pDlg != null)
				pDlg.dismiss();
		}
	}
}
