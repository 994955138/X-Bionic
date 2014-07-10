package com.imcore.x_bionic.util;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;








import com.imcore.x_bionic.datameta.HttpMethod;

import android.util.Log;

public class HttpHelper {
	private static final String DOMAIN_URL = "http://bulo2bulo.com:8080/mobile/api";

	private static final String LOG_HTTP_POST_INFO = "REQUEST";
	private static final String LOG_HTTP_GET_ERROR = "com.imcore.x_bionic.http.GetError";
	private static final String LOG_HTTP_POST_ERROR = "com.imcore.x_bionic.http.PostError";

	private static final String CONTENT_TYPE_URL_ENCODED = "application/x-www-form-urlencoded";
	private static final String CHARSET = "utf-8";
	
	/**
	 * 发�?http请求，获得响应数�?
	 * 
	 * @param entity
	 *            包含请求实体信息的RequestEntity实例
	 * @return 返回服务器端响应的JSON字符串结�?
	 * @throws Exception
	 */
	public synchronized static String execute(RequestEntity entity)
			throws Exception {
		String jsonResult = "";

		String url = DOMAIN_URL + entity.getUrl();
		switch (entity.getMethod()) {
		case HttpMethod.GET:
			if (entity.getTextFields() == null) {
				jsonResult = get(url);
			} else {
				jsonResult = get(url, entity.getTextFields());
			}
			break;
		case HttpMethod.POST:
			if (entity instanceof MultipartFormEntity) {
				MultipartFormEntity multipartFormEntity = (MultipartFormEntity)entity;
				jsonResult = postMultipartForm(url, multipartFormEntity);
			} else {
				if (entity.getTextFields() == null) {
					jsonResult = post(url);
				} else {
					jsonResult = post(url, entity.getTextFields());
				}
			}
			break;
		}

		return jsonResult;
	}

	private synchronized static String get(String url) throws Exception {
		return get(url, null);
	}

	/**
	 * 执行GET请求
	 */
	private synchronized static String get(String url, Map<String, Object> params)
			throws Exception {
		String jsonResult = "";
		InputStream is = null;
		try {
			if (params != null && params.size() > 0) {
				String urlEncodedForm = toUrlEncodedFormParams(params);
				url = url + "?" + urlEncodedForm;
			}
			HttpURLConnection conn = getHttpURLConnection(url);
			conn.setRequestMethod("GET");
			if (conn.getResponseCode() == 200) {
				is = conn.getInputStream();
				jsonResult = read(is);
				Log.i(LOG_HTTP_GET_ERROR, jsonResult);
			} else {
				throw (new Exception());
			}
		} catch (MalformedURLException e) {
			Log.e(LOG_HTTP_GET_ERROR, e.getLocalizedMessage());
			e.printStackTrace();
			throw (e);
		} catch (IOException e) {
			Log.e(LOG_HTTP_GET_ERROR, e.getLocalizedMessage());
			e.printStackTrace();
			throw (e);
		} finally {
			closeStream(is);
		}

		return jsonResult;
	}

	private synchronized static String post(String url) throws Exception {
		return post(url, null);
	}

	/**
	 * 执行http post请求
	 * 
	 * @param url
	 *            请求的服务器端api的链�?
	 * @param params
	 *            包含请求参数的Map
	 * @return 返回Json格式的响应数�?
	 * @throws Exception
	 */
	private synchronized static String post(String url,
			Map<String, Object> params) throws Exception {
		String jsonResult = "";
		OutputStream os = null;
		InputStream is = null;

		try {
			HttpURLConnection conn = getHttpURLConnection(url);
			conn.setDoOutput(true);
			conn.setUseCaches(false);

			conn.setRequestMethod("POST");
			conn.setRequestProperty("Content-type", CONTENT_TYPE_URL_ENCODED);
			conn.setRequestProperty("Charset", CHARSET);

			os = conn.getOutputStream();
			if (params != null && params.size() > 0) {
				String urlEncodedForm = toUrlEncodedFormParams(params);
				Log.i(LOG_HTTP_POST_INFO, urlEncodedForm);
				os.write(urlEncodedForm.getBytes());
				os.flush();
			}

			if (conn.getResponseCode() == 200) {
				is = conn.getInputStream();
				jsonResult = read(is);
				Log.i("Post", jsonResult);
			} else {
				throw (new Exception());
			}
		} catch (MalformedURLException e) {
			e.printStackTrace();
			throw (e);
		} catch (IOException e) {
			Log.e(LOG_HTTP_POST_ERROR, e.getLocalizedMessage());
			e.printStackTrace();
			throw (e);
		} finally {
			closeStream(is);
			closeStream(os);
		}
		return jsonResult;
	}
	
	/**
	 * 执行http GET请求，获取链接成功后的输入流，为了下载文件时使用
	 * 
	 * @param url
	 *            请求的服务器端api的链�?
	 * @return 返回�?��输入流对象实�?
	 */
	public synchronized static InputStream getInputStream(String url) {
		InputStream is = null;
		try {
			HttpURLConnection conn = getHttpURLConnection(url);
			conn.setRequestMethod("GET");
			if (conn.getResponseCode() == 200) {
				is = conn.getInputStream();
			}
		} catch (MalformedURLException e) {
			Log.e(LOG_HTTP_GET_ERROR, e.getLocalizedMessage());
			e.printStackTrace();
		} catch (IOException e) {
			Log.e(LOG_HTTP_GET_ERROR, e.getLocalizedMessage());
			e.printStackTrace();
		}
		return is;
	}

	/**
	 * 获得HttpURLConnection连接实例
	 * 
	 * @param strURL
	 *            服务器端api的链�?
	 * @return 返回HttpURLConnection实例
	 * @throws IOException
	 */
	private static HttpURLConnection getHttpURLConnection(String strURL)
			throws IOException {
		URL url = new URL(strURL);
		HttpURLConnection conn = (HttpURLConnection) url.openConnection();
		conn.setConnectTimeout(10000);
		conn.setReadTimeout(15000);
		return conn;
	}

	/**
	 * 从输入流中读出文本信�?
	 * 
	 * @param is
	 *            指定的输入流
	 * @return
	 * @throws IOException
	 */
	private static String read(InputStream is) throws IOException {
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		byte[] buffer = new byte[128];
		int len = 0;
		while ((len = is.read(buffer)) != -1) {
			out.write(buffer, 0, len);
		}

		String text = new String(out.toByteArray(), "utf-8");
		out.flush();
		closeStream(out);
		return text;
	}

	/**
	 * 将包含http post请求数据的map，解析为UrlEncoded格式的字符串
	 * 
	 * @param params
	 *            包含请求参数的Map
	 * @return 返回解析后的UrlEncoded格式的字符串
	 */
	private static String toUrlEncodedFormParams(Map<String, Object> params) {
		StringBuffer strBuffer = new StringBuffer();
		Set<String> keySet = params.keySet();
		Iterator<String> i = keySet.iterator();
		while (i.hasNext()) {
			String key = i.next();
			String value = params.get(key).toString();
			strBuffer.append(key);
			strBuffer.append("=");
			strBuffer.append(value);
			if (i.hasNext()) {
				strBuffer.append("&");
			}
		}
		return strBuffer.toString();
	}
	
	/**
	 * 执行http post请求,发�?复合表单数据，如上传文件时调用此方法
	 * 
	 * @param url
	 *            请求的服务器端api的链�?
	 * @param entity
	 *            包含Multipart Form格式的请求实�?
	 * @return 返回Json格式的响应数�?
	 */
	public synchronized static String postMultipartForm(String url,
			MultipartFormEntity entity) {
		String BOUNDARY = java.util.UUID.randomUUID().toString();
		String PREFIX = "--", LINEND = "\r\n";
		String MULTIPART_FROM_DATA = "multipart/form-data";

		String resultStr = null;
		HttpURLConnection conn = null;
		DataOutputStream outStream = null;
		try {
			conn = getHttpURLConnection(url);

			conn.setDoInput(true);// 允许输入
			conn.setDoOutput(true);// 允许输出
			conn.setUseCaches(false);
			conn.setRequestMethod("POST"); // Post方式
			conn.setRequestProperty("connection", "keep-alive");
			conn.setRequestProperty("Charset", CHARSET);

			conn.setRequestProperty("Content-Type", MULTIPART_FROM_DATA
					+ ";boundary=" + BOUNDARY);

			outStream = new DataOutputStream(conn.getOutputStream());

			// 首先组拼文本类型的参�?
			if (entity.getTextFields() != null) {
				StringBuilder sb = new StringBuilder();
				for (Map.Entry<String, Object> entry : entity.getTextFields()
						.entrySet()) {
					sb.append(PREFIX);
					sb.append(BOUNDARY);
					sb.append(LINEND);
					sb.append("Content-Disposition: form-data; name=\""
							+ entry.getKey() + "\"" + LINEND);
					sb.append("Content-Type: text/plain; charset=" + CHARSET
							+ LINEND);
					sb.append("Content-Transfer-Encoding: 8bit" + LINEND);
					sb.append(LINEND);
					sb.append(entry.getValue().toString());
					sb.append(LINEND);
				}
				outStream.write(sb.toString().getBytes());
			}

			if (entity.getFileField() != null) {
				StringBuilder sb1 = new StringBuilder();
				sb1.append(PREFIX);
				sb1.append(BOUNDARY);
				sb1.append(LINEND);
				sb1.append("Content-Disposition: form-data; name=\"file\"; filename=\""
						+ entity.getFileFieldName() + "\"" + LINEND);
				sb1.append("Content-Type: application/octet-stream; charset="
						+ CHARSET + LINEND);
				sb1.append(LINEND);
				outStream.write(sb1.toString().getBytes());

				InputStream is = new FileInputStream(entity.getFileField());
				byte[] buffer = new byte[1024];
				int len = 0;
				while ((len = is.read(buffer)) != -1) {
					outStream.write(buffer, 0, len);
				}

				closeStream(is);
				outStream.write(LINEND.getBytes());
			}

			// 请求结束标�?
			byte[] end_data = (PREFIX + BOUNDARY + PREFIX + LINEND).getBytes();
			outStream.write(end_data);
			outStream.flush();
			resultStr = read(conn.getInputStream());

			Log.d("httpPost", "url:" + url);
			Log.d("httpPost", "result:" + resultStr);
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			closeStream(outStream);
			conn.disconnect();
		}
		return resultStr;
	}

	/**
	 * 关闭IO�?
	 * 
	 * @param obj
	 *            �?��输入流或输出流对象实�?
	 */
	public static void closeStream(Object obj) {
		if (obj != null && obj instanceof InputStream) {
			InputStream is = (InputStream) obj;
			try {
				is.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		} else if (obj != null && obj instanceof OutputStream) {
			OutputStream os = (OutputStream) obj;
			try {
				os.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}