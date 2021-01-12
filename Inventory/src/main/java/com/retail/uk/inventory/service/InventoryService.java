/**
 * 
 */
package com.retail.uk.inventory.service;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.retail.uk.inventory.exception.ProblemDetailsException;
import com.retail.uk.inventory.model.Delivery;
import com.retail.uk.inventory.model.Refund;
import com.retail.uk.inventory.model.Sale;
import com.retail.uk.inventory.model.StoreInfo;
import com.retail.uk.inventory.model.UpdateStoreInfo;
import com.retail.uk.inventory.repository.StoreInfoRepository;
import com.retail.uk.inventory.validation.StoreValidator;

/**
 * service which has the implentations for the buisess operations.
 *
 */
@Service
public class InventoryService {

	@Autowired
	private StoreInfoRepository storeInfoRespository;

	@Autowired
	private StoreValidator storeValidator;

	public void addStoresInfo(List<StoreInfo> storesInfo) throws ProblemDetailsException {

		storeValidator.validateStoresInfoRequest(storesInfo);
		
		List<StoreInfo> existingStores = getAllStoresInfo();
		List<Integer> storeIds = new ArrayList<>() ;
		for(StoreInfo existingStoreInfo : existingStores) {
			Integer existingStoreId = existingStoreInfo.getStoreId();
			storeIds.add(existingStoreId);
		}
		for(StoreInfo newStoreInfo : storesInfo) {
			int newStoreId = newStoreInfo.getStoreId();
			if(storeIds.contains(newStoreId)) {
				throw new ProblemDetailsException(HttpStatus.BAD_REQUEST,
						HttpStatus.BAD_REQUEST.getReasonPhrase(),
						"Store Details with storeId : "+newStoreId+" allready exists. Please update the store details");
			}
		}
		storeInfoRespository.saveAll(storesInfo);
	}

	public StoreInfo getStoreInfoByStoreId(int storeId) throws ProblemDetailsException {

		return storeInfoRespository.findById(storeId)
				.orElseThrow(() -> new ProblemDetailsException(HttpStatus.BAD_REQUEST,
						HttpStatus.BAD_REQUEST.getReasonPhrase(),
						"Store details with the given storeId does not exist"));
	}

	public List<StoreInfo> getAllStoresInfo() {
		return storeInfoRespository.findAll();
	}

	public void deleteStoreInfoByStoreId(int storeId) throws ProblemDetailsException {
		boolean isPresent = storeInfoRespository.existsById(storeId);
		if (isPresent)
			storeInfoRespository.deleteById(storeId);
		else
			throw new ProblemDetailsException(HttpStatus.BAD_REQUEST, HttpStatus.BAD_REQUEST.getReasonPhrase(),
					"Store does not exist");

	}

	@Transactional
	public void updateStoresInfo(List<UpdateStoreInfo> updateStoresInfoRequest) throws ProblemDetailsException {

		for (UpdateStoreInfo updatedStoreInfo : updateStoresInfoRequest) {
			int storeId = updatedStoreInfo.getStoreId();

			if (storeInfoRespository.findById(storeId).isPresent()) {

				List<Sale> updatedSaleInfo = updatedStoreInfo.getSale();
				List<Refund> updatedRefundInfo = updatedStoreInfo.getRefund();
				List<Delivery> updatedDeliveryInfo = updatedStoreInfo.getDelivery();

				storeValidator.validateSalesItems(updatedSaleInfo, storeId);
				storeValidator.validateRefundItems(updatedRefundInfo, storeId);
				storeValidator.validateDeliveryInfo(updatedDeliveryInfo, storeId);

			} else
				throw new ProblemDetailsException(HttpStatus.BAD_REQUEST, HttpStatus.BAD_REQUEST.getReasonPhrase(),
						"Store with storeId : " + storeId + " does not exist");
		}

		for (UpdateStoreInfo updatedStoreInfo : updateStoresInfoRequest) {
			int storeId = updatedStoreInfo.getStoreId();
			StoreInfo existingStoreInfo = storeInfoRespository.findById(storeId).get();

			existingStoreInfo.setSale(updatedStoreInfo.getSale());
			existingStoreInfo.setRefund(updatedStoreInfo.getRefund());
			existingStoreInfo.setDelivery(updatedStoreInfo.getDelivery());
		}

	}

}
