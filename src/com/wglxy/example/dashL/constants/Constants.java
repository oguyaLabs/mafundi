package com.wglxy.example.dashL.constants;

public class Constants {

	//search activity
	public static final String KEY_SEARCH_ARGS = "search_args";
	
	//company activity
	public static final String KEY_COMPANY_ARGS = "company_args";
	public static final String KEY_COMPANY_BUNDLE_ARGS = "bundle_args";	

	//A/C auth
	public static final String KEY_USER_LOGGED_IN = "logged_in";
	public static final String KEY_USER_PHONE= "user_phone";
	public static final String KEY_USER_EMAIL = "user_email";
	public static final String KEY_USER_FIRST_NAME = "first_name";
	public static final String KEY_USER_LAST_NAME = "last_name";	
	
	//API URLS
//	public static final String API_BASE_URL = "http://107.170.165.53/mafundis-app/app/";
//	public static final String API_BASE_URL = "192.168.43.100/droid/mafundiAPI/v1";
	public static final String API_BASE_URL = "192.168.7.66/droid/mafundiAPI/v1";
	public static final String API_ENDPOINT_URL = "/endpoint.php";
	public static final String API_ENDPOINT_ARG = "endpoint";
	public static final String API_ENDPOINT_LOGIN = "login";
	public static final String API_ENDPOINT_SIGNUP = "signup";
	public static final String API_ENDPOINT_SEARCH = "search";
	public static final String API_ENDPOINT_COMPANY = "profile";
	public static final String API_ENDPOINT_CATEGORY = "category";
	public static final String API_ENDPOINT_CONTACT = "contact";
	public static final String API_ENDPOINT_REVIEWS = "review";
	
	//API URL ARGS
	public static final String API_LOGIN_ARGS_EMAIL = "email";
	public static final String API_LOGIN_ARGS_PHONE = "phone";
	public static final String API_SEARCH_ARGS_QUERY = "query";
	public static final String API_CATEGORY_ARGS_CATEGORYID = "categoryID";
	public static final String API_PROFILE_ARGS_COMPANYID = "companyID";
	public static final String API_REVIEWS_ARGS_COMPANYID = "companyID";
	public static final String API_CONTACT_ARGS_TO = "to";
	public static final String API_CONTACT_ARGS_FROM = "from";
	public static final String API_CONTACT_ARGS_PHONE = "phone";
	public static final String API_CONTACT_ARGS_SUBJECT = "subject";
	public static final String API_CONTACT_ARGS_BODY = "body";
	
}
