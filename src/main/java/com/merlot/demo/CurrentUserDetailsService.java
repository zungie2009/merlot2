/**
 * 
 */
package com.merlot.demo;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.merlot.demo.model.Member;

/**
 * @author Robert Tu - (Sep 3, 2016 6:49:52 PM)
 *
 */
@Service
public class CurrentUserDetailsService implements UserDetailsService {

	private final UserService userService;

    @Autowired
    public CurrentUserDetailsService(UserService userService) {
        this.userService = userService;
    }
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		//Member member = userService.getMemberByUsername(username)
        //        .orElseThrow(() -> new UsernameNotFoundException(String.format("User with user name =%s was not found", username)));
		Optional<Member> member = userService.getMemberByUsername(username);
		if(member.isPresent()) {
			Member signonMember = member.get();
			//System.out.println("***********************************Found Login User: " + username + " with Password " + signonMember.getPassword());
			return new CurrentUser(signonMember);
		} else {
			throw new UsernameNotFoundException(String.format("User with user name =%s was not found", username));
		}
        
	}

}
