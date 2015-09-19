/*
 * This is my personal solution to the GS Collections Kata 6.0.0.
 * The original exercise was licensed under Apache License 2.0. 
 * You may find it here:
 * https://github.com/goldmansachs
 */

package com.gs.collections.kata;

import com.gs.collections.api.block.function.Function;
import com.gs.collections.api.list.MutableList;
import com.gs.collections.impl.block.function.AddFunction;
import com.gs.collections.impl.list.mutable.FastList;
import com.gs.collections.impl.utility.ListIterate;

/**
 * Customers have a name, city and a list of {@link Order}s
 */
public class Customer
{
    public static final Function<Customer, String> TO_NAME = Customer::getName;

    public static final Function<Customer, String> TO_CITY = Customer::getCity;

    public static final Function<Customer, Double> TO_TOTAL_ORDER_VALUE = Customer::getTotalOrderValue;

    private final String name;
    private final String city;

    private final MutableList<Order> orders = FastList.newList();

    public Customer(String name, String city)
    {
        this.name = name;
        this.city = city;
    }

    public String getCity()
    {
        return this.city;
    }

    public String getName()
    {
        return this.name;
    }

    public MutableList<Order> getOrders()
    {
        return this.orders;
    }

    public void addOrder(Order anOrder)
    {
        this.orders.add(anOrder);
    }

    public double getTotalOrderValue()
    {
        MutableList<Double> orderValues = ListIterate.collect(this.orders, Order::getValue);
        return orderValues.injectInto(0.0, AddFunction.DOUBLE_TO_DOUBLE);
    }
    
    public double getMostExpensiveItemValue()
    {
    	MutableList<LineItem> items = this.orders.flatCollect(x -> x.getLineItems());
    	return items.collect(x -> x.getValue()).max();
    }
}
