package com.seller.quickbuy.QuickBuyApp.enums;


public enum OrderStatusEnum implements CodeEnum {
	NEW(0, "New OrderMain"), 
	FINISHED(1, "Finished"),
	CANCELED(2, "Canceled"),
	APPROVAL(3, "Penging For Approval") ,
	READYFORSHIPMENT(4, "Ready To Ship"),
	REJECTED(5, "Order Rejected") ;
    private  int code;
    private String msg;
    OrderStatusEnum(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    @Override
    public Integer getCode() {
        return code;
    }
}
