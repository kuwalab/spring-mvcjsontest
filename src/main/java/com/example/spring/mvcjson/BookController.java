package com.example.spring.mvcjson;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class BookController {
	private static List<Book> bookList;

	static {
		bookList = new ArrayList<>();
		bookList.add(new Book("1", "よくわかるSpring", 3000));
		bookList.add(new Book("2", "よくわかるJava", 3200));
		bookList.add(new Book("3", "よくわかるJUnit", 2800));
	}

	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String index() {
		return "book/index";
	}

	@RequestMapping(value = "/books", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE
			+ ";charset=utf-8")
	@ResponseBody
	public List<Book> books() {
		return bookList;
	}

	@RequestMapping(value = "/books", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE
			+ ";charset=utf-8")
	@ResponseBody
	public Book insert(@RequestBody Book book) {
		bookList.add(book);
		return book;
	}

	@RequestMapping(value = "/books/{bookId}", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE
			+ ";charset=utf-8")
	@ResponseBody
	public Book update(@PathVariable("bookId") String initBookId,
			@RequestBody Book book) {
		for (int i = 0; i < bookList.size(); i++) {
			Book currentBook = bookList.get(i);
			if (currentBook.getBookId().equals(initBookId)) {
				currentBook.setBookId(book.getBookId());
				currentBook.setBookName(book.getBookName());
				currentBook.setPrice(book.getPrice());
				return currentBook;
			}
		}

		return new Book();
	}

	@RequestMapping(value = "/books/{bookId}", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_VALUE
			+ ";charset=utf-8")
	@ResponseBody
	public Book delete(@PathVariable String bookId) {
		for (int i = 0; i < bookList.size(); i++) {
			Book book = bookList.get(i);
			if (book.getBookId().equals(bookId)) {
				bookList.remove(i);
				return book;
			}
		}

		return new Book();
	}
}
