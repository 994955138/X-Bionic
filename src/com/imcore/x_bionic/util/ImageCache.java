package com.imcore.x_bionic.util;

import java.io.File;

import android.graphics.Bitmap;
import android.support.v4.util.LruCache;

public class ImageCache {
	private static ImageCache instance;
	private LruCache<String, Bitmap> lruc;
	
	private ImageCache(){
		int maxsize = (int) ((Runtime.getRuntime().maxMemory()/1024)/8);
		lruc = new LruCache<String , Bitmap>(maxsize);
	}
	
	/**
	 * ��ȡImageCache����ʵ��
	 * @return
	 */
	synchronized static ImageCache getInstance(){
		if(instance == null){
			instance = new ImageCache();
		}
		return instance;
	}
	
	/**
	 * key ָ����ͼƬ�Ƿ񱻻���
	 * @param key
	 * @return
	 */
	protected synchronized boolean isCached(String key){
		return this.isExistsInMemory(key)? true:this.isExistsInLocal(key);
	}
	
	//�ж��Ƿ����ڴ���
	private boolean isExistsInMemory(String key){
		return (this.lruc.get(key)!= null);
	}
	
	//�ж��Ƿ񻺴�ǰ�����ж��Ƿ��ڱ��ش洢�豸��
	private boolean isExistsInLocal(String key){
		boolean isExist = true;
		String fileName = String.valueOf(key.hashCode()); // ȡͼƬ������url��hashֵ��Ϊ�ļ���
		File file = new File(StorageHelper.getAppDir(),fileName);
		if(!file.exists()){
			isExist = false;
		}
		return isExist;
	}
	
	/**
	 * �ӻ����л�ȡkey ָ����ͼƬ����
	 * 
	 * @param key
	 * @return
	 */
	protected Bitmap get(String key) {
		return this.get(key, 0, 0);
	}
	
	/**
	 * �ӻ����л�ȡkey ָ����ͼƬ���󣬲���ָ������֮��ĳߴ�
	 * 
	 * @param key
	 * @param reqWidth
	 * @param reqHeight
	 * @return
	 */
	protected Bitmap get(String key, int reqWidth, int reqHeight) {
		if(this.isExistsInMemory(key)){// �Ƿ����ڴ��д���
			return this.lruc.get(key);
		}
		if(reqWidth !=0 && reqHeight != 0){
			return this.get(key, reqWidth, reqHeight);
		}
		return null;
	}
	
	// �ӱ��ش洢�豸�ж�ȡkeyָ����ͼƬ
	private Bitmap getBitmapFromLocal(String key) {
		return this.getBitmapFromLocal(key, 0, 0);
	}

	// �ӱ��ش洢�豸�ж�ȡkeyָ����ͼƬ,ͬʱָ�����ź�Ŀ�߳ߴ�
	private Bitmap getBitmapFromLocal(String key, int reqWidth, int reqHeight) {
		Bitmap bitmap = null;
		if(reqHeight != 0 && reqWidth != 0){
			bitmap = StorageHelper.getBitmapFromLocal
					(String.valueOf(key.hashCode()), reqWidth, reqHeight);
		}else{
			bitmap = getBitmapFromLocal
					(String.valueOf(key.hashCode()));
		}
		// ��ȡ��֮����put���ڴ���
		this.put(key, bitmap);
		return bitmap;
	}

	/**
	 * ��ָ����Bitmap�����ڴ滺��
	 * 
	 * @param key
	 * @param bitmap
	 */
	private void put(String key, Bitmap bitmap) {
		if(key != null && bitmap != null){
			this.put(key, bitmap);
		}
	}
}
