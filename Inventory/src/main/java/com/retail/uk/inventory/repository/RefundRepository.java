/**
 * 
 */
package com.retail.uk.inventory.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.retail.uk.inventory.model.Refund;

/**
 * Repository for Refund entity
 *
 */
public interface RefundRepository extends JpaRepository<Refund, Long>{

}
