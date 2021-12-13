package org.javacream.books.warehouse;

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
import org.javacream.util.Ordering;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class BooksServiceOperationsTest {

	private BooksService booksService;
	private final String ISBN = "TEST-ISBN";
	private final String TITLE = "TEST-TITLE";
	private final double PRICE = 9.99;
	private final double NEW_PRICE = 19.99;

	private final String WRONG_ISBN = "##ISBN##";

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
	public void deleteBookByIsbnOk() throws BookException {
		Book book = booksService.findBookByIsbn(ISBN);
		booksService.deleteBookByIsbn(ISBN);
		Assert.assertFalse(booksService.findAllBooks().contains(book));
	}

	@Test(expected = BookException.class)
	public void deleteBookByIsbnWrong() throws BookException {
		booksService.deleteBookByIsbn(WRONG_ISBN);
	}

	@Test
	public void findBookByIsbnOk() throws BookException {
		Book book = booksService.findBookByIsbn(ISBN);
		Assert.assertNotNull(book);
	}

	@Test(expected = BookException.class)
	public void findBookByIsbnWrong() throws BookException {
		booksService.findBookByIsbn(WRONG_ISBN);
	}

	@Test
	public void findBookAllBooks() {
		Assert.assertNotNull(booksService.findAllBooks());
	}

	@Test
	public void updateBookOk() throws BookException {
		final String NEW_TITLE = "CHANGED";
		Book book = booksService.findBookByIsbn(ISBN);
		book.setTitle(NEW_TITLE);
		book.setPrice(NEW_PRICE);
		Book book2 = booksService.updateBook(book);
		Assert.assertTrue(NEW_TITLE.equals(book2.getTitle()));
	}

	@Test(expected = Exception.class)
	public void updateBookWrong() throws BookException {
		Book book = new Book();
		book.setIsbn(WRONG_ISBN);
		book.setPrice(NEW_PRICE);
		booksService.updateBook(book);
	}

	@Test(expected = Exception.class)
	public void updateBookPriceNotGreaterZeroFails() throws BookException {
		Book book = booksService.findBookByIsbn(ISBN);
		book.setPrice(-NEW_PRICE);
		booksService.updateBook(book);
	}

	@Test
	public void createBook() throws BookException {
		String isbn = booksService.newBook("TEST", PRICE, new HashMap<String, Object>());
		Book book = booksService.findBookByIsbn(isbn);
		Assert.assertTrue(book.getClass() == Book.class);

	}

	@Test
	public void createSchoolBook() throws BookException {
		HashMap<String, Object> options = new HashMap<String, Object>();
		options.put("subject", "Physics");
		options.put("year", 10);
		String isbn = booksService.newBook("TEST", PRICE, options);
		Book book = booksService.findBookByIsbn(isbn);
		Assert.assertTrue(book.getClass() == SchoolBook.class);

	}

	@Test
	public void createSpecialistBook() throws BookException {
		HashMap<String, Object> options = new HashMap<String, Object>();
		options.put("topic", "Very Special");
		String isbn = booksService.newBook("TEST", PRICE, options);
		Book book = booksService.findBookByIsbn(isbn);
		Assert.assertTrue(book.getClass() == SpecialistBook.class);

	}

	@Test(expected = Exception.class)
	public void createBookFailsNullOptions() throws BookException {
		booksService.newBook("TEST", PRICE, null);

	}

	@Test
	public void listBooks() {
		Assert.assertNotNull(booksService.booksList());
	}

	@Test
	public void listBooksAscending() {
		Assert.assertNotNull(booksService.booksList(Ordering.ASCENDING));
	}

	@Test
	public void listBooksDescending() {
		Assert.assertNotNull(booksService.booksList(Ordering.DESCENDING));
	}

	@Test
	public void listBooksPriceRange() {
		Assert.assertNotNull(booksService.findBooksByPriceRange(12.5, 33.40));
	}

}
