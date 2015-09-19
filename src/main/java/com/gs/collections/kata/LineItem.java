/*
 * This is my personal solution to the GS Collections Kata 6.0.0.
 * The original exercise was licensed under Apache License 2.0. 
 * You may find it here:
 * https://github.com/goldmansachs
 */

package com.gs.collections.kata;

import com.gs.collections.api.block.function.Function;

/**
 * An Item has a name and a value.
 */
public class LineItem
{
    public static final Function<LineItem, String> TO_NAME = LineItem::getName;

    private String name;
    private final double value;

    public String getName()
    {
        return this.name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public double getValue()
    {
        return this.value;
    }

    public LineItem(String name, double value)
    {
        this.name = name;
        this.value = value;
    }

    @Override
    public String toString()
    {
        return this.name + " $ " + this.getValue();
    }
}
