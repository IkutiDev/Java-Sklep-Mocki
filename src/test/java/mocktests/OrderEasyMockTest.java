package mocktests;

import entities.Item;
import entities.Order;
import entities.OrderItem;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import repositories.ItemRepo;
import repositories.OrderItemRepo;
import repositories.OrderRepo;
import services.OrderService;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.easymock.EasyMock.*;
import static org.easymock.EasyMock.replay;

class OrderEasyMockTest {
    private OrderRepo orderRepo;
    private OrderItemRepo orderItemRepo;
    private ItemRepo itemRepo;
    private OrderService orderService;
    @BeforeEach
    void setup(){
        orderRepo=createMock(OrderRepo.class);
        orderItemRepo=createMock(OrderItemRepo.class);
        itemRepo=createMock(ItemRepo.class);
        orderService = new OrderService(itemRepo,orderItemRepo,orderRepo);
    }
    @AfterEach
    void teardown(){
        orderService=null;
        itemRepo=null;
        orderRepo=null;
        orderItemRepo=null;
    }
    @Test
    void getAllOrdersWhenNoOrders(){
        expect(orderRepo.getAll()).andReturn(new ArrayList<>());
        replay(orderRepo);

        List<Order> orders = orderService.getAllOrders();

        Assertions.assertTrue(orders.isEmpty());
    }
    @Test
    void gettAllOrders(){
        ArrayList<Order> orders =new ArrayList<>();
        orders.add(new Order(1,3));
        orders.add(new Order(2,3));
        orders.add(new Order(3,1));
        expect(orderRepo.getAll()).andReturn(orders);
        replay(orderRepo);

        Assertions.assertFalse( orderService.getAllOrders().isEmpty());
    }
    @Test
    void totalValueZeroWhenOrderEmpty(){
        Order empty = new Order(1,2);
        expect(orderItemRepo.getByOrderId(empty.getId())).andReturn(new ArrayList<>());
        replay(orderItemRepo);
        Assertions.assertEquals(0,orderService.totalValueForOrder(empty));
    }
    @Test
    void getTotalValue(){
        Order order = new Order(1,2);
        Item item1 = new Item(1,"Pencil",10.5);
        Item item2 = new Item(2,"Pen",12.4);
        Item item3 = new Item(3,"Notepad",10.99);
        OrderItem orderItem1 = new OrderItem(item1.getId(),order.getId());
        OrderItem orderItem2 = new OrderItem(item2.getId(),order.getId());
        OrderItem orderItem3 = new OrderItem(item3.getId(),order.getId());

        List<OrderItem> orderItems = Arrays.asList(orderItem1,orderItem2,orderItem3);

        expect(orderItemRepo.getByOrderId(order.getId())).andReturn(orderItems);
        expect(itemRepo.getById(item1.getId())).andReturn(item1);
        expect(itemRepo.getById(item2.getId())).andReturn(item2);
        expect(itemRepo.getById(item3.getId())).andReturn(item3);
        replay(itemRepo);
        replay(orderItemRepo);
        double result = orderService.totalValueForOrder(order);
        double expected = item1.getValue()+item2.getValue()+item3.getValue();

            Assertions.assertEquals(expected,result,0.01);
    }
    @Test
    void clearOrderWhenNull(){
        Assertions.assertThrows(IllegalArgumentException.class,()->  orderService.clearOrderItems(null));
    }
    @Test
    void clearOrder(){
        Order order = new Order(1,2);
        Item item1 = new Item(1,"Pencil",10.5);
        Item item2 = new Item(2,"Pen",12.4);
        Item item3 = new Item(3,"Notepad",10.99);
        OrderItem orderItem1 = new OrderItem(item1.getId(),order.getId());
        OrderItem orderItem2 = new OrderItem(item2.getId(),order.getId());
        OrderItem orderItem3 = new OrderItem(item3.getId(),order.getId());

        List<OrderItem> orderItems = Arrays.asList(orderItem1,orderItem2,orderItem3);

        expect(orderItemRepo.getByOrderId(order.getId())).andReturn(orderItems);
        expect(orderItemRepo.delete(orderItem1)).andReturn(true);
        expect(orderItemRepo.delete(orderItem2)).andReturn(true);
        expect(orderItemRepo.delete(orderItem3)).andReturn(true);
        replay(orderItemRepo);



        Assertions.assertTrue(orderService.clearOrderItems(order));
        verify(orderItemRepo);
    }
    @Test
    void updateOrderWhenNull(){
        Order order = new Order(1,2);
        expect(orderRepo.getById(anyInt())).andReturn(null);
        replay(orderRepo);
        Assertions.assertFalse(orderService.updateOrder(order));
    }
    @Test
    void updateOrder(){
        Order order = new Order(1,2);
        expect(orderRepo.getById(order.getId())).andReturn(order);
        expect(orderRepo.update(order)).andReturn(true);
        replay(orderRepo);
        Assertions.assertTrue(orderService.updateOrder(order));
    }
    @Test
    void createOrder(){
        Order order = new Order(1,2);
        expect(orderRepo.add(order)).andReturn(true);
        replay(orderRepo);
        Assertions.assertTrue(orderService.createOrder(order));
    }
    @Test
    void addItemToOrder(){
        Order order = new Order(1,1);
        Item item1 = new Item(1,"Pencil",10.5);
        expect(orderItemRepo.getByItemId(item1.getId())).andReturn(null);
        expect(orderItemRepo.add(anyObject(OrderItem.class))).andReturn(true);
        replay(orderItemRepo);
        Assertions.assertTrue(orderService.addItemToOrder(item1,order));
    }
}
