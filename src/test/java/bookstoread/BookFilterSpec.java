package bookstoread;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDate;
import java.time.Month;
import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

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

		@Test
		@DisplayName("Composite criteria invokes multiple filters")
		void shouldFilterOnMultiplesCriteria() {
			/** 
			 * Para evitar este engorroso test se puede construir un Mock, como MockedFilter
			 * Esto aún así resulta molesto ya que cada vez que quieramos probar nuestra funcionalidad deberemos crear nuestros Mocks
			 * Es posible usar Mockito para ejecutar dicha tarea. En otro ejemplo veremos esto.
			 */
			CompositeFilter compositeFilter = new CompositeFilter();
			final Map<Integer, Boolean> invocationMap = new HashMap<>();
			compositeFilter.addFilter(b -> {
				invocationMap.put(1, true);
				return true;
			});
			assertTrue(compositeFilter.apply(cleanCode));
			
//			CompositeFilter compositeFilter = new CompositeFilter();
//			compositeFilter.addFilter(b -> false);
//			assertFalse(compositeFilter.apply(cleanCode));
		}

		@Test
//		@Disabled
		@DisplayName("Composite criteria does not invoke after first failure")
		void shouldNotInvokeAfterFirstFailure() {
			CompositeFilter compositeFilter = new CompositeFilter();
			
			BookFilter invokedMockedFilter = Mockito.mock(BookFilter.class);
			Mockito.when(invokedMockedFilter.apply(cleanCode)).thenReturn(false);
			compositeFilter.addFilter(invokedMockedFilter);
			
			BookFilter nonInvokedMockedFilter = Mockito.mock(BookFilter.class);
			Mockito.when(nonInvokedMockedFilter.apply(cleanCode)).thenReturn(true);
			compositeFilter.addFilter(nonInvokedMockedFilter);
			
			assertFalse(compositeFilter.apply(cleanCode));
			Mockito.verify(invokedMockedFilter).apply(cleanCode);
			Mockito.verifyZeroInteractions(nonInvokedMockedFilter);
			
//			compositeFilter.addFilter(b -> false);
//			compositeFilter.addFilter(b -> true);
//			assertFalse(compositeFilter.apply(cleanCode));
		}

		@Test
//		@Disabled
		@DisplayName("Composite criteria invokes all filters")
		void shouldInvokeAllFilters() {
			CompositeFilter compositeFilter = new CompositeFilter();
			BookFilter firstInvokedMockedFilter = Mockito.mock(BookFilter.class);
			Mockito.when(firstInvokedMockedFilter.apply(cleanCode)).thenReturn(true);
			compositeFilter.addFilter(firstInvokedMockedFilter);
			
			BookFilter secondInvokedMockedFilter = Mockito.mock(BookFilter.class);
			Mockito.when(secondInvokedMockedFilter.apply(cleanCode)).thenReturn(true);
			compositeFilter.addFilter(secondInvokedMockedFilter);
			
			assertTrue(compositeFilter.apply(cleanCode));
			Mockito.verify(firstInvokedMockedFilter).apply(cleanCode);
			Mockito.verify(secondInvokedMockedFilter).apply(cleanCode);
			
//			CompositeFilter compositeFilter = new CompositeFilter();
//			compositeFilter.addFilter(b -> true);
//			compositeFilter.addFilter(b -> true);
//			assertTrue(compositeFilter.apply(cleanCode));
		}
		
		
	}

}
