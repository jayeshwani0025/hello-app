package com.seller.quickbuy.QuickBuyApp.response;

import lombok.Data;

@Data //for getters and setters
public class OperationResponse {
  public enum ResponseStatusEnum {SUCCESS, ERROR, WARNING, NO_ACCESS};
//  @ApiModelProperty(required = true)
  private ResponseStatusEnum  operationStatus;
  private String  operationMessage;
  
}
