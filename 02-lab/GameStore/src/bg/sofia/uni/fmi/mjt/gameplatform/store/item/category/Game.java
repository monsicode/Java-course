package bg.sofia.uni.fmi.mjt.gameplatform.store.item.category;
import bg.sofia.uni.fmi.mjt.gameplatform.store.item.StoreItem;

import bg.sofia.uni.fmi.mjt.gameplatform.store.item.filter.ItemFilter;
import bg.sofia.uni.fmi.mjt.gameplatform.store.item.filter.PriceItemFilter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class Game extends BaseStoreItem {

    private String genre;

    public Game(String title, BigDecimal price, LocalDateTime releaseDate, String genre)
    {
        super(title,price,releaseDate);
        setGenre(genre);
    }

    public Game(String title, BigDecimal price, LocalDateTime releaseDate)
    {
       super(title,price,releaseDate);
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public String getGenre(){
        return genre;
    }


    public static void main(String[] args) {

        StoreItem item1 = new Game("God of war", new BigDecimal("59.99"),                   // price
                                   LocalDateTime.of(2017, 3, 3, 0, 0),       // releaseDate
                                     "Action-Adventure");

        StoreItem item2 = new Game("The Forest", new BigDecimal("24.99"),                   // price
                                        LocalDateTime.of(2015, 8, 20, 0, 0),       // releaseDate
                                        "Action-Adventure");

        StoreItem[] arr = {item1,item2};

        ItemFilter filter = new PriceItemFilter(new BigDecimal(30), new BigDecimal(60));

        for (var item : arr)
        {
            if(filter.matches(item)){
                System.out.println(item.getTitle());
            }
        }



    }



}
