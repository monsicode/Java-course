package bg.sofia.uni.fmi.mjt.goodreads;

import bg.sofia.uni.fmi.mjt.goodreads.book.Book;
import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvException;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.Set;
import java.util.stream.Collectors;

public class BookLoader {
    public static Set<Book> load(Reader reader) {
        try (CSVReader csvReader = new CSVReader(reader)) {
            return csvReader.readAll().stream()
                .skip(1)
                .map(Book::of)
                .collect(Collectors.toSet());

        } catch (IOException | CsvException ex) {
            throw new IllegalArgumentException("Could not load dataset", ex);
        }
    }


    public static void main(String[] args) throws FileNotFoundException {

        String filePath = "goodreads_data.csv";

        try (FileReader reader = new FileReader(filePath)) {

            Set<Book> books = BookLoader.load(reader);

            books.stream()
                .limit(2)
                .forEach(book -> System.out.println(book));

        } catch (IOException err) {

        }

    }

}