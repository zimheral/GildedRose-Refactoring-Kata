package com.gildedrose;

/**
 * A simple utility class which includes the methods for quality and sellIn calculation
 */

final class Utils {

    private Utils() {
    }

    static int increaseQuality(Item item) {
        if (item.quality < 50) {
            return item.quality + 1;
        }
        return item.quality;
    }

    static int decreaseQuality(Item item) {
        if (item.quality > 0) {
            item.quality = item.quality - 1;
        }
        return item.quality;
    }

    /**
     * Method overloaded for Conjured Items with foresight in possible other additions which might need different iterations for
     * decreasing quality
     * @param item
     * @param rate : Number of iterations
     * @return
     */
    static int decreaseQuality(Item item, int rate) {
        for (int i = 0; i < rate; i++) {
            if (item.quality > 0) {
                item.quality = item.quality - 1;
            }
        }
        return item.quality;
    }

    static int decreaseSellIn(Item item) {
        return item.sellIn - 1;
    }
}