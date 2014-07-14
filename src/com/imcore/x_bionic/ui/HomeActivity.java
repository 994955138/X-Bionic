package com.imcore.x_bionic.ui;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBar.Tab;
import android.support.v7.app.ActionBar.TabListener;
import android.support.v7.app.ActionBarActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import com.imcore.x_bionic.R;
import com.imcore.x_bionic.ui.fragment.FragementHomeTwo;
import com.imcore.x_bionic.ui.fragment.FragmentHomeFour;
import com.imcore.x_bionic.ui.fragment.FragmentHomeOne;
import com.imcore.x_bionic.ui.fragment.FragmentHomeThree;

public class HomeActivity extends ActionBarActivity {
	
	ViewPager mViewPager;
	String []fragmentList;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_home);
		String []list={"X的活动","X的介绍","产品购买","达人故事"};
		fragmentList=list;
		initialViewPager();
		initialActionBar();
		if (savedInstanceState == null) {
			getSupportFragmentManager().beginTransaction()
					.add(R.id.container, new PlaceholderFragment()).commit();
		}
	}

	
	
	
	private void initialActionBar(){
		ActionBar actionBar=getSupportActionBar();
		actionBar.setDisplayHomeAsUpEnabled(true);
		actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
		for(String str:fragmentList){
		Tab tab=actionBar.newTab();
		tab.setText(str);
		tab.setTabListener(mListener1);
		actionBar.addTab(tab);
		}
	}
	
	
	private TabListener mListener1=new TabListener() {
		
		@Override
		public void onTabUnselected(Tab arg0, FragmentTransaction arg1) {
			// TODO Auto-generated method stub
			
		}
		
		@Override
		public void onTabSelected(Tab arg0, FragmentTransaction arg1) {
			// TODO Auto-generated method stub
			mViewPager.setCurrentItem(arg0.getPosition());
		}
		
		@Override
		public void onTabReselected(Tab arg0, FragmentTransaction arg1) {
			// TODO Auto-generated method stub
			
		}
	};
	
	
    private void initialViewPager(){
    	
    	mViewPager=(ViewPager) findViewById(R.id.vp_home_activity);
    	mViewPager.setAdapter(new ViewPagerAdapter(getSupportFragmentManager()));
    	mViewPager.setOnPageChangeListener(mListener2);
    	
    }
	
	public class ViewPagerAdapter extends FragmentStatePagerAdapter{

		public ViewPagerAdapter(FragmentManager fm) {
			super(fm);
			// TODO Auto-generated constructor stub
		}

		@Override
		public Fragment getItem(int arg0) {
			// TODO Auto-generated method stub
			Fragment mFragment = null;
			switch (arg0) {
			case 0:mFragment=new FragmentHomeOne();break;
			case 1:mFragment=new FragementHomeTwo();break;
			case 2:mFragment=new FragmentHomeThree();break;
			case 3:mFragment=new FragmentHomeFour();break;
		    }
			return mFragment;
			}
		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return fragmentList.length;
		}
		
	}
	
	private OnPageChangeListener mListener2=new OnPageChangeListener() {
		
		@Override
		public void onPageSelected(int arg0) {
			// TODO Auto-generated method stub
			ActionBar mActionBar=getSupportActionBar();
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
		getMenuInflater().inflate(R.menu.home, menu);
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
			View rootView = inflater.inflate(R.layout.fragment_home, container,
					false);
			return rootView;
		}
	}

}
