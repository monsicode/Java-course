package bg.sofia.uni.fmi.mjt.goodreads.finder;

import bg.sofia.uni.fmi.mjt.goodreads.book.Book;
import bg.sofia.uni.fmi.mjt.goodreads.tokenizer.TextTokenizer;

import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static bg.sofia.uni.fmi.mjt.goodreads.Validator.emptyStringCheck;
import static bg.sofia.uni.fmi.mjt.goodreads.Validator.nullCheck;

public class BookFinder implements BookFinderAPI {

    private final Set<Book> books;
    private final TextTokenizer tokenizer;

    public BookFinder(Set<Book> books, TextTokenizer tokenizer) {
        this.books = books;
        this.tokenizer = tokenizer;
    }

    public Set<Book> allBooks() {
        return Collections.unmodifiableSet(books);
    }

    @Override
    public Set<String> allGenres() {

        return books.stream()
            .map(Book::genres)
            .flatMap(List::stream)
            .collect(Collectors.toSet());

    }

    @Override
    public List<Book> searchByAuthor(String authorName) {
        nullCheck(authorName, "Author name cannot be null!");
        emptyStringCheck(authorName, "Author cannot be empty");

        return books.stream()
            .filter(book -> book.author().equals(authorName))
            .toList();
    }

    @Override
    public List<Book> searchByGenres(Set<String> genres, MatchOption option) {
        nullCheck(genres, "Genres cannot be null!");

        return switch (option) {
            case MATCH_ANY -> books.stream()
                .filter(book -> book.genres().stream().anyMatch(genres::contains))
                .toList();
            case MATCH_ALL -> books.stream()
                .filter(book -> book.genres().containsAll(genres))
                .toList();
        };

    }

    @Override
    public List<Book> searchByKeywords(Set<String> keywords, MatchOption option) {
        nullCheck(keywords, "Keywords cannot be null");

        return switch (option) {
            case MATCH_ANY -> books.stream()
                .filter(book -> keywords.stream()
                    .anyMatch(keyword ->
                            tokenizer.tokenize(book.title()).contains(keyword) ||
                            tokenizer.tokenize(book.description()).contains(keyword)))
                .toList();
            case MATCH_ALL -> books.stream()
                .filter(book -> keywords.stream()
                    .allMatch(keyword ->
                            tokenizer.tokenize(book.title()).contains(keyword) ||
                            tokenizer.tokenize(book.description()).contains(keyword)))
                .toList();
        };
    }

}