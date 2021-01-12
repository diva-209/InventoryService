/**
 * 
 */
package com.retail.uk.inventory.controller;

import java.util.List;
import java.util.Optional;

import javax.websocket.server.PathParam;

import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.retail.uk.inventory.exception.ProblemDetailsException;
import com.retail.uk.inventory.model.StoreInfo;
import com.retail.uk.inventory.model.UpdateStoreInfo;
import com.retail.uk.inventory.service.InventoryService;

/**
 * Controller which performs the CRUD operations based on the request.
 *
 */
@RestController
@RequestMapping("/api/v1/")
public class InventoryController {
	
	@Autowired
	private InventoryService inventoryService;

	@PostMapping("addStoreDetails")
	public ResponseEntity<String> addStoresInfo(@RequestBody List<StoreInfo> storesInfo) throws ProblemDetailsException {
		
		inventoryService.addStoresInfo(storesInfo);
		
		return ResponseEntity.ok("Details of the stores added successfully");		
	}
	
	@GetMapping("storeInfo/{storeId}")
	public StoreInfo getStoreDataByStoreId(@PathVariable int storeId) throws ProblemDetailsException {
		StoreInfo storeInfo = inventoryService.getStoreInfoByStoreId(storeId);
		
		return storeInfo;
	}
	
	@GetMapping("/stores")
	public List<StoreInfo> getAllStores() throws ProblemDetailsException {
		return inventoryService.getAllStoresInfo();
		
	}
	
	@DeleteMapping("storeInfo/{storeId}")
	public ResponseEntity<String> deleteStoreDataByStoreId(@PathVariable int storeId) throws ProblemDetailsException {
		inventoryService.deleteStoreInfoByStoreId(storeId);
		
		return ResponseEntity.ok("Successfully delete the store");
	}
	
	@PutMapping("updateStoresInfo")
	public ResponseEntity<String> updateStoresInfo(@RequestBody List<UpdateStoreInfo> updateStoresInfoRequest) throws ProblemDetailsException {
		inventoryService.updateStoresInfo(updateStoresInfoRequest);
		
		return ResponseEntity.ok("Successfully updated the store details");
	}

}
