/*
 * This is my personal solution to the GS Collections Kata 6.0.0.
 * The original exercise was licensed under Apache License 2.0. 
 * You may find it here:
 * https://github.com/goldmansachs
 */

package com.gs.collections.kata;

import com.gs.collections.api.list.MutableList;
import com.gs.collections.impl.list.mutable.FastList;
import com.gs.collections.impl.test.Verify;
import org.junit.Assert;
import org.junit.Test;

public class Exercise1Test extends CompanyDomainForKata
{
    @Test
    public void getCustomerNames()
    {
        /**
         * Get the name of each of the company's customers.
         */
        MutableList<Customer> customers = this.company.getCustomers();
        MutableList<String> customerNames = customers.collect(x -> x.getName());

        MutableList<String> expectedNames = FastList.newListWith("Fred", "Mary", "Bill");
        Assert.assertEquals(expectedNames, customerNames);
    }

    @Test
    public void getCustomerCities()
    {
        /**
         * Get the city for each of the company's customers.
         */
        MutableList<Customer> customers = this.company.getCustomers();
        MutableList<String> customerCities = customers.collect(x -> x.getCity());

        MutableList<String> expectedCities = FastList.newListWith("London", "Liphook", "London");
        Assert.assertEquals(expectedCities, customerCities);
    }

    @Test
    public void getLondonCustomers()
    {
        /**
         * Which customers come from London? Get a collection of those which do.
         */
        MutableList<Customer> customers = this.company.getCustomers();
        MutableList<Customer> customersFromLondon = customers.select(x -> x.getCity() == "London");
        Verify.assertSize("Should be 2 London customers", 2, customersFromLondon);
    }
}
