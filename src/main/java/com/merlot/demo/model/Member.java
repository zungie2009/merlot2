/**
 * 
 */
package com.merlot.demo.model;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @author Robert Tu Sep 1, 2016
 *
 */
@Entity
@Table(name="member")
public class Member {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private long id;
	private String first_name;
	private String middle_name;
	private String last_name;
	private String email;
	private String username;
	private String password;
	private String job_title;
	private String member_type;
	private char displayEmailFlag;
	private char reviewer_flag;
	private Role role;
	private Date login_date;
	private Date create_date;
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getFirst_name() {
		return first_name;
	}
	public void setFirst_name(String first_name) {
		this.first_name = first_name;
	}
	public String getMiddle_name() {
		return middle_name;
	}
	public void setMiddle_name(String middle_name) {
		this.middle_name = middle_name;
	}
	public String getLast_name() {
		return last_name;
	}
	public void setLast_name(String last_name) {
		this.last_name = last_name;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getJob_title() {
		return job_title;
	}
	public void setJob_title(String job_title) {
		this.job_title = job_title;
	}
	public String getMember_type() {
		return member_type;
	}
	public void setMember_type(String member_type) {
		this.member_type = member_type;
	}
	public char getReviewer_flag() {
		return reviewer_flag;
	}
	public void setReviewer_flag(char reviewer_flag) {
		this.reviewer_flag = reviewer_flag;
	}
	public Date getLogin_date() {
		return login_date;
	}
	public void setLogin_date(Date login_date) {
		this.login_date = login_date;
	}
	public Date getCreate_date() {
		return create_date;
	}
	public void setCreate_date(Date create_date) {
		this.create_date = create_date;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public char getDisplayEmailFlag() {
		return displayEmailFlag;
	}
	public void setDisplayEmailFlag(char displayEmailFlag) {
		this.displayEmailFlag = displayEmailFlag;
	}
	public Role getRole() {
		return role;
	}
	public void setRole(Role role) {
		this.role = role;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	
	
}