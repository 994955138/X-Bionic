package com.imcore.x_bionic.util;

import java.io.File;
import java.util.Map;

public class MultipartFormEntity extends RequestEntity {
	private String fileFieldName;
	private File fileField;

	/**
	 * ���캯��
	 */
	public MultipartFormEntity() {
	}

	/**
	 * ���캯��
	 * 
	 * @param url ָ���������ַ 
	 */
	public MultipartFormEntity(String url) {
		super(url);
	}

	/**
	 * ����ļ��������
	 * 
	 * @return
	 */
	public String getFileFieldName() {
		return fileFieldName;
	}

	/**
	 * �����ļ��������
	 * 
	 * @param fileFieldName
	 */
	public void setFileFieldName(String fileFieldName) {
		this.fileFieldName = fileFieldName;
	}

	/**
	 * ����ļ���
	 * 
	 * @return
	 */
	public File getFileField() {
		return fileField;
	}

	/**
	 * �����ļ���
	 * 
	 * @param fileField
	 */
	public void setFileField(File fileField) {
		this.fileField = fileField;
	}

	public Map<String, Object> getTextFields() {
		// TODO Auto-generated method stub
		return null;
	}

}
