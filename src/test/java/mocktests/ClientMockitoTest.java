package mocktests;

import entities.Client;
import entities.Order;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import repositories.ClientRepo;
import repositories.OrderRepo;
import services.ClientService;
import validators.ClientValidation;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;

class ClientMockitoTest {
    private ClientService clientService;
    private ClientRepo clientRepo;
    private OrderRepo orderRepo;
    private ClientValidation clientValidation;
    private Client client;
    @BeforeEach
    void setup(){
        orderRepo= Mockito.mock(OrderRepo.class);
        clientRepo = Mockito.mock(ClientRepo.class);
        clientValidation = Mockito.mock(ClientValidation.class);
        clientService = new ClientService(clientValidation,clientRepo,orderRepo);
        client = new Client(1,"Jan","Kowalski","jw@wp.pl");
    }
    @AfterEach
    void teardown(){
        orderRepo=null;
        clientRepo=null;
        clientValidation=null;
        clientService=null;
    }
    @Test
    void getAllClientOrders(){
        List<Order> orders = Arrays.asList(new Order(1,client.getId()),new Order(2,client.getId()));
        doReturn(orders).when(orderRepo).getOrderByClientId(client.getId());
        Assertions.assertArrayEquals(orders.toArray(),clientService.clientOrders(client).toArray());
    }
    @Test
    void getAllClientOrdersWhenNull(){

        doReturn(new ArrayList<>()).when(orderRepo).getOrderByClientId(client.getId());

        Assertions.assertTrue(clientService.clientOrders(client).isEmpty());
    }
    @Test
    void invalidClient(){
        doReturn(false).when(clientValidation).isValid(any());
        Assertions.assertFalse(clientService.createClient(client));
    }
    @Test
    void validClient(){
        doReturn(true).when(clientValidation).isValid(client);
        doReturn(true).when(clientRepo).add(client);
        Assertions.assertTrue(clientService.createClient(client));
    }
    @Test
    void getAllClients(){
        ArrayList<Client> clients = new ArrayList<>();
        clients.add(new Client(2,"Test","Test","test@test.test"));
        clients.add(new Client(3,"Test","Test","test@test.test"));
        clients.add(new Client(4,"Test","Test","test@test.test"));
        doReturn(clients).when(clientRepo).getAll();
        Assertions.assertArrayEquals(clients.toArray(),clientService.getAllClients().toArray());
    }
    @Test
    void clientNullWhenUpdate(){
        Assertions.assertThrows(IllegalArgumentException.class,()->  clientService.updateClient(null));
    }
    @Test
    void clientUpdateFail(){
        doReturn(true).when(clientValidation).isValid(client);
        doReturn(false).when(clientRepo).update(client);
        Assertions.assertFalse(clientService.updateClient(client));
    }
    @Test
    void clientDelete(){
        doReturn(true).when(clientValidation).isValid(client);
        doReturn(true).when(clientRepo).delete(client);
        clientService.deleteClient(client);
        Mockito.verify(clientRepo,Mockito.times(1)).delete(client);
    }
    @Test
    void printClient(){
        String result="Name: "+client.getName()+"\n"+"Surname: "+client.getSurname()+"\n"+"Email: "+client.getEmail()+"\n";
        Assertions.assertEquals(result,clientService.printClient(client));
    }
    @Test
    void getAllClientsEmpty(){
        doReturn(new ArrayList<>()).when(clientRepo).getAll();
        Assertions.assertTrue(clientService.getAllClients().isEmpty());
    }

}
