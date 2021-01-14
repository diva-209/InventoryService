/**
 * 
 */
package com.retail.uk.inventory.utility;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.retail.uk.inventory.model.Delivery;
import com.retail.uk.inventory.model.Refund;
import com.retail.uk.inventory.model.Sale;
import com.retail.uk.inventory.model.StoreInfo;
import com.retail.uk.inventory.model.UpdateStoreInfo;

/**
 * class which has utility methods.
 *
 */
public class Utility {
	
	public String getJsonFromObject(Object object) throws JsonProcessingException {
		ObjectMapper objectMapper = new ObjectMapper();
		return objectMapper.writeValueAsString(object);
	}
	
	public List<StoreInfo> getMockDataForMultipleStores() {
		List<StoreInfo> stores = new ArrayList<StoreInfo>();
		List<Refund> refunds = new ArrayList<Refund>();
		List<Sale> saleItems = new ArrayList<Sale>();
		List<Delivery> deliveries = new ArrayList<Delivery>();
		
		StoreInfo store1 = new StoreInfo();
		StoreInfo store2 = new StoreInfo();
		
		Refund refund = new Refund();
		refund.setItemId(725896412345L);
		refund.setItemName("T-shirt");
		refund.setQuantity(4);
		refunds.add(refund);
		
		Sale sale = new Sale();
		sale.setItemId(725896412345L);
		sale.setItemName("T-shirt");
		sale.setQuantity(5);
		saleItems.add(sale);
		
		Delivery delivery = new Delivery();
		delivery.setItemId(725896412345L);	
		delivery.setItemName("T-shirt");
		delivery.setQuantity(6);
		deliveries.add(delivery);
		
		store1.setStoreId(99319);
		store1.setRefund(refunds);
		store1.setSale(saleItems);
		store1.setDelivery(deliveries);
		
		store2.setStoreId(99308);
		store2.setRefund(refunds);
		store2.setSale(saleItems);
		store2.setDelivery(deliveries);
		
		stores.add(store1);
		stores.add(store2);
		
		return stores;
	}
	
	
	public List<UpdateStoreInfo> getMockDataForUpdatingMultipleStores() {
		List<UpdateStoreInfo> stores = new ArrayList<UpdateStoreInfo>();
		List<Refund> refunds = new ArrayList<Refund>();
		List<Sale> saleItems = new ArrayList<Sale>();
		List<Delivery> deliveries = new ArrayList<Delivery>();
		
		UpdateStoreInfo store1 = new UpdateStoreInfo();
		UpdateStoreInfo store2 = new UpdateStoreInfo();
		
		Refund refund = new Refund();
		refund.setItemId(725896412345L);
		refund.setItemName("Jeans");
		refund.setQuantity(4);
		refunds.add(refund);
		
		Sale sale = new Sale();
		sale.setItemId(725896412345L);
		sale.setItemName("T-shirt");
		sale.setQuantity(7);
		saleItems.add(sale);
		
		Delivery delivery = new Delivery();
		delivery.setItemId(725896412345L);	
		delivery.setItemName("Trousers");
		delivery.setQuantity(6);
		deliveries.add(delivery);
		
		store1.setStoreId(99319);
		store1.setRefund(refunds);
		store1.setSale(saleItems);
		store1.setDelivery(deliveries);
		
		store2.setStoreId(99308);
		store2.setRefund(refunds);
		store2.setSale(saleItems);
		store2.setDelivery(deliveries);
		
		stores.add(store1);
		stores.add(store2);
		
		return stores;
	}

}
