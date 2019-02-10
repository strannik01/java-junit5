package bookstoread;

import java.time.LocalDate;

public class BookPublishedYearFilterIsBeforeDate implements BookFilter {
	private LocalDate startDate;
	
	static BookFilter Before(int year) {
		BookPublishedYearFilterIsBeforeDate filter = new BookPublishedYearFilterIsBeforeDate();
		filter.startDate = LocalDate.of(year, 12, 31);
		return filter;
	}
	
	@Override
	public boolean apply(Book b) {
		return b.getPublishedOn().isBefore(startDate);
	}

}
