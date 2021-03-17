package com.seller.quickbuy.QuickBuyApp.response;

import org.springframework.data.domain.Page;

import com.seller.quickbuy.QuickBuyApp.entity.ProductMaster;


public class CategoryPage {
    private String category;
    private Page<ProductMaster> page;

    public CategoryPage(String category, Page<ProductMaster> productInCategory) {
        this.category = category;
        this.page = productInCategory;
    }

 

	public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }



	public Page<ProductMaster> getPage() {
		return page;
	}



	public void setPage(Page<ProductMaster> page) {
		this.page = page;
	}



}
