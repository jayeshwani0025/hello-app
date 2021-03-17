package com.seller.quickbuy.QuickBuyApp.enums;

public enum InventoryStatus implements CodeEnum {
	PUNE(0, "Pune"), 
	MUMBAI(1, "Mumbai"),
	DELHI(2, "Delhi");
	
    private  int code;
    private String msg;
    
    InventoryStatus(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    @Override
    public Integer getCode() {
        return code;
    }
}
