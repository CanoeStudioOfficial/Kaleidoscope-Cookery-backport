package com.github.ysbbbbbb.kaleidoscopecookery.content.item;

import com.github.ysbbbbbb.kaleidoscopecookery.content.ItemFoodCreator;
import com.github.ysbbbbbb.kaleidoscopecookery.content.item.scarecrow.Scarecrow;
import net.minecraft.item.Item;
import net.minecraft.item.ItemFood;

import java.util.ArrayList;
import java.util.List;

public class ModItems {
    public static final List<Item> ITEMS = new ArrayList<>();

    public static final ItemFood Glow_Berries = new ItemFoodCreator("Glow_Berries", 4, 0.4f);

    public static final Item SCARECROW = new Scarecrow("Scarecrow");

    public static void init() {
    }
}
