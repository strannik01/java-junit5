package bookstoread;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

@Nested
@ExtendWith(BooksParameterResolver.class)
@DisplayName("search")
public class BookShelfSeachSpec {
	private BookShelf shelf;
	private Book effectiveJava;
	private Book codeComplete;
	private Book mythicalManMonth;
	private Book cleanCode;
	
	@BeforeEach
	void setup(Map<String, Book> books) {
		shelf = new BookShelf();
		this.effectiveJava = books.get("Effective Java");
		this.codeComplete = books.get("Code Complete");
		this.mythicalManMonth = books.get("The Mythical Man-Month");
		this.cleanCode = books.get("Clean Code");
		shelf.add(codeComplete, effectiveJava, mythicalManMonth, cleanCode);
	}
	
	@Test
    @DisplayName("should find books with title containing text")
    void shouldFindBooksWithTitleContainingText() {
        List<Book> books = shelf.findBooksByTitle("code");
        assertThat(books.size()).isEqualTo(2);
    }
}
