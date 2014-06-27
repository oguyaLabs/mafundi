package com.wglxy.example.dashL.net;

import java.io.InputStream;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ImageView;

public class ImageDownloader extends AsyncTask<Void, Void, Bitmap> {

	ImageView imageView;
	String imageURL;

	public ImageDownloader(ImageView imageView, String imageURL) {
		this.imageView = imageView;
		this.imageURL = imageURL;
	}

	protected Bitmap doInBackground(Void... urls) {
		Bitmap bitmap = null;
		try {
			InputStream in = new java.net.URL(this.imageURL).openStream();
			bitmap = BitmapFactory.decodeStream(in);
		} catch (Exception e) {
			Log.e("DownloadImageTask", e.getMessage());
			e.printStackTrace();
		}
		return bitmap;
	}

	protected void onPostExecute(Bitmap result) {
		if(result != null)
			imageView.setImageBitmap(result);
	}
}
