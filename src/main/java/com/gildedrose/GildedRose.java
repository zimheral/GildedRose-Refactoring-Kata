package com.gildedrose;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

class GildedRose {
    Item[] items;
    private static final List<String> LEGENDARY_ITEMS = Collections.singletonList("Sulfuras, Hand of Ragnaros");
    private static final String AGED_BRIE = "Aged Brie";

    public GildedRose(Item[] items) {
        this.items = items;
    }

    public void updateQuality() {
        Arrays.stream(items).filter(item -> !LEGENDARY_ITEMS.contains(item.name)).forEach(item -> {
            String itemName = item.name;
            if (itemName.matches("(?i).*" + AGED_BRIE + ".*")) {
                processAgedBrie(item);
            } else if (itemName.matches("(?i).*Backstage passes.*")) {
                processBackstagePass(item);
            } else {
                processNormalItems(item);
            }
        });
    }

    private void processNormalItems(Item item) {
        if (item.quality > 0) {
            item.quality = item.quality - 1;
        }
        item.sellIn = decreaseSellIn(item);
        if (item.sellIn < 0) {
            if (item.quality > 0) {
                item.quality = item.quality - 1;
            }
        }
    }

    private void processBackstagePass(Item item) {
        item.quality = increaseQuality(item);
        if (item.sellIn < 11) {
            item.quality = increaseQuality(item);
        }
        if (item.sellIn < 6) {
            item.quality = increaseQuality(item);
        }
        item.sellIn = decreaseSellIn(item);
        if (item.sellIn < 0) {
            item.quality = 0;
        }
    }

    private void processAgedBrie(Item item) {
        item.quality = increaseQuality(item);
        item.sellIn = decreaseSellIn(item);
        if (item.sellIn < 0) {
            item.quality = increaseQuality(item);
        }
    }

    private int increaseQuality(Item item) {
        if (item.quality < 50) {
            return item.quality + 1;
        }
        return item.quality;
    }

    private int decreaseSellIn(Item item) {
        return item.sellIn - 1;
    }
}