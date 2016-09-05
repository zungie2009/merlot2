package com.merlot.demo.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.merlot.demo.util.CsvFormatUtil;

/**
 * @author Robert Tu (Sep 1, 2016)
 *
 */
@Entity
@Table(name="member_type")
public class MemberType {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private long id;
	private String type;
	
	public MemberType() {}
	
	public MemberType(String type) {
		this.type = type;
	}
	
	public MemberType(long id, String type) {
		this.id = id;
		this.type = type;
	}
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	
	public static MemberType parseMemberType(String s) throws Exception {
		String[] tokens = s.split("\",\"");
		if (s.length() > 0) {
			return new MemberType(Long.parseLong(CsvFormatUtil.parseField(tokens[0])), CsvFormatUtil.parseField(tokens[1]));
		} else {
			throw new Exception("Invalid Data format");
		}
	}
	
	@Override
	public String toString() {
		return CsvFormatUtil.formatField("" + id) + "," + CsvFormatUtil.formatField(type);
	}
}
