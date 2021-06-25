package in.reno;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import in.reno.controller.BooksController;
import in.reno.model.BookDetails;
import in.reno.service.BooksService;

@RunWith(SpringRunner.class)
@AutoConfigureMockMvc
@SpringBootTest
class Spring2ApplicationTests {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private BooksService obj;

	@InjectMocks
	private BooksController bookController;

	@Test
	void contextLoads() throws Exception {

		BookDetails book = new BookDetails();
		book.setName("JAVA");
		book.setQuantity(10);
		List<BookDetails> searchResults = new ArrayList<>();
		searchResults.add(book);

		when(obj.getBookByName(any(String.class))).thenReturn(searchResults);

		mockMvc.perform(get("/getBookByName").param("bookName", "java").accept(MediaType.ALL))
				.andExpect(jsonPath("$[0].name").value("JAVA")).andExpect(jsonPath("$[0].quantity").value(10));
	}

}
