package com.imcore.x_bionic.ui.login;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBarActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.imcore.x_bionic.R;
import com.imcore.x_bionic.ui.register.RegisterMainActivity;

public class LoginMainActivity extends ActionBarActivity {
Button tencent, sina, tribe, newUser
	, help, services;



	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login__main);

		tencent = (Button) findViewById(R.id.tencent_login);
		sina = (Button) findViewById(R.id.sina_login);
		tribe = (Button) findViewById(R.id.tribe_login);
		newUser = (Button) findViewById(R.id.new_user);
		help = (Button) findViewById(R.id.home_help);
		services = (Button) findViewById(R.id.home_terms);
		
		

		tencent.setOnClickListener(mtencent);
		sina.setOnClickListener(msina);
		tribe.setOnClickListener(mtribe);
		newUser.setOnClickListener(mnewUser);
		help.setOnClickListener(mhelp);
		services.setOnClickListener(mservices);

		if (savedInstanceState == null) {
			getSupportFragmentManager().beginTransaction()
					.add(R.id.container, new PlaceholderFragment()).commit();
		}
	}

	
	
	
	OnClickListener mtencent=new OnClickListener() {
		
		@Override
		public void onClick(View arg0) {
			// TODO Auto-generated method stub
			startActivity(new Intent(LoginMainActivity.this, XLLoginActivity.class));
		}
	};
	
OnClickListener msina=new OnClickListener() {
		
		@Override
		public void onClick(View arg0) {
			// TODO Auto-generated method stub
			startActivity(new Intent(LoginMainActivity.this, TxLoginActivity.class));
		}
	};
	
	
OnClickListener mtribe=new OnClickListener() {
		
		@Override
		public void onClick(View arg0) {
			// TODO Auto-generated method stub
			startActivity(new Intent(LoginMainActivity.this, XLoginActivity.class));
		}
	};
	
	
OnClickListener mnewUser=new OnClickListener() {
		
		@Override
		public void onClick(View arg0) {
			// TODO Auto-generated method stub
			startActivity(new Intent(LoginMainActivity.this, RegisterMainActivity.class));
		}
	};
	
	
OnClickListener mhelp=new OnClickListener() {
		
		@Override
		public void onClick(View arg0) {
			// TODO Auto-generated method stub
//			startActivity(new Intent(LoginMainActivity.this, cls));
		}
	};
	
OnClickListener mservices=new OnClickListener() {
		
		@Override
		public void onClick(View arg0) {
			// TODO Auto-generated method stub
//			startActivity(new Intent(LoginMainActivity.this, cls));
		}
	};
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.login__main, menu);
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
			View rootView = inflater.inflate(R.layout.fragment_login__main,
					container, false);
			return rootView;
		}
	}

	private void helpLogin() {
		Toast toast = Toast.makeText(getApplicationContext(), "暂不支持帮助",
				Toast.LENGTH_SHORT);
		toast.show();

	}

	private void serviceIterm() {
		Toast toast = Toast.makeText(getApplicationContext(), "暂不支持服务条款",
				Toast.LENGTH_SHORT);
		toast.show();

	}

}
