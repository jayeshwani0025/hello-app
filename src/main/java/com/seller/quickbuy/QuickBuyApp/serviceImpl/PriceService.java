package com.seller.quickbuy.QuickBuyApp.serviceImpl;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.seller.quickbuy.QuickBuyApp.entity.CartItem;
import com.seller.quickbuy.QuickBuyApp.entity.UserCart;

@Service
public class PriceService {

    public UserCart calculatePrice(UserCart cart) {

        Float totalPrice = 0F;
        Float totalCargoPrice = 0F;
        Long itemQuantity = 0L;
        List<Float> total = new ArrayList<Float>();
    	Long itemQuantity_v = 0L;
    	Long totalPrice_V =0L;
        try {
			for (CartItem i : cart.getCartItemList()) {
			
				try {
					if (i.getCartItemStatus().equalsIgnoreCase("P")) {
						i.getItemQuantity();
						 System.out.println("amount " + i.getAmount());
					     i.getCartProduct().getId();
					     System.out.println(i.getCartProduct().getId());
					     System.out.println(i.getCartProduct().getPrice());
					     
					     itemQuantity_v = i.getItemQuantity();
					     totalPrice_V = (i.getAmount() * itemQuantity_v);
					     totalPrice = totalPrice + totalPrice_V;
					     
//                 System.out.println();
					     itemQuantity = itemQuantity + itemQuantity_v;
//				     total.add(totalPrice);
					}
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			   
			}

//        //Applying discount percent if exists
//        if (cart.getCartDiscount() != null) {
////            totalPrice = totalPrice - ((totalPrice * cart.getCartDiscount().getDiscountPercentage()) / 100);
//        }

			cart.setTotalPrice(roundTwoDecimals(totalPrice));
			cart.setTotalCargoPrice(roundTwoDecimals(totalCargoPrice));
			cart.setItemQuantity(itemQuantity);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
//        System.out.println(cart); throws stackoverflow
        return cart;
    }

    private float roundTwoDecimals(float d) {
        DecimalFormat twoDForm = new DecimalFormat("#.##");
        return Float.valueOf(twoDForm.format(d));
    }

}
