package org.javacream.books.warehouse;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;

import org.javacream.books.isbngenerator.impl.RandomIsbnGenerator;
import org.javacream.books.warehouse.api.Book;
import org.javacream.books.warehouse.api.BookException;
import org.javacream.books.warehouse.api.BooksService;
import org.javacream.books.warehouse.api.SchoolBook;
import org.javacream.books.warehouse.api.SpecialistBook;
import org.javacream.books.warehouse.impl.MapBooksService;
import org.javacream.store.impl.MapStoreService;
import org.junit.Before;
import org.junit.Test;

public class BooksServiceScenarioTest {

	private BooksService booksService;
	private final String ISBN = "TEST-ISBN";
	private final String TITLE = "TEST-TITLE";
	private final double PRICE = 9.99;

	@Before
	public void init() {
		MapBooksService mapBooksService = new MapBooksService();
		RandomIsbnGenerator randomIsbnGenerator = new RandomIsbnGenerator("TEST:", "-de");
		MapStoreService mapStoreService = new MapStoreService();
		HashMap<String, Book> books = new HashMap<String, Book>();
		Map<Set<String>, Function<Map<String, Object>, Book>> generators = new HashMap<>();
		Map<String, Map<String, Integer>> store = new HashMap<>();
		HashMap<String, Integer> booksStore = new HashMap<>();

		for (int i = 1; i <= 10; i++) {
			Book book = new Book();
			book.setIsbn("ISBN" + i);
			book.setTitle("Title" + i);
			book.setPrice(9.99 * i);
			books.put(book.getIsbn(), book);
			booksStore.put(book.getIsbn(), i);
		}
		
		Book testBook = new Book();
		testBook.setIsbn(ISBN);
		testBook.setTitle(TITLE);
		testBook.setPrice(PRICE);
		books.put(ISBN, testBook);

		HashSet<String> booksKeySet = new HashSet<>();
		generators.put(booksKeySet, (Map<String, Object> options) -> {
			Book book = new Book();
			return book;
		});

		HashSet<String> schoolBooksKeySet = new HashSet<>(booksKeySet);
		schoolBooksKeySet.add("subject");
		schoolBooksKeySet.add("year");
		generators.put(schoolBooksKeySet, (Map<String, Object> options) -> {
			SchoolBook book = new SchoolBook();
			book.setSubject((String) options.get("subject"));
			book.setYear((Integer) options.get("year"));
			return book;
		});
		HashSet<String> specialistBooksKeySet = new HashSet<>(booksKeySet);
		specialistBooksKeySet.add("topic");
		generators.put(specialistBooksKeySet, (Map<String, Object> options) -> {
			SpecialistBook book = new SpecialistBook();
			book.setTopic((String) options.get("topic"));
			return book;
		});

		store.put("books", booksStore);
		mapBooksService.setBooks(books);
		mapBooksService.setGenerators(generators);
		mapBooksService.setIsbnGenerator(randomIsbnGenerator);
		mapBooksService.setStoreService(mapStoreService);
		
		mapStoreService.setStore(store);
		booksService = mapBooksService;
	}

	@Test
	public void testBooksSequence() {
		doTest(booksService);
	}

	private void doTest(BooksService booksService) {

		try {
			Collection<Book> books = booksService.findAllBooks();
			final int startSize = books.size();
			String j2eeTitle = "Spring";
			String isbn = booksService.newBook(j2eeTitle, PRICE, new HashMap<String, Object>());
			books = booksService.findAllBooks();
			final int endSize = books.size();
			assertTrue(endSize == startSize + 1);
			assertNotNull(isbn);
			assertNotNull(booksService.findBookByIsbn(isbn));

			try {
				booksService.findBookByIsbn("ISBN-3");
				fail("BookException must be thrown!");
			} catch (BookException e) {
				// OK
			}
			booksService.deleteBookByIsbn(isbn);
			assertTrue(startSize == booksService.findAllBooks().size());
			try {
				booksService.deleteBookByIsbn(isbn);
				fail("BookException must be thrown!");
			} catch (BookException e) {
				// OK
			}

		} catch (BookException bookException) {
			bookException.printStackTrace();
			fail(bookException.getMessage());
		}

	}

}
