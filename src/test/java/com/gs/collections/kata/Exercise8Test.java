/*
 * This is my personal solution to the GS Collections Kata 6.0.0.
 * The original exercise was licensed under Apache License 2.0. 
 * You may find it here:
 * https://github.com/goldmansachs
 */

package com.gs.collections.kata;

import java.util.Collections;

import com.gs.collections.api.RichIterable;
import com.gs.collections.api.bag.sorted.MutableSortedBag;
import com.gs.collections.api.block.function.Function;
import com.gs.collections.api.block.function.Function0;
import com.gs.collections.api.block.function.Function2;
import com.gs.collections.api.block.predicate.Predicate;
import com.gs.collections.api.list.MutableList;
import com.gs.collections.api.map.MutableMap;
import com.gs.collections.api.multimap.list.MutableListMultimap;
import com.gs.collections.impl.bag.sorted.mutable.TreeBag;
import com.gs.collections.impl.block.factory.Predicates;
import com.gs.collections.impl.list.mutable.FastList;
import com.gs.collections.impl.test.Verify;
import org.junit.Assert;
import org.junit.Test;

public class Exercise8Test extends CompanyDomainForKata
{
    /**
     * Extra credit. Aggregate the total order values by city.
     *
     * @see RichIterable#aggregateBy(Function, Function0, Function2)
     */
    @Test
    public void totalOrderValuesByCity()
    {
        Function0<Double> zeroValueFactory = () -> 0.0;
        Function2<Double, Customer, Double> aggregator = (result, customer) -> result + customer.getTotalOrderValue();

        MutableMap<String, Double> map = this.company.getCustomers().aggregateBy(Customer::getCity, zeroValueFactory, aggregator);
        Assert.assertEquals(2, map.size());
        Assert.assertEquals(446.25, map.get("London"), 0.0);
        Assert.assertEquals(857.0, map.get("Liphook"), 0.0);
    }

    /**
     * Extra credit. Aggregate the total order values by item.
     * Hint: Look at {@link RichIterable#aggregateBy(Function, Function0, Function2)} and remember
     * how to use {@link RichIterable#flatCollect(Function)} to get an iterable of all items.
     */
    @Test
    public void totalOrderValuesByItem()
    {
        Function0<Double> zeroValueFactory = () -> 0.0;
        Function2<Double, LineItem, Double> aggregator = (result, lineItem) -> result + lineItem.getValue();
        
        MutableList<LineItem> lineItems = this.company.getOrders().flatCollect(Order::getLineItems);
        MutableMap<String, Double> map = lineItems.aggregateBy(LineItem::getName, zeroValueFactory, aggregator);
        Verify.assertSize(12, map);
        Assert.assertEquals(100.0, map.get("shed"), 0.0);
        Assert.assertEquals(10.5, map.get("cup"), 0.0);
    }

    /**
     * Extra credit. Find all customers' line item values greater than 7.5 and sort them by highest to lowest price.
     */
    @Test
    public void sortedOrders()
    {
    	MutableList<LineItem> lineItems = this.company.getOrders().flatCollect(Order::getLineItems);
    	MutableList<Double> lineItemValues = lineItems.collect(x -> x.getValue()).select(y -> y > 7.5);
        MutableSortedBag<Double> orderedPrices = lineItemValues.toSortedBag(Collections.reverseOrder());

        MutableSortedBag<Double> expectedPrices = TreeBag.newBagWith(
                Collections.reverseOrder(), 500.0, 150.0, 120.0, 75.0, 50.0, 50.0, 12.5);
        Verify.assertSortedBagsEqual(expectedPrices, orderedPrices);
    }

    /**
     * Extra credit. Figure out which customers ordered saucers (in any of their orders).
     */
    @Test
    public void whoOrderedSaucers()
    {
    	Predicate<LineItem> lineItemHasSaucers = x -> x.getName() == "saucer";
    	Predicate<Order> orderHasSaucers = Predicates.attributeAnySatisfy(Order::getLineItems, lineItemHasSaucers);
    	Predicate<Customer> customerHasSaucers = Predicates.attributeAnySatisfy(Customer::getOrders, orderHasSaucers);
    	
        MutableList<Customer> customersWithSaucers = this.company.getCustomers().select(customerHasSaucers);
        Verify.assertSize("customers with saucers", 2, customersWithSaucers);
    }

    /**
     * Extra credit. Look into the {@link MutableList#toMap(Function, Function)} method.
     */
    @Test
    public void ordersByCustomerUsingAsMap()
    {
        MutableMap<String, MutableList<Order>> customerNameToOrders =
                this.company.getCustomers().toMap(Customer::getName, Customer::getOrders);

        Assert.assertNotNull("customer name to orders", customerNameToOrders);
        Verify.assertSize("customer names", 3, customerNameToOrders);
        MutableList<Order> ordersForBill = customerNameToOrders.get("Bill");
        Verify.assertSize("Bill orders", 3, ordersForBill);
    }

    /**
     * Extra credit. Create a multimap where the values are customers and the key is the price of
     * the most expensive item that the customer ordered.
     */
    @Test
    public void mostExpensiveItem()
    {   	
        MutableListMultimap<Double, Customer> multimap = this.company.getCustomers().groupBy(Customer::getMostExpensiveItemValue);
        Assert.assertEquals(3, multimap.size());
        Assert.assertEquals(2, multimap.keysView().size());
        Assert.assertEquals(
                FastList.newListWith(
                        this.company.getCustomerNamed("Fred"),
                        this.company.getCustomerNamed("Bill")),
                multimap.get(50.0));
    }
}
