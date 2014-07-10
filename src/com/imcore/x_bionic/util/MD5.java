package com.imcore.x_bionic.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class MD5 {

	
	private static String byte2hex(byte[] bytes) {
		StringBuffer buf = new StringBuffer("");
		for (int i = 0; i < bytes.length; i++) {
			String t = Integer.toHexString(bytes[i] >= 0 ? bytes[i]
					: (bytes[i] + 256));
			if (t.length() < 2)
				t = "0" + t;
			buf.append(t.toLowerCase());
		}
		return buf.toString();
	}

	public static String hex_md5(String s) {
		MessageDigest md5_dig = null;
		try {
			md5_dig = MessageDigest.getInstance("md5");
		} catch (NoSuchAlgorithmException e) {
			// do nothing , it is impossible!!
			return "";
		}
		byte[] bytes = s.getBytes();
		md5_dig.update(bytes);
		return byte2hex(md5_dig.digest());
	}

	private MessageDigest md5_dig;

	public MD5() {
		try {
			md5_dig = MessageDigest.getInstance("md5");
		} catch (NoSuchAlgorithmException e) {
		}
	}

	public MD5(String s) {
		this();
		update(s);
	}

	public void update(String s) {
		byte[] bytes = s.getBytes();
		md5_dig.update(bytes);
	}

	public String hex_digest() {
		return byte2hex(md5_dig.digest());
	}
	
}
