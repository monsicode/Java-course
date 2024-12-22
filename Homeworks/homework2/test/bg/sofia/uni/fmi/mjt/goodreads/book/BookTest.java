package bg.sofia.uni.fmi.mjt.goodreads.book;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class BookTest {

    private static final String[] tokens = {
        "id1",
        "Title",
        "Tom",
        "academy is a the superhero club superhero",
        "Action, Comedy",
        "3.4",
        "3",
        "baba.net"
    };

    @Test
    void testFactoryMethod() {
        Book expected = new Book("id1", "Title", "Tom", "academy is a the superhero club superhero",
            List.of("Action", "Comedy"), 3.4, 3, "baba.net");

        Book result = Book.of(tokens);

        assertEquals(expected, result, "The given token should return a valid book");
    }

}
