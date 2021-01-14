/**
 * 
 */
package com.retail.uk.inventory.integration.tests;

import static org.junit.Assert.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import com.retail.uk.inventory.InventoryApplication;
import com.retail.uk.inventory.model.StoreInfo;
import com.retail.uk.inventory.utility.Utility;

/**
 * End to end integraion test by interacting with real DB.
 *
 */
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = InventoryApplication.class)
public class ControllerTest {

	@LocalServerPort
	private int port;

	TestRestTemplate restTemplate = new TestRestTemplate();

	private Utility utility = new Utility();

	HttpHeaders headers = new HttpHeaders();

	@Test
	public void testAddStoresInfo() throws Exception {
		List<StoreInfo> stores = utility.getMockDataForMultipleStores();
		headers.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<List<StoreInfo>> entity = new HttpEntity<List<StoreInfo>>(stores, headers);
		ResponseEntity<String> response = restTemplate.exchange(generateURL("/api/v1/addStoreDetails"), HttpMethod.POST,
				entity, String.class);
		assertEquals(200, response.getStatusCodeValue());
		assertEquals("Details of the stores added successfully", response.getBody());
	}

	@Test
	public void testRetrieveStoresInfo() throws Exception {

		ResponseEntity<StoreInfo[]> response = restTemplate.exchange(generateURL("/api/v1/stores"),
				HttpMethod.GET, null, StoreInfo[].class);
		StoreInfo[] stores = response.getBody();

		assertEquals(200, response.getStatusCodeValue());
		assertNotNull(response.getBody());
		assertEquals(2, stores.length);
	}

	@Test
	public void testRetrieveStoresInfoByStoreId() throws Exception {

		ResponseEntity<StoreInfo> response = restTemplate.exchange(generateURL("/api/v1/storeInfo/99308"),
				HttpMethod.GET, null, StoreInfo.class);

		assertEquals(200, response.getStatusCodeValue());
		assertNotNull(response.getBody());
		assertEquals(99308, response.getBody().getStoreId());
		assertEquals("Jeans", response.getBody().getDelivery().get(0).getItemName());
	}

	@Test
	public void testDeleteStoresInfoByStoreId() throws Exception {

		ResponseEntity<String> response = restTemplate.exchange(generateURL("/api/v1/storeInfo/99319"),
				HttpMethod.DELETE, null, String.class);

		assertEquals(200, response.getStatusCodeValue());
		assertNotNull(response.getBody());
		assertEquals("Successfully deleted the store", response.getBody());
	}

	@Test
	public void testUpdateStoresInfo() throws Exception {
		List<StoreInfo> stores = utility.getMockDataForMultipleStores();
		
		stores.get(0).getDelivery().get(0).setItemName("Jeans");
		
		headers.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<List<StoreInfo>> entity = new HttpEntity<List<StoreInfo>>(stores, headers);
		ResponseEntity<String> response = restTemplate.exchange(generateURL("/api/v1/updateStoresInfo"),
				HttpMethod.PUT, entity, String.class);
		assertEquals(200, response.getStatusCodeValue());
		assertEquals("Successfully updated the store details", response.getBody());

	}

	private String generateURL(String uri) {
		return "http://localhost:" + port + uri;
	}

}
