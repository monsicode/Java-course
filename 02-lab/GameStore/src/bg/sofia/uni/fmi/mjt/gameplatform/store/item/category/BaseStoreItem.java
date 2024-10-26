package bg.sofia.uni.fmi.mjt.gameplatform.store.item.category;

import bg.sofia.uni.fmi.mjt.gameplatform.store.item.StoreItem;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.util.prefs.BackingStoreException;

abstract class BaseStoreItem implements StoreItem {

    protected String title;
    protected BigDecimal price;
    protected LocalDateTime releaseDate;
    protected double rating;
    private int countRatings;

    public BaseStoreItem(String title, BigDecimal price, LocalDateTime releaseDate){
        setTitle(title);
        setPrice(price);
        setReleaseDate(releaseDate);
        rating = 0;
        countRatings = 0;
    }

    @Override
    public String getTitle() {
        return title;
    }

    @Override
    public BigDecimal getPrice() {
        return price;
    }

    @Override
    public double getRating() {
        return rating;
    }

    @Override
    public LocalDateTime getReleaseDate() {
        return releaseDate;
    }

    @Override
    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public void setPrice(BigDecimal price) {
        BigDecimal roundedValue = price.setScale(2, RoundingMode.HALF_UP);
        this.price = roundedValue;
    }

    @Override
    public void setReleaseDate(LocalDateTime releaseDate) {
        this.releaseDate = releaseDate;
    }

    @Override
    public void rate(double rating) {
        double temp = this.rating * countRatings;
        countRatings++;

        this.rating = (temp + rating) / countRatings;
    }

    public static void main(String[] args) {
        BaseStoreItem game = new Game("Epic Adventure", new BigDecimal("29.99"), LocalDateTime.of(2024, 9, 15, 10, 0), "Adventure");

        game.rate(4);
        game.rate(3);
        game.rate(1);

        System.out.println(game.getRating());



    }


}
