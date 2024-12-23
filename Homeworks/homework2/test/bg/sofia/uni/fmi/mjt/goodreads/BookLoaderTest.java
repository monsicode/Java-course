package bg.sofia.uni.fmi.mjt.goodreads;

import bg.sofia.uni.fmi.mjt.goodreads.book.Book;
import org.junit.jupiter.api.Test;

import java.io.StringReader;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class BookLoaderTest {

    @Test
    void testLoadCountBooks() {
        String csvData = """
            N,Book,Author,Description,Genres,Avg_Rating,Num_Ratings,URL
            0,To Kill a Mockingbird,Harper Lee,"The unforgettable novel of a childhood in a sleepy Southern town and the crisis of conscience that rocked it. ""To Kill A Mockingbird"" became both an instant bestseller and a critical success when it was first published in 1960. It went on to win the Pulitzer Prize in 1961 and was later made into an Academy Award-winning film, also a classic.Compassionate, dramatic, and deeply moving, ""To Kill A Mockingbird"" takes readers to the roots of human behavior - to innocence and experience, kindness and cruelty, love and hatred, humor and pathos. Now with over 18 million copies in print and translated into forty languages, this regional story by a young Alabama woman claims universal appeal. Harper Lee always considered her book to be a simple love story. Today it is regarded as a masterpiece of American literature.","['Classics', 'Fiction', 'Historical Fiction', 'School', 'Literature', 'Young Adult', 'Historical']",4.27,"5,691,311",https://www.goodreads.com/book/show/2657.To_Kill_a_Mockingbird
            1,"Harry Potter and the Philosopher’s Stone (Harry Potter, #1)",J.K. Rowling,"Harry Potter thinks he is an ordinary boy - until he is rescued by an owl, taken to Hogwarts School of Witchcraft and Wizardry, learns to play Quidditch and does battle in a deadly duel. The Reason ... HARRY POTTER IS A WIZARD!","['Fantasy', 'Fiction', 'Young Adult', 'Magic', 'Childrens', 'Middle Grade', 'Classics']",4.47,"9,278,135",https://www.goodreads.com/book/show/72193.Harry_Potter_and_the_Philosopher_s_Stone
            """;

        Set<Book> books = BookLoader.load(new StringReader(csvData));

        assertEquals(2, books.size(), "The number of loaded books is incorrect");
    }

    @Test
    void testLoadValidBooks() {
        String csvData = """
            N,Book,Author,Description,Genres,Avg_Rating,Num_Ratings,URL
            0,To Kill a Mockingbird,Harper Lee,"The unforgettable novel of a childhood in a sleepy Southern town and the crisis of conscience that rocked it. ""To Kill A Mockingbird"" became both an instant bestseller and a critical success when it was first published in 1960. It went on to win the Pulitzer Prize in 1961 and was later made into an Academy Award-winning film, also a classic.Compassionate, dramatic, and deeply moving, ""To Kill A Mockingbird"" takes readers to the roots of human behavior - to innocence and experience, kindness and cruelty, love and hatred, humor and pathos. Now with over 18 million copies in print and translated into forty languages, this regional story by a young Alabama woman claims universal appeal. Harper Lee always considered her book to be a simple love story. Today it is regarded as a masterpiece of American literature.","['Classics', 'Fiction', 'Historical Fiction', 'School', 'Literature', 'Young Adult', 'Historical']",4.27,"5,691,311",https://www.goodreads.com/book/show/2657.To_Kill_a_Mockingbird
            1,"Harry Potter and the Philosopher’s Stone (Harry Potter, #1)",J.K. Rowling,"Harry Potter thinks he is an ordinary boy - until he is rescued by an owl, taken to Hogwarts School of Witchcraft and Wizardry, learns to play Quidditch and does battle in a deadly duel. The Reason ... HARRY POTTER IS A WIZARD!","['Fantasy', 'Fiction', 'Young Adult', 'Magic', 'Childrens', 'Middle Grade', 'Classics']",4.47,"9,278,135",https://www.goodreads.com/book/show/72193.Harry_Potter_and_the_Philosopher_s_Stone
            """;

        Set<Book> books = BookLoader.load(new StringReader(csvData));

        Book book1 = Book.of(new String[] {
            "0",
            "To Kill a Mockingbird",
            "Harper Lee",
            "The unforgettable novel of a childhood in a sleepy Southern town and the crisis of conscience that rocked it. \"To Kill A Mockingbird\" became both an instant bestseller and a critical success when it was first published in 1960. It went on to win the Pulitzer Prize in 1961 and was later made into an Academy Award-winning film, also a classic.Compassionate, dramatic, and deeply moving, \"To Kill A Mockingbird\" takes readers to the roots of human behavior - to innocence and experience, kindness and cruelty, love and hatred, humor and pathos. Now with over 18 million copies in print and translated into forty languages, this regional story by a young Alabama woman claims universal appeal. Harper Lee always considered her book to be a simple love story. Today it is regarded as a masterpiece of American literature.",
            "['Classics', 'Fiction', 'Historical Fiction', 'School', 'Literature', 'Young Adult', 'Historical']",
            "4.27",
            "5,691,311",
            "https://www.goodreads.com/book/show/2657.To_Kill_a_Mockingbird"
        });

        Book book2 = Book.of(new String[] {
            "1",
            "Harry Potter and the Philosopher’s Stone (Harry Potter, #1)",
            "J.K. Rowling",
            "Harry Potter thinks he is an ordinary boy - until he is rescued by an owl, taken to Hogwarts School of Witchcraft and Wizardry, learns to play Quidditch and does battle in a deadly duel. The Reason ... HARRY POTTER IS A WIZARD!",
            "['Fantasy', 'Fiction', 'Young Adult', 'Magic', 'Childrens', 'Middle Grade', 'Classics']",
            "4.47",
            "9,278,135",
            "https://www.goodreads.com/book/show/72193.Harry_Potter_and_the_Philosopher_s_Stone"
        });

        Set<Book> expectedBooks = Set.of(book1, book2);

        assertTrue(expectedBooks.containsAll(books), "Book is missing from the loaded books");
    }

    @Test
    void testLoadInvalidDataThrowsException() {
        String invalidCsvData = """
        N,Book,Author,Description,Genres,Avg_Rating,Num_Ratings,URL
        0,To Kill a Mockingbird,Harper Lee,"The unforgettable novel...",['Classics', 'Fiction'],4.27,"5,691,311"
        """;

        assertThrows(IllegalArgumentException.class,
            () -> BookLoader.load(new StringReader(invalidCsvData)),
            "Expected IllegalArgumentException to be thrown for invalid CSV data");
    }

}
