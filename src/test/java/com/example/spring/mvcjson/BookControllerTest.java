package com.example.spring.mvcjson;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.*;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.context.WebApplicationContext;

import com.fasterxml.jackson.databind.ObjectMapper;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(locations = { "file:src/main/webapp/WEB-INF/spring/beans-webmvc.xml" })
public class BookControllerTest {
	@Autowired
	private WebApplicationContext wac;

	private MockMvc mockMvc;

	@Before
	public void setup() {
		mockMvc = webAppContextSetup(wac).build();
	}

	/* JSONPath http://goessner.net/articles/JsonPath/ */
	@Test
	public void slash_booksのGET() throws Exception {
		mockMvc.perform(get("/books", "json"))
				.andExpect(jsonPath("$").isArray())
				.andExpect(jsonPath("$", hasSize(3)))
				.andExpect(jsonPath("$[0].bookId").value("1"))
				.andExpect(jsonPath("$[0].bookName").value("よくわかるSpring"))
				.andExpect(jsonPath("$[0].price").value(3000));
	}

	@Test
	public void slash_booksのPOST() throws Exception {
		Book book = new Book("123", "よくわかるJSON", 2999);
		ObjectMapper mapper = new ObjectMapper();
		String jsonStr = mapper.writerWithDefaultPrettyPrinter()
				.writeValueAsString(book);
		System.out.println(jsonStr);
		mockMvc.perform(
				post("/books", "json").contentType(MediaType.APPLICATION_JSON)
						.content(jsonStr.getBytes()))
				.andExpect(jsonPath("$.bookId").value(book.getBookId()))
				.andExpect(jsonPath("$.bookName").value(book.getBookName()))
				.andExpect(jsonPath("$.price").value(book.getPrice()));

		// 追加されているか確認
		mockMvc.perform(get("/books", "json"))
				.andExpect(jsonPath("$").isArray())
				.andExpect(jsonPath("$", hasSize(4)))
				.andExpect(jsonPath("$[3].bookId").value(book.getBookId()))
				.andExpect(jsonPath("$[3].bookName").value(book.getBookName()))
				.andExpect(jsonPath("$[3].price").value(book.getPrice()));

		// 追加したものを削除しておく
		mockMvc.perform(delete("/books/" + book.getBookId(), "json"));

		// 削除されているか確認
		mockMvc.perform(get("/books", "json"))
				.andExpect(jsonPath("$").isArray())
				.andExpect(jsonPath("$", hasSize(3)))
				.andExpect(jsonPath("$[0].bookId").value("1"))
				.andExpect(jsonPath("$[0].bookName").value("よくわかるSpring"))
				.andExpect(jsonPath("$[0].price").value(3000));

	}
}
