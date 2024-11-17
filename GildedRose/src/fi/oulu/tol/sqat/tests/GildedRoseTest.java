package fi.oulu.tol.sqat.tests;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Before;
import org.junit.Test;

import fi.oulu.tol.sqat.GildedRose;
import fi.oulu.tol.sqat.Item;

public class GildedRoseTest {
	
	GildedRose inn;
	
	@Before
	public void setUp() {
		inn = new GildedRose();
	}

	@Test
	public void testTheTruth() {
		assertTrue(true);
	}
	
	@Test
	public void test_getItems_when_empty() {
		assertTrue(inn.getItems().isEmpty());
	}
	
	@Test
	public void test_set_items() {
		Item item = new Item("+5 Dexterity Vest", 10, 20);
		inn.setItem(item);
		assertEquals(item, inn.getItems().get(0));
	}
	
	@Test
	public void test_TAFKAL80ETC_quality_less_than_50_sellin_less_than_6() {
		List<Item> items = inn.getItems();
		Item item = new Item("Backstage passes to a TAFKAL80ETC concert", 2, 4);
		items.add(item);
		inn.oneDay();
		assertEquals(7,item.getQuality());
	}
	
	@Test
	public void test_TAFKAL80ETC_quality_less_than_50_sellin_between_6_and_11() {
		List<Item> items = inn.getItems();
		Item item = new Item("Backstage passes to a TAFKAL80ETC concert", 7, 4);
		items.add(item);
		inn.oneDay();
		assertEquals(6,item.getQuality());
	}
	
	@Test
	public void test_TAFKAL80ETC_quality_less_than_50_sellin_greater_than_10() {
		List<Item> items = inn.getItems();
		Item item = new Item("Backstage passes to a TAFKAL80ETC concert", 11, 4);
		items.add(item);
		inn.oneDay();
		assertEquals(5,item.getQuality());
	}
	
	@Test
	public void test_TAFKAL80ETC_outdated() {
		List<Item> items = inn.getItems();
		Item item = new Item("Backstage passes to a TAFKAL80ETC concert", 0, 4);
		items.add(item);
		inn.oneDay();
		assertEquals(0,item.getQuality());
	}
	
	@Test
	public void test_aged_brie_quality_greater_or_equal_to_50() {
		List<Item> items = inn.getItems();
		Item item = new Item("Aged Brie", 2, 50);
		items.add(item);
		inn.oneDay();
		assertEquals(50,item.getQuality());
	}
	
	@Test
	public void test_aged_brie_quality_less_than_50_sellin_less_than_6() {
		List<Item> items = inn.getItems();
		Item item = new Item("Aged Brie", 2, 4);
		items.add(item);
		inn.oneDay();
		assertEquals(7,item.getQuality());
	}
	
	@Test
	public void test_aged_brie_quality_less_than_50_sellin_between_6_and_11() {
		List<Item> items = inn.getItems();
		Item item = new Item("Aged Brie", 7, 4);
		items.add(item);
		inn.oneDay();
		assertEquals(6,item.getQuality());
	}
	
	@Test
	public void test_aged_brie_quality_less_than_50_sellin_greater_than_10() {
		List<Item> items = inn.getItems();
		Item item = new Item("Aged Brie", 11, 4);
		items.add(item);
		inn.oneDay();
		assertEquals(5,item.getQuality());
	}
	
	@Test
	public void test_aged_brie_outdated() {
		List<Item> items = inn.getItems();
		Item item = new Item("Aged Brie", 0, 4);
		items.add(item);
		inn.oneDay();
		assertEquals(0,item.getQuality());
	}
	
	@Test
	public void test_sulfuras_quality() {
		List<Item> items = inn.getItems();
		Item item = new Item("Sulfuras, Hand of Ragnaros", 0, 80);
		items.add(item);
		inn.oneDay();
		assertEquals(80,item.getQuality());
	}
	
	@Test
	public void test_sulfuras_sellin() {
		List<Item> items = inn.getItems();
		Item item = new Item("Sulfuras, Hand of Ragnaros", 0, 80);
		items.add(item);
		inn.oneDay();
		assertEquals(0, item.getSellIn());
	}
	
	
	@Test
	public void test_common_item() {
		List<Item> items = inn.getItems();
		Item item = new Item("+5 Dexterity Vest", 10, 20);
		items.add(item);
		inn.oneDay();
		assertEquals(19, item.getQuality());
		assertEquals(9, item.getSellIn());
	}
	
	@Test
	public void test_common_item_sellin_past() {
		List<Item> items = inn.getItems();
		Item item = new Item("+5 Dexterity Vest", 0, 20);
		items.add(item);
		inn.oneDay();
		inn.oneDay();
		assertEquals(16, item.getQuality());
		assertEquals(-2, item.getSellIn());
	}
	
	@Test
    public void test_two_items() {
        inn.setItem(new Item("Aged Brie", 2, 4));
        inn.setItem(new Item("Elixir of the Mongoose", 5, 7));
        
        inn.oneDay();

        List<Item> items = inn.getItems();
        assertEquals(1, items.get(0).getSellIn());
        assertEquals(7, items.get(0).getQuality());
        assertEquals(4, items.get(1).getSellIn());
        assertEquals(6, items.get(1).getQuality());
    }

    @Test
    public void test_multiple_items() {
        inn.setItem(new Item("+5 Dexterity Vest", 10, 20));
        inn.setItem(new Item("Aged Brie", 2, 4));
        inn.setItem(new Item("Sulfuras, Hand of Ragnaros", 0, 80));
        inn.oneDay();

        List<Item> items = inn.getItems();
        assertEquals(9, items.get(0).getSellIn());
        assertEquals(19, items.get(0).getQuality());
        assertEquals(1, items.get(1).getSellIn());
        assertEquals(7, items.get(1).getQuality());
        assertEquals(0, items.get(2).getSellIn());
        assertEquals(80, items.get(2).getQuality());
    }

    @Test
    public void test_more_multiple_items() {
        for (int i = 0; i < 100; i++) {
            inn.setItem(new Item("+5 Dexterity Vest", 10, 20));
        }
        inn.oneDay();
        
        for (Item item : inn.getItems()) {
            assertEquals(9, item.getSellIn());
            assertEquals(19, item.getQuality());
        }
    }
    
    @Test
    public void test_common_item_quality_never_negative() {
        List<Item> items = inn.getItems();
        Item item = new Item("+5 Dexterity Vest", 10, 0);
        items.add(item);
        inn.oneDay();
        assertEquals(0, item.getQuality());
        assertEquals(9, item.getSellIn());
    }
   

    @Test
    public void test_backstage_passes_quality_changes_exact_boundaries() {
        List<Item> items = inn.getItems();
        Item pass11 = new Item("Backstage passes to a TAFKAL80ETC concert", 11, 20);
        Item pass10 = new Item("Backstage passes to a TAFKAL80ETC concert", 10, 20);
        Item pass6 = new Item("Backstage passes to a TAFKAL80ETC concert", 6, 20);
        Item pass5 = new Item("Backstage passes to a TAFKAL80ETC concert", 5, 20);
        
        items.add(pass11);
        items.add(pass10);
        items.add(pass6);
        items.add(pass5);
        
        inn.oneDay();
        
        assertEquals(21, pass11.getQuality());
        assertEquals(22, pass10.getQuality());
        assertEquals(22, pass6.getQuality());
        assertEquals(23, pass5.getQuality());
    }

    
}
