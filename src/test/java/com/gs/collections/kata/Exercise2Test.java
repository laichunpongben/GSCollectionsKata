/*
 * This is my personal solution to the GS Collections Kata 6.0.0.
 * The original exercise was licensed under Apache License 2.0. 
 * You may find it here:
 * https://github.com/goldmansachs
 */

package com.gs.collections.kata;

import com.gs.collections.api.block.predicate.Predicate;
import com.gs.collections.api.list.MutableList;
import com.gs.collections.impl.block.factory.Predicates;
import com.gs.collections.impl.test.Verify;
import org.junit.Assert;
import org.junit.Test;

public class Exercise2Test extends CompanyDomainForKata
{
    /**
     * Set up a {@link Predicate} that tests to see if a {@link Customer}'s city is "London"
     */
    private static final Predicate<Customer> CUSTOMER_FROM_LONDON = Predicates.attributeEqual(Customer::getCity, "London");

    @Test
    public void customerFromLondonPredicate()
    {
        String predicateClass = CUSTOMER_FROM_LONDON.getClass().getSimpleName();
        Assert.assertTrue(
                "Solution should use Predicates.attributeEquals() or a lambda but used " + predicateClass,
                "AttributePredicate".equals(predicateClass) || predicateClass.startsWith("Exercise2Test$$Lambda"));

        Customer customerFromLondon = new Customer("test customer", "London");
        
        Assert.assertEquals(
                "Implement Customer.TO_CITY",
                "London",
                Customer.TO_CITY.valueOf(customerFromLondon));

        Assert.assertTrue(
                "CUSTOMER_FROM_LONDON should accept Customers where city is London",
                CUSTOMER_FROM_LONDON.accept(customerFromLondon));
    }

    /**
     * Do any customers come from London? Use the Predicate {@link #CUSTOMER_FROM_LONDON}.
     */
    @Test
    public void doAnyCustomersLiveInLondon()
    {
    	Assert.assertTrue(this.company.getCustomers().anySatisfy(CUSTOMER_FROM_LONDON));
    }

    /**
     * Do all customers come from London? Use the Predicate {@link #CUSTOMER_FROM_LONDON}.
     */
    @Test
    public void doAllCustomersLiveInLondon()
    {
    	Assert.assertFalse(this.company.getCustomers().allSatisfy(CUSTOMER_FROM_LONDON));
    }

    /**
     * How many customers come from London? Use the Predicate {@link #CUSTOMER_FROM_LONDON}.
     */
    @Test
    public void howManyCustomersLiveInLondon()
    {
        int numberOfCustomerFromLondon = this.company.getCustomers().count(CUSTOMER_FROM_LONDON);
        Assert.assertEquals("Should be 2 London customers", 2, numberOfCustomerFromLondon);
    }

    /**
     * Which customers come from London? Get a collection of those which do. Use the Predicate {@link
     * #CUSTOMER_FROM_LONDON}.
     */
    @Test
    public void getLondonCustomers()
    {
        MutableList<Customer> customersFromLondon = this.company.getCustomers().select(CUSTOMER_FROM_LONDON);
        Verify.assertSize("Should be 2 London customers", 2, customersFromLondon);
    }

    /**
     * Which customers do not come from London? Get a collection of those which don't. Use the Predicate {@link
     * #CUSTOMER_FROM_LONDON}.
     */
    @Test
    public void getCustomersWhoDontLiveInLondon()
    {
        MutableList<Customer> customersNotFromLondon = this.company.getCustomers().reject(CUSTOMER_FROM_LONDON);
        Verify.assertSize("customers not from London", 1, customersNotFromLondon);
    }

    /**
     * Which customers come from London? Which customers do not come from London? Get a collection of each in a single pass.
     * Use the Predicate {@link #CUSTOMER_FROM_LONDON}.
     */
    @Test
    public void getCustomersWhoDoAndDoNotLiveInLondon()
    {
    	MutableList<Customer> customers = this.company.getCustomers();
        MutableList<Customer> customersFromLondon = customers.select(CUSTOMER_FROM_LONDON);
        MutableList<Customer> customersNotFromLondon = customers.reject(CUSTOMER_FROM_LONDON);
        Verify.assertSize("Should be 2 London customers", 2, customersFromLondon);
        Verify.assertSize("customers not from London", 1, customersNotFromLondon);
    }

    /**
     * Implement {@link Company#getCustomerNamed(String)} and get this test to pass.
     */
    @Test
    public void findMary()
    {
        Customer mary = this.company.getCustomerNamed("Mary");
        Assert.assertEquals("customer's name should be Mary", "Mary", mary.getName());
    }

    /**
     * Implement {@link Company#getCustomerNamed(String)} and get this test to pass.
     */
    @Test
    public void findPete()
    {
        Customer pete = this.company.getCustomerNamed("Pete");
        Assert.assertNull(
                "Should be null as there is no customer called Pete",
                pete);
    }
}
