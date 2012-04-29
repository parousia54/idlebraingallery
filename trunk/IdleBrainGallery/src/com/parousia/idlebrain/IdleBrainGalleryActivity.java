package com.parousia.idlebrain;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import android.app.Activity;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.Gallery;
import android.widget.ImageView;

import com.parousia.idlebrain.network.HTTPUtility;

public class IdleBrainGalleryActivity extends Activity {

	private ArrayList<String> imgSrc = new ArrayList<String>();

	protected ImageView imageView;

	protected Map<Integer, Bitmap> bitMapCache = new HashMap<Integer, Bitmap>();

	@Override
	public void onCreate(Bundle savedInstanceState) {
		requestWindowFeature(Window.FEATURE_INDETERMINATE_PROGRESS);
		requestWindowFeature(Window.FEATURE_PROGRESS);
		setProgress(10);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.galleryview);

		for (int i = 1; i < 44; i++) {
			imgSrc.add("http://idlebrain.com/movie/photogallery/kajalagarwal140/images/kajalagarwal"
					+ i + ".jpg");
		}

		Gallery gallery = (Gallery) findViewById(R.id.gallery);
		imageView = (ImageView) findViewById(R.id.ImageView01);
		ImageAdapter imageAdapter = new ImageAdapter(this, imgSrc);
		imageAdapter.setMyRemoteImages(imgSrc);
		gallery.setAdapter(imageAdapter);

		if (imgSrc.size() > 1) {
			gallery.setSelection(3);
		}

		gallery.setOnItemClickListener(new OnItemClickListener() {
			public void onItemClick(AdapterView<?> parent, View v,
					int position, long id) {
				Log.d("IDLEBRAIN", "Clicked on position " + position);
				imageView.setImageBitmap(bitMapCache.get(position));

			}
		});
	}

	public class ImageAdapter extends BaseAdapter {

		Context mContext;
		int mGalleryItemBackground;
		ArrayList<String> myRemoteImages;

		public ImageAdapter(Context mContext, ArrayList<String> urls) {
			super();
			this.mContext = mContext;
			myRemoteImages = urls;
			TypedArray attr = mContext
					.obtainStyledAttributes(R.styleable.HelloGallery);
			mGalleryItemBackground = attr.getResourceId(
					R.styleable.HelloGallery_android_galleryItemBackground, 0);
			attr.recycle();
		}

		@Override
		public int getCount() {
			return myRemoteImages.size();
		}

		@Override
		public Object getItem(int position) {
			return null;
		}

		@Override
		public long getItemId(int position) {
			// TODO Auto-generated method stub
			return 0;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {

			ImageView imgView = new ImageView(mContext);
			imgView.setLayoutParams(new Gallery.LayoutParams(200, 150));
			imgView.setScaleType(ImageView.ScaleType.FIT_XY);
			imgView.setScaleType(ImageView.ScaleType.FIT_CENTER);
			Log.d("IDLEBRAIN", "Position = " + Integer.toString(position));
			Log.d("IDLEBRAIN", "URL = " + myRemoteImages.get(position));
			if (bitMapCache.containsKey(position)) {
				Log.d("IDLEBRAIN", "Fetching from Cache");
				imgView.setImageBitmap(bitMapCache.get(position));
				return imgView;
			}
			downloadImage(position);
			return imgView;

		}

		public ArrayList<String> getMyRemoteImages() {
			return myRemoteImages;
		}

		public void setMyRemoteImages(ArrayList<String> myRemoteImages) {
			this.myRemoteImages = myRemoteImages;
		}

		private void downloadImage(final int position) {

			final String url = myRemoteImages.get(position);
			AsyncTask<Object, Integer, Bitmap> task = new AsyncTask<Object, Integer, Bitmap>() {

				@Override
				protected void onPreExecute() {
					super.onPreExecute();
					setProgressBarIndeterminateVisibility(true);
				}

				@Override
				protected Bitmap doInBackground(Object... params) {
					byte[] imageData;
					try {
						imageData = HTTPUtility.fetchImage(new URI(url));
						if (imageData != null) {
							BitmapFactory.Options options = new BitmapFactory.Options();
							options.inSampleSize = 2;
							Log.d("IDLEBRAIN",
									"DataLength = "
											+ Integer
													.toString(imageData.length));
							Bitmap bm = BitmapFactory.decodeByteArray(
									imageData, 0, imageData.length, options);
							return bm;
						} else {
							return null;
						}
					} catch (URISyntaxException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
						return null;
					}
				}

				@Override
				protected void onPostExecute(Bitmap result) {
					setProgressBarIndeterminateVisibility(false);
					bitMapCache.put(position, result);
				}

			};

			task.execute("");
		}

	}

}
