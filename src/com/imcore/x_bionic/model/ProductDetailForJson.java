package com.imcore.x_bionic.model;

public class ProductDetailForJson {// 因为Json解析只包含基本数据类型，没有List类型，所以先获得color JSon
									// String 再进一步解析
	public int id;
	public String name;
	public double price;
	public String sysColorList;
}
