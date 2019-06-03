package entities;

public class OrderItem extends Entity {
    private int itemId;
    private int orderId;
    public OrderItem(int itemId, int orderId){
        this.itemId=itemId;
        this.orderId=orderId;
    }

    public int getItemId() {
        return itemId;
    }

    public void setItemId(int itemId) {
        this.itemId = itemId;
    }

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }
}
