package com.imcore.x_bionic.ui;

import com.imcore.x_bionic.R;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public abstract class LoginBaseActivity extends ActionBarActivity{
	TextView tvWelcome;
	EditText etUserName,etPassWord;
	Button btEnter,btForgot;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		initWidgets();
		initActivity();
		
	}
	protected abstract void initWidgets();
	
	public void initActivity(){
		
		tvWelcome=(TextView) findViewById(R.id.tv_base_welcome);
		etUserName=(EditText) findViewById(R.id.et_base_username);
		etPassWord=(EditText) findViewById(R.id.et_base_password);
		btEnter=(Button) findViewById(R.id.bt_base_enter);
		btForgot=(Button) findViewById(R.id.bt_base_forgot);
	}

}
