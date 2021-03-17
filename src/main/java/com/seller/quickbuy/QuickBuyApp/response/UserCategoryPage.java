package com.seller.quickbuy.QuickBuyApp.response;

import org.springframework.data.domain.Page;

public class UserCategoryPage {

	private String category;
	private Page<ProductMasterResponse> page;
	
	public UserCategoryPage(String category, Page<ProductMasterResponse> productInCategory) {
        this.category = category;
        this.page = productInCategory;
    }
	
	public String getCategory() {
		return category;
	}
	public void setCategory(String category) {
		this.category = category;
	}
	public Page<ProductMasterResponse> getPage() {
		return page;
	}
	public void setPage(Page<ProductMasterResponse> page) {
		this.page = page;
	}

	
}
