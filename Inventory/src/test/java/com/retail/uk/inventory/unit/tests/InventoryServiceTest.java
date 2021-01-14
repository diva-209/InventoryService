package com.retail.uk.inventory.unit.tests;

import static org.junit.Assert.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import com.retail.uk.inventory.exception.ProblemDetailsException;
import com.retail.uk.inventory.model.Refund;
import com.retail.uk.inventory.model.StoreInfo;
import com.retail.uk.inventory.model.UpdateStoreInfo;
import com.retail.uk.inventory.repository.StoreInfoRepository;
import com.retail.uk.inventory.service.InventoryService;
import com.retail.uk.inventory.utility.Utility;

@RunWith(SpringRunner.class)
@SpringBootTest
public class InventoryServiceTest {

	@MockBean
	private StoreInfoRepository storeInfoRepo;

	@Autowired
	private InventoryService inventoryService;
	
	private Utility utility = new Utility();
	
	@Test
	public void testAddMultipleStoresDetails() throws ProblemDetailsException {
		List<StoreInfo> stores = utility.getMockDataForMultipleStores();
		when(storeInfoRepo.saveAll(stores)).thenReturn(stores);
		List<StoreInfo> response = inventoryService.addStoresInfo(stores);
		assertEquals(2,response.size());
		assertEquals(99319,response.get(0).getStoreId());
		assertEquals(99308,response.get(1).getStoreId());
	}
	
	@Test
	public void testAddSingleStoreDetails() throws ProblemDetailsException {
		List<StoreInfo> stores = utility.getMockDataForMultipleStores();
		stores.remove(0);
		when(storeInfoRepo.saveAll(stores)).thenReturn(stores);
		List<StoreInfo> response = inventoryService.addStoresInfo(stores);
		assertEquals(1,response.size());
		assertEquals(99308,response.get(0).getStoreId());
	}

	@Test
	public void testRetrieveAllStoresDetails() throws Exception {
		
		when(storeInfoRepo.findAll())
				.thenReturn(utility.getMockDataForMultipleStores());

		List<StoreInfo> storesInfo = inventoryService.getAllStoresInfo();

		assertEquals(2, storesInfo.size());

	}
	
	@Test
	public void testRetrieveByStoreId() throws Exception {
		
		List<StoreInfo> stores = utility.getMockDataForMultipleStores();
		Optional<StoreInfo> storeInfo = Optional.of(stores.get(0));
		
		when(storeInfoRepo.findById(99319))
				.thenReturn(storeInfo);

		StoreInfo response = inventoryService.getStoreInfoByStoreId(99319);

		assertNotNull(response);
		assertEquals(99319,response.getStoreId());
		assertEquals(725896412345L,response.getRefund().get(0).getItemId());
		assertEquals("T-shirt",response.getRefund().get(0).getItemName());
		assertEquals(6,response.getDelivery().get(0).getQuantity());

	}
	
	@Test
	public void testUpdateStores() throws Exception {
		List<StoreInfo> existingStores = utility.getMockDataForMultipleStores();
		when(storeInfoRepo.findAll()).thenReturn(existingStores);
		List<UpdateStoreInfo> updatedStoresRequest = utility.getMockDataForUpdatingMultipleStores();
		
		Refund updatedRefund = updatedStoresRequest.get(0).getRefund().get(0);
		updatedRefund.setItemName("Jeans");
		
		Optional<StoreInfo> storeInfo = Optional.of(existingStores.get(0));
		when(storeInfoRepo.findById(99319))
		.thenReturn(storeInfo);
		
		Optional<StoreInfo> secondStore = Optional.of(existingStores.get(1));
		when(storeInfoRepo.findById(99308))
		.thenReturn(secondStore);
		
		List<StoreInfo> response = inventoryService.updateStoresInfo(updatedStoresRequest);
		assertEquals("Jeans",response.get(0).getRefund().get(0).getItemName());
	}
	
	@Test
	public void testValidStoreId() {
		List<StoreInfo> stores = utility.getMockDataForMultipleStores();
		stores.get(0).setStoreId(123456);
		try {
			inventoryService.addStoresInfo(stores);
		} catch (ProblemDetailsException exception) {
			assertEquals(400,exception.getStatus().BAD_REQUEST.value());
			assertEquals("storeId must be of 5 digits",exception.getMessage());
		}
	}
	
	@Test
	public void testValidItemName() {
		List<StoreInfo> stores = utility.getMockDataForMultipleStores();
		stores.get(0).getDelivery().get(0).setItemName("");
		try {
			inventoryService.addStoresInfo(stores);
		} catch (ProblemDetailsException exception) {
			assertEquals(400,exception.getStatus().BAD_REQUEST.value());
			assertEquals("delivery/itemName is missing for storeId : 99319",exception.getMessage());
		}
	}
	
	@Test
	public void testValidItemNameLength() {
		List<StoreInfo> stores = utility.getMockDataForMultipleStores();
		stores.get(0).getDelivery().get(0).setItemName("setting the item name for the first delivery item of 1st store");
		try {
			inventoryService.addStoresInfo(stores);
		} catch (ProblemDetailsException exception) {
			assertEquals(400,exception.getStatus().BAD_REQUEST.value());
			assertEquals("delivery/itemName should not excede 40 characters for storeId : 99319",exception.getMessage());
		}
	}
	
	@Test
	public void testValidItemId() {
		List<StoreInfo> stores = utility.getMockDataForMultipleStores();
		stores.get(0).getDelivery().get(0).setItemId(12345678945612L);
		try {
			inventoryService.addStoresInfo(stores);
		} catch (ProblemDetailsException exception) {
			assertEquals(400,exception.getStatus().BAD_REQUEST.value());
			assertEquals("delivery/itemId must be of 12 digits for storeId : 99319",exception.getMessage());
		}
	}
	

}
