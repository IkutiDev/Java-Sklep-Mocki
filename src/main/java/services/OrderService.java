package services;

import entities.Item;
import entities.Order;
import entities.OrderItem;
import repositories.ItemRepo;
import repositories.OrderItemRepo;
import repositories.OrderRepo;

import java.util.ArrayList;
import java.util.List;

public class OrderService {
    private ItemRepo itemRepo;
    private OrderItemRepo orderItemRepo;
    private OrderRepo orderRepo;
    public OrderService(ItemRepo itemRepo, OrderItemRepo orderItemRepo, OrderRepo orderRepo){
        this.itemRepo = itemRepo;
        this.orderItemRepo = orderItemRepo;
        this.orderRepo=orderRepo;
    }
    public List<Order> getAllOrders(){
        return orderRepo.getAll();
    }
    public boolean updateOrder(Order order){
        if(orderRepo.getById(order.getId())==null){
            return false;
        }
        return orderRepo.update(order);
    }
    public boolean createOrder(Order order){
        orderNotNull(order);
        return orderRepo.add(order);
    }
    public boolean deleteOrder(Order order){
        for (OrderItem o:orderItemRepo.getByOrderId(order.getId())
             ) {
            orderItemRepo.delete(o);
        }
        return orderRepo.delete(order);
    }
    public boolean addItemToOrder(Item item,Order order){
        orderNotNull(order);
        itemNotNull(item);
        OrderItem orderItem = new OrderItem(order.getId(),item.getId());
        return orderItemRepo.add(orderItem);
    }
    private List<Item> getItemsByOrder(Order order){
        orderNotNull(order);
        List <Item> items = new ArrayList<>();
        for (OrderItem o:orderItemRepo.getByOrderId(order.getId())
             ) {
            items.add(itemRepo.getById(o.getItemId()));
        }
        return items;
    }
    public boolean clearOrderItems(Order order){
        orderNotNull(order);
        List <OrderItem> orderItems = orderItemRepo.getByOrderId(order.getId());
        for (OrderItem o:orderItems
             ) {
            orderItemRepo.delete(o);
        }
        return true;
    }
    public double totalValueForOrder(Order order){
        orderNotNull(order);
        List <Item> items = getItemsByOrder(order);
        return items.stream().mapToDouble(Item::getValue).sum();
    }
    private void itemNotNull(Item item){
        if(item==null){
            throw new IllegalArgumentException("Item is null");
        }
    }
    private void orderNotNull(Order order){
        if(order==null){
            throw new IllegalArgumentException("Order is null");
        }
    }
}
