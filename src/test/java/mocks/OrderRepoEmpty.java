package mocks;

import entities.Order;
import repositories.InMemoryGenericRepo;
import repositories.OrderRepo;

import java.util.ArrayList;
import java.util.List;

public class OrderRepoEmpty extends InMemoryGenericRepo<Order> implements OrderRepo {
    @Override
    public List<Order> getOrderByClientId(int clientId)
    {
        return new ArrayList<>();
    }
}
