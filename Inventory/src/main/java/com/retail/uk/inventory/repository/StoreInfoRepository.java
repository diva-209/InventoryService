/**
 * 
 */
package com.retail.uk.inventory.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.retail.uk.inventory.model.StoreInfo;

/**
 * Repository for storeInfo
 *
 */
public interface StoreInfoRepository extends JpaRepository<StoreInfo, Integer>{

}
