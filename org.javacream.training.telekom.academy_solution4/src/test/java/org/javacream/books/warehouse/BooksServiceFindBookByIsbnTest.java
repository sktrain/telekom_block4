package org.javacream.books.warehouse;

import java.util.Map;

import org.javacream.books.warehouse.api.Book;
import org.javacream.books.warehouse.api.BookException;
import org.javacream.books.warehouse.api.BooksService;
import org.javacream.books.warehouse.impl.MapBooksService;
import org.javacream.store.api.StoreService;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

public class BooksServiceFindBookByIsbnTest {

	private BooksService booksService;
	private final String ISBN = "TEST-ISBN";
	private final String TITLE = "TEST-TITLE";
	private final double PRICE = 9.99;

	private final String WRONG_ISBN = "##ISBN##";

	@Before
	public void init() {
		MapBooksService mapBooksService = new MapBooksService();
		StoreService storeService = Mockito.mock(StoreService.class);
		Mockito.when(storeService.getStock("books", ISBN)).thenReturn(42);
		Book book = new Book();
		book.setIsbn(ISBN);
		book.setTitle(TITLE);
		book.setPrice(PRICE);
		@SuppressWarnings("unchecked")
		Map<String, Book> booksMap = Mockito.mock(Map.class);
		Mockito.when(booksMap.get(ISBN)).thenReturn(book);
		Mockito.when(booksMap.get(WRONG_ISBN)).thenReturn(null);
		
		mapBooksService.setStoreService(storeService);
		mapBooksService.setBooks(booksMap);
		booksService = mapBooksService;
	}

	@Test
	public void findBookByIsbnOk() throws BookException {
		Book book = booksService.findBookByIsbn(ISBN);
		Assert.assertEquals(ISBN, book.getIsbn());
		Assert.assertEquals(TITLE, book.getTitle());
		Assert.assertEquals(PRICE, book.getPrice(), 1e-9);
		Assert.assertEquals(true, book.isAvailable());
		
	}

	@Test(expected = BookException.class)
	public void findBookByIsbnWrong() throws BookException {
		booksService.findBookByIsbn(WRONG_ISBN);
	}

}
