/*
 * This is my personal solution to the GS Collections Kata 6.0.0.
 * The original exercise was licensed under Apache License 2.0. 
 * You may find it here:
 * https://github.com/goldmansachs
 */

package com.gs.collections.kata;

import com.gs.collections.api.block.function.Function;
import com.gs.collections.api.list.MutableList;
import com.gs.collections.impl.list.mutable.FastList;
import com.gs.collections.impl.utility.ArrayIterate;

/**
 * Suppliers have a name and an array of itemNames. Suppliers don't like lists - they prefer arrays....
 */
public class Supplier
{
    public static final Function<Supplier, String> TO_NAME = Supplier::getName;
    
    public static final Function<Supplier, MutableList<String>> TO_ITEM_NAMES = supplier -> FastList.newListWith(supplier.getItemNames());

    public static final Function<Supplier, Integer> TO_NUMBER_OF_ITEMS = supplier -> supplier.getItemNames().length;

    private final String name;
    private final String[] itemNames;

    public Supplier(String name, String[] itemNames)
    {
        this.name = name;
        this.itemNames = itemNames;
    }

    public String getName()
    {
        return this.name;
    }

    public String[] getItemNames()
    {
        return this.itemNames;
    }
    
    public boolean supplies(String name)
    {
    	return ArrayIterate.contains(this.itemNames, name);
    }
}
