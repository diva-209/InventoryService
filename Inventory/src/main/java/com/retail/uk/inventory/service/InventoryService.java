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

	/**
	 * This method is used to add the details of the Stores.
	 * 
	 * @param storesInfo
	 * @throws ProblemDetailsException
	 */
	public List<StoreInfo> addStoresInfo(List<StoreInfo> storesInfo) throws ProblemDetailsException {

		storeValidator.validateStoresInfoRequest(storesInfo);

		checkForDuplicateStoresAndThrowException(storesInfo);

		return storeInfoRespository.saveAll(storesInfo);
	}

	/**
	 * This method retrieves the Store Information by storeId
	 * 
	 * @param storeId
	 * @return
	 * @throws ProblemDetailsException
	 */
	public StoreInfo getStoreInfoByStoreId(int storeId) throws ProblemDetailsException {

		return storeInfoRespository.findById(storeId)
				.orElseThrow(() -> new ProblemDetailsException(HttpStatus.BAD_REQUEST,
						HttpStatus.BAD_REQUEST.getReasonPhrase(),
						"Store details with the given storeId does not exist"));
	}

	/**
	 * This method retrives all the stores from DB
	 * 
	 * @return
	 */
	public List<StoreInfo> getAllStoresInfo() {
		return storeInfoRespository.findAll();
	}

	/**
	 * This method deletes the Store Info for the requested storedId
	 * 
	 * @param storeId
	 * @throws ProblemDetailsException
	 */
	public void deleteStoreInfoByStoreId(int storeId) throws ProblemDetailsException {
		boolean isPresent = storeInfoRespository.existsById(storeId);
		if (isPresent)
			storeInfoRespository.deleteById(storeId);
		else
			throw new ProblemDetailsException(HttpStatus.BAD_REQUEST, HttpStatus.BAD_REQUEST.getReasonPhrase(),
					"Store does not exist");

	}

	/**
	 * This method updates the Store details for the single as well as multiple
	 * Stores in the update request and throws exception in case of wrong data
	 * updatation.
	 * 
	 * @param updateStoresInfoRequest
	 * @throws ProblemDetailsException
	 */
	@Transactional
	public List<StoreInfo> updateStoresInfo(List<UpdateStoreInfo> updateStoresInfoRequest) throws ProblemDetailsException {

		for (UpdateStoreInfo updatedStoreInfo : updateStoresInfoRequest) {

			validateStoreDetailsBeforeUpdating(updatedStoreInfo);

		}

		return updateStoresDetails(updateStoresInfoRequest);

	}

	private List<StoreInfo> updateStoresDetails(List<UpdateStoreInfo> updateStoresInfoRequest) {
		List<StoreInfo> existingStores = getAllStoresInfo();

		for (UpdateStoreInfo updatedStoreInfo : updateStoresInfoRequest) {
			int storeId = updatedStoreInfo.getStoreId();
			for (StoreInfo existingStore : existingStores) {
				int existingStoreId = existingStore.getStoreId();
				if (existingStoreId == storeId) {
					existingStore.setSale(updatedStoreInfo.getSale());
					existingStore.setRefund(updatedStoreInfo.getRefund());
					existingStore.setDelivery(updatedStoreInfo.getDelivery());
				}
			}
		}
		
		return existingStores;

	}

	/**
	 * Checks for duplicate stores before adding to DB and throws exception
	 * 
	 * @param storesInfo
	 * @throws ProblemDetailsException
	 */
	private void checkForDuplicateStoresAndThrowException(List<StoreInfo> storesInfo) throws ProblemDetailsException {
		List<StoreInfo> existingStores = getAllStoresInfo();
		List<Integer> storeIds = new ArrayList<>();
		for (StoreInfo existingStoreInfo : existingStores) {
			Integer existingStoreId = existingStoreInfo.getStoreId();
			storeIds.add(existingStoreId);
		}
		for (StoreInfo newStoreInfo : storesInfo) {
			int newStoreId = newStoreInfo.getStoreId();
			if (storeIds.contains(newStoreId)) {
				throw new ProblemDetailsException(HttpStatus.BAD_REQUEST, HttpStatus.BAD_REQUEST.getReasonPhrase(),
						"Store Details with storeId : " + newStoreId
								+ " allready exists. Go for Update instead of adding it again.");
			}
		}
	}

	/**
	 * validates the stores details in the update request
	 * 
	 * @param updatedStoreInfo
	 * @throws ProblemDetailsException
	 */
	private void validateStoreDetailsBeforeUpdating(UpdateStoreInfo updatedStoreInfo) throws ProblemDetailsException {
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

}
