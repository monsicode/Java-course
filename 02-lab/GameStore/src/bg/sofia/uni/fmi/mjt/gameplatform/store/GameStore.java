package bg.sofia.uni.fmi.mjt.gameplatform.store;

import bg.sofia.uni.fmi.mjt.gameplatform.store.item.StoreItem;
import bg.sofia.uni.fmi.mjt.gameplatform.store.item.category.Game;
import bg.sofia.uni.fmi.mjt.gameplatform.store.item.filter.ItemFilter;

import java.math.BigDecimal;

public class GameStore implements StoreAPI {

    private static final String PROMO_CODE_40 = "VAN40";
    private static final String PROMO_CODE_100 = "100YO";
    private static final BigDecimal DISCOUNT_40 = new BigDecimal("0.60");
    private static final BigDecimal DISCOUNT_100 = new BigDecimal("0");

    private StoreItem[] availableItems;
    boolean isDiscounted = false;

    public GameStore(StoreItem[] availableItems) {
        this.availableItems = availableItems;
    }


    @Override
    public StoreItem[] findItemByFilters(ItemFilter[] itemFilters) {

        StoreItem[] tempMatches = new StoreItem[availableItems.length];
        int matchCount = 0;

        for (int i = 0; i < availableItems.length; i++) {
            boolean matchesAllFilters = true;

            for (int j = 0; j < itemFilters.length; j++) {
                if (!itemFilters[j].matches(availableItems[i])) {
                    matchesAllFilters = false;
                    break;
                }
            }

            if (matchesAllFilters) {
                tempMatches[matchCount] = availableItems[i];
                matchCount++;
            }
        }

        StoreItem[] matchingItems = new StoreItem[matchCount];
        for (int k = 0; k < matchCount; k++) {
            matchingItems[k] = tempMatches[k];
        }

        return matchingItems;
    }

    @Override
    public void applyDiscount(String promoCode) {

        if (!isDiscounted) {
            if (promoCode.equals(PROMO_CODE_40)) {
                for (int i = 0; i < availableItems.length; i++) {
                    availableItems[i].setPrice(availableItems[i].getPrice().multiply(DISCOUNT_40));
                }
                isDiscounted = true;
            } else if (promoCode.equals(PROMO_CODE_100)) {
                for (int i = 0; i < availableItems.length; i++) {
                    availableItems[i].setPrice(availableItems[i].getPrice().multiply(DISCOUNT_100));
                }
                isDiscounted = true;
            }
        }
    }

    @Override
    public boolean rateItem(StoreItem item, int rating) {
        if(rating < 1 || rating > 5)
        {
            System.out.println("Rating is out of range");
            return false;
        }

        for (int i = 0; i < availableItems.length; i++) {
            if (availableItems[i].getTitle().equals(item.getTitle())) {
                availableItems[i].rate(rating);
                return true;
            }
        }

        return false;
    }
}
