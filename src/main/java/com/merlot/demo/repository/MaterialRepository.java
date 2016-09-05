/**
 * 
 */
package com.merlot.demo.repository;

import org.springframework.data.repository.CrudRepository;

import com.merlot.demo.model.Material;

/**
 * @author Robert Tu - (Sep 3, 2016 3:39:48 PM)
 *
 */
public interface MaterialRepository extends CrudRepository<Material, Long> {

}
