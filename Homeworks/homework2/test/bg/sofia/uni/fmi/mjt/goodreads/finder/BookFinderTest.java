package bg.sofia.uni.fmi.mjt.goodreads.finder;

import bg.sofia.uni.fmi.mjt.goodreads.book.Book;
import bg.sofia.uni.fmi.mjt.goodreads.tokenizer.TextTokenizer;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class BookFinderTest {
    private final TextTokenizer tokenizer = mock(TextTokenizer.class);

    private final Book book1 = new Book("id1", "title1", "Tom", "academy is a the superhero club superhero",
        List.of("Action", "Horror", "Young Adult"), 3.4, 3, "baba");
    private final Book book2 = new Book("id2", "title2", "Tom", "academy superhero club superhero",
        List.of("Science Fiction", "Fantasy", "Adventure"), 3.4, 3, "baba");
    private final Book book3 = new Book("id3", "title3", "Greg", "superhero mission save club",
        List.of("Young Adult", "Horror"), 3.4, 3, "baba");
    private final Book book4 = new Book("id4", "title4", "Anna", "crime murder mystery club",
        List.of("Fantasy", "Criminal"), 3.4, 3, "baba");

    private final BookFinder bookFinder = new BookFinder(Set.of(book1, book2, book3, book4), tokenizer);

    @Test
    void testAllGenres() {
        Set<String> expectedSet = Set.of(
            "Action", "Horror", "Science Fiction", "Fantasy", "Adventure", "Young Adult", "Criminal"
        );

        assertEquals(expectedSet, bookFinder.allGenres(), "All genres should match");
    }

    @Test
    void testSearchByAuthorWithNullAuthor() {
        assertThrows(IllegalArgumentException.class, () -> bookFinder.searchByAuthor(null));
    }

    @Test
    void testSearchByAuthorWithEmptyString() {
        assertThrows(IllegalArgumentException.class, () -> bookFinder.searchByAuthor(""));
    }

    @Test
    void testSearchByAuthorWithTwoBooksToReturn() {
        List<Book> expectedBooks = List.of(book1, book2);
        List<Book> result = bookFinder.searchByAuthor("Tom");

        assertTrue(expectedBooks.containsAll(result) && result.containsAll(expectedBooks),
            "Book with id1 and id2 should be returned");
    }

    @Test
    void testSearchByAuthorWithZeroBooksToReturn() {
        List<Book> expectedBooks = List.of();

        List<Book> result = bookFinder.searchByAuthor("Bob");

        assertTrue(expectedBooks.containsAll(result) && result.containsAll(expectedBooks),
            "Book with id1 and id2 should be returned");
    }

    @Test
    void testSearchByGenresWithNullGenres() {
        assertThrows(IllegalArgumentException.class, () -> bookFinder.searchByGenres(null, MatchOption.MATCH_ALL));
    }

    @Test
    void testSearchByGenresWithOneExistingGenreMatchAll() {
        List<Book> expectedBooks = List.of(book1, book3);

        Set<String> searchedGenres = Set.of("Horror");

        List<Book> result = bookFinder.searchByGenres(searchedGenres, MatchOption.MATCH_ALL);

        assertTrue(expectedBooks.containsAll(result) && result.containsAll(expectedBooks));
    }

    @Test
    void testSearchByGenresWithMultipleExistingGenresMatchAll() {
        List<Book> expectedBooks = List.of(book1, book3);

        Set<String> searchedGenres = Set.of("Horror", "Young Adult");
        List<Book> result = bookFinder.searchByGenres(searchedGenres, MatchOption.MATCH_ALL);

        assertTrue(expectedBooks.containsAll(result) && result.containsAll(expectedBooks));
    }

    @Test
    void testSearchByGenresWithNotingToMatchMatchAll() {
        List<Book> expectedBooks = List.of();

        Set<String> searchedGenres = Set.of("Comedy", "Young Adult");
        List<Book> result = bookFinder.searchByGenres(searchedGenres, MatchOption.MATCH_ALL);

        assertTrue(expectedBooks.containsAll(result) && result.containsAll(expectedBooks));
    }

    @Test
    void testSearchByGenresWithMultipleGenresMatchAny() {
        List<Book> expectedBooks = List.of(book1, book3);

        Set<String> searchedGenres = Set.of("Comedy", "Young Adult");
        List<Book> result = bookFinder.searchByGenres(searchedGenres, MatchOption.MATCH_ANY);

        assertTrue(expectedBooks.containsAll(result) && result.containsAll(expectedBooks));
    }

    @Test
    void testSearchByGenresWithOneGenreMatchAny() {
        List<Book> expectedBooks = List.of(book1, book3);

        Set<String> searchedGenres = Set.of("Young Adult");
        List<Book> result = bookFinder.searchByGenres(searchedGenres, MatchOption.MATCH_ANY);

        assertTrue(expectedBooks.containsAll(result) && result.containsAll(expectedBooks));
    }

    @Test
    void testSearchByGenresWithNothingToMatchMatchAny() {
        List<Book> expectedBooks = List.of();

        Set<String> searchedGenres = Set.of("Comedy", "LGB");
        List<Book> result = bookFinder.searchByGenres(searchedGenres, MatchOption.MATCH_ANY);

        assertTrue(expectedBooks.containsAll(result) && result.containsAll(expectedBooks));
    }

    private void setMockForTokenizer() {
        when(tokenizer.tokenize("academy superhero club superhero"))
            .thenReturn(List.of("academy", "superhero", "club", "superhero"));
        when(tokenizer.tokenize("academy is a the superhero club superhero"))
            .thenReturn(List.of("academy", "superhero", "club", "superhero"));
        when(tokenizer.tokenize("superhero mission save club"))
            .thenReturn(List.of("superhero", "mission", "save", "club"));
        when(tokenizer.tokenize("crime murder mystery club"))
            .thenReturn(List.of("crime", "murder", "mystery", "club"));

        when(tokenizer.tokenize("title1"))
            .thenReturn(List.of("title1"));
        when(tokenizer.tokenize("title2"))
            .thenReturn(List.of("title2"));
        when(tokenizer.tokenize("title3"))
            .thenReturn(List.of("title3"));
        when(tokenizer.tokenize("title4"))
            .thenReturn(List.of("title4"));

    }

    @Test
    void testSearchByKeywordsOneWordInDescriptionMatchAny() {
        setMockForTokenizer();

        List<Book> expectedBooks = List.of(book1, book2, book3);

        Set<String> keywords = Set.of("superhero");
        List<Book> result = bookFinder.searchByKeywords(keywords, MatchOption.MATCH_ANY);

        assertTrue(expectedBooks.containsAll(result) && result.containsAll(expectedBooks));
    }

    @Test
    void testSearchByKeywordsTwoWordInDescriptionMatchAny() {
        setMockForTokenizer();
        List<Book> expectedBooks = List.of(book1, book2, book3, book4);

        Set<String> keywords = Set.of("superhero", "murder");
        List<Book> result = bookFinder.searchByKeywords(keywords, MatchOption.MATCH_ANY);

        assertTrue(expectedBooks.containsAll(result) && result.containsAll(expectedBooks));
    }

    @Test
    void testSearchByKeywordsOneWordInDescriptionOneInTitleMatchAny() {
        setMockForTokenizer();
        List<Book> expectedBooks = List.of(book1, book2, book3, book4);

        Set<String> keywords = Set.of("superhero", "title4");
        List<Book> result = bookFinder.searchByKeywords(keywords, MatchOption.MATCH_ANY);

        assertTrue(expectedBooks.containsAll(result) && result.containsAll(expectedBooks));
    }

    @Test
    void testSearchByKeywordsOneWordInTitleMatchAny() {
        setMockForTokenizer();

        List<Book> expectedBooks = List.of(book3);

        Set<String> keywords = Set.of("title3");
        List<Book> result = bookFinder.searchByKeywords(keywords, MatchOption.MATCH_ANY);

        assertTrue(expectedBooks.containsAll(result) && result.containsAll(expectedBooks));
    }

    @Test
    void testSearchByKeywordsWithSubstringOneWordInTitleMatchAny() {
        setMockForTokenizer();
        List<Book> expectedBooks = List.of(book2);

        Set<String> keywords = Set.of("title");
        List<Book> result = bookFinder.searchByKeywords(keywords, MatchOption.MATCH_ANY);

        assertFalse(expectedBooks.containsAll(result) && result.containsAll(expectedBooks));
    }

    @Test
    void testSearchByKeywordsWithNull() {
        assertThrows(IllegalArgumentException.class, () -> bookFinder.searchByKeywords(null, MatchOption.MATCH_ALL));
    }

    @Test
    void testSearchByKeywordsOneWordInDescriptionOneInTitleMatchAll() {
        setMockForTokenizer();
        List<Book> expectedBooks = List.of();

        Set<String> keywords = Set.of("superhero", "title4");
        List<Book> result = bookFinder.searchByKeywords(keywords, MatchOption.MATCH_ALL);

        assertTrue(expectedBooks.containsAll(result) && result.containsAll(expectedBooks));
    }

    @Test
    void testSearchByKeywordsOneWordInDescriptionOneInTitleMatchAllValid() {
        setMockForTokenizer();
        List<Book> expectedBooks = List.of(book3);

        Set<String> keywords = Set.of("superhero", "title3");
        List<Book> result = bookFinder.searchByKeywords(keywords, MatchOption.MATCH_ALL);

        assertTrue(expectedBooks.containsAll(result) && result.containsAll(expectedBooks));
    }

    @Test
    void testAllBooksReturn() {
        Set<Book> expectedBooks = Set.of(book1, book2, book3, book4);
        Set<Book> result = bookFinder.allBooks();
        assertTrue(expectedBooks.containsAll(result) && result.containsAll(expectedBooks));
    }

}
