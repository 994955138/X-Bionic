package com.imcore.x_bionic.ui;

import java.util.ArrayList;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.imcore.x_bionic.R;
import com.imcore.x_bionic.datameta.BaseUrl;
import com.imcore.x_bionic.datameta.HttpMethod;
import com.imcore.x_bionic.model.SubCategoryDetail;
import com.imcore.x_bionic.ui.fragment.FragementHomeTwo;
import com.imcore.x_bionic.ui.fragment.FragmentHomeFour;
import com.imcore.x_bionic.ui.fragment.FragmentHomeOne;
import com.imcore.x_bionic.ui.fragment.FragmentHomeThree;
import com.imcore.x_bionic.util.DataRequestVolley;
import com.imcore.x_bionic.util.JsonUtil;
import com.imcore.x_bionic.util.ToastUtil;

import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBar.Tab;
import android.support.v7.app.ActionBar.TabListener;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;

public class ProductDetailsActivity extends ActionBarActivity {
	GridView gridView;
	int nPosition;

	int mNavId;
	int mSubNavId;

	ArrayList<SubCategoryDetail> scdList;
	ViewPager vpSubCatogery;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_product_details);

		getNavId();
		getSubCategoryDetail();

		initialViewPager();
		initialActionBar();
		if (savedInstanceState == null) {
			getSupportFragmentManager().beginTransaction()
					.add(R.id.container, new PlaceholderFragment()).commit();
		}
	}

	public void getNavId() {
		ArrayList<Integer> list = this.getIntent().getExtras()
				.getIntegerArrayList("mParam");

		nPosition = list.get(0);
		if (nPosition == 0) {
			mNavId = 100001;
		} else {
			mNavId = 100002;
		}
		mSubNavId = list.get(1);
	}

	private void getSubCategoryDetail() {

		String url = BaseUrl.BASE_SERVER_URL + "category/list.do?" + mNavId
				+ "&" + mSubNavId;

		DataRequestVolley dataRequestVolley = new DataRequestVolley(
				HttpMethod.GET, url, new Response.Listener<String>() {

					@Override
					public void onResponse(String response) {
						// TODO Auto-generated method stub
						scdList = (ArrayList<SubCategoryDetail>) JsonUtil
								.toObjectList(response, SubCategoryDetail.class);
					}
				}, new Response.ErrorListener() {

					@Override
					public void onErrorResponse(VolleyError error) {
						// TODO Auto-generated method stub
						ToastUtil.showToast(getApplicationContext(),
								"º”‘ÿ ß∞‹£¨«ÎºÏ≤ÈÕ¯¬Á");
					}
				});
	}

	public void getProductList(int item){
	
	String url=BaseUrl.BASE_SERVER_URL+"category/products.do?"+"navId="+mNavId+"&"+"subNavId="+mSubNavId+"&"+"id="+scdList.get(item).id;
    
	
	
	

}

	private void initialActionBar() {
		ActionBar actionBar = getSupportActionBar();
		actionBar.setDisplayHomeAsUpEnabled(true);
		actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
		for (SubCategoryDetail scd : scdList) {
			Tab tab = actionBar.newTab();
			tab.setText(scd.categoryName);
			tab.setTabListener(mListener1);
			actionBar.addTab(tab);
		}
	}

	private TabListener mListener1 = new TabListener() {

		@Override
		public void onTabUnselected(Tab arg0, FragmentTransaction arg1) {
			// TODO Auto-generated method stub

		}

		@Override
		public void onTabSelected(Tab arg0, FragmentTransaction arg1) {
			// TODO Auto-generated method stub
			vpSubCatogery.setCurrentItem(arg0.getPosition());
		}

		@Override
		public void onTabReselected(Tab arg0, FragmentTransaction arg1) {
			// TODO Auto-generated method stub

		}
	};

	private void initialViewPager() {

		vpSubCatogery = (ViewPager) findViewById(R.id.vp_subcategry);
		vpSubCatogery.setAdapter(new ViewPagerAdapter(
				getSupportFragmentManager()));
		vpSubCatogery.setOnPageChangeListener(mListener2);

	}

	private class ViewPagerAdapter extends FragmentStatePagerAdapter {

		public ViewPagerAdapter(FragmentManager fm) {
			super(fm);
			// TODO Auto-generated constructor stub
		}

		@Override
		public Fragment getItem(int arg0) {
			// TODO Auto-generated method stub
			Fragment mFragment = null;
			switch (arg0) {
			case 0:
				mFragment = new FragmentHomeOne();
				break;
			case 1:
				mFragment = new FragementHomeTwo();
				break;
			case 2:
				mFragment = new FragmentHomeThree();
				break;
			case 3:
				mFragment = new FragmentHomeFour();
				break;
			}
			return mFragment;
		}

		@Override
		public int getCount() {
			return 0;
			// TODO Auto-generated method stub
			
		}

	}

	private OnPageChangeListener mListener2 = new OnPageChangeListener() {

		@Override
		public void onPageSelected(int arg0) {
			// TODO Auto-generated method stub
			ActionBar mActionBar = getSupportActionBar();
			mActionBar.setSelectedNavigationItem(arg0);
		}

		@Override
		public void onPageScrolled(int arg0, float arg1, int arg2) {
			// TODO Auto-generated method stub

		}

		@Override
		public void onPageScrollStateChanged(int arg0) {
			// TODO Auto-generated method stub

		}
	};

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.product_details, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	/**
	 * A placeholder fragment containing a simple view.
	 */
	public static class PlaceholderFragment extends Fragment {

		public PlaceholderFragment() {
		}

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			View rootView = inflater.inflate(R.layout.fragment_product_details,
					container, false);
			return rootView;
		}
	}

}
