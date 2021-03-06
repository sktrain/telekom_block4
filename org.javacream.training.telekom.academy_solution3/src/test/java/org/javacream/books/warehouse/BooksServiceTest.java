package org.javacream.books.warehouse;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.Collection;
import java.util.HashMap;

import org.javacream.util.Ordering;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class BooksServiceTest {

	private MapBooksService booksService;
	private String ISBN;

	private static final String WRONG_ISBN = "##ISBN##";
	private static final double PRICE = 9.99;

	@Before
	public void init() {
		booksService = new MapBooksService();
		try {
			ISBN = booksService.newBook("TEST", PRICE, new HashMap<String, Object>());
		} catch (BookException e) {
			throw new RuntimeException(e.getMessage());
		}

	}

//	@Test
//	public void testBooksSequence() {
//		doTest(booksService);
//	}
//
//	@Test
//	public void deleteBookByIsbnOk() throws BookException {
//		Book book = booksService.findBookByIsbn(ISBN);
//		booksService.deleteBookByIsbn(ISBN);
//		Assert.assertFalse(booksService.findAllBooks().contains(book));
//	}
//
//	@Test(expected = BookException.class)
//	public void deleteBookByIsbnWrong() throws BookException {
//		booksService.deleteBookByIsbn(WRONG_ISBN);
//	}
//
//	@Test
//	public void findBookByIsbnOk() throws BookException {
//		Book book = booksService.findBookByIsbn(ISBN);
//		Assert.assertNotNull(book);
//	}
//
//	@Test(expected = BookException.class)
//	public void findBookByIsbnWrong() throws BookException {
//		booksService.findBookByIsbn(WRONG_ISBN);
//	}

	@Test
	public void findBookAllBooks() {
		Assert.assertNotNull(booksService.findAllBooks());
		booksService.findAllBooks().forEach(System.out::println);
	}

//	@Test
//	public void updateBookOk() throws BookException {
//		final String NEW_TITLE = "CHANGED";
//		Book book = booksService.findBookByIsbn(ISBN);
//		book.setTitle(NEW_TITLE);
//		book.setPrice(19.99);
//		Book book2 = booksService.updateBook(book);
//		Assert.assertTrue(NEW_TITLE.equals(book2.getTitle()));
//	}
//
//	@Test(expected = Exception.class)
//	public void updateBookWrong() throws BookException {
//		Book book = new Book();
//		book.setIsbn(WRONG_ISBN);
//		book.setPrice(19.99);
//		booksService.updateBook(book);
//	}
//
//	@Test(expected = Exception.class)
//	public void updateBookPriceNotGreaterZeroFails() throws BookException {
//		Book book = booksService.findBookByIsbn(ISBN);
//		book.setPrice(-19.99);
//		booksService.updateBook(book);
//	}
//
//	@Test
//	public void createBook() throws BookException {
//		String isbn = booksService.newBook("TEST", PRICE, new HashMap<String, Object>());
//		Book book = booksService.findBookByIsbn(isbn);
//		Assert.assertTrue(book.getClass() == Book.class);
//
//	}
//
//	@Test
//	public void createSchoolBook() throws BookException {
//		HashMap<String, Object> options = new HashMap<String, Object>();
//		options.put("subject", "Physics");
//		options.put("year", 10);
//		String isbn = booksService.newBook("TEST", PRICE, options);
//		Book book = booksService.findBookByIsbn(isbn);
//		Assert.assertTrue(book.getClass() == SchoolBook.class);
//
//	}
//
//	@Test
//	public void createSpecialistBook() throws BookException {
//		HashMap<String, Object> options = new HashMap<String, Object>();
//		options.put("topic", "Very Special");
//		String isbn = booksService.newBook("TEST", PRICE, options);
//		Book book = booksService.findBookByIsbn(isbn);
//		Assert.assertTrue(book.getClass() == SpecialistBook.class);
//
//	}
//
//	@Test(expected = Exception.class)
//	public void createBookFailsNullOptions() throws BookException {
//		booksService.newBook("TEST", PRICE, null);
//
//	}
//
//	private void doTest(MapBooksService booksService) {
//
//		try {
//			Collection<Book> books = booksService.findAllBooks();
//			final int startSize = books.size();
//			String j2eeTitle = "Spring";
//			String isbn = booksService.newBook(j2eeTitle, PRICE, new HashMap<String, Object>());
//			books = booksService.findAllBooks();
//			final int endSize = books.size();
//			assertTrue(endSize == startSize + 1);
//			assertNotNull(isbn);
//			assertNotNull(booksService.findBookByIsbn(isbn));
//
//			try {
//				booksService.findBookByIsbn("ISBN-3");
//				fail("BookException must be thrown!");
//			} catch (BookException e) {
//				// OK
//			}
//			booksService.deleteBookByIsbn(isbn);
//			assertTrue(startSize == booksService.findAllBooks().size());
//			try {
//				booksService.deleteBookByIsbn(isbn);
//				fail("BookException must be thrown!");
//			} catch (BookException e) {
//				// OK
//			}
//
//		} catch (BookException bookException) {
//			bookException.printStackTrace();
//			fail(bookException.getMessage());
//		}
//
//	}
//
//	@Test
//	public void listBooks() {
//		Assert.assertNotNull(booksService.booksList());
//	}
//
//	@Test
//	public void listBooksAscending() {
//		Assert.assertNotNull(booksService.booksList(Ordering.ASCENDING));
//	}
//
//	@Test
//	public void listBooksDescending() {
//		Assert.assertNotNull(booksService.booksList(Ordering.DESCENDING));
//	}
//	@Test
//	public void listBooksPriceRange() {
//		Assert.assertNotNull(booksService.findBooksByPriceRange(12.5, 33.40));
//	}

}
