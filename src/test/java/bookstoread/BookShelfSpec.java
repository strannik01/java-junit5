package bookstoread;

import static java.util.Arrays.asList;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import java.time.Year;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

@DisplayName("A bookshelf")
@ExtendWith(BooksParameterResolver.class)
public class BookShelfSpec {

	private BookShelf shelf;
	private Book effectiveJava;
	private Book codeComplete;
	private Book mythicalManMonth;
	private Book cleanCode;

	@BeforeEach
	void init(Map<String, Book> books) throws Exception {
		shelf = new BookShelf();
		this.effectiveJava = books.get("Effective Java");
		this.codeComplete = books.get("Code Complete");
        this.mythicalManMonth = books.get("The Mythical Man-Month");
        this.cleanCode = books.get("Clean Code");
	}
	
	@Nested
	@DisplayName("is empty")
	class IsEmpty {
		
		@DisplayName("bookshelf is empty when no book is added to it")
		@Test
		public void shelfEmptyWhenNoBookAdded() {
			List<Book> books = shelf.books();
			assertTrue(books.isEmpty(), () -> "BookShelf should be empty.");
		}
		
		@DisplayName("empty bookshelf remains empty when add is called without books")
		@Test
		public void emptyBookShelfWhenAddIsCalledWithoutBooks() {
			shelf.add();
			List<Book> books = shelf.books();
			assertTrue(books.isEmpty(), () -> "BookShelf should be empty.");
		}
	}
	
	@Nested
    @DisplayName("after adding books")
    class BooksAreAdded {
		
		@DisplayName("bookshelf contains two books when two books are added")
		@Test
		public void bookshelfConstainsTwoBooksAdded() {
			shelf.add(effectiveJava, codeComplete);
			List<Book> books = shelf.books();
			assertEquals(2, books.size(), () -> "BookShelf should have two books.");
		}
		
		@DisplayName("bookshelf returns an inmutable books collection to client")
		@Test
		void booksReturnedFromBookShelfIsImmutableForClient() {
			shelf.add(effectiveJava, codeComplete);
			List<Book> books = shelf.books();
			try {
				books.add(mythicalManMonth);
				fail(() -> "Should not be able to add book to books");
			} catch (Exception e) {
				assertTrue(e instanceof UnsupportedOperationException, () -> "Should throw UnsupportedOperationException.");
			}
		}
	}
	
	@Nested
	@DisplayName("bookshelf is arranged by specific criteria")
	class BooksGrouped {
		
		@DisplayName("bookshelf is arranged lexicographically by book title")
//		@Disabled("Needs to implement Comparator")
		@Test
		void bookshelfArrangedByBookTitle() {
			shelf.add(effectiveJava, codeComplete, mythicalManMonth);
			List<Book> books = shelf.arrange();
			assertEquals(asList(codeComplete, effectiveJava, mythicalManMonth), books,
					() -> "Books in a bookshelf should be arranged lexicographically by book title");
		}

		@DisplayName("books inside bookshelf are grouped according to user provided criteria")
		@Test
		void booksInBookShelfAreInInsertionOrderAfterCallingArrange() {
			shelf.add(effectiveJava, codeComplete, mythicalManMonth);
			shelf.arrange();
			List<Book> books = shelf.books();
			assertEquals(asList(effectiveJava, codeComplete, mythicalManMonth), books,
					() -> "Books in bookshelf are in insertion order");
		}

		@DisplayName("bookshelf is arranged by user provided criteria (by book title lexicographically)")
		@Test
		void bookshelfArrangedByUserProvidedCriteria() {
			shelf.add(effectiveJava, codeComplete, mythicalManMonth);
			Comparator<Book> reversed = Comparator.<Book>naturalOrder().reversed();
			List<Book> books = shelf.arrange(reversed);
			assertThat(books).isSortedAccordingTo(reversed);
		}

		@DisplayName("books inside bookshelf are grouped by publication year")
		@Test
		void groupBooksInsideBookShelfByPublicationYear() {
			shelf.add(effectiveJava, codeComplete, mythicalManMonth, cleanCode);

			Map<Year, List<Book>> booksByPublicationYear = shelf.groupByPublicationYear();

			assertThat(booksByPublicationYear).containsKey(Year.of(2008))
					.containsValues(Arrays.asList(effectiveJava, cleanCode));

			assertThat(booksByPublicationYear).containsKey(Year.of(2004))
					.containsValues(Collections.singletonList(codeComplete));

			assertThat(booksByPublicationYear).containsKey(Year.of(1975))
					.containsValues(Collections.singletonList(mythicalManMonth));
		}

		@DisplayName("books inside bookshelf are grouped according to user provided criteria(group by author name)")
		@Test
		void groupBooksByUserProvidedCriteria() {
			shelf.add(effectiveJava, codeComplete, mythicalManMonth, cleanCode);

			Map<String, List<Book>> booksByAuthor = shelf.groupBy(Book::getAuthor);

			assertThat(booksByAuthor).containsKey("Joshua Bloch").containsValues(Collections.singletonList(effectiveJava));
			
			assertThat(booksByAuthor).containsKey("Steve McConnel").containsValues(Collections.singletonList(codeComplete));
			
			assertThat(booksByAuthor).containsKey("Frederick Phillips Brooks").containsValues(Collections.singletonList(mythicalManMonth));
			
			assertThat(booksByAuthor).containsKey("Robert C. Martin").containsValues(Collections.singletonList(cleanCode));

		}
	}

}
