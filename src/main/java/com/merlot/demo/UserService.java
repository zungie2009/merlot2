/**
 * 
 */
package com.merlot.demo;

import java.util.Collection;
import java.util.Optional;

import com.merlot.demo.model.Member;
import com.merlot.demo.model.UserCreateForm;

/**
 * @author Robert Tu - (Sep 3, 2016 6:22:16 PM)
 *
 */
public interface UserService {

    Optional<Member> getMemberById(long id);

    Optional<Member> getMemberByEmail(String email);
    
    Optional<Member> getMemberByUsername(String userName);

    Collection<Member> getAllUsers();

    Member create(UserCreateForm form);
    
    Member create(Member member);

}
