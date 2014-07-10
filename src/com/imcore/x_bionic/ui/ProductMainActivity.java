package com.imcore.x_bionic.ui;

import java.security.PublicKey;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBarActivity;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.NetworkImageView;
import com.imcore.x_bionic.R;
import com.imcore.x_bionic.datameta.BaseUrl;
import com.imcore.x_bionic.datameta.HttpMethod;
import com.imcore.x_bionic.model.Bionic;
import com.imcore.x_bionic.model.ProductDetail;
import com.imcore.x_bionic.model.Sock;
import com.imcore.x_bionic.util.DataRequestVolley;
import com.imcore.x_bionic.util.JsonUtil;
import com.imcore.x_bionic.util.NetworkImage;
import com.imcore.x_bionic.util.RequestQueueSingleton;
import com.imcore.x_bionic.util.ToastUtil;

public class ProductMainActivity extends ActionBarActivity {
	ExpandableListView listView;
	List<Integer> productCategory;
	List<Bionic> bionicList;
	List<Sock> sockList;

	// private List<List<ProductDetail>> children;
	// private List<String> group;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_product_main);
		productCategory = new ArrayList<Integer>();
		productCategory.add(R.drawable.upbackground);
		productCategory.add(R.drawable.downbackground);

		addProductList();

		// listView = (ExpandableListView)
		// findViewById(R.id.el_expandablelistview);
		// listView.setAdapter(new MyAdapter());

		if (savedInstanceState == null) {
			getSupportFragmentManager().beginTransaction()
					.add(R.id.container, new PlaceholderFragment()).commit();
		}
	}

	public void addProductList() {

		String urlBionic = BaseUrl.BASE_SERVER_URL + "category/list.do?"
				+ "navId=100001";
		String urlSock = BaseUrl.BASE_SERVER_URL + "category/list.do?"
				+ "navId=100002";
		@SuppressWarnings("unused")
		DataRequestVolley bionicRequest = new DataRequestVolley(HttpMethod.GET,
				urlBionic, bionicListener, bErrorListener);

		DataRequestVolley sockRequest = new DataRequestVolley(HttpMethod.GET,
				urlSock, sockListener, sErrorListener);

		RequestQueueSingleton.getInstance(ProductMainActivity.this)
				.addToRequestQueue(bionicRequest);
		RequestQueueSingleton.getInstance(ProductMainActivity.this)
				.addToRequestQueue(sockRequest);
	}

	private Response.Listener<String> bionicListener = new Response.Listener<String>() {

		@Override
		public void onResponse(String response) {

			// TODO Auto-generated method stub
			bionicList = JsonUtil.toObjectList(response, Bionic.class);

		}

	};

	private Response.ErrorListener bErrorListener = new Response.ErrorListener() {

		@Override
		public void onErrorResponse(VolleyError error) {
			// TODO Auto-generated method stub
			ToastUtil.showToast(ProductMainActivity.this, error.getMessage());
		}

	};

	private Response.Listener<String> sockListener = new Response.Listener<String>() {

		@Override
		public void onResponse(String response) {
			// TODO Auto-generated method stub
			sockList = JsonUtil.toObjectList(response, Sock.class);
		}
	};

	private Response.ErrorListener sErrorListener = new Response.ErrorListener() {

		@Override
		public void onErrorResponse(VolleyError error) {
			// TODO Auto-generated method stub

		}
	};

	public class MyAdapter extends BaseExpandableListAdapter {

		@Override
		public Object getChild(int groupPosition, int childPosition) {
			// TODO Auto-generated method stub
			if (groupPosition == 0) {
				return bionicList.get(childPosition);
			} else {
				return sockList.get(childPosition);
			}

		}

		@Override
		public long getChildId(int groupPosition, int childPosition) {
			// TODO Auto-generated method stub
			return childPosition;
		}

		@Override
		public View getChildView(int groupPosition, int childPosition,
				boolean isLastChild, View convertView, ViewGroup parent) {
			// TODO Auto-generated method stub
			View view = convertView;
			ChildViewHolder cvh;
			if (view == null) {
				cvh = new ChildViewHolder();
				view = getLayoutInflater().inflate(
						R.layout.layout_product_childview, parent, false);

				cvh.mImageView = (NetworkImageView) view
						.findViewById(R.id.iv_productdetail);
				cvh.textView = (TextView) view
						.findViewById(R.id.tv_productdetail);
				view.setTag(cvh);
			} else {
				cvh = (ChildViewHolder) view.getTag();
			}
			if (groupPosition == 0) {
				new NetworkImage(cvh.mImageView, ProductMainActivity.this, bionicList.get(childPosition),
						30, 30); 
				cvh.textView.setText(bionicList.get(childPosition));
			} else {
				new NetworkImage(cvh.mImageView, ProductMainActivity.this, sockList.get(childPosition), 30,30);
				cvh.textView.setText(sockList.get(childPosition));
			}
			
			
			
			return view;
		}

		@Override
		public int getChildrenCount(int arg0) {
			// TODO Auto-generated method stub
			return children.get(arg0).size();
		}

		@Override
		public Object getGroup(int arg0) {
			// TODO Auto-generated method stub
			return productCategory.get(arg0);
		}

		@Override
		public int getGroupCount() {
			// TODO Auto-generated method stub
			return productCategory.size();
		}

		@Override
		public long getGroupId(int arg0) {
			// TODO Auto-generated method stub
			return arg0;
		}

		@Override
		public View getGroupView(int arg0, boolean arg1, View converView,
				ViewGroup arg3) {
			// TODO Auto-generated method stub
			View view = converView;
			GroupViewHolder vgh;
			if (converView == null) {
				vgh = new GroupViewHolder();
				view = getLayoutInflater().inflate(
						R.layout.layout_product_groupview, arg3, false);
				vgh.networkImageView = (NetworkImageView) view
						.findViewById(R.id.iv_groupview);
				view.setTag(vgh);
			} else {
				vgh = (GroupViewHolder) view.getTag();
			}
			vgh.networkImageView.setImageResource(productCategory.get(arg0));
			return view;
		}

		@Override
		public boolean hasStableIds() {
			// TODO Auto-generated method stub
			return true;
		}

		@Override
		public boolean isChildSelectable(int arg0, int arg1) {
			// TODO Auto-generated method stub
			return true;
		}

		class GroupViewHolder {

			NetworkImageView networkImageView;
		}

		class ChildViewHolder {
			NetworkImageView mImageView;
			TextView textView;
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.product_main, menu);
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
			View rootView = inflater.inflate(R.layout.fragment_product_main,
					container, false);
			return rootView;
		}
	}

}
