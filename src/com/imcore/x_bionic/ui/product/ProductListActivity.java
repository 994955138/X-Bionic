package com.imcore.x_bionic.ui.product;

import java.util.ArrayList;
import java.util.List;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
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
import android.widget.GridView;
import android.widget.TextView;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.imcore.x_bionic.R;
import com.imcore.x_bionic.datameta.BaseUrl;
import com.imcore.x_bionic.datameta.HttpMethod;
import com.imcore.x_bionic.model.ProductList;
import com.imcore.x_bionic.model.SubCategoryDetail;
import com.imcore.x_bionic.util.DataRequestVolley;
import com.imcore.x_bionic.util.JsonUtil;
import com.imcore.x_bionic.util.RequestQueueSingleton;
import com.imcore.x_bionic.util.ToastUtil;

public class ProductListActivity extends ActionBarActivity {
	int nPosition;
	int mNavId;
	int mSubNavId;
    int pId;
	GridView gvProductList;
	List<SubCategoryDetail> scdList;
	ArrayList<ProductList> pdList;
	ProgressDialog pd;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_product_list);
		initialActionBar();
		pd = new ProgressDialog(ProductListActivity.this);
		getNavId();
		initList();

		getSubCategoryDetailList();
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

		Log.i("测试", String.valueOf(mNavId));
		Log.i("测试", String.valueOf(mSubNavId));
	}

	public void initList() {

		scdList = new ArrayList<SubCategoryDetail>();
		pdList = new ArrayList<ProductList>();
	}

	public void getSubCategoryDetailList() {

		String url = BaseUrl.BASE_SERVER_URL + "category/list.do?" + "navId="
				+ mNavId + "&" + "subNavId=" + mSubNavId;
		pd = ProgressDialog.show(ProductListActivity.this, "正在加载产品",
				"宝贝搬运中。。。。。。");
		DataRequestVolley dataRequestVolley = new DataRequestVolley(
				HttpMethod.GET, url, cListener, cErrorListener);
		RequestQueueSingleton.getInstance(ProductListActivity.this)
				.addToRequestQueue(dataRequestVolley);
	}

	public void getProductList(int id) {

		Log.i("测试", "进入列表函数");
		String url2 = BaseUrl.BASE_SERVER_URL + "category/products.do?"
				+ "navId=" + mNavId + "&" + "subNavId=" + mSubNavId + "&"
				+ "id=" + id;
		DataRequestVolley dataRequest = new DataRequestVolley(HttpMethod.GET,
				url2, pListener, pErrorListener);

		RequestQueueSingleton.getInstance(ProductListActivity.this)
				.addToRequestQueue(dataRequest);
	}

	private Response.Listener<String> cListener = new Response.Listener<String>() {

		@Override
		public void onResponse(String response) {
			// TODO Auto-generated method stub
			scdList = (ArrayList<SubCategoryDetail>) JsonUtil.toObjectList(
					response, SubCategoryDetail.class);
			getProductList(scdList.get(0).id);
		}
	};

	private Response.ErrorListener cErrorListener = new Response.ErrorListener() {

		@Override
		public void onErrorResponse(VolleyError error) {
			// TODO Auto-generated method stub
			pd.dismiss();
			ToastUtil.showToast(getApplicationContext(), "加载失败，请检查网络");
		}
	};

	/**
	 * 在加载线程的回调函数里面加载UI是因为，要在子线程数据加载完才能填充Ui。在Ui线程加载UI的话，子线程分发出去后UI线程就会接着执行，
	 * 这样子线程还未完成加载不到数据。 所以在子线程的回调函数里面执行UI加载，保证数据已经加载完。
	 * 
	 */
	private Response.Listener<String> pListener = new Response.Listener<String>() {

		@Override
		public void onResponse(String response) {
			// TODO Auto-generated method stub
			pdList = (ArrayList<ProductList>) JsonUtil.toObjectList(response,
					ProductList.class);
			Log.i("测试", "产品列表请求成功");

			Log.i("产品列表长度", "" + pdList.size());
			Log.i("分类长度", scdList.size() + "");
			pd.dismiss();
			gvProductList = (GridView) findViewById(R.id.gv_product_list);
			gvProductList.setAdapter(new GviewAdapter());
			gvProductList.setOnItemClickListener(new OnItemClickListener() {

				@Override
				public void onItemClick(AdapterView<?> arg0, View arg1,
						int arg2, long arg3) {
					// TODO Auto-generated method stub
					Intent intent=new Intent(ProductListActivity.this, ProductDetailsActivity.class);
					intent.putExtra("productId", pId);
					startActivity(intent);
				}
				
			});
		}
	};

	private Response.ErrorListener pErrorListener = new Response.ErrorListener() {

		@Override
		public void onErrorResponse(VolleyError error) {
			// TODO Auto-generated method stub
			pd.dismiss();
			ToastUtil.showToast(getApplicationContext(), "请检查网络");

		}

	};

	public void initialActionBar() {
		ActionBar actionBar = getSupportActionBar();
		actionBar.hide();
	}

	public class GviewAdapter extends BaseAdapter {

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return pdList.size();
		}

		@Override
		public Object getItem(int arg0) {
			// TODO Auto-generated method stub
			return pdList.get(arg0);
		}

		@Override
		public long getItemId(int arg0) {
			// TODO Auto-generated method stub
			return arg0;
		}

		@Override
		public View getView(int arg0, View arg1, ViewGroup arg2) {
			// TODO Auto-generated method stub
			pId=pdList.get(arg0).id;
			View view = arg1;
			ChildViewHolder cvh;
			if (view == null) {
				cvh = new ChildViewHolder();
				view = getLayoutInflater().inflate(
						R.layout.layout_product_desription, null);
				cvh.nivProduct = (NetworkImageView) view
						.findViewById(R.id.niv_product_image);
				cvh.tvPrice = (TextView) view
						.findViewById(R.id.tv_product_price);
				view.setTag(cvh);
			} else {
				cvh = (ChildViewHolder) view.getTag();
			}
			ImageLoader imageLoader = RequestQueueSingleton.getInstance(
					ProductListActivity.this).getImageLoader();
			String url = BaseUrl.PRODUCT_DETAIL_IMAGE_SERVER
					+ pdList.get(arg0).imageUrl + BaseUrl.IMAGE_JPG_TAIL;
			cvh.nivProduct.setDefaultImageResId(R.drawable.default_image);
			cvh.nivProduct.setErrorImageResId(R.drawable.error_image);
			cvh.nivProduct.setImageUrl(url, imageLoader);
			cvh.tvPrice.setText(pdList.get(arg0).name + "\n" + "价格:¥"
					+ pdList.get(arg0).price);
			Log.i("图片加载", "已执行图片加载");
			return view;
		}

		class ChildViewHolder {
			NetworkImageView nivProduct;
			TextView tvPrice;
		}

	}

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
			View rootView = inflater.inflate(R.layout.fragment_product_list,
					container, false);
			return rootView;
		}
	}

}
