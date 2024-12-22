//package bg.sofia.uni.fmi.mjt.goodreads.recommender.similaritycalculator.descriptions;
//
//import bg.sofia.uni.fmi.mjt.goodreads.book.Book;
//import bg.sofia.uni.fmi.mjt.goodreads.tokenizer.TextTokenizer;
//
//import java.io.FileReader;
//import java.io.IOException;
//import java.io.Reader;
//import java.util.List;
//import java.util.Set;
//
//import static org.junit.jupiter.api.Assertions.assertEquals;
//
//public class TFIDFSimilarityCalculatorTest {
//    public static void main(String[] args) {
//        Set<Book> books = Set.of(
//            new Book("id", "title", "Tom", "academy superhero club superhero",
//                List.of(new String[] {"Action", "Others"}), 3.4, 3, "baba")
//        );
//
//        Book book = new Book("id", "title", "Tom", "academy is a the superhero club superhero",
//            List.of(new String[] {"Action", "Others"}), 3.4, 3, "baba");
//
//        try (Reader stopwordsReader = new FileReader("stopwords.txt")) {
//            TextTokenizer tokenizer = new TextTokenizer(stopwordsReader);
//
//            TFIDFSimilarityCalculator calculator = new TFIDFSimilarityCalculator(books, tokenizer);
//
//            System.out.println(calculator.computeIDF(book));
//
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//
//    }
//
//    void test(){
//        assertEquals();
//    }
//
////    public static void main(String[] args) {
////        Set<Book> books = Set.of(
////            new Book("id", "title", "Tom", "academy superhero club superhero",
////                List.of(new String[] {"Action", "Others"}), 3.4, 3, "baba"),
////            new Book("id2", "title", "Tom", "superhero mission save club\"",
////                List.of(new String[] {"Action", "Others"}), 3.4, 3, "baba"),
////            new Book("id3", "title", "Tom", "crime murder mystery club",
////                List.of(new String[] {"Action", "Others"}), 3.4, 3, "baba")
////        );
////
////        Book book = new Book("id", "title", "Tom", "academy is a the superhero club superhero",
////            List.of(new String[] {"Action", "Others"}), 3.4, 3, "baba");
////
////        try (Reader stopwordsReader = new FileReader("stopwords.txt")) {
////            TextTokenizer tokenizer = new TextTokenizer(stopwordsReader);
////
////            TFIDFSimilarityCalculator calculator = new TFIDFSimilarityCalculator(books, tokenizer);
////
////            System.out.println(calculator.computeTFIDF(book));
////
////        } catch (IOException e) {
////            e.printStackTrace();
////        }
////
////    }
//
//}
