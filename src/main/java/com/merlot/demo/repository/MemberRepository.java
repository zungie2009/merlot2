/**
 * 
 */
package com.merlot.demo.repository;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import com.merlot.demo.model.Member;

/**
 * @author Robert Tu - (Sep 3, 2016 3:38:03 PM)
 *
 */
public interface MemberRepository extends CrudRepository<Member, Long> {

	public Optional<Member> findOneByEmail(String email);
	
	public Optional<Member> findOneByUsername(String userName);
}
