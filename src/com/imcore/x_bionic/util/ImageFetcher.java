package com.imcore.x_bionic.util;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.ref.WeakReference;

import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ImageView;

public class ImageFetcher {
	private ImageCache mImageCache;

	private final static String IMAGE_FETCHER_DEBUG_TAG = "ImageFetcher";

	public ImageFetcher() {
		this.mImageCache = ImageCache.getInstance();
	}

	/**
	 * ��ȡͼƬ����
	 * 
	 * @param url
	 *            ͼƬ������·�� ,���urlҲ��ΪͼƬ�ڻ����е�key
	 * @param view
	 *            ��ʾͼƬ�õĿؼ�
	 */
	public void fetch(String url, ImageView view) {
		new ImageWorkerTask(url, view).execute();
	}

	// ͼƬ�����첽�����ڲ���
	private class ImageWorkerTask extends AsyncTask<Void, Void, Boolean> {
		private String url;
		private WeakReference<ImageView> weakImageView;
		private int reqWidth;
		private int reqHeight;

		protected ImageWorkerTask(String url, ImageView view) {
			this.url = url;
			weakImageView = new WeakReference<ImageView>(view);
			this.reqWidth = DisplayUtil.px2Dip(view.getContext(),
					view.getWidth());
			this.reqHeight = DisplayUtil.px2Dip(view.getContext(),
					view.getHeight());
		}

		@Override
		protected Boolean doInBackground(Void... params) {
			if (!mImageCache.isCached(url)) {
				boolean isSucc = downLoadImage(url);
				return isSucc;
			}
			return true;
		}

		@Override
		protected void onPostExecute(Boolean result) {
			super.onPostExecute(result);
			if (result) {
				Bitmap bm = null;
				if (this.reqWidth != 0 && this.reqHeight != 0) {
					bm = mImageCache.get(url, this.reqWidth, this.reqHeight);
				} else {
					bm = mImageCache.get(url);
				}
				if (weakImageView != null) {
					ImageView view = weakImageView.get();
					if (view != null) {
						// �жϵ�ǰImageView�Ƿ�����AdapterView�б����õ�
						@SuppressWarnings("unchecked")
						WeakReference<ImageWorkerTask> weakTask = (WeakReference<ImageWorkerTask>) view
								.getTag();
						if (weakTask != null) {
							// �����ʾ�����ã������õĻ��������ͼƬ
							view.setImageBitmap(null);
						} else {
							weakTask = new WeakReference<ImageWorkerTask>(this);
							view.setTag(weakTask);
						}

						view.setImageBitmap(bm);
					}
				}
			}
		}
	}

	/**
	 * ��ָ����url����ͼƬ�����ش洢��
	 * 
	 * @param url
	 * @return ����true��ʾͼƬ���سɹ�
	 */
	private boolean downLoadImage(String url) {
		File imageFile = new File(StorageHelper.getAppImageDir() + "/"
				+ url.hashCode());
		InputStream is = null;
		FileOutputStream out = null;

		boolean isSucc = false;
		try {
			if (!imageFile.exists()) {
				imageFile.createNewFile();
			}

			out = new FileOutputStream(imageFile.getAbsolutePath());
			is = HttpHelper.getInputStream(url);
			if (is != null) {
				byte[] buffer = new byte[128];
				int len = 0;
				while ((len = is.read(buffer)) != -1) {
					out.write(buffer, 0, len);
				}
				out.flush();
				isSucc = true;
			}
		} catch (FileNotFoundException e) {
			Log.e(IMAGE_FETCHER_DEBUG_TAG, "�ļ�δ�ҵ�");
		} catch (IOException e) {
			Log.e(IMAGE_FETCHER_DEBUG_TAG, e.getLocalizedMessage());
		} finally {
			HttpHelper.closeStream(out);
			HttpHelper.closeStream(is);
		}
		return isSucc;
	}
}
