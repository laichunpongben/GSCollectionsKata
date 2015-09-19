/*
 * This is my personal solution to the GS Collections Kata 6.0.0.
 * The original exercise was licensed under Apache License 2.0. 
 * You may find it here:
 * https://github.com/goldmansachs
 */

package com.gs.collections.kata;

import org.junit.Assert;
import org.junit.Test;

import com.gs.collections.api.list.MutableList;
import com.gs.collections.api.multimap.list.MutableListMultimap;
import com.gs.collections.impl.list.mutable.FastList;
import com.gs.collections.impl.test.Verify;

public class Exercise7Test extends CompanyDomainForKata
{
    /**
     * Create a multimap where the keys are the names of cities and the values are the customers from those cities.
     */
    @Test
    public void customersByCity()
    {
        // Notice that the second generic type is Customer, not List<Customer>
        MutableListMultimap<String, Customer> multimap = this.company.getCustomers().groupBy(Customer::getCity);

        Assert.assertEquals(FastList.newListWith(this.company.getCustomerNamed("Mary")), multimap.get("Liphook"));
        Assert.assertEquals(
                FastList.newListWith(
                        this.company.getCustomerNamed("Fred"),
                        this.company.getCustomerNamed("Bill")),
                multimap.get("London"));
    }

    @Test
    public void mapOfItemsToSuppliers()
    {
        /**
         * Change itemsToSuppliers to a MutableMultimap<String, Supplier>
         */
        MutableList<Supplier> suppliers = FastList.newListWith(this.company.getSuppliers());
        MutableListMultimap<String, Supplier> itemsToSuppliers = suppliers.groupByEach(Supplier.TO_ITEM_NAMES);
        Verify.assertIterableSize("should be 2 suppliers for sofa", 2, itemsToSuppliers.get("sofa"));
    }

}
