package org.javacream.books.warehouse.api;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.javacream.util.Ordering;

public interface BooksService {

	String newBook(String title, double price, Map<String, Object> options) throws BookException;

	Book findBookByIsbn(String isbn) throws BookException;

	Book updateBook(Book bookDetailValue) throws BookException;

	void deleteBookByIsbn(String isbn) throws BookException;

	Collection<Book> findAllBooks();

	List<Book> findBooksByType(Class<? extends Book> type);

	List<Book> findBooksByPriceRange(double minPrice, double maxPrice);

	List<Book> findBooksByTitleCriterion(String expression);

	List<Book> booksList(Ordering ordering);

	List<Book> booksList();

}