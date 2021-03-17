package com.seller.quickbuy.QuickBuyApp.response;

import java.math.BigDecimal;

import lombok.Data;

@Data
public class SingleSerise  {
    private String name;
    private BigDecimal value;
    private BigDecimal id;
    
    

    public SingleSerise( String name, BigDecimal value){
        this.name  = name;
        this.value = value;
    }
    public SingleSerise( String name, BigDecimal value,BigDecimal id){
        this.name  = name;
        this.value = value;
        this.id = id;
    }
    
   
}
