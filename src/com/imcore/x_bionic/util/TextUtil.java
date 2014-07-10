package com.imcore.x_bionic.util;

public class TextUtil {

	/**
	 * �жϸ����ַ����Ƿ�Ϊ���ַ���
	 * 
	 * @param source
	 * @return �������ַ����ǿ��ַ�������true�����򷵻�false
	 */
	public static boolean isEmptyString(String source) {
		if (source == null) {
			return true;
		} else if (source.length() == 0) {
			return true;
		}
		return false;
	}

	/**
	 * �ж�ָ�����ַ����Ƿ��ǺϷ��ĵ绰����
	 * 
	 * @param numberString
	 * @return
	 */
	public static boolean isPhoneNumber(String numberString) {
		boolean isNumber = false;
		if (!numberString.equals("")) {
			if (numberString.length() == 11
					&& (isNumber(numberString))
					&& (numberString.startsWith("13")
							|| numberString.startsWith("18")
							|| numberString.startsWith("15") || numberString
								.startsWith("14"))) {
				isNumber = true;
			}
		}

		return isNumber;
	}

	/**
	 * �жϸ������ı��Ƿ�������
	 * 
	 * @param numberString
	 * @return
	 */
	public static boolean isNumber(String numberString) {
		return numberString.matches("^[0-9]*$");
	}

	/**
	 * �������ȸ���������ʽ���ַ���ת����float��������
	 * 
	 * @param floatString
	 * @return
	 */
	public static float getFloat(String floatString) {
		float number = 0;
		try {
			number = Float.parseFloat(floatString);
		} catch (Exception e) {
			// TODO: handle exception
		}
		return number;
	}

	/**
	 * ��������ʽ���ַ���ת����int��������
	 * 
	 * @param intString
	 * @return
	 */
	public static int getInt(String intString) {
		int number = 0;
		try {
			number = Integer.parseInt(intString.trim());
		} catch (NumberFormatException e) {
			// TODO: handle exception
		}
		return number;
	}

	/**
	 * ��˫���ȸ���������ʽ���ַ���ת����float��������
	 * 
	 * @param doubleString
	 * @return
	 */
	public static Double getDouble(String doubleString) {
		Double number = 0d;
		try {
			number = Double.parseDouble(doubleString);
		} catch (Exception e) {
			// TODO: handle exception
		}
		return number;
	}
	
	
}
