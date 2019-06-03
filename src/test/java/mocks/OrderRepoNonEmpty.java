package mocks;

import entities.Order;
import repositories.InMemoryGenericRepo;
import repositories.OrderRepo;

import java.util.Arrays;
import java.util.List;

public class OrderRepoNonEmpty extends InMemoryGenericRepo<Order> implements OrderRepo {
    @Override
    public List<Order> getOrderByClientId(int clientId)
    {
        return Arrays.asList(new Order(1,clientId),new Order(2,clientId));
    }
    public OrderRepoNonEmpty(){
        this.add(new Order(1,1));
        this.add(new Order(2,2));
    }
}
