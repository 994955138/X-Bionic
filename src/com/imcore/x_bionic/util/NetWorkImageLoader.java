package com.imcore.x_bionic.util;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager.NameNotFoundException;
import android.net.http.AndroidHttpClient;
import android.os.Build;

import com.android.volley.Cache;
import com.android.volley.Network;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.BasicNetwork;
import com.android.volley.toolbox.DiskBasedCache;
import com.android.volley.toolbox.HttpClientStack;
import com.android.volley.toolbox.HttpStack;
import com.android.volley.toolbox.HurlStack;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.ImageLoader.ImageCache;

public class NetWorkImageLoader {

	public ImageLoader getImageLoader(Context context){
		String userAgent = "volley/0";
		
			String packageName = context.getPackageName();
			PackageInfo info = null;
			HttpStack stack=null;
			try {
				info = context.getPackageManager().getPackageInfo(
						packageName, 0);
			} catch (NameNotFoundException e) {
				// TODO Auto-generated catch block
				
			}
		userAgent = packageName + "/" + info.versionCode;
		
		if (Build.VERSION.SDK_INT >= 9) {
			stack = new HurlStack();
		} else {
			stack = new HttpClientStack(
					AndroidHttpClient.newInstance(userAgent));
		}

		Network network=new BasicNetwork(stack);
		Cache cache=new DiskBasedCache(context.getCacheDir(), 2*1024*1024);
		RequestQueue queue=new RequestQueue(cache, network);
		queue.start();
		ImageCache imageCache=new LruImageCache(context);
		ImageLoader imageLoader=new ImageLoader(queue, imageCache);
		
		return imageLoader;
		
	}
	
}
