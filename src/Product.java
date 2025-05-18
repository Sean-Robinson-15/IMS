public class Product {
    private int ID;
    private String name;
    private int quantity;
    private double price;

    //Constructor
    public Product(int ID, String name, int quantity, double price) {
        this.ID = ID;
        this.name = name;
        this.quantity = quantity;
        this.price = price;
    }

    public String getName() {
        return name;
    }
}