package bg.sofia.uni.fmi.mjt.gameplatform.store.item.category;

import bg.sofia.uni.fmi.mjt.gameplatform.store.item.StoreItem;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Arrays;

public class GameBundle extends BaseStoreItem {

    //??
   private final Game[] games;

    public GameBundle(String title, BigDecimal price, LocalDateTime releaseDate, Game[] games)
    {
        super(title, price, releaseDate);
        this.games = Arrays.copyOf(games, games.length);
    }

    public Game[] getGames(){
        return  Arrays.copyOf(games, games.length);
    }


}
