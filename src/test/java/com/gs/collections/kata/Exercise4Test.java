/*
 * This is my personal solution to the GS Collections Kata 6.0.0.
 * The original exercise was licensed under Apache License 2.0. 
 * You may find it here:
 * https://github.com/goldmansachs
 */

package com.gs.collections.kata;

import java.util.List;

import org.junit.Assert;
import org.junit.Test;

import com.gs.collections.api.block.predicate.Predicate;
import com.gs.collections.api.list.MutableList;
import com.gs.collections.impl.block.factory.Predicates;
import com.gs.collections.impl.list.mutable.FastList;
import com.gs.collections.impl.utility.ArrayIterate;
import com.gs.collections.impl.utility.Iterate;
import com.gs.collections.impl.utility.ListIterate;

public class Exercise4Test extends CompanyDomainForKata
{
    /**
     * Solve this without changing the return type of {@link Company#getSuppliers()}. Find the appropriate method on
     * {@link ArrayIterate}.
     */
    @Test
    public void findSupplierNames()
    {
        MutableList<String> supplierNames = ArrayIterate.collect(this.company.getSuppliers(), x -> x.getName());

        MutableList<String> expectedSupplierNames = FastList.newListWith(
                "Shedtastic",
                "Splendid Crocks",
                "Annoying Pets",
                "Gnomes 'R' Us",
                "Furniture Hamlet",
                "SFD",
                "Doxins");
        Assert.assertEquals(expectedSupplierNames, supplierNames);
    }

    /**
     * Create a {@link Predicate} for Suppliers that supply more than 2 items. Find the number of suppliers that
     * satisfy that Predicate.
     */
    @Test
    public void countSuppliersWithMoreThanTwoItems()
    {
        Predicate<Supplier> moreThanTwoItems = Predicates.attributeGreaterThan(Supplier.TO_NUMBER_OF_ITEMS, 2);
        int suppliersWithMoreThanTwoItems = ArrayIterate.count(this.company.getSuppliers(), moreThanTwoItems);
        Assert.assertEquals("suppliers with more than 2 items", 5, suppliersWithMoreThanTwoItems);
    }

    /**
     * Try to solve this without changing the return type of {@link Supplier#getItemNames()}.
     */
    @Test
    public void whoSuppliesSandwichToaster()
    {
        // Create a Predicate that will check to see if a Supplier supplies a "sandwich toaster".
    	Supplier[] suppliers = this.company.getSuppliers();
    	Predicate<Supplier> SUPPLIES_TOASTER = Predicates.attributeAnySatisfy(Supplier.TO_ITEM_NAMES, Predicates.equal("sandwich toaster"));
    	//Predicate<Supplier> SUPPLIES_TOASTER = supplier -> supplier.supplies("sandwich toaster");
    	
        // Find one supplier that supplies toasters.	
        Supplier toasterSupplier = ArrayIterate.detect(suppliers, SUPPLIES_TOASTER);
        Assert.assertNotNull("toaster supplier", toasterSupplier);
        Assert.assertEquals("Doxins", toasterSupplier.getName());
    }

    @Test
    public void filterOrderValues()
    {
        List<Order> orders = this.company.getMostRecentCustomer().getOrders();
        /**
         * Get the order values that are greater than 1.5.
         */
        MutableList<Double> orderValues = ListIterate.collect(orders, Order.TO_VALUE);
        MutableList<Double> filtered = orderValues.select(x -> x > 1.5);
        Assert.assertEquals(FastList.newListWith(372.5, 1.75), filtered);
    }

    @Test
    public void filterOrders()
    {
        List<Order> orders = this.company.getMostRecentCustomer().getOrders();
        /**
         * Get the actual orders (not their double values) where those orders have a value greater than 2.0.
         */
        Predicate<Order> ORDER_VALUE_GREATER_THAN_2 = Predicates.attributeGreaterThan(Order.TO_VALUE, 2.0);
        MutableList<Order> filtered = ListIterate.select(orders, ORDER_VALUE_GREATER_THAN_2);
        Assert.assertEquals(FastList.newListWith(Iterate.getFirst(this.company.getMostRecentCustomer().getOrders())), filtered);
    }
}
