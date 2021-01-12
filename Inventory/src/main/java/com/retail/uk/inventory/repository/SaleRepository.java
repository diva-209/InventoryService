/**
 * 
 */
package com.retail.uk.inventory.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.retail.uk.inventory.model.Sale;

/**
 * Repository for Sale Entity
 *
 */
public interface SaleRepository extends JpaRepository<Sale, Long>{

}
