package repositories;

import entities.Order;

import java.util.List;

public interface OrderRepo extends GenericRepo<Order> {
    List<Order> getOrderByClientId(int id);
}
