package mocks;

import entities.OrderItem;
import repositories.InMemoryGenericRepo;
import repositories.OrderItemRepo;

import java.util.Arrays;
import java.util.List;

public class OrderItemRepoNonEmpty extends InMemoryGenericRepo<OrderItem> implements OrderItemRepo {
    @Override
    public List<OrderItem> getByOrderId(int id){
        return Arrays.asList(new OrderItem(1,id),new OrderItem(2,id));
    }
    @Override
    public List<OrderItem> getByItemId(int id){
        return Arrays.asList(new OrderItem(id,1),new OrderItem(id,2));
    }
}
