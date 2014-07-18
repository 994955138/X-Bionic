package com.imcore.x_bionic.ui.product;

import java.util.ArrayList;
import java.util.List;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.BaseExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.imcore.x_bionic.R;
import com.imcore.x_bionic.datameta.BaseUrl;
import com.imcore.x_bionic.datameta.HttpMethod;
import com.imcore.x_bionic.model.CategoryDetail;
import com.imcore.x_bionic.util.DataRequestVolley;
import com.imcore.x_bionic.util.JsonUtil;
import com.imcore.x_bionic.util.RequestQueueSingleton;
import com.imcore.x_bionic.util.ToastUtil;

public class ProductMainActivity extends ActionBarActivity {
	/**
	 * 数据列表
	 */
	ExpandableListView listView;
	List<Integer> productCategory;
	List<CategoryDetail> bionicList;
	List<CategoryDetail> sockList;
	List<List<CategoryDetail>> productList;
	ProgressDialog pd;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_product_main);

		getLayoutData();

		listView = (ExpandableListView) findViewById(R.id.el_expandablelistview);
		listView.setAdapter(new MyAdapter());
		if (savedInstanceState == null) {
			getSupportFragmentManager().beginTransaction()
					.add(R.id.container, new PlaceholderFragment()).commit();
		}
	}

	public void getLayoutData() {

		productCategory = new ArrayList<Integer>();
		bionicList = new ArrayList<CategoryDetail>();
		sockList = new ArrayList<CategoryDetail>();
		productList = new ArrayList<List<CategoryDetail>>();

		productCategory.add(R.drawable.upbackground);
		productCategory.add(R.drawable.downbackground);

		String urlBionic = BaseUrl.BASE_SERVER_URL + "category/list.do?"
				+ "navId=100001";
		String urlSock = BaseUrl.BASE_SERVER_URL + "category/list.do?"
				+ "navId=100002";
		pd = new ProgressDialog(ProductMainActivity.this);
		pd = ProgressDialog.show(ProductMainActivity.this, "数据加载", "数据加载中...");
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
			bionicList = JsonUtil.toObjectList(response, CategoryDetail.class);
			productList.add(bionicList);
			pd.dismiss();
		}

	};

	private Response.ErrorListener bErrorListener = new Response.ErrorListener() {

		@Override
		public void onErrorResponse(VolleyError error) {
			// TODO Auto-generated method stub
			pd.dismiss();
			ToastUtil.showToast(ProductMainActivity.this, "加载失败,请检查网络");
		}

	};

	private Response.Listener<String> sockListener = new Response.Listener<String>() {

		@Override
		public void onResponse(String response) {
			// TODO Auto-generated method stub
			sockList = JsonUtil.toObjectList(response, CategoryDetail.class);
			productList.add(sockList);
		}
	};

	private Response.ErrorListener sErrorListener = new Response.ErrorListener() {

		@Override
		public void onErrorResponse(VolleyError error) {
			// TODO Auto-generated method stub
			ToastUtil.showToast(ProductMainActivity.this, "加载失败,请检查网络");
		}
	};

	public class MyAdapter extends BaseExpandableListAdapter {
public int gp;
		@Override
		public Object getChild(int groupPosition, int childPosition) {
			// TODO Auto-generated method stub
			return productList.get(groupPosition).get(0);

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
			View view = getLayoutInflater().inflate(
					R.layout.layout_category_childview, parent, false);
			GridView gridView = (GridView) view
					.findViewById(R.id.gv_product_detail);
			gridView.setAdapter(new GridAdapter(groupPosition));
			
			gp=groupPosition;
			gridView.setOnItemClickListener(new OnItemClickListener() {

				@Override
				public void onItemClick(AdapterView<?> arg0, View arg1,
						int arg2, long arg3) {
					// TODO Auto-generated method stub
					Intent intent=new Intent(ProductMainActivity.this,ProductListActivity.class);
					intent.putIntegerArrayListExtra("mParam",setSubNavParam(gp,productList.get(gp).get(arg2).id));
					startActivity(intent);
                    Log.i("subNavId",String.valueOf(arg2));   
				}
			});
			return view;
		}

		public ArrayList<Integer> setSubNavParam(int groupPosi, int sudI) {
			ArrayList<Integer> list = new ArrayList<Integer>();
			list.add(groupPosi);
			list.add(sudI);
			return list;

		}
		
		
		@Override
		public int getChildrenCount(int groupPosition) {
			// TODO Auto-generated method stub

			return 1;
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

			View view = getLayoutInflater().inflate(
					R.layout.layout_category_groupview, arg3, false);
			ImageView imageView = (ImageView) view
					.findViewById(R.id.iv_groupview);

			imageView.setImageResource(productCategory.get(arg0));
			return view;
		}

		@Override
		public boolean hasStableIds() {
			// TODO Auto-generated method stub
			return false;
		}

		@Override
		public boolean isChildSelectable(int arg0, int arg1) {
			// TODO Auto-generated method stub
			return true;
		}

	}

	class GridAdapter extends BaseAdapter {
		int groupPosition;


		public GridAdapter(int groupPosition) {
			this.groupPosition = groupPosition;
		}

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return productList.get(groupPosition).size();
		}

		@Override
		public Object getItem(int arg0) {
			// TODO Auto-generated method stub
			return productList.get(groupPosition).get(arg0);
		}

		@Override
		public long getItemId(int arg0) {
			// TODO Auto-generated method stub
			return arg0;
		}

		@Override
		public View getView(int arg0, View arg1, ViewGroup arg2) {
			// TODO Auto-generated method stub
			View view = arg1;
			ViewHolder vh;
			if (view == null) {
				vh = new ViewHolder();
				view = getLayoutInflater().inflate(
						R.layout.layout_category_grid, null);
				vh.nImageView = (NetworkImageView) view
						.findViewById(R.id.niv_prodeutimage);
				vh.textView = (TextView) view.findViewById(R.id.tv_description);
				view.setTag(vh);
			} else {
				vh = (ViewHolder) view.getTag();
			}
            ImageLoader imageLoader=RequestQueueSingleton.getInstance(getApplicationContext()).getImageLoader();
			vh.nImageView.setDefaultImageResId(R.drawable.default_image);
			vh.nImageView.setErrorImageResId(R.drawable.error_image);
			vh.nImageView.setImageUrl(BaseUrl.IMAGE_SERVER_URL
					+ productList.get(groupPosition).get(arg0).imageUrl
					+ BaseUrl.IMAGE_PNG_TAIL,imageLoader ); 
			vh.textView.setText(productList.get(groupPosition).get(arg0).name);		
					
			return view;
		}

		class ViewHolder {
			NetworkImageView nImageView;
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
