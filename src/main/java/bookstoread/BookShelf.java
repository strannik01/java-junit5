package bookstoread;

import java.time.Year;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

public class BookShelf {

	private final List<Book> books = new ArrayList<>();

	public List<Book> books() {
		return Collections.unmodifiableList(books);
	}

	public void add(Book... bookToAdd) {
		Arrays.stream(bookToAdd).forEach(books::add);
	}

	public List<Book> arrange() {
		return arrange(Comparator.naturalOrder());
	}

	public List<Book> arrange(Comparator<Book> criteria) {
		return books.stream().sorted(criteria).collect(Collectors.toList());
	}

	public Map<Year, List<Book>> groupByPublicationYear() {
		return groupBy(book -> Year.of(book.getPublishedOn().getYear()));
	}

	public <K> Map<K, List<Book>> groupBy(Function<Book, K> fx) {
		return books.stream().collect(Collectors.groupingBy(fx));
	}

	public Progress progress() {
		int booksRead = Long.valueOf(books.stream().filter(Book::isRead).count()).intValue();
		int booksToRead = books.size() - booksRead;
		int percentageCompleted = booksRead * 100 / books.size();
		int percentageToRead = booksToRead * 100 / books.size();
		return new Progress(percentageCompleted, percentageToRead, 0);
	}

}
