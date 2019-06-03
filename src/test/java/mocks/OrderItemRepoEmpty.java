package mocks;

import entities.OrderItem;
import repositories.InMemoryGenericRepo;
import repositories.OrderItemRepo;

import java.util.ArrayList;
import java.util.List;

public class OrderItemRepoEmpty extends InMemoryGenericRepo<OrderItem> implements OrderItemRepo {
    @Override
    public List<OrderItem> getByOrderId(int id){
        return new ArrayList<>();
    }
    @Override
    public List<OrderItem> getByItemId(int id){
        return new ArrayList<>();
    }
}
