package com.imcore.x_bionic.model;

public class ProductDetail {
String imageUrl;
String descriptionText;

public ProductDetail(String imageUrl,String descriptionText){
	this.imageUrl=imageUrl;
	this.descriptionText=descriptionText;
}

public String getImageUrl() {
	return imageUrl;
}

public void setImageUrl(String imageUrl) {
	this.imageUrl = imageUrl;
}

public String descriptionText() {
	return descriptionText;
}

public void descriptionText(String descriptionText) {
	this.descriptionText = descriptionText;
}


}
