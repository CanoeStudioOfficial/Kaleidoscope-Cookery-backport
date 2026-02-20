package com.github.ysbbbbbb.kaleidoscopecookery.content.item;

import com.github.ysbbbbbb.kaleidoscopecookery.KaleidoscopeCookery;
import net.minecraft.item.Item;

import java.util.ArrayList;
import java.util.List;

public class ModItems {
    public static final List<Item> ITEMS = new ArrayList<>();
    
    public static ScarecrowItem SCARECROW;
    
    public static void init() {
        SCARECROW = register(new ScarecrowItem());
    }
    
    private static <T extends Item> T register(T item) {
        ITEMS.add(item);
        return item;
    }
}
