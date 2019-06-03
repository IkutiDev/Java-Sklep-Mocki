package repositories;

import entities.OrderItem;

import java.util.List;

public interface OrderItemRepo extends GenericRepo<OrderItem> {
    List<OrderItem> getByOrderId(int id);
    List<OrderItem> getByItemId(int id);
}
