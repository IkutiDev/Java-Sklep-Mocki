package services;

import entities.Item;
import entities.Order;
import entities.OrderItem;
import repositories.ItemRepo;
import repositories.OrderItemRepo;
import repositories.OrderRepo;
import validators.ItemValidation;

import java.util.ArrayList;
import java.util.List;

public class ItemService {
    private ItemValidation itemValidation;
    private ItemRepo itemRepo;
    private OrderItemRepo orderItemRepo;
    private OrderRepo orderRepo;
    public ItemService(ItemValidation itemValidation, ItemRepo itemRepo, OrderItemRepo orderItemRepo, OrderRepo orderRepo){
        this.itemValidation = itemValidation;
        this.itemRepo = itemRepo;
        this.orderItemRepo = orderItemRepo;
        this.orderRepo=orderRepo;
    }
    public List<Item> getAllItems(){
        return itemRepo.getAll();
    }
    public boolean createItem(Item item){
        itemNotNull(item);
        if(itemValidation.isValid(item)){
            return itemRepo.add(item);
        }
        return false;
    }
    public boolean deleteItem(Item item){
        itemNotNull(item);
        return itemRepo.delete(item);
    }
    public boolean updateItem(Item item){
        itemNotNull(item);
        if(itemValidation.isValid(item) && itemRepo.getById(item.getId())!=null){
            return itemRepo.update(item);
        }
        return false;
    }
    public String printItem(Item item){
        itemNotNull(item);
        return "Name: "+item.getName()+"\n"+"Value: "+item.getValue()+"\n";
    }
    public List<Order> getOrdersByItem(Item item){
        itemNotNull(item);
        List<Order> orders = new ArrayList<>();
        for (OrderItem o:orderItemRepo.getByItemId(item.getId())
             ) {
            orders.add(orderRepo.getById(o.getOrderId()));
        }
        if(orders.size()==0){
            orders=null;
        }
        return orders;
    }
    private void itemNotNull(Item item){
        if(item==null){
            throw new IllegalArgumentException("Item is null");
        }
    }

}
