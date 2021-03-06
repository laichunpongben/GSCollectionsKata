/*
 * This is my personal solution to the GS Collections Kata 6.0.0.
 * The original exercise was licensed under Apache License 2.0. 
 * You may find it here:
 * https://github.com/goldmansachs
 */

package com.gs.collections.kata;

import com.gs.collections.api.RichIterable;
import com.gs.collections.api.block.procedure.Procedure;
import com.gs.collections.api.list.MutableList;
import com.gs.collections.impl.block.factory.Procedures;
import com.gs.collections.impl.list.mutable.FastList;
import com.gs.collections.impl.test.Verify;
import org.junit.Assert;
import org.junit.Test;

public class Exercise6Test extends CompanyDomainForKata
{
    /**
     * Get a list of the customers' total order values, sorted. Check out the implementation of {@link
     * Customer#getTotalOrderValue()} and {@link Order#getValue()} .
     */
    @Test
    public void sortedTotalOrderValue()
    {
    	MutableList<Double> sortedTotalValues = this.company.getCustomers().collect(x -> x.getTotalOrderValue()).toSortedList();

        // Don't forget the handy utility methods getFirst() and getLast()...
        Assert.assertEquals("Highest total order value", Double.valueOf(857.0), sortedTotalValues.getLast());
        Assert.assertEquals("Lowest total order value", Double.valueOf(71.0), sortedTotalValues.getFirst());
    }

    /**
     * Find the max total order value across all customers.
     */
    @Test
    public void maximumTotalOrderValue()
    {
        Double maximumTotalOrderValue = this.company.getCustomers().collect(x -> x.getTotalOrderValue()).max();
        Assert.assertEquals("max value", Double.valueOf(857.0), maximumTotalOrderValue);
    }

    /**
     * Find the customer with the highest total order value.
     */
    @Test
    public void customerWithMaxTotalOrderValue()
    {
        Customer customerWithMaxTotalOrderValue = this.company.getCustomers().maxBy(x -> x.getTotalOrderValue());
        Assert.assertEquals(this.company.getCustomerNamed("Mary"), customerWithMaxTotalOrderValue);
    }

    /**
     * Create some code to get the company's supplier names as a tilde delimited string.
     */
    @Test
    public void supplierNamesAsTildeDelimitedString()
    {
    	MutableList<Supplier> suppliers = FastList.newListWith(this.company.getSuppliers());
        String tildeSeparatedNames = suppliers.collect(x -> x.getName()).makeString("~");
        Assert.assertEquals(
                "tilde separated names",
                "Shedtastic~Splendid Crocks~Annoying Pets~Gnomes 'R' Us~Furniture Hamlet~SFD~Doxins",
                tildeSeparatedNames);
    }

    /**
     * Deliver all orders going to customers from London.
     * <p>
     * Hint: Use {@link RichIterable#forEach(Procedure)}. To solve the ambiguity error, use {@link Procedures#cast(Procedure)}}.
     *
     * @see Order#deliver()
     */
    @Test
    public void deliverOrdersToLondon()
    {
    	MutableList<Customer> customersFromLondon = this.company.getCustomers().select(x -> x.getCity().equals("London"));
    	MutableList<Order> ordersToDeliver = customersFromLondon.flatCollect(x -> x.getOrders());
    	Procedure<Order> deliver = x -> x.deliver();
    	ordersToDeliver.forEach(deliver);
    	
        Verify.assertAllSatisfy(this.company.getCustomerNamed("Fred").getOrders(), Order::isDelivered);
        Verify.assertNoneSatisfy(this.company.getCustomerNamed("Mary").getOrders(), Order::isDelivered);
        Verify.assertAllSatisfy(this.company.getCustomerNamed("Bill").getOrders(), Order::isDelivered);
    }
}
