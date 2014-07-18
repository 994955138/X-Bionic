package com.imcore.x_bionic.ui.product;

import java.util.ArrayList;
import java.util.List;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.imcore.x_bionic.R;
import com.imcore.x_bionic.datameta.BaseUrl;
import com.imcore.x_bionic.datameta.HttpMethod;
import com.imcore.x_bionic.model.ProductDetailForJson;
import com.imcore.x_bionic.model.ProductImage;
import com.imcore.x_bionic.model.SizeStanddard;
import com.imcore.x_bionic.model.SysColor;
import com.imcore.x_bionic.model.SysSize;
import com.imcore.x_bionic.util.DataRequestVolley;
import com.imcore.x_bionic.util.JsonUtil;
import com.imcore.x_bionic.util.RequestQueueSingleton;
import com.imcore.x_bionic.util.ToastUtil;

import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.support.v4.app.Fragment;
import android.app.ActionBar.LayoutParams;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.Gallery;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

@SuppressWarnings("deprecation")
public class ProductDetailsActivity extends ActionBarActivity {
	int productId;
	Gallery gvImage;
	List<ProductImage> imageUrlList;
	ProductDetailForJson pdForJson;
	List<SysColor> sysColorsList;
	List<SizeStanddard> standardSizeList;
	List<SysSize> sysSizeList;
	TextView pdName, pdPrice;
	ProgressDialog pd;
	LinearLayout llSysSize;
	TableLayout ssTableLayout;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_product_details);
		ActionBar actionBar=getSupportActionBar();
		actionBar.hide();
		pd = new ProgressDialog(ProductDetailsActivity.this);
		imageUrlList = new ArrayList<ProductImage>();
		pdForJson = new ProductDetailForJson();
		sysColorsList = new ArrayList<SysColor>();
		standardSizeList = new ArrayList<SizeStanddard>();
		sysSizeList = new ArrayList<SysSize>();
		getLastIntentData();
		initGallery();

		if (savedInstanceState == null) {
			getSupportFragmentManager().beginTransaction()
					.add(R.id.container, new PlaceholderFragment()).commit();
		}
	}

	public void getLastIntentData() {
		productId = this.getIntent().getIntExtra("productId", 0);
		Log.i("pId=", "" + productId);
	}

	public void initGallery() {
		String gUrl = BaseUrl.BASE_SERVER_URL + "product/images/list.do?"
				+ "id=" + productId;
		pd = ProgressDialog.show(ProductDetailsActivity.this, "加载宝贝",
				"加载中。。。。。。");
		DataRequestVolley gDataRequestVolley = new DataRequestVolley(
				HttpMethod.GET, gUrl, gListener, gErrorListener);
		RequestQueueSingleton.getInstance(ProductDetailsActivity.this)
				.addToRequestQueue(gDataRequestVolley);
	}

	/**
	 * 得到产品详情数据，包括价格、名称、颜色表。在请求成功的回调函数里面填充UI
	 * 
	 */
	public void getProductDetail() {
		String pdUrl = BaseUrl.BASE_SERVER_URL + "product/get.do?" + "id="
				+ productId;
		DataRequestVolley pdDataRequestVolley = new DataRequestVolley(
				HttpMethod.GET, pdUrl, pdListener, pdErrorListener);
		RequestQueueSingleton.getInstance(ProductDetailsActivity.this)
				.addToRequestQueue(pdDataRequestVolley);
	}

	public void setPdColor() {
		TextView tvColor = new TextView(ProductDetailsActivity.this);
		tvColor.setText("颜色:" + "  ");
        tvColor.setTextSize(18);
		LinearLayout pcLinearLayout = (LinearLayout) findViewById(R.id.ll_pb_colors);

		pcLinearLayout.addView(tvColor);

		for (SysColor sc : sysColorsList) {
			ImageLoader imageLoader = RequestQueueSingleton.getInstance(
					getApplicationContext()).getImageLoader();
			NetworkImageView nviImageView = new NetworkImageView(
					ProductDetailsActivity.this);
			nviImageView.setMaxWidth(40);
			nviImageView.setMaxHeight(40);
			nviImageView.setDefaultImageResId(R.drawable.default_image);
			nviImageView.setErrorImageResId(R.drawable.error_image);
			nviImageView.setImageUrl(BaseUrl.PRODUCT_DETAIL_IMAGE_SERVER
					+ sc.colorImage + BaseUrl.IMAGE_COLOR_TAIL, imageLoader);
			
			TextView tvCname=new TextView(ProductDetailsActivity.this);
			tvCname.setText(sc.color+" ");
			tvCname.setTextSize(18);
			pcLinearLayout.addView(nviImageView);
			pcLinearLayout.addView(tvCname);
		}
		getSize();
	}

	public void getSize() {
		String gsUrl = BaseUrl.BASE_SERVER_URL + "product/size/list.do?"
				+ "id=" + productId;
		DataRequestVolley gsDataRequestVolley = new DataRequestVolley(
				HttpMethod.GET, gsUrl, gsListener, gsErrorListener);
		RequestQueueSingleton.getInstance(ProductDetailsActivity.this)
				.addToRequestQueue(gsDataRequestVolley);
	}
	
	public void getLaveNunber(){
		
		
	}

	Response.Listener<String> gListener = new Response.Listener<String>() {

		@Override
		public void onResponse(String response) {
			// TODO Auto-generated method stub
			imageUrlList = JsonUtil.toObjectList(response, ProductImage.class);
			Log.i("图片列表Url长度", "" + imageUrlList.size());

			gvImage = (Gallery) findViewById(R.id.gv_imagegallery);
			gvImage.setAdapter(new GalleryAdapter());

			getProductDetail();
		}
	};
	Response.ErrorListener gErrorListener = new Response.ErrorListener() {

		@Override
		public void onErrorResponse(VolleyError error) {
			// TODO Auto-generated method stub
			pd.dismiss();
			ToastUtil.showToast(ProductDetailsActivity.this, "网络不给力,请重新加载");
		}
	};

	Response.Listener<String> pdListener = new Response.Listener<String>() {

		@Override
		public void onResponse(String response) {
			// TODO Auto-generated method stub

			pdForJson = JsonUtil.toObject(response, ProductDetailForJson.class);
			sysColorsList = JsonUtil.toObjectList(pdForJson.sysColorList,
					SysColor.class);
			Log.i("颜色列表长度", "" + sysColorsList.size());

			pdName = (TextView) findViewById(R.id.tv_productname);
			pdName.setText(pdForJson.name);
			pdPrice = (TextView) findViewById(R.id.tv_productprice);
			pdPrice.append("  " + "¥" + String.valueOf(pdForJson.price));
			setPdColor();
		}
	};

	Response.ErrorListener pdErrorListener = new Response.ErrorListener() {

		@Override
		public void onErrorResponse(VolleyError error) {
			// TODO Auto-generated method stub
			pd.dismiss();
			ToastUtil.showToast(ProductDetailsActivity.this, "网络不给力,请重新加载");
		}
	};

	Response.Listener<String> gsListener = new Response.Listener<String>() {

		@Override
		public void onResponse(String response) {
			// TODO Auto-generated method stub
			pd.dismiss();
			String sizeStandarStr = JsonUtil.getJsonValueByKey(response,
					"sizeStandardDetailList");
			String SysSizeStr = JsonUtil.getJsonValueByKey(response,
					"sysSizeList");
			standardSizeList = JsonUtil.toObjectList(sizeStandarStr,
					SizeStanddard.class);
			sysSizeList = JsonUtil.toObjectList(SysSizeStr, SysSize.class);
			Log.i("标准尺码列表长度", "" + standardSizeList.size());
			Log.i("尺码列表长度", "" + sysSizeList.size());

			llSysSize = (LinearLayout) findViewById(R.id.ll_productsize);
			TextView textView=new TextView(ProductDetailsActivity.this);
			textView.setText("尺码:");
			textView.setTextSize(18);
			llSysSize.addView(textView);
			for (SysSize ss : sysSizeList) {
				TextView mView=new TextView(ProductDetailsActivity.this);
				mView.setText("  " + ss.size + "  ");
				mView.setTextSize(18);
				llSysSize.addView(mView);
			}

			ssTableLayout = (TableLayout) findViewById(R.id.tl_sizestaandard);
			for (SizeStanddard ssd : standardSizeList) {
				TableRow ssTableRow = new TableRow(ProductDetailsActivity.this);

				TextView tvSize = new TextView(ProductDetailsActivity.this);
				tvSize.setText(ssd.size);
				tvSize.setTextSize(18);
				tvSize.setBackgroundResource(R.drawable.textview_fram);
				ssTableRow.addView(tvSize);

				TextView tvP1 = new TextView(ProductDetailsActivity.this);
				tvP1.setText(ssd.p1);
				tvP1.setTextSize(18);
				tvP1.setBackgroundResource(R.drawable.textview_fram);
				ssTableRow.addView(tvP1);

				TextView tvP2 = new TextView(ProductDetailsActivity.this);
				tvP2.setText(ssd.p2);
				tvP2.setTextSize(18);
				tvP2.setBackgroundResource(R.drawable.textview_fram);
				ssTableRow.addView(tvP2);

				TextView tvP3 = new TextView(ProductDetailsActivity.this);
				tvP3.setText(ssd.p3);
				tvP3.setTextSize(18);
				tvP3.setBackgroundResource(R.drawable.textview_fram);
				ssTableRow.addView(tvP3);

				TextView tvP4 = new TextView(ProductDetailsActivity.this);
				tvP4.setText(ssd.p4);
				tvP4.setTextSize(18);
				tvP4.setBackgroundResource(R.drawable.textview_fram);
				ssTableRow.addView(tvP4);

				TextView tvP5 = new TextView(ProductDetailsActivity.this);
				tvP5.setText(ssd.p5);
				tvP5.setTextSize(18);
				tvP5.setBackgroundResource(R.drawable.textview_fram);
				ssTableRow.addView(tvP5);

				TextView tvP6 = new TextView(ProductDetailsActivity.this);
				tvP6.setText(ssd.p6);
				tvP6.setTextSize(18);
				tvP6.setBackgroundResource(R.drawable.textview_fram);
				ssTableRow.addView(tvP6);

				TextView tvP7 = new TextView(ProductDetailsActivity.this);
				tvP7.setText(ssd.p7);
				tvP7.setTextSize(18);
				tvP7.setBackgroundResource(R.drawable.textview_fram);
				ssTableRow.addView(tvP7);

				TextView tvP8 = new TextView(ProductDetailsActivity.this);
				tvP8.setText(ssd.p8);
				tvP8.setTextSize(18);
				tvP8.setBackgroundResource(R.drawable.textview_fram);
				ssTableRow.addView(tvP8);

				TextView tvP9 = new TextView(ProductDetailsActivity.this);
				tvP9.setText(ssd.p9);
				tvP9.setTextSize(18);
				tvP9.setBackgroundResource(R.drawable.textview_fram);
				ssTableRow.addView(tvP9);

				TextView tvP10 = new TextView(ProductDetailsActivity.this);
				tvP10.setText(ssd.p10);
				tvP10.setTextSize(18);
				tvP10.setBackgroundResource(R.drawable.textview_fram);
				ssTableRow.addView(tvP10);

				TextView tvP11 = new TextView(ProductDetailsActivity.this);
				tvP11.setText(ssd.p11);
				tvP11.setTextSize(18);
				tvP11.setBackgroundResource(R.drawable.textview_fram);
				ssTableRow.addView(tvP11);

				ssTableLayout.addView(ssTableRow);
			}

		}

	};

	Response.ErrorListener gsErrorListener = new Response.ErrorListener() {

		@Override
		public void onErrorResponse(VolleyError error) {
			// TODO Auto-generated method stub
			pd.dismiss();
			ToastUtil.showToast(ProductDetailsActivity.this, "网络不给力,请重新加载");
		}
	};

	public class GalleryAdapter extends BaseAdapter {

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return imageUrlList.size();
		}

		@Override
		public Object getItem(int arg0) {
			// TODO Auto-generated method stub
			return imageUrlList.get(arg0);
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
			GViewHolder gvh;
			if (view == null) {
				gvh = new GViewHolder();
				view = getLayoutInflater().inflate(
						R.layout.layout_productdetail_gallery, null);
				gvh.nivImage = (NetworkImageView) view
						.findViewById(R.id.niv_product_detail_gallery);
				view.setTag(gvh);
			} else {
				gvh = (GViewHolder) view.getTag();
			}
			ImageLoader gImageLoader = RequestQueueSingleton.getInstance(
					getApplicationContext()).getImageLoader();
			gvh.nivImage.setDefaultImageResId(R.drawable.default_image);
			gvh.nivImage.setErrorImageResId(R.drawable.error_image);
			gvh.nivImage.setImageUrl(BaseUrl.PRODUCT_DETAIL_IMAGE_SERVER
					+ imageUrlList.get(arg0).image + BaseUrl.IMAGE_JPG_TAIL,
					gImageLoader);
			Log.i("gv图片加载", "已执行" );
			return view;
		}

		class GViewHolder {
			NetworkImageView nivImage;
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
			View rootView = inflater.inflate(R.layout.fragment_product_details,
					container, false);
			return rootView;
		}
	}

}
