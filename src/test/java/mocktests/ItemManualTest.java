package mocktests;

import entities.Item;
import entities.Order;
import mocks.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import repositories.ItemRepo;
import repositories.OrderItemRepo;
import repositories.OrderRepo;
import services.ItemService;
import validators.ItemValidation;

import java.util.ArrayList;

class ItemManualTest {
    private ItemService itemService;
    private ItemRepo itemRepo;
    private ItemValidation itemValidation;
    private OrderRepo orderRepo;
    private OrderItemRepo orderItemRepo;
    private Item testItem;
    @BeforeEach
    void setup(){
        itemRepo = new ItemRepoInMemoryMock();
        itemValidation= new ItemValidatorTrue();
        orderRepo = new OrderRepoEmpty();
        orderItemRepo = new OrderItemRepoEmpty();
        itemService=new ItemService(itemValidation,itemRepo,orderItemRepo,orderRepo);
        testItem=new Item(1,"Pencil",10.5);
    }
    @AfterEach
    void teardown(){
        itemService=null;
        itemRepo=null;
        itemValidation=null;
        orderRepo=null;
        orderItemRepo=null;
        testItem=null;
    }
    @Test
    void addNullItem(){
        Assertions.assertThrows(IllegalArgumentException.class,()->  itemService.createItem(null));
    }
    @Test
    void addItem(){
        itemService.createItem(testItem);
        Assertions.assertEquals("Pencil",itemRepo.getById(1).getName());
    }
    @Test
    void addItemFailValidation(){
        itemService = new ItemService(new ItemValidatorFalse(),new ItemRepoInMemoryMock(), new OrderItemRepoEmpty(), new OrderRepoEmpty());
        Assertions.assertFalse(itemService.createItem(testItem));
    }
    @Test
    void getAllItems(){
        ArrayList<Item> items = new ArrayList<>();
        items.add(new Item(1,"Pen",12.4));
        items.add(new Item(2,"Notepad",20.8));
        items.add(new Item(3,"Ruler",12));
        items.add(new Item(4,"Pencil Case",11.3));
        for (Item item:items
             ) {
            itemService.createItem(item);
        }
        Assertions.assertArrayEquals(items.toArray(),itemService.getAllItems().toArray());
    }
    @Test
    void printNullItem(){
        Assertions.assertThrows(IllegalArgumentException.class,()->  itemService.printItem(null));
    }
    @Test
    void printItem(){
        Assertions.assertEquals("Name: Pencil\nValue: 10.5\n",itemService.printItem(testItem));
    }
    @Test
    void updateNullItem(){
        Assertions.assertThrows(IllegalArgumentException.class,()->  itemService.updateItem(null));
    }
    @Test
    void updateItemFailValidation(){
        itemService = new ItemService(new ItemValidatorFalse(),new ItemRepoInMemoryMock(), new OrderItemRepoEmpty(), new OrderRepoEmpty());
        Assertions.assertFalse(itemService.updateItem(testItem));
    }
    @Test
    void updateItemNonExistent(){
        Assertions.assertFalse(itemService.updateItem(new Item(1,"Pencil",10.2)));
    }
    @Test
    void updateItemTrue(){
        itemService.createItem(testItem);
        Assertions.assertTrue(itemService.updateItem(new Item(1,"Pencil",10.2)));
    }
    @Test
    void deleteItemNull(){
        Assertions.assertThrows(IllegalArgumentException.class,()->  itemService.deleteItem(null));
    }
    @Test
    void deleteItem(){
        itemService.createItem(testItem);
        itemService.deleteItem(testItem);
        Assertions.assertArrayEquals(new ArrayList<>().toArray(),itemService.getAllItems().toArray());
    }
    @Test
    void getOrdersByNullItem(){
        Assertions.assertThrows(IllegalArgumentException.class,()->  itemService.getOrdersByItem(null));
    }
    @Test
    void getOrdersByItemEmpty(){
        Assertions.assertNull(itemService.getOrdersByItem(testItem));
    }
    @Test
    void getOrdersByItem(){
        itemService = new ItemService(new ItemValidatorTrue(),new ItemRepoInMemoryMock(), new OrderItemRepoNonEmpty(), new OrderRepoNonEmpty());
        ArrayList<Order> orders = new ArrayList<>();
        orders.add(new Order(1,1));
        orders.add(new Order(2,2));
        for(int i=0;i<orders.size();i++){
            Assertions.assertEquals(orders.get(i).getId(),itemService.getOrdersByItem(testItem).get(i).getId());
        }
    }

}
