package com.imcore.x_bionic.ui.login;

import java.util.HashMap;
import java.util.Map;

import android.app.ProgressDialog;
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
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.EditText;

import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.imcore.x_bionic.R;
import com.imcore.x_bionic.datameta.BaseUrl;
import com.imcore.x_bionic.datameta.HttpMethod;
import com.imcore.x_bionic.model.UserMessage;
import com.imcore.x_bionic.ui.FirstLoginActivity;
import com.imcore.x_bionic.ui.HomeActivity;
import com.imcore.x_bionic.util.DataRequestVolley;
import com.imcore.x_bionic.util.JsonUtil;
import com.imcore.x_bionic.util.RequestQueueSingleton;
import com.imcore.x_bionic.util.TextUtil;
import com.imcore.x_bionic.util.ToastUtil;

public class XLoginActivity extends ActionBarActivity {

	TextView tvWelcome;
	EditText etUserName, etPassWord;
	Button btEnter, btForgot;
	CheckBox cbRemenber;

	String userName;
	String password;
	UserMessage um;
	ProgressDialog pd;
	SharedPreferences sp;
	SharedPreferences.Editor editor;


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_xlogin);
		tvWelcome = (TextView) findViewById(R.id.tv_welcome_x);
		etUserName = (EditText) findViewById(R.id.et_username_x);
		etPassWord = (EditText) findViewById(R.id.et_password_x);
		btEnter = (Button) findViewById(R.id.bt_enter_x);
		btForgot = (Button) findViewById(R.id.bt_forgot_x);
		cbRemenber = (CheckBox) findViewById(R.id.cb_remenber__x);


		sp = getSharedPreferences("userMessage", MODE_PRIVATE);
		editor = sp.edit();
		String strU = sp.getString("username", "");
		String strP = sp.getString("password", "");
		if (!strU.equals("") && !strP.equals("")) {
			etUserName.setText(strU);
			etPassWord.setText(strP);
		}

		cbRemenber.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(CompoundButton arg0, boolean arg1) {
				// TODO Auto-generated method stub
				if (arg1) {
					editor.putString("username", etUserName.getText()
							.toString());
					editor.putString("password", etPassWord.getText()
							.toString());
					editor.commit();
				}
				else{
					editor.clear();
					editor.commit();
				}
			}
		});

		btEnter.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				pd = new ProgressDialog(XLoginActivity.this);
				pd = ProgressDialog.show(XLoginActivity.this, "客户端登录",
						"玩命登陆录中。。。");
				userName = etUserName.getText().toString();
				password = etPassWord.getText().toString();
				if (userName.length() != 11) {
					ToastUtil.showToast(XLoginActivity.this, "请输入11位手机号码");
					pd.dismiss();
				} else if ((!TextUtil.isPhoneNumber(userName))
						|| (!TextUtil.isNumber(userName))) {
					ToastUtil.showToast(XLoginActivity.this, "请确保您输入的是手机号码");
					pd.dismiss();
				} else {

					tribeLogin();
				}

			}
		});

	}

	private void tribeLogin() {

		try {
			String url = BaseUrl.BASE_SERVER_URL + "passport/login.do";
			int method = HttpMethod.POST;
			DataRequestVolley requestVolley = new DataRequestVolley(method,
					url, mResponseSuccess, mErrorListener) {
				@Override
				protected Map<String, String> getParams()
						throws AuthFailureError {
					// TODO Auto-generated method stub

					Map<String, String> map = new HashMap<String, String>();
					map.put("phoneNumber", userName);
					map.put("password", password);
					map.put("client", "android");
					return map;

				}
					
			};
			RequestQueueSingleton.getInstance(getApplicationContext())
					.addToRequestQueue(requestVolley);

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	
	 public  boolean firstLogin(){
	    	boolean fl=sp.getBoolean("isFirstLogin",true );
	    	editor=sp.edit();
	    	editor.putBoolean("isFirstLogin", false);
	    	editor.commit();
			return fl;
	    }
	
	
	private Response.Listener<String> mResponseSuccess = new Response.Listener<String>() {

		@Override
		public void onResponse(String response) {
			// TODO Auto-generated method stub
			pd.dismiss();
			um = new UserMessage();
			um = JsonUtil.toObject(response, UserMessage.class);
			if(firstLogin()){
				startActivity(new Intent(XLoginActivity.this, FirstLoginActivity.class));
			}else{
				startActivity(new Intent(XLoginActivity.this, HomeActivity.class));
			}
		}

	};

	private Response.ErrorListener mErrorListener = new Response.ErrorListener() {

		@Override
		public void onErrorResponse(VolleyError error) {
			// TODO Auto-generated method stub
			pd.dismiss();
			ToastUtil.showToast(XLoginActivity.this, error.getMessage());
		}

	};

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.xlogin, menu);
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
			View rootView = inflater.inflate(R.layout.fragment_xlogin,
					container, false);
			return rootView;
		}
	}

}
