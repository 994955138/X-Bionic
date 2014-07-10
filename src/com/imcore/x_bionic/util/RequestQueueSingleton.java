package com.imcore.x_bionic.util;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager.NameNotFoundException;
import android.net.http.AndroidHttpClient;
import android.os.Build;

import com.android.volley.Cache;
import com.android.volley.Network;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.BasicNetwork;
import com.android.volley.toolbox.DiskBasedCache;
import com.android.volley.toolbox.HttpClientStack;
import com.android.volley.toolbox.HttpStack;
import com.android.volley.toolbox.HurlStack;

/**
 * Volley请求队列单例
 * 
 * @author user
 */
public class RequestQueueSingleton {
	private static RequestQueueSingleton mInstance;

	private RequestQueue mRequestQueue;

	// 私有构造函数，初始化请求队列
	private RequestQueueSingleton(Context context) {
		Cache cache = new DiskBasedCache(context.getCacheDir(), 1024 * 1024);

		HttpStack stack = null;
		String userAgent = "volley/0";
		try {
			String packageName = context.getPackageName();
			PackageInfo info = context.getPackageManager().getPackageInfo(
					packageName, 0);
			userAgent = packageName + "/" + info.versionCode;
		} catch (NameNotFoundException e) {
		}
		if (Build.VERSION.SDK_INT >= 9) {
			stack = new HurlStack();
		} else {
			stack = new HttpClientStack(
					AndroidHttpClient.newInstance(userAgent));
		}

		Network network = new BasicNetwork(stack);
		mRequestQueue = new RequestQueue(cache, network);
		mRequestQueue.start();
	}

	public static RequestQueueSingleton getInstance(Context context) {
		if (mInstance == null) {
			mInstance = new RequestQueueSingleton(context);
		}
		return mInstance;
	}
	
	public RequestQueue getRequestQueue() {
		return mRequestQueue;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public void addToRequestQueue(Request request) {
		mRequestQueue.add(request);
	}

	public void stopRequestQueue() {
		mRequestQueue.stop();
	}
}
