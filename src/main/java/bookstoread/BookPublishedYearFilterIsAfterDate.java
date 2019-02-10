package bookstoread;

import java.time.LocalDate;

public class BookPublishedYearFilterIsAfterDate implements BookFilter {
	private LocalDate startDate;
	
	static BookPublishedYearFilterIsAfterDate After(int year) {
		BookPublishedYearFilterIsAfterDate filter = new BookPublishedYearFilterIsAfterDate();
		filter.startDate = LocalDate.of(year, 12, 31);
		return filter;
	}

	@Override
	public boolean apply(final Book b) {
		return b.getPublishedOn().isAfter(startDate);
	}

}
