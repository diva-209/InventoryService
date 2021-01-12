/**
 * 
 */
package com.retail.uk.inventory.validation;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import com.retail.uk.inventory.exception.ProblemDetailsException;
import com.retail.uk.inventory.model.Delivery;
import com.retail.uk.inventory.model.Refund;
import com.retail.uk.inventory.model.Sale;
import com.retail.uk.inventory.model.StoreInfo;

/**
 * This class validates the stores information from request body.
 *
 */
@Component
public class StoreValidator {

	/**
	 * validates stores information
	 * 
	 * @param storesInfo
	 * @throws ProblemDetailsException
	 */
	public void validateStoresInfoRequest(List<StoreInfo> storesInfo) throws ProblemDetailsException {
		for (StoreInfo storeInfo : storesInfo) {

			if (storeInfo != null) {

				Integer storeId = storeInfo.getStoreId();
				List<Sale> salesInfo = storeInfo.getSale();
				List<Refund> refundInfo = storeInfo.getRefund();
				List<Delivery> deliveryInfo = storeInfo.getDelivery();
				validateStoreId(storeId);

				validateSalesItems(salesInfo, storeId);

				validateRefundItems(refundInfo, storeId);

				validateDeliveryInfo(deliveryInfo, storeId);

			}

		}
	}

	public void validateStoreId(int storeId) throws ProblemDetailsException {
		int storeIdLength = String.valueOf(storeId).length();
		if (storeIdLength != 5)
			throw new ProblemDetailsException(HttpStatus.BAD_REQUEST, HttpStatus.BAD_REQUEST.getReasonPhrase(),
					"storeId must be of 5 digits");
	}

	public void validateSalesItems(List<Sale> salesInfo, int storeId) throws ProblemDetailsException {
		for (Sale sale : salesInfo) {
			if (sale.getItemName().isBlank())
				throw new ProblemDetailsException(HttpStatus.BAD_REQUEST, HttpStatus.BAD_REQUEST.getReasonPhrase(),
						"sale/itemName is missing for storeId : " + storeId);
			if (sale.getItemName().length() > 40)
				throw new ProblemDetailsException(HttpStatus.BAD_REQUEST, HttpStatus.BAD_REQUEST.getReasonPhrase(),
						"sale/itemName should not excede 40 characters for storeId : " + storeId);
			Long itemId = sale.getItemId();
			if (itemId != null) {
				int itemIdSize = String.valueOf(itemId).length();
				if (itemIdSize != 12)
					throw new ProblemDetailsException(HttpStatus.BAD_REQUEST, HttpStatus.BAD_REQUEST.getReasonPhrase(),
							"sale/itemId must be of 12 digits for storeId : " + storeId);
			}
		}
	}

	public void validateRefundItems(List<Refund> refundInfo, int storeId) throws ProblemDetailsException {
		for (Refund refund : refundInfo) {
			if (refund.getItemName().isBlank())
				throw new ProblemDetailsException(HttpStatus.BAD_REQUEST, HttpStatus.BAD_REQUEST.getReasonPhrase(),
						"refund/itemName is missing for storeId : " + storeId);
			if (refund.getItemName().length() > 40)
				throw new ProblemDetailsException(HttpStatus.BAD_REQUEST, HttpStatus.BAD_REQUEST.getReasonPhrase(),
						"refund/itemName should not excede 40 characters for storeId : " + storeId);
			Long itemId = refund.getItemId();
			if (itemId != null) {
				int itemIdSize = String.valueOf(itemId).length();
				if (itemIdSize != 12)
					throw new ProblemDetailsException(HttpStatus.BAD_REQUEST, HttpStatus.BAD_REQUEST.getReasonPhrase(),
							"refund/itemId must be of 12 digits for storeId : " + storeId);
			}
		}
	}

	public void validateDeliveryInfo(List<Delivery> deliveryInfo, int storeId) throws ProblemDetailsException {
		for (Delivery delivery : deliveryInfo) {
			if (delivery.getItemName().isBlank())
				throw new ProblemDetailsException(HttpStatus.BAD_REQUEST, HttpStatus.BAD_REQUEST.getReasonPhrase(),
						"delivery/itemName is missing for storeId : " + storeId);
			if (delivery.getItemName().length() > 40)
				throw new ProblemDetailsException(HttpStatus.BAD_REQUEST, HttpStatus.BAD_REQUEST.getReasonPhrase(),
						"delivery/itemName should not excede 40 characters for storeId : " + storeId);
			Long itemId = delivery.getItemId();
			if (itemId != null) {
				int itemIdSize = String.valueOf(itemId).length();
				if (itemIdSize != 12)
					throw new ProblemDetailsException(HttpStatus.BAD_REQUEST, HttpStatus.BAD_REQUEST.getReasonPhrase(),
							"delivery/itemId must be of 12 digits for storeId : " + storeId);
			}
		}
	}
}
