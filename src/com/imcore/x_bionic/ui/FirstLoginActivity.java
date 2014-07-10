package com.imcore.x_bionic.ui;

import java.util.ArrayList;
import java.util.List;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import com.imcore.x_bionic.R;

public class FirstLoginActivity extends ActionBarActivity {
	ViewPager mViewPager;
	List<View>viewList;
	List<ImageView>pointList;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_first_login);
		ActionBar actionBar=getSupportActionBar();
		actionBar.hide();
		viewList=new ArrayList<View>();
		pointList=new ArrayList<ImageView>();
		
		getViewList();
		getPointList();
		
		
		mViewPager = (ViewPager) findViewById(R.id.vp_image);
		mViewPager.setAdapter(new ViewPagerAdapter());	
		mViewPager.setOnPageChangeListener(mChangeListener);

		if (savedInstanceState == null) {
			getSupportFragmentManager().beginTransaction()
					.add(R.id.container, new PlaceholderFragment()).commit();
		}
	}

	
	
	public void getViewList(){
		View one=getLayoutInflater().inflate(R.layout.layout_viewpager, null);
		ImageView imViewOne=(ImageView) one.findViewById(R.id.iv_viewpager);
		imViewOne.setImageResource(R.drawable.welcompage1);
		
		View two=getLayoutInflater().inflate(R.layout.layout_viewpager_two, null);
		ImageView ivViewtwo=(ImageView) two.findViewById(R.id.iv_viewpager_two);
		ivViewtwo.setImageResource(R.drawable.welcompage2);
		
		View three=getLayoutInflater().inflate(R.layout.layout_viewpager_three, null);
		ImageView ivViewThree=(ImageView) three.findViewById(R.id.iv_viewpager_three);
		Button mButton=(Button) three.findViewById(R.id.bt_enjoy);
		mButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				startActivity(new Intent(FirstLoginActivity.this,HomeActivity.class));
			}
		});
		ivViewThree.setImageResource(R.drawable.welcompage3);
		
		viewList.add(one);
		viewList.add(two);
		viewList.add(three);
		
	}
	

	public void getPointList(){
		
		ImageView point1, point2, point3;
		point1=(ImageView) findViewById(R.id.iv_image1);
		point2=(ImageView) findViewById(R.id.iv_image2);
		point3=(ImageView) findViewById(R.id.iv_image3);
		point1.setImageResource(R.drawable.whitepoint);
		point2.setImageResource(R.drawable.blackpoint);
		point3.setImageResource(R.drawable.blackpoint);
		pointList.add(point1);
		pointList.add(point2);
		pointList.add(point3);
	}
	public class ViewPagerAdapter extends PagerAdapter {
View view;
		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return viewList.size();
		}

		@Override
		public boolean isViewFromObject(View arg0, Object arg1) {
			// TODO Auto-generated method stub
			return arg0 == arg1;
		}

		@Override
		public Object instantiateItem(ViewGroup container, int position) {
			// TODO Auto-generated method stub

			View view=viewList.get(position);
			((ViewPager)container).addView(view);
			
			return view;
			
		}

		@Override
		public void destroyItem(ViewGroup container, int position, Object object) {
			// TODO Auto-generated method stub
	
			((ViewPager)container).removeView(viewList.get(position));
			
		}
		
	}

	
	private OnPageChangeListener mChangeListener=new OnPageChangeListener() {
		
		@Override
		public void onPageSelected(int arg0) {
			// TODO Auto-generated method stub
			int i=0;
			
			for(i=0;i<3;i++){
				if(i==arg0){
					pointList.get(i).setImageResource(R.drawable.whitepoint);
					}
				else {
					pointList.get(i).setImageResource(R.drawable.blackpoint);
				}
				
         }
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
		getMenuInflater().inflate(R.menu.first_login, menu);
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
			View rootView = inflater.inflate(R.layout.fragment_first_login,
					container, false);
			return rootView;
		}
	}

}
