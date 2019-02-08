package bookstoread;

import java.time.LocalDate;

public class Book implements Comparable<Book> {
	private final String title;
	private final String author;
	private final LocalDate publishedOn;
	private LocalDate startedReadingOn;
	private LocalDate finishedReadingOn;

	public Book(String title, String author, LocalDate publishedOn) {
		this.title = title;
		this.author = author;
		this.publishedOn = publishedOn;
	}

	public String getTitle() {
		return title;
	}

	public String getAuthor() {
		return author;
	}

	public LocalDate getPublishedOn() {
		return publishedOn;
	}

	@Override
	public String toString() {
		return "Book [title=" + title + ", author=" + author + ", publishedOn=" + publishedOn + "]";
	}

	@Override
	public int compareTo(Book that) {
		return this.title.compareTo(that.title);
	}

	public void startedReadingOn(LocalDate startedOn) {
		this.startedReadingOn = startedOn;
	}

	public void finishedReadingOn(LocalDate finishedOn) {
		this.finishedReadingOn = finishedOn;
	}
	
	public boolean isRead() {
		return startedReadingOn != null && finishedReadingOn != null;
	}

	public boolean isProgress() {
		return false;
	}
}
