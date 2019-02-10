package bookstoread;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDate;
import java.time.Month;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

public class BookFilterSpec {
	private Book cleanCode;
	private Book codeComplete;

	@BeforeEach
	void init() {
		cleanCode = new Book("Clean Code", "Robert C. Martin", LocalDate.of(2008, Month.AUGUST, 1));
		codeComplete = new Book("Code Complete", "Steve McConnel", LocalDate.of(2004, Month.JUNE, 9));
	}
	
	@Nested
	@DisplayName("book published date")
	class BookPublishedFilterSpec {
		
		@Test
		@DisplayName("is after specified year")
		void validateBookPublishedYearIsAfterDateSpecified() {
			BookFilter filter = BookPublishedYearFilterIsAfterDate.After(2007);
			assertTrue(filter.apply(cleanCode));
			assertFalse(filter.apply(codeComplete));
		}
		
		@Test
		@DisplayName("is before specified year")
		void validateBookPublishedYearIsBeforeDateSpecified() {
			BookFilter filter = BookPublishedYearFilterIsBeforeDate.Before(2007);
			assertFalse(filter.apply(cleanCode));
			assertTrue(filter.apply(codeComplete));
		}
		
	}

}
