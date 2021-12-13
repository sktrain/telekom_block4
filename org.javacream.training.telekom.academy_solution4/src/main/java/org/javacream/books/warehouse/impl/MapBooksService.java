package org.javacream.books.warehouse.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.javacream.books.isbngenerator.api.IsbnGenerator;
import org.javacream.books.warehouse.api.Book;
import org.javacream.books.warehouse.api.BookException;
import org.javacream.books.warehouse.api.BooksService;
import org.javacream.store.api.StoreService;
import org.javacream.util.Ordering;

public class MapBooksService implements BooksService{

	private StoreService storeService;
	private IsbnGenerator isbnGenerator;

	private Map<Set<String>, Function<Map<String, Object>, Book>> generators;

	private Map<String, Book> books;

	@Override
	public String newBook(String title, double price, Map<String, Object> options) throws BookException {
		String isbn = isbnGenerator.next();
		Function<Map<String, Object>, Book> generator = generators.get(options.keySet());
		Book book = generator.apply(options);
		book.setIsbn(isbn);
		book.setTitle(title);
		book.setPrice(price);
		books.put(isbn, book);
		return isbn;
	}

	@Override
	public Book findBookByIsbn(String isbn) throws BookException {
		Book result = (Book) books.get(isbn);
		if (result == null) {
			throw new BookException(BookException.BookExceptionType.NOT_FOUND, isbn);
		}
		int stock = storeService.getStock("books", isbn);
		result.setAvailable(stock > 0);
		return result;
	}

	@Override
	public Book updateBook(Book bookDetailValue) throws BookException {
		if (bookDetailValue.getPrice() <= 0) {
			throw new BookException(BookException.BookExceptionType.CONSTRAINT, "price <= 0");
		}

		Book value = books.get(bookDetailValue.getIsbn());
		value.setTitle(bookDetailValue.getTitle());
		value.setPrice(bookDetailValue.getPrice());
		value.setAvailable(bookDetailValue.isAvailable());
		return value;
	}

	@Override
	public void deleteBookByIsbn(String isbn) throws BookException {
		Object result = books.remove(isbn);
		if (result == null) {
			throw new BookException(BookException.BookExceptionType.NOT_DELETED, isbn);
		}
	}

	@Override
	public Collection<Book> findAllBooks() {
		return new ArrayList<Book>(books.values());
	}
	@Override
	public  List<Book> findBooksByType(Class<? extends Book> type) {
		return findAllBooks().stream().filter((book) -> book.getClass().isAssignableFrom(type))
				.collect(Collectors.toList());
	}

	@Override
	public  List<Book> findBooksByPriceRange(double minPrice, double maxPrice) {
		return findAllBooks().stream().filter((book) -> (book.getPrice() >= minPrice) && (book.getPrice() <= maxPrice))
				.sorted().collect(Collectors.toList());
	}

	@Override
	public List<Book> findBooksByTitleCriterion(String expression) {
		return findAllBooks().stream().filter((book) -> book.getTitle().matches(expression))
				.collect(Collectors.toList());
	}

	@Override
	public List<Book> booksList(Ordering ordering) {
		switch (ordering) {
		case ASCENDING: {
			return findAllBooks().stream().sorted((book1, book2) -> book1.getIsbn().compareTo(book2.getIsbn()))
					.collect(Collectors.toList());
		}
		case DESCENDING: {
			return findAllBooks().stream().sorted((book1, book2) -> book2.getIsbn().compareTo(book1.getIsbn()))
					.collect(Collectors.toList());
		}
		default: {
			return findAllBooks().stream().sorted((book1, book2) -> book1.getIsbn().compareTo(book2.getIsbn()))
					.collect(Collectors.toList());
		}
		}
	}

	@Override
	public List<Book> booksList() {
		return booksList(Ordering.ASCENDING);
	}

	public void setStoreService(StoreService storeService) {
		this.storeService = storeService;
	}

	public void setIsbnGenerator(IsbnGenerator isbnGenerator) {
		this.isbnGenerator = isbnGenerator;
	}

	public void setGenerators(Map<Set<String>, Function<Map<String, Object>, Book>> generators) {
		this.generators = generators;
	}

	public void setBooks(Map<String, Book> books) {
		this.books = books;
	}

}
