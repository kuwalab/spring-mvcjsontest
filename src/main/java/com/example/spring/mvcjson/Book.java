package com.example.spring.mvcjson;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;

public class Book {
	@NotBlank
	@Length(max = 5)
	private String bookId;
	@NotBlank
	@Length(max = 10)
	private String bookName;
	// Integer、intにはNotNullはできない
	@NotNull
	@Min(1)
	private int price;

	public Book() {
	}

	public Book(String bookId, String bookName, int price) {
		super();
		this.bookId = bookId;
		this.bookName = bookName;
		this.price = price;
	}

	public String getBookId() {
		return bookId;
	}

	public void setBookId(String bookId) {
		this.bookId = bookId;
	}

	public String getBookName() {
		return bookName;
	}

	public void setBookName(String bookName) {
		this.bookName = bookName;
	}

	public int getPrice() {
		return price;
	}

	public void setPrice(int price) {
		this.price = price;
	}

	@Override
	public String toString() {
		return "Book [bookId=" + bookId + ", bookName=" + bookName + ", price="
				+ price + "]";
	}

}
