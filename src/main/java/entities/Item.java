package entities;

public class Item extends Entity {
    private String name;
    private double value;
    public Item(int id, String name, double value){
        this.id=id;
        this.name=name;
        this.value=value;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }
}
