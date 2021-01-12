/**
 * 
 */
package com.retail.uk.inventory.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.retail.uk.inventory.model.Delivery;

/**
 * Repository for Delivery Entity
 *
 */
public interface DeliveryRepository extends JpaRepository<Delivery, Long>{

}
