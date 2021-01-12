/**
 * 
 */
package com.retail.uk.inventory.model;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Entity used for storing store info.
 *
 */
@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class StoreInfo {
	
	/*
	 * @Id
	 * 
	 * @GeneratedValue(strategy = GenerationType.IDENTITY) private int id;
	 */
	
	@Id
	private Integer storeId;
	
	@OneToMany(targetEntity = Refund.class,cascade = CascadeType.ALL)
	@JoinColumn(name = "storeId_foreignKey",referencedColumnName = "storeId")
	private List<Refund> refund;
	
	@OneToMany(targetEntity = Delivery.class,cascade = CascadeType.ALL)
	@JoinColumn(name = "storeId_foreignKey",referencedColumnName = "storeId")
	private List<Delivery> delivery;
	
	@OneToMany(targetEntity = Sale.class,cascade = CascadeType.ALL)
	@JoinColumn(name = "storeId_foreignKey",referencedColumnName = "storeId")
	private List<Sale> sale;

}
