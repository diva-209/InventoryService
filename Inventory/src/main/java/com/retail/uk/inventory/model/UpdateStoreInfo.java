/**
 * 
 */
package com.retail.uk.inventory.model;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * DTO used for updating the storeInfo request payload.
 *
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UpdateStoreInfo {

	private Integer storeId;
	private List<Refund> refund;
	private List<Sale> sale;
	private List<Delivery> delivery;

}
