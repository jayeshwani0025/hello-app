package com.seller.quickbuy.QuickBuyApp.response;

import java.util.List;

public class RelatedProductResponse {

	private List<RelatedProducts> response;

	
	public RelatedProductResponse(List<RelatedProducts> response) {
		super();
		this.response = response;
	}

	public List<RelatedProducts> getResponse() {
		return response;
	}

	public void setResponse(List<RelatedProducts> response) {
		this.response = response;
	}

	
}
