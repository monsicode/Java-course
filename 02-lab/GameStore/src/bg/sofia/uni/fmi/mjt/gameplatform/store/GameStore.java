package bg.sofia.uni.fmi.mjt.gameplatform.store;

import bg.sofia.uni.fmi.mjt.gameplatform.store.item.StoreItem;
import bg.sofia.uni.fmi.mjt.gameplatform.store.item.filter.ItemFilter;

public class GameStore implements StoreAPI{

    @Override
    public StoreItem[] findItemByFilters(ItemFilter[] itemFilters) {
        return new StoreItem[0];
    }

    @Override
    public void applyDiscount(String promoCode) {

    }

    @Override
    public boolean rateItem(StoreItem item, int rating) {
        return false;
    }
}
