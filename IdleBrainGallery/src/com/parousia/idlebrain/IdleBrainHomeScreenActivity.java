package com.parousia.idlebrain;

import java.util.ArrayList;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.ListFragment;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.parousia.dummy.Cheeses;
import com.parousia.idlebrain.data.DataHelper;

public class IdleBrainHomeScreenActivity extends FragmentActivity {

	static final int NUM_PAGES = 3;
	IdleBrainPagerAdapter idleBrainPagerAdapter;
	ViewPager viewPager;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.homescreen);
		idleBrainPagerAdapter = new IdleBrainPagerAdapter(
				getSupportFragmentManager());
		 viewPager = (ViewPager)findViewById(R.id.pager);
	        viewPager.setAdapter(idleBrainPagerAdapter);
	}

	public static class IdleBrainPagerAdapter extends FragmentPagerAdapter {

		public IdleBrainPagerAdapter(FragmentManager fm) {
			super(fm);
		}

		@Override
		public Fragment getItem(int position) {
			return ArrayListFragment.newInstance(position);
		}

		@Override
		public int getCount() {
			return NUM_PAGES;
		}

		public static class ArrayListFragment extends ListFragment {
			int mNum;

			/**
			 * Create a new instance of CountingFragment, providing "num" as an
			 * argument.
			 */
			static ArrayListFragment newInstance(int num) {
				ArrayListFragment f = new ArrayListFragment();

				// Supply num input as an argument.
				Bundle args = new Bundle();
				args.putInt("num", num);
				f.setArguments(args);

				return f;
			}

			/**
			 * When creating, retrieve this instance's number from its
			 * arguments.
			 */
			@Override
			public void onCreate(Bundle savedInstanceState) {
				super.onCreate(savedInstanceState);
				mNum = getArguments() != null ? getArguments().getInt("num")
						: 1;
			}

			/**
			 * The Fragment's UI is just a simple text view showing its instance
			 * number.
			 */
			@Override
			public View onCreateView(LayoutInflater inflater,
					ViewGroup container, Bundle savedInstanceState) {
				View v = inflater.inflate(R.layout.fragment_pager_list,
						container, false);
				View tv = v.findViewById(R.id.text);
				((TextView) tv).setText("Fragment #" + mNum);
				return v;
			}

			@Override
			public void onActivityCreated(Bundle savedInstanceState) {
				super.onActivityCreated(savedInstanceState);
				
				fetchHeroineList();
				setListAdapter(new ArrayAdapter<String>(getActivity(),
						android.R.layout.simple_list_item_1,
						Cheeses.sCheeseStrings));
			}

			@Override
			public void onListItemClick(ListView l, View v, int position,
					long id) {
				Log.i("FragmentList", "Item clicked: " + id);
			}
			
			public void fetchHeroineList()
			{

				AsyncTask<Object, Integer, ArrayList<String>> task = new AsyncTask<Object, Integer, ArrayList<String>>() {

					@Override
					protected void onPreExecute() {
						super.onPreExecute();
//						setProgressBarIndeterminateVisibility(true);
					}

					@Override
					protected ArrayList<String> doInBackground(Object... params) {
						return DataHelper.fetchHeroineList();
					}

					@Override
					protected void onPostExecute(ArrayList<String> result) {
//						setProgressBarIndeterminateVisibility(false);
//						bitMapCache.put(position, result);
					}

				};

				task.execute("");
			}
		}
	}
}
