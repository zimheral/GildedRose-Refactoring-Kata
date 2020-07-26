package com.gildedrose;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

class GildedRose {
    Item[] items;

    /**
     * Added a field for easy modification as LEGENDAY_ITEMS could eventually be composed of more items than Sulfuras.
     * For the moment it contains just one element so it was made a singleton list.
     */
    private static final List<String> LEGENDARY_ITEMS = Collections.singletonList("Sulfuras, Hand of Ragnaros");
    private static final String AGED_BRIE = "Aged Brie";

    public GildedRose(Item[] items) {
        this.items = items;
    }

    /**
     * Method refactored into specialized ones to improve reusability and readability
     * After delegation it's only concern is to filter out Legendary Items as this ones are not processed
     * according to the requirements
     */
    public void updateQuality() {
        Arrays.stream(items).filter(item -> !LEGENDARY_ITEMS.contains(item.name)).forEach(this::process);
    }

    /**
     * Could have been a switch but I went with an if-else as it made the  expression matching more straightforward
     * @param item
     */
    private void process(Item item) {
        String itemName = item.name;
        if (itemName.matches("(?i).*" + AGED_BRIE + ".*")) {
            processAgedBrie(item);
        } else if (itemName.matches("(?i).*Backstage passes.*")) {
            processBackstagePass(item);
        } else if (itemName.matches("(?i).*Conjured.*")) {
            processConjuredItems(item);
        } else {
            processNormalItems(item);
        }
    }

    /**
     * Logic for processing regular Items not the specialized ones
     * @param item
     */
    private void processNormalItems(Item item) {
        item.quality = Utils.decreaseQuality(item);
        item.sellIn = Utils.decreaseSellIn(item);
        if (item.sellIn < 0) {
            item.quality = Utils.decreaseQuality(item);
        }
    }

    /**
     * Logic for processing backstage pass Items
     * @param item
     */
    private void processBackstagePass(Item item) {
        item.quality = Utils.increaseQuality(item);
        if (item.sellIn < 11) {
            item.quality = Utils.increaseQuality(item);
        }
        if (item.sellIn < 6) {
            item.quality = Utils.increaseQuality(item);
        }
        item.sellIn = Utils.decreaseSellIn(item);
        if (item.sellIn < 0) {
            item.quality = 0;
        }
    }

    /**
     * Logic for processing Aged Brie
     * @param item
     */
    private void processAgedBrie(Item item) {
        item.quality = Utils.increaseQuality(item);
        item.sellIn = Utils.decreaseSellIn(item);
        if (item.sellIn < 0) {
            item.quality = Utils.increaseQuality(item);
        }
    }

    /**
     * New Logic for processing ConjuredItems
     * Utils.decreaseQuality was overloaded to include a second param for specifying the number of iterations for
     * decreasing the quality
     * @param item
     */
    private void processConjuredItems(Item item) {
        item.quality = Utils.decreaseQuality(item, 2);
        item.sellIn = Utils.decreaseSellIn(item);
        if (item.sellIn < 0) {
            item.quality = Utils.decreaseQuality(item, 2);
        }
    }
}