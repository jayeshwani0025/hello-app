package com.seller.quickbuy.QuickBuyApp.form;

import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;


@Data
public class ItemForm {
    @Min(value = 1)
    private Long quantity;
    @NotEmpty
    private Long productId;
    private Long sellerInventoryId;
    private Long sellerProductId;
}
