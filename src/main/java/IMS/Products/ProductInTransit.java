package IMS.Products;

public class ProductInTransit extends Product{
    private String orderID;

    public ProductInTransit(String orderID, String ID, String name, int quantity, double price) {
        super(ID, name, quantity, price);
        this.orderID = orderID;
    }

    public String getOrderID(){
        return orderID;
    }

    public void setOrderID(String orderID){
        this.orderID = orderID;
    }
}
