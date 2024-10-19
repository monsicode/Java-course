package bg.sofia.uni.fmi.mjt.gameplatform.store.item.category;

import bg.sofia.uni.fmi.mjt.gameplatform.store.item.StoreItem;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;

abstract class BaseStoreItem implements StoreItem {

    protected String title;
    protected BigDecimal price;
    protected LocalDateTime releaseDate;
    protected double rating;

    public BaseStoreItem(String title, BigDecimal price, LocalDateTime releaseDate){
        setTitle(title);
        setPrice(price);
        setReleaseDate(releaseDate);
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

        if(rating < 1 || rating > 5)
        {
            System.out.println("Price is out of range");
        }

        this.rating = rating;
    }

}
