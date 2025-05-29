package IMS.Products;

public class Product {
    private final String ID;
    private String name;
    private int quantity;
    private double price;

    //Constructor
    public Product(String ID, String name, int quantity, double price) {
        this.ID = ID;
        this.name = name;
        this.quantity = quantity;
        this.price = price;
    }

    //Getters
    public String getID() {
        return ID;
    }

    public String getName() {
        return name;
    }

    public int getQuantity() {
        return quantity;
    }

    public double getPrice() {
        return price;
    }

    //Setters
    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public void setPrice(double price) {
        this.price = price;
    }
    public void setName(String name) {
        this.name = name;
    }
}