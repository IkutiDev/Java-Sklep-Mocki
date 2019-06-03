package services;

import entities.Client;
import entities.Order;
import repositories.ClientRepo;
import repositories.OrderRepo;
import validators.ClientValidation;

import java.util.List;

public class ClientService {
    private ClientValidation clientValidation;
    private ClientRepo clientRepo;
    private OrderRepo orderRepo;
    public ClientService(ClientValidation clientValidation, ClientRepo clientRepo, OrderRepo orderRepo){
        this.clientValidation = clientValidation;
        this.clientRepo = clientRepo;
        this.orderRepo = orderRepo;
    }
    public List<Client> getAllClients() {
        return clientRepo.getAll();
    }
    public boolean createClient(Client client)  {
        clientNotNull(client);
        if(clientValidation.isValid(client)){
            return clientRepo.add(client);
        }
        return false;
    }
    public boolean deleteClient(Client client){
        clientNotNull(client);
        if(orderRepo.getOrderByClientId(client.getId()).isEmpty()){
            return clientRepo.delete(client);
        }
        return false;
    }
    public boolean updateClient(Client client){
        clientNotNull(client);
        if(clientValidation.isValid(client)){
            return clientRepo.update(client);
        }
        return false;
    }
    public String printClient(Client client){
        clientNotNull(client);
        return "Name: "+client.getName()+"\n"+"Surname: "+client.getSurname()+"\n"+"Email: "+client.getEmail()+"\n";
    }
    public List<Order> clientOrders(Client client){
        clientNotNull(client);
        return orderRepo.getOrderByClientId(client.getId());
    }
    private void clientNotNull(Client client){
        if(client==null){
            throw new IllegalArgumentException("Client is null");
        }
    }
}
