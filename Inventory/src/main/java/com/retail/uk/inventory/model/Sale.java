/**
 * 
 */
package com.retail.uk.inventory.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Entity used for the items sold.
 *
 */
@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Sale {
	
    @Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
    
    private long itemId;

	private String itemName;

	private Integer quantity;
	
}
