package com.imcore.x_bionic.util;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;

import com.android.volley.Response;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageRequest;
import com.android.volley.toolbox.NetworkImageView;
import com.imcore.x_bionic.R;

public class NetworkImage {
	NetworkImageView nImageview;
	String url;
	int width;
	int height;
	Context context;
public NetworkImage(NetworkImageView nImageview,Context context,String url,int width,int height){
	this.nImageview=nImageview;
	this.url=url;
	this.width=width;
	this.height=height;
	this.context=context;
}

public void dowloadImage(){
	
	ImageRequest iRequest=new ImageRequest(url, listener, width, height, Config.RGB_565, errorListener);
	RequestQueueSingleton.getInstance(context).addToRequestQueue(iRequest);
}

Response.Listener<Bitmap> listener=new Listener<Bitmap>() {

	@Override
	public void onResponse(Bitmap response) {
		// TODO Auto-generated method stub
		nImageview.setImageBitmap(response);
	}
	
};

Response.ErrorListener errorListener=new ErrorListener() {

	@Override
	public void onErrorResponse(VolleyError error) {
		// TODO Auto-generated method stub
		nImageview.setImageResource(R.drawable.xlogo);
	}
	
};



}
