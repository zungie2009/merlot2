/**
 * 
 */
package com.merlot.demo;

import java.util.Collection;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.merlot.demo.model.Member;
import com.merlot.demo.model.UserCreateForm;
import com.merlot.demo.repository.MemberRepository;

/**
 * @author Robert Tu - (Sep 3, 2016 6:23:57 PM)
 *
 */

@Service
public class MemberServiceImpl implements UserService {
	protected final MemberRepository memberRepository;
	
	@Autowired
	public MemberServiceImpl(MemberRepository memberRepository) {
		this.memberRepository = memberRepository;
	}
	
	@Override
	public Optional<Member> getMemberById(long id) {
		return Optional.ofNullable(memberRepository.findOne(id));
	}

	@Override
	public Optional<Member> getMemberByEmail(String email) {
		return memberRepository.findOneByEmail(email);
	}
	
	@Override
	public Optional<Member> getMemberByUsername(String userName) {
		return memberRepository.findOneByUsername(userName);
	}

	@Override
	public Collection<Member> getAllUsers() {
		return (Collection<Member>) memberRepository.findAll();
	}

	@Override
	public Member create(UserCreateForm form) {
		Member member = new Member();
		member.setEmail(form.getEmail());
        //user.setPasswordHash(new BCryptPasswordEncoder().encode(form.getPassword()));
		member.setPassword(new BCryptPasswordEncoder().encode(form.getPassword()));
		member.setRole(form.getRole());
        return memberRepository.save(member);
	}

	@Override
	public Member create(Member member) {
		member.setPassword(new BCryptPasswordEncoder().encode(member.getPassword()));
		return memberRepository.save(member);
	}

}
