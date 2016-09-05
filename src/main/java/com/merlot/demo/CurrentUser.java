/**
 * 
 */
package com.merlot.demo;

import org.springframework.security.core.authority.AuthorityUtils;

import com.merlot.demo.model.Member;
import com.merlot.demo.model.Role;

/**
 * @author Robert Tu - (Sep 3, 2016 6:56:17 PM)
 *
 */
public class CurrentUser extends org.springframework.security.core.userdetails.User {
	private static final long serialVersionUID = 6837195266359858742L;
	
	private Member member;

    public CurrentUser(Member member) {
        //super(member.getUsername(), member.getPassword(), AuthorityUtils.createAuthorityList(member.getRole().toString()));
        super(member.getUsername(), member.getPassword(), AuthorityUtils.createAuthorityList(Role.USER.toString()));
        this.member = member;
    }

    public Member getMember() {
        return member;
    }

    public Long getId() {
        return member.getId();
    }

    public Role getRole() {
        return member.getRole();
    }
}
