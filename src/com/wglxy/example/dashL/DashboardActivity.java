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



import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.wglxy.example.dashL.model.User;
import com.wglxy.example.dashL.utils.PrefUtils;

/**
 * This is the base class for activities in the dashboard application.
 * It implements methods that are useful to all top level activities.
 * That includes: (1) stub methods for all the activity lifecycle methods;
 * (2) onClick methods for clicks on home, search, feature 1, feature 2, etc.
 * (3) a method for displaying a message to the screen via the Toast class.
 *
 */

public abstract class DashboardActivity extends Activity 
{

/**
 * onCreate - called when the activity is first created.
 *
 * Called when the activity is first created. 
 * This is where you should do all of your normal static set up: create views, bind data to lists, etc. 
 * This method also provides you with a Bundle containing the activity's previously frozen state, if there was one.
 * 
 * Always followed by onStart().
 *
 */

protected void onCreate(Bundle savedInstanceState) 
{
    super.onCreate(savedInstanceState);
    //setContentView(R.layout.activity_default);
}
    
/**
 * onDestroy
 * The final call you receive before your activity is destroyed. 
 * This can happen either because the activity is finishing (someone called finish() on it, 
 * or because the system is temporarily destroying this instance of the activity to save space. 
 * You can distinguish between these two scenarios with the isFinishing() method.
 *
 */

protected void onDestroy ()
{
   super.onDestroy ();
}

/**
 * onPause
 * Called when the system is about to start resuming a previous activity. 
 * This is typically used to commit unsaved changes to persistent data, stop animations 
 * and other things that may be consuming CPU, etc. 
 * Implementations of this method must be very quick because the next activity will not be resumed 
 * until this method returns.
 * Followed by either onResume() if the activity returns back to the front, 
 * or onStop() if it becomes invisible to the user.
 *
 */

protected void onPause ()
{
   super.onPause ();
}

/**
 * onRestart
 * Called after your activity has been stopped, prior to it being started again.
 * Always followed by onStart().
 *
 */

protected void onRestart ()
{
   super.onRestart ();
}

/**
 * onResume
 * Called when the activity will start interacting with the user. 
 * At this point your activity is at the top of the activity stack, with user input going to it.
 * Always followed by onPause().
 *
 */

protected void onResume ()
{
   super.onResume ();
}

/**
 * onStart
 * Called when the activity is becoming visible to the user.
 * Followed by onResume() if the activity comes to the foreground, or onStop() if it becomes hidden.
 *
 */

protected void onStart ()
{
   super.onStart ();
}

/**
 * onStop
 * Called when the activity is no longer visible to the user
 * because another activity has been resumed and is covering this one. 
 * This may happen either because a new activity is being started, an existing one 
 * is being brought in front of this one, or this one is being destroyed.
 *
 * Followed by either onRestart() if this activity is coming back to interact with the user, 
 * or onDestroy() if this activity is going away.
 */

protected void onStop ()
{
   super.onStop ();
}

/**
 */
// Click Methods

/**
 * Handle the click on the home button.
 * 
 * @param v View
 * @return void
 */

public void onClickHome (View v)
{
    goHome (this);
}

public void showLogginLogout(View view){
	if(isLoggedIn())
		((ImageButton)view).setImageResource(R.drawable.ic_logout);
	else
		((ImageButton)view).setImageResource(R.drawable.ic_login);
}

public boolean isLoggedIn(){
	return new PrefUtils(this).isLoggedIn();
}

public User getLoggedInUser(){
	return new PrefUtils(this).getLoggedInUser();
}

public void setLoggedIn(String phone, String email, boolean loggedIn, String first_name, String last_name){
	new PrefUtils(this).setLoggedIn(phone, email, loggedIn, first_name, last_name);
}

public void logOutUser(){
	new PrefUtils(this).logOutUser();
}

public void onClickLoginLogout(View view){
	if(isLoggedIn()){
		((ImageButton)view).setImageResource(R.drawable.ic_login);
		logOutUser();
	}else{
		((ImageButton)view).setImageResource(R.drawable.ic_login);
		//start login
		startActivity(new Intent(getApplicationContext(), LoginActivity.class));
	}
}

/**
 * Handle the click on the search button.
 * 
 * @param v View
 * @return void
 */

public void onClickSearch (View v)
{
    startActivity (new Intent(getApplicationContext(), SearchActivity.class));
}

/**
 * Handle the click on the About button.
 * 
 * @param v View
 * @return void
 */

public void onClickAbout (View v)
{
    startActivity (new Intent(getApplicationContext(), AboutActivity.class));
}

/**
 * Handle the click of a Feature button.
 * 
 * @param v View
 * @return void
 */

public void onClickFeature (View v)
{
    int id = v.getId ();
    switch (id) {
      case R.id.home_btn_electronics :
           startActivity (new Intent(getApplicationContext(), F1Activity.class));
           break;
      case R.id.home_btn_mechanics :
           startActivity (new Intent(getApplicationContext(), F2Activity.class));
           break;
      case R.id.home_btn_electricians :
           startActivity (new Intent(getApplicationContext(), F3Activity.class));
           break;
      case R.id.home_btn_plumbers :
           startActivity (new Intent(getApplicationContext(), F4Activity.class));
           break;
      case R.id.home_btn_cabletv :
           startActivity (new Intent(getApplicationContext(), F5Activity.class));
           break;
      case R.id.home_btn_houserepairs :
           startActivity (new Intent(getApplicationContext(), F6Activity.class));
           break;
      default:
    	   break;
    }
}

/**
 */
// More Methods

/**
 * Go back to the home activity.
 * 
 * @param context Context
 * @return void
 */

public void goHome(Context context) 
{
    final Intent intent = new Intent(context, HomeActivity.class);
    intent.setFlags (Intent.FLAG_ACTIVITY_CLEAR_TOP);
    context.startActivity (intent);
}

/**
 * Set the content view for the activity.
 * If the current configuration is large or xlarge, the layout is actually placed in another
 * container defined by large.xml. See the definitions in the layout-large, layout-large-land,
 * layout-xlarge, layout-xlarge-land folders.
 * 
 * @param layoutId int - the resource id of the layout to use for the activity
 * @return void
 */

@Override public void setContentView (int layoutId)
{
    Configuration c = getResources ().getConfiguration ();
    int size = c.screenLayout & Configuration.SCREENLAYOUT_SIZE_MASK;
    boolean isLarge = (size == Configuration.SCREENLAYOUT_SIZE_LARGE);
    boolean isXLarge = (size == Configuration.SCREENLAYOUT_SIZE_XLARGE);
    boolean addFrame = isLarge || isXLarge;

    //if (isLarge) System.out.println ("Large screen");
    //if (isXLarge) System.out.println ("XLarge screen");

    int finalLayoutId = addFrame ? R.layout.large : layoutId;
    super.setContentView (finalLayoutId);

    if (addFrame) {
       LinearLayout frameView = (LinearLayout) findViewById (R.id.frame);
       if (frameView != null) {

          // If the frameView is there, inflate the layout given as an argument.
          // Attach it as a child to the frameView.
          LayoutInflater li = ((Activity) this).getLayoutInflater();
          View childView = li.inflate (layoutId, null);
          if (childView != null) {
             LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams
                                                   (ViewGroup.LayoutParams.MATCH_PARENT,
                                                    ViewGroup.LayoutParams.MATCH_PARENT,
                                                    1.0F);
             frameView.addView (childView, lp);
             //childView.setBackgroundResource (R.color.background1);
          }

       }
    }
} // end setContentView

/**
 * Use the activity label to set the text in the activity's title text view.
 * The argument gives the name of the view.
 *
 * <p> This method is needed because we have a custom title bar rather than the default Android title bar.
 * See the theme definitons in styles.xml.
 * 
 * @param textViewId int
 * @return void
 */

public void setTitleFromActivityLabel (int textViewId)
{
    TextView tv = (TextView) findViewById (textViewId);
    if (tv != null) tv.setText (getTitle ());
} // end setTitleText

/**
 * Show a string on the screen via Toast.
 * 
 * @param msg String
 * @return void
 */

public void toast (String msg)
{
    Toast.makeText (getApplicationContext(), msg, Toast.LENGTH_SHORT).show ();
} // end toast

/**
 * Send a message to the debug log and display it using Toast.
 */
public void trace (String msg) 
{
    Log.d("Demo", msg);
    toast (msg);
}

public boolean onCreateOptionsMenu(Menu menu){
	super.onCreateOptionsMenu(menu);
	MenuInflater awesome=getMenuInflater();
	awesome.inflate (R.menu.main_menu,menu);
	return true;
}
public boolean onOptionsItemSelected(MenuItem item){
	switch(item.getItemId()){
	case R.id.MenuSweet:
		Toast mytoast=Toast.makeText(DashboardActivity.this, "this is a toast", Toast.LENGTH_LONG);
		mytoast.show();
    case R.id.MenuToast:
		Toast toaster=Toast.makeText(DashboardActivity.this, "this is a toast", Toast.LENGTH_LONG);
		toaster.show();
		return true;
    case R.id.Mafundi:
		Toast toast1=Toast.makeText(DashboardActivity.this, "this is a toast", Toast.LENGTH_LONG);
		toast1.show();
    case R.id.MenuSettings:
		Toast toast2=Toast.makeText(DashboardActivity.this, "this is a toast", Toast.LENGTH_LONG);
		toast2.show();
		return true;
   
   
		
	}
	return false;
	
}

public interface AuthenticateUser{
	
	
	public void logOut();
}

}
