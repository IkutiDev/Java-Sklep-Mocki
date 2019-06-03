package entities;

public class Order extends Entity {
    private int clientId;
    public Order(int id, int clientId){
        this.id=id;
        this.clientId=clientId;
    }

    public int getClientId() {
        return clientId;
    }

    public void setClientId(int clientId) {
        this.clientId = clientId;
    }
}
