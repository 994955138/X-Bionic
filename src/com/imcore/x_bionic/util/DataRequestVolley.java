package com.imcore.x_bionic.util;

import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.HttpHeaderParser;

public class DataRequestVolley extends Request<String>{
	

	private Response.Listener<String> mListener;
	
	public DataRequestVolley(int method, String url,Response.Listener<String>listener,Response.ErrorListener errorListener) {
		super(method, url,errorListener);
		// TODO Auto-generated constructor stub
		mListener=listener;
	}

	@Override
	protected Response<String> parseNetworkResponse(NetworkResponse response) {
		// TODO Auto-generated method stub
		try {
			String json=new String(response.data, HttpHeaderParser.parseCharset(response.headers));
			int status=Integer.parseInt(JsonUtil.getJsonValueByKey(json, "status"));
			if(status==200){
				String dataJson=JsonUtil.getJsonValueByKey(json, "data");
				return Response.success(dataJson, HttpHeaderParser.parseCacheHeaders(response));
			}else{
				String message=JsonUtil.getJsonValueByKey(json, "message");
				return Response.error(new VolleyError(message));
			}
			
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			return Response.error(new ParseError(e));
		}

	}

	@Override
	protected void deliverResponse(String response) {
		// TODO Auto-generated method stub
		mListener.onResponse(response);
	}

}
