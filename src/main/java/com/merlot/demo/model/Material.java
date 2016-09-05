/**
 * 
 */
package com.merlot.demo.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.merlot.demo.util.CsvFormatUtil;

/**
 * @author Robert Tu - Sep 1, 2016 1:55:01 PM
 *
 */
@Entity
@Table(name="material")
public class Material {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private long id;
	private String title;
	private String description;
	private String category;
	private String author;
	private String url;
	private String isbn;
	
	public Material() {}
	
	public Material(String title, String description, String category, String author, String url, String isbn) {
		this.title = title;
		this.description = description;
		this.category = category;
		this.author = author;
		this.url = url;
		this.isbn = isbn;
	}
	
	public Material(long id, String title, String description, String category, String author, String url, String isbn) {
		this.id = id;
		this.title = title;
		this.description = description;
		this.category = category;
		this.author = author;
		this.url = url;
		this.isbn = isbn;
	}
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getCategory() {
		return category;
	}
	public void setCategory(String category) {
		this.category = category;
	}
	public String getAuthor() {
		return author;
	}
	public void setAuthor(String author) {
		this.author = author;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getIsbn() {
		return isbn;
	}
	public void setIsbn(String isbn) {
		this.isbn = isbn;
	}
	
	public static Material parseMaterial(String s) throws Exception {
		String[] tokens = s.split("\",\"");
		if (tokens.length == 7) {
			return new Material(Integer.parseInt(CsvFormatUtil.parseField(tokens[0])),
					CsvFormatUtil.parseField(tokens[1]), CsvFormatUtil.parseField(tokens[2]),
					CsvFormatUtil.parseField(tokens[3]), CsvFormatUtil.parseField(tokens[4]),
					CsvFormatUtil.parseField(tokens[5]), CsvFormatUtil.parseField(tokens[6]));
		} else {
			throw new Exception("Invalid Data format");
		}
	}
	
	public String toString() {
		return CsvFormatUtil.formatField("" + id) + "," + CsvFormatUtil.formatField(title) + ","
				+ CsvFormatUtil.formatField(description) + "," + CsvFormatUtil.formatField(category) + ","
				+ CsvFormatUtil.formatField(author) + "," + CsvFormatUtil.formatField(url) + ","
				+ CsvFormatUtil.formatField(isbn);
	}

}
