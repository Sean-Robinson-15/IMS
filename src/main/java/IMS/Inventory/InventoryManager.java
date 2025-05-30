package IMS.Inventory;
import IMS.Orders.Purchase;
import IMS.Orders.Transaction;
import IMS.Products.Product;
import IMS.UI.InputValidator;

import java.util.ArrayList;

public class InventoryManager {
    private final ProductManager productManager;
    private final UserManager userManager;
    private final TransactionManager transactionManager;
    private final BasketManager basketManager;
    public static final int DEFAULT_LOW_STOCK_THRESHOLD = 10;


    public InventoryManager(ProductManager productManager, BasketManager basketManager, UserManager userManager, TransactionManager transactionManager) {
        this.productManager = productManager;
        this.userManager = userManager;
        this.transactionManager = transactionManager;
        this.basketManager = basketManager;
    }

    public String checkoutBasket(String userID) {
        if (!userManager.userExists(userID)) {
            return "Error: User does not exist. Please try again.";
        }
        if(basketManager.isBasketEmpty()) {
            return "Error: Basket is empty. Please try again.";
        }

        String orderId = getNextOrderID(); // This should be generated properly
        Transaction transaction = basketManager.createSale(orderId, userID);
        String output = transactionManager.addTransaction(orderId, transaction);
        if (output.contains("Error:")) {
            return output;
        }
        basketManager.clearBasket();

        return orderId + " Checked out for £" + transaction.getTotalCost();

    }
    public String getNextOrderID() {
        int nextOrderID = transactionManager.getAllTransactions().size() + 1;
        return "T" + String.format("%03d",nextOrderID);
    }

    public void testItems() {
        productManager.randomize(10);
        ArrayList<Product> inventoryArray = productManager.getAllItems();
        Product prod = inventoryArray.get((int)(Math.random()*inventoryArray.size()));
        int quantity = (int)(Math.random()*prod.getQuantity());
        basketManager.addToBasket(prod.getID(), prod.getName(), String.valueOf(quantity), prod.getPrice());
        userManager.addCustomer("C001", "Customer 1", "101 Made Up Lane", "Customer1@gmail.com");
        userManager.addSupplier("S001", "Supplier 1", "202 Not Real Road", "Supplier1@gmail.com", "Sales");
        checkoutBasket("S001");
        transactionManager.addTransaction("T002", new Purchase("T002", "C001", getRandomProducts(inventoryArray, (int)(Math.random()*10) + 1)));
        productManager.addInTransitItem( new Product("BNU001", "Prod1", 69, 10));
    }

    public ArrayList<Product> getRandomProducts(ArrayList<Product> products, int num) {
        ArrayList<Product> randomProducts = new ArrayList<>();
        for (int i = 0; i < num; i++) {
            int randomIndex = (int)(Math.random()*products.size());
            Product randomProduct = products.get(randomIndex);
            randomProduct.setQuantity((int)(Math.random()*randomProduct.getQuantity()));
            randomProducts.add(products.get(randomIndex));
        }
        return randomProducts;
    }

}
