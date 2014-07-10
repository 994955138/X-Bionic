package com.imcore.x_bionic.ui;

import java.util.Timer;
import java.util.TimerTask;

import com.imcore.x_bionic.R;
import com.imcore.x_bionic.ui.login.LoginMainActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBarActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.ProgressBar;

public class LoadingActivity extends ActionBarActivity {
	ProgressBar mProgressBar;
	int progress;
	Timer mTimer;
	boolean jumpFlag;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_loading);
		mProgressBar = (ProgressBar) findViewById(R.id.Pb_welcome_background);
		progress = 0;
		jumpFlag = false;

		mTimer = new Timer();
		mTimer.schedule(task, 0, 10);//时间值应设置为50,为测试方便先设为10

		if (savedInstanceState == null) {
			getSupportFragmentManager().beginTransaction()
					.add(R.id.container, new PlaceholderFragment()).commit();
		}
	}

	TimerTask task = new TimerTask() {

		@Override
		public void run() {
			// TODO Auto-generated method stub
			if (progress < 100) {
				progress++;
				mProgressBar.setProgress(progress);
			} else {
				Intent intent = new Intent(LoadingActivity.this,
						LoginMainActivity.class);
				startActivity(intent);
				mTimer.cancel();//timer只要没有被cacel就一直会在后台运行，会所以没有cancel的话就会一直执行跳转到LoginMainActivity
			}
		}
	};

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.loading, menu);
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
			View rootView = inflater.inflate(R.layout.fragment_loading,
					container, false);
			return rootView;
		}
	}

}
