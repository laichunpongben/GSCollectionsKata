/*
 * This is my personal solution to the GS Collections Kata 6.0.0.
 * The original exercise was licensed under Apache License 2.0. 
 * You may find it here:
 * https://github.com/goldmansachs
 */

package com.gs.collections.kata;

import java.util.Collection;
import java.util.List;

import com.gs.collections.api.block.function.Function;
import com.gs.collections.api.list.MutableList;
import com.gs.collections.impl.block.function.AddFunction;
import com.gs.collections.impl.collection.mutable.CollectionAdapter;
import com.gs.collections.impl.list.mutable.FastList;
import com.gs.collections.impl.utility.Iterate;

/**
 * Has a number, a {@link Customer}, a {@link List} of {@link LineItem}s, and a boolean that states whether or not the order
 * has been delivered. There is a class variable that contains the next order number.
 */
public class Order
{
    public static final Function<Order, Double> TO_VALUE = Order::getValue;

    public static final Function<Order, Iterable<LineItem>> TO_LINE_ITEMS = Order::getLineItems;

    private static int nextOrderNumber = 1;

    private final int orderNumber;
    private final MutableList<LineItem> lineItems = FastList.newList();
    private boolean isDelivered;

    public Order()
    {
        this.orderNumber = nextOrderNumber;
        nextOrderNumber += 1;
    }

    public static void resetNextOrderNumber()
    {
        nextOrderNumber = 1;
    }

    public void deliver()
    {
        this.isDelivered = true;
    }

    public boolean isDelivered()
    {
        return this.isDelivered;
    }

    public void addLineItem(LineItem aLineItem)
    {
        this.lineItems.add(aLineItem);
    }
    
    public void addLineItem(LineItem aLineItem, int count)
    {
        for (int i = 0; i < count; i++)
    		this.lineItems.add(aLineItem);
    }

    public List<LineItem> getLineItems()
    {
        return this.lineItems;
    }

    @Override
    public String toString()
    {
        return "order " + this.orderNumber + " items: " + this.lineItems.size();
    }

    public double getValue()
    {
        Collection<Double> itemValues = Iterate.collect(this.lineItems, LineItem::getValue);

        return CollectionAdapter.adapt(itemValues).injectInto(0.0, AddFunction.DOUBLE_TO_DOUBLE);
    }
    
}
