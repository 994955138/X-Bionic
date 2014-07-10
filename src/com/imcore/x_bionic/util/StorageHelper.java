package com.imcore.x_bionic.util;

import java.io.File;

import android.content.ContentResolver;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapFactory.Options;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.provider.MediaStore.MediaColumns;
import android.util.Log;

public class StorageHelper {
	public final static String APP_DIR_ROOT = "YunMing";
	public final static String APP_DIR_IMAGE = "YunMing/images";

	private final static String LOG_DEBUG_TAG = "gh_storage";

	// ��������ߴ�������ʵ��
	private StorageHelper() {
	}

	/**
	 * �ж��ⲿ�洢���Ƿ��д��
	 * 
	 * @return ����һ������ֵ��true��ʾ�ⲿ�洢����д��
	 */
	public static boolean isExternalStorageWritable() {
		String state = Environment.getExternalStorageState();
		if (Environment.MEDIA_MOUNTED.equals(state)) {
			return true;
		}
		return false;
	}

	/**
	 * �ж��ⲿ�洢���Ƿ�ɶ�
	 * 
	 * @return ����һ������ֵ��true��ʾ��洢���ɶ�
	 */
	public static boolean isExternalStorageReadable() {
		String state = Environment.getExternalStorageState();
		if (Environment.MEDIA_MOUNTED.equals(state)
				|| Environment.MEDIA_MOUNTED_READ_ONLY.equals(state)) {
			return true;
		}
		return false;
	}

	/**
	 * ��ȡ��ǰapp�ļ��洢·��
	 * 
	 * @return ����File����ʵ������ʾ��ǰapp�ļ��洢��Ŀ¼
	 */
	public static File getAppDir() {
		File appDir = null;
		if (isExternalStorageWritable()) {
			appDir = new File(Environment.getExternalStorageDirectory() + "/"
					+ APP_DIR_ROOT);
			if (!appDir.exists()) {
				appDir.mkdirs();
			}
		} else {
			Log.e(LOG_DEBUG_TAG, "�ⲿ�洢������д��");
		}
		return appDir;
	}

	/**
	 * ��ȡ��ǰapp���ͼƬ�ļ���Ŀ¼
	 * 
	 * @return ����File����ʵ������ʾ��ǰapp�洢ͼƬ��Ŀ¼
	 */
	public static File getAppImageDir() {
		File appDir = null;
		if (isExternalStorageWritable()) {
			appDir = new File(Environment.getExternalStorageDirectory() + "/"
					+ APP_DIR_IMAGE);
			if (!appDir.exists()) {
				appDir.mkdirs();
			}
		} else {
			Log.d(LOG_DEBUG_TAG, "�ⲿ�洢������д��");
		}
		return appDir;
	}

	/**
	 * �ӱ��ض�ȡͼƬ������һ��Bitmap����
	 * 
	 * @param imgName
	 *            ͼƬ��
	 * @return ���ػ�ȡ������ͼƬ��Bitmapʵ��
	 */
	public static Bitmap getBitmapFromLocal(String imgName) {
		if (!isExternalStorageReadable()) {
			Log.d(LOG_DEBUG_TAG, "�ⲿ�洢�����ɶ�");
			return null;
		}
		File imageFile = new File(getAppImageDir(), imgName);
		Bitmap bm = BitmapFactory.decodeFile(imageFile.getAbsolutePath());
		return bm;
	}

	/**
	 * �ӱ��ض�ȡͼƬ������һ��Bitmap����
	 * 
	 * @param imgName
	 *            ͼƬ��
	 * @param reqWidth
	 *            Ҫ��ȡͼƬ��Ŀ����
	 * @param reqHeight
	 *            Ҫ��ȡͼƬ��Ŀ��߶�
	 * @return ���ػ�ȡ������ͼƬ��Bitmapʵ��
	 */
	public static Bitmap getBitmapFromLocal(String imgName, int reqWidth,
			int reqHeight) {
		if (!isExternalStorageReadable()) {
			Log.d(LOG_DEBUG_TAG, "�ⲿ�洢�����ɶ�");
			return null;
		}
		Options opts = new Options();
		opts.inJustDecodeBounds = true;
		BitmapFactory.decodeFile(getAppImageDir() + "/" + imgName, opts);
		opts.inSampleSize = calculateInSampleSize(opts, reqWidth, reqHeight);
		opts.inJustDecodeBounds = false;

		File imageFile = new File(getAppImageDir(), imgName);
		Bitmap bm = BitmapFactory.decodeFile(imageFile.getAbsolutePath(), opts);

		return bm;
	}

	/**
	 * ����ͼƬinSampleSize���ű�
	 * 
	 * @param options
	 * @param reqWidth
	 * @param reqHeight
	 * @return
	 */
	private static int calculateInSampleSize(Options options, int reqWidth,
			int reqHeight) {
		final int height = options.outHeight;
		final int width = options.outWidth;
		int inSampleSize = 1;

		if (height > reqHeight || width > reqWidth) {
			final int heightRatio = Math.round((float) height
					/ (float) reqHeight);
			final int widthRatio = Math.round((float) width / (float) reqWidth);
			inSampleSize = heightRatio < widthRatio ? heightRatio : widthRatio;

			final float totalPixels = width * height;
			final float totalReqPixelsCap = reqWidth * reqHeight * 2;
			while (totalPixels / (inSampleSize * inSampleSize) > totalReqPixelsCap) {
				inSampleSize++;
			}
		}
		return inSampleSize;
	}

	/**
	 * ��ȡuriָ�����ļ�����ʵ·��
	 * 
	 * @param resolver
	 *            ContentResolverʵ��
	 * @param uri
	 *            ָ���ļ���uri
	 * @return ����һ���ַ�������ʾ��ȡ�����ļ�����·��
	 */
	public static String getRealPathByUri(ContentResolver resolver, Uri uri) {
		String[] proj = new String[] { MediaColumns.DATA };
		Cursor cursor = MediaStore.Images.Media.query(resolver, uri, proj);
		String realPath = null;
		if (cursor != null && cursor.moveToFirst()) {
			int columnIndex = cursor.getColumnIndexOrThrow(MediaColumns.DATA);
			if (cursor.getCount() > 0 && cursor.moveToFirst()) {
				realPath = cursor.getString(columnIndex);
			}
		}
		cursor.close();
		return realPath;
	}
}
