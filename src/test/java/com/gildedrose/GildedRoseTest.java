package com.gildedrose;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.assertEquals;

class GildedRoseTest {

    @Test
    void foo() {
        //GIVEN
        Item[] items = new Item[]{new Item("fixme", 0, 0)};
        GildedRose app = new GildedRose(items);

        //WHEN
        app.updateQuality();

        //THEN
        assertEquals("fixme", app.items[0].name);
    }

    @Test
    void shouldLowerSellInValue() {
        //GIVEN
        Item[] items = new Item[]{new Item("ReduceSellIn", 1, 2)};
        GildedRose app = new GildedRose(items);

        //WHEN
        app.updateQuality();

        //THEN
        assertEquals(0, app.items[0].sellIn);
    }

    @Test
    void shouldLowerQualityValue() {
        //GIVEN
        Item[] items = new Item[]{new Item("ReduceQuality", 1, 2)};
        GildedRose app = new GildedRose(items);

        //WHEN
        app.updateQuality();

        //THEN
        assertEquals(1, app.items[0].quality);
    }

    @Test
    void shouldLowerQualityValueByTwoWhenSellInHasPassed() {
        //GIVEN
        Item[] items = new Item[]{new Item("ReduceQualityBy2", 0, 2)};
        GildedRose app = new GildedRose(items);

        //WHEN
        app.updateQuality();

        //THEN
        assertEquals(0, app.items[0].quality);
    }

    @Test
    void shouldCheckQualityNotNegative() {
        //GIVEN
        Item[] items = new Item[]{new Item("NotNegativeQuality", 0, 0)};
        GildedRose app = new GildedRose(items);

        //WHEN
        app.updateQuality();

        //THEN
        assertEquals(0, app.items[0].quality);
    }

    @ParameterizedTest
    @ValueSource(strings = {"Aged Brie", "Backstage passes to a TAFKAL80ETC concert"})
    void shouldIncreaseQualityWhenItemIsAgedBrieOrBackstagePasses(String item) {
        //GIVEN
        Item[] items = new Item[]{new Item(item, 11, 4)};
        GildedRose app = new GildedRose(items);

        //WHEN
        app.updateQuality();

        //THEN
        assertEquals(5, app.items[0].quality);
    }

    //This is not said in the specifications but Aged Brie is increasing Quality by "two" when SellIn is <=0
    @Test
    void shouldIncreaseTwiceQualityWhenItemIsAgedBrieAndSellInEqualOrLessThan0() {
        //GIVEN
        Item agedBrie1 = new Item("Aged Brie", 0, 4);
        Item agedBrie2 = new Item("Aged Brie", -1, 6);
        Item[] items = new Item[]{agedBrie1, agedBrie2};
        GildedRose app = new GildedRose(items);

        //WHEN
        app.updateQuality();

        //THEN
        assertEquals(6, app.items[0].quality);
        assertEquals(8, app.items[1].quality);
    }

    @ParameterizedTest
    @ValueSource(strings = {"Aged Brie", "Backstage passes to a TAFKAL80ETC concert"})
    void shouldNotIncreaseQualityMoreThan50WhenItemIsAgedBrieOrBackstagePasses(String item) {
        //GIVEN
        Item[] items = new Item[]{new Item(item, 1, 50)};
        GildedRose app = new GildedRose(items);

        //WHEN
        app.updateQuality();

        //THEN
        assertEquals(50, app.items[0].quality);
    }

    @Test
    void shouldNotDecreaseSellInORQualityWhenItemIsSulfuras() {
        //GIVEN
        Item[] items = new Item[]{new Item("Sulfuras, Hand of Ragnaros", 5, 80)};
        GildedRose app = new GildedRose(items);

        //WHEN
        app.updateQuality();

        //THEN
        assertEquals(80, app.items[0].quality);
        assertEquals(5, app.items[0].sellIn);
    }

    @Test
    void shouldIncreaseQualityByTwoWhenItemIsBackstagePassesAndSellInEqualsOrLessThan10() {
        //GIVEN
        Item backstagePass1 = new Item("Backstage passes to a TAFKAL80ETC concert", 10, 30);
        Item backstagePass2 = new Item("Backstage passes to a TAFKAL80ETC concert", 9, 28);
        Item[] items = new Item[]{backstagePass1, backstagePass2};
        GildedRose app = new GildedRose(items);

        //WHEN
        app.updateQuality();

        //THEN
        assertEquals(32, app.items[0].quality);
        assertEquals(30, app.items[1].quality);
    }

    @Test
    void shouldIncreaseQualityByThreeWhenItemIsBackstagePassesAndSellInEqualsOrLessThan5() {
        //GIVEN
        Item backstagePass1 = new Item("Backstage passes to a TAFKAL80ETC concert", 5, 30);
        Item backstagePass2 = new Item("Backstage passes to a TAFKAL80ETC concert", 4, 28);
        Item[] items = new Item[]{backstagePass1, backstagePass2};
        GildedRose app = new GildedRose(items);

        //WHEN
        app.updateQuality();

        //THEN
        assertEquals(33, app.items[0].quality);
        assertEquals(31, app.items[1].quality);
    }

    @Test
    void shouldDecreaseQualityTo0WhenItemIsBackstagePassesAndSellInEqualsOrLessThan0() {
        //GIVEN
        Item backstagePass1 = new Item("Backstage passes to a TAFKAL80ETC concert", 0, 30);
        Item backstagePass2 = new Item("Backstage passes to a TAFKAL80ETC concert", -1, 28);
        Item[] items = new Item[]{backstagePass1, backstagePass2};
        GildedRose app = new GildedRose(items);

        //WHEN
        app.updateQuality();

        //THEN
        assertEquals(0, app.items[0].quality);
        assertEquals(0, app.items[1].quality);
    }
}
