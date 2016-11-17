package com.example.artofandroiddev.base;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by ouyangym on 2016/11/11.
 */
public class DummyContent {
    /**
     * An array of sample (dummy) items.
     */
    public static final List<String> ITEMS = new ArrayList<String>();

    public static final List<Map<String, String>> ITEM_MAP = new ArrayList<>();

    static {
        addItem();
    }

    private static void addItem() {
        for (int i = 0; i < 20; i++) {
            ITEMS.add("item string " + i);

            Map<String,String> map = new HashMap();
            map.put("KEY","map_item " + i);
            ITEM_MAP.add(map);
        }
    }
}
